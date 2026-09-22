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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.iemr.helpline1097.data.co.feedbackRequest.FeedbackRequest;
import com.iemr.helpline1097.service.co.feedback.FeedbackRequestServiceImpl;

@ExtendWith(MockitoExtension.class)
class FeedbackRequestControllerTest {

	@Mock
	private FeedbackRequestServiceImpl feedbackRequestService;

	@InjectMocks
	private FeedbackRequestController controller;

	@Test
	void feedbackCreatePersistsRequest() {
		when(feedbackRequestService.createFeedbackRequest(any(FeedbackRequest.class)))
				.thenReturn(new FeedbackRequest());

		String response = controller.feedbackCreate("{\"supUserID\":3}");

		assertTrue(response.contains("\"statusCode\":200"));
		verify(feedbackRequestService).createFeedbackRequest(any(FeedbackRequest.class));
	}

	@Test
	void feedbackCreateReturnsErrorOnMalformedJson() {
		assertTrue(controller.feedbackCreate("{oops}").contains("statusCode"));
	}

	@Test
	void getFeedbackRequestsReturnsStoredRequest() {
		when(feedbackRequestService.getFeedbackReuest(anyInt())).thenReturn(new FeedbackRequest());

		assertTrue(controller.getFeedbackRequests(11).contains("\"statusCode\":200"));
		verify(feedbackRequestService).getFeedbackReuest(11);
	}

	@Test
	void getFeedbackRequestsReturnsErrorOnFailure() {
		when(feedbackRequestService.getFeedbackReuest(anyInt())).thenThrow(new RuntimeException("request missing"));

		assertTrue(controller.getFeedbackRequests(11).contains("request missing"));
	}
}
