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
package com.iemr.helpline1097.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigInteger;
import java.sql.Timestamp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.iemr.helpline1097.data.co.beneficiarycall.BenCallServicesMappingHistory;
import com.iemr.helpline1097.data.co.beneficiarycall.BeneficiaryCall;
import com.iemr.helpline1097.data.co.beneficiarycall.CallType;
import com.iemr.helpline1097.data.co.beneficiarycall.ServiceProvided;
import com.iemr.helpline1097.data.co.beneficiarycall.SubServices;
import com.iemr.helpline1097.data.co.services.CategoryDetails;
import com.iemr.helpline1097.data.co.services.DirectoryMapping;
import com.iemr.helpline1097.data.co.services.SubCategoryDetails;

class BeneficiaryCallEntityAccessorsTest {

	private static final Timestamp CREATED = new Timestamp(1_700_000_000_000L);

	@BeforeEach
	void primeOutputMapper() {
		EntityAccessors.primeOutputMapper();
	}

	@Test
	void everyBeneficiaryCallEntityRoundTripsItsAccessors() {
		EntityAccessors.assertRoundTrips(BenCallServicesMappingHistory.class, BeneficiaryCall.class, CallType.class,
				ServiceProvided.class, SubServices.class);
	}

	@Test
	void callTypeConstructorSetsEveryColumn() {
		CallType callType = new CallType(1, "Inbound", "Inbound calls", "General", true, false);

		assertEquals(1, callType.getCallTypeID());
		assertEquals("Inbound", callType.getCallType());
		assertEquals("Inbound calls", callType.getCallTypeDesc());
		assertEquals("General", callType.getCallGroupType());
		assertEquals(true, callType.getFitToBlock());
		assertEquals(false, callType.getFitForFollowUp());
		assertNotNull(callType.toString());
	}

	@Test
	void serviceProvidedConstructorSetsEveryColumn() {
		ServiceProvided serviceProvided = new ServiceProvided(4L, "info", "counselling", "referral", "feedback");

		assertEquals(4L, serviceProvided.getServiceID());
		assertEquals("info", serviceProvided.getInformationService());
		assertEquals("counselling", serviceProvided.getCounsellingService());
		assertEquals("referral", serviceProvided.getReferralService());
		assertEquals("feedback", serviceProvided.getFeedbackSystem());
	}

	@Test
	void subServicesConstructorSetsEveryColumn() {
		SubServices subServices = new SubServices(1, "1097", "Helpline 1097", false);

		assertEquals(1, subServices.getSubServiceID());
		assertEquals("1097", subServices.getSubServiceName());
		assertEquals("Helpline 1097", subServices.getSubServiceDesc());
		assertEquals(false, subServices.getDeleted());
	}

	@Test
	void beneficiaryCallNewCallConstructorMarksTheCallAsSystemCreated() {
		BeneficiaryCall call = new BeneficiaryCall(10L, true, "system");

		assertEquals(10L, call.getBeneficiaryRegID());
		assertEquals(true, call.getIs1097());
		assertEquals("system", call.getCreatedBy());
	}

	@Test
	void beneficiaryCallSummaryConstructorNormalisesServiceCountsToFlags() {
		BeneficiaryCall call = new BeneficiaryCall(1L, CREATED, "remark", 3L, 0L, 5L, CREATED, 0L, new CallType());

		assertEquals(1L, call.getBenCallID());
		assertEquals("remark", call.getRemarks());
		assertEquals(1L, call.getInformationServices());
		assertEquals(0L, call.getFeedbackServices());
		assertEquals(1L, call.getReferralServices());
		assertEquals(0L, call.getCounsellingServices());
	}

	@Test
	void beneficiaryCallHistoryConstructorParsesTheServiceHistoryJson() {
		BeneficiaryCall call = new BeneficiaryCall(1L, "[]", 3, true, CREATED, "remark", "resolved", 2);

		assertEquals(1L, call.getBenCallID());
		assertEquals(3, call.getCalledServiceID());
		assertEquals("resolved", call.getCallClosureType());
		assertEquals(2, call.getDispositionStatusID());
		assertTrue(call.getBenCallServicesMappingHistories().isEmpty());
	}

	@Test
	void informationConstructorSetsTheCategoryPair() {
		BenCallServicesMappingHistory history = new BenCallServicesMappingHistory(10L, 7L, 1, 2, 3, false, "agent");

		assertEquals(10L, history.getBeneficiaryRegID());
		assertEquals(2, history.getCategoryID());
		assertEquals(3, history.getSubCategoryID());
		assertEquals("agent", history.getCreatedBy());
	}

	@Test
	void counsellingConstructorStoresTheCoCategoryPairInTheInformationColumns() {
		BenCallServicesMappingHistory history = new BenCallServicesMappingHistory(10L, 7L, 1, false, 4, 5, "agent");

		// The constructor assigns its coCategoryID/coSubCategoryID arguments to
		// categoryID/subCategoryID, so the co-* fields stay unset.
		assertEquals(4, history.getCategoryID());
		assertEquals(5, history.getSubCategoryID());
		assertNull(history.getCoCategoryID());
		assertNull(history.getCoSubCategoryID());
	}

