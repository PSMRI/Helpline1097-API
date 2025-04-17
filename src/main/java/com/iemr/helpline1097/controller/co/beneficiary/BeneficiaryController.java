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

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iemr.helpline1097.data.co.beneficiary.M_Promoservice;
import com.iemr.helpline1097.data.co.beneficiarycall.BenCallServicesMappingHistory;
import com.iemr.helpline1097.data.co.beneficiarycall.BeneficiaryCall;
import com.iemr.helpline1097.service.co.beneficiary.BenInformationCounsellingFeedbackReferralImpl;
import com.iemr.helpline1097.service.co.beneficiary.IEMRPromoserviceDetailsServiceImpl;
import com.iemr.helpline1097.utils.mapper.InputMapper;
import com.iemr.helpline1097.utils.response.OutputResponse;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.ws.rs.core.MediaType;

@RequestMapping(value = "/iEMR", consumes = "application/json", produces = "application/json")
@RestController
public class BeneficiaryController {
	InputMapper inputMapper = new InputMapper();
	Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	private IEMRPromoserviceDetailsServiceImpl iEMRPromoserviceDetailsServiceImpl;
	private BenInformationCounsellingFeedbackReferralImpl benInformationCounsellingFeedbackReferralImpl;

	@Autowired
	public void setiEMRPromoserviceDetailsServiceImpl(
			IEMRPromoserviceDetailsServiceImpl iEMRPromoserviceDetailsServiceImpl) {
		this.iEMRPromoserviceDetailsServiceImpl = iEMRPromoserviceDetailsServiceImpl;
	}

	@Autowired
	public void setBenInformationCounsellingFeedbackReferralImpl(
			BenInformationCounsellingFeedbackReferralImpl benInformationCounsellingFeedbackReferralImpl) {
		this.benInformationCounsellingFeedbackReferralImpl = benInformationCounsellingFeedbackReferralImpl;
	}

	
	@Operation(summary = "Add promo service detail")
	@PostMapping(value = "add/promoServiceDetails", headers = "Authorization")
	public String addPromoServiceDetails(@RequestBody String request) {

		OutputResponse response = new OutputResponse();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			M_Promoservice m_promoservice = objectMapper.readValue(request, M_Promoservice.class);
			M_Promoservice m_Promoservice = iEMRPromoserviceDetailsServiceImpl.addPromoServiceDetail(m_promoservice);

			if (m_Promoservice != null) {
				response.setResponse("PromoServiceDetails Added");
			} else {
				response.setResponse("Failed to add PromoServiceDetails");
			}
		} catch (Exception e) {
			logger.error("", e);
			response.setError(e);
		}
		return response.toString();

	}

	
	@Operation(summary = "Save information requested by the beneficiary during call")
	@PostMapping(value = "/saveBenCalServiceCatSubcatMapping", headers = "Authorization")
	public String saveBenCalServiceCatSubcatMapping(@RequestBody String request) {
		OutputResponse response = new OutputResponse();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			BenCallServicesMappingHistory[] benCallServicesMappingHistories =objectMapper.readValue(request, BenCallServicesMappingHistory[].class);
			
			Iterable<BenCallServicesMappingHistory> benCallServicesMappingHistory = Arrays
					.asList(benCallServicesMappingHistories);

			response.setResponse(benInformationCounsellingFeedbackReferralImpl
					.saveBenCallServiceCatSubCat(benCallServicesMappingHistory));
		} catch (Exception e) {
			logger.error("", e);
			response.setError(e);
		}
		return response.toString();

	}

	
	@Operation(summary = "Save counselling requested by beneficiary")
	@PostMapping(value = "/saveBenCalServiceCOCatSubcatMapping", headers = "Authorization")
	public String saveBenCalServiceCOCatSubcatMapping(@RequestBody String request) {
		OutputResponse response = new OutputResponse();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			BenCallServicesMappingHistory[] benCallServicesMappingHistories =objectMapper.readValue(request, BenCallServicesMappingHistory[].class);
			
			Iterable<BenCallServicesMappingHistory> benCallServicesMappingHistory = Arrays
					.asList(benCallServicesMappingHistories);

			response.setResponse(benInformationCounsellingFeedbackReferralImpl
					.saveBenCallServiceCOCatSubCat(benCallServicesMappingHistory));
		} catch (Exception e) {
			logger.error("", e);
			response.setError(e);
		}
		return response.toString();

	}

	/**
	 * @param referralRequest
	 * @return
	 */
	
	@Operation(summary = "Save beneficiary call referral mapping")
	@PostMapping(value = "/saveBenCalReferralMapping", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON, headers = "Authorization")
	public String saveBenCalReferralMapping(@RequestBody String referralRequest) {
		OutputResponse response = new OutputResponse();
		logger.debug("saveBenCalReferralMapping request is " + referralRequest);
		try {

			response.setResponse(
					benInformationCounsellingFeedbackReferralImpl.saveBenCalReferralMapping(referralRequest));
		} catch (Exception e) {
			logger.error("saveBenCalReferralMapping failed with exception " + e.getMessage(), e);
			response.setError(e);
		}
		logger.debug("saveBenCalReferralMapping response is " + response);
		return response.toString();
	}

}
