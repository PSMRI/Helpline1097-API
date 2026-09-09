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
package com.iemr.helpline1097.service.co.services;

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

import com.iemr.helpline1097.data.co.services.DesignationDetails;
import com.iemr.helpline1097.repository.co.services.DesignationRepository;

@ExtendWith(MockitoExtension.class)
class DesignationServiceImplTest {

	@Mock
	private DesignationRepository designationRepository;

	private DesignationServiceImpl service;

	@BeforeEach
	void setUp() {
		service = new DesignationServiceImpl();
		service.setDesignationRepository(designationRepository);
	}

	@Test
	void getDesignationsMapsActiveDesignations() {
		Set<Object[]> rows = new LinkedHashSet<>();
		rows.add(new Object[] { 1, "Doctor" });
		when(designationRepository.findAciveDesignations()).thenReturn(rows);

		List<DesignationDetails> designations = service.getDesignations();

		assertEquals(1, designations.size());
		assertEquals("Doctor", designations.get(0).getDesignationName());
	}

	@Test
	void getDesignationsSkipsShortRows() {
		Set<Object[]> rows = new LinkedHashSet<>();
		rows.add(new Object[] { 1 });
		when(designationRepository.findAciveDesignations()).thenReturn(rows);

		assertTrue(service.getDesignations().isEmpty());
	}

	@Test
	void getDesignationsReturnsEmptyListWhenNoRows() {
		when(designationRepository.findAciveDesignations()).thenReturn(Collections.emptySet());

		assertTrue(service.getDesignations().isEmpty());
	}

	@Test
	void nullRowsAreSkipped() {
		Set<Object[]> rows = new LinkedHashSet<>();
		rows.add(null);
		when(designationRepository.findAciveDesignations()).thenReturn(rows);

		assertTrue(service.getDesignations().isEmpty());
	}
}
