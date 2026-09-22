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

import com.iemr.helpline1097.data.co.services.SubCategoryDetails;
import com.iemr.helpline1097.repository.co.services.SubCategoryRepository;

@ExtendWith(MockitoExtension.class)
class SubCategoryServiceImplTest {

	@Mock
	private SubCategoryRepository subCategoryRepository;

	private SubCategoryServiceImpl service;

	@BeforeEach
	void setUp() {
		service = new SubCategoryServiceImpl();
		service.setSubCategoryRepository(subCategoryRepository);
	}

	@Test
	void getSubCategoriesMapsRepositoryRows() {
		ArrayList<Object[]> rows = new ArrayList<>();
		rows.add(new Object[] { 5, "Diet" });
		when(subCategoryRepository.findBy(anyInt())).thenReturn(rows);

		List<SubCategoryDetails> subCategories = service.getSubCategories(4);

		assertEquals(1, subCategories.size());
		assertEquals("Diet", subCategories.get(0).getSubCategoryName());
		verify(subCategoryRepository).findBy(4);
	}

	@Test
	void getSubCategoriesSkipsEmptyRows() {
		ArrayList<Object[]> rows = new ArrayList<>();
		rows.add(new Object[0]);
		when(subCategoryRepository.findBy(anyInt())).thenReturn(rows);

		assertTrue(service.getSubCategories(4).isEmpty());
	}

	@Test
	void getSubCategoriesReturnsEmptyListWhenNoRows() {
		when(subCategoryRepository.findBy(anyInt())).thenReturn(new ArrayList<>());

		assertTrue(service.getSubCategories(4).isEmpty());
	}

	@Test
	void nullRowsAreSkipped() {
		ArrayList<Object[]> rows = new ArrayList<>();
		rows.add(null);
		when(subCategoryRepository.findBy(anyInt())).thenReturn(rows);

		assertTrue(service.getSubCategories(4).isEmpty());
	}
}
