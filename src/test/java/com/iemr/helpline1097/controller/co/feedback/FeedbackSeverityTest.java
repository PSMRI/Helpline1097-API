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
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.iemr.helpline1097.data.co.feedback.FeedbackType;
import com.iemr.helpline1097.service.co.feedback.FeedbackSeverityService;
import com.iemr.helpline1097.service.co.feedback.FeedbackTypeService;

@ExtendWith(MockitoExtension.class)
class FeedbackSeverityTest {

	@Mock
	private FeedbackSeverityService feedbackSeverityService;

	@Mock
	private FeedbackTypeService feedbackTypeService;

	@InjectMocks
	private FeedbackSeverity controller;

	@Test
	void getSeverityReturnsActiveSeverities() {
		when(feedbackSeverityService.getActiveFeedbackSeverity()).thenReturn(Collections.singletonList(
				new com.iemr.helpline1097.data.co.feedback.FeedbackSeverity(1, "High")));

		assertTrue(controller.getSeverity().contains("\"statusCode\":200"));
	}

	@Test
	void getSeverityReturnsErrorOnFailure() {
		when(feedbackSeverityService.getActiveFeedbackSeverity())
				.thenThrow(new RuntimeException("severity unavailable"));

		assertTrue(controller.getSeverity().contains("severity unavailable"));
	}

	@Test
	void getFeedbackTypeReturnsActiveTypes() {
		when(feedbackTypeService.getActiveFeedbackTypes())
				.thenReturn(Collections.singletonList(new FeedbackType(1, "Complaint")));

		assertTrue(controller.getFeedbackType().contains("\"statusCode\":200"));
	}

	@Test
	void getFeedbackTypeReturnsErrorOnFailure() {
		when(feedbackTypeService.getActiveFeedbackTypes()).thenThrow(new RuntimeException("types unavailable"));

		assertTrue(controller.getFeedbackType().contains("types unavailable"));
	}

	@Test
	void setFeedbackSeverityServiceReplacesInjectedService() {
		FeedbackSeverity plain = new FeedbackSeverity();
		plain.SetFeedbackSeverityService(feedbackSeverityService);
		when(feedbackSeverityService.getActiveFeedbackSeverity()).thenReturn(Collections.emptyList());

		assertTrue(plain.getSeverity().contains("\"statusCode\":200"));
	}
}
