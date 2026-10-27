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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iemr.helpline1097.data.co.beneficiarycall.BenCallServicesMappingHistory;
import com.iemr.helpline1097.data.co.services.CategoryDetails;
import com.iemr.helpline1097.data.co.services.SubCategoryDetails;
import com.iemr.helpline1097.repository.co.beneficiarycall.ServicesHistoryRepository;
import com.iemr.helpline1097.repository.co.services.InstitutionRepository;

@ExtendWith(MockitoExtension.class)
class ServicesHistoryServiceImplTest {

	private static final String REQUEST_WITH_SERVICE = "{\"beneficiaryRegID\":10,\"calledServiceID\":3}";
	private static final String REQUEST_WITHOUT_SERVICE = "{\"beneficiaryRegID\":10}";

	@Mock
	private ServicesHistoryRepository serviceHistoryRepository;

	@Mock
	private InstitutionRepository institutionRepository;

	private ServicesHistoryServiceImpl service;

	@BeforeEach
	void setUp() {
		service = new ServicesHistoryServiceImpl();
		service.setServiceHistoryRepository(serviceHistoryRepository);
		service.setInstitutionRepository(institutionRepository);
	}

	/** A row shaped for the 19-column call-detail projection. */
	private static List<Object[]> detailRows() {
		Object[] row = new Object[19];
		row[0] = BigInteger.ONE;
		row[1] = 10L;
		row[2] = 20L;
		row[13] = "created-by";
		row[14] = new Timestamp(0L);
		return Collections.singletonList(row);
	}

	/** A row shaped for the 9-column referral/feedback projection. */
	private static List<Object[]> nineColumnRows() {
		Object[] row = new Object[9];
		row[0] = BigInteger.ONE;
		row[1] = 10L;
		row[2] = 20L;
		row[7] = "created-by";
		row[8] = new Timestamp(0L);
		return Collections.singletonList(row);
	}

	/** A row shaped for the 11-column information/counselling projection. */
	private static List<Object[]> elevenColumnRows() {
		Object[] row = new Object[11];
		row[0] = BigInteger.ONE;
		row[1] = 10L;
		row[2] = 20L;
		row[9] = "created-by";
		row[10] = new Timestamp(0L);
		return Collections.singletonList(row);
	}

	@Test
	void createServiceHistoryDelegatesToRepository() {
		BenCallServicesMappingHistory history = new BenCallServicesMappingHistory();
		when(serviceHistoryRepository.save(history)).thenReturn(history);

		assertSame(history, service.createServiceHistory(history));
	}

	@Test
	void getServiceHistoryByIdMapsProjectionRows() {
		when(serviceHistoryRepository.findCallDetailsForBeneficiary(anyLong())).thenReturn(detailRows());

		List<BenCallServicesMappingHistory> history = service.getServiceHistory(10L);

		assertEquals(1, history.size());
		assertEquals(BigInteger.ONE, history.get(0).getBenCall97ServiceMapID());
	}

	@Test
	void getServiceHistoryByIdSkipsShortRows() {
		when(serviceHistoryRepository.findCallDetailsForBeneficiary(anyLong()))
				.thenReturn(Collections.singletonList(new Object[3]));

		assertTrue(service.getServiceHistory(10L).isEmpty());
	}

	@Test
	void getServiceHistoryByRequestUsesCalledServiceWhenPresent() throws Exception {
		when(serviceHistoryRepository.findCallDetailsForBeneficiary(anyLong(), anyInt())).thenReturn(detailRows());

		assertEquals(1, service.getServiceHistory(REQUEST_WITH_SERVICE).size());
		verify(serviceHistoryRepository).findCallDetailsForBeneficiary(10L, 3);
	}

	@Test
	void getServiceHistoryByRequestFallsBackWithoutCalledService() throws Exception {
		when(serviceHistoryRepository.findCallDetailsForBeneficiary(anyLong())).thenReturn(detailRows());

		assertEquals(1, service.getServiceHistory(REQUEST_WITHOUT_SERVICE).size());
		verify(serviceHistoryRepository).findCallDetailsForBeneficiary(10L);
	}

