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

import java.sql.Timestamp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.iemr.helpline1097.data.co.feedback.Designation;
import com.iemr.helpline1097.data.co.feedback.FeedbackDetails;
import com.iemr.helpline1097.data.co.feedback.FeedbackRequestDetails;
import com.iemr.helpline1097.data.co.feedback.FeedbackSeverity;
import com.iemr.helpline1097.data.co.feedback.FeedbackStatus;
import com.iemr.helpline1097.data.co.feedback.FeedbackType;
import com.iemr.helpline1097.data.co.feedback.InstituteType;
import com.iemr.helpline1097.data.co.feedback.Severity;
import com.iemr.helpline1097.data.co.feedbackRequest.FeedbackRequest;
import com.iemr.helpline1097.data.co.feedbackResponse.FeedbackResponse;

class FeedbackEntityAccessorsTest {

	@BeforeEach
	void primeOutputMapper() {
		EntityAccessors.primeOutputMapper();
	}

	@Test
	void everyFeedbackEntityRoundTripsItsAccessors() {
		EntityAccessors.assertRoundTrips(FeedbackDetails.class, FeedbackRequestDetails.class, FeedbackSeverity.class,
				FeedbackType.class, FeedbackRequest.class, FeedbackResponse.class);
	}

	@Test
	void theLookupEntitiesAreInstantiableAndPrintable() {
		assertNotNull(new Designation().toString());
		assertNotNull(new FeedbackStatus().toString());
		assertNotNull(new InstituteType().toString());
		assertNotNull(new Severity().toString());
	}

	@Test
	void feedbackSeverityConstructorSetsIdAndName() {
		FeedbackSeverity severity = new FeedbackSeverity(1, "High");

		assertEquals(1, severity.getSeverityID());
		assertEquals("High", severity.getSeverityTypeName());
	}

	@Test
	void feedbackTypeConstructorSetsIdAndName() {
		FeedbackType type = new FeedbackType(2, "Complaint");

		assertEquals(2, type.getFeedbackTypeID());
		assertEquals("Complaint", type.getFeedbackTypeName());
	}

	@Test
	void feedbackDetailsSummaryConstructorSetsTheProjectedColumns() {
		FeedbackDetails feedback = new FeedbackDetails(5L, (short) 1, (short) 2, (short) 3, "poor service",
				"FE/1/01012024/5");

		assertEquals(5L, feedback.getFeedbackID());
		assertEquals((short) 1, feedback.getSeverityID());
		assertEquals((short) 2, feedback.getFeedbackTypeID());
		assertEquals((short) 3, feedback.getFeedbackStatusID());
		assertEquals("poor service", feedback.getFeedback());
		assertEquals("FE/1/01012024/5", feedback.getCreatedBy());
		assertNotNull(feedback.toString());
	}

	@Test
	void feedbackDetailsFullConstructorSetsEveryPersistedColumn() {
		Timestamp availedOn = new Timestamp(1_700_000_000_000L);

		FeedbackDetails feedback = new FeedbackDetails(5L, 7L, 3, (short) 1, (short) 2, (short) 3, "poor service",
				10L, 4, 9, "9999999999", availedOn, false, "agent", availedOn, "supervisor", availedOn, true);

		assertEquals(5L, feedback.getFeedbackID());
		assertEquals(7L, feedback.getInstitutionID());
		assertEquals(3, feedback.getDesignationID());
		assertEquals(10L, feedback.getBeneficiaryRegID());
		assertEquals(4, feedback.getServiceID());
		assertEquals(9, feedback.getUserID());
		assertEquals("9999999999", feedback.getsMSPhoneNo());
		assertEquals(availedOn, feedback.getServiceAvailDate());
		assertEquals(false, feedback.getDeleted());
		assertEquals("agent", feedback.getCreatedBy());
		assertEquals("supervisor", feedback.getModifiedBy());
		assertEquals(true, feedback.getBeneficiaryConsent());
	}

	@Test
	void feedbackRequestConstructorSetsEveryAuditColumn() {
		Timestamp when = new Timestamp(1_700_000_000_000L);

		FeedbackRequest request = new FeedbackRequest(4L, "escalated", 9, "please review", false, "agent", when,
				"supervisor", when);

		assertEquals(4L, request.getFeedbackRequestID());
		assertEquals("escalated", request.getFeedbackSupSummary());
		assertEquals(9, request.getSupUserID());
		assertEquals("please review", request.getComments());
		assertEquals(false, request.getDeleted());
		assertEquals("agent", request.getCreatedBy());
		assertEquals(when, request.getCreatedDate());
		assertEquals("supervisor", request.getModifiedBy());
		assertEquals(when, request.getLastModDate());
	}

	@Test
	void feedbackResponseConstructorSetsEveryAuditColumn() {
		Timestamp when = new Timestamp(1_700_000_000_000L);

		FeedbackResponse response = new FeedbackResponse(6L, 4L, "resolved", 9, "closed", false, "agent", when,
				"supervisor", when);

		assertEquals(6L, response.getFeedbackResponseID());
		assertEquals(4L, response.getFeedbackRequestID());
		assertEquals("resolved", response.getResponseSummary());
		assertEquals(9, response.getAuthUserID());
		assertEquals("closed", response.getComments());
		assertEquals(false, response.getDeleted());
		assertEquals("agent", response.getCreatedBy());
		assertEquals(when, response.getCreatedDate());
		assertEquals("supervisor", response.getModifiedBy());
		assertEquals(when, response.getLastModDate());
	}
}
