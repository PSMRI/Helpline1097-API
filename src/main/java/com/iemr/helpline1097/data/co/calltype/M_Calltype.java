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
package com.iemr.helpline1097.data.co.calltype;

import com.google.gson.annotations.Expose;
import com.iemr.helpline1097.utils.mapper.OutputMapper;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.Data;

@Entity
@Data
public class M_Calltype
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Expose
	private Long id;
	@Expose
	private String callType;
	@Expose
	private String remarks;
	@Expose
	private String invalidType;

	public M_Calltype()
	{
		super();
	}

	public M_Calltype(String callType, String remarks, String invalidType)
	{

		this.callType = callType;
		this.remarks = remarks;
		this.invalidType = invalidType;
	}

	public Long getId()
	{
		return id;
	}

	public String getCallType()
	{
		return callType;
	}

	public void setCallType(String callType)
	{
		this.callType = callType;
	}

	public String getRemarks()
	{
		return remarks;
	}

	public void setRemarks(String remarks)
	{
		this.remarks = remarks;
	}

	public String getInvalidType()
	{
		return invalidType;
	}

	public void setInvalidType(String invalidType)
	{
		this.invalidType = invalidType;
	}

	@Transient
	private OutputMapper outputMapper = new OutputMapper();

	@Override
	public String toString()
	{
		return outputMapper.gson().toJson(this);
	}
	
	
}
