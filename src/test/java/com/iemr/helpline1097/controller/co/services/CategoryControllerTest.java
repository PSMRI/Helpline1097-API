package com.iemr.helpline1097.controller.co.services;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.iemr.helpline1097.data.co.services.CategoryDetails;
import com.iemr.helpline1097.service.co.services.CategoryService;
import com.iemr.helpline1097.utils.response.OutputResponse;

import jakarta.ws.rs.NotFoundException;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

	@InjectMocks
	CategoryController categoryController;

	@Mock
	private CategoryService categoryService;

	@Test
	void getAllCategriesTest() {

		OutputResponse response = new OutputResponse();

		CategoryDetails categoryDetails3 = new CategoryDetails();
		categoryDetails3.setCategoryDesc("Category Desc");
		categoryDetails3.setCategoryID(1);
		categoryDetails3.setCategoryName("Category Name");
		categoryDetails3.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
		categoryDetails3.setCreatedDate(mock(Timestamp.class));
		categoryDetails3.setDeleted(true);
		categoryDetails3.setLastModDate(mock(Timestamp.class));
		categoryDetails3.setSubServiceID(1);

		categoryDetails3.toString();

		List<CategoryDetails> categoryList = new ArrayList<CategoryDetails>();
		categoryList.add(categoryDetails3);

		when(this.categoryService.getAllCategories()).thenReturn(categoryList);

		response.setResponse(categoryList.toString());

		Assertions.assertEquals(response.toString(), categoryController.getAllCategries());
	}

	@Test
	void getAllCategriesTest_Exception() {

		when(this.categoryService.getAllCategories()).thenThrow(NotFoundException.class);

		String response = categoryController.getAllCategries();

		Assertions.assertEquals(response, categoryController.getAllCategries());

	}

}
