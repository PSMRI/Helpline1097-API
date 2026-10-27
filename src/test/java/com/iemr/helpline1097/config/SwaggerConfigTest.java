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
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.mock.env.MockEnvironment;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;

class SwaggerConfigTest {

	private final SwaggerConfig swaggerConfig = new SwaggerConfig();

	@Test
	void theApiIsDescribedWithABearerSecurityScheme() {
		OpenAPI openApi = swaggerConfig.customOpenAPI(new MockEnvironment());

		assertEquals("Helpline 1097 API", openApi.getInfo().getTitle());
		SecurityScheme scheme = openApi.getComponents().getSecuritySchemes().get("my security");
		assertEquals(SecurityScheme.Type.HTTP, scheme.getType());
		assertEquals("bearer", scheme.getScheme());
		assertNotNull(openApi.getSecurity());
	}

	@Test
	void theConfiguredEnvironmentUrlsBecomeServers() {
		MockEnvironment environment = new MockEnvironment();
		environment.setProperty("api.dev.url", "https://dev.example.org");
		environment.setProperty("api.uat.url", "https://uat.example.org");
		environment.setProperty("api.demo.url", "https://demo.example.org");

		OpenAPI openApi = swaggerConfig.customOpenAPI(environment);

		assertEquals(3, openApi.getServers().size());
		assertEquals("https://dev.example.org", openApi.getServers().get(0).getUrl());
		assertEquals("Dev", openApi.getServers().get(0).getDescription());
		assertEquals("https://uat.example.org", openApi.getServers().get(1).getUrl());
		assertEquals("https://demo.example.org", openApi.getServers().get(2).getUrl());
	}

	@Test
	void everyServerFallsBackToLocalhostWhenUnconfigured() {
		OpenAPI openApi = swaggerConfig.customOpenAPI(new MockEnvironment());

		openApi.getServers().forEach(server -> assertEquals("http://localhost:9090", server.getUrl()));
	}
}
