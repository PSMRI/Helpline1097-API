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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iemr.helpline1097.data.co.services.CategoryDetails;
import com.iemr.helpline1097.repository.co.services.CategoryRepository;
import com.iemr.helpline1097.utils.exception.IEMRException;
import com.iemr.helpline1097.utils.mapper.InputMapper;

@Service
public class CategoryServiceImpl implements CategoryService {
	/**
	 * category repository
	 */
	private CategoryRepository categoryRepository;

	/**
	 * inject category service
	 */
	@Autowired
	public void setCategoryRepository(CategoryRepository categoryRepository) {

		this.categoryRepository = categoryRepository;
	}

	private InputMapper inputMapper = new InputMapper();

	@Override
	public List<CategoryDetails> getAllCategories(String request) throws IEMRException {
		CategoryDetails categoryDetails = inputMapper.gson().fromJson(request, CategoryDetails.class);
		List<CategoryDetails> categoryList = new ArrayList<CategoryDetails>();
		ArrayList<Objects[]> lists = categoryRepository.getAllCategories(categoryDetails.getSubServiceID());
		for (Object[] objects : lists) {
			if (objects != null && objects.length >= 2) {
				categoryList.add(new CategoryDetails((Integer) objects[0], (String) objects[1]));
			}
		}

		return categoryList;
	}

	@Override
	public List<CategoryDetails> getAllCategories() {
		List<CategoryDetails> categoryList = new ArrayList<CategoryDetails>();
		ArrayList<Objects[]> lists = categoryRepository.findBy();

		for (Object[] objects : lists) {

			if (objects != null && objects.length > 0) {
				categoryList.add(new CategoryDetails((Integer) objects[0], (String) objects[1]));
			}
		}

		return categoryList;
	}
}
