package com.iemr.helpline1097.data.co.beneficiarycall;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import com.iemr.helpline1097.utils.mapper.OutputMapper;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(MockitoExtension.class)
class BeneficiaryCallTest {
    @InjectMocks
    private BeneficiaryCall beneficiaryCall;

    @Test
    void testGettersAndSetters() {
        // Arrange
        BeneficiaryCall beneficiaryCall = new BeneficiaryCall();

        // Act
        String actualToStringResult = beneficiaryCall.toString();
        Long actualBenCallID = beneficiaryCall.getBenCallID();
        List<BenCallServicesMappingHistory> actualBenCallServicesMappingHistories = beneficiaryCall
                .getBenCallServicesMappingHistories();
        Long actualBeneficiaryRegID = beneficiaryCall.getBeneficiaryRegID();
        String actualCallClosureType = beneficiaryCall.getCallClosureType();
        Integer actualCallEndUserID = beneficiaryCall.getCallEndUserID();
        Integer actualCallReceivedUserID = beneficiaryCall.getCallReceivedUserID();
        Timestamp actualCallTime = beneficiaryCall.getCallTime();
        Integer actualCalledServiceID = beneficiaryCall.getCalledServiceID();
        String actualCategory = beneficiaryCall.getCategory();
        String actualCreatedBy = beneficiaryCall.getCreatedBy();
        Timestamp actualCreatedDate = beneficiaryCall.getCreatedDate();
        Boolean actualDeleted = beneficiaryCall.getDeleted();
        Integer actualDispositionStatusID = beneficiaryCall.getDispositionStatusID();
        Long actualFeedbackServices = beneficiaryCall.getFeedbackServices();
        Long actualInformationServices = beneficiaryCall.getInformationServices();
        Boolean actualIs1097 = beneficiaryCall.getIs1097();
        Timestamp actualLastModDate = beneficiaryCall.getLastModDate();
        String actualModifiedBy = beneficiaryCall.getModifiedBy();
        Long actualReferralServices = beneficiaryCall.getReferralServices();
        String actualRemarks = beneficiaryCall.getRemarks();
        String actualServicesProvided = beneficiaryCall.getServicesProvided();

        // Assert
        assertEquals(
                "{\"benCallID\":null,\"calledServiceID\":null,\"is1097\":null,\"callTime\":null,\"remarks\":null,\"callClosureType"
                        + "\":null,\"dispositionStatusID\":null,\"callReceivedUserID\":null,\"callEndUserID\":null,\"createdBy\":null,"
                        + "\"createdDate\":null,\"callTypeID\":null,\"callTypeObj\":null,\"informationServices\":null,\"feedbackServices"
                        + "\":null,\"referralServices\":null,\"counsellingServices\":null}",
                actualToStringResult);
        assertNull(actualDeleted);
        assertNull(actualIs1097);
        assertNull(actualCallEndUserID);
        assertNull(actualCallReceivedUserID);
        assertNull(actualCalledServiceID);
        assertNull(actualDispositionStatusID);
        assertNull(actualBenCallID);
        assertNull(actualBeneficiaryRegID);
        assertNull(actualFeedbackServices);
        assertNull(actualInformationServices);
        assertNull(actualReferralServices);
        assertNull(actualCallClosureType);
        assertNull(actualCategory);
        assertNull(actualCreatedBy);
        assertNull(actualModifiedBy);
        assertNull(actualRemarks);
        assertNull(actualServicesProvided);
        assertNull(beneficiaryCall.getSubCategory());
        assertNull(actualCallTime);
        assertNull(actualCreatedDate);
        assertNull(actualLastModDate);
        assertNull(actualBenCallServicesMappingHistories);
    }

