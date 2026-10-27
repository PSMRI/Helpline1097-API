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
package com.iemr.helpline1097.controller.co.feedback;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.iemr.helpline1097.data.co.feedback.FeedbackDetails;
import com.iemr.helpline1097.service.co.feedback.FeedbackService;
import com.iemr.helpline1097.service.co.feedback.FeedbackServiceImpl;

import jakarta.servlet.http.HttpServletRequest;

@ExtendWith(MockitoExtension.class)
class FeedbackControllerTest {

	@Mock
	private FeedbackService feedbackService;

	@Mock
	private FeedbackServiceImpl feedbackServiceImpl;

	private FeedbackController controller;

	@BeforeEach
	void setUp() {
		controller = new FeedbackController();
		controller.setFeedbackService(feedbackService);
		controller.setFeedbackServiceImpl(feedbackServiceImpl);
	}

	@Test
	void feedbackReuestLooksUpByBeneficiaryRegId() {
		when(feedbackService.getFeedbackRequests(anyLong()))
				.thenReturn(Collections.singletonList(new FeedbackDetails()));

		String response = controller.feedbackReuest("{\"beneficiaryRegID\":42}");

		assertTrue(response.contains("\"statusCode\":200"));
		verify(feedbackService).getFeedbackRequests(42L);
	}

	@Test
	void feedbackReuestReturnsErrorOnMalformedJson() {
		assertTrue(controller.feedbackReuest("{oops}").contains("statusCode"));
	}

	@Test
	void getFeedbackByPostLooksUpByFeedbackId() {
		when(feedbackService.getFeedbackRequests(anyLong()))
				.thenReturn(Collections.singletonList(new FeedbackDetails()));

		assertTrue(controller.getFeedbackByPost(7).contains("\"statusCode\":200"));
		verify(feedbackService).getFeedbackRequests(7L);
	}

	@Test
	void getFeedbackByPostReturnsErrorOnFailure() {
		when(feedbackService.getFeedbackRequests(anyLong())).thenThrow(new RuntimeException("feedback missing"));

		assertTrue(controller.getFeedbackByPost(7).contains("feedback missing"));
	}

	@Test
	void saveBenFeedbackReturnsSavedPayload() throws Exception {
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getRequestURI()).thenReturn("/co/saveBenFeedback");
		when(feedbackServiceImpl.saveFeedbackFromCustomer(anyString(), any()))
				.thenReturn("{\"feedBackId\":\"1\"}");

		String response = controller.saveBenFeedback("[{\"subServiceID\":1}]", request);

		assertTrue(response.contains("\"statusCode\":200"));
	}

	@Test
	void saveBenFeedbackAcceptsNullServiceResult() throws Exception {
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getRequestURI()).thenReturn("/co/saveBenFeedback");
		when(feedbackServiceImpl.saveFeedbackFromCustomer(anyString(), any())).thenReturn(null);

		assertTrue(controller.saveBenFeedback("[]", request).contains("statusCode"));
	}

	@Test
	void saveBenFeedbackReturnsErrorOnFailure() throws Exception {
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getRequestURI()).thenReturn("/co/saveBenFeedback");
		when(feedbackServiceImpl.saveFeedbackFromCustomer(anyString(), any()))
				.thenThrow(new RuntimeException("save failed"));

		assertTrue(controller.saveBenFeedback("[]", request).contains("save failed"));
	}
}
