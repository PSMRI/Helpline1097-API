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
import static org.junit.jupiter.api.Assertions.assertFalse;
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

import com.iemr.helpline1097.data.co.beneficiarycall.SubServices;
import com.iemr.helpline1097.data.co.services.CategoryDetails;
import com.iemr.helpline1097.data.co.services.SubCategoryDetails;
import com.iemr.helpline1097.repository.co.services.CategoryRepository;
import com.iemr.helpline1097.repository.co.services.ServiceTypeRepository;
import com.iemr.helpline1097.repository.co.services.SubCategoryRepository;

@ExtendWith(MockitoExtension.class)
class CommonServiceImplTest {

	@Mock
	private CategoryRepository categoryRepository;

	@Mock
	private SubCategoryRepository subCategoryRepository;

	@Mock
	private ServiceTypeRepository serviceTypeRepository;

	private CommonServiceImpl service;

	@BeforeEach
	void setUp() {
		service = new CommonServiceImpl();
		service.setCategoryRepository(categoryRepository);
		service.setSubCategoryRepository(subCategoryRepository);
		service.setServiceTypeRepository(serviceTypeRepository);
	}

	private static ArrayList<Object[]> pairRows() {
		ArrayList<Object[]> rows = new ArrayList<>();
		rows.add(new Object[] { 1, "Nutrition" });
		return rows;
	}

	private static <T> List<T> toList(Iterable<T> iterable) {
		List<T> list = new ArrayList<>();
		iterable.forEach(list::add);
		return list;
	}

	@Test
	void getCategoriesMapsRepositoryRows() {
		when(categoryRepository.findBy()).thenReturn(pairRows());

		List<CategoryDetails> categories = toList(service.getCategories());

		assertEquals(1, categories.size());
		assertEquals("Nutrition", categories.get(0).getCategoryName());
	}

	@Test
	void getCategoriesSkipsSingleColumnRows() {
		ArrayList<Object[]> rows = new ArrayList<>();
		rows.add(new Object[] { 1 });
		when(categoryRepository.findBy()).thenReturn(rows);

		assertEquals(0, toList(service.getCategories()).size());
	}

	@Test
	void getSubCategoriesMapsRepositoryRows() {
		when(subCategoryRepository.findByCategoryID(anyInt())).thenReturn(pairRows());

		List<SubCategoryDetails> subCategories = toList(service.getSubCategories(4));

		assertEquals(1, subCategories.size());
		verify(subCategoryRepository).findByCategoryID(4);
	}

	@Test
	void getSubCategoriesSkipsSingleColumnRows() {
		ArrayList<Object[]> rows = new ArrayList<>();
		rows.add(new Object[] { 1 });
		when(subCategoryRepository.findByCategoryID(anyInt())).thenReturn(rows);

		assertEquals(0, toList(service.getSubCategories(4)).size());
	}

	@Test
	void getCategoriesBySubServiceMapsRepositoryRows() {
		when(categoryRepository.getAllCategories(anyInt())).thenReturn(pairRows());

		List<CategoryDetails> categories = toList(service.getCategories(9));

		assertEquals(1, categories.size());
		verify(categoryRepository).getAllCategories(9);
	}

	@Test
	void getCategoriesBySubServiceSkipsSingleColumnRows() {
		ArrayList<Object[]> rows = new ArrayList<>();
		rows.add(new Object[] { 1 });
		when(categoryRepository.getAllCategories(anyInt())).thenReturn(rows);

		assertEquals(0, toList(service.getCategories(9)).size());
	}

	@Test
	void getActiveServiceTypesMapsFourColumnRows() {
		ArrayList<Object[]> rows = new ArrayList<>();
		rows.add(new Object[] { 1, "1097", "Helpline", Boolean.FALSE });
		when(serviceTypeRepository.findActiveServiceTypes()).thenReturn(rows);

		List<SubServices> serviceTypes = toList(service.getActiveServiceTypes());

		assertEquals(1, serviceTypes.size());
		assertEquals("1097", serviceTypes.get(0).getSubServiceName());
		assertFalse(serviceTypes.get(0).getDeleted());
	}

	@Test
	void getActiveServiceTypesSkipsShortRows() {
		ArrayList<Object[]> rows = new ArrayList<>();
		rows.add(new Object[] { 1, "1097", "Helpline" });
		when(serviceTypeRepository.findActiveServiceTypes()).thenReturn(rows);

		assertEquals(0, toList(service.getActiveServiceTypes()).size());
	}

	@Test
	void nullRowsAreSkippedByEveryProjection() {
		ArrayList<Object[]> rows = new ArrayList<>();
		rows.add(null);
		when(categoryRepository.findBy()).thenReturn(rows);
		when(categoryRepository.getAllCategories(anyInt())).thenReturn(rows);
		when(subCategoryRepository.findByCategoryID(anyInt())).thenReturn(rows);
		when(serviceTypeRepository.findActiveServiceTypes()).thenReturn(rows);

		assertEquals(0, toList(service.getCategories()).size());
		assertEquals(0, toList(service.getCategories(9)).size());
		assertEquals(0, toList(service.getSubCategories(4)).size());
		assertEquals(0, toList(service.getActiveServiceTypes()).size());
	}
}
