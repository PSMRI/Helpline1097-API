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
package com.iemr.helpline1097.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import com.iemr.helpline1097.utils.http.HTTPRequestInterceptor;

@ExtendWith(MockitoExtension.class)
class InterceptorConfigTest {

	@Mock
	private HTTPRequestInterceptor requestInterceptor;

	@Test
	void theHttpRequestInterceptorIsRegistered() {
		InterceptorConfig config = new InterceptorConfig();
		config.requestInterceptor = requestInterceptor;
		InterceptorRegistry registry = new InterceptorRegistry();

		config.addInterceptors(registry);

		@SuppressWarnings("unchecked")
		List<Object> registrations = (List<Object>) ReflectionTestUtils.getField(registry, "registrations");
		assertEquals(1, registrations.size());
		assertSame(requestInterceptor, ReflectionTestUtils.getField(registrations.get(0), "interceptor"));
	}
}
