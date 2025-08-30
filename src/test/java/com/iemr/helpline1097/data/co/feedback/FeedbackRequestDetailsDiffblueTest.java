package com.iemr.helpline1097.data.co.feedback;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import com.iemr.helpline1097.data.co.services.DistrictBlock;
import com.iemr.helpline1097.data.co.services.DistrictBranchMapping;
import com.iemr.helpline1097.data.co.services.Districts;
import com.iemr.helpline1097.data.co.services.Institute;
import com.iemr.helpline1097.data.co.services.States;
import com.iemr.helpline1097.utils.mapper.OutputMapper;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class FeedbackRequestDetailsDiffblueTest {
    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link FeedbackRequestDetails#setComments(String)}
     *   <li>{@link FeedbackRequestDetails#setCreatedBy(String)}
     *   <li>{@link FeedbackRequestDetails#setDeleted(Boolean)}
     *   <li>{@link FeedbackRequestDetails#setFeedback(FeedbackDetails)}
     *   <li>{@link FeedbackRequestDetails#setFeedbackID(Long)}
     *   <li>{@link FeedbackRequestDetails#setFeedbackRequestID(Long)}
     *   <li>{@link FeedbackRequestDetails#setFeedbackSupSummary(String)}
     *   <li>{@link FeedbackRequestDetails#setLastModDate(Timestamp)}
     *   <li>{@link FeedbackRequestDetails#setModifiedBy(String)}
     *   <li>{@link FeedbackRequestDetails#setSupUserID(Integer)}
     *   <li>{@link FeedbackRequestDetails#toString()}
     *   <li>{@link FeedbackRequestDetails#getComments()}
     *   <li>{@link FeedbackRequestDetails#getCreatedBy()}
     *   <li>{@link FeedbackRequestDetails#getDeleted()}
     *   <li>{@link FeedbackRequestDetails#getFeedback()}
     *   <li>{@link FeedbackRequestDetails#getFeedbackID()}
     *   <li>{@link FeedbackRequestDetails#getFeedbackRequestID()}
     *   <li>{@link FeedbackRequestDetails#getFeedbackSupSummary()}
     *   <li>{@link FeedbackRequestDetails#getLastModDate()}
     *   <li>{@link FeedbackRequestDetails#getModifiedBy()}
     *   <li>{@link FeedbackRequestDetails#getSupUserID()}
     * </ul>
     */
    @Test
    void testGettersAndSetters() {
        // Arrange
        FeedbackRequestDetails feedbackRequestDetails = new FeedbackRequestDetails();

        // Act
        feedbackRequestDetails.setComments("Comments");
        feedbackRequestDetails.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        feedbackRequestDetails.setDeleted(true);
        Designation designation = new Designation();
        designation.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        designation.setCreatedDate(mock(Timestamp.class));
        designation.setDeleted(true);
        designation.setDesignationDesc("Designation Desc");
        designation.setDesignationID(1);
        designation.setDesignationName("Designation Name");
        designation.setLastModDate(mock(Timestamp.class));
        designation.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        States states = new States();
        states.setCountryID(1);
        states.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        states.setCreatedDate(mock(Timestamp.class));
        states.setDeleted(true);
        states.setLastModDate(mock(Timestamp.class));
        states.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        states.setOutputMapper(new OutputMapper());
        states.setStateCode("MD");
        states.setStateID(1);
        states.setStateIName("MD");
        states.setStateName("MD");
        Districts district = new Districts();
        district.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        district.setCreatedDate(mock(Timestamp.class));
        district.setDeleted(true);
        district.setDistrictID(1);
        district.setDistrictName("District Name");
        district.setLastModDate(mock(Timestamp.class));
        district.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        district.setOutputMapper(new OutputMapper());
        district.setStateID(1);
        district.setStates(states);
        DistrictBlock districtBlock = new DistrictBlock();
        districtBlock.setBlockID(1);
        districtBlock.setBlockName("Block Name");
        districtBlock.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        districtBlock.setCreatedDate(mock(Timestamp.class));
        districtBlock.setDeleted(true);
        districtBlock.setDistrictID(1);
        districtBlock.setLastModDate(mock(Timestamp.class));
        districtBlock.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        districtBlock.setOutputMapper(new OutputMapper());
        districtBlock.setStateID(1);
        FeedbackStatus feedbackStatus = new FeedbackStatus();
        feedbackStatus.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        feedbackStatus.setCreatedDate(mock(Timestamp.class));
        feedbackStatus.setDeleted(true);
        feedbackStatus.setFeedbackStatus("Feedback Status");
        feedbackStatus.setFeedbackStatusDesc("Feedback Status Desc");
        feedbackStatus.setFeedbackStatusID(1);
        feedbackStatus.setLastModDate(mock(Timestamp.class));
        feedbackStatus.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        feedbackStatus.setProviderServiceMapID(1);
        InstituteType instituteType = new InstituteType();
        instituteType.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        instituteType.setCreatedDate(mock(Date.class));
        instituteType.setDeleted(true);
        instituteType.setInstitutionType("Institution Type");
        instituteType.setInstitutionTypeDesc("Institution Type Desc");
        instituteType.setInstitutionTypeID(1);
        instituteType.setLastModDate(mock(Date.class));
        instituteType.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        instituteType.setProviderServiceMapID(1);
        DistrictBranchMapping m_districtbranchmapping = new DistrictBranchMapping();
        m_districtbranchmapping.setBlockID(1);
        m_districtbranchmapping.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        m_districtbranchmapping.setCreatedDate(mock(Timestamp.class));
        m_districtbranchmapping.setDeleted(true);
        m_districtbranchmapping.setDistrictBranchID(1);
        m_districtbranchmapping.setHabitat("Habitat");
        m_districtbranchmapping.setLastModDate(mock(Timestamp.class));
        m_districtbranchmapping.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        m_districtbranchmapping.setPanchayatName("Panchayat Name");
        m_districtbranchmapping.setPinCode("Pin Code");
        m_districtbranchmapping.setVillageName("Village Name");
        Severity severity = new Severity();
        severity.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        severity.setCreatedDate(mock(Timestamp.class));
        severity.setDeleted(true);
        severity.setLastModDate(mock(Timestamp.class));
        severity.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        severity.setProviderServiceMapID(1);
        severity.setSeverityDesc("S1");
        severity.setSeverityID(1);
        severity.setSeverityTypeName("S1");
        States states2 = new States();
        states2.setCountryID(1);
        states2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        states2.setCreatedDate(mock(Timestamp.class));
        states2.setDeleted(true);
        states2.setLastModDate(mock(Timestamp.class));
        states2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        states2.setOutputMapper(new OutputMapper());
        states2.setStateCode("MD");
        states2.setStateID(1);
        states2.setStateIName("MD");
        states2.setStateName("MD");
        FeedbackDetails feedback = new FeedbackDetails();
        feedback.setBenCallID(1L);
        feedback.setBeneficiaryRegID(1L);
        feedback.setBlockID(1);
        feedback.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        feedback.setCreatedDate(mock(Timestamp.class));
        feedback.setDeleted(true);
        feedback.setDesignation(designation);
        feedback.setDesignationID(1);
        feedback.setDistrict(district);
        feedback.setDistrictBlock(districtBlock);
        feedback.setDistrictBranchID(1);
        feedback.setDistrictID(1);
        feedback.setFeedback("Feedback");
        feedback.setFeedbackID(1L);
        feedback.setFeedbackRequestDetails(new ArrayList<>());
        feedback.setFeedbackStatus(feedbackStatus);
        feedback.setFeedbackStatusID((short) 1);
        feedback.setFeedbackTypeID((short) 1);
        feedback.setInstiName("Insti Name");
        feedback.setInstitute(new Institute(1, "Institution Name"));
        feedback.setInstituteName("Institute Name");
        feedback.setInstituteType(instituteType);
        feedback.setInstituteTypeID(1L);
        feedback.setInstitutionID(1L);
        feedback.setInstitutionTypeID(1L);
        feedback.setLastModDate(mock(Timestamp.class));
        feedback.setM_districtbranchmapping(m_districtbranchmapping);
        feedback.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        feedback.setRequestID("Request ID");
        feedback.setServiceAvailDate(mock(Timestamp.class));
        feedback.setServiceID(1);
        feedback.setSeverity(severity);
        feedback.setSeverityID((short) 1);
        feedback.setStateID(1);
        feedback.setStates(states2);
        feedback.setSubServiceID(1);
        feedback.setUserID(1);
        feedback.setsMSPhoneNo("6625550144");
        feedbackRequestDetails.setFeedback(feedback);
        feedbackRequestDetails.setFeedbackID(1L);
        feedbackRequestDetails.setFeedbackRequestID(1L);
        feedbackRequestDetails.setFeedbackSupSummary("Feedback Sup Summary");
        Timestamp lastModDate = mock(Timestamp.class);
        feedbackRequestDetails.setLastModDate(lastModDate);
        feedbackRequestDetails.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        feedbackRequestDetails.setSupUserID(1);
        String actualToStringResult = feedbackRequestDetails.toString();
        String actualComments = feedbackRequestDetails.getComments();
        String actualCreatedBy = feedbackRequestDetails.getCreatedBy();
        Boolean actualDeleted = feedbackRequestDetails.getDeleted();
        FeedbackDetails actualFeedback = feedbackRequestDetails.getFeedback();
        Long actualFeedbackID = feedbackRequestDetails.getFeedbackID();
        Long actualFeedbackRequestID = feedbackRequestDetails.getFeedbackRequestID();
        String actualFeedbackSupSummary = feedbackRequestDetails.getFeedbackSupSummary();
        Timestamp actualLastModDate = feedbackRequestDetails.getLastModDate();
        String actualModifiedBy = feedbackRequestDetails.getModifiedBy();

        // Assert that nothing has changed
        assertEquals("Comments", actualComments);
        assertEquals("Feedback Sup Summary", actualFeedbackSupSummary);
        assertEquals("Jan 1, 2020 8:00am GMT+0100", actualCreatedBy);
        assertEquals("Jan 1, 2020 9:00am GMT+0100", actualModifiedBy);
        assertEquals(
                "{\"feedbackRequestID\":\"1\",\"feedbackID\":\"1\",\"feedbackSupSummary\":\"Feedback Sup Summary\",\"supUserID\":1,"
                        + "\"comments\":\"Comments\",\"deleted\":true,\"createdBy\":\"Jan 1, 2020 8:00am GMT+0100\"}",
                actualToStringResult);
        assertEquals(1, feedbackRequestDetails.getSupUserID().intValue());
        assertEquals(1L, actualFeedbackID.longValue());
        assertEquals(1L, actualFeedbackRequestID.longValue());
        assertTrue(actualDeleted);
        assertSame(feedback, actualFeedback);
        assertSame(lastModDate, actualLastModDate);
    }
}
