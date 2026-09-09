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
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.iemr.helpline1097.data.co.services.CategoryDetails;
import com.iemr.helpline1097.service.co.services.CategoryService;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

	@Mock
	private CategoryService categoryService;

	private CategoryController controller;

	@BeforeEach
	void setUp() {
		controller = new CategoryController();
		controller.setCategoryService(categoryService);
	}

	@Test
	void getAllCategriesReturnsServiceResult() {
		when(categoryService.getAllCategories())
				.thenReturn(Collections.singletonList(new CategoryDetails(1, "Nutrition")));

		assertTrue(controller.getAllCategries().contains("\"statusCode\":200"));
	}

	@Test
	void getAllCategriesReturnsErrorOnFailure() {
		when(categoryService.getAllCategories()).thenThrow(new RuntimeException("categories unavailable"));

		assertTrue(controller.getAllCategries().contains("categories unavailable"));
	}
}
