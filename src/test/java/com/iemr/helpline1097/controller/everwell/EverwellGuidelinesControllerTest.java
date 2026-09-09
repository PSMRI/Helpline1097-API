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
package com.iemr.helpline1097.controller.everwell;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.iemr.helpline1097.service.everwell.EverwellService;
import com.iemr.helpline1097.utils.exception.IEMRException;

@ExtendWith(MockitoExtension.class)
class EverwellGuidelinesControllerTest {

	private static final String REQUEST = "{\"providerServiceMapID\":1}";

	@Mock
	private EverwellService everwellService;

	@InjectMocks
	private EverwellGuidelinesController controller;

	@Test
	void saveEverwellGuidelinesReturnsServiceResult() throws Exception {
		when(everwellService.saveGuidelines(anyString())).thenReturn("{\"data\":\"saved\"}");

		String response = controller.saveEverwellGuidelines(REQUEST);

		assertTrue(response.contains("\"statusCode\":200"));
		verify(everwellService).saveGuidelines(REQUEST);
	}

	@Test
	void saveEverwellGuidelinesReturnsErrorOnFailure() throws Exception {
		when(everwellService.saveGuidelines(anyString())).thenThrow(new IEMRException("save failed"));

		assertTrue(controller.saveEverwellGuidelines(REQUEST).contains("save failed"));
	}

	@Test
	void fetchEverwellGuidelinesReturnsServiceResult() throws Exception {
		when(everwellService.fetchGuidelines(anyString())).thenReturn("{\"data\":[]}");

		assertTrue(controller.fetchEverwellGuidelines(REQUEST).contains("\"statusCode\":200"));
	}

	@Test
	void fetchEverwellGuidelinesReturnsErrorOnFailure() throws Exception {
		when(everwellService.fetchGuidelines(anyString())).thenThrow(new IEMRException("fetch failed"));

		assertTrue(controller.fetchEverwellGuidelines(REQUEST).contains("fetch failed"));
	}

	@Test
	void deleteEverwellGuidelinesReturnsServiceResult() throws Exception {
		when(everwellService.deleteGuideline(anyString())).thenReturn("Guideline deleted successfully");

		assertTrue(controller.deleteEverwellGuidelines(REQUEST).contains("\"statusCode\":200"));
	}

	@Test
	void deleteEverwellGuidelinesReturnsErrorOnFailure() throws Exception {
		when(everwellService.deleteGuideline(anyString())).thenThrow(new IEMRException("delete failed"));

		assertTrue(controller.deleteEverwellGuidelines(REQUEST).contains("delete failed"));
	}
}
