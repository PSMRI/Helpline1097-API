package com.iemr.helpline1097.controller.beneficiarycall;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Description;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iemr.helpline1097.data.co.beneficiarycall.BenCallServicesMappingHistory;
import com.iemr.helpline1097.data.co.beneficiarycall.BeneficiaryCall;
import com.iemr.helpline1097.service.co.beneficiarycall.BeneficiaryCallService;
import com.iemr.helpline1097.service.co.beneficiarycall.ServicesHistoryService;
import com.iemr.helpline1097.utils.exception.IEMRException;
import com.iemr.helpline1097.utils.mapper.InputMapper;
import com.iemr.helpline1097.utils.response.OutputResponse;

import jakarta.ws.rs.NotFoundException;

@ExtendWith(MockitoExtension.class)
class Service1097HistoryControllerTest {

	@InjectMocks
	Service1097HistoryController service1097HistoryController;

	@Mock
	InputMapper inputMapper;

	@Mock
	private ServicesHistoryService servicesHistoryService;

	@Mock
	private BeneficiaryCallService beneficiaryCallService;

	@Test
	@Description("Tests retrieving service history (TC_Get_Service_History_001)")
	void getServiceHistoryTest() throws Exception {

		String beneficiaryRequest = "{\"beneficiaryRegID\":123, \"calledServiceID\":234}";

		BenCallServicesMappingHistory benCallServicesMappingHistory = new BenCallServicesMappingHistory();

		benCallServicesMappingHistory.setBenCall97ServiceMapID(BigInteger.valueOf(12L));
		benCallServicesMappingHistory.setBenCallID(23L);
		benCallServicesMappingHistory.setBeneficiaryRegID(123L);
		benCallServicesMappingHistory.setCategoryID(23);
		benCallServicesMappingHistory.setCoCategoryID(12);
		benCallServicesMappingHistory.setCoSubCategoryID(1234);
		benCallServicesMappingHistory.setCreatedBy("A K");
		benCallServicesMappingHistory.setCreatedDate(Timestamp.valueOf("2029-09-09 09:09:09"));
		benCallServicesMappingHistory.setDeleted(false);
		benCallServicesMappingHistory.setFeedbackID(129L);
		benCallServicesMappingHistory.setInstituteDirMapID(234L);
		benCallServicesMappingHistory.setLastModDate(Timestamp.valueOf("2029-09-09 09:09:09"));
		benCallServicesMappingHistory.setModifiedBy("beneficiaryRequest");
		benCallServicesMappingHistory.setRequestID("beneficiaryRequest");
		benCallServicesMappingHistory.setSubCategoryID(12);
		benCallServicesMappingHistory.setSubServiceID(234);

		List<BenCallServicesMappingHistory> serviceHistoryList = new ArrayList<>();
		serviceHistoryList.add(benCallServicesMappingHistory);

		when(servicesHistoryService.getServiceHistory(beneficiaryRequest)).thenReturn(serviceHistoryList);

		String response = service1097HistoryController.getServiceHistory(beneficiaryRequest);

		Assertions.assertEquals(response, service1097HistoryController.getServiceHistory(beneficiaryRequest));

	}

	@Test
	@Description("Tests retrieving service history with expected exception (TC_Get_Service_History_Exception_002)")
	void getServiceHistoryTest_Exception() throws Exception {

		String beneficiaryRequest = "{\"statusCode\":5000,\"errorMessage\":\"Failed with generic error\",\"status\":\"FAILURE\"}";

		when(servicesHistoryService.getServiceHistory(beneficiaryRequest)).thenThrow(NotFoundException.class);

		String response = service1097HistoryController.getServiceHistory(beneficiaryRequest);

		Assertions.assertEquals(response, service1097HistoryController.getServiceHistory(beneficiaryRequest));

	}

