/*
* AMRIT – Accessible Medical Records via Integrated Technology
* Integrated EHR (Electronic Health Records) Solution
*
* Copyright (C) "Piramal Swasthya Management and Research Institute"
*
* This file is part of AMRIT.
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see https://www.gnu.org/licenses/.
*/
package com.iemr.helpline1097.utils.http;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.test.util.ReflectionTestUtils;

import jakarta.ws.rs.core.MediaType;

import com.iemr.helpline1097.utils.redis.RedisSessionException;
import com.iemr.helpline1097.utils.sessionobject.SessionObject;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class HTTPRequestInterceptorTest {

	@Mock
	private SessionObject sessionObject;

	@Mock
	private HttpServletRequest request;

	@Mock
	private HttpServletResponse response;

	private HTTPRequestInterceptor interceptor;

	@BeforeEach
	void setUp() {
		interceptor = new HTTPRequestInterceptor();
		interceptor.setSessionObject(sessionObject);
		ReflectionTestUtils.setField(interceptor, "allowedOrigins", "https://amrit.example.org");
	}

	@Test
	void preHandleAllowsRequestsWithoutAnAuthorizationHeader() throws Exception {
		assertTrue(interceptor.preHandle(request, response, new Object()));
	}

	@Test
	void preHandleAllowsRequestsWithAnEmptyAuthorizationHeader() throws Exception {
		when(request.getHeader("Authorization")).thenReturn("");

		assertTrue(interceptor.preHandle(request, response, new Object()));
	}

	@Test
	void preHandleStripsTheBearerPrefixAndAllowsTheRequest() throws Exception {
		when(request.getHeader("Authorization")).thenReturn("Bearer token");
		when(request.getRequestURI()).thenReturn("/co/getfeedbacklist");
		when(request.getMethod()).thenReturn("POST");

		assertTrue(interceptor.preHandle(request, response, new Object()));
	}

	@Test
	void preHandleAllowsWhitelistedApisThrough() throws Exception {
		when(request.getHeader("Authorization")).thenReturn("token");
		when(request.getRequestURI()).thenReturn("/user/userAuthenticate");
		when(request.getMethod()).thenReturn("POST");

		assertTrue(interceptor.preHandle(request, response, new Object()));
	}

	@Test
	void preHandleSkipsInspectionForOptionsRequests() throws Exception {
		when(request.getHeader("Authorization")).thenReturn("token");
		when(request.getRequestURI()).thenReturn("/co/getfeedbacklist");
		when(request.getMethod()).thenReturn("OPTIONS");

		assertTrue(interceptor.preHandle(request, response, new Object()));
	}

	@Test
	void postHandleRefreshesTheSessionForAnAuthorizedRequest() throws Exception {
		when(request.getHeader("Authorization")).thenReturn("Bearer token");
		when(request.getRequestURI()).thenReturn("/co/getfeedbacklist");
		when(sessionObject.getSessionObject("token")).thenReturn("{\"userName\":\"agent\"}");

		interceptor.postHandle(request, response, new Object(), null);

		verify(sessionObject).updateSessionObject("token", "{\"userName\":\"agent\"}");
	}

	@Test
	void postHandleLeavesTheSessionAloneWithoutAnAuthorizationHeader() throws Exception {
		interceptor.postHandle(request, response, new Object(), null);

		verify(sessionObject, never()).updateSessionObject(anyString(), anyString());
	}

	@Test
	void postHandleSwallowsSessionFailures() throws Exception {
		when(request.getHeader("Authorization")).thenReturn("token");
		when(request.getRequestURI()).thenReturn("/co/getfeedbacklist");
		when(sessionObject.getSessionObject("token")).thenThrow(new RedisSessionException("session expired"));

		interceptor.postHandle(request, response, new Object(), null);

		verify(sessionObject, never()).updateSessionObject(anyString(), anyString());
	}

	@Test
	void afterCompletionIsANoOp() throws Exception {
		interceptor.afterCompletion(request, response, new Object(), null);

		verify(sessionObject, never()).updateSessionObject(anyString(), anyString());
	}

	@Test
	void preHandleWritesAnErrorResponseAndAddsCorsHeadersForAnAllowedOrigin() throws Exception {
		ServletOutputStream outputStream = mock(ServletOutputStream.class);
		when(request.getHeader("Authorization")).thenReturn("token");
		when(request.getHeader("Origin")).thenReturn("https://amrit.example.org");
		when(request.getMethod()).thenReturn("POST");
		// A null URI makes the request-API lookup throw, taking the error branch.
		when(request.getRequestURI()).thenReturn(null);
		when(response.getOutputStream()).thenReturn(outputStream);

		assertFalse(interceptor.preHandle(request, response, new Object()));

		verify(response).setHeader("Access-Control-Allow-Origin", "https://amrit.example.org");
		verify(response).setHeader("Access-Control-Allow-Credentials", "true");
		verify(response).setContentType(MediaType.APPLICATION_JSON);
	}

	@Test
	void preHandleWithholdsCorsHeadersOnTheErrorResponseForAnUnknownOrigin() throws Exception {
		ServletOutputStream outputStream = mock(ServletOutputStream.class);
		when(request.getHeader("Authorization")).thenReturn("token");
		when(request.getHeader("Origin")).thenReturn("https://evil.example.com");
		when(request.getMethod()).thenReturn("POST");
		when(request.getRequestURI()).thenReturn(null);
		when(response.getOutputStream()).thenReturn(outputStream);

		assertFalse(interceptor.preHandle(request, response, new Object()));

		verify(response, never()).setHeader(eq("Access-Control-Allow-Origin"), anyString());
	}

	@Test
	void preHandleWithholdsCorsHeadersOnTheErrorResponseWhenNoOriginsAreConfigured() throws Exception {
		ServletOutputStream outputStream = mock(ServletOutputStream.class);
		ReflectionTestUtils.setField(interceptor, "allowedOrigins", "");
		when(request.getHeader("Authorization")).thenReturn("token");
		when(request.getHeader("Origin")).thenReturn("https://amrit.example.org");
		when(request.getMethod()).thenReturn("POST");
		when(request.getRequestURI()).thenReturn(null);
		when(response.getOutputStream()).thenReturn(outputStream);

		assertFalse(interceptor.preHandle(request, response, new Object()));

		verify(response, never()).setHeader(eq("Access-Control-Allow-Origin"), anyString());
	}

	@Test
	void preHandleWithholdsCorsHeadersOnTheErrorResponseWhenNoOriginIsSent() throws Exception {
		ServletOutputStream outputStream = mock(ServletOutputStream.class);
		when(request.getHeader("Authorization")).thenReturn("token");
		when(request.getMethod()).thenReturn("POST");
		when(request.getRequestURI()).thenReturn(null);
		when(response.getOutputStream()).thenReturn(outputStream);

		assertFalse(interceptor.preHandle(request, response, new Object()));

		verify(response, never()).setHeader(eq("Access-Control-Allow-Origin"), anyString());
	}

	@Test
	void preHandleRejectsTheSpringErrorDispatch() throws Exception {
		when(request.getHeader("Authorization")).thenReturn("token");
		when(request.getRequestURI()).thenReturn("/error");
		when(request.getMethod()).thenReturn("POST");

		assertFalse(interceptor.preHandle(request, response, new Object()));
	}
}