	@Test
	void getServiceHistoryByRequestPropagatesParseFailure() {
		assertThrows(JsonProcessingException.class, () -> service.getServiceHistory("{oops}"));
	}

	@Test
	void getCallSummaryByIdMapsProjectionRows() {
		when(serviceHistoryRepository.getCallSummary(anyLong())).thenReturn(detailRows());

		assertEquals(1, service.getCallSummary(10L).size());
	}

	@Test
	void getCallSummaryByRequestUsesCalledServiceWhenPresent() throws Exception {
		when(serviceHistoryRepository.getCallSummary(anyLong(), anyInt())).thenReturn(detailRows());

		assertEquals(1, service.getCallSummary(REQUEST_WITH_SERVICE).size());
		verify(serviceHistoryRepository).getCallSummary(10L, 3);
	}

	@Test
	void getCallSummaryByRequestFallsBackWithoutCalledService() throws Exception {
		when(serviceHistoryRepository.getCallSummary(anyLong())).thenReturn(detailRows());

		assertEquals(1, service.getCallSummary(REQUEST_WITHOUT_SERVICE).size());
	}

	@Test
	void getCallSummaryV1ReturnsEmptyListWhenNoHistory() {
		when(serviceHistoryRepository.getCallSummaryV1(anyLong())).thenReturn(new ArrayList<>());

		assertTrue(service.getCallSummaryV1(5L).isEmpty());
	}

	@Test
	void getCallSummaryV1CollectsInformationsAndCounsellingsOntoFirstCaseSheet() {
		BenCallServicesMappingHistory caseSheet = new BenCallServicesMappingHistory();
		caseSheet.setInformations(new ArrayList<>());
		caseSheet.setCounsellings(new ArrayList<>());
		caseSheet.setFeedbacks(new ArrayList<>());
		caseSheet.setReferrals(new ArrayList<>());

		BenCallServicesMappingHistory information = new BenCallServicesMappingHistory();
		information.setSubCategoryID(2);
		information.setSubCategoryDetails(new SubCategoryDetails(2, "Diet"));

		BenCallServicesMappingHistory counselling = new BenCallServicesMappingHistory();
		counselling.setCoSubCategoryID(3);
		counselling.setCoSubCategoryDetails(new SubCategoryDetails(3, "Support"));

		when(serviceHistoryRepository.getCallSummaryV1(anyLong()))
				.thenReturn(new ArrayList<>(Arrays.asList(caseSheet, information, counselling)));

		List<BenCallServicesMappingHistory> caseSheets = service.getCallSummaryV1(5L);

		assertEquals(1, caseSheets.size());
		assertEquals(1, caseSheets.get(0).getInformations().size());
		assertEquals(1, caseSheets.get(0).getCounsellings().size());
	}

	@Test
	void getCallSummaryV1CollectsFeedbacksAndReferralsOntoFirstCaseSheet() {
		BenCallServicesMappingHistory caseSheet = new BenCallServicesMappingHistory();
		caseSheet.setInformations(new ArrayList<>());
		caseSheet.setCounsellings(new ArrayList<>());
		caseSheet.setFeedbacks(new ArrayList<>());
		caseSheet.setReferrals(new ArrayList<>());

		BenCallServicesMappingHistory feedback = new BenCallServicesMappingHistory();
		feedback.setFeedbackID(4L);

		BenCallServicesMappingHistory referral = new BenCallServicesMappingHistory();
		referral.setInstituteDirMapID(6L);

		when(serviceHistoryRepository.getCallSummaryV1(anyLong()))
				.thenReturn(new ArrayList<>(Arrays.asList(caseSheet, feedback, referral)));

		List<BenCallServicesMappingHistory> caseSheets = service.getCallSummaryV1(5L);

		assertEquals(1, caseSheets.get(0).getFeedbacks().size());
		assertEquals(1, caseSheets.get(0).getReferrals().size());
	}

