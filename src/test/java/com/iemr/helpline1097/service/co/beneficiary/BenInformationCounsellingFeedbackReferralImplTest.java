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
package com.iemr.helpline1097.service.co.beneficiary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.iemr.helpline1097.data.co.beneficiarycall.BenCallServicesMappingHistory;
import com.iemr.helpline1097.repository.co.beneficiary.BenCalServiceCatSubcatMappingRepo;
import com.iemr.helpline1097.repository.co.services.DirectoryMappingRepository;
import com.iemr.helpline1097.repository.co.services.SubCategoryRepository;
import com.iemr.helpline1097.utils.config.ConfigProperties;

@ExtendWith(MockitoExtension.class)
class BenInformationCounsellingFeedbackReferralImplTest {

	@Mock
	private BenCalServiceCatSubcatMappingRepo benCalServiceCatSubcatMappingRepo;

	@Mock
	private SubCategoryRepository subCategoryRepository;

	@Mock
	private DirectoryMappingRepository directoryMappingRepository;

	private BenInformationCounsellingFeedbackReferralImpl service;

	@BeforeEach
	void setUp() {
		service = new BenInformationCounsellingFeedbackReferralImpl();
		service.getBenCalServiceCatSubcatMappingRepo(benCalServiceCatSubcatMappingRepo);
		service.getSubCategoryRepository(subCategoryRepository);
		service.getDirectoryMappingRepository(directoryMappingRepository);
		service.setConfigProperties(new ConfigProperties());
	}

	private static Set<Object[]> subCategoryRow(String fileUid) {
		Set<Object[]> rows = new LinkedHashSet<>();
		rows.add(new Object[] { fileUid, "Diet", "Diet advice" });
		return rows;
	}

	private static BenCallServicesMappingHistory information(Integer subCategoryId) {
		BenCallServicesMappingHistory history = new BenCallServicesMappingHistory();
		history.setSubCategoryID(subCategoryId);
		return history;
	}

	private static BenCallServicesMappingHistory counselling(Integer coSubCategoryId) {
		BenCallServicesMappingHistory history = new BenCallServicesMappingHistory();
		history.setCoSubCategoryID(coSubCategoryId);
		return history;
	}

	@Test
	void saveBenCallServiceCatSubCatPersistsRowsWithAResolvedSubCategory() {
		when(subCategoryRepository.findFilePathBySubCategoryId(anyInt())).thenReturn(subCategoryRow(null));

		String result = service.saveBenCallServiceCatSubCat(Collections.singletonList(information(2)));

		assertTrue(result.contains("Diet"));
		verify(benCalServiceCatSubcatMappingRepo).save(any(BenCallServicesMappingHistory.class));
	}

	@Test
	void saveBenCallServiceCatSubCatBuildsDownloadUriWhenFileUidIsPresent() {
		when(subCategoryRepository.findFilePathBySubCategoryId(anyInt())).thenReturn(subCategoryRow("uid-123"));

		String result = service.saveBenCallServiceCatSubCat(Collections.singletonList(information(2)));

		assertTrue(result.contains("Download?uuid"));
		assertTrue(result.contains("uid-123"));
	}

	@Test
	void saveBenCallServiceCatSubCatSkipsRowsWithoutAResolvedSubCategory() {
		when(subCategoryRepository.findFilePathBySubCategoryId(anyInt())).thenReturn(new LinkedHashSet<>());

		assertEquals("[]", service.saveBenCallServiceCatSubCat(Collections.singletonList(information(2))));
		verify(benCalServiceCatSubcatMappingRepo, never()).save(any(BenCallServicesMappingHistory.class));
	}

	@Test
	void saveBenCallServiceCatSubCatSkipsShortProjectionRows() {
		Set<Object[]> rows = new LinkedHashSet<>();
		rows.add(new Object[] { "uid", "Diet" });
		when(subCategoryRepository.findFilePathBySubCategoryId(anyInt())).thenReturn(rows);

		assertEquals("[]", service.saveBenCallServiceCatSubCat(Collections.singletonList(information(2))));
	}

	@Test
	void saveBenCallServiceCatSubCatReturnsEmptyResultForNoInput() {
		assertEquals("[]", service.saveBenCallServiceCatSubCat(Collections.emptyList()));
	}

	@Test
	void saveBenCallServiceCoCatSubCatPersistsRowsWithAResolvedSubCategory() {
		when(subCategoryRepository.findFilePathBySubCategoryId(anyInt())).thenReturn(subCategoryRow(null));

		String result = service.saveBenCallServiceCOCatSubCat(Collections.singletonList(counselling(3)));

		assertTrue(result.contains("Diet"));
		verify(benCalServiceCatSubcatMappingRepo).save(any(BenCallServicesMappingHistory.class));
	}

	@Test
	void saveBenCallServiceCoCatSubCatSkipsRowsWithoutAResolvedSubCategory() {
		when(subCategoryRepository.findFilePathBySubCategoryId(anyInt())).thenReturn(new LinkedHashSet<>());

		assertEquals("[]", service.saveBenCallServiceCOCatSubCat(Collections.singletonList(counselling(3))));
		verify(benCalServiceCatSubcatMappingRepo, never()).save(any(BenCallServicesMappingHistory.class));
	}

	@Test
	void saveBenCalReferralMappingSwallowsFailuresAndReturnsAnEmptyResult() {
		assertEquals("[]", service.saveBenCalReferralMapping("[{\"instituteDirectoryID\":1,\"stateID\":2}]"));
		verify(directoryMappingRepository, never()).findDirectories(anyInt(), anyInt());
	}

	@Test
	void saveBenCalReferralMappingReturnsAnEmptyResultForMalformedInput() {
		assertEquals("[]", service.saveBenCalReferralMapping("{oops}"));
	}

	@Test
	void nullProjectionRowsAreSkippedByBothSubCategoryLookups() {
		Set<Object[]> rows = new LinkedHashSet<>();
		rows.add(null);
		when(subCategoryRepository.findFilePathBySubCategoryId(anyInt())).thenReturn(rows);

		assertEquals("[]", service.saveBenCallServiceCatSubCat(Collections.singletonList(information(2))));
		assertEquals("[]", service.saveBenCallServiceCOCatSubCat(Collections.singletonList(counselling(3))));
	}

	@Test
	void aBlankFileUidLeavesTheDownloadUriUnset() {
		when(subCategoryRepository.findFilePathBySubCategoryId(anyInt())).thenReturn(subCategoryRow("   "));

		String result = service.saveBenCallServiceCatSubCat(Collections.singletonList(information(2)));

		assertTrue(result.contains("\"subCatFilePath\":null"));
	}
}
