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
package com.iemr.helpline1097.repository.everwell;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iemr.helpline1097.data.everwell.EverwellGuidelines;

@Repository
@RestResource(exported = false)
public interface EverwellGuidelinesRepo extends CrudRepository<EverwellGuidelines, Integer> { 
	@Query("select b from EverwellGuidelines b "
			+ "where b.category = :category and b.providerServiceMapID = :providerServiceMapID and b.deleted!=true ")
	public List<EverwellGuidelines> findGuidelinesByCategory(@Param("category") String category,@Param("providerServiceMapID") Integer providerServiceMapID);
	@Query("select b from EverwellGuidelines b "
			+ "where b.providerServiceMapID = :providerServiceMapID and b.deleted!=true ")
	public List<EverwellGuidelines> findByProviderServiceMapID(@Param("providerServiceMapID") Integer providerServiceMapID);
	
	@Query("select b from EverwellGuidelines b "
			+ "where b.category = :category and b.providerServiceMapID = :providerServiceMapID and b.deleted!=true ")
	public List<EverwellGuidelines> checkIfDocAlreadyexists(@Param("category") String category,@Param("providerServiceMapID") Integer providerServiceMapID);
	

	@Transactional
	@Modifying
	@Query("update EverwellGuidelines set deleted = true,modifiedBy =:modifiedBy where id = :id")
	public int deleteGuideline(@Param("id") Integer id,@Param("modifiedBy") String modifiedBy);
}
