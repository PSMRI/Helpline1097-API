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

import com.iemr.helpline1097.data.co.feedbackRequest.FeedbackRequest;
import com.iemr.helpline1097.repository.co.feedback.FeedbackRequestRepository;

@ExtendWith(MockitoExtension.class)
class FeedbackRequestServiceImplTest {

	@Mock
	private FeedbackRequestRepository feedbackRequestRepository;

	@InjectMocks
	private FeedbackRequestServiceImpl service;

	@Test
	void getFeedbackReuestReturnsStoredRequest() {
		FeedbackRequest stored = new FeedbackRequest();
		when(feedbackRequestRepository.findById(anyLong())).thenReturn(Optional.of(stored));

		assertSame(stored, service.getFeedbackReuest(11));
		verify(feedbackRequestRepository).findById(11L);
	}

	@Test
	void getFeedbackReuestFailsWhenRequestIsMissing() {
		when(feedbackRequestRepository.findById(anyLong())).thenReturn(Optional.empty());

		assertThrows(NoSuchElementException.class, () -> service.getFeedbackReuest(11));
	}

	@Test
	void createFeedbackRequestDelegatesToRepository() {
		FeedbackRequest payload = new FeedbackRequest();
		when(feedbackRequestRepository.save(payload)).thenReturn(payload);

		assertSame(payload, service.createFeedbackRequest(payload));
	}
}
