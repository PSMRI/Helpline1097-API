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
package com.iemr.helpline1097.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.iemr.helpline1097.data.co.services.CategoryDetails;
import com.iemr.helpline1097.data.co.services.DesignationDetails;
import com.iemr.helpline1097.data.co.services.Directory;
import com.iemr.helpline1097.data.co.services.DirectoryMapping;
import com.iemr.helpline1097.data.co.services.DistrictBlock;
import com.iemr.helpline1097.data.co.services.DistrictBranchMapping;
import com.iemr.helpline1097.data.co.services.Districts;
import com.iemr.helpline1097.data.co.services.Institute;
import com.iemr.helpline1097.data.co.services.InstituteSubDirectory;
import com.iemr.helpline1097.data.co.services.InstitutionDetails;
import com.iemr.helpline1097.data.co.services.States;
import com.iemr.helpline1097.data.co.services.SubCategoryDetails;

class ServicesEntityAccessorsTest {

	@BeforeEach
	void primeOutputMapper() {
		EntityAccessors.primeOutputMapper();
	}

	@Test
	void everyServicesEntityRoundTripsItsAccessors() {
		EntityAccessors.assertRoundTrips(CategoryDetails.class, SubCategoryDetails.class, DesignationDetails.class,
				Directory.class, DirectoryMapping.class, DistrictBlock.class, DistrictBranchMapping.class,
				Districts.class, Institute.class, InstituteSubDirectory.class, InstitutionDetails.class,
				States.class);
	}

	@Test
	void categoryDetailsIdAndNameConstructorSetsBothFields() {
		CategoryDetails category = new CategoryDetails(4, "Nutrition");

		assertEquals(4, category.getCategoryID());
		assertEquals("Nutrition", category.getCategoryName());
		assertNull(category.getCategoryDesc());
		assertNotNull(category.toString());
	}

	@Test
	void subCategoryDetailsIdAndNameConstructorSetsBothFields() {
		SubCategoryDetails subCategory = new SubCategoryDetails(5, "Diet");

		assertEquals(5, subCategory.getSubCategoryID());
		assertEquals("Diet", subCategory.getSubCategoryName());
	}

	@Test
	void subCategoryDetailsIdNameAndPathConstructorSetsAllThree() {
		SubCategoryDetails subCategory = new SubCategoryDetails(5, "Diet", "/docs/diet.pdf");

		assertEquals(5, subCategory.getSubCategoryID());
		assertEquals("Diet", subCategory.getSubCategoryName());
		assertEquals("/docs/diet.pdf", subCategory.getSubCatFilePath());
	}

	@Test
	void subCategoryDetailsDescriptionConstructorSetsTheDescriptionInsteadOfTheId() {
		SubCategoryDetails subCategory = new SubCategoryDetails("Diet advice", "Diet", "/docs/diet.pdf");

		assertEquals("Diet advice", subCategory.getSubCategoryDesc());
		assertEquals("Diet", subCategory.getSubCategoryName());
		assertNull(subCategory.getSubCategoryID());
	}

	@Test
	void designationDetailsConstructorSetsIdAndName() {
		DesignationDetails designation = new DesignationDetails(2, "Doctor");

		assertEquals(2, designation.getDesignationID());
		assertEquals("Doctor", designation.getDesignationName());
	}

	@Test
	void directoryConstructorIsAcceptedAndLeavesTheProviderMappingUnset() {
		Directory directory = new Directory(3, "Hospitals");

		assertNull(directory.getProviderServiceMapID());
		assertNotNull(directory.toString());
	}

	@Test
	void directoryMappingInstitutionConstructorNestsTheInstitution() {
		InstitutionDetails institution = new InstitutionDetails();

		DirectoryMapping mapping = new DirectoryMapping(9L, institution);

		assertEquals(9L, mapping.getInstituteDirMapID());
		assertNotNull(mapping.toString());
	}

	@Test
	void districtsIdAndNameConstructorSetsBothFields() {
		Districts district = new Districts(6, "Pune");

		assertEquals(6, district.getDistrictID());
		assertEquals("Pune", district.getDistrictName());
	}

	@Test
	void districtsStateConstructorAlsoCarriesTheState() {
		Districts district = new Districts(6, "Pune", 1, "Maharashtra");

		assertEquals(6, district.getDistrictID());
		assertEquals("Pune", district.getDistrictName());
		assertNotNull(district.toString());
	}

	@Test
	void districtBlockConstructorSetsIdAndName() {
		DistrictBlock block = new DistrictBlock(8, "Haveli");

		assertEquals(8, block.getBlockID());
		assertEquals("Haveli", block.getBlockName());
	}

	@Test
	void instituteConstructorSetsIdAndName() {
		Institute institute = new Institute(10, "District Hospital");

		assertEquals(10, institute.getInstitutionID());
		assertEquals("District Hospital", institute.getInstitutionName());
	}

	@Test
	void instituteSubDirectoryConstructorSetsIdAndName() {
		InstituteSubDirectory subDirectory = new InstituteSubDirectory(12, "Paediatrics");

		assertEquals(12, subDirectory.getInstituteSubDirectoryID());
		assertEquals("Paediatrics", subDirectory.getInstituteSubDirectoryName());
	}

	@Test
	void institutionDetailsContactConstructorSetsTheAddressAndPhones() {
		InstitutionDetails institution = new InstitutionDetails(10, "District Hospital", "Main Road", "111", "222",
				"333");

		assertEquals(10, institution.getInstitutionID());
		assertEquals("District Hospital", institution.getInstitutionName());
		assertEquals("Main Road", institution.getAddress());
		assertEquals("111", institution.getContactNo1());
		assertEquals("222", institution.getContactNo2());
		assertEquals("333", institution.getContactNo3());
	}

	@Test
	void institutionDetailsLocationConstructorSetsTheGeographyIds() {
		InstitutionDetails institution = new InstitutionDetails(10, "District Hospital", 1, 6, 8);

		assertEquals(10, institution.getInstitutionID());
		assertEquals(1, institution.getStateID());
		assertEquals(6, institution.getDistrictID());
		assertEquals(8, institution.getDistrictBranchMappingID());
	}

	@Test
	void instituteLocationConstructorSetsTheGeographyIds() {
		Institute institute = new Institute(10, "District Hospital", 1, 6, 8);

		assertEquals(10, institute.getInstitutionID());
		assertEquals(1, institute.getStateID());
		assertEquals(6, institute.getDistrictID());
		assertEquals(8, institute.getDistrictBranchMappingID());
	}

	@Test
	void directoryMappingContactConstructorSetsTheAddressAndPhones() {
		DirectoryMapping mapping = new DirectoryMapping(9L, 10, "District Hospital", "Main Road", "111", "222",
				"333");

		assertEquals(9L, mapping.getInstituteDirMapID());
		assertNotNull(mapping.toString());
	}

	@Test
	void instituteSubDirectoryDirectoryConstructorSetsTheParentDirectory() {
		InstituteSubDirectory subDirectory = new InstituteSubDirectory(12, 3, "Paediatrics");

		assertEquals(12, subDirectory.getInstituteSubDirectoryID());
		assertEquals(3, subDirectory.getInstituteDirectoryID());
		assertEquals("Paediatrics", subDirectory.getInstituteSubDirectoryName());
	}
}
