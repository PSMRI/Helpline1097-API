package com.iemr.helpline1097.controller.co.feedback;

import static org.mockito.Mockito.when;

import java.sql.Timestamp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.iemr.helpline1097.data.co.feedbackRequest.FeedbackRequest;
import com.iemr.helpline1097.service.co.feedback.FeedbackRequestServiceImpl;
import com.iemr.helpline1097.utils.response.OutputResponse;

import jakarta.ws.rs.NotFoundException;

@ExtendWith(MockitoExtension.class)
class FeedbackRequestControllerTest {

	@InjectMocks
	FeedbackRequestController feedbackRequestController;

	@Mock
	private FeedbackRequestServiceImpl feedbackRequestServiceImpl;

	@Test
	void feedbackCreateTest() {

		String request = "Create feedback request";

		FeedbackRequest t_feedbackRequest = new FeedbackRequest();

		t_feedbackRequest.setFeedbackRequestID(12L);
		t_feedbackRequest.setFeedbackSupSummary("FeedbackSupSummary");
		t_feedbackRequest.setSupUserID(12);
		t_feedbackRequest.setComments("Comments");
		t_feedbackRequest.setDeleted(false);
		t_feedbackRequest.setCreatedBy("A Kundu");
		t_feedbackRequest.setCreatedDate(Timestamp.valueOf("2020-09-09 09:09:09"));
		t_feedbackRequest.setModifiedBy("B K");
		t_feedbackRequest.setLastModDate(Timestamp.valueOf("2020-09-09 09:09:09"));

		t_feedbackRequest.toString();

		FeedbackRequest savedDetails = t_feedbackRequest;

		String response = feedbackRequestController.feedbackCreate(request);

		Assertions.assertEquals(response, feedbackRequestController.feedbackCreate(request));

	}

	@Test
	void feedbackCreateTest_Exception() {

		String request = "{\"statusCode\":5000,\"errorMessage\":\"Failed with generic error\",\"status\":\"FAILURE\"}";

		String response = feedbackRequestController.feedbackCreate(request);

		Assertions.assertEquals(response, feedbackRequestController.feedbackCreate(request));

	}

	@Test
	void getFeedbackRequestsTest() {

		int feedbackRequestID = 12;

		FeedbackRequest savedDetails = new FeedbackRequest();

		savedDetails.setFeedbackRequestID(12L);
		savedDetails.setFeedbackSupSummary("FeedbackSupSummary");
		savedDetails.setSupUserID(12);
		savedDetails.setComments("Comments");
		savedDetails.setDeleted(false);
		savedDetails.setCreatedBy("A Kundu");
		savedDetails.setCreatedDate(Timestamp.valueOf("2020-09-09 09:09:09"));
		savedDetails.setModifiedBy("B K");
		savedDetails.setLastModDate(Timestamp.valueOf("2020-09-09 09:09:09"));

		savedDetails.toString();

		when(feedbackRequestServiceImpl.getFeedbackReuest(feedbackRequestID)).thenReturn(savedDetails);

		OutputResponse response = new OutputResponse();

		response.setResponse(savedDetails.toString());

		Assertions.assertEquals(response.toString(), feedbackRequestController.getFeedbackRequests(feedbackRequestID));

	}

	@Test
	void getFeedbackRequestsTest_Exception() {

		int feedbackRequestID = 0;

		when(feedbackRequestServiceImpl.getFeedbackReuest(feedbackRequestID)).thenThrow(NotFoundException.class);

		String response = feedbackRequestController.getFeedbackRequests(feedbackRequestID);

		Assertions.assertEquals(response, feedbackRequestController.getFeedbackRequests(feedbackRequestID));

	}

}
