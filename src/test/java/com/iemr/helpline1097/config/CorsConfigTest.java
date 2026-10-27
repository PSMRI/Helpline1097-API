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
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

class CorsConfigTest {

	private static CorsConfiguration configurationFor(String allowedOrigins) {
		CorsConfig corsConfig = new CorsConfig();
		ReflectionTestUtils.setField(corsConfig, "allowedOrigins", allowedOrigins);
		CorsRegistry registry = new CorsRegistry();

		corsConfig.addCorsMappings(registry);

		@SuppressWarnings("unchecked")
		List<Object> registrations = (List<Object>) ReflectionTestUtils.getField(registry, "registrations");
		Object registration = registrations.get(0);
		return (CorsConfiguration) ReflectionTestUtils.invokeMethod(registration, "getCorsConfiguration");
	}

	@Test
	void everyConfiguredOriginPatternIsRegisteredAndTrimmed() {
		CorsConfiguration configuration = configurationFor("https://amrit.example.org , http://localhost:*");

		assertEquals(List.of("https://amrit.example.org", "http://localhost:*"),
				configuration.getAllowedOriginPatterns());
	}

	@Test
	void onlyTheSupportedHttpMethodsAreAllowed() {
		CorsConfiguration configuration = configurationFor("https://amrit.example.org");

		assertEquals(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"), configuration.getAllowedMethods());
	}

	@Test
	void credentialsAreAllowedAndAuthorizationIsExposed() {
		CorsConfiguration configuration = configurationFor("https://amrit.example.org");

		assertEquals(Boolean.TRUE, configuration.getAllowCredentials());
		assertEquals(List.of("Authorization"), configuration.getExposedHeaders());
		assertEquals(3600L, configuration.getMaxAge());
	}

	@Test
	void theJwtAndAuthorizationHeadersAreAccepted() {
		CorsConfiguration configuration = configurationFor("https://amrit.example.org");

		assertTrue(configuration.getAllowedHeaders().contains("Authorization"));
		assertTrue(configuration.getAllowedHeaders().contains("Jwttoken"));
	}
}