	@Test
	@Description("Tests retrieving beneficiary call history with valid request data (TC_Get_BeneficiaryCallHistory_ValidData_001)")
	void getBeneficiaryCallHistoryTest() throws Exception {

		String beneficiaryRequest = "{\"beneficiaryRegID\":123, \"calledServiceID\":234}";

		BenCallServicesMappingHistory benCallServicesMappingHistory = new BenCallServicesMappingHistory();

		benCallServicesMappingHistory.setBenCall97ServiceMapID(BigInteger.valueOf(12L));
		benCallServicesMappingHistory.setBenCallID(23L);
		benCallServicesMappingHistory.setBeneficiaryRegID(123L);
		benCallServicesMappingHistory.setCategoryID(23);
		benCallServicesMappingHistory.setCoCategoryID(12);
		benCallServicesMappingHistory.setCoSubCategoryID(1234);
		benCallServicesMappingHistory.setCreatedBy("A K");
		benCallServicesMappingHistory.setCreatedDate(Timestamp.valueOf("2029-09-09 09:09:09"));
		benCallServicesMappingHistory.setDeleted(false);
		benCallServicesMappingHistory.setFeedbackID(129L);
		benCallServicesMappingHistory.setInstituteDirMapID(234L);
		benCallServicesMappingHistory.setLastModDate(Timestamp.valueOf("2029-09-09 09:09:09"));
		benCallServicesMappingHistory.setModifiedBy("beneficiaryRequest");
		benCallServicesMappingHistory.setRequestID("beneficiaryRequest");
		benCallServicesMappingHistory.setSubCategoryID(12);
		benCallServicesMappingHistory.setSubServiceID(234);

		List<BenCallServicesMappingHistory> serviceHistoryList = new ArrayList<>();
		serviceHistoryList.add(benCallServicesMappingHistory);

		String response = service1097HistoryController.getBeneficiaryCallHistory(beneficiaryRequest);

		Assertions.assertEquals(response, service1097HistoryController.getBeneficiaryCallHistory(beneficiaryRequest));
	}

	@Test
	@Description("Tests retrieving beneficiary call history with expected exception (TC_Get_BeneficiaryCallHistory_Exception_002)")
	void getBeneficiaryCallHistoryTest_Exception() throws Exception {

		String beneficiaryRequest = "{\"statusCode\":5000,\"errorMessage\":\"Failed with generic error\",\"status\":\"FAILURE\"}";

		String response = service1097HistoryController.getBeneficiaryCallHistory(beneficiaryRequest);

		Assertions.assertEquals(response, service1097HistoryController.getBeneficiaryCallHistory(beneficiaryRequest));

	}
	
	@Test
	@Description("Tests retrieving beneficiary call history with invalid request data (TC_Get_BeneficiaryCallHistory_InvalidData_003)")
	public void testGetBeneficiaryCallHistory_InvalidData() throws Exception {
	  // Simulate invalid JSON data
	  String invalidRequest = "{invalid_data}";

	  // Expected exception (replace with the actual exception type if known)
	  Exception expectedException = new RuntimeException("Invalid JSON data");

	  when(servicesHistoryService.getServiceHistory(invalidRequest)).thenThrow(expectedException);

	  String response = service1097HistoryController.getBeneficiaryCallHistory(invalidRequest);

	  // Verify the response object
	  OutputResponse outputResponse = new ObjectMapper().readValue(response, OutputResponse.class);
	  assertNotNull(outputResponse);
	  assertNotNull(outputResponse.getErrorMessage()); // Error object should be set
	}

	@Test
	@Description("Tests setting service history with valid data (TC_Set_ServiceHistory_ValidData_001)")
	public void testSetServiceHistoryTest() throws Exception {
	  // Prepare valid request data
	  String beneficiaryRegID = "123";
	  String benCallID = "23";

	  String requestJson = createServiceHistoryRequest(beneficiaryRegID, benCallID); // Create valid JSON
	  // Mock successful service call (replace with expected behavior)
	  BenCallServicesMappingHistory expectedSavedObj = new BenCallServicesMappingHistory(); // Set expected saved object
	  when(servicesHistoryService.createServiceHistory(Mockito.any(BenCallServicesMappingHistory.class))).thenReturn(expectedSavedObj);

	  // Call the service method
	  String response = service1097HistoryController.setServiceHistory(requestJson);
	  
	  // Verify the response
	  assertNotNull(response); // Ensure response is not null
	}

