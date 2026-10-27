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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

class CookieUtilTest {

	private final CookieUtil cookieUtil = new CookieUtil();

	private static HttpServletRequest requestWith(Cookie... cookies) {
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getCookies()).thenReturn(cookies);
		return request;
	}

	@Test
	void getCookieValueFindsTheNamedCookie() {
		HttpServletRequest request = requestWith(new Cookie("other", "x"), new Cookie("Jwttoken", "token-value"));

		assertEquals(Optional.of("token-value"), cookieUtil.getCookieValue(request, "Jwttoken"));
	}

	@Test
	void getCookieValueIsEmptyWhenTheCookieIsAbsent() {
		assertTrue(cookieUtil.getCookieValue(requestWith(new Cookie("other", "x")), "Jwttoken").isEmpty());
	}

	@Test
	void getCookieValueIsEmptyWhenThereAreNoCookies() {
		assertTrue(cookieUtil.getCookieValue(requestWith((Cookie[]) null), "Jwttoken").isEmpty());
	}

	@Test
	void getJwtTokenFromCookieReturnsTheTokenValue() {
		HttpServletRequest request = requestWith(new Cookie("Jwttoken", "token-value"));

		assertEquals("token-value", CookieUtil.getJwtTokenFromCookie(request));
	}

	@Test
	void getJwtTokenFromCookieReturnsNullWhenTheTokenCookieIsAbsent() {
		assertNull(CookieUtil.getJwtTokenFromCookie(requestWith(new Cookie("other", "x"))));
	}

	@Test
	void getJwtTokenFromCookieReturnsNullWhenThereAreNoCookies() {
		assertNull(CookieUtil.getJwtTokenFromCookie(requestWith((Cookie[]) null)));
	}
}