	@Test
	void getReferralsHistoryByIdMapsNineColumnRows() {
		when(serviceHistoryRepository.findReferralsForBeneficiary(anyLong(), any(Pageable.class)))
				.thenReturn(nineColumnRows());

		assertEquals(1, service.getReferralsHistory(10L, 0, 100).size());
	}

	@Test
	void getReferralsHistoryByRequestUsesCalledServiceWhenPresent() throws Exception {
		when(serviceHistoryRepository.findReferralsForBeneficiary(anyLong(), anyInt(), any(Pageable.class)))
				.thenReturn(nineColumnRows());

		assertEquals(1, service.getReferralsHistory(REQUEST_WITH_SERVICE).size());
	}

	@Test
	void getReferralsHistoryByRequestFallsBackWithoutCalledService() throws Exception {
		when(serviceHistoryRepository.findReferralsForBeneficiary(anyLong(), any(Pageable.class)))
				.thenReturn(nineColumnRows());

		assertEquals(1, service.getReferralsHistory(REQUEST_WITHOUT_SERVICE).size());
	}

	@Test
	void getFeedbacksHistoryByIdMapsNineColumnRows() {
		when(serviceHistoryRepository.findFeedbacksForBeneficiary(anyLong(), any(Pageable.class)))
				.thenReturn(nineColumnRows());

		assertEquals(1, service.getFeedbacksHistory(10L, 0, 100).size());
	}

	@Test
	void getFeedbacksHistoryByRequestUsesCalledServiceWhenPresent() throws Exception {
		when(serviceHistoryRepository.findFeedbacksForBeneficiary(anyLong(), anyInt(), any(Pageable.class)))
				.thenReturn(nineColumnRows());

		assertEquals(1, service.getFeedbacksHistory(REQUEST_WITH_SERVICE).size());
	}

	@Test
	void getFeedbacksHistoryByRequestFallsBackWithoutCalledService() throws Exception {
		when(serviceHistoryRepository.findFeedbacksForBeneficiary(anyLong(), any(Pageable.class)))
				.thenReturn(nineColumnRows());

		assertEquals(1, service.getFeedbacksHistory(REQUEST_WITHOUT_SERVICE).size());
	}

	@Test
	void getInformationsHistoryByIdMapsElevenColumnRows() {
		when(serviceHistoryRepository.findInformationsForBeneficiary(anyLong(), any(Pageable.class)))
				.thenReturn(elevenColumnRows());

		assertEquals(1, service.getInformationsHistory(10L, 0, 100).size());
	}

	@Test
	void getInformationsHistoryByRequestUsesCalledServiceWhenPresent() throws Exception {
		when(serviceHistoryRepository.findInformationsForBeneficiary(anyLong(), anyInt(), any(Pageable.class)))
				.thenReturn(elevenColumnRows());

		assertEquals(1, service.getInformationsHistory(REQUEST_WITH_SERVICE).size());
	}

	@Test
	void getInformationsHistoryByRequestFallsBackWithoutCalledService() throws Exception {
		when(serviceHistoryRepository.findInformationsForBeneficiary(anyLong(), any(Pageable.class)))
				.thenReturn(elevenColumnRows());

		assertEquals(1, service.getInformationsHistory(REQUEST_WITHOUT_SERVICE).size());
	}

	@Test
	void getCounsellingsHistoryByIdMapsElevenColumnRows() {
		when(serviceHistoryRepository.findCounsellingsForBeneficiary(anyLong(), any(Pageable.class)))
				.thenReturn(elevenColumnRows());

		assertEquals(1, service.getCounsellingsHistory(10L, 0, 100).size());
	}

	@Test
	void getCounsellingsHistoryByRequestUsesCalledServiceWhenPresent() throws Exception {
		when(serviceHistoryRepository.findCounsellingsForBeneficiary(anyLong(), anyInt(), any(Pageable.class)))
				.thenReturn(elevenColumnRows());

		assertEquals(1, service.getCounsellingsHistory(REQUEST_WITH_SERVICE).size());
	}

