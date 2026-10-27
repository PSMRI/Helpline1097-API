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
package com.iemr.helpline1097.service.co.beneficiarycall;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import com.iemr.helpline1097.data.co.beneficiarycall.BeneficiaryCall;
import com.iemr.helpline1097.repository.co.beneficiarycall.BeneficiaryCallRepository;
import com.iemr.helpline1097.utils.mapper.OutputMapper;

@ExtendWith(MockitoExtension.class)
class BeneficiaryCallServiceImplTest {

	@Mock
	private BeneficiaryCallRepository beneficiaryCallRepository;

	@InjectMocks
	private BeneficiaryCallServiceImpl service;

	@BeforeEach
	void primeOutputMapper() {
		// OutputMapper.gson() reads a static builder that only the constructor populates.
		new OutputMapper();
	}

	private static ArrayList<Object[]> summaryRows() {
		ArrayList<Object[]> rows = new ArrayList<>();
		rows.add(new Object[] { 1L, new Timestamp(0L), "remark", 2L, 3L, 4L, new Timestamp(1L), 5L, null });
		return rows;
	}

	private static ArrayList<Object[]> callRows(int count) {
		ArrayList<Object[]> rows = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			rows.add(new Object[] { (long) i, new ArrayList<>(), 2, Boolean.TRUE, new Timestamp(0L), "remark",
					"closure", 3 });
		}
		return rows;
	}

	@Test
	void addCalltypeMapsQueryRowsIntoCallsOnceResultSetIsLargeEnough() {
		when(beneficiaryCallRepository.findCallsByBenefeciaryID(anyLong(), any(Pageable.class)))
				.thenReturn(callRows(8));

		List<BeneficiaryCall> calls = service.addCalltype(7L);

		assertEquals(8, calls.size());
		assertEquals(0L, calls.get(0).getBenCallID());
		assertEquals("remark", calls.get(0).getRemarks());
	}

	@Test
	void addCalltypeSkipsRowsWhenResultSetHasFewerThanEightRows() {
		when(beneficiaryCallRepository.findCallsByBenefeciaryID(anyLong(), any(Pageable.class)))
				.thenReturn(callRows(7));

		assertTrue(service.addCalltype(7L).isEmpty());
	}

	@Test
	void addCalltypeReturnsEmptyListWhenNoRows() {
		when(beneficiaryCallRepository.findCallsByBenefeciaryID(anyLong(), any(Pageable.class)))
				.thenReturn(new ArrayList<>());

		assertTrue(service.addCalltype(7L).isEmpty());
	}

	@Test
	void createCallSavesNewSystemCall() {
		BeneficiaryCall saved = new BeneficiaryCall();
		when(beneficiaryCallRepository.save(any(BeneficiaryCall.class))).thenReturn(saved);

		assertSame(saved, service.createCall(11L, true));
		verify(beneficiaryCallRepository).save(any(BeneficiaryCall.class));
	}

	@Test
	void closeCallReturnsUpdateCountFromRepository() {
		when(beneficiaryCallRepository.closeCall(anyLong(), anyString(), any(Timestamp.class), anyString(), anyInt()))
				.thenReturn(1);

		Integer updated = service.closeCall(
				"{\"benCallID\":5,\"remarks\":\"done\",\"callClosureType\":\"resolved\",\"dispositionStatusID\":2}");

		assertEquals(1, updated);
	}

	@Test
	void getBeneficiaryCallsHistoryMapsRows() {
		when(beneficiaryCallRepository.findCallsHistoryByBenefeciaryID(anyLong(), any(Pageable.class)))
				.thenReturn(summaryRows());

		List<BeneficiaryCall> calls = service.getBeneficiaryCallsHistory(7L, 0, 10);

		assertEquals(1, calls.size());
		assertEquals("remark", calls.get(0).getRemarks());
	}

	@Test
	void getBeneficiaryCallsHistorySkipsRowsWithTooFewColumns() {
		ArrayList<Object[]> rows = new ArrayList<>();
		rows.add(new Object[] { 1L, new Timestamp(0L) });
		when(beneficiaryCallRepository.findCallsHistoryByBenefeciaryID(anyLong(), any(Pageable.class)))
				.thenReturn(rows);

		assertTrue(service.getBeneficiaryCallsHistory(7L, 0, 10).isEmpty());
	}

	@Test
	void getBeneficiaryCallsHistoryByCalledServiceMapsRows() {
		when(beneficiaryCallRepository.findCallsHistoryByBenefeciaryID(anyLong(), anyInt(), any(Pageable.class)))
				.thenReturn(summaryRows());

		List<BeneficiaryCall> calls = service.getBeneficiaryCallsHistory(7L, 3, 0, 10);

		assertEquals(1, calls.size());
		verify(beneficiaryCallRepository).findCallsHistoryByBenefeciaryID(anyLong(), anyInt(), any(Pageable.class));
	}

	@Test
	void getCallSummaryByCallIdMapsRows() {
		when(beneficiaryCallRepository.findCallSummaryByCallID(anyLong())).thenReturn(summaryRows());

		List<BeneficiaryCall> calls = service.getCallSummaryByCallID(5L);

		assertEquals(1, calls.size());
		assertEquals(1L, calls.get(0).getBenCallID());
	}

	@Test
	void getCallSummaryByCallIdReturnsEmptyListWhenNoRows() {
		when(beneficiaryCallRepository.findCallSummaryByCallID(anyLong())).thenReturn(new ArrayList<>());

		assertTrue(service.getCallSummaryByCallID(5L).isEmpty());
	}

	@Test
	void updateBeneficiaryIdInCallReturnsUpdateCount() {
		when(beneficiaryCallRepository.updateBeneficiaryIDInCall(anyLong(), anyLong())).thenReturn(2);

		assertEquals(2, service.updateBeneficiaryIDInCall("{\"benCallID\":5,\"beneficiaryRegID\":9}"));
		verify(beneficiaryCallRepository).updateBeneficiaryIDInCall(5L, 9L);
	}

	@Test
	void nullRowsAreSkippedByEveryCallProjection() {
		ArrayList<Object[]> rows = new ArrayList<>();
		rows.add(null);
		when(beneficiaryCallRepository.findCallsHistoryByBenefeciaryID(anyLong(), any(Pageable.class)))
				.thenReturn(rows);
		when(beneficiaryCallRepository.findCallsHistoryByBenefeciaryID(anyLong(), anyInt(), any(Pageable.class)))
				.thenReturn(rows);
		when(beneficiaryCallRepository.findCallSummaryByCallID(anyLong())).thenReturn(rows);

		assertTrue(service.getBeneficiaryCallsHistory(7L, 0, 10).isEmpty());
		assertTrue(service.getBeneficiaryCallsHistory(7L, 3, 0, 10).isEmpty());
		assertTrue(service.getCallSummaryByCallID(5L).isEmpty());
	}

	@Test
	void shortRowsAreSkippedByTheCalledServiceAndSummaryProjections() {
		ArrayList<Object[]> rows = new ArrayList<>();
		rows.add(new Object[] { 1L, new Timestamp(0L) });
		when(beneficiaryCallRepository.findCallsHistoryByBenefeciaryID(anyLong(), anyInt(), any(Pageable.class)))
				.thenReturn(rows);
		when(beneficiaryCallRepository.findCallSummaryByCallID(anyLong())).thenReturn(rows);

		assertTrue(service.getBeneficiaryCallsHistory(7L, 3, 0, 10).isEmpty());
		assertTrue(service.getCallSummaryByCallID(5L).isEmpty());
	}
}
