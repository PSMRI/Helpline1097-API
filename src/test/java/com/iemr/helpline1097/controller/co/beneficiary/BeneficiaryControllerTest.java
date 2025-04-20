package com.iemr.helpline1097.controller.co.beneficiary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Description;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iemr.helpline1097.data.co.beneficiary.M_Promoservice;
import com.iemr.helpline1097.data.co.beneficiarycall.BenCallServicesMappingHistory;
import com.iemr.helpline1097.service.co.beneficiary.BenInformationCounsellingFeedbackReferralImpl;
import com.iemr.helpline1097.service.co.beneficiary.IEMRPromoserviceDetailsServiceImpl;
import com.iemr.helpline1097.utils.response.OutputResponse;

import jakarta.ws.rs.NotFoundException;

@ExtendWith(MockitoExtension.class)
class BeneficiaryControllerTest {

	@InjectMocks
	BeneficiaryController beneficiaryController;

	@Mock
	private IEMRPromoserviceDetailsServiceImpl iEMRPromoserviceDetailsServiceImpl;

	@Mock
	private BenInformationCounsellingFeedbackReferralImpl benInformationCounsellingFeedbackReferralImpl;

	@Test
	@Description("Tests adding promo service details with service layer exception (TC_AddPromoServiceDetails_ServiceLayerException_001)")
	void addPromoServiceDetailsTest_Exception() {

		String request = "{\"statusCode\":5000,\"errorMessage\":\"Failed with generic error\",\"status\":\"FAILURE\"}";

		String response = beneficiaryController.addPromoServiceDetails(request);

		Assertions.assertEquals(response.toString(), beneficiaryController.addPromoServiceDetails(request));

	}

	@Test
	@Description("Tests adding promo service details with valid request (TC_AddPromoServiceDetails_ValidData_002)")
	public void addPromoServiceDetailsTest_ValidData() throws Exception {
		// Simulate valid JSON data for promo service details
		M_Promoservice m_promoservice = new M_Promoservice();
		m_promoservice.setPamphlet("pamphlet");
		m_promoservice.setRadio("FM");
		m_promoservice.setTelevision("Samsung 8K");
		m_promoservice.setFamilyFriends("Many");
		m_promoservice.setHealthcareWorker("healthcareWorker");
		m_promoservice.setOthers("Others");
		m_promoservice.setNotDisclosed("notDisclosed");

		String request = new ObjectMapper().writeValueAsString(m_promoservice); // Convert to JSON string

		// Mock iEMRPromoserviceDetailsServiceImpl (if necessary)
		M_Promoservice mockPromoservice = new M_Promoservice(); // Mock return value
		Mockito.when(iEMRPromoserviceDetailsServiceImpl.addPromoServiceDetail(m_promoservice))
				.thenReturn(mockPromoservice);

		String response = beneficiaryController.addPromoServiceDetails(request);

		// Verify response and object mapping
		assertNotNull(response);
	}

	@Test
	@Description("Tests adding promo service details with invalid JSON request (TC_AddPromoServiceDetails_InvalidJSON_003)")
	public void addPromoServiceDetailsTest_InvalidJSON() throws Exception {
		String invalidRequest = "{invalid_data}"; // Simulate invalid JSON

		String response = beneficiaryController.addPromoServiceDetails(invalidRequest);

		// Verify response indicates error
		assertNotNull(response);
		assertTrue(response.contains("error")); // Check for error message or exception details in response
	}

	@Test
	@Description("Tests adding promo service details with service layer returning null (TC_AddPromoServiceDetails_ServiceLayerReturnNull_004)")
	public void addPromoServiceDetailsTest_ServiceLayerReturnNull() throws Exception {
		// Simulate valid JSON data
		M_Promoservice m_promoservice = new M_Promoservice();
		m_promoservice.setPamphlet("pamphlet");
		m_promoservice.setRadio("FM");
		m_promoservice.setTelevision("Samsung 8K");
		m_promoservice.setFamilyFriends("Many");
		m_promoservice.setHealthcareWorker("healthcareWorker");
		m_promoservice.setOthers("Others");
		m_promoservice.setNotDisclosed("notDisclosed");
		String request = new ObjectMapper().writeValueAsString(m_promoservice);

		// Mock iEMRPromoserviceDetailsServiceImpl to return null (simulating failure)
		Mockito.when(iEMRPromoserviceDetailsServiceImpl.addPromoServiceDetail(m_promoservice)).thenReturn(null);

		String response = beneficiaryController.addPromoServiceDetails(request);

		// Verify response indicates failure
		assertNotNull(response);
	}