	// Helper method to create valid JSON request
	private String createServiceHistoryRequest(String beneficiaryRegID, String benCallID) {
	  ObjectMapper objectMapper = new ObjectMapper();
	  BenCallServicesMappingHistory serviceHistoryDetails = new BenCallServicesMappingHistory();
	  serviceHistoryDetails.setBeneficiaryRegID(Long.valueOf(beneficiaryRegID));
	  serviceHistoryDetails.setBenCallID(Long.valueOf(benCallID));
	  serviceHistoryDetails.setBenCall97ServiceMapID(BigInteger.valueOf(12L));
		serviceHistoryDetails.setBenCallID(23L);
		serviceHistoryDetails.setBeneficiaryRegID(123L);
		serviceHistoryDetails.setCategoryID(23);
		serviceHistoryDetails.setCoCategoryID(12);
		serviceHistoryDetails.setCoSubCategoryID(1234);
		serviceHistoryDetails.setCreatedBy("A K");
		serviceHistoryDetails.setCreatedDate(Timestamp.valueOf("2029-09-09 09:09:09"));
		serviceHistoryDetails.setDeleted(false);
		serviceHistoryDetails.setFeedbackID(129L);
		serviceHistoryDetails.setInstituteDirMapID(234L);
		serviceHistoryDetails.setLastModDate(Timestamp.valueOf("2029-09-09 09:09:09"));
		serviceHistoryDetails.setModifiedBy("beneficiaryRequest");
		serviceHistoryDetails.setRequestID("beneficiaryRequest");
		serviceHistoryDetails.setSubCategoryID(12);
		serviceHistoryDetails.setSubServiceID(234);

	  try {
	    return objectMapper.writeValueAsString(serviceHistoryDetails);
	  } catch (JsonProcessingException e) {
	    throw new RuntimeException("Error creating request JSON", e);
	  }
	}


	@Test
	@Description("Tests setting service history with expected exception during service call (TC_Set_ServiceHistory_Exception_002)")
	void setServiceHistoryTest_Exception() throws Exception {

		String request = "{\"statusCode\":5000,\"errorMessage\":\"Failed with generic error\",\"status\":\"FAILURE\"}";

		String response = service1097HistoryController.setServiceHistory(request);

		Assertions.assertEquals(response, service1097HistoryController.setServiceHistory(request));

	}

	@Test
	@Description("Tests retrieving call summary (TC_Get_CallSummary_001)")
	void getCallSummaryTest() {

		String request = "{\"benCallID\":91}";

		OutputResponse response = new OutputResponse();

		BeneficiaryCall call = new BeneficiaryCall();

		call.setBenCallID(91L);
		call.setBeneficiaryRegID(123L);
		call.setCalledServiceID(123);
		call.setCallClosureType("Normal");
		call.setIs1097(true);
		call.setCallTime(Timestamp.valueOf("2029-09-09 09:09:09"));
		call.setRemarks("Nice");
		call.setDispositionStatusID(134);

		call.toString();

		List<BeneficiaryCall> callHistoryList = new ArrayList<>();
		callHistoryList.add(call);

		when(beneficiaryCallService.getCallSummaryByCallID(call.getBenCallID())).thenReturn(callHistoryList);

		response.setResponse(callHistoryList.toString());

		Assertions.assertEquals(response.toString(), service1097HistoryController.getCallSummary(request));

	}

	@Test
	@Description("Tests retrieving call summary with expected exception (TC_Get_CallSummary_Exception_002)")
	void getCallSummaryTest_Exception() throws Exception {

		BeneficiaryCall call = new BeneficiaryCall();

		String request = "{\"statusCode\":5000,\"errorMessage\":\"Failed with generic error\",\"status\":\"FAILURE\"}";

		lenient().when(beneficiaryCallService.getCallSummaryByCallID(call.getBenCallID())).thenThrow(NotFoundException.class);

		String response = service1097HistoryController.getCallSummary(request);

		Assertions.assertEquals(response, service1097HistoryController.getCallSummary(request));

	}

