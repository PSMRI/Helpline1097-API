package com.iemr.helpline1097.service.co.callhandling;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Description;

import com.iemr.helpline1097.data.co.calltype.M_Calltype;
import com.iemr.helpline1097.repository.co.calltype.IEMRCalltypeRepositoryImplCustom;

@ExtendWith(MockitoExtension.class)
class IEMRCalltypeServiceImplTest {

	@InjectMocks
	IEMRCalltypeServiceImpl iEMRCalltypeServiceImpl;

	@Mock
	private IEMRCalltypeRepositoryImplCustom iEMRCalltypeRepositoryImplCustom;

	@Test
	@Description("Tests adding a call type with valid data (TC_AddCalltype_ValidData_001)")
	void addCalltypeTest() {

		String callType = "callType";
		String remarks = "remarks";
		String invalidType = "invalidType";

		M_Calltype m_calltype = new M_Calltype("callType", "remarks", "invalidType");
		m_calltype.setId(null);
		m_calltype.setCallType(callType);
		m_calltype.setRemarks(remarks);
		m_calltype.setInvalidType(invalidType);

		m_calltype.toString();

		when(iEMRCalltypeRepositoryImplCustom.save(m_calltype)).thenReturn(m_calltype);

		Assertions.assertEquals(m_calltype, iEMRCalltypeServiceImpl.addCalltype(m_calltype));
	}
	
	@Test
	@Description("Tests adding a call type with missing call type (TC_AddCalltype_MissingCallType_002)")
	public void testAddCalltype_MissingCallType() {
	  String remarks = "Additional details";
	  String invalidType = "N";

	  try {
	    iEMRCalltypeServiceImpl.addCalltype(null, remarks, invalidType);
	  } catch (IllegalArgumentException e) {
	    // Assert the expected exception message
	    assertEquals("Call type cannot be null or empty", e.getMessage());
	  } catch (Exception e) {
	    // If a different exception is thrown, fail the test with details
	    fail("Unexpected exception: " + e.getClass().getName() + " with message: " + e.getMessage());
	  }
	}

	@Test
	@Description("Tests adding a call type with empty call type (TC_AddCalltype_EmptyCallType_003)")
	public void testAddCalltype_EmptyCallType() {
	  String remarks = "Additional details";
	  String invalidType = "N";

	  try {
	    iEMRCalltypeServiceImpl.addCalltype("", remarks, invalidType);
	  } catch (IllegalArgumentException e) {
	    // Assert the expected exception message
	    assertEquals("Call type cannot be null or empty", e.getMessage());
	  } catch (Exception e) {
	    // If a different exception is thrown, fail the test with details
	    fail("Unexpected exception: " + e.getClass().getName() + " with message: " + e.getMessage());
	  }
	}

	@Test
	@Description("Tests retrieving all call types (TC_Get_All_Calltypes_001)")
	void getAllCalltypesTest() {

		int id = 12;

		M_Calltype m_calltype = new M_Calltype("callType", "remarks", "invalidType");
		m_calltype.setId(12L);
		m_calltype.setCallType("callType");
		m_calltype.setRemarks("remarks");
		m_calltype.setInvalidType("invalidType");

		m_calltype.toString();

		when(iEMRCalltypeRepositoryImplCustom.findById(new Long(id))).thenReturn(Optional.of(m_calltype));

		Assertions.assertEquals(m_calltype, iEMRCalltypeServiceImpl.getAllCalltypes(id));
	}

	@Test
	@Description("Tests updating a call type (TC_Update_Calltype_001)")
	void updateCalltypeTest() {

		M_Calltype m_Calltype = new M_Calltype("callType", "remarks", "invalidType");
		m_Calltype.setId(12L);
		m_Calltype.setCallType("callType");
		m_Calltype.setRemarks("remarks");
		m_Calltype.setInvalidType("invalidType");

		m_Calltype.toString();

		when(iEMRCalltypeRepositoryImplCustom.updateCallType(new Long(m_Calltype.getId()), m_Calltype.getCallType(),
				m_Calltype.getRemarks())).thenReturn(12);

		Assertions.assertEquals("success", iEMRCalltypeServiceImpl.updateCalltype(m_Calltype));
	}

}
