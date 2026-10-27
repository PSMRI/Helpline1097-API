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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.Cookie;

class RestTemplateUtilTest {

	@AfterEach
	void tearDown() {
		RequestContextHolder.resetRequestAttributes();
		UserAgentContext.clear();
	}

	@Test
	void withoutAServletRequestOnlyContentTypeAndAuthorizationAreSet() {
		HttpEntity<Object> entity = RestTemplateUtil.createRequestEntity("body", "Bearer token");

		assertEquals("body", entity.getBody());
		assertEquals("Bearer token", entity.getHeaders().getFirst(HttpHeaders.AUTHORIZATION));
		assertTrue(entity.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE).startsWith("application/json"));
		assertFalse(entity.getHeaders().containsKey("JwtToken"));
	}

	@Test
	void theIncomingJwtTokenHeaderIsForwarded() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addHeader("JwtToken", "header-token");
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		HttpEntity<Object> entity = RestTemplateUtil.createRequestEntity("body", "Bearer token");

		assertEquals("header-token", entity.getHeaders().getFirst("JwtToken"));
		assertNull(entity.getHeaders().getFirst(HttpHeaders.COOKIE));
	}

	@Test
	void theIncomingJwtTokenCookieIsForwardedAsACookieHeader() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setCookies(new Cookie("Jwttoken", "cookie-token"));
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		HttpEntity<Object> entity = RestTemplateUtil.createRequestEntity("body", "Bearer token");

		assertEquals("Jwttoken=cookie-token", entity.getHeaders().getFirst(HttpHeaders.COOKIE));
	}

	@Test
	void theCallersUserAgentIsForwardedWhenOneIsInScope() {
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));
		UserAgentContext.setUserAgent("okhttp/4.9.0");

		HttpEntity<Object> entity = RestTemplateUtil.createRequestEntity("body", "Bearer token");

		assertEquals("okhttp/4.9.0", entity.getHeaders().getFirst(HttpHeaders.USER_AGENT));
	}

	@Test
	void noUserAgentHeaderIsAddedWhenNoneIsInScope() {
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));

		HttpEntity<Object> entity = RestTemplateUtil.createRequestEntity("body", "Bearer token");

		assertFalse(entity.getHeaders().containsKey(HttpHeaders.USER_AGENT));
	}
}
