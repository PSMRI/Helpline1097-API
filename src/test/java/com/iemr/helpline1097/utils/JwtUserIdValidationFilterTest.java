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
package com.iemr.helpline1097.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.iemr.helpline1097.utils.exception.IEMRException;
import com.iemr.helpline1097.utils.http.AuthorizationHeaderRequestWrapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class JwtUserIdValidationFilterTest {

	private static final String ALLOWED = "https://amrit.example.org, http://localhost:*";

	@Mock
	private JwtAuthenticationUtil jwtAuthenticationUtil;

	@Mock
	private HttpServletRequest request;

	@Mock
	private HttpServletResponse response;

	@Mock
	private FilterChain filterChain;

	private JwtUserIdValidationFilter filter(String allowedOrigins) {
		return new JwtUserIdValidationFilter(jwtAuthenticationUtil, allowedOrigins);
	}

	@Test
	void optionsRequestWithoutAnOriginIsForbidden() throws Exception {
		when(request.getMethod()).thenReturn("OPTIONS");
		when(request.getRequestURI()).thenReturn("/co/getfeedbacklist");

		filter(ALLOWED).doFilter(request, response, filterChain);

		verify(response).sendError(eq(HttpServletResponse.SC_FORBIDDEN), anyString());
		verify(filterChain, never()).doFilter(any(), any());
	}

	@Test
	void optionsRequestFromAnUnknownOriginIsForbidden() throws Exception {
		when(request.getMethod()).thenReturn("OPTIONS");
		when(request.getHeader("Origin")).thenReturn("https://evil.example.com");
		when(request.getRequestURI()).thenReturn("/co/getfeedbacklist");

		filter(ALLOWED).doFilter(request, response, filterChain);

		verify(response).sendError(HttpServletResponse.SC_FORBIDDEN, "Origin not allowed");
	}

	@Test
	void optionsPreflightFromAnAllowedOriginAnswersOkWithCorsHeaders() throws Exception {
		when(request.getMethod()).thenReturn("OPTIONS");
		when(request.getHeader("Origin")).thenReturn("https://amrit.example.org");
		when(request.getRequestURI()).thenReturn("/co/getfeedbacklist");

		filter(ALLOWED).doFilter(request, response, filterChain);

		verify(response).setStatus(HttpServletResponse.SC_OK);
		verify(response).setHeader("Access-Control-Allow-Origin", "https://amrit.example.org");
		verify(response).setHeader("Access-Control-Allow-Credentials", "true");
		verify(filterChain, never()).doFilter(any(), any());
	}

	@Test
	void optionsPreflightIsForbiddenWhenNoOriginsAreConfigured() throws Exception {
		when(request.getMethod()).thenReturn("OPTIONS");
		when(request.getHeader("Origin")).thenReturn("https://amrit.example.org");
		when(request.getRequestURI()).thenReturn("/co/getfeedbacklist");

		filter("").doFilter(request, response, filterChain);

		verify(response).sendError(eq(HttpServletResponse.SC_FORBIDDEN), anyString());
	}

	@Test
	void postFromAnUnknownOriginIsForbidden() throws Exception {
		when(request.getMethod()).thenReturn("POST");
		when(request.getHeader("Origin")).thenReturn("https://evil.example.com");
		when(request.getRequestURI()).thenReturn("/co/getfeedbacklist");

		filter(ALLOWED).doFilter(request, response, filterChain);

		verify(response).sendError(HttpServletResponse.SC_FORBIDDEN, "Origin not allowed");
	}

	@Test
	void wildcardLocalhostPortIsAllowed() throws Exception {
		when(request.getMethod()).thenReturn("GET");
		when(request.getHeader("Origin")).thenReturn("http://localhost:4200");
		when(request.getRequestURI()).thenReturn("/version");
		when(request.getContextPath()).thenReturn("");

		filter(ALLOWED).doFilter(request, response, filterChain);

		verify(response).setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
		verify(filterChain).doFilter(request, response);
	}

	@Test
	void publicPathsSkipTokenValidation() throws Exception {
		when(request.getMethod()).thenReturn("GET");
		when(request.getRequestURI()).thenReturn("/health");
		when(request.getContextPath()).thenReturn("");

		filter(ALLOWED).doFilter(request, response, filterChain);

		verify(filterChain).doFilter(request, response);
		verify(jwtAuthenticationUtil, never()).validateUserIdAndJwtToken(anyString());
	}

	@Test
	void swaggerPathsSkipTokenValidation() throws Exception {
		when(request.getMethod()).thenReturn("GET");
		when(request.getRequestURI()).thenReturn("/swagger-ui/index.html");
		when(request.getContextPath()).thenReturn("");

		filter(ALLOWED).doFilter(request, response, filterChain);

		verify(filterChain).doFilter(request, response);
	}

	@Test
	void aValidCookieTokenForwardsWithABlankedAuthorizationHeader() throws Exception {
		when(request.getMethod()).thenReturn("POST");
		when(request.getRequestURI()).thenReturn("/co/getfeedbacklist");
		when(request.getContextPath()).thenReturn("");
		when(request.getCookies()).thenReturn(new Cookie[] { new Cookie("Jwttoken", "token") });
		when(jwtAuthenticationUtil.validateUserIdAndJwtToken("token")).thenReturn(true);

		filter(ALLOWED).doFilter(request, response, filterChain);

		ArgumentCaptor<AuthorizationHeaderRequestWrapper> captor = ArgumentCaptor
				.forClass(AuthorizationHeaderRequestWrapper.class);
		verify(filterChain).doFilter(captor.capture(), eq(response));
		assertEquals("", captor.getValue().getHeader("Authorization"));
	}

	@Test
	void aUserIdCookieIsClearedBeforeTheRequestProceeds() throws Exception {
		when(request.getMethod()).thenReturn("POST");
		when(request.getRequestURI()).thenReturn("/co/getfeedbacklist");
		when(request.getContextPath()).thenReturn("");
		when(request.getCookies())
				.thenReturn(new Cookie[] { new Cookie("userId", "42"), new Cookie("Jwttoken", "token") });
		when(jwtAuthenticationUtil.validateUserIdAndJwtToken("token")).thenReturn(true);

		filter(ALLOWED).doFilter(request, response, filterChain);

		ArgumentCaptor<Cookie> captor = ArgumentCaptor.forClass(Cookie.class);
		verify(response).addCookie(captor.capture());
		assertEquals("userId", captor.getValue().getName());
		assertEquals(0, captor.getValue().getMaxAge());
	}

	@Test
	void aValidHeaderTokenForwardsTheRequest() throws Exception {
		when(request.getMethod()).thenReturn("POST");
		when(request.getRequestURI()).thenReturn("/co/getfeedbacklist");
		when(request.getContextPath()).thenReturn("");
		when(request.getHeader("JwtToken")).thenReturn("header-token");
		when(jwtAuthenticationUtil.validateUserIdAndJwtToken("header-token")).thenReturn(true);

		filter(ALLOWED).doFilter(request, response, filterChain);

		verify(filterChain).doFilter(any(AuthorizationHeaderRequestWrapper.class), eq(response));
	}

	@Test
	void anOkHttpClientWithAnAuthorizationHeaderIsForwardedAsIs() throws Exception {
		when(request.getMethod()).thenReturn("POST");
		when(request.getRequestURI()).thenReturn("/co/getfeedbacklist");
		when(request.getContextPath()).thenReturn("");
		when(request.getHeader("User-Agent")).thenReturn("okhttp/4.9.0");
		when(request.getHeader("Authorization")).thenReturn("Bearer token");

		filter(ALLOWED).doFilter(request, response, filterChain);

		verify(filterChain).doFilter(request, response);
	}

	@Test
	void aBrowserRequestWithNoTokenIsUnauthorized() throws Exception {
		when(request.getMethod()).thenReturn("POST");
		when(request.getRequestURI()).thenReturn("/co/getfeedbacklist");
		when(request.getContextPath()).thenReturn("");
		when(request.getHeader("User-Agent")).thenReturn("Mozilla/5.0");

		filter(ALLOWED).doFilter(request, response, filterChain);

		verify(response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Invalid or missing token");
	}

	@Test
	void aRejectedCookieTokenIsUnauthorized() throws Exception {
		when(request.getMethod()).thenReturn("POST");
		when(request.getRequestURI()).thenReturn("/co/getfeedbacklist");
		when(request.getContextPath()).thenReturn("");
		when(request.getCookies()).thenReturn(new Cookie[] { new Cookie("Jwttoken", "token") });
		when(jwtAuthenticationUtil.validateUserIdAndJwtToken("token")).thenReturn(false);

		filter(ALLOWED).doFilter(request, response, filterChain);

		verify(response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Invalid or missing token");
	}

	@Test
	void aValidationFailureIsReportedAsUnauthorized() throws Exception {
		when(request.getMethod()).thenReturn("POST");
		when(request.getRequestURI()).thenReturn("/co/getfeedbacklist");
		when(request.getContextPath()).thenReturn("");
		when(request.getCookies()).thenReturn(new Cookie[] { new Cookie("Jwttoken", "token") });
		when(jwtAuthenticationUtil.validateUserIdAndJwtToken("token"))
				.thenThrow(new IEMRException("session expired"));

		filter(ALLOWED).doFilter(request, response, filterChain);

		verify(response).sendError(eq(HttpServletResponse.SC_UNAUTHORIZED), anyString());
	}

	@Test
	void requestsWithoutAnOriginStillReachTheTokenChecks() throws Exception {
		when(request.getMethod()).thenReturn("POST");
		when(request.getRequestURI()).thenReturn("/co/getfeedbacklist");
		when(request.getContextPath()).thenReturn("");

		filter(null).doFilter(request, response, filterChain);

		verify(response).sendError(anyInt(), anyString());
	}

	@Test
	void loginEndpointsSkipTokenValidation() throws Exception {
		when(request.getMethod()).thenReturn("POST");
		when(request.getRequestURI()).thenReturn("/user/userAuthenticate");
		when(request.getContextPath()).thenReturn("");

		filter(ALLOWED).doFilter(request, response, filterChain);

		verify(filterChain).doFilter(request, response);
	}

	@Test
	void aCookieJarWithoutAJwtTokenFallsThroughToTheUserAgentChecks() throws Exception {
		when(request.getMethod()).thenReturn("POST");
		when(request.getRequestURI()).thenReturn("/co/getfeedbacklist");
		when(request.getContextPath()).thenReturn("");
		when(request.getCookies()).thenReturn(new Cookie[] { new Cookie("other", "x") });

		filter(ALLOWED).doFilter(request, response, filterChain);

		verify(response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Invalid or missing token");
	}

	@Test
	void anOkHttpClientWithoutAnAuthorizationHeaderIsUnauthorized() throws Exception {
		when(request.getMethod()).thenReturn("POST");
		when(request.getRequestURI()).thenReturn("/co/getfeedbacklist");
		when(request.getContextPath()).thenReturn("");
		when(request.getHeader("User-Agent")).thenReturn("okhttp/4.9.0");

		filter(ALLOWED).doFilter(request, response, filterChain);

		verify(response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Invalid or missing token");
	}

	@Test
	void whitespaceOnlyAllowedOriginsRejectEveryOrigin() throws Exception {
		when(request.getMethod()).thenReturn("OPTIONS");
		when(request.getHeader("Origin")).thenReturn("https://amrit.example.org");
		when(request.getRequestURI()).thenReturn("/co/getfeedbacklist");

		filter("   ").doFilter(request, response, filterChain);

		verify(response).sendError(eq(HttpServletResponse.SC_FORBIDDEN), anyString());
	}

	@Test
	void aRejectedHeaderTokenIsUnauthorized() throws Exception {
		when(request.getMethod()).thenReturn("POST");
		when(request.getRequestURI()).thenReturn("/co/getfeedbacklist");
		when(request.getContextPath()).thenReturn("");
		when(request.getHeader("JwtToken")).thenReturn("header-token");
		when(jwtAuthenticationUtil.validateUserIdAndJwtToken("header-token")).thenReturn(false);

		filter(ALLOWED).doFilter(request, response, filterChain);

		verify(response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Invalid or missing token");
		verify(filterChain, never()).doFilter(any(), any());
	}
}