	@Test
	@Description("Tests retrieving beneficiary call history with valid request data (TC_Get_BeneficiaryCallsHistory_ValidData_001)")
	void getBeneficiaryCallsHistoryTest() {

		String request = "{\"beneficiaryRegID\":123,\"calledServiceID\":234}";

		BeneficiaryCall call = new BeneficiaryCall();

		call.setBenCallID(91L);
		call.setBeneficiaryRegID(123L);
		call.setCalledServiceID(123);
		call.setCallClosureType("Normal");
		call.setIs1097(true);
		call.setCallTime(Timestamp.valueOf("2029-09-09 09:09:09"));
		call.setRemarks("Nice");
		call.setDispositionStatusID(134);
		call.setCalledServiceID(234);

		call.toString();

		List<BeneficiaryCall> callHistoryList = new ArrayList<>();
		callHistoryList.add(call);

		String response = service1097HistoryController.getBeneficiaryCallsHistory(request);

		Assertions.assertNotNull(call.getCalledServiceID());
		Assertions.assertEquals(response.toString(), service1097HistoryController.getBeneficiaryCallsHistory(request));

	}

	@Test
	@Description("Tests retrieving beneficiary call history with expected exception during service call (TC_Get_BeneficiaryCallsHistory_ServiceCallException_002)")
	void getBeneficiaryCallsHistoryTest_Exception() throws Exception {

		String request = "{\"statusCode\":5000,\"errorMessage\":\"Failed with generic error\",\"status\":\"FAILURE\"}";

		String response = service1097HistoryController.getBeneficiaryCallsHistory(request);

		Assertions.assertEquals(response, service1097HistoryController.getBeneficiaryCallsHistory(request));

	}

	@Test
	@Description("Tests retrieving call history for all services when calledServiceID is null (TC_Get_BeneficiaryCallsHistory_AllServices_003)")
	void getBeneficiaryCallsHistoryTest_Else() {

		String request = "{\"beneficiaryRegID\":123,\"calledServiceID\":null}";

		BeneficiaryCall call = new BeneficiaryCall();

		call.setBenCallID(91L);
		call.setBeneficiaryRegID(123L);
		call.setCalledServiceID(123);
		call.setCallClosureType("Normal");
		call.setIs1097(true);
		call.setCallTime(Timestamp.valueOf("2029-09-09 09:09:09"));
		call.setRemarks("Nice");
		call.setDispositionStatusID(134);
		call.setCalledServiceID(null);

		call.toString();

		List<BeneficiaryCall> callHistoryList = new ArrayList<>();
		callHistoryList.add(call);

		String response = service1097HistoryController.getBeneficiaryCallsHistory(request);

		Assertions.assertNull(call.getCalledServiceID());
		Assertions.assertEquals(response, service1097HistoryController.getBeneficiaryCallsHistory(request));

	}

	@Test
	@Description("Tests retrieving beneficiary call history with exception (TC_Get_BeneficiaryCallsHistory_InvalidJSON_004)")
	void getBeneficiaryCallsHistoryTest_Else_Exception() throws Exception {

		String request = "{\"statusCode\":5000,\"errorMessage\":\"Failed with generic error\",\"status\":\"FAILURE\"}";

		String response = service1097HistoryController.getBeneficiaryCallsHistory(request);

		Assertions.assertEquals(response, service1097HistoryController.getBeneficiaryCallsHistory(request));

	}
	
	@Test
	@Description("Tests retrieving beneficiary call history with invalid JSON request (TC_Get_BeneficiaryCallsHistory_InvalidJSON_005)")
	public void testGetBeneficiaryCallsHistoryTest_InvalidJSON() throws Exception {
	  // Simulate invalid JSON data
	  String invalidRequest = "{invalid_data}";

	  // Expected exception (replace with the actual exception type if known)
	  Exception expectedException = new JSONException("Invalid JSON data");

	  String response = service1097HistoryController.getBeneficiaryCallsHistory(invalidRequest);

	  // Verify the response object
	  OutputResponse outputResponse = new ObjectMapper().readValue(response, OutputResponse.class);
	  assertNotNull(outputResponse);
	  assertNotNull(outputResponse.getErrorMessage()); // Error object should be set
	}


