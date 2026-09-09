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
package com.iemr.helpline1097.controller.co.beneficiary;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.iemr.helpline1097.data.co.beneficiary.M_Promoservice;
import com.iemr.helpline1097.service.co.beneficiary.BenInformationCounsellingFeedbackReferralImpl;
import com.iemr.helpline1097.service.co.beneficiary.IEMRPromoserviceDetailsServiceImpl;

@ExtendWith(MockitoExtension.class)
class BeneficiaryControllerTest {

	private static final String MAPPING_REQUEST = "[{\"beneficiaryRegID\":10,\"subCategoryID\":2}]";

	@Mock
	private IEMRPromoserviceDetailsServiceImpl promoserviceDetailsService;

	@Mock
	private BenInformationCounsellingFeedbackReferralImpl benInformationService;

	private BeneficiaryController controller;

	@BeforeEach
	void setUp() {
		controller = new BeneficiaryController();
		controller.setiEMRPromoserviceDetailsServiceImpl(promoserviceDetailsService);
		controller.setBenInformationCounsellingFeedbackReferralImpl(benInformationService);
	}

	@Test
	void addPromoServiceDetailsReportsSuccessWhenSaved() {
		when(promoserviceDetailsService.addPromoServiceDetail(any(M_Promoservice.class)))
				.thenReturn(new M_Promoservice());

		assertTrue(controller.addPromoServiceDetails("{\"radio\":\"Y\"}").contains("PromoServiceDetails Added"));
	}

	@Test
	void addPromoServiceDetailsReportsFailureWhenServiceReturnsNull() {
		when(promoserviceDetailsService.addPromoServiceDetail(any(M_Promoservice.class))).thenReturn(null);

		assertTrue(controller.addPromoServiceDetails("{\"radio\":\"Y\"}")
				.contains("Failed to add PromoServiceDetails"));
	}

	@Test
	void addPromoServiceDetailsReturnsErrorOnMalformedJson() {
		assertTrue(controller.addPromoServiceDetails("{oops}").contains("statusCode"));
	}

	@Test
	void saveBenCalServiceCatSubcatMappingDelegatesToService() {
		when(benInformationService.saveBenCallServiceCatSubCat(any())).thenReturn("[]");

		String response = controller.saveBenCalServiceCatSubcatMapping(MAPPING_REQUEST);

		assertTrue(response.contains("\"statusCode\":200"));
		verify(benInformationService).saveBenCallServiceCatSubCat(any());
	}

	@Test
	void saveBenCalServiceCatSubcatMappingReturnsErrorOnMalformedJson() {
		assertTrue(controller.saveBenCalServiceCatSubcatMapping("{oops}").contains("statusCode"));
	}

	@Test
	void saveBenCalServiceCOCatSubcatMappingDelegatesToService() {
		when(benInformationService.saveBenCallServiceCOCatSubCat(any())).thenReturn("[]");

		String response = controller.saveBenCalServiceCOCatSubcatMapping(MAPPING_REQUEST);

		assertTrue(response.contains("\"statusCode\":200"));
		verify(benInformationService).saveBenCallServiceCOCatSubCat(any());
	}

	@Test
	void saveBenCalServiceCOCatSubcatMappingReturnsErrorOnMalformedJson() {
		assertTrue(controller.saveBenCalServiceCOCatSubcatMapping("{oops}").contains("statusCode"));
	}

	@Test
	void saveBenCalReferralMappingDelegatesToService() {
		when(benInformationService.saveBenCalReferralMapping(anyString())).thenReturn("[]");

		String response = controller.saveBenCalReferralMapping(MAPPING_REQUEST);

		assertTrue(response.contains("\"statusCode\":200"));
		verify(benInformationService).saveBenCalReferralMapping(MAPPING_REQUEST);
	}

	@Test
	void saveBenCalReferralMappingReturnsErrorOnFailure() {
		when(benInformationService.saveBenCalReferralMapping(anyString()))
				.thenThrow(new RuntimeException("referral failed"));

		assertTrue(controller.saveBenCalReferralMapping(MAPPING_REQUEST).contains("referral failed"));
	}
}
