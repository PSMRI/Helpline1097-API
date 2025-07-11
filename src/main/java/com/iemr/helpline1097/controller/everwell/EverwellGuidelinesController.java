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
package com.iemr.helpline1097.controller.everwell;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.iemr.helpline1097.service.everwell.EverwellService;
import com.iemr.helpline1097.utils.mapper.InputMapper;
import com.iemr.helpline1097.utils.response.OutputResponse;

import io.lettuce.core.dynamic.annotation.Param;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.ws.rs.core.MediaType;

@RestController
public class EverwellGuidelinesController {
	InputMapper inputMapper = new InputMapper();
	Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	@Autowired
	private EverwellService everwellService;

	@Operation(summary = "Save everwell guidelines")
	@PostMapping(value = "/saveEverwellGuidelines", produces = MediaType.APPLICATION_JSON, headers = "Authorization")
	public String saveEverwellGuidelines(
			@Param(value = "{\"providerServiceMapID\":\"Integer\",\"Category\":\\\"String\\\","
					+ "\"fileContent\":\"String\"\"\\\"GuidelineName\\\":\\\"String\\\"\"\\\"GuidelineDescription\\\":\\\"String\\\"}") @RequestBody String request) {

		OutputResponse response = new OutputResponse();
		try {
			String result = everwellService.saveGuidelines(request);
			response.setResponse(result);
		} catch (Exception e) {
			logger.error("Error while saving everwell guidelines ", e);
			response.setError(e);
		}
		return response.toString();
	}

	@Operation(summary = "Fetch everwell guidelines")
	@PostMapping(value = "/fetchEverwellGuidelines", produces = MediaType.APPLICATION_JSON, headers = "Authorization")
	public String fetchEverwellGuidelines(
			@Param(value = "{\"providerServiceMapID\":\"Integer\",\"adherencePercentage\":\\\"Integer\\\"}") @RequestBody String request) {

		OutputResponse response = new OutputResponse();
		try {
			String result = everwellService.fetchGuidelines(request);
			response.setResponse(result);
		} catch (Exception e) {
			logger.error("Error while fetching everwell guidelines ", e);
			response.setError(e);
		}
		return response.toString();
	}

	@Operation(summary = "Delete everwell guidelines")
	@PostMapping(value = "/deleteEverwellGuidelines", produces = MediaType.APPLICATION_JSON, headers = "Authorization")
	public String deleteEverwellGuidelines(
			@Param(value = "{\"Id\":\"Integer\",\"modifiedBy\":\\\"String\\\"}") @RequestBody String request) {
		OutputResponse response = new OutputResponse();
		try {
			String result = everwellService.deleteGuideline(request);
			response.setResponse(result);
		} catch (Exception e) {
			logger.error("Error while fetching everwell guidelines ", e);
			response.setError(e);
		}
		return response.toString();
	}
}
