package com.iemr.helpline1097.controller.co.feedback;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iemr.helpline1097.data.co.feedback.Designation;
import com.iemr.helpline1097.data.co.feedback.FeedbackDetails;
import com.iemr.helpline1097.data.co.feedback.FeedbackRequestDetails;
import com.iemr.helpline1097.data.co.feedback.FeedbackStatus;
import com.iemr.helpline1097.data.co.feedback.InstituteType;
import com.iemr.helpline1097.data.co.feedback.Severity;
import com.iemr.helpline1097.data.co.services.DistrictBlock;
import com.iemr.helpline1097.data.co.services.DistrictBranchMapping;
import com.iemr.helpline1097.data.co.services.Districts;
import com.iemr.helpline1097.data.co.services.Institute;
import com.iemr.helpline1097.data.co.services.States;
import com.iemr.helpline1097.service.co.feedback.FeedbackService;
import com.iemr.helpline1097.service.co.feedback.FeedbackServiceImpl;
import com.iemr.helpline1097.utils.mapper.OutputMapper;
import jakarta.servlet.http.HttpServletRequest;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.User;
import org.apache.catalina.realm.UserDatabaseRealm;
import org.apache.catalina.users.MemoryUserDatabase;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {FeedbackController.class, FeedbackService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class FeedbackControllerDiffblueTest {
    @Autowired
    private FeedbackController feedbackController;

    @MockBean
    private FeedbackService feedbackService;

    @MockBean
    private FeedbackServiceImpl feedbackServiceImpl;

    /**
     * Method under test: {@link FeedbackController#getFeedbackByPost(int)}
     */
    @Test
    void testGetFeedbackByPost() {
        

        // Arrange
        FeedbackServiceImpl feedbackService = mock(FeedbackServiceImpl.class);
        when(feedbackService.getFeedbackRequests(anyLong())).thenReturn(new ArrayList<>());

        FeedbackController feedbackController = new FeedbackController();
        feedbackController.setFeedbackService(feedbackService);

        // Act
        String actualFeedbackByPost = feedbackController.getFeedbackByPost(1);

        // Assert
        verify(feedbackService).getFeedbackRequests(eq(1L));
        assertEquals("{\"data\":[],\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}",
                actualFeedbackByPost);
    }

    /**
     * Method under test: {@link FeedbackController#getFeedbackByPost(int)}
     */
    @Test
    void testGetFeedbackByPost2() {
        

        // Arrange
        Designation designation = new Designation();
        designation.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        designation.setCreatedDate(mock(Timestamp.class));
        designation.setDeleted(true);
        designation.setDesignationDesc("Failed with generic error");
        designation.setDesignationID(1);
        designation.setDesignationName("Failed with generic error");
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
        district.setDistrictName("Failed with generic error");
        district.setLastModDate(mock(Timestamp.class));
        district.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        district.setOutputMapper(new OutputMapper());
        district.setStateID(1);
        district.setStates(states);

        DistrictBlock districtBlock = new DistrictBlock();
        districtBlock.setBlockID(1);
        districtBlock.setBlockName("Failed with generic error");
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
        feedbackStatus.setFeedbackStatus("Failed with generic error");
        feedbackStatus.setFeedbackStatusDesc("Failed with generic error");
        feedbackStatus.setFeedbackStatusID(1);
        feedbackStatus.setLastModDate(mock(Timestamp.class));
        feedbackStatus.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        feedbackStatus.setProviderServiceMapID(1);

        InstituteType instituteType = new InstituteType();
        instituteType.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        instituteType.setCreatedDate(mock(Date.class));
        instituteType.setDeleted(true);
        instituteType.setInstitutionType("Failed with generic error");
        instituteType.setInstitutionTypeDesc("Failed with generic error");
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
        m_districtbranchmapping.setHabitat("Failed with generic error");
        m_districtbranchmapping.setLastModDate(mock(Timestamp.class));
        m_districtbranchmapping.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        m_districtbranchmapping.setPanchayatName("Failed with generic error");
        m_districtbranchmapping.setPinCode("Failed with generic error");
        m_districtbranchmapping.setVillageName("Failed with generic error");
        Timestamp serviceAvailDate = mock(Timestamp.class);
        when(serviceAvailDate.getTime()).thenReturn(10L);

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

        FeedbackDetails feedbackDetails = new FeedbackDetails();
        feedbackDetails.setBenCallID(1L);
        feedbackDetails.setBeneficiaryRegID(1L);
        feedbackDetails.setBlockID(1);
        feedbackDetails.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        feedbackDetails.setCreatedDate(mock(Timestamp.class));
        feedbackDetails.setDeleted(true);
        feedbackDetails.setDesignation(designation);
        feedbackDetails.setDesignationID(1);
        feedbackDetails.setDistrict(district);
        feedbackDetails.setDistrictBlock(districtBlock);
        feedbackDetails.setDistrictBranchID(1);
        feedbackDetails.setDistrictID(1);
        feedbackDetails.setFeedback("Failed with generic error");
        feedbackDetails.setFeedbackID(1L);
        feedbackDetails.setFeedbackRequestDetails(new ArrayList<>());
        feedbackDetails.setFeedbackStatus(feedbackStatus);
        feedbackDetails.setFeedbackStatusID((short) 5000);
        feedbackDetails.setFeedbackTypeID((short) 5000);
        feedbackDetails.setInstiName("Failed with generic error");
        feedbackDetails.setInstitute(new Institute(1, "Failed with generic error"));
        feedbackDetails.setInstituteName("Failed with generic error");
        feedbackDetails.setInstituteType(instituteType);
        feedbackDetails.setInstituteTypeID(1L);
        feedbackDetails.setInstitutionID(1L);
        feedbackDetails.setInstitutionTypeID(1L);
        feedbackDetails.setLastModDate(mock(Timestamp.class));
        feedbackDetails.setM_districtbranchmapping(m_districtbranchmapping);
        feedbackDetails.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        feedbackDetails.setRequestID("Failed with generic error");
        feedbackDetails.setServiceAvailDate(serviceAvailDate);
        feedbackDetails.setServiceID(1);
        feedbackDetails.setSeverity(severity);
        feedbackDetails.setSeverityID((short) 5000);
        feedbackDetails.setStateID(1);
        feedbackDetails.setStates(states2);
        feedbackDetails.setSubServiceID(1);
        feedbackDetails.setUserID(1);
        feedbackDetails.setsMSPhoneNo("6625550144");

        ArrayList<FeedbackDetails> feedbackDetailsList = new ArrayList<>();
        feedbackDetailsList.add(feedbackDetails);
        FeedbackServiceImpl feedbackService = mock(FeedbackServiceImpl.class);
        when(feedbackService.getFeedbackRequests(anyLong())).thenReturn(feedbackDetailsList);

        FeedbackController feedbackController = new FeedbackController();
        feedbackController.setFeedbackService(feedbackService);

        // Act
        feedbackController.getFeedbackByPost(1);

        // Assert
        verify(feedbackService).getFeedbackRequests(eq(1L));
        verify(serviceAvailDate).getTime();
    }

    /**
     * Method under test: {@link FeedbackController#getFeedbackByPost(int)}
     */
    @Test
    void testGetFeedbackByPost3() {
        

        // Arrange
        Designation designation = new Designation();
        designation.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        designation.setCreatedDate(mock(Timestamp.class));
        designation.setDeleted(true);
        designation.setDesignationDesc("Failed with generic error");
        designation.setDesignationID(1);
        designation.setDesignationName("Failed with generic error");
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
        district.setDistrictName("Failed with generic error");
        district.setLastModDate(mock(Timestamp.class));
        district.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        district.setOutputMapper(new OutputMapper());
        district.setStateID(1);
        district.setStates(states);

        DistrictBlock districtBlock = new DistrictBlock();
        districtBlock.setBlockID(1);
        districtBlock.setBlockName("Failed with generic error");
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
        feedbackStatus.setFeedbackStatus("Failed with generic error");
        feedbackStatus.setFeedbackStatusDesc("Failed with generic error");
        feedbackStatus.setFeedbackStatusID(1);
        feedbackStatus.setLastModDate(mock(Timestamp.class));
        feedbackStatus.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        feedbackStatus.setProviderServiceMapID(1);

        InstituteType instituteType = new InstituteType();
        instituteType.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        instituteType.setCreatedDate(mock(Date.class));
        instituteType.setDeleted(true);
        instituteType.setInstitutionType("Failed with generic error");
        instituteType.setInstitutionTypeDesc("Failed with generic error");
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
        m_districtbranchmapping.setHabitat("Failed with generic error");
        m_districtbranchmapping.setLastModDate(mock(Timestamp.class));
        m_districtbranchmapping.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        m_districtbranchmapping.setPanchayatName("Failed with generic error");
        m_districtbranchmapping.setPinCode("Failed with generic error");
        m_districtbranchmapping.setVillageName("Failed with generic error");

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
        FeedbackDetails feedbackDetails = mock(FeedbackDetails.class);
        doNothing().when(feedbackDetails).setBenCallID(Mockito.<Long>any());
        doNothing().when(feedbackDetails).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(feedbackDetails).setBlockID(Mockito.<Integer>any());
        doNothing().when(feedbackDetails).setCreatedBy(Mockito.<String>any());
        doNothing().when(feedbackDetails).setCreatedDate(Mockito.<Timestamp>any());
        doNothing().when(feedbackDetails).setDeleted(Mockito.<Boolean>any());
        doNothing().when(feedbackDetails).setDesignation(Mockito.<Designation>any());
        doNothing().when(feedbackDetails).setDesignationID(Mockito.<Integer>any());
        doNothing().when(feedbackDetails).setDistrict(Mockito.<Districts>any());
        doNothing().when(feedbackDetails).setDistrictBlock(Mockito.<DistrictBlock>any());
        doNothing().when(feedbackDetails).setDistrictBranchID(Mockito.<Integer>any());
        doNothing().when(feedbackDetails).setDistrictID(Mockito.<Integer>any());
        doNothing().when(feedbackDetails).setFeedback(Mockito.<String>any());
        doNothing().when(feedbackDetails).setFeedbackID(Mockito.<Long>any());
        doNothing().when(feedbackDetails).setFeedbackRequestDetails(Mockito.<List<FeedbackRequestDetails>>any());
        doNothing().when(feedbackDetails).setFeedbackStatus(Mockito.<FeedbackStatus>any());
        doNothing().when(feedbackDetails).setFeedbackStatusID(Mockito.<Short>any());
        doNothing().when(feedbackDetails).setFeedbackTypeID(Mockito.<Short>any());
        doNothing().when(feedbackDetails).setInstiName(Mockito.<String>any());
        doNothing().when(feedbackDetails).setInstitute(Mockito.<Institute>any());
        doNothing().when(feedbackDetails).setInstituteName(Mockito.<String>any());
        doNothing().when(feedbackDetails).setInstituteType(Mockito.<InstituteType>any());
        doNothing().when(feedbackDetails).setInstituteTypeID(Mockito.<Long>any());
        doNothing().when(feedbackDetails).setInstitutionID(Mockito.<Long>any());
        doNothing().when(feedbackDetails).setInstitutionTypeID(Mockito.<Long>any());
        doNothing().when(feedbackDetails).setLastModDate(Mockito.<Timestamp>any());
        doNothing().when(feedbackDetails).setM_districtbranchmapping(Mockito.<DistrictBranchMapping>any());
        doNothing().when(feedbackDetails).setModifiedBy(Mockito.<String>any());
        doNothing().when(feedbackDetails).setRequestID(Mockito.<String>any());
        doNothing().when(feedbackDetails).setServiceAvailDate(Mockito.<Timestamp>any());
        doNothing().when(feedbackDetails).setServiceID(Mockito.<Integer>any());
        doNothing().when(feedbackDetails).setSeverity(Mockito.<Severity>any());
        doNothing().when(feedbackDetails).setSeverityID(Mockito.<Short>any());
        doNothing().when(feedbackDetails).setStateID(Mockito.<Integer>any());
        doNothing().when(feedbackDetails).setStates(Mockito.<States>any());
        doNothing().when(feedbackDetails).setSubServiceID(Mockito.<Integer>any());
        doNothing().when(feedbackDetails).setUserID(Mockito.<Integer>any());
        doNothing().when(feedbackDetails).setsMSPhoneNo(Mockito.<String>any());
        feedbackDetails.setBenCallID(1L);
        feedbackDetails.setBeneficiaryRegID(1L);
        feedbackDetails.setBlockID(1);
        feedbackDetails.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        feedbackDetails.setCreatedDate(mock(Timestamp.class));
        feedbackDetails.setDeleted(true);
        feedbackDetails.setDesignation(designation);
        feedbackDetails.setDesignationID(1);
        feedbackDetails.setDistrict(district);
        feedbackDetails.setDistrictBlock(districtBlock);
        feedbackDetails.setDistrictBranchID(1);
        feedbackDetails.setDistrictID(1);
        feedbackDetails.setFeedback("Failed with generic error");
        feedbackDetails.setFeedbackID(1L);
        feedbackDetails.setFeedbackRequestDetails(new ArrayList<>());
        feedbackDetails.setFeedbackStatus(feedbackStatus);
        feedbackDetails.setFeedbackStatusID((short) 5000);
        feedbackDetails.setFeedbackTypeID((short) 5000);
        feedbackDetails.setInstiName("Failed with generic error");
        feedbackDetails.setInstitute(new Institute(1, "Failed with generic error"));
        feedbackDetails.setInstituteName("Failed with generic error");
        feedbackDetails.setInstituteType(instituteType);
        feedbackDetails.setInstituteTypeID(1L);
        feedbackDetails.setInstitutionID(1L);
        feedbackDetails.setInstitutionTypeID(1L);
        feedbackDetails.setLastModDate(mock(Timestamp.class));
        feedbackDetails.setM_districtbranchmapping(m_districtbranchmapping);
        feedbackDetails.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        feedbackDetails.setRequestID("Failed with generic error");
        feedbackDetails.setServiceAvailDate(mock(Timestamp.class));
        feedbackDetails.setServiceID(1);
        feedbackDetails.setSeverity(severity);
        feedbackDetails.setSeverityID((short) 5000);
        feedbackDetails.setStateID(1);
        feedbackDetails.setStates(states2);
        feedbackDetails.setSubServiceID(1);
        feedbackDetails.setUserID(1);
        feedbackDetails.setsMSPhoneNo("6625550144");

        ArrayList<FeedbackDetails> feedbackDetailsList = new ArrayList<>();
        feedbackDetailsList.add(feedbackDetails);
        FeedbackServiceImpl feedbackService = mock(FeedbackServiceImpl.class);
        when(feedbackService.getFeedbackRequests(anyLong())).thenReturn(feedbackDetailsList);

        FeedbackController feedbackController = new FeedbackController();
        feedbackController.setFeedbackService(feedbackService);

        // Act
        feedbackController.getFeedbackByPost(1);

        // Assert
        verify(feedbackDetails).setBenCallID(isA(Long.class));
        verify(feedbackDetails).setBeneficiaryRegID(isA(Long.class));
        verify(feedbackDetails).setBlockID(isA(Integer.class));
        verify(feedbackDetails).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(feedbackDetails).setCreatedDate(isA(Timestamp.class));
        verify(feedbackDetails).setDeleted(isA(Boolean.class));
        verify(feedbackDetails).setDesignation(isA(Designation.class));
        verify(feedbackDetails).setDesignationID(isA(Integer.class));
        verify(feedbackDetails).setDistrict(isA(Districts.class));
        verify(feedbackDetails).setDistrictBlock(isA(DistrictBlock.class));
        verify(feedbackDetails).setDistrictBranchID(isA(Integer.class));
        verify(feedbackDetails).setDistrictID(isA(Integer.class));
        verify(feedbackDetails).setFeedback(eq("Failed with generic error"));
        verify(feedbackDetails).setFeedbackID(isA(Long.class));
        verify(feedbackDetails).setFeedbackRequestDetails(isA(List.class));
        verify(feedbackDetails).setFeedbackStatus(isA(FeedbackStatus.class));
        verify(feedbackDetails).setFeedbackStatusID(isA(Short.class));
        verify(feedbackDetails).setFeedbackTypeID(isA(Short.class));
        verify(feedbackDetails).setInstiName(eq("Failed with generic error"));
        verify(feedbackDetails).setInstitute(isA(Institute.class));
        verify(feedbackDetails).setInstituteName(eq("Failed with generic error"));
        verify(feedbackDetails).setInstituteType(isA(InstituteType.class));
        verify(feedbackDetails).setInstituteTypeID(isA(Long.class));
        verify(feedbackDetails).setInstitutionID(isA(Long.class));
        verify(feedbackDetails).setInstitutionTypeID(isA(Long.class));
        verify(feedbackDetails).setLastModDate(isA(Timestamp.class));
        verify(feedbackDetails).setM_districtbranchmapping(isA(DistrictBranchMapping.class));
        verify(feedbackDetails).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
        verify(feedbackDetails).setRequestID(eq("Failed with generic error"));
        verify(feedbackDetails).setServiceAvailDate(isA(Timestamp.class));
        verify(feedbackDetails).setServiceID(isA(Integer.class));
        verify(feedbackDetails).setSeverity(isA(Severity.class));
        verify(feedbackDetails).setSeverityID(isA(Short.class));
        verify(feedbackDetails).setStateID(isA(Integer.class));
        verify(feedbackDetails).setStates(isA(States.class));
        verify(feedbackDetails).setSubServiceID(isA(Integer.class));
        verify(feedbackDetails).setUserID(isA(Integer.class));
        verify(feedbackDetails).setsMSPhoneNo(eq("6625550144"));
        verify(feedbackService).getFeedbackRequests(eq(1L));
    }

    /**
     * Method under test:
     * {@link FeedbackController#saveBenFeedback(String, HttpServletRequest)}
     */
    @Test
    void testSaveBenFeedback() throws Exception {
        

        // Arrange
        FeedbackServiceImpl feedbackServiceImpl = mock(FeedbackServiceImpl.class);
        when(feedbackServiceImpl.saveFeedbackFromCustomer(Mockito.<String>any(), Mockito.<HttpServletRequest>any()))
                .thenReturn("jane.doe@example.org");

        FeedbackController feedbackController = new FeedbackController();
        feedbackController.setFeedbackServiceImpl(feedbackServiceImpl);

        // Act
        String actualSaveBenFeedbackResult = feedbackController.saveBenFeedback("Feedback Request",
                new MockHttpServletRequest());

        // Assert
        verify(feedbackServiceImpl).saveFeedbackFromCustomer(eq("Feedback Request"), isA(HttpServletRequest.class));
        assertEquals(
                "{\"data\":{\"response\":\"jane.doe@example.org\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":"
                        + "\"Success\"}",
                actualSaveBenFeedbackResult);
    }

    /**
     * Method under test:
     * {@link FeedbackController#saveBenFeedback(String, HttpServletRequest)}
     */
    @Test
    void testSaveBenFeedback2() throws Exception {
        

        // Arrange
        FeedbackServiceImpl feedbackServiceImpl = mock(FeedbackServiceImpl.class);
        when(feedbackServiceImpl.saveFeedbackFromCustomer(Mockito.<String>any(), Mockito.<HttpServletRequest>any()))
                .thenReturn("Failed with generic error");

        FeedbackController feedbackController = new FeedbackController();
        feedbackController.setFeedbackServiceImpl(feedbackServiceImpl);

        // Act
        String actualSaveBenFeedbackResult = feedbackController.saveBenFeedback("Feedback Request",
                new MockHttpServletRequest());

        // Assert
        verify(feedbackServiceImpl).saveFeedbackFromCustomer(eq("Feedback Request"), isA(HttpServletRequest.class));
        assertEquals(
                "{\"data\":{\"response\":\"Failed with generic error\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":"
                        + "\"Success\"}",
                actualSaveBenFeedbackResult);
    }

    /**
     * Method under test:
     * {@link FeedbackController#saveBenFeedback(String, HttpServletRequest)}
     */
    @Test
    void testSaveBenFeedback3() throws Exception {
       

        // Arrange
        FeedbackServiceImpl feedbackServiceImpl = mock(FeedbackServiceImpl.class);
        when(feedbackServiceImpl.saveFeedbackFromCustomer(Mockito.<String>any(), Mockito.<HttpServletRequest>any()))
                .thenReturn("{\"response\":\"$$STRING\"}");

        FeedbackController feedbackController = new FeedbackController();
        feedbackController.setFeedbackServiceImpl(feedbackServiceImpl);

        // Act
        String actualSaveBenFeedbackResult = feedbackController.saveBenFeedback("Feedback Request",
                new MockHttpServletRequest());

        // Assert
        verify(feedbackServiceImpl).saveFeedbackFromCustomer(eq("Feedback Request"), isA(HttpServletRequest.class));
        assertEquals(
                "{\"data\":{\"response\":\"$$STRING\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}",
                actualSaveBenFeedbackResult);
    }

    /**
     * Method under test:
     * {@link FeedbackController#saveBenFeedback(String, HttpServletRequest)}
     */
    @Test
    void testSaveBenFeedback4() throws Exception {
       

        // Arrange
        FeedbackServiceImpl feedbackServiceImpl = mock(FeedbackServiceImpl.class);
        when(feedbackServiceImpl.saveFeedbackFromCustomer(Mockito.<String>any(), Mockito.<HttpServletRequest>any()))
                .thenReturn("");

        FeedbackController feedbackController = new FeedbackController();
        feedbackController.setFeedbackServiceImpl(feedbackServiceImpl);

        // Act
        String actualSaveBenFeedbackResult = feedbackController.saveBenFeedback("Feedback Request",
                new MockHttpServletRequest());

        // Assert
        verify(feedbackServiceImpl).saveFeedbackFromCustomer(eq("Feedback Request"), isA(HttpServletRequest.class));
        assertEquals(
                "{\"data\":{\"response\":\"\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}",
                actualSaveBenFeedbackResult);
    }

    /**
     * Method under test: {@link FeedbackController#feedbackReuest(String)}
     */
    @Test
    void testFeedbackReuest() throws Exception {
        // Arrange
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/co/getfeedbacklist")
                .contentType(MediaType.APPLICATION_JSON);
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content((new ObjectMapper()).writeValueAsString("foo"));

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(feedbackController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link FeedbackController#feedbackReuest(String)}
     */
    @Test
    void testFeedbackReuest2() throws Exception {
        // Arrange
        User user = mock(User.class);
        when(user.getName()).thenReturn("Name");
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/co/getfeedbacklist");
        postResult.principal(new UserDatabaseRealm.UserDatabasePrincipal(user, new MemoryUserDatabase()));
        MockHttpServletRequestBuilder contentTypeResult = postResult.contentType(MediaType.APPLICATION_JSON);
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content((new ObjectMapper()).writeValueAsString("foo"));

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(feedbackController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link FeedbackController#getFeedbackByPost(int)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetFeedbackByPost4() throws Exception {
    

        // Arrange
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/co/getfeedback/{feedbackID}", 1);

        // Act
        MockMvcBuilders.standaloneSetup(feedbackController).build().perform(requestBuilder);
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link FeedbackController#setFeedbackService(FeedbackService)}
     *   <li>{@link FeedbackController#setFeedbackServiceImpl(FeedbackServiceImpl)}
     * </ul>
     */
    @Test
    void testGettersAndSetters() {


        // Arrange
        FeedbackController feedbackController = new FeedbackController();

        // Act
        feedbackController.setFeedbackService(new FeedbackServiceImpl());
        feedbackController.setFeedbackServiceImpl(new FeedbackServiceImpl());
    }

    /**
     * Method under test:
     * {@link FeedbackController#saveBenFeedback(String, HttpServletRequest)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testSaveBenFeedback5() throws Exception {
   

        // Arrange
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/co/saveBenFeedback")
                .contentType(MediaType.APPLICATION_JSON);
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content((new ObjectMapper()).writeValueAsString("foo"));

        // Act
        MockMvcBuilders.standaloneSetup(feedbackController).build().perform(requestBuilder);
    }
}