	@Test
	void getCounsellingsHistoryByRequestFallsBackWithoutCalledService() throws Exception {
		when(serviceHistoryRepository.findCounsellingsForBeneficiary(anyLong(), any(Pageable.class)))
				.thenReturn(elevenColumnRows());

		assertEquals(1, service.getCounsellingsHistory(REQUEST_WITHOUT_SERVICE).size());
	}

	@Test
	void shortRowsAreSkippedAcrossEveryProjection() throws Exception {
		when(serviceHistoryRepository.findReferralsForBeneficiary(anyLong(), any(Pageable.class)))
				.thenReturn(Collections.singletonList(new Object[2]));
		when(serviceHistoryRepository.findInformationsForBeneficiary(anyLong(), any(Pageable.class)))
				.thenReturn(Collections.singletonList(new Object[2]));

		assertTrue(service.getReferralsHistory(10L, 0, 100).isEmpty());
		assertTrue(service.getInformationsHistory(10L, 0, 100).isEmpty());
	}

	@Test
	void unusedCategoryProjectionColumnsAreTolerated() {
		Object[] row = new Object[19];
		row[0] = BigInteger.TEN;
		row[6] = new CategoryDetails(1, "Nutrition");
		row[16] = new CategoryDetails(2, "Counselling");
		when(serviceHistoryRepository.getCallSummary(anyLong())).thenReturn(Collections.singletonList(row));

		List<BenCallServicesMappingHistory> summary = service.getCallSummary(10L);

		assertEquals(BigInteger.TEN, summary.get(0).getBenCall97ServiceMapID());
	}

	/** A single null row, which every projection loop must skip. */
	private static List<Object[]> nullRow() {
		return Collections.singletonList((Object[]) null);
	}

	@Test
	void nullRowsAreSkippedByTheCallDetailProjections() throws Exception {
		when(serviceHistoryRepository.findCallDetailsForBeneficiary(anyLong())).thenReturn(nullRow());
		when(serviceHistoryRepository.findCallDetailsForBeneficiary(anyLong(), anyInt())).thenReturn(nullRow());

		assertTrue(service.getServiceHistory(10L).isEmpty());
		assertTrue(service.getServiceHistory(REQUEST_WITHOUT_SERVICE).isEmpty());
		assertTrue(service.getServiceHistory(REQUEST_WITH_SERVICE).isEmpty());
	}

	@Test
	void nullRowsAreSkippedByTheCallSummaryProjections() throws Exception {
		when(serviceHistoryRepository.getCallSummary(anyLong())).thenReturn(nullRow());
		when(serviceHistoryRepository.getCallSummary(anyLong(), anyInt())).thenReturn(nullRow());

		assertTrue(service.getCallSummary(10L).isEmpty());
		assertTrue(service.getCallSummary(REQUEST_WITHOUT_SERVICE).isEmpty());
		assertTrue(service.getCallSummary(REQUEST_WITH_SERVICE).isEmpty());
	}

	@Test
	void nullAndShortRowsAreSkippedByTheReferralProjections() throws Exception {
		when(serviceHistoryRepository.findReferralsForBeneficiary(anyLong(), any(Pageable.class)))
				.thenReturn(nullRow());
		when(serviceHistoryRepository.findReferralsForBeneficiary(anyLong(), anyInt(), any(Pageable.class)))
				.thenReturn(Collections.singletonList(new Object[2]));

		assertTrue(service.getReferralsHistory(10L, 0, 100).isEmpty());
		assertTrue(service.getReferralsHistory(REQUEST_WITHOUT_SERVICE).isEmpty());
		assertTrue(service.getReferralsHistory(REQUEST_WITH_SERVICE).isEmpty());
	}

