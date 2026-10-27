/*
* AMRIT – Accessible Medical Records via Integrated Technology
* Integrated EHR (Electronic Health Records) Solution
*
* Copyright (C) "Piramal Swasthya Management and Research Institute"
*
* This file is part of AMRIT.
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see https://www.gnu.org/licenses/.
*/
package com.iemr.helpline1097.controller.co.services;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.iemr.helpline1097.data.co.beneficiarycall.SubServices;
import com.iemr.helpline1097.data.co.services.CategoryDetails;
import com.iemr.helpline1097.data.co.services.SubCategoryDetails;
import com.iemr.helpline1097.service.co.services.CommonService;

@ExtendWith(MockitoExtension.class)
class CommonControllerTest {

	@Mock
	private CommonService commonService;

	private CommonController controller;

	@BeforeEach
	void setUp() {
		controller = new CommonController();
		controller.setCommonService(commonService);
	}

	@Test
	void getCategoriesReturnsServiceResult() {
		when(commonService.getCategories())
				.thenReturn(Collections.singletonList(new CategoryDetails(1, "Nutrition")));

		assertTrue(controller.getCategories().contains("\"statusCode\":200"));
	}

	@Test
	void getCategoriesReturnsErrorOnFailure() {
		when(commonService.getCategories()).thenThrow(new RuntimeException("categories unavailable"));

		assertTrue(controller.getCategories().contains("categories unavailable"));
	}

	@Test
	void getSubcategoriesPassesCategoryIdFromRequest() {
		when(commonService.getSubCategories(anyInt()))
				.thenReturn(Collections.singletonList(new SubCategoryDetails(5, "Diet")));

		String response = controller.getSubcategories("{\"categoryID\":4}");

		assertTrue(response.contains("\"statusCode\":200"));
		verify(commonService).getSubCategories(4);
	}

	@Test
	void getSubcategoriesReturnsErrorOnMalformedJson() {
		assertTrue(controller.getSubcategories("{oops}").contains("statusCode"));
	}

	@Test
	void getcategoriesByIdPassesSubServiceIdFromRequest() {
		when(commonService.getCategories(anyInt()))
				.thenReturn(Collections.singletonList(new CategoryDetails(1, "Nutrition")));

		String response = controller.getcategoriesById("{\"subServiceID\":9}");

		assertTrue(response.contains("\"statusCode\":200"));
		verify(commonService).getCategories(9);
	}

	@Test
	void getcategoriesByIdReturnsErrorOnMalformedJson() {
		assertTrue(controller.getcategoriesById("{oops}").contains("statusCode"));
	}

	@Test
	void getservicetypesReturnsServiceResult() {
		when(commonService.getActiveServiceTypes())
				.thenReturn(Collections.singletonList(new SubServices(1, "1097", "desc", false)));

		assertTrue(controller.getservicetypes().contains("\"statusCode\":200"));
	}

	@Test
	void getservicetypesReturnsErrorOnFailure() {
		when(commonService.getActiveServiceTypes()).thenThrow(new RuntimeException("service types unavailable"));

		assertTrue(controller.getservicetypes().contains("service types unavailable"));
	}
}
