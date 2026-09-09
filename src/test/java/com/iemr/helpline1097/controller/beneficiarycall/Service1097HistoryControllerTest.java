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
package com.iemr.helpline1097.controller.beneficiarycall;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.iemr.helpline1097.data.co.beneficiarycall.BenCallServicesMappingHistory;
import com.iemr.helpline1097.data.co.beneficiarycall.BeneficiaryCall;
import com.iemr.helpline1097.service.co.beneficiarycall.BeneficiaryCallService;
import com.iemr.helpline1097.service.co.beneficiarycall.ServicesHistoryService;

@ExtendWith(MockitoExtension.class)
class Service1097HistoryControllerTest {

	private static final String REQUEST = "{\"beneficiaryRegID\":10,\"calledServiceID\":3}";
	private static final String REQUEST_NO_SERVICE = "{\"beneficiaryRegID\":10}";

	@Mock
	private ServicesHistoryService servicesHistoryService;

	@Mock
	private BeneficiaryCallService beneficiaryCallService;

	private Service1097HistoryController controller;

	@BeforeEach
	void setUp() {
		controller = new Service1097HistoryController();
		controller.setService1097HistoryService(servicesHistoryService);
		controller.setBeneficiaryCallService(beneficiaryCallService);
	}

	private static List<BenCallServicesMappingHistory> history() {
		return Collections.singletonList(new BenCallServicesMappingHistory());
	}

	@Test
	void getServiceHistoryReturnsSuccess() throws Exception {
		when(servicesHistoryService.getServiceHistory(anyString())).thenReturn(history());

		String response = controller.getServiceHistory(REQUEST);

		assertTrue(response.contains("\"statusCode\":200"));
	}

	@Test
	void getServiceHistoryReturnsErrorOnFailure() throws Exception {
		when(servicesHistoryService.getServiceHistory(anyString())).thenThrow(new RuntimeException("db down"));

		String response = controller.getServiceHistory(REQUEST);

		assertTrue(response.contains("db down"));
	}

	@Test
	void getBeneficiaryCallHistoryReturnsSuccess() throws Exception {
		when(servicesHistoryService.getServiceHistory(anyString())).thenReturn(history());

		assertTrue(controller.getBeneficiaryCallHistory(REQUEST).contains("\"statusCode\":200"));
	}

	@Test
	void getBeneficiaryCallHistoryReturnsErrorOnFailure() throws Exception {
		when(servicesHistoryService.getServiceHistory(anyString())).thenThrow(new RuntimeException("failed"));

		assertTrue(controller.getBeneficiaryCallHistory(REQUEST).contains("failed"));
	}

	@Test
	void setServiceHistoryPersistsRequest() {
		BenCallServicesMappingHistory saved = new BenCallServicesMappingHistory();
		when(servicesHistoryService.createServiceHistory(any())).thenReturn(saved);

		String response = controller.setServiceHistory("{\"beneficiaryRegID\":10}");

		assertTrue(response.contains("\"statusCode\":200"));
		verify(servicesHistoryService).createServiceHistory(any());
	}

	@Test
	void setServiceHistoryReturnsErrorOnMalformedJson() {
		assertNotNull(controller.setServiceHistory("not-json"));
	}

	@Test
	void getCallSummaryDelegatesToBeneficiaryCallService() {
		when(beneficiaryCallService.getCallSummaryByCallID(any()))
				.thenReturn(Collections.singletonList(new BeneficiaryCall()));

		String response = controller.getCallSummary("{\"benCallID\":5}");

		assertTrue(response.contains("\"statusCode\":200"));
		verify(beneficiaryCallService).getCallSummaryByCallID(5L);
	}

	@Test
	void getCallSummaryReturnsErrorOnMalformedJson() {
		assertTrue(controller.getCallSummary("{oops}").contains("statusCode"));
	}

	@Test
	void getBeneficiaryCallsHistoryUsesCalledServiceIdWhenPresent() {
		when(beneficiaryCallService.getBeneficiaryCallsHistory(anyLong(), anyInt(), anyInt(), anyInt()))
				.thenReturn(Collections.singletonList(new BeneficiaryCall()));

		String response = controller.getBeneficiaryCallsHistory(REQUEST);

		assertTrue(response.contains("\"statusCode\":200"));
		verify(beneficiaryCallService).getBeneficiaryCallsHistory(10L, 3, 0, 1000);
	}

	@Test
	void getBeneficiaryCallsHistoryRejectsPagingFieldsNotOnBeneficiaryCall() {
		String response = controller
				.getBeneficiaryCallsHistory("{\"beneficiaryRegID\":10,\"pageNo\":2,\"rowsPerPage\":25}");

		assertTrue(response.contains("pageNo"));
	}