	@Test
	void nullAndShortRowsAreSkippedByTheFeedbackProjections() throws Exception {
		when(serviceHistoryRepository.findFeedbacksForBeneficiary(anyLong(), any(Pageable.class)))
				.thenReturn(nullRow());
		when(serviceHistoryRepository.findFeedbacksForBeneficiary(anyLong(), anyInt(), any(Pageable.class)))
				.thenReturn(Collections.singletonList(new Object[2]));

		assertTrue(service.getFeedbacksHistory(10L, 0, 100).isEmpty());
		assertTrue(service.getFeedbacksHistory(REQUEST_WITHOUT_SERVICE).isEmpty());
		assertTrue(service.getFeedbacksHistory(REQUEST_WITH_SERVICE).isEmpty());
	}

	@Test
	void nullAndShortRowsAreSkippedByTheInformationProjections() throws Exception {
		when(serviceHistoryRepository.findInformationsForBeneficiary(anyLong(), any(Pageable.class)))
				.thenReturn(nullRow());
		when(serviceHistoryRepository.findInformationsForBeneficiary(anyLong(), anyInt(), any(Pageable.class)))
				.thenReturn(Collections.singletonList(new Object[2]));

		assertTrue(service.getInformationsHistory(10L, 0, 100).isEmpty());
		assertTrue(service.getInformationsHistory(REQUEST_WITHOUT_SERVICE).isEmpty());
		assertTrue(service.getInformationsHistory(REQUEST_WITH_SERVICE).isEmpty());
	}

	@Test
	void nullAndShortRowsAreSkippedByTheCounsellingProjections() throws Exception {
		when(serviceHistoryRepository.findCounsellingsForBeneficiary(anyLong(), any(Pageable.class)))
				.thenReturn(nullRow());
		when(serviceHistoryRepository.findCounsellingsForBeneficiary(anyLong(), anyInt(), any(Pageable.class)))
				.thenReturn(Collections.singletonList(new Object[2]));

		assertTrue(service.getCounsellingsHistory(10L, 0, 100).isEmpty());
		assertTrue(service.getCounsellingsHistory(REQUEST_WITHOUT_SERVICE).isEmpty());
		assertTrue(service.getCounsellingsHistory(REQUEST_WITH_SERVICE).isEmpty());
	}

	@Test
	void getCallSummaryV1LeavesTheCaseSheetAloneWhenItsCollectionsAreNull() {
		BenCallServicesMappingHistory caseSheet = new BenCallServicesMappingHistory();
		caseSheet.setInformations(null);
		caseSheet.setCounsellings(null);
		caseSheet.setFeedbacks(null);
		caseSheet.setReferrals(null);

		BenCallServicesMappingHistory information = new BenCallServicesMappingHistory();
		information.setSubCategoryID(2);
		BenCallServicesMappingHistory counselling = new BenCallServicesMappingHistory();
		counselling.setCoSubCategoryID(3);
		BenCallServicesMappingHistory feedback = new BenCallServicesMappingHistory();
		feedback.setFeedbackID(4L);
		BenCallServicesMappingHistory referral = new BenCallServicesMappingHistory();
		referral.setInstituteDirMapID(6L);

		when(serviceHistoryRepository.getCallSummaryV1(anyLong())).thenReturn(
				new ArrayList<>(Arrays.asList(caseSheet, information, counselling, feedback, referral)));

		List<BenCallServicesMappingHistory> caseSheets = service.getCallSummaryV1(5L);

		assertEquals(1, caseSheets.size());
		assertNull(caseSheets.get(0).getInformations());
	}

	@Test
	void getCallSummaryV1IgnoresRowsThatMatchNoServiceType() {
		BenCallServicesMappingHistory caseSheet = new BenCallServicesMappingHistory();
		BenCallServicesMappingHistory unclassified = new BenCallServicesMappingHistory();

		when(serviceHistoryRepository.getCallSummaryV1(anyLong()))
				.thenReturn(new ArrayList<>(Arrays.asList(caseSheet, unclassified)));

		List<BenCallServicesMappingHistory> caseSheets = service.getCallSummaryV1(5L);

		assertEquals(1, caseSheets.size());
		assertTrue(caseSheets.get(0).getInformations().isEmpty());
	}
}
