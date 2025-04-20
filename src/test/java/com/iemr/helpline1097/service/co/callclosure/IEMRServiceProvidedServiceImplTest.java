package com.iemr.helpline1097.service.co.callclosure;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Description;

import com.iemr.helpline1097.data.co.beneficiarycall.ServiceProvided;
import com.iemr.helpline1097.repository.co.beneficiary.IEMRServiceProvidedRepositoryImplCustom;

@ExtendWith(MockitoExtension.class)
class IEMRServiceProvidedServiceImplTest {

	@InjectMocks
	IEMRServiceProvidedServiceImpl iEMRServiceProvidedServiceImpl;

	@Mock
	private IEMRServiceProvidedRepositoryImplCustom iEMRServiceProvidedRepositoryImplCustom;

	@Test
	@Description("Test Case for adding a service provided (TC_ADD_SERVICE_PROVIDED_001)")
	void addServiceProvidedTest() {

		ServiceProvided m_serviceprovided = new ServiceProvided();

		// Act
		m_serviceprovided.setCounsellingService("Counselling Service");
		m_serviceprovided.setFeedbackSystem("Feedback System");
		m_serviceprovided.setInformationService("Information Service");
		m_serviceprovided.setReferralService("Referral Service");
		m_serviceprovided.setServiceID(1L);
		
		m_serviceprovided.toString();
		
		when(iEMRServiceProvidedRepositoryImplCustom.save(m_serviceprovided)).thenReturn(m_serviceprovided);
		
		Assertions.assertEquals(m_serviceprovided,
				iEMRServiceProvidedServiceImpl.addServiceProvided(m_serviceprovided));
	}

}
