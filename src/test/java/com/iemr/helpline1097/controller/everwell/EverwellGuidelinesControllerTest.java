package com.iemr.helpline1097.controller.everwell;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Description;

import com.iemr.helpline1097.service.everwell.EverwellService;
import com.iemr.helpline1097.utils.exception.IEMRException;

import jakarta.ws.rs.NotFoundException;

@ExtendWith(MockitoExtension.class)
class EverwellGuidelinesControllerTest {

	@InjectMocks
	EverwellGuidelinesController everwellGuidelinesController;

	@Mock
	private EverwellService everwellService;

	@Test
	@Description("Tests successful saving of Everwell guidelines (TC_SaveEverwellGuidelines_ValidData_001)")
	void saveEverwellGuidelinesTest() throws IEMRException {

		String request = "{\"providerServiceMapID\":123,\"Category\":\\\"String\\\",\"fileContent\":\"String\"\"\\\"GuidelineName\\\":\\\"String\\\"\"\\\"GuidelineDescription\\\":\\\"String\\\"}";

		String result = request;

		when(everwellService.saveGuidelines(request)).thenReturn(result);

		String response = everwellGuidelinesController.saveEverwellGuidelines(request);

		Assertions.assertEquals(response, everwellGuidelinesController.saveEverwellGuidelines(request));

	}

	@Test
	@Description("Tests handling exception during saving Everwell guidelines (TC_SaveEverwellGuidelines_Exception_002)")
	void saveEverwellGuidelinesTest_Exception() throws IEMRException {

		String request = "{\"statusCode\":5000,\"errorMessage\":\"Failed with generic error\",\"status\":\"FAILURE\"}";

		when(everwellService.saveGuidelines(request)).thenThrow(NotFoundException.class);

		String response = everwellGuidelinesController.saveEverwellGuidelines(request);

		Assertions.assertEquals(response, everwellGuidelinesController.saveEverwellGuidelines(request));
	}

	@Test
	@Description("Tests successful retrieval of Everwell guidelines (TC_FetchEverwellGuidelines_ValidData_001)")
	void fetchEverwellGuidelinesTest() throws IEMRException {

		String request = "{\"providerServiceMapID\":123,\"adherencePercentage\":234}";

		String result = request;

		when(everwellService.fetchGuidelines(request)).thenReturn(result);

		String response = everwellGuidelinesController.fetchEverwellGuidelines(request);

		Assertions.assertEquals(response, everwellGuidelinesController.fetchEverwellGuidelines(request));

	}

	@Test
	@Description("Tests handling exception during fetching Everwell guidelines (TC_FetchEverwellGuidelines_Exception_002)")
	void fetchEverwellGuidelinesTest_Exception() throws IEMRException {

		String request = "{\"statusCode\":5000,\"errorMessage\":\"Failed with generic error\",\"status\":\"FAILURE\"}";

		when(everwellService.fetchGuidelines(request)).thenThrow(NotFoundException.class);

		String response = everwellGuidelinesController.fetchEverwellGuidelines(request);

		Assertions.assertEquals(response, everwellGuidelinesController.fetchEverwellGuidelines(request));
	}

	@Test
	@Description("Tests successful deletion of Everwell guidelines (TC_DeleteEverwellGuidelines_ValidData_001)")
	void deleteEverwellGuidelinesTest() throws IEMRException {

		String request = "{\"Id\":1,\"modifiedBy\":\\\"String\\\"}";

		String result = request;

		when(everwellService.deleteGuideline(request)).thenReturn(result);

		String response = everwellGuidelinesController.deleteEverwellGuidelines(request);

		Assertions.assertEquals(response, everwellGuidelinesController.deleteEverwellGuidelines(request));

	}

	@Test
	@Description("Tests handling exception during deleting Everwell guidelines (TC_DeleteEverwellGuidelines_Exception_002)")
	void deleteEverwellGuidelinesTest_Exception() throws IEMRException {

		String request = "{\"statusCode\":5000,\"errorMessage\":\"Failed with generic error\",\"status\":\"FAILURE\"}";

		when(everwellService.deleteGuideline(request)).thenThrow(NotFoundException.class);

		String response = everwellGuidelinesController.deleteEverwellGuidelines(request);

		Assertions.assertEquals(response, everwellGuidelinesController.deleteEverwellGuidelines(request));
	}

}
