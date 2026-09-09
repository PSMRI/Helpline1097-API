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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.iemr.helpline1097.data.co.beneficiary.M_Promoservice;
import com.iemr.helpline1097.data.co.beneficiary.User;
import com.iemr.helpline1097.data.co.calltype.M_Calltype;
import com.iemr.helpline1097.data.everwell.EverwellGuidelines;

class BeneficiaryAndEverwellEntityAccessorsTest {

	@BeforeEach
	void primeOutputMapper() {
		EntityAccessors.primeOutputMapper();
	}

	@Test
	void everyRemainingEntityRoundTripsItsAccessors() {
		EntityAccessors.assertRoundTrips(M_Promoservice.class, User.class, M_Calltype.class,
				EverwellGuidelines.class);
	}

	@Test
	void promoserviceConstructorSetsEveryReferralChannel() {
		M_Promoservice promoservice = new M_Promoservice("Y", "N", "Y", "N", "Y", "N", "N");

		assertEquals("Y", promoservice.getPamphlet());
		assertEquals("N", promoservice.getRadio());
		assertEquals("Y", promoservice.getTelevision());
		assertEquals("N", promoservice.getFamilyFriends());
		assertEquals("Y", promoservice.getHealthcareWorker());
		assertEquals("N", promoservice.getOthers());
		assertEquals("N", promoservice.getNotDisclosed());
		assertNull(promoservice.getId());
	}

	@Test
	void calltypeConstructorSetsTypeRemarksAndValidity() {
		M_Calltype calltype = new M_Calltype("Inbound", "handled", "valid");

		assertEquals("Inbound", calltype.getCallType());
		assertEquals("handled", calltype.getRemarks());
		assertEquals("valid", calltype.getInvalidType());
		assertNotNull(calltype.toString());
	}

	@Test
	void userExposesTheMinimalCachedIdentity() {
		User user = new User();

		user.setUserID(42L);
		user.setUserName("agent");
		user.setDeleted(false);

		assertEquals(42L, user.getUserID());
		assertEquals("agent", user.getUserName());
		assertEquals(false, user.getDeleted());
	}

	@Test
	void everwellGuidelinesCarriesTheDocumentAndItsAdherenceBand() {
		EverwellGuidelines guidelines = new EverwellGuidelines();

		guidelines.setId(3);
		guidelines.setCategory("> 95 adherence percentage");
		guidelines.setProviderServiceMapID(4);
		guidelines.setAdherencePercentage(97);
		guidelines.setFileName("guideline.pdf");
		guidelines.setFileContent("base64");

		assertEquals(3, guidelines.getId());
		assertEquals("> 95 adherence percentage", guidelines.getCategory());
		assertEquals(4, guidelines.getProviderServiceMapID());
		assertEquals(97, guidelines.getAdherencePercentage());
		assertEquals("guideline.pdf", guidelines.getFileName());
		assertEquals("base64", guidelines.getFileContent());
	}
}
