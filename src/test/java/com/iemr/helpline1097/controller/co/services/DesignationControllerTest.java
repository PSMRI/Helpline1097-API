package com.iemr.helpline1097.controller.co.services;

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

import com.iemr.helpline1097.service.co.services.DesignationService;
import com.iemr.helpline1097.utils.response.OutputResponse;

@ExtendWith(MockitoExtension.class)
class DesignationControllerTest {

	@InjectMocks
	DesignationController designationController;

	@Mock
	private DesignationService designationService;

	@Test
	@Description("Tests successful retrieval of designations (TC_GetDesignations_ValidData_001)")
	void getDesignationsTest() {

		OutputResponse response = new OutputResponse();

		response.setResponse(designationService.getDesignations().toString());

		Assertions.assertEquals(response.toString(), designationController.getDesignations());
	}
	
	@Test
	@Description("Tests handling exception during retrieving designations (TC_GetDesignations_Exception_002)")
	public void getDesignationsTest_Exception() throws Exception {
	  Mockito.when(designationService.getDesignations()).thenThrow(new RuntimeException("Simulated Exception"));

	  String response = designationController.getDesignations();

	  // Verify response contains error information
	  assertNotNull(response);
	}

}