    @Test
    void testNewBeneficiaryCall() {
        // Arrange and Act
        BeneficiaryCall actualBeneficiaryCall = new BeneficiaryCall();

        // Assert
        assertNull(actualBeneficiaryCall.getCallTypeObj());
        assertNull(actualBeneficiaryCall.getDeleted());
        assertNull(actualBeneficiaryCall.getIs1097());
        assertNull(actualBeneficiaryCall.getCallEndUserID());
        assertNull(actualBeneficiaryCall.getCallReceivedUserID());
        assertNull(actualBeneficiaryCall.getCallTypeID());
        assertNull(actualBeneficiaryCall.getCalledServiceID());
        assertNull(actualBeneficiaryCall.getDispositionStatusID());
        assertNull(actualBeneficiaryCall.getBenCallID());
        assertNull(actualBeneficiaryCall.getBeneficiaryRegID());
        assertNull(actualBeneficiaryCall.getCounsellingServices());
        assertNull(actualBeneficiaryCall.getFeedbackServices());
        assertNull(actualBeneficiaryCall.getInformationServices());
        assertNull(actualBeneficiaryCall.getReferralServices());
        assertNull(actualBeneficiaryCall.getCallClosureType());
        assertNull(actualBeneficiaryCall.getCategory());
        assertNull(actualBeneficiaryCall.getCreatedBy());
        assertNull(actualBeneficiaryCall.getModifiedBy());
        assertNull(actualBeneficiaryCall.getRemarks());
        assertNull(actualBeneficiaryCall.getServicesProvided());
        assertNull(actualBeneficiaryCall.getSubCategory());
        assertNull(actualBeneficiaryCall.getCallTime());
        assertNull(actualBeneficiaryCall.getCreatedDate());
        assertNull(actualBeneficiaryCall.getLastModDate());
        assertNull(actualBeneficiaryCall.getBenCallServicesMappingHistories());
    }

    @Test
    void testNewBeneficiaryCall2() {
        // Arrange and Act
        BeneficiaryCall actualBeneficiaryCall = new BeneficiaryCall(1L, true, "Jan 1, 2020 8:00am GMT+0100");

        // Assert
        assertEquals("Jan 1, 2020 8:00am GMT+0100", actualBeneficiaryCall.getCreatedBy());
        assertEquals(1L, actualBeneficiaryCall.getBeneficiaryRegID().longValue());
        assertTrue(actualBeneficiaryCall.getIs1097());
    }

    @Test
    void testNewBeneficiaryCall3() {
        // Arrange
        Timestamp callTime = mock(Timestamp.class);

        // Act
        BeneficiaryCall actualBeneficiaryCall = new BeneficiaryCall(1L, "", 1, true, callTime, "Remarks",
                "Call Closure Type", 1);

        // Assert
        assertEquals("Call Closure Type", actualBeneficiaryCall.getCallClosureType());
        assertEquals("Remarks", actualBeneficiaryCall.getRemarks());
        assertNull(actualBeneficiaryCall.getBenCallServicesMappingHistories());
        assertEquals(1, actualBeneficiaryCall.getCalledServiceID().intValue());
        assertEquals(1, actualBeneficiaryCall.getDispositionStatusID().intValue());
        assertEquals(1L, actualBeneficiaryCall.getBenCallID().longValue());
        assertTrue(actualBeneficiaryCall.getIs1097());
        assertSame(callTime, actualBeneficiaryCall.getCallTime());
    }

   
    @Test
    void testNewBeneficiaryCall4() {
        // Arrange
        Timestamp callTime = mock(Timestamp.class);
        Timestamp createdDate = mock(Timestamp.class);

        CallType callTypeObj = new CallType();
        callTypeObj.setBeneficiaryCalls(new ArrayList<>());
        callTypeObj.setCallGroupType("Call Group Type");
        callTypeObj.setCallType("Call Type");
        callTypeObj.setCallTypeDesc("Call Type Desc");
        callTypeObj.setCallTypeID(1);
        callTypeObj.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        callTypeObj.setCreatedDate(mock(Timestamp.class));
        callTypeObj.setDeleted(true);
        callTypeObj.setFitForFollowUp(true);
        callTypeObj.setFitToBlock(true);
        callTypeObj.setLastModDate(mock(Timestamp.class));
        callTypeObj.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        callTypeObj.setOutputMapper(new OutputMapper());
        callTypeObj.setProviderServiceMapID(1);

        // Act
        BeneficiaryCall actualBeneficiaryCall = new BeneficiaryCall(1L, callTime, "Remarks", 1L, 1L, 1L, createdDate, 1L,
                callTypeObj);

        // Assert
        assertEquals("Call Group Type", actualBeneficiaryCall.getCallTypeObj().getCallGroupType());
        assertEquals("Remarks", actualBeneficiaryCall.getRemarks());
        assertEquals(1L, actualBeneficiaryCall.getBenCallID().longValue());
        assertEquals(1L, actualBeneficiaryCall.getCounsellingServices().longValue());
        assertEquals(1L, actualBeneficiaryCall.getFeedbackServices().longValue());
        assertEquals(1L, actualBeneficiaryCall.getInformationServices().longValue());
        assertEquals(1L, actualBeneficiaryCall.getReferralServices().longValue());
        assertSame(callTime, actualBeneficiaryCall.getCallTime());
        assertSame(createdDate, actualBeneficiaryCall.getCreatedDate());
    }

