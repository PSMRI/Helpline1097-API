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
package com.iemr.helpline1097.controller.co.callhandling;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.iemr.helpline1097.data.co.calltype.M_Calltype;
import com.iemr.helpline1097.service.co.callhandling.IEMRCalltypeServiceImpl;

@ExtendWith(MockitoExtension.class)
class CalltypeControllerTest {

	@Mock
	private IEMRCalltypeServiceImpl calltypeService;

	@InjectMocks
	private CalltypeController controller;

	@Test
	void addCallTypeReportsSuccessWhenSaved() {
		when(calltypeService.addCalltype(any(M_Calltype.class)))
				.thenReturn(new M_Calltype("inbound", "ok", "valid"));

		String response = controller.addCallType("{\"callType\":\"inbound\"}");

		assertTrue(response.contains("callType Added"));
	}

	@Test
	void addCallTypeReportsFailureWhenServiceReturnsNull() {
		when(calltypeService.addCalltype(any(M_Calltype.class))).thenReturn(null);

		assertTrue(controller.addCallType("{\"callType\":\"inbound\"}").contains("Failed to add callType"));
	}

	@Test
	void addCallTypeReturnsErrorOnMalformedJson() {
		assertTrue(controller.addCallType("{oops}").contains("statusCode"));
	}

	@Test
	void getAllCallTypesReturnsCallType() {
		when(calltypeService.getAllCalltypes(anyInt())).thenReturn(new M_Calltype("inbound", "ok", "valid"));

		assertTrue(controller.getAllCallTypes(1).contains("\"statusCode\":200"));
	}

	@Test
	void getAllCallTypesReturnsErrorOnFailure() {
		when(calltypeService.getAllCalltypes(anyInt())).thenThrow(new RuntimeException("not found"));

		assertTrue(controller.getAllCallTypes(1).contains("not found"));
	}
}