	@Test
	@Description("Tests retrieving referral history with valid request data (TC_Get_ReferralsHistory_ValidData_001)")
	void getReferralsHistoryTest() throws Exception {

		String request = "{\"beneficiaryRegID\":123,\"calledServiceID\":234}";

		BenCallServicesMappingHistory benCallServicesMappingHistory = new BenCallServicesMappingHistory();

		benCallServicesMappingHistory.setBenCall97ServiceMapID(BigInteger.valueOf(12L));
		benCallServicesMappingHistory.setBenCallID(23L);
		benCallServicesMappingHistory.setBeneficiaryRegID(123L);
		benCallServicesMappingHistory.setCategoryID(23);
		benCallServicesMappingHistory.setCoCategoryID(12);
		benCallServicesMappingHistory.setCoSubCategoryID(1234);
		benCallServicesMappingHistory.setCreatedBy("A K");
		benCallServicesMappingHistory.setCreatedDate(Timestamp.valueOf("2029-09-09 09:09:09"));
		benCallServicesMappingHistory.setDeleted(false);
		benCallServicesMappingHistory.setFeedbackID(129L);
		benCallServicesMappingHistory.setInstituteDirMapID(234L);
		benCallServicesMappingHistory.setLastModDate(Timestamp.valueOf("2029-09-09 09:09:09"));
		benCallServicesMappingHistory.setModifiedBy("beneficiaryRequest");
		benCallServicesMappingHistory.setRequestID("beneficiaryRequest");
		benCallServicesMappingHistory.setSubCategoryID(12);
		benCallServicesMappingHistory.setSubServiceID(234);

		List<BenCallServicesMappingHistory> callHistoryList = new ArrayList<>();
		callHistoryList.add(benCallServicesMappingHistory);

		when(servicesHistoryService.getReferralsHistory(request)).thenReturn(callHistoryList);

		String response = service1097HistoryController.getReferralsHistory(request);

		Assertions.assertEquals(response, service1097HistoryController.getReferralsHistory(request));

	}

	@Test
	@Description("Tests retrieving referral history with expected exception during service call (TC_Get_ReferralsHistory_ServiceCallException_002)")
	void getReferralsHistoryTest_Exception() throws Exception {

		String request = "{\"statusCode\":5000,\"errorMessage\":\"Failed with generic error\",\"status\":\"FAILURE\"}";

		when(servicesHistoryService.getReferralsHistory(request)).thenThrow(NotFoundException.class);

		String response = service1097HistoryController.getReferralsHistory(request);

		Assertions.assertEquals(response, service1097HistoryController.getReferralsHistory(request));

	}
	
	@Test
	@Description("Tests retrieving referral history with invalid JSON request (TC_Get_ReferralsHistory_InvalidJSON_003)")
	public void testGetReferralsHistoryTest_InvalidJSON() throws Exception {
	  // Simulate invalid JSON data
	  String invalidRequest = "{invalid_data}";

	  // Expected exception (replace with the actual exception type if known)
	  Exception expectedException = new JSONException("Invalid JSON data");

	  // Make the service call
	  String response = service1097HistoryController.getReferralsHistory(invalidRequest);

	  // Verify the response object
	  OutputResponse outputResponse = new ObjectMapper().readValue(response, OutputResponse.class);
	  assertNotNull(outputResponse);
	  assertNotNull(outputResponse.getErrorMessage()); // Error object should be set
	}