    @Test
    void testNewBeneficiaryCall5() {
        // Arrange
        Timestamp callTime = mock(Timestamp.class);
        Timestamp createdDate = mock(Timestamp.class);

        CallType callTypeObj = new CallType();
        callTypeObj.setBeneficiaryCalls(new ArrayList<>());
        callTypeObj.setCallGroupType("Call Group Type");
        callTypeObj.setCallType("Call Type");
        callTypeObj.setCallTypeDesc("Call Type Desc");
        callTypeObj.setCallTypeID(1);
        callTypeObj.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        callTypeObj.setCreatedDate(mock(Timestamp.class));
        callTypeObj.setDeleted(true);
        callTypeObj.setFitForFollowUp(true);
        callTypeObj.setFitToBlock(true);
        callTypeObj.setLastModDate(mock(Timestamp.class));
        callTypeObj.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        callTypeObj.setOutputMapper(new OutputMapper());
        callTypeObj.setProviderServiceMapID(1);

        // Act
        BeneficiaryCall actualBeneficiaryCall = new BeneficiaryCall(1L, callTime, "Remarks", 0L, 1L, 1L, createdDate, 1L,
                callTypeObj);

        // Assert
        assertEquals("Call Group Type", actualBeneficiaryCall.getCallTypeObj().getCallGroupType());
        assertEquals("Remarks", actualBeneficiaryCall.getRemarks());
        assertEquals(0L, actualBeneficiaryCall.getInformationServices().longValue());
        assertEquals(1L, actualBeneficiaryCall.getBenCallID().longValue());
        assertEquals(1L, actualBeneficiaryCall.getCounsellingServices().longValue());
        assertEquals(1L, actualBeneficiaryCall.getFeedbackServices().longValue());
        assertEquals(1L, actualBeneficiaryCall.getReferralServices().longValue());
        assertSame(callTime, actualBeneficiaryCall.getCallTime());
        assertSame(createdDate, actualBeneficiaryCall.getCreatedDate());
    }

    @Test
    void testNewBeneficiaryCall6() {
        // Arrange
        Timestamp callTime = mock(Timestamp.class);
        Timestamp createdDate = mock(Timestamp.class);

        CallType callTypeObj = new CallType();
        callTypeObj.setBeneficiaryCalls(new ArrayList<>());
        callTypeObj.setCallGroupType("Call Group Type");
        callTypeObj.setCallType("Call Type");
        callTypeObj.setCallTypeDesc("Call Type Desc");
        callTypeObj.setCallTypeID(1);
        callTypeObj.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        callTypeObj.setCreatedDate(mock(Timestamp.class));
        callTypeObj.setDeleted(true);
        callTypeObj.setFitForFollowUp(true);
        callTypeObj.setFitToBlock(true);
        callTypeObj.setLastModDate(mock(Timestamp.class));
        callTypeObj.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        callTypeObj.setOutputMapper(new OutputMapper());
        callTypeObj.setProviderServiceMapID(1);

        // Act
        BeneficiaryCall actualBeneficiaryCall = new BeneficiaryCall(1L, callTime, "Remarks", 1L, 0L, 1L, createdDate, 1L,
                callTypeObj);

        // Assert
        assertEquals("Call Group Type", actualBeneficiaryCall.getCallTypeObj().getCallGroupType());
        assertEquals("Remarks", actualBeneficiaryCall.getRemarks());
        assertEquals(0L, actualBeneficiaryCall.getFeedbackServices().longValue());
        assertEquals(1L, actualBeneficiaryCall.getBenCallID().longValue());
        assertEquals(1L, actualBeneficiaryCall.getCounsellingServices().longValue());
        assertEquals(1L, actualBeneficiaryCall.getInformationServices().longValue());
        assertEquals(1L, actualBeneficiaryCall.getReferralServices().longValue());
        assertSame(callTime, actualBeneficiaryCall.getCallTime());
        assertSame(createdDate, actualBeneficiaryCall.getCreatedDate());
    }

