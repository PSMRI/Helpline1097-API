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
package com.iemr.helpline1097.service.co.feedback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iemr.helpline1097.data.co.beneficiarycall.BenCallServicesMappingHistory;
import com.iemr.helpline1097.data.co.feedback.FeedbackDetails;
import com.iemr.helpline1097.data.co.feedback.FeedbackRequestDetails;
import com.iemr.helpline1097.repository.co.beneficiary.BenCalServiceCatSubcatMappingRepo;
import com.iemr.helpline1097.repository.co.feedback.FeedbackRepository;
import com.iemr.helpline1097.utils.config.ConfigProperties;
import com.iemr.helpline1097.utils.exception.IEMRException;
import com.iemr.helpline1097.utils.http.HttpUtils;
import com.iemr.helpline1097.utils.mapper.InputMapper;
import com.iemr.helpline1097.utils.mapper.OutputMapper;
import com.iemr.helpline1097.utils.response.OutputResponse;

@Service
public class FeedbackServiceImpl implements FeedbackService {

	private Logger logger = LoggerFactory.getLogger(FeedbackServiceImpl.class);

	private BenCalServiceCatSubcatMappingRepo benCalServiceCatSubcatMappingRepo;

	@Autowired
	public void getBenCalServiceCatSubcatMappingRepo(
			BenCalServiceCatSubcatMappingRepo benCalServiceCatSubcatMappingRepo) {
		this.benCalServiceCatSubcatMappingRepo = benCalServiceCatSubcatMappingRepo;
	}

	/**
	 * Feedback Repository
	 */
	private FeedbackRepository feedbackRepository;

	@Autowired
	public void setFeedbackRepository(FeedbackRepository feedbackRepository) {

		this.feedbackRepository = feedbackRepository;
	}

	private InputMapper inputMapper = new InputMapper();

	private ConfigProperties properties;

	@Autowired
	public void setProperties(ConfigProperties properties) {
		this.properties = properties;
	}

	@Override
	public List<FeedbackDetails> getFeedbackRequests(long id) {

		List<FeedbackDetails> feedbackList = new ArrayList<FeedbackDetails>();
		ArrayList<Objects[]> lists = feedbackRepository.findByBeneficiaryID(id);

		for (Object[] objects : lists) {
			if (objects != null && objects.length >= 6) {
				feedbackList.add(new FeedbackDetails((Long) objects[0], (Short) objects[1], (Short) objects[2],
						(Short) objects[3], (String) objects[4], (String) objects[5]));
			}
		}
		return feedbackList;
	}

	@Override
	public List<FeedbackDetails> getFeedbackRequest(long id) {

		List<FeedbackDetails> feedbackList = new ArrayList<FeedbackDetails>();
		ArrayList<Objects[]> lists = feedbackRepository.findByFeedbackID(id);

		for (Object[] objects : lists) {
			if (objects != null && objects.length >= 6) {
				feedbackList.add(new FeedbackDetails((Long) objects[0], (Short) objects[1], (Short) objects[2],
						(Short) objects[3], (String) objects[4], (String) objects[5]));
			}
		}
		return feedbackList;
	}

	@Override
	public FeedbackDetails createFeedback(FeedbackDetails feedbackDetails) {

		List<FeedbackRequestDetails> feedbackRequestList = feedbackDetails.getFeedbackRequestDetails();

		for (FeedbackRequestDetails frd : feedbackRequestList) {

			frd.setFeedback(feedbackDetails);
		}

		feedbackDetails.setFeedbackRequestDetails(feedbackRequestList);

		return feedbackRepository.save(feedbackDetails);
	}

	@Override
	public String updateFeedback(FeedbackDetails feedbackDetails) {
		return null;
	}

	/***
	 * @param request
	 * @throws Exception
	 */
	@Override
	public String saveFeedbackFromCustomer(String feedbackDetails, HttpServletRequest request) throws Exception {
		OutputResponse output = new OutputResponse();

		FeedbackDetails[] feedbacks = inputMapper.gson().fromJson(feedbackDetails, FeedbackDetails[].class);
		for (FeedbackDetails feedback : feedbacks) {
			if (feedback.getSubServiceID() == null) {
				throw new Exception("Sub service is not configured for this provider");
			}

		}

		Map<String, String> resMap = new HashMap<String, String>();
		OutputResponse response = createFeedback(feedbackDetails, request);
		if (response.isSuccess()) {
			logger.info(response.getData());
			FeedbackDetails[] feedbackSavedData = inputMapper.gson().fromJson(response.getData(),
					FeedbackDetails[].class);
			if (feedbackSavedData != null) {
				List<BenCallServicesMappingHistory> obj = new ArrayList<>();
				for (FeedbackDetails f : feedbackSavedData) {
					BenCallServicesMappingHistory benCallServicesMappingHistory = new BenCallServicesMappingHistory(
							f.getBeneficiaryRegID(), f.getBenCallID(), f.getSubServiceID(), f.getFeedbackID(), false,
							f.getCreatedBy());
					obj.add(benCallServicesMappingHistory);
				}
				Iterable<BenCallServicesMappingHistory> dataInserted = benCalServiceCatSubcatMappingRepo.save(obj);
				String requestID = "";

				for (FeedbackDetails feedback : feedbackSavedData) {

					feedback.setInstituteName(feedback.getInstiName());

					requestID = "FE" + "/" + feedback.getDistrictID() + "/"
							+ new SimpleDateFormat("ddMMyyyy").format(Calendar.getInstance().getTimeInMillis()) + "/"
							+ feedback.getFeedbackID();
					feedback.setRequestID(requestID);
					feedback.setDeleted(false);
				}
				createFeedback(new OutputMapper().gson().toJson(feedbackSavedData).toString(), request);

				for (FeedbackDetails m : feedbackSavedData) {
					resMap.put("feedBackId", m.getFeedbackID() + "");
					resMap.put("requestID", requestID);
				}
			}
		} else {
			throw new Exception(response.getErrorMessage());
		}
		return OutputMapper.gson().toJson(resMap);

	}

	private OutputResponse createFeedback(String feedbackDetails, HttpServletRequest request) throws IEMRException {
		HttpUtils utils = new HttpUtils();
		HashMap<String, Object> header = new HashMap<String, Object>();
		header.put("Authorization", request.getHeader("Authorization"));
		String responseStr = utils.post(
				properties.getPropertyByName("common-url") + "/" + properties.getPropertyByName("create-feedback"),
				feedbackDetails, header);
		OutputResponse response = inputMapper.gson().fromJson(responseStr, OutputResponse.class);
		return response;
	}
}
