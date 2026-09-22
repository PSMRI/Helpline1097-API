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
package com.iemr.helpline1097.service.co.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iemr.helpline1097.data.co.services.CategoryDetails;
import com.iemr.helpline1097.repository.co.services.CategoryRepository;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

	@Mock
	private CategoryRepository categoryRepository;

	private CategoryServiceImpl service;

	@BeforeEach
	void setUp() {
		service = new CategoryServiceImpl();
		service.setCategoryRepository(categoryRepository);
	}

	private static ArrayList<Object[]> rows() {
		ArrayList<Object[]> rows = new ArrayList<>();
		rows.add(new Object[] { 1, "Nutrition" });
		rows.add(new Object[] { 2, "Immunisation" });
		return rows;
	}

	@Test
	void getAllCategoriesMapsRepositoryRows() {
		when(categoryRepository.findBy()).thenReturn(rows());

		List<CategoryDetails> categories = service.getAllCategories();

		assertEquals(2, categories.size());
		assertEquals("Nutrition", categories.get(0).getCategoryName());
	}

	@Test
	void getAllCategoriesReturnsEmptyListWhenNoRows() {
		when(categoryRepository.findBy()).thenReturn(new ArrayList<>());

		assertTrue(service.getAllCategories().isEmpty());
	}

	@Test
	void getAllCategoriesSkipsEmptyRows() {
		ArrayList<Object[]> rows = new ArrayList<>();
		rows.add(new Object[0]);
		when(categoryRepository.findBy()).thenReturn(rows);

		assertTrue(service.getAllCategories().isEmpty());
	}

	@Test
	void getAllCategoriesForSubServiceReadsSubServiceIdFromRequest() throws Exception {
		when(categoryRepository.getAllCategories(anyInt())).thenReturn(rows());

		List<CategoryDetails> categories = service.getAllCategories("{\"subServiceID\":9}");

		assertEquals(2, categories.size());
		verify(categoryRepository).getAllCategories(9);
	}

	@Test
	void getAllCategoriesForSubServiceSkipsShortRows() throws Exception {
		ArrayList<Object[]> rows = new ArrayList<>();
		rows.add(new Object[] { 1 });
		when(categoryRepository.getAllCategories(anyInt())).thenReturn(rows);

		assertTrue(service.getAllCategories("{\"subServiceID\":9}").isEmpty());
	}

	@Test
	void getAllCategoriesForSubServicePropagatesParseFailure() {
		assertThrows(JsonProcessingException.class, () -> service.getAllCategories("{oops}"));
	}

	@Test
	void nullRowsAreSkippedByBothCategoryLookups() throws Exception {
		ArrayList<Object[]> rows = new ArrayList<>();
		rows.add(null);
		when(categoryRepository.findBy()).thenReturn(rows);
		when(categoryRepository.getAllCategories(anyInt())).thenReturn(rows);

		assertTrue(service.getAllCategories().isEmpty());
		assertTrue(service.getAllCategories("{\"subServiceID\":9}").isEmpty());
	}
}
