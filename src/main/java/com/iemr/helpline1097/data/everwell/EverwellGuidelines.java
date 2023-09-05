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
package com.iemr.helpline1097.data.everwell;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.gson.annotations.Expose;

import lombok.Data;

@Entity
@Table(name = "m_everwellguidelines")
@Data
public class EverwellGuidelines {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "egid")
	@Expose
	private Long id;
	@Expose
	@Column(name = "GuidelineName")
	private String guidelineName;
	@Expose
	@Column(name = "GuidelineDesc")
	private String guidelineDesc;
	@Expose
	@Column(name = "fileName")
	private String fileName;
	@Expose
	@Column(name = "fileExtension")
	private String fileExtension;
	@Expose
	@Column(name = "fileContent")
	private String fileContent;
	@Expose
	@Column(name = "category")
	private String category;
	@Column(name = "providerServiceMapID")
	private Integer providerServiceMapID;
	@Expose
	@Column(name = "userID")
	private Integer userID;
	@Expose
	@Column(name = "validFrom")
	private Timestamp validFrom;
	@Expose
	@Column(name = "validTill")
	private Timestamp validTill;
	@Transient
	private Integer adherencePercentage;
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
}