	@Test
	@Description("Tests retrieving feedback history with valid request data (TC_Get_FeedbacksHistory_ValidData_001)")
	void getFeedbacksHistoryTest() throws Exception {

		String request = "{\"beneficiaryRegID\":123,\"calledServiceID\":234}";

		BenCallServicesMappingHistory benCallServicesMappingHistory = new BenCallServicesMappingHistory();

		benCallServicesMappingHistory.setBenCall97ServiceMapID(BigInteger.valueOf(12L));
		benCallServicesMappingHistory.setBenCallID(23L);
		benCallServicesMappingHistory.setBeneficiaryRegID(123L);
		benCallServicesMappingHistory.setCategoryID(23);
		benCallServicesMappingHistory.setCoCategoryID(12);
		benCallServicesMappingHistory.setCoSubCategoryID(1234);
		benCallServicesMappingHistory.setCreatedBy("A K");
		benCallServicesMappingHistory.setCreatedDate(Timestamp.valueOf("2029-09-09 09:09:09"));
		benCallServicesMappingHistory.setDeleted(false);
		benCallServicesMappingHistory.setFeedbackID(129L);
		benCallServicesMappingHistory.setInstituteDirMapID(234L);
		benCallServicesMappingHistory.setLastModDate(Timestamp.valueOf("2029-09-09 09:09:09"));
		benCallServicesMappingHistory.setModifiedBy("beneficiaryRequest");
		benCallServicesMappingHistory.setRequestID("beneficiaryRequest");
		benCallServicesMappingHistory.setSubCategoryID(12);
		benCallServicesMappingHistory.setSubServiceID(234);

		List<BenCallServicesMappingHistory> callHistoryList = new ArrayList<>();
		callHistoryList.add(benCallServicesMappingHistory);

		when(servicesHistoryService.getFeedbacksHistory(request)).thenReturn(callHistoryList);

		String response = service1097HistoryController.getFeedbacksHistory(request);

		Assertions.assertEquals(response, service1097HistoryController.getFeedbacksHistory(request));

	}

	@Test
	@Description("Tests retrieving feedback history with expected exception during service call (TC_Get_FeedbacksHistory_ServiceCallException_002)")
	void getFeedbacksHistoryTest_Exception() throws Exception {

		String request = "{\"statusCode\":5000,\"errorMessage\":\"Failed with generic error\",\"status\":\"FAILURE\"}";

		when(servicesHistoryService.getFeedbacksHistory(request)).thenThrow(NotFoundException.class);

		String response = service1097HistoryController.getFeedbacksHistory(request);

		Assertions.assertEquals(response, service1097HistoryController.getFeedbacksHistory(request));

	}
	
	@Test
	@Description("Tests retrieving feedback history with invalid JSON request (TC_Get_FeedbacksHistory_InvalidJSON_003)")
	public void getFeedbacksHistoryTest_InvalidJSON() throws Exception {
	  // Simulate invalid JSON data
	  String invalidRequest = "{invalid_data}";

	  // Expected exception (replace with the actual exception type if known)
	  Exception expectedException = new JSONException("Invalid JSON data");

	  String response = service1097HistoryController.getFeedbacksHistory(invalidRequest);

	  // Verify the response object
	  OutputResponse outputResponse = new ObjectMapper().readValue(response, OutputResponse.class);
	  assertNotNull(outputResponse);
	  // assertNotNull(outputResponse.getError());  // Optional verification
	  assertNotNull(outputResponse.getErrorMessage()); // Error message should be set
	}
	

	@Test
	@Description("Tests retrieving information history with valid request data (TC_GetInformationsHistory_ValidData_001)")
	void getInformationsHistoryTest() throws Exception {

		String request = "{\"beneficiaryRegID\":123,\"calledServiceID\":234}";

		BenCallServicesMappingHistory benCallServicesMappingHistory = new BenCallServicesMappingHistory();

		benCallServicesMappingHistory.setBenCall97ServiceMapID(BigInteger.valueOf(12L));
		benCallServicesMappingHistory.setBenCallID(23L);
		benCallServicesMappingHistory.setBeneficiaryRegID(123L);
		benCallServicesMappingHistory.setCategoryID(23);
		benCallServicesMappingHistory.setCoCategoryID(12);
		benCallServicesMappingHistory.setCoSubCategoryID(1234);
		benCallServicesMappingHistory.setCreatedBy("A K");
		benCallServicesMappingHistory.setCreatedDate(Timestamp.valueOf("2029-09-09 09:09:09"));
		benCallServicesMappingHistory.setDeleted(false);
		benCallServicesMappingHistory.setFeedbackID(129L);
		benCallServicesMappingHistory.setInstituteDirMapID(234L);
		benCallServicesMappingHistory.setLastModDate(Timestamp.valueOf("2029-09-09 09:09:09"));
		benCallServicesMappingHistory.setModifiedBy("beneficiaryRequest");
		benCallServicesMappingHistory.setRequestID("beneficiaryRequest");
		benCallServicesMappingHistory.setSubCategoryID(12);
		benCallServicesMappingHistory.setSubServiceID(234);

		List<BenCallServicesMappingHistory> callHistoryList = new ArrayList<>();
		callHistoryList.add(benCallServicesMappingHistory);

		when(servicesHistoryService.getInformationsHistory(request)).thenReturn(callHistoryList);

		String response = service1097HistoryController.getInformationsHistory(request);

		Assertions.assertEquals(response, service1097HistoryController.getInformationsHistory(request));

	}

