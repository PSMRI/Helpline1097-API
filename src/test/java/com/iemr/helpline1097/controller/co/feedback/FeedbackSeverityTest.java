package com.iemr.helpline1097.controller.co.feedback;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Description;

import com.iemr.helpline1097.service.co.feedback.FeedbackSeverityService;
import com.iemr.helpline1097.service.co.feedback.FeedbackTypeService;
import com.iemr.helpline1097.utils.response.OutputResponse;

@ExtendWith(MockitoExtension.class)
class FeedbackSeverityTest {

	@InjectMocks
	FeedbackSeverity feedbackSeverity;

	@Mock
	private FeedbackSeverityService feedbackSeverityService;

	@Mock
	private FeedbackTypeService feedbackTypeService;

	@Test
	@Description("Tests successful retrieval of active feedback severity (TC_GetSeverity_ValidData_001)")
	void getSeverityTest() {

		OutputResponse response = new OutputResponse();

		response.setResponse(feedbackSeverityService.getActiveFeedbackSeverity().toString());

		Assertions.assertEquals(response.toString(), feedbackSeverity.getSeverity());
	}
	
	@Test
	@Description("Tests handling exception during retrieving severity (TC_GetSeverity_Exception_002)")
	public void getSeverityTest_Exception() throws Exception {
		
	  Mockito.when(feedbackSeverityService.getActiveFeedbackSeverity()).thenThrow(new RuntimeException("Simulated Exception"));

	  // Call the controller method
	  String response = feedbackSeverity.getSeverity();

	  // Verify response contains error information
	  assertNotNull(response);
	}

	@Test
	@Description("Tests successful retrieval of active feedback types (TC_GetFeedbackType_ValidData_001)")
	void getFeedbackTypeTest() {

		OutputResponse response = new OutputResponse();

		response.setResponse(feedbackTypeService.getActiveFeedbackTypes().toString());

		Assertions.assertEquals(response.toString(), feedbackSeverity.getFeedbackType());

	}
	
	@Test
	@Description("Tests handling exception during retrieving feedback types (TC_GetFeedbackType_Exception_002)")
	public void getFeedbackTypeTest_Exception() throws Exception {
		
	  Mockito.when(feedbackTypeService.getActiveFeedbackTypes()).thenThrow(new RuntimeException("Simulated Exception"));

	  // Call the controller method
	  String response = feedbackSeverity.getFeedbackType();

	  // Verify response contains error information
	  assertNotNull(response);
	}
}
