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
package com.iemr.helpline1097.controller.health;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.iemr.helpline1097.service.health.HealthService;

@ExtendWith(MockitoExtension.class)
class HealthControllerTest {

	@Mock
	private HealthService healthService;

	@Test
	void checkHealthReturnsOkWhenUp() {
		when(healthService.checkHealth()).thenReturn(Map.of("status", "UP"));

		ResponseEntity<Map<String, Object>> response = new HealthController(healthService).checkHealth();

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("UP", response.getBody().get("status"));
	}

	@Test
	void checkHealthReturnsOkWhenDegraded() {
		when(healthService.checkHealth()).thenReturn(Map.of("status", "DEGRADED"));

		ResponseEntity<Map<String, Object>> response = new HealthController(healthService).checkHealth();

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("DEGRADED", response.getBody().get("status"));
	}

	@Test
	void checkHealthReturnsServiceUnavailableWhenDown() {
		when(healthService.checkHealth()).thenReturn(Map.of("status", "DOWN"));

		ResponseEntity<Map<String, Object>> response = new HealthController(healthService).checkHealth();

		assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
	}

	@Test
	void checkHealthReturnsServiceUnavailableOnUnexpectedFailure() {
		when(healthService.checkHealth()).thenThrow(new IllegalStateException("boom"));

		ResponseEntity<Map<String, Object>> response = new HealthController(healthService).checkHealth();

		assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
		assertEquals("DOWN", response.getBody().get("status"));
		assertNotNull(response.getBody().get("timestamp"));
	}
}
