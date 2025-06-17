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

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iemr.helpline1097.data.co.calltype.M_Calltype;
import com.iemr.helpline1097.data.co.feedback.FeedbackDetails;
import com.iemr.helpline1097.service.co.feedback.FeedbackService;
import com.iemr.helpline1097.service.co.feedback.FeedbackServiceImpl;
import com.iemr.helpline1097.utils.mapper.InputMapper;
import com.iemr.helpline1097.utils.response.OutputResponse;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.MediaType;

@RequestMapping(value = "/co", consumes = "application/json", produces = "application/json")
@RestController
public class FeedbackController {
	private static final String request = null;
	private InputMapper inputMapper = new InputMapper();
	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	/**
	 * feedback service
	 */
	private FeedbackService feedbackService;

	/***
	 * FeedbackServiceImpl
	 */
	private FeedbackServiceImpl feedbackServiceImpl;

	@Autowired
	public void setFeedbackServiceImpl(FeedbackServiceImpl feedbackServiceImpl) {
		this.feedbackServiceImpl = feedbackServiceImpl;
	}

	@Autowired
	public void setFeedbackService(FeedbackService feedbackService) {

		this.feedbackService = feedbackService;
	}

	@Operation(summary = "Get feedback list")
	@PostMapping(value = "/getfeedbacklist", produces = MediaType.APPLICATION_JSON, headers = "Authorization")
	public String feedbackReuest(@RequestBody String request) {
		OutputResponse response = new OutputResponse();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			FeedbackDetails feedbackDetails = objectMapper.readValue(request, FeedbackDetails.class);
			List<FeedbackDetails> feedbackList = feedbackService
					.getFeedbackRequests(feedbackDetails.getBeneficiaryRegID());
			response.setResponse(feedbackList.toString());
		} catch (Exception e) {
			logger.error(e.getMessage());
			response.setError(e);
		}
		return response.toString();
	}

	@Operation(summary = "Get feedback by post")
	@PostMapping(value = "/getfeedback/{feedbackID}", produces = MediaType.APPLICATION_JSON, headers = "Authorization")
	public String getFeedbackByPost(@PathVariable("feedbackID") int feedbackID) {
		OutputResponse response = new OutputResponse();
		try {
			logger.info("" + feedbackID);
			List<FeedbackDetails> savedDetails = feedbackService.getFeedbackRequests(feedbackID);
			response.setResponse(savedDetails.toString());
		} catch (Exception e) {
			logger.error("", e);
			response.setError(e);
		}
		return response.toString();
	}

	@Operation(summary = "Save beneficiary feedback")
	@PostMapping(value = "/saveBenFeedback", produces = MediaType.APPLICATION_JSON, headers = "Authorization")
	public String saveBenFeedback(@RequestBody String feedbackRequest, HttpServletRequest request) {
		OutputResponse response = new OutputResponse();
		try {
			String path = request.getRequestURI();
			String savedFeedback = feedbackServiceImpl.saveFeedbackFromCustomer(feedbackRequest, request);
			if (savedFeedback != null) {
				logger.info("save feedback" + savedFeedback);
			}
			response.setResponse(savedFeedback);
		} catch (Exception e) {
			logger.error("saveBenFeedback failed with error " + e.getMessage(), e);
			response.setError(e);
		}

		return response.toString();
	}
}
