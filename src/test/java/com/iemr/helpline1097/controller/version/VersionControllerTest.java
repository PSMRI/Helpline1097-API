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
package com.iemr.helpline1097.controller.version;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class VersionControllerTest {

	@Test
	void versionInformationReturnsAllKeys() {
		ResponseEntity<Map<String, String>> response = new VersionController().versionInformation();

		assertEquals(HttpStatus.OK, response.getStatusCode());
		Map<String, String> body = response.getBody();
		assertNotNull(body);
		assertTrue(body.containsKey("buildTimestamp"));
		assertTrue(body.containsKey("version"));
		assertTrue(body.containsKey("branch"));
		assertTrue(body.containsKey("commitHash"));
	}

	@Test
	void versionInformationNeverReturnsNullValues() {
		ResponseEntity<Map<String, String>> response = new VersionController().versionInformation();

		response.getBody().values().forEach(value -> assertNotNull(value));
	}
}
