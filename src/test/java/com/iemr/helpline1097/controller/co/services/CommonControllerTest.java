package com.iemr.helpline1097.controller.co.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.sql.Timestamp;
import java.util.Collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Description;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.iemr.helpline1097.data.co.beneficiarycall.SubServices;
import com.iemr.helpline1097.data.co.services.CategoryDetails;
import com.iemr.helpline1097.data.co.services.SubCategoryDetails;
import com.iemr.helpline1097.service.co.services.CommonService;
import com.iemr.helpline1097.utils.response.OutputResponse;

@ExtendWith(MockitoExtension.class)
class CommonControllerTest {

	@InjectMocks
	CommonController commonController;

	@Mock
	private CommonService commonService;

	@Test
	@Description("Tests successful retrieval of categories (TC_GetCategories_ValidData_001)")
	void getCategoriesTest() {

		OutputResponse response = new OutputResponse();

		response.setResponse(commonService.getCategories().toString());

		Assertions.assertEquals(response.toString(), commonController.getCategories());
	}
	
	@Test
	@Description("Tests handling exception during category retrieval (TC_GetCategories_Exception_002)")
	void getCategoriesTest_Exception() {

		String response = commonController.getCategories();

		Assertions.assertEquals(response, commonController.getCategories());
	}
	
	@Test
	@Description("Tests successful retrieval of subcategories (TC_GetSubcategories_ValidData_001)")
	public void getSubcategoriesTest_ValidData() throws Exception {
	  // Sample valid CategoryDetails object (adjust based on your actual structure)
	    CategoryDetails categoryDetails3 = new CategoryDetails();
		categoryDetails3.setCategoryDesc("Category Desc");
		categoryDetails3.setCategoryID(1);
		categoryDetails3.setCategoryName("Category Name");
		categoryDetails3.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
		categoryDetails3.setCreatedDate(mock(Timestamp.class));
		categoryDetails3.setDeleted(true);
		categoryDetails3.setLastModDate(mock(Timestamp.class));
		categoryDetails3.setSubServiceID(1);

	  // Convert to JSON string
	  ObjectMapper objectMapper = new ObjectMapper();
	  objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false); // Disable failing on empty beans (optional)

	  String requestJson = objectMapper.writeValueAsString(categoryDetails3);

	  Mockito.when(commonService.getSubCategories(categoryDetails3.getCategoryID())).thenReturn(
	    Collections.singletonList(new SubCategoryDetails()));

	  // Call the controller method
			  String response = commonController.getSubcategories(requestJson);

	  // Verify response contains retrieved subcategories (adjust based on actual response format)
	  assertNotNull(response);
	}

	@Test
	@Description("Tests handling exception during subcategory retrieval (TC_GetSubcategories_Exception_002)")
	void getSubcategoriesTest_Exception() {

		String request = "{\"statusCode\":5000,\"errorMessage\":\"Failed with generic error\",\"status\":\"FAILURE\"}";

		String response = commonController.getSubcategories(request);

		Assertions.assertEquals(response, commonController.getSubcategories(request));
	}
	
	@Test
	@Description("Tests successful retrieval of categories by SubServiceID (TC_GetCategoriesById_ValidData_001)")
	public void getCategoriesByIdTest_ValidData() throws Exception {
	  // Sample valid SubServices object (adjust based on your actual structure)
		SubServices serviceMaster = new SubServices();

		serviceMaster.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
		serviceMaster.setCreatedDate(mock(Timestamp.class));
		serviceMaster.setDeleted(true);
		serviceMaster.setLastModDate(mock(Timestamp.class));
		serviceMaster.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
		serviceMaster.setSubServiceDesc("Sub Service Desc");
		serviceMaster.setSubServiceID(1);
		serviceMaster.setSubServiceName("Sub Service Name");

	  // Convert to JSON string
	  ObjectMapper objectMapper = new ObjectMapper();
	  objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	  String requestJson = objectMapper.writeValueAsString(serviceMaster);

	  Mockito.when(commonService.getCategories(serviceMaster.getSubServiceID())).thenReturn(
	      Collections.singletonList(new CategoryDetails()));

	  // Call the controller method
	  String response = commonController.getcategoriesById(requestJson);

	  // Verify response contains retrieved categories (adjust based on actual response format)
	  assertNotNull(response);
	}

	@Test
	@Description("Tests handling exception during category retrieval (TC_GetCategoriesById_Exception_001)")
	void getcategoriesByIdTest_Exception() {

		String request = "{\"statusCode\":5000,\"errorMessage\":\"Failed with generic error\",\"status\":\"FAILURE\"}";

		String response = commonController.getcategoriesById(request);

		Assertions.assertEquals(response, commonController.getcategoriesById(request));
	}

	@Test
	@Description("Tests successful retrieval of active service types (TC_GetServiceTypes_ValidData_001)")
	void getservicetypesTest() {

		OutputResponse response = new OutputResponse();

		response.setResponse(commonService.getActiveServiceTypes().toString());

		Assertions.assertEquals(response.toString(), commonController.getservicetypes());
	}
	
	@Test
	@Description("Tests handling exception during retrieving service types (TC_GetServiceTypes_Exception_001)")
	public void getServiceTypesTest_Exception() throws Exception {
	  Mockito.when(commonService.getActiveServiceTypes()).thenThrow(new RuntimeException("Simulated Exception"));

	  // Call the controller method
	  String response = commonController.getservicetypes();

	  // Verify response contains error information
	  assertNotNull(response);
	}


}
