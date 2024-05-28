package com.iemr.helpline1097.controller.co.callhandling;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.iemr.helpline1097.data.co.calltype.M_Calltype;
import com.iemr.helpline1097.service.co.callhandling.IEMRCalltypeServiceImpl;
import com.iemr.helpline1097.utils.response.OutputResponse;

import jakarta.ws.rs.NotFoundException;

@ExtendWith(MockitoExtension.class)
class CalltypeControllerTest {

	@InjectMocks
	CalltypeController calltypeController;

	@Mock
	private IEMRCalltypeServiceImpl iEMRCalltypeServiceImpl;

	@Test
	void addCallTypeTest() {

		String request = "Add type";

		M_Calltype payload = new M_Calltype();

		payload.setId(12L);
		payload.setCallType("callType");
		payload.setRemarks("remarks");
		payload.setInvalidType("invalidType");

		payload.toString();

		M_Calltype m_calltype = payload;

		String response = calltypeController.addCallType(request);

		Assertions.assertNotNull(m_calltype);
		Assertions.assertEquals(response, calltypeController.addCallType(request));

	}
	
	@Test
	void addCallTypeTest_AsNull() {

		String request = "Add type";
		
		M_Calltype m_calltype = null;

		String response = calltypeController.addCallType(request);

		Assertions.assertNull(m_calltype);
		Assertions.assertEquals(response, calltypeController.addCallType(request));

	}
	
	@Test
	void addCallTypeTest_Exception() {

		String request = "{\"statusCode\":5000,\"errorMessage\":\"Failed with generic error\",\"status\":\"FAILURE\"}";

		String response = calltypeController.addCallType(request);

		Assertions.assertEquals(response, calltypeController.addCallType(request));

	}
	
	
	@Test
	void getAllCallTypesTest() {
		
		int id = 12;
		
		M_Calltype m_calltypes = new M_Calltype();

		m_calltypes.setId(12L);
		m_calltypes.setCallType("callType");
		m_calltypes.setRemarks("remarks");
		m_calltypes.setInvalidType("invalidType");

		m_calltypes.toString();
		
		when(iEMRCalltypeServiceImpl.getAllCalltypes(id)).thenReturn(m_calltypes);
		
		OutputResponse response = new OutputResponse();
		response.setResponse(m_calltypes.toString());
		
		
		Assertions.assertEquals(response.toString(), calltypeController.getAllCallTypes(id));
		
	}
	
	@Test
	void getAllCallTypesTest_Exception() {

		int id = 0;
		
		when(iEMRCalltypeServiceImpl.getAllCalltypes(id)).thenThrow(NotFoundException.class);

		String response = calltypeController.getAllCallTypes(id);

		Assertions.assertEquals(response, calltypeController.getAllCallTypes(id));

	}

}