    @Test
    void testNewBeneficiaryCall7() {
        // Arrange
        Timestamp callTime = mock(Timestamp.class);
        Timestamp createdDate = mock(Timestamp.class);

        CallType callTypeObj = new CallType();
        callTypeObj.setBeneficiaryCalls(new ArrayList<>());
        callTypeObj.setCallGroupType("Call Group Type");
        callTypeObj.setCallType("Call Type");
        callTypeObj.setCallTypeDesc("Call Type Desc");
        callTypeObj.setCallTypeID(1);
        callTypeObj.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        callTypeObj.setCreatedDate(mock(Timestamp.class));
        callTypeObj.setDeleted(true);
        callTypeObj.setFitForFollowUp(true);
        callTypeObj.setFitToBlock(true);
        callTypeObj.setLastModDate(mock(Timestamp.class));
        callTypeObj.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        callTypeObj.setOutputMapper(new OutputMapper());
        callTypeObj.setProviderServiceMapID(1);

        // Act
        BeneficiaryCall actualBeneficiaryCall = new BeneficiaryCall(1L, callTime, "Remarks", 1L, 1L, 0L, createdDate, 1L,
                callTypeObj);

        // Assert
        assertEquals("Call Group Type", actualBeneficiaryCall.getCallTypeObj().getCallGroupType());
        assertEquals("Remarks", actualBeneficiaryCall.getRemarks());
        assertEquals(0L, actualBeneficiaryCall.getReferralServices().longValue());
        assertEquals(1L, actualBeneficiaryCall.getBenCallID().longValue());
        assertEquals(1L, actualBeneficiaryCall.getCounsellingServices().longValue());
        assertEquals(1L, actualBeneficiaryCall.getFeedbackServices().longValue());
        assertEquals(1L, actualBeneficiaryCall.getInformationServices().longValue());
        assertSame(callTime, actualBeneficiaryCall.getCallTime());
        assertSame(createdDate, actualBeneficiaryCall.getCreatedDate());
    }

    @Test
    void testNewBeneficiaryCall8() {
        // Arrange
        Timestamp callTime = mock(Timestamp.class);
        Timestamp createdDate = mock(Timestamp.class);

        CallType callTypeObj = new CallType();
        callTypeObj.setBeneficiaryCalls(new ArrayList<>());
        callTypeObj.setCallGroupType("Call Group Type");
        callTypeObj.setCallType("Call Type");
        callTypeObj.setCallTypeDesc("Call Type Desc");
        callTypeObj.setCallTypeID(1);
        callTypeObj.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        callTypeObj.setCreatedDate(mock(Timestamp.class));
        callTypeObj.setDeleted(true);
        callTypeObj.setFitForFollowUp(true);
        callTypeObj.setFitToBlock(true);
        callTypeObj.setLastModDate(mock(Timestamp.class));
        callTypeObj.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        callTypeObj.setOutputMapper(new OutputMapper());
        callTypeObj.setProviderServiceMapID(1);

        // Act
        BeneficiaryCall actualBeneficiaryCall = new BeneficiaryCall(1L, callTime, "Remarks", 1L, 1L, 1L, createdDate, 0L,
                callTypeObj);

        // Assert
        assertEquals("Call Group Type", actualBeneficiaryCall.getCallTypeObj().getCallGroupType());
        assertEquals("Remarks", actualBeneficiaryCall.getRemarks());
        assertEquals(0L, actualBeneficiaryCall.getCounsellingServices().longValue());
        assertEquals(1L, actualBeneficiaryCall.getBenCallID().longValue());
        assertEquals(1L, actualBeneficiaryCall.getFeedbackServices().longValue());
        assertEquals(1L, actualBeneficiaryCall.getInformationServices().longValue());
        assertEquals(1L, actualBeneficiaryCall.getReferralServices().longValue());
        assertSame(callTime, actualBeneficiaryCall.getCallTime());
        assertSame(createdDate, actualBeneficiaryCall.getCreatedDate());
    }
}
