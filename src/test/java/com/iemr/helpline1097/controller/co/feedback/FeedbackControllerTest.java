package com.iemr.helpline1097.controller.co.feedback;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Description;
import org.springframework.mock.web.MockHttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iemr.helpline1097.data.co.feedback.FeedbackDetails;
import com.iemr.helpline1097.service.co.feedback.FeedbackService;
import com.iemr.helpline1097.service.co.feedback.FeedbackServiceImpl;

import jakarta.servlet.http.HttpServletRequest;

@ExtendWith(MockitoExtension.class)
class FeedbackControllerTest {
    @InjectMocks
    private FeedbackController feedbackController;

    @Mock
    private FeedbackService feedbackService;

    @Mock
    private FeedbackServiceImpl feedbackServiceImpl;
    
    @Test
    @Description("Tests retrieving feedback list with valid beneficiary ID (TC_GetFeedbackList_ValidData_001)")
    public void feedbackReuestTest_ValidData() throws Exception {
      // Create a FeedbackDetails object with a valid beneficiary ID
      FeedbackDetails feedbackDetails = new FeedbackDetails();
      feedbackDetails.setBeneficiaryRegID(123456L);

      // Convert to JSON string
      ObjectMapper objectMapper = new ObjectMapper();
      String request = objectMapper.writeValueAsString(feedbackDetails);

      List<FeedbackDetails> mockFeedbackList = new ArrayList<>();
      FeedbackDetails mockFeedback = new FeedbackDetails();
      mockFeedback.setFeedback("Test Feedback");
      mockFeedbackList.add(mockFeedback);
      Mockito.when(feedbackService.getFeedbackRequests(Mockito.anyLong())).thenReturn(mockFeedbackList);

      // Call the controller method
      String response = feedbackController.feedbackReuest(request);

      // Verify response and object mapping
      assertNotNull(response);
    }
    
    @Test
    @Description("Tests successful retrieval of feedback by post with valid ID (TC_GetFeedbackByPost_ValidData_001)")
    void testGetFeedbackByPost() {
        when(feedbackService.getFeedbackRequests(anyLong())).thenReturn(new ArrayList<>());

        FeedbackController feedbackController = new FeedbackController();
        feedbackController.setFeedbackService(feedbackService);

        // Act
        String actualFeedbackByPost = feedbackController.getFeedbackByPost(1);

        // Assert
        verify(feedbackService).getFeedbackRequests(eq(1L));
        assertEquals("{\"data\":[],\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}",
                actualFeedbackByPost);
    }
    
    @Test
    @Description("Tests handling exception during feedback retrieval (TC_GetFeedbackByPost_Exception_002)")
    public void getFeedbackByPostTest_Exception() throws Exception {
      // Mock feedbackService (if necessary)
      Mockito.when(feedbackService.getFeedbackRequests(Mockito.anyInt())).thenThrow(new RuntimeException("Simulated Exception"));

      // Call the controller method with a valid feedback ID (doesn't matter in this case)
      int feedbackID = 123;
      String response = feedbackController.getFeedbackByPost(feedbackID);

      // Verify response contains error information
      assertNotNull(response);
    }

    @Test
    @Description("Tests successful saving of beneficiary feedback (TC_SaveBenFeedback_ValidData_001)")
    void testSaveBenFeedback() throws Exception {
        

        // Arrange
        FeedbackServiceImpl feedbackServiceImpl = mock(FeedbackServiceImpl.class);
        when(feedbackServiceImpl.saveFeedbackFromCustomer(Mockito.<String>any(), Mockito.<HttpServletRequest>any()))
                .thenReturn("jane.doe@example.org");

        FeedbackController feedbackController = new FeedbackController();
        feedbackController.setFeedbackServiceImpl(feedbackServiceImpl);

        // Act
        String actualSaveBenFeedbackResult = feedbackController.saveBenFeedback("Feedback Request",
                new MockHttpServletRequest());

        // Assert
        verify(feedbackServiceImpl).saveFeedbackFromCustomer(eq("Feedback Request"), isA(HttpServletRequest.class));
        assertEquals(
                "{\"data\":{\"response\":\"jane.doe@example.org\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":"
                        + "\"Success\"}",
                actualSaveBenFeedbackResult);
    }

    @Test
    @Description("Tests handling exception during feedback saving (TC_SaveBenFeedback_Exception_002)")
    public void saveBenFeedbackTest_Exception() throws Exception {
      // Mock feedbackServiceImpl (if necessary)
      Mockito.when(feedbackServiceImpl.saveFeedbackFromCustomer(Mockito.anyString(), Mockito.any(HttpServletRequest.class))).thenThrow(new RuntimeException("Simulated Exception"));
      // Create a sample feedback request (adjust based on your actual format)
      String feedbackRequest = "{\"feedback\":\"Test feedback\"}";

      // Call the controller method
      String response = feedbackController.saveBenFeedback(feedbackRequest, null); // Assuming HttpServletRequest is not required

      // Verify response contains error information
      assertNotNull(response);
    }
}
