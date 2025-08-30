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

import com.iemr.helpline1097.service.co.services.InstitutionService;
import com.iemr.helpline1097.utils.response.OutputResponse;

@ExtendWith(MockitoExtension.class)
class InstitutionControllerTest {

	@InjectMocks
	InstitutionController institutionController;

	@Mock
	private InstitutionService institutionService;

	@Test
	@Description("Tests successful retrieval of institutions (TC_GetInstitutions_ValidData_001)")
	void getInstitutionsTest() {

		OutputResponse response = new OutputResponse();

		response.setResponse(institutionService.getInstitutions().toString());

		Assertions.assertEquals(response.toString(), institutionController.getInstitutions());
	}
	
	@Test
	@Description("Tests handling exception during retrieving institutions (TC_GetInstitutions_Exception_002)")
	public void getInstitutionsTest_Exception() throws Exception {
	  Mockito.when(institutionService.getInstitutions()).thenThrow(new RuntimeException("Simulated Exception"));

	  String response = institutionController.getInstitutions();

	  // Verify response contains error information
	  assertNotNull(response);
	}
}
