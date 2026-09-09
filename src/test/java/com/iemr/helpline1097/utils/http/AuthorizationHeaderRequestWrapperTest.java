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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

class AuthorizationHeaderRequestWrapperTest {

	private static AuthorizationHeaderRequestWrapper wrapper(String override, String... incomingHeaders) {
		MockHttpServletRequest request = new MockHttpServletRequest();
		for (int i = 0; i < incomingHeaders.length; i += 2) {
			request.addHeader(incomingHeaders[i], incomingHeaders[i + 1]);
		}
		return new AuthorizationHeaderRequestWrapper(request, override);
	}

	@Test
	void authorizationIsReplacedByTheOverrideValue() {
		AuthorizationHeaderRequestWrapper wrapper = wrapper("", "Authorization", "Bearer original");

		assertEquals("", wrapper.getHeader("Authorization"));
	}

	@Test
	void authorizationLookupIsCaseInsensitive() {
		AuthorizationHeaderRequestWrapper wrapper = wrapper("replaced", "Authorization", "Bearer original");

		assertEquals("replaced", wrapper.getHeader("authorization"));
	}

	@Test
	void otherHeadersPassThroughUnchanged() {
		AuthorizationHeaderRequestWrapper wrapper = wrapper("", "JwtToken", "token");

		assertEquals("token", wrapper.getHeader("JwtToken"));
	}

	@Test
	void getHeadersReturnsOnlyTheOverrideForAuthorization() {
		AuthorizationHeaderRequestWrapper wrapper = wrapper("replaced", "Authorization", "Bearer original");

		assertEquals(Collections.singletonList("replaced"), Collections.list(wrapper.getHeaders("Authorization")));
	}

	@Test
	void getHeadersPassesOtherHeadersThrough() {
		AuthorizationHeaderRequestWrapper wrapper = wrapper("", "JwtToken", "token");

		assertEquals(Collections.singletonList("token"), Collections.list(wrapper.getHeaders("JwtToken")));
	}

	@Test
	void getHeaderNamesAlwaysIncludesAuthorization() {
		AuthorizationHeaderRequestWrapper wrapper = wrapper("", "JwtToken", "token");

		List<String> names = Collections.list(wrapper.getHeaderNames());

		assertTrue(names.contains("Authorization"));
		assertTrue(names.contains("JwtToken"));
	}

	@Test
	void getHeaderNamesDoesNotDuplicateAnExistingAuthorization() {
		AuthorizationHeaderRequestWrapper wrapper = wrapper("", "Authorization", "Bearer original");

		List<String> names = Collections.list(wrapper.getHeaderNames());

		assertEquals(1, names.stream().filter("Authorization"::equals).count());
	}
}