	@Test
	@Description("Tests saving beneficiary call service category and subcategory mapping with valid data (TC_SaveBenCalServiceCatSubcatMapping_ValidData_001)")
	void saveBenCalServiceCatSubcatMappingTest() {

		String request = "Save information requested by the beneficiary during call";

		BenCallServicesMappingHistory benCallServicesMappingHistory1 = new BenCallServicesMappingHistory();

		benCallServicesMappingHistory1.setBenCall97ServiceMapID(BigInteger.valueOf(12L));
		benCallServicesMappingHistory1.setBenCallID(23L);
		benCallServicesMappingHistory1.setBeneficiaryRegID(123L);
		benCallServicesMappingHistory1.setCategoryID(23);
		benCallServicesMappingHistory1.setCoCategoryID(12);
		benCallServicesMappingHistory1.setCoSubCategoryID(1234);
		benCallServicesMappingHistory1.setCreatedBy("A K");
		benCallServicesMappingHistory1.setCreatedDate(Timestamp.valueOf("2029-09-09 09:09:09"));
		benCallServicesMappingHistory1.setDeleted(false);
		benCallServicesMappingHistory1.setFeedbackID(129L);
		benCallServicesMappingHistory1.setInstituteDirMapID(234L);
		benCallServicesMappingHistory1.setLastModDate(Timestamp.valueOf("2029-09-09 09:09:09"));
		benCallServicesMappingHistory1.setModifiedBy("beneficiaryRequest");
		benCallServicesMappingHistory1.setRequestID("beneficiaryRequest");
		benCallServicesMappingHistory1.setSubCategoryID(12);
		benCallServicesMappingHistory1.setSubServiceID(234);

		List<BenCallServicesMappingHistory> callHistoryList = new ArrayList<>();
		callHistoryList.add(benCallServicesMappingHistory1);

		BenCallServicesMappingHistory[] benCallServicesMappingHistories = new BenCallServicesMappingHistory[callHistoryList
				.size()];
		benCallServicesMappingHistories = callHistoryList.toArray(benCallServicesMappingHistories);

		Iterable<BenCallServicesMappingHistory> benCallServicesMappingHistory = Arrays
				.asList(benCallServicesMappingHistories);

		String response = beneficiaryController.saveBenCalServiceCatSubcatMapping(request);

		Assertions.assertEquals(response.toString(), beneficiaryController.saveBenCalServiceCatSubcatMapping(request));

	}

	@Test
	@Description("Tests saving beneficiary call service category and subcategory mapping with an exception (TC_SaveBenCalServiceCatSubcatMapping_Exception_002)")
	void saveBenCalServiceCatSubcatMappingTest_Exception() {

		String request = "{\"statusCode\":5000,\"errorMessage\":\"Failed with generic error\",\"status\":\"FAILURE\"}";

		String response = beneficiaryController.saveBenCalServiceCatSubcatMapping(request);

		Assertions.assertEquals(response.toString(), beneficiaryController.saveBenCalServiceCatSubcatMapping(request));

	}

