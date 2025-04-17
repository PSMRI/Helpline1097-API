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
package com.iemr.helpline1097.controller.co.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iemr.helpline1097.service.co.services.InstitutionService;
import com.iemr.helpline1097.utils.mapper.InputMapper;
import com.iemr.helpline1097.utils.response.OutputResponse;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.ws.rs.core.MediaType;

@RestController
public class InstitutionController {
	InputMapper inputMapper = new InputMapper();
	Logger logger = LoggerFactory.getLogger(this.getClass().getName());


	@Operation(summary = "Get institutions")
	@GetMapping(value = "/api/helpline1097/co/get/institutions", produces = MediaType.APPLICATION_JSON, headers = "Authorization")
	public String getInstitutions() {
		OutputResponse response = new OutputResponse();
		try {
			response.setResponse(institutionService.getInstitutions().toString());
		} catch (Exception e) {
			logger.error("", e);
			response.setError(e);
		}
		return response.toString();
	}

	/**
	 * Institution service
	 */
	private InstitutionService institutionService;

	@Autowired
	public void setInstitutionService(InstitutionService institutionService) {

		this.institutionService = institutionService;
	}
}
