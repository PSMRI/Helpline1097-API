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

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.iemr.helpline1097.data.co.services.InstitutionDetails;
import com.iemr.helpline1097.repository.co.services.InstitutionRepository;

@ExtendWith(MockitoExtension.class)
class InstitutionServiceImplTest {

	@Mock
	private InstitutionRepository institutionRepository;

	@InjectMocks
	private InstitutionServiceImpl service;

	@Test
	void getInstitutionsReturnsEverythingTheRepositoryHolds() {
		Iterable<InstitutionDetails> stored = Collections.singletonList(new InstitutionDetails());
		when(institutionRepository.findAll()).thenReturn(stored);

		assertSame(stored, service.getInstitutions());
	}
}