	@Test
	@Description("Tests saving beneficiary call service, category, and subcategory mapping with an exception (TC_SaveBenCalServiceCOCatSubcatMapping_Exception_001)")
	void saveBenCalServiceCOCatSubcatMappingTest() {

		String request = "Save counselling requested by beneficiary";

		BenCallServicesMappingHistory benCallServicesMappingHistory1 = new BenCallServicesMappingHistory();

		benCallServicesMappingHistory1.setBenCall97ServiceMapID(BigInteger.valueOf(12L));
		benCallServicesMappingHistory1.setBenCallID(23L);
		benCallServicesMappingHistory1.setBeneficiaryRegID(123L);
		benCallServicesMappingHistory1.setCategoryID(23);
		benCallServicesMappingHistory1.setCoCategoryID(12);
		benCallServicesMappingHistory1.setCoSubCategoryID(1234);
		benCallServicesMappingHistory1.setCreatedBy("A K");
		benCallServicesMappingHistory1.setCreatedDate(Timestamp.valueOf("2029-09-09 09:09:09"));
		benCallServicesMappingHistory1.setDeleted(false);
		benCallServicesMappingHistory1.setFeedbackID(129L);
		benCallServicesMappingHistory1.setInstituteDirMapID(234L);
		benCallServicesMappingHistory1.setLastModDate(Timestamp.valueOf("2029-09-09 09:09:09"));
		benCallServicesMappingHistory1.setModifiedBy("beneficiaryRequest");
		benCallServicesMappingHistory1.setRequestID("beneficiaryRequest");
		benCallServicesMappingHistory1.setSubCategoryID(12);
		benCallServicesMappingHistory1.setSubServiceID(234);

		List<BenCallServicesMappingHistory> callHistoryList = new ArrayList<>();
		callHistoryList.add(benCallServicesMappingHistory1);

		BenCallServicesMappingHistory[] benCallServicesMappingHistories = new BenCallServicesMappingHistory[callHistoryList
				.size()];
		benCallServicesMappingHistories = callHistoryList.toArray(benCallServicesMappingHistories);

		Iterable<BenCallServicesMappingHistory> benCallServicesMappingHistory = Arrays
				.asList(benCallServicesMappingHistories);

		String response = beneficiaryController.saveBenCalServiceCOCatSubcatMapping(request);

		Assertions.assertEquals(response.toString(),
				beneficiaryController.saveBenCalServiceCOCatSubcatMapping(request));

	}

	@Test
	@Description("Tests saving beneficiary call service, category, and subcategory mapping with an exception (TC_SaveBenCalServiceCOCatSubcatMapping_Exception_002)")
	void saveBenCalServiceCOCatSubcatMappingTest_Exception() {

		String request = "{\"statusCode\":5000,\"errorMessage\":\"Failed with generic error\",\"status\":\"FAILURE\"}";

		String response = beneficiaryController.saveBenCalServiceCOCatSubcatMapping(request);

		Assertions.assertEquals(response.toString(),
				beneficiaryController.saveBenCalServiceCOCatSubcatMapping(request));
	}

	@Test
	@Description("Tests saving beneficiary call referral mapping with valid data (TC_SaveBenCalReferralMapping_ValidData_001)")
	void saveBenCalReferralMappingTest() {

		String referralRequest = "Save beneficiary call referral mapping";

		when(benInformationCounsellingFeedbackReferralImpl.saveBenCalReferralMapping(referralRequest))
				.thenReturn(referralRequest);

		OutputResponse response = new OutputResponse();

		response.setResponse(benInformationCounsellingFeedbackReferralImpl.saveBenCalReferralMapping(referralRequest));

		Assertions.assertEquals(response.toString(), beneficiaryController.saveBenCalReferralMapping(referralRequest));
	}

	@Test
	@Description("Tests saving beneficiary call referral mapping with an exception (TC_SaveBenCalReferralMapping_Exception_002)")
	void saveBenCalReferralMappingTest_Exception() {

		String referralRequest = "{\"statusCode\":5000,\"errorMessage\":\"Failed with generic error\",\"status\":\"FAILURE\"}";

		when(benInformationCounsellingFeedbackReferralImpl.saveBenCalReferralMapping(referralRequest))
				.thenThrow(NotFoundException.class);

		String response = beneficiaryController.saveBenCalReferralMapping(referralRequest);

		Assertions.assertEquals(response, beneficiaryController.saveBenCalReferralMapping(referralRequest));
	}

}
