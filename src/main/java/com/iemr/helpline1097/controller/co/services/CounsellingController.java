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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import com.iemr.helpline1097.utils.mapper.InputMapper;

@RestController
public class CounsellingController
{
	InputMapper inputMapper = new InputMapper();
	Logger logger = LoggerFactory.getLogger(CommonController.class);

	/*
	 * @CrossOrigin()
	 * 
	 * @RequestMapping(value = "/api/helpline1097/co/get/designation", method = RequestMethod.POST) public
	 * Iterable<DesignationDetails> getDesignations(){
	 * 
	 * return designationService.getDesignations(); }
	 * 
	 *//**
		 * Designation Service
		 *//*
		 * private DesignationService designationService;
		 * 
		 * @Autowired public void setDesignationService(DesignationService designationService){
		 * 
		 * this.designationService = designationService; }
		 */
}
