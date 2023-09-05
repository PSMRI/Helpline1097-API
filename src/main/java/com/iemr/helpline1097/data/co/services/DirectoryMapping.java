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
package com.iemr.helpline1097.data.co.services;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.iemr.helpline1097.utils.mapper.OutputMapper;

import lombok.Data;

@Entity
@Table(name = "m_institutedirectorymapping")
@Data
public class DirectoryMapping {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "InstituteDirMapID")
	@Expose
	private Long instituteDirMapID;

	@Column(name = "InstitutionID")
	@Expose
	private Integer institutionID;
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "InstitutionID", insertable = false, updatable = false)
	@Expose
	private InstitutionDetails institutionDetails;

	@Column(name = "InstituteDirectoryID")
	@Expose
	private Integer instituteDirectoryID;
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "InstituteDirectoryID", insertable = false, updatable = false)
	@Expose
	private Directory directory;

	@Column(name = "InstituteSubDirectoryID")
	@Expose
	private Integer instituteSubDirectoryID;
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "InstituteSubDirectoryID", insertable = false, updatable = false)
	@Expose
	private InstituteSubDirectory subDirectory;

	@Expose
	@Column(name = "InstituteRouteDirectoryID")
	private Integer instituteRouteDirectoryID;
	@Column(name = "Deleted", insertable = false, updatable = true)
	@Expose
	private Boolean deleted;
	@Column(name = "CreatedBy")
	@Expose
	private String createdBy;
	@Column(name = "CreatedDate", insertable = false, updatable = false)
	private Timestamp createdDate;
	@Column(name = "ModifiedBy")
	private String modifiedBy;
	@Column(name = "LastModDate", insertable = false, updatable = false)
	private Timestamp lastModDate;

	public DirectoryMapping() {
	}

	public DirectoryMapping(Long instituteDirMapID, Integer institutionID, String institutionName, String address,
			String contactNo1, String contactNo2, String contactNo3) {
		this.instituteDirMapID = instituteDirMapID;
		this.institutionDetails = new InstitutionDetails(institutionID, institutionName, address, contactNo1,
				contactNo2, contactNo3);
	}

	public DirectoryMapping(Long instituteDirMapID, InstitutionDetails institution) {
		this.instituteDirMapID = instituteDirMapID;
		this.institutionDetails = institution;
	}

	@Override
	public String toString() {
		return OutputMapper.gson().toJson(this);
	}
}
