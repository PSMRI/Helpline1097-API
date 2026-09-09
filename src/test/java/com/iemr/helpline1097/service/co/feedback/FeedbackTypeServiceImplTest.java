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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.iemr.helpline1097.data.co.feedback.FeedbackType;
import com.iemr.helpline1097.repository.co.feedback.FeedbackTypeRepository;

@ExtendWith(MockitoExtension.class)
class FeedbackTypeServiceImplTest {

	@Mock
	private FeedbackTypeRepository feedbackTypeRepository;

	private FeedbackTypeServiceImpl service;

	@BeforeEach
	void setUp() {
		service = new FeedbackTypeServiceImpl();
		service.setFeedbackTypeRepository(feedbackTypeRepository);
	}

	@Test
	void getActiveFeedbackTypesMapsRows() {
		Set<Object[]> rows = new LinkedHashSet<>();
		rows.add(new Object[] { 1, "Complaint" });
		when(feedbackTypeRepository.findActiveFeedbackTypes()).thenReturn(rows);

		List<FeedbackType> types = service.getActiveFeedbackTypes();

		assertEquals(1, types.size());
		assertEquals("Complaint", types.get(0).getFeedbackTypeName());
	}

	@Test
	void getActiveFeedbackTypesSkipsShortRows() {
		Set<Object[]> rows = new LinkedHashSet<>();
		rows.add(new Object[] { 1 });
		when(feedbackTypeRepository.findActiveFeedbackTypes()).thenReturn(rows);

		assertTrue(service.getActiveFeedbackTypes().isEmpty());
	}

	@Test
	void getActiveFeedbackTypesReturnsEmptyListWhenNoRows() {
		when(feedbackTypeRepository.findActiveFeedbackTypes()).thenReturn(Collections.emptySet());

		assertTrue(service.getActiveFeedbackTypes().isEmpty());
	}

	@Test
	void nullRowsAreSkipped() {
		Set<Object[]> rows = new LinkedHashSet<>();
		rows.add(null);
		when(feedbackTypeRepository.findActiveFeedbackTypes()).thenReturn(rows);

		assertTrue(service.getActiveFeedbackTypes().isEmpty());
	}
}
