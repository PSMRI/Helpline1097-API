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
package com.iemr.helpline1097.controller.co.feedback;

import jakarta.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iemr.helpline1097.service.co.feedback.FeedbackSeverityService;
import com.iemr.helpline1097.service.co.feedback.FeedbackTypeService;
import com.iemr.helpline1097.utils.mapper.InputMapper;
import com.iemr.helpline1097.utils.response.OutputResponse;

import io.swagger.v3.oas.annotations.Operation;

@RequestMapping(value = "/feedback", consumes = "application/json", produces = "application/json")
@RestController
public class FeedbackSeverity {
	InputMapper inputMapper = new InputMapper();
	Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	private FeedbackSeverityService feedbackSeverityService;
	private FeedbackTypeService feedbackTypeService;

	@Autowired
	public void SetFeedbackSeverityService(FeedbackSeverityService feedbackSeverityService) {
		this.feedbackSeverityService = feedbackSeverityService;
	}

	
	@Operation(summary = "Get feedback type")
	@PostMapping(value = "/getseverity", produces = MediaType.APPLICATION_JSON, headers = "Authorization")
	public String getSeverity() {

		OutputResponse response = new OutputResponse();
		try {

			response.setResponse(feedbackSeverityService.getActiveFeedbackSeverity().toString());
		} catch (Exception e) {
			logger.error("", e);
			response.setError(e);
		}
		return response.toString();
	}

	
	@Operation(summary = "Get feedback type")
	@PostMapping(value = "/gettype", produces = MediaType.APPLICATION_JSON, headers = "Authorization")
	public String getFeedbackType() {

		OutputResponse response = new OutputResponse();
		try {

			response.setResponse(feedbackTypeService.getActiveFeedbackTypes().toString());
		} catch (Exception e) {
			logger.error("", e);
			response.setError(e);
		}
		return response.toString();
	}

}
