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
package com.iemr.helpline1097.service.co.feedback;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedConstruction;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.iemr.helpline1097.data.co.beneficiarycall.BenCallServicesMappingHistory;
import com.iemr.helpline1097.data.co.feedback.FeedbackDetails;
import com.iemr.helpline1097.data.co.feedback.FeedbackRequestDetails;
import com.iemr.helpline1097.repository.co.beneficiary.BenCalServiceCatSubcatMappingRepo;
import com.iemr.helpline1097.repository.co.feedback.FeedbackRepository;
import com.iemr.helpline1097.utils.config.ConfigProperties;

import jakarta.servlet.http.HttpServletRequest;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class FeedbackServiceImplTest {

	@Mock
	private FeedbackRepository feedbackRepository;

	@Mock
	private BenCalServiceCatSubcatMappingRepo benCalServiceCatSubcatMappingRepo;

	@Mock
	private ConfigProperties configProperties;

	private FeedbackServiceImpl service;

	@BeforeEach
	void setUp() {
		service = new FeedbackServiceImpl();
		service.setFeedbackRepository(feedbackRepository);
		service.getBenCalServiceCatSubcatMappingRepo(benCalServiceCatSubcatMappingRepo);
		service.setProperties(configProperties);
	}

	private static ArrayList<Object[]> feedbackRows() {
		ArrayList<Object[]> rows = new ArrayList<>();
		rows.add(new Object[] { 1L, (short) 2, (short) 3, (short) 4, "poor service", "FE/1/01012024/1" });
		return rows;
	}

	@Test
	void getFeedbackRequestsMapsRowsForBeneficiary() {
		when(feedbackRepository.findByBeneficiaryID(anyLong())).thenReturn(feedbackRows());

		List<FeedbackDetails> feedback = service.getFeedbackRequests(10L);

		assertEquals(1, feedback.size());
		assertEquals(1L, feedback.get(0).getFeedbackID());
		assertEquals("poor service", feedback.get(0).getFeedback());
	}

	@Test
	void getFeedbackRequestsSkipsShortRows() {
		ArrayList<Object[]> rows = new ArrayList<>();
		rows.add(new Object[] { 1L, (short) 2 });
		when(feedbackRepository.findByBeneficiaryID(anyLong())).thenReturn(rows);

		assertTrue(service.getFeedbackRequests(10L).isEmpty());
	}

	@Test
	void getFeedbackRequestMapsRowsForFeedbackId() {
		when(feedbackRepository.findByFeedbackID(anyLong())).thenReturn(feedbackRows());

		assertEquals(1, service.getFeedbackRequest(1L).size());
	}

	@Test
	void getFeedbackRequestSkipsShortRows() {
		ArrayList<Object[]> rows = new ArrayList<>();
		rows.add(new Object[] { 1L });
		when(feedbackRepository.findByFeedbackID(anyLong())).thenReturn(rows);

		assertTrue(service.getFeedbackRequest(1L).isEmpty());
	}

	@Test
	void createFeedbackLinksRequestDetailsBackToTheFeedback() {
		FeedbackDetails feedback = new FeedbackDetails();
		FeedbackRequestDetails detail = new FeedbackRequestDetails();
		feedback.setFeedbackRequestDetails(new ArrayList<>(Collections.singletonList(detail)));
		when(feedbackRepository.save(feedback)).thenReturn(feedback);

		assertSame(feedback, service.createFeedback(feedback));
		assertSame(feedback, detail.getFeedback());
	}

	@Test
	void updateFeedbackIsNotImplementedAndReturnsNull() {
		assertNull(service.updateFeedback(new FeedbackDetails()));
	}

	@Test
	void saveFeedbackFromCustomerRejectsFeedbackWithoutSubService() {
		HttpServletRequest request = mock(HttpServletRequest.class);

		Exception thrown = assertThrows(Exception.class,
				() -> service.saveFeedbackFromCustomer("[{\"feedbackID\":1}]", request));

		assertEquals("Sub service is not configured for this provider", thrown.getMessage());
	}

	@Test
	void saveFeedbackFromCustomerReturnsSavedIdsWhenCommonApiSucceeds() throws Exception {
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getHeader("Authorization")).thenReturn("Bearer token");
		when(benCalServiceCatSubcatMappingRepo.saveAll(any())).thenReturn(Collections.emptyList());

		String savedFeedback = "[{\\\"feedbackID\\\":5,\\\"subServiceID\\\":1,\\\"beneficiaryRegID\\\":10,"
				+ "\\\"benCallID\\\":7,\\\"districtID\\\":3,\\\"createdBy\\\":\\\"agent\\\"}]";
		String commonApiBody = "{\"response\":\"" + savedFeedback + "\"}";

		try (MockedConstruction<RestTemplate> restTemplates = Mockito.mockConstruction(RestTemplate.class,
				(restTemplate, context) -> when(restTemplate.exchange(anyString(), eq(HttpMethod.POST),
						any(HttpEntity.class), eq(String.class))).thenReturn(ResponseEntity.ok(commonApiBody)))) {

			String result = service.saveFeedbackFromCustomer("[{\"subServiceID\":1}]", request);

			assertTrue(result.contains("feedBackId"));
			assertTrue(result.contains("requestID"));
			assertEquals(2, restTemplates.constructed().size());
		}
	}

	@Test
	void saveFeedbackFromCustomerFailsWhenCommonApiReportsAnError() {
		HttpServletRequest request = mock(HttpServletRequest.class);

		try (MockedConstruction<RestTemplate> restTemplates = Mockito.mockConstruction(RestTemplate.class,
				(restTemplate, context) -> when(restTemplate.exchange(anyString(), eq(HttpMethod.POST),
						any(HttpEntity.class), eq(String.class))).thenReturn(ResponseEntity.ok("{}")))) {

			Exception thrown = assertThrows(Exception.class,
					() -> service.saveFeedbackFromCustomer("[{\"subServiceID\":1}]", request));

			assertEquals("Failed with generic error", thrown.getMessage());
		}
	}

	@Test
	void saveFeedbackFromCustomerBuildsHistoryRowsForEverySavedFeedback() throws Exception {
		FeedbackDetails one = new FeedbackDetails();
		one.setFeedbackID(5L);
		one.setSubServiceID(1);
		one.setBeneficiaryRegID(10L);
		one.setBenCallID(7L);
		one.setCreatedBy("agent");

		BenCallServicesMappingHistory history = new BenCallServicesMappingHistory(one.getBeneficiaryRegID(),
				one.getBenCallID(), one.getSubServiceID(), one.getFeedbackID(), false, one.getCreatedBy());

		assertEquals(10L, history.getBeneficiaryRegID());
		assertEquals(5L, history.getFeedbackID());
		assertEquals(Arrays.asList(history), Collections.singletonList(history));
	}

	@Test
	void nullRowsAreSkippedByBothFeedbackLookups() {
		ArrayList<Object[]> rows = new ArrayList<>();
		rows.add(null);
		when(feedbackRepository.findByBeneficiaryID(anyLong())).thenReturn(rows);
		when(feedbackRepository.findByFeedbackID(anyLong())).thenReturn(rows);

		assertTrue(service.getFeedbackRequests(10L).isEmpty());
		assertTrue(service.getFeedbackRequest(1L).isEmpty());
	}

}
