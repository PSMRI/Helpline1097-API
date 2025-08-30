package com.iemr.helpline1097.service.co.beneficiary;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Description;

import com.iemr.helpline1097.data.co.beneficiary.M_Promoservice;
import com.iemr.helpline1097.repository.co.beneficiary.IEMRPromoserviceRepositoryImplCustom;

@ExtendWith(MockitoExtension.class)
class IEMRPromoserviceDetailsServiceImplTest {

	@InjectMocks
	IEMRPromoserviceDetailsServiceImpl iEMRPromoserviceDetailsServiceImpl;

	@Mock
	private IEMRPromoserviceRepositoryImplCustom iEMRPromoserviceRepositoryImplCustom;

	@Test
	@Description("Test Case for adding promotion service details (TC_ADD_PROMOTION_SERVICE_DETAIL_001)")
	void addPromoServiceDetailTest() {

		M_Promoservice m_promoservice = new M_Promoservice();

		m_promoservice.setFamilyFriends("Family Friends");
		m_promoservice.setHealthcareWorker("Healthcare Worker");
		m_promoservice.setNotDisclosed("Not Disclosed");
		m_promoservice.setOthers("Others");
		m_promoservice.setPamphlet("Pamphlet");
		m_promoservice.setRadio("Radio");

		m_promoservice.toString();

		when(iEMRPromoserviceRepositoryImplCustom.save(m_promoservice)).thenReturn(m_promoservice);

		Assertions.assertEquals(m_promoservice,
				iEMRPromoserviceDetailsServiceImpl.addPromoServiceDetail(m_promoservice));

	}

}
