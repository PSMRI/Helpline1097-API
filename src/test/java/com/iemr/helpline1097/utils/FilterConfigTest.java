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
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.core.Ordered;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class FilterConfigTest {

	@Mock
	private JwtAuthenticationUtil jwtAuthenticationUtil;

	@Test
	void theJwtFilterIsRegisteredFirstForEveryPath() {
		FilterConfig filterConfig = new FilterConfig();
		ReflectionTestUtils.setField(filterConfig, "allowedOrigins", "https://amrit.example.org");

		FilterRegistrationBean<JwtUserIdValidationFilter> registration = filterConfig
				.jwtUserIdValidationFilter(jwtAuthenticationUtil);

		assertEquals(Ordered.HIGHEST_PRECEDENCE, registration.getOrder());
		assertTrue(registration.getUrlPatterns().contains("/*"));
		assertEquals("https://amrit.example.org",
				ReflectionTestUtils.getField(registration.getFilter(), "allowedOrigins"));
	}
}