	@Test
	void feedbackConstructorSetsTheFeedbackId() {
		BenCallServicesMappingHistory history = new BenCallServicesMappingHistory(Long.valueOf(10L), 7L, 1, 9L, false,
				"agent");

		assertEquals(9L, history.getFeedbackID());
		assertEquals(false, history.getDeleted());
	}

	@Test
	void referralConstructorSetsTheInstituteDirectoryMapping() {
		BenCallServicesMappingHistory history = new BenCallServicesMappingHistory(10L, 7L, 1, 12L, "agent", false);

		assertEquals(12L, history.getInstituteDirMapID());
		assertEquals("agent", history.getCreatedBy());
	}

	@Test
	void nineteenColumnProjectionConstructorSetsEveryJoinedEntity() {
		BenCallServicesMappingHistory history = new BenCallServicesMappingHistory(BigInteger.ONE, 10L, 7L, 1,
				new SubServices(), 2, new CategoryDetails(), 3, new SubCategoryDetails(), 9L,
				new com.iemr.helpline1097.data.co.feedback.FeedbackDetails(), 12L, new DirectoryMapping(), "agent",
				CREATED, 4, new CategoryDetails(), 5, new SubCategoryDetails());

		assertEquals(BigInteger.ONE, history.getBenCall97ServiceMapID());
		assertEquals(2, history.getCategoryID());
		assertEquals(3, history.getSubCategoryID());
		assertEquals(9L, history.getFeedbackID());
		assertNotNull(history.getInstituteDirectoryMapping());
		assertEquals(CREATED, history.getCreatedDate());
		// coCategoryID is assigned twice, the second time from coSubCategoryID, and
		// coSubCategoryID itself is never assigned.
		assertEquals(5, history.getCoCategoryID());
		assertNull(history.getCoSubCategoryID());
		assertNotNull(history.getCoCategoryDetails());
	}

	@Test
	void tenColumnProjectionConstructorSetsTheIdColumnsOnly() {
		BenCallServicesMappingHistory history = new BenCallServicesMappingHistory(BigInteger.TEN, 10L, 7L, 1, 2, 3, 9L,
				12L, "agent", CREATED);

		assertEquals(BigInteger.TEN, history.getBenCall97ServiceMapID());
		assertEquals(9L, history.getFeedbackID());
		assertEquals(12L, history.getInstituteDirMapID());
	}

	@Test
	void informationProjectionConstructorSetsTheCategoryEntities() {
		BenCallServicesMappingHistory history = new BenCallServicesMappingHistory(BigInteger.ONE, 10L, 7L, 1,
				new SubServices(), 2, new CategoryDetails(), 3, new SubCategoryDetails(), "agent", CREATED);

		assertEquals(2, history.getCategoryID());
		assertNotNull(history.getCategoryDetails());
		assertNotNull(history.getSubCategoryDetails());
	}

	@Test
	void theEntityFirstProjectionConstructorAlsoFillsTheInformationColumns() {
		BenCallServicesMappingHistory history = new BenCallServicesMappingHistory(BigInteger.ONE, 10L, 7L, 1,
				new SubServices(), new CategoryDetails(), 2, new SubCategoryDetails(), 3, "agent", CREATED);

		assertEquals(2, history.getCategoryID());
		assertEquals(3, history.getSubCategoryID());
		assertNotNull(history.getCategoryDetails());
		assertNull(history.getCoCategoryID());
	}

	@Test
	void feedbackProjectionConstructorSetsTheFeedbackEntity() {
		BenCallServicesMappingHistory history = new BenCallServicesMappingHistory(BigInteger.ONE, 10L, 7L, 1,
				new SubServices(), 9L, new com.iemr.helpline1097.data.co.feedback.FeedbackDetails(), "agent", CREATED);

		assertEquals(9L, history.getFeedbackID());
		assertNotNull(history.getFeedbackDetails());
	}

	@Test
	void referralProjectionConstructorSetsTheDirectoryMappingEntity() {
		BenCallServicesMappingHistory history = new BenCallServicesMappingHistory(BigInteger.ONE, 10L, 7L, 1,
				new SubServices(), 12L, new DirectoryMapping(), "agent", CREATED);

		assertEquals(12L, history.getInstituteDirMapID());
		assertNotNull(history.getInstituteDirectoryMapping());
	}

	@Test
	void beneficiaryCallSummaryConstructorFlagsEveryServiceThatWasUsed() {
		BeneficiaryCall call = new BeneficiaryCall(1L, CREATED, "remark", 3L, 2L, 5L, CREATED, 4L, new CallType());

		assertEquals(1L, call.getInformationServices());
		assertEquals(1L, call.getFeedbackServices());
		assertEquals(1L, call.getReferralServices());
		assertEquals(1L, call.getCounsellingServices());
	}

	@Test
	void beneficiaryCallSummaryConstructorFlagsNoServiceWhenNoneWasUsed() {
		BeneficiaryCall call = new BeneficiaryCall(1L, CREATED, "remark", 0L, 0L, 0L, CREATED, 0L, new CallType());

		assertEquals(0L, call.getInformationServices());
		assertEquals(0L, call.getFeedbackServices());
		assertEquals(0L, call.getReferralServices());
		assertEquals(0L, call.getCounsellingServices());
	}
}