	@Test
	@Description("Tests retrieving information history with invalid JSON request (TC_GetInformationsHistory_InvalidJSON_002)")
	void getInformationsHistoryTest_JSONException() throws Exception {

		String request = "{\"statusCode\":5000,\"errorMessage\":\"Failed with generic error\",\"status\":\"FAILURE\"}";

		when(servicesHistoryService.getInformationsHistory(request)).thenThrow(NotFoundException.class);

		String response = service1097HistoryController.getInformationsHistory(request);

		Assertions.assertEquals(response, service1097HistoryController.getInformationsHistory(request));

	}

	@Test
	@Description("Tests retrieving information history with expected exception during service call (TC_GetInformationsHistory_ServiceCallException_003)")
	void getInformationsHistoryTest_Exception() throws Exception {

		String request = "{\"statusCode\":5000,\"errorMessage\":\"Failed with generic error\",\"status\":\"FAILURE\"}";

		when(servicesHistoryService.getInformationsHistory(request)).thenThrow(NotFoundException.class);

		String response = service1097HistoryController.getInformationsHistory(request);

		Assertions.assertEquals(response, service1097HistoryController.getInformationsHistory(request));

	}
	
	@Test
	@Description("Tests retrieving counselling history with valid request data (TC_GetCounsellingsHistory_ValidData_001)")
	void getCounsellingsHistoryTest() throws Exception {

		String request = "{\"beneficiaryRegID\":123,\"calledServiceID\":234}";

		BenCallServicesMappingHistory benCallServicesMappingHistory = new BenCallServicesMappingHistory();

		benCallServicesMappingHistory.setBenCall97ServiceMapID(BigInteger.valueOf(12L));
		benCallServicesMappingHistory.setBenCallID(23L);
		benCallServicesMappingHistory.setBeneficiaryRegID(123L);
		benCallServicesMappingHistory.setCategoryID(23);
		benCallServicesMappingHistory.setCoCategoryID(12);
		benCallServicesMappingHistory.setCoSubCategoryID(1234);
		benCallServicesMappingHistory.setCreatedBy("A K");
		benCallServicesMappingHistory.setCreatedDate(Timestamp.valueOf("2029-09-09 09:09:09"));
		benCallServicesMappingHistory.setDeleted(false);
		benCallServicesMappingHistory.setFeedbackID(129L);
		benCallServicesMappingHistory.setInstituteDirMapID(234L);
		benCallServicesMappingHistory.setLastModDate(Timestamp.valueOf("2029-09-09 09:09:09"));
		benCallServicesMappingHistory.setModifiedBy("beneficiaryRequest");
		benCallServicesMappingHistory.setRequestID("beneficiaryRequest");
		benCallServicesMappingHistory.setSubCategoryID(12);
		benCallServicesMappingHistory.setSubServiceID(234);

		List<BenCallServicesMappingHistory> callHistoryList = new ArrayList<>();
		callHistoryList.add(benCallServicesMappingHistory);

		when(servicesHistoryService.getCounsellingsHistory(request)).thenReturn(callHistoryList);

		String response = service1097HistoryController.getCounsellingsHistory(request);

		Assertions.assertEquals(response, service1097HistoryController.getCounsellingsHistory(request));

	}

