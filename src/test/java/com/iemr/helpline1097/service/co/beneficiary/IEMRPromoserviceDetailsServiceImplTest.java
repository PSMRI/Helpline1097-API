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
package com.iemr.helpline1097.service.co.beneficiary;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.iemr.helpline1097.data.co.beneficiary.M_Promoservice;
import com.iemr.helpline1097.repository.co.beneficiary.IEMRPromoserviceRepositoryImplCustom;

@ExtendWith(MockitoExtension.class)
class IEMRPromoserviceDetailsServiceImplTest {

	@Mock
	private IEMRPromoserviceRepositoryImplCustom promoserviceRepository;

	@InjectMocks
	private IEMRPromoserviceDetailsServiceImpl service;

	@Test
	void addPromoServiceDetailDelegatesToRepository() {
		M_Promoservice payload = new M_Promoservice();
		when(promoserviceRepository.save(payload)).thenReturn(payload);

		assertSame(payload, service.addPromoServiceDetail(payload));
	}
}
