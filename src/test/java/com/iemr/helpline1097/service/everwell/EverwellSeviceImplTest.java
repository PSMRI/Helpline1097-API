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
package com.iemr.helpline1097.service.everwell;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.iemr.helpline1097.data.everwell.EverwellGuidelines;
import com.iemr.helpline1097.repository.everwell.EverwellGuidelinesRepo;
import com.iemr.helpline1097.utils.exception.IEMRException;

@ExtendWith(MockitoExtension.class)
class EverwellSeviceImplTest {

	@Mock
	private EverwellGuidelinesRepo everwellGuidelinesRepo;

	@InjectMocks
	private EverwellSeviceImpl service;

	private static EverwellGuidelines guideline(Integer id) {
		EverwellGuidelines guidelines = new EverwellGuidelines();
		guidelines.setId(id);
		return guidelines;
	}

	@Test
	void saveGuidelinesPersistsNewDocumentWhenNoneExists() throws Exception {
		when(everwellGuidelinesRepo.checkIfDocAlreadyexists(any(), any())).thenReturn(Collections.emptyList());
		when(everwellGuidelinesRepo.save(any(EverwellGuidelines.class))).thenReturn(guideline(1));

		String result = service.saveGuidelines("{\"category\":\"> 95 adherence percentage\",\"providerServiceMapID\":4}");

		assertTrue(result.contains("data"));
		verify(everwellGuidelinesRepo).save(any(EverwellGuidelines.class));
	}

	@Test
	void saveGuidelinesRejectsDuplicateCategoryForNewDocument() throws Exception {
		when(everwellGuidelinesRepo.checkIfDocAlreadyexists(any(), any()))
				.thenReturn(Collections.singletonList(guideline(9)));

		String result = service.saveGuidelines("{\"category\":\"dup\",\"providerServiceMapID\":4}");

		assertTrue(result.contains("already exists"));
		verify(everwellGuidelinesRepo, never()).save(any(EverwellGuidelines.class));
	}

	@Test
	void saveGuidelinesUpdatesExistingDocumentWhenIdPresent() throws Exception {
		when(everwellGuidelinesRepo.save(any(EverwellGuidelines.class))).thenReturn(guideline(3));

		String result = service.saveGuidelines("{\"id\":3,\"category\":\"cat\"}");

		assertTrue(result.contains("data"));
		verify(everwellGuidelinesRepo, never()).checkIfDocAlreadyexists(any(), any());
	}

	@Test
	void saveGuidelinesWrapsFailuresInIemrException() {
		IEMRException thrown = assertThrows(IEMRException.class, () -> service.saveGuidelines("{oops}"));

		assertTrue(thrown.getMessage() != null);
	}

	@Test
	void fetchGuidelinesFetchesAllWhenAdherenceIsNull() throws Exception {
		List<EverwellGuidelines> stored = Collections.singletonList(guideline(1));
		when(everwellGuidelinesRepo.findByProviderServiceMapID(any())).thenReturn(stored);

		String result = service.fetchGuidelines("{\"providerServiceMapID\":4}");

		assertTrue(result.contains("data"));
		verify(everwellGuidelinesRepo).findByProviderServiceMapID(4);
	}

	@Test
	void fetchGuidelinesUsesHighAdherenceCategoryAbove95() throws Exception {
		when(everwellGuidelinesRepo.findGuidelinesByCategory(anyString(), anyInt()))
				.thenReturn(Collections.emptyList());

		service.fetchGuidelines("{\"providerServiceMapID\":4,\"adherencePercentage\":97}");

		verify(everwellGuidelinesRepo).findGuidelinesByCategory("> 95 adherence percentage", 4);
	}

	@Test
	void fetchGuidelinesUsesLowAdherenceCategoryAtOrBelow95() throws Exception {
		when(everwellGuidelinesRepo.findGuidelinesByCategory(anyString(), anyInt()))
				.thenReturn(Collections.emptyList());

		service.fetchGuidelines("{\"providerServiceMapID\":4,\"adherencePercentage\":95}");

		verify(everwellGuidelinesRepo).findGuidelinesByCategory("<= 95 adherence percentage", 4);
	}

	@Test
	void fetchGuidelinesWrapsFailuresInIemrException() {
		assertThrows(IEMRException.class, () -> service.fetchGuidelines("{oops}"));
	}

	@Test
	void deleteGuidelineReportsSuccessWhenRowRemoved() throws Exception {
		when(everwellGuidelinesRepo.deleteGuideline(anyInt(), any())).thenReturn(1);

		assertEquals("Guideline deleted successfully",
				service.deleteGuideline("{\"id\":3,\"modifiedBy\":\"admin\"}"));
	}

	@Test
	void deleteGuidelineReportsMissingRowWhenNothingRemoved() throws Exception {
		when(everwellGuidelinesRepo.deleteGuideline(anyInt(), any())).thenReturn(0);

		assertEquals("Row with id does not exist", service.deleteGuideline("{\"id\":3,\"modifiedBy\":\"admin\"}"));
	}

	@Test
	void deleteGuidelineRejectsMissingId() {
		IEMRException thrown = assertThrows(IEMRException.class,
				() -> service.deleteGuideline("{\"modifiedBy\":\"admin\"}"));

		assertEquals("Auto increment id cannot be null", thrown.getMessage());
	}
}
