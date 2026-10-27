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
package com.iemr.helpline1097.utils.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.iemr.helpline1097.data.co.services.CategoryDetails;

class OutputMapperTest {

	@BeforeEach
	void primeTheSharedBuilder() {
		// gson() reads a static builder that only the constructor populates.
		new OutputMapper();
	}

	@Test
	void gsonReturnsAUsableMapper() {
		assertNotNull(OutputMapper.gson());
	}

	@Test
	void onlyExposedFieldsAreSerialised() {
		String json = OutputMapper.gson().toJson(new CategoryDetails(4, "Nutrition"));

		assertTrue(json.contains("\"categoryID\":4"));
		assertTrue(json.contains("\"categoryName\":\"Nutrition\""));
	}

	@Test
	void nullFieldsAreKeptInTheOutput() {
		assertTrue(OutputMapper.gson().toJson(new CategoryDetails()).contains("null"));
	}

	@Test
	void longsAreSerialisedAsStrings() {
		assertEquals("\"7\"", OutputMapper.gson().toJson(7L));
	}
}
