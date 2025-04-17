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
package com.iemr.helpline1097.controller.beneficiarycall;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iemr.helpline1097.data.co.beneficiarycall.BenCallServicesMappingHistory;
import com.iemr.helpline1097.data.co.beneficiarycall.BeneficiaryCall;
import com.iemr.helpline1097.service.co.beneficiarycall.BeneficiaryCallService;
import com.iemr.helpline1097.service.co.beneficiarycall.ServicesHistoryService;
import com.iemr.helpline1097.utils.mapper.InputMapper;
import com.iemr.helpline1097.utils.response.OutputResponse;

import io.lettuce.core.dynamic.annotation.Param;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.ws.rs.core.MediaType;

@RestController
@RequestMapping(value = "/services", consumes = "application/json", produces = "application/json")
public class Service1097HistoryController {
	InputMapper inputMapper = new InputMapper();
	Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	private ServicesHistoryService servicesHistoryService;
	private BeneficiaryCallService beneficiaryCallService;

	@Autowired
	public void setService1097HistoryService(ServicesHistoryService servicesHistoryService) {

		this.servicesHistoryService = servicesHistoryService;
	}

	@Autowired
	public void setBeneficiaryCallService(BeneficiaryCallService beneficiaryCallService) {

		this.beneficiaryCallService = beneficiaryCallService;
	}

	
	@Operation(summary = "Get service history")
	@PostMapping(value = "/getHistory", produces = MediaType.APPLICATION_JSON, headers = "Authorization")
	public String getServiceHistory(
			@Param(value = "{\"beneficiaryRegID\":\"Integer - Beneficiary registration ID\", "
					+ "\"calledServiceID\":\"provider Service Map ID as integer\"}") @RequestBody String beneficiaryRequest) {
		OutputResponse response = new OutputResponse();
		try {

			List<BenCallServicesMappingHistory> serviceHistoryList = servicesHistoryService
					.getServiceHistory(beneficiaryRequest);
			response.setResponse(serviceHistoryList.toString());
		} catch (Exception e) {
			logger.error("", e);
			response.setError(e);
		}
		return response.toString();
	}

	
	@Operation(summary = "Get beneficiary call history")
	@PostMapping(value = "/getBeneficiaryCallHistory", produces = MediaType.APPLICATION_JSON, headers = "Authorization")
	public String getBeneficiaryCallHistory(
			@Param(value = "{\"beneficiaryRegID\":\"Integer - Beneficiary registration ID\", "
					+ "\"calledServiceID\":\"provider Service Map ID as integer\"}") @RequestBody String beneficiaryRequest) {
		OutputResponse response = new OutputResponse();
		try {

			List<BenCallServicesMappingHistory> serviceHistoryList = servicesHistoryService
					.getServiceHistory(beneficiaryRequest);
			response.setResponse(serviceHistoryList.toString());
		} catch (Exception e) {
			logger.error("", e);
			response.setError(e);
		}
		return response.toString();
	}

	
	@Operation(summary = "Fetch service history")
	@PostMapping(value = "/setHistory", produces = MediaType.APPLICATION_JSON, headers = "Authorization")
	public String setServiceHistory(@RequestBody String request) {
		OutputResponse response = new OutputResponse();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			BenCallServicesMappingHistory serviceHistoryDetails = objectMapper.readValue(request, BenCallServicesMappingHistory.class);
			
			BenCallServicesMappingHistory savedObj = servicesHistoryService.createServiceHistory(serviceHistoryDetails);
			response.setResponse(savedObj.toString());
		} catch (Exception e) {
			logger.error("", e);
			response.setError(e);
		}
		return response.toString();
	}

	
	@Operation(summary = "Get call summary")
	@PostMapping(value = "/getCallSummary", produces = MediaType.APPLICATION_JSON, headers = "Authorization")
	public String getCallSummary(
			@Param(value = "{\"benCallID\":\"Integer - current call ID\"}") @RequestBody String request) {
		OutputResponse response = new OutputResponse();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			BeneficiaryCall call = objectMapper.readValue(request, BeneficiaryCall.class);
			List<BeneficiaryCall> callHistoryList = beneficiaryCallService.getCallSummaryByCallID(call.getBenCallID());
			response.setResponse(callHistoryList.toString());
		} catch (Exception e) {
			logger.error("", e);
			response.setError(e);
		}
		return response.toString();
	}

	
	@Operation(summary= "Get beneficiary call history")
	@PostMapping(value = "/getBeneficiaryCallsHistory", produces = MediaType.APPLICATION_JSON, headers = "Authorization")
	public String getBeneficiaryCallsHistory(
			@Param(value = "{\"beneficiaryRegID\":\"Integer - Beneficiary registration ID\", "
					+ "\"calledServiceID\":\"provider Service Map ID as integer\"}") @RequestBody String request) {
		OutputResponse response = new OutputResponse();
		try {
			JSONObject requestObject = new JSONObject(request);
			int pageNo = requestObject.has("pageNo") ? (requestObject.getInt("pageNo") - 1) : 0;
			int rows = requestObject.has("rowsPerPage") ? requestObject.getInt("rowsPerPage") : 1000;
			ObjectMapper objectMapper = new ObjectMapper();
			BeneficiaryCall call = objectMapper.readValue(request, BeneficiaryCall.class);
			List<BeneficiaryCall> callHistoryList;
			if (call.getCalledServiceID() != null) {
				callHistoryList = beneficiaryCallService.getBeneficiaryCallsHistory(call.getBeneficiaryRegID(),
						call.getCalledServiceID(), pageNo, rows);
			} else {
				callHistoryList = beneficiaryCallService.getBeneficiaryCallsHistory(call.getBeneficiaryRegID(), pageNo,
						rows);
			}
			response.setResponse(callHistoryList.toString());
		} catch (JSONException e) {
			logger.error("", e);
			response.setError(e);
		} catch (Exception e) {
			logger.error("", e);
			response.setError(e);
		}

		return response.toString();
	}

	
	@Operation(summary = "Get referral history")
	@PostMapping(value = "/getReferralsHistory", produces = MediaType.APPLICATION_JSON, headers = "Authorization")
	public String getReferralsHistory(
			@Param(value = "{\"beneficiaryRegID\":\"Integer - Beneficiary registration ID\", "
					+ "\"calledServiceID\":\"provider Service Map ID as integer\"}") @RequestBody String request) {
		OutputResponse response = new OutputResponse();
		try {

			List<BenCallServicesMappingHistory> callHistoryList = servicesHistoryService.getReferralsHistory(request);
			response.setResponse(callHistoryList.toString());
		} catch (JSONException e) {
			logger.error("", e);
			response.setError(e);
		} catch (Exception e) {
			logger.error("", e);
			response.setError(e);
		}

		return response.toString();
	}

	
	@Operation(summary = "Get feedback history")
	@PostMapping(value = "/getFeedbacksHistory", produces = MediaType.APPLICATION_JSON, headers = "Authorization")
	public String getFeedbacksHistory(
			@Param(value = "{\"beneficiaryRegID\":\"Integer - Beneficiary registration ID\", "
					+ "\"calledServiceID\":\"provider Service Map ID as integer\"}") @RequestBody String request) {
		OutputResponse response = new OutputResponse();
		try {

			List<BenCallServicesMappingHistory> callHistoryList = servicesHistoryService.getFeedbacksHistory(request);
			response.setResponse(callHistoryList.toString());
		} catch (JSONException e) {
			logger.error("", e);
			response.setError(e);
		} catch (Exception e) {
			logger.error("", e);
			response.setError(e);
		}

		return response.toString();
	}

	
	@Operation(summary = "Get information history")
	@PostMapping(value = "/getInformationsHistory", produces = MediaType.APPLICATION_JSON, headers = "Authorization")
	public String getInformationsHistory(
			@Param(value = "{\"beneficiaryRegID\":\"Integer - Beneficiary registration ID\"}, "
					+ "\"calledServiceID\":\"provider Service Map ID as integer\"") @RequestBody String request) {
		OutputResponse response = new OutputResponse();
		try {

			List<BenCallServicesMappingHistory> callHistoryList = servicesHistoryService
					.getInformationsHistory(request);
			response.setResponse(callHistoryList.toString());
		} catch (JSONException e) {
			logger.error("", e);
			response.setError(e);
		} catch (Exception e) {
			logger.error("", e);
			response.setError(e);
		}

		return response.toString();
	}

	
	@Operation(summary = "Get counselling history")
	@PostMapping(value = "/getCounsellingsHistory")
	public String getCounsellingsHistory(
			@Param(value = "{\"beneficiaryRegID\":\"Integer - Beneficiary registration ID\", "
					+ "\"calledServiceID\":\"provider Service Map ID as integer\"}") @RequestBody String request) {
		OutputResponse response = new OutputResponse();
		try {

			List<BenCallServicesMappingHistory> callHistoryList = servicesHistoryService
					.getCounsellingsHistory(request);
			response.setResponse(callHistoryList.toString());
		} catch (JSONException e) {
			logger.error("", e);
			response.setError(e);
		} catch (Exception e) {
			logger.error("", e);
			response.setError(e);
		}

		return response.toString();
	}

	
	@Operation(summary = "Get case report")
	@PostMapping(value = "/getCaseSheet", produces = MediaType.APPLICATION_JSON, headers = "Authorization")
	public String getCaseSheet(
			@Param(value = "{\"benCallID\":\"Integer - current call ID\"}") @RequestBody String request) {
		OutputResponse response = new OutputResponse();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			BeneficiaryCall call = objectMapper.readValue(request, BeneficiaryCall.class);
			List<BenCallServicesMappingHistory> callHistoryList = servicesHistoryService
					.getCallSummaryV1(call.getBenCallID());
			response.setResponse(callHistoryList.toString());
		} catch (Exception e) {
			logger.error("getCaseSheet failed with error" + e.getMessage(), e);
			response.setError(e);
		}
		return response.toString();
	}

}