	@Test
	void getBeneficiaryCallsHistoryFallsBackToDefaultPagingWithoutCalledService() {
		when(beneficiaryCallService.getBeneficiaryCallsHistory(anyLong(), anyInt(), anyInt()))
				.thenReturn(Collections.singletonList(new BeneficiaryCall()));

		String response = controller.getBeneficiaryCallsHistory(REQUEST_NO_SERVICE);

		assertTrue(response.contains("\"statusCode\":200"));
		verify(beneficiaryCallService).getBeneficiaryCallsHistory(10L, 0, 1000);
	}

	@Test
	void getBeneficiaryCallsHistoryReturnsErrorOnMalformedJson() {
		assertTrue(controller.getBeneficiaryCallsHistory("{oops}").contains("statusCode"));
	}

	@Test
	void getReferralsHistoryReturnsSuccess() throws Exception {
		when(servicesHistoryService.getReferralsHistory(anyString())).thenReturn(history());

		assertTrue(controller.getReferralsHistory(REQUEST).contains("\"statusCode\":200"));
	}

	@Test
	void getReferralsHistoryReturnsErrorOnFailure() throws Exception {
		when(servicesHistoryService.getReferralsHistory(anyString())).thenThrow(new RuntimeException("no referrals"));

		assertTrue(controller.getReferralsHistory(REQUEST).contains("no referrals"));
	}

	@Test
	void getFeedbacksHistoryReturnsSuccess() throws Exception {
		when(servicesHistoryService.getFeedbacksHistory(anyString())).thenReturn(history());

		assertTrue(controller.getFeedbacksHistory(REQUEST).contains("\"statusCode\":200"));
	}

	@Test
	void getFeedbacksHistoryReturnsErrorOnFailure() throws Exception {
		when(servicesHistoryService.getFeedbacksHistory(anyString())).thenThrow(new RuntimeException("no feedback"));

		assertTrue(controller.getFeedbacksHistory(REQUEST).contains("no feedback"));
	}

	@Test
	void getInformationsHistoryReturnsSuccess() throws Exception {
		when(servicesHistoryService.getInformationsHistory(anyString())).thenReturn(history());

		assertTrue(controller.getInformationsHistory(REQUEST).contains("\"statusCode\":200"));
	}

	@Test
	void getInformationsHistoryReturnsErrorOnFailure() throws Exception {
		when(servicesHistoryService.getInformationsHistory(anyString())).thenThrow(new RuntimeException("no info"));

		assertTrue(controller.getInformationsHistory(REQUEST).contains("no info"));
	}

	@Test
	void getCounsellingsHistoryReturnsSuccess() throws Exception {
		when(servicesHistoryService.getCounsellingsHistory(anyString())).thenReturn(history());

		assertTrue(controller.getCounsellingsHistory(REQUEST).contains("\"statusCode\":200"));
	}

	@Test
	void getCounsellingsHistoryReturnsErrorOnFailure() throws Exception {
		when(servicesHistoryService.getCounsellingsHistory(anyString()))
				.thenThrow(new RuntimeException("no counselling"));

		assertTrue(controller.getCounsellingsHistory(REQUEST).contains("no counselling"));
	}

	@Test
	void getCaseSheetDelegatesToServiceHistory() {
		when(servicesHistoryService.getCallSummaryV1(anyLong())).thenReturn(history());

		String response = controller.getCaseSheet("{\"benCallID\":7}");

		assertTrue(response.contains("\"statusCode\":200"));
		verify(servicesHistoryService).getCallSummaryV1(7L);
	}

	@Test
	void getCaseSheetReturnsErrorOnMalformedJson() {
		assertTrue(controller.getCaseSheet("{oops}").contains("statusCode"));
	}

	@Test
	void getReferralsHistoryReportsAJsonFailureAsAnObjectConversionError() throws Exception {
		when(servicesHistoryService.getReferralsHistory(anyString())).thenThrow(new JSONException("bad json"));

		assertTrue(controller.getReferralsHistory(REQUEST).contains("Invalid object conversion"));
	}

	@Test
	void getFeedbacksHistoryReportsAJsonFailureAsAnObjectConversionError() throws Exception {
		when(servicesHistoryService.getFeedbacksHistory(anyString())).thenThrow(new JSONException("bad json"));

		assertTrue(controller.getFeedbacksHistory(REQUEST).contains("Invalid object conversion"));
	}

	@Test
	void getInformationsHistoryReportsAJsonFailureAsAnObjectConversionError() throws Exception {
		when(servicesHistoryService.getInformationsHistory(anyString())).thenThrow(new JSONException("bad json"));

		assertTrue(controller.getInformationsHistory(REQUEST).contains("Invalid object conversion"));
	}

	@Test
	void getCounsellingsHistoryReportsAJsonFailureAsAnObjectConversionError() throws Exception {
		when(servicesHistoryService.getCounsellingsHistory(anyString())).thenThrow(new JSONException("bad json"));

		assertTrue(controller.getCounsellingsHistory(REQUEST).contains("Invalid object conversion"));
	}

	@Test
	void getBeneficiaryCallsHistoryReportsAJsonFailureAsAnObjectConversionError() {
		assertTrue(controller.getBeneficiaryCallsHistory("not-json").contains("Invalid object conversion"));
	}
}