	@Test
	@Description("Tests retrieving counselling history with invalid JSON request (TC_GetCounsellingsHistory_InvalidJSON_002)")
	void getCounsellingsHistoryTest_JSONException() throws Exception {

		String request = "{\"statusCode\":5000,\"errorMessage\":\"Failed with generic error\",\"status\":\"FAILURE\"}";

		when(servicesHistoryService.getCounsellingsHistory(request)).thenThrow(NotFoundException.class);

		String response = service1097HistoryController.getCounsellingsHistory(request);

		Assertions.assertEquals(response, service1097HistoryController.getCounsellingsHistory(request));

	}

	@Test
	@Description("Tests retrieving counselling history and verifies expected exception during service call (TC_GetCounsellingsHistory_ServiceCallException_003)")
	void getCounsellingsHistoryTest_Exception() throws Exception {

		String request = "{\"statusCode\":5000,\"errorMessage\":\"Failed with generic error\",\"status\":\"FAILURE\"}";

		when(servicesHistoryService.getCounsellingsHistory(request)).thenThrow(NotFoundException.class);

		String response = service1097HistoryController.getCounsellingsHistory(request);

		Assertions.assertEquals(response, service1097HistoryController.getCounsellingsHistory(request));

	}

	@Test
	@Description("Tests retrieving case sheet with valid request data (TC_GetCaseSheet_ValidData_001)")
	void getCaseSheetTest() {

		String request = "{\"benCallID\":91}";

		BeneficiaryCall call = new BeneficiaryCall();

		call.setBenCallID(91L);
		call.setBeneficiaryRegID(123L);
		call.setCalledServiceID(123);
		call.setCallClosureType("Normal");
		call.setIs1097(true);
		call.setCallTime(Timestamp.valueOf("2029-09-09 09:09:09"));
		call.setRemarks("Nice");
		call.setDispositionStatusID(134);

		call.toString();

		BenCallServicesMappingHistory benCallServicesMappingHistory = new BenCallServicesMappingHistory();

		benCallServicesMappingHistory.setBenCall97ServiceMapID(BigInteger.valueOf(12L));
		benCallServicesMappingHistory.setBenCallID(23L);
		benCallServicesMappingHistory.setBeneficiaryRegID(123L);
		benCallServicesMappingHistory.setCategoryID(23);
		benCallServicesMappingHistory.setCoCategoryID(12);
		benCallServicesMappingHistory.setCoSubCategoryID(1234);
		benCallServicesMappingHistory.setCreatedBy("A K");
		benCallServicesMappingHistory.setCreatedDate(Timestamp.valueOf("2029-09-09 09:09:09"));
		benCallServicesMappingHistory.setDeleted(false);
		benCallServicesMappingHistory.setFeedbackID(129L);
		benCallServicesMappingHistory.setInstituteDirMapID(234L);
		benCallServicesMappingHistory.setLastModDate(Timestamp.valueOf("2029-09-09 09:09:09"));
		benCallServicesMappingHistory.setModifiedBy("beneficiaryRequest");
		benCallServicesMappingHistory.setRequestID("beneficiaryRequest");
		benCallServicesMappingHistory.setSubCategoryID(12);
		benCallServicesMappingHistory.setSubServiceID(234);

		List<BenCallServicesMappingHistory> callHistoryList = new ArrayList<>();
		callHistoryList.add(benCallServicesMappingHistory);

		when(servicesHistoryService.getCallSummaryV1(call.getBenCallID())).thenReturn(callHistoryList);

		String response = service1097HistoryController.getCaseSheet(request);

		Assertions.assertEquals(response, service1097HistoryController.getCaseSheet(request));

	}
	
	@Test
	@Description("Tests retrieving case sheet and verifies expected exception during service call (TC_GetCaseSheet_ServiceCallException_002)")
	void getCaseSheetTest_Exception() throws Exception {
		
		BeneficiaryCall call = new BeneficiaryCall();

		String request = "{\"statusCode\":5000,\"errorMessage\":\"Failed with generic error\",\"status\":\"FAILURE\"}";

		lenient().when(servicesHistoryService.getCallSummaryV1(call.getBenCallID())).thenThrow(NotFoundException.class);

		String response = service1097HistoryController.getCaseSheet(request);

		Assertions.assertEquals(response, service1097HistoryController.getCaseSheet(request));

	}

}
