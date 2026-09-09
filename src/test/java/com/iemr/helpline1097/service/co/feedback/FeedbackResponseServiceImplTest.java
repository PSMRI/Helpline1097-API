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

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.iemr.helpline1097.data.co.feedbackResponse.FeedbackResponse;
import com.iemr.helpline1097.repository.co.feedback.FeedbackResponseRepository;

@ExtendWith(MockitoExtension.class)
class FeedbackResponseServiceImplTest {

	@Mock
	private FeedbackResponseRepository feedbackResponseRepository;

	@InjectMocks
	private FeedbackResponseServiceImpl service;

	@Test
	void getFeedbackResponseReturnsStoredResponse() {
		FeedbackResponse stored = new FeedbackResponse();
		when(feedbackResponseRepository.findById(anyLong())).thenReturn(Optional.of(stored));

		assertSame(stored, service.getFeedbackResponse(6));
		verify(feedbackResponseRepository).findById(6L);
	}

	@Test
	void getFeedbackResponseFailsWhenResponseIsMissing() {
		when(feedbackResponseRepository.findById(anyLong())).thenReturn(Optional.empty());

		assertThrows(NoSuchElementException.class, () -> service.getFeedbackResponse(6));
	}

	@Test
	void createFeedbackResponseDelegatesToRepository() {
		FeedbackResponse payload = new FeedbackResponse();
		when(feedbackResponseRepository.save(payload)).thenReturn(payload);

		assertSame(payload, service.createFeedbackResponse(payload));
	}
}
