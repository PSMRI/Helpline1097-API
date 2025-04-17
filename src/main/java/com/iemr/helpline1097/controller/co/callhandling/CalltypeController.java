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
package com.iemr.helpline1097.controller.co.callhandling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iemr.helpline1097.data.co.calltype.M_Calltype;
import com.iemr.helpline1097.service.co.callhandling.IEMRCalltypeServiceImpl;
import com.iemr.helpline1097.utils.mapper.InputMapper;
import com.iemr.helpline1097.utils.response.OutputResponse;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.ws.rs.core.MediaType;

@RequestMapping(value = "/call", consumes = "application/json", produces = "application/json")
@RestController
public class CalltypeController {
	InputMapper inputMapper = new InputMapper();
	Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	@Autowired
	private IEMRCalltypeServiceImpl iEMRCalltypeServiceImpl;

	
	@Operation(summary = "Add type")
	@PostMapping(value = "/addType", produces = MediaType.APPLICATION_JSON, headers = "Authorization")
	public String addCallType(@RequestBody String request) {

		OutputResponse response = new OutputResponse();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			M_Calltype payload = objectMapper.readValue(request, M_Calltype.class);
			M_Calltype m_calltype = iEMRCalltypeServiceImpl.addCalltype(payload);

			if (m_calltype != null) {
				response.setResponse("callType Added");
			} else {
				response.setResponse("Failed to add callType");
			}
		} catch (Exception e) {
			logger.error("", e);
			response.setError(e);
		}
		return response.toString();
	}

	
	@Operation(summary = "Get type")
	@PostMapping(value = "/getTypes/{id}", produces = MediaType.APPLICATION_JSON, headers = "Authorization")
	public String getAllCallTypes(@PathVariable("id") int id) {

		OutputResponse response = new OutputResponse();
		try {
			M_Calltype m_calltypes = iEMRCalltypeServiceImpl.getAllCalltypes(id);

			response.setResponse(m_calltypes.toString());
		} catch (Exception e) {
			logger.error("", e);
			response.setError(e);
		}
		return response.toString();
	}

}
