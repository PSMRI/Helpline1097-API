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
package com.iemr.helpline1097.service.co.callhandling;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.iemr.helpline1097.data.co.calltype.M_Calltype;
import com.iemr.helpline1097.repository.co.calltype.IEMRCalltypeRepositoryImplCustom;

@ExtendWith(MockitoExtension.class)
class IEMRCalltypeServiceImplTest {

	@Mock
	private IEMRCalltypeRepositoryImplCustom calltypeRepository;

	@InjectMocks
	private IEMRCalltypeServiceImpl service;

	@Test
	void addCalltypeFromFieldsBuildsAndSavesCalltype() {
		M_Calltype saved = new M_Calltype("inbound", "ok", "valid");
		when(calltypeRepository.save(any(M_Calltype.class))).thenReturn(saved);

		assertSame(saved, service.addCalltype("inbound", "ok", "valid"));

		ArgumentCaptor<M_Calltype> captor = ArgumentCaptor.forClass(M_Calltype.class);
		verify(calltypeRepository, org.mockito.Mockito.atLeastOnce()).save(captor.capture());
		assertEquals("inbound", captor.getValue().getCallType());
		assertEquals("ok", captor.getValue().getRemarks());
		assertEquals("valid", captor.getValue().getInvalidType());
	}

	@Test
	void addCalltypeFromEntityDelegatesToRepository() {
		M_Calltype payload = new M_Calltype("outbound", "note", "valid");
		when(calltypeRepository.save(payload)).thenReturn(payload);

		assertSame(payload, service.addCalltype(payload));
	}

	@Test
	void getAllCalltypesReturnsStoredCalltype() {
		M_Calltype stored = new M_Calltype("inbound", "ok", "valid");
		when(calltypeRepository.findById(anyLong())).thenReturn(Optional.of(stored));

		assertSame(stored, service.getAllCalltypes(3));
		verify(calltypeRepository).findById(3L);
	}

	@Test
	void updateCalltypeReportsSuccess() {
		M_Calltype payload = new M_Calltype("inbound", "ok", "valid");
		payload.setId(7L);

		assertEquals("success", service.updateCalltype(payload));
		verify(calltypeRepository).updateCallType(anyLong(), anyString(), anyString());
	}
}
