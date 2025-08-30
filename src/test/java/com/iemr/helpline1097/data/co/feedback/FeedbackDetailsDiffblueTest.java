package com.iemr.helpline1097.data.co.feedback;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {FeedbackDetails.class})
@ExtendWith(SpringExtension.class)
class FeedbackDetailsDiffblueTest {
    @Autowired
    private FeedbackDetails feedbackDetails;

    /**
     * Method under test: {@link FeedbackDetails#getsMSPhoneNo()}
     */
    @Test
    void testGetsMSPhoneNo() {
        // Arrange, Act and Assert
        assertNull((new FeedbackDetails()).getsMSPhoneNo());
        assertEquals("6625550144",
                (new FeedbackDetails(1L, 1L, 1, (short) 1, (short) 1, (short) 1, "Feedback", 1L, 1, 1, "6625550144",
                        mock(Timestamp.class), true, "Jan 1, 2020 8:00am GMT+0100", mock(Timestamp.class),
                        "Jan 1, 2020 9:00am GMT+0100", mock(Timestamp.class))).getsMSPhoneNo());
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link FeedbackDetails#FeedbackDetails()}
     *   <li>{@link FeedbackDetails#setBenCallID(Long)}
     *   <li>{@link FeedbackDetails#setBeneficiaryRegID(Long)}
     *   <li>{@link FeedbackDetails#setCreatedBy(String)}
     *   <li>{@link FeedbackDetails#setCreatedDate(Timestamp)}
     *   <li>{@link FeedbackDetails#setDeleted(Boolean)}
     *   <li>{@link FeedbackDetails#setDesignationID(Integer)}
     *   <li>{@link FeedbackDetails#setFeedback(String)}
     *   <li>{@link FeedbackDetails#setFeedbackID(Long)}
     *   <li>{@link FeedbackDetails#setFeedbackRequestDetails(List)}
     *   <li>{@link FeedbackDetails#setFeedbackStatusID(Short)}
     *   <li>{@link FeedbackDetails#setFeedbackTypeID(Short)}
     *   <li>{@link FeedbackDetails#setInstitutionID(Long)}
     *   <li>{@link FeedbackDetails#setLastModDate(Timestamp)}
     *   <li>{@link FeedbackDetails#setModifiedBy(String)}
     *   <li>{@link FeedbackDetails#setServiceAvailDate(Timestamp)}
     *   <li>{@link FeedbackDetails#setServiceID(Integer)}
     *   <li>{@link FeedbackDetails#setSeverityID(Short)}
     *   <li>{@link FeedbackDetails#setUserID(Integer)}
     *   <li>{@link FeedbackDetails#setsMSPhoneNo(String)}
     *   <li>{@link FeedbackDetails#getBenCallID()}
     *   <li>{@link FeedbackDetails#getBeneficiaryRegID()}
     *   <li>{@link FeedbackDetails#getCreatedBy()}
     *   <li>{@link FeedbackDetails#getCreatedDate()}
     *   <li>{@link FeedbackDetails#getDeleted()}
     *   <li>{@link FeedbackDetails#getDesignationID()}
     *   <li>{@link FeedbackDetails#getFeedback()}
     *   <li>{@link FeedbackDetails#getFeedbackID()}
     *   <li>{@link FeedbackDetails#getFeedbackRequestDetails()}
     *   <li>{@link FeedbackDetails#getFeedbackStatusID()}
     *   <li>{@link FeedbackDetails#getFeedbackTypeID()}
     *   <li>{@link FeedbackDetails#getInstitutionID()}
     *   <li>{@link FeedbackDetails#getLastModDate()}
     *   <li>{@link FeedbackDetails#getModifiedBy()}
     *   <li>{@link FeedbackDetails#getServiceAvailDate()}
     *   <li>{@link FeedbackDetails#getServiceID()}
     *   <li>{@link FeedbackDetails#getSeverityID()}
     *   <li>{@link FeedbackDetails#getSubServiceID()}
     *   <li>{@link FeedbackDetails#getUserID()}
     * </ul>
     */
    @Test
    void testGettersAndSetters() {
        // Arrange and Act
        FeedbackDetails actualFeedbackDetails = new FeedbackDetails();
        actualFeedbackDetails.setBenCallID(1L);
        actualFeedbackDetails.setBeneficiaryRegID(1L);
        actualFeedbackDetails.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        Timestamp createdDate = mock(Timestamp.class);
        actualFeedbackDetails.setCreatedDate(createdDate);
        actualFeedbackDetails.setDeleted(true);
        actualFeedbackDetails.setDesignationID(1);
        actualFeedbackDetails.setFeedback("Feedback");
        actualFeedbackDetails.setFeedbackID(1L);
        ArrayList<FeedbackRequestDetails> feedbackRequestDetails = new ArrayList<>();
        actualFeedbackDetails.setFeedbackRequestDetails(feedbackRequestDetails);
        actualFeedbackDetails.setFeedbackStatusID((short) 1);
        actualFeedbackDetails.setFeedbackTypeID((short) 1);
        actualFeedbackDetails.setInstitutionID(1L);
        Timestamp lastModDate = mock(Timestamp.class);
        actualFeedbackDetails.setLastModDate(lastModDate);
        actualFeedbackDetails.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        Timestamp serviceAvailDate = mock(Timestamp.class);
        actualFeedbackDetails.setServiceAvailDate(serviceAvailDate);
        actualFeedbackDetails.setServiceID(1);
        actualFeedbackDetails.setSeverityID((short) 1);
        actualFeedbackDetails.setUserID(1);
        actualFeedbackDetails.setsMSPhoneNo("6625550144");
        Long actualBenCallID = actualFeedbackDetails.getBenCallID();
        Long actualBeneficiaryRegID = actualFeedbackDetails.getBeneficiaryRegID();
        String actualCreatedBy = actualFeedbackDetails.getCreatedBy();
        Timestamp actualCreatedDate = actualFeedbackDetails.getCreatedDate();
        Boolean actualDeleted = actualFeedbackDetails.getDeleted();
        Integer actualDesignationID = actualFeedbackDetails.getDesignationID();
        String actualFeedback = actualFeedbackDetails.getFeedback();
        Long actualFeedbackID = actualFeedbackDetails.getFeedbackID();
        List<FeedbackRequestDetails> actualFeedbackRequestDetails = actualFeedbackDetails.getFeedbackRequestDetails();
        Short actualFeedbackStatusID = actualFeedbackDetails.getFeedbackStatusID();
        Short actualFeedbackTypeID = actualFeedbackDetails.getFeedbackTypeID();
        Long actualInstitutionID = actualFeedbackDetails.getInstitutionID();
        Timestamp actualLastModDate = actualFeedbackDetails.getLastModDate();
        String actualModifiedBy = actualFeedbackDetails.getModifiedBy();
        Timestamp actualServiceAvailDate = actualFeedbackDetails.getServiceAvailDate();
        Integer actualServiceID = actualFeedbackDetails.getServiceID();
        Short actualSeverityID = actualFeedbackDetails.getSeverityID();
        actualFeedbackDetails.getSubServiceID();
        Integer actualUserID = actualFeedbackDetails.getUserID();

        // Assert that nothing has changed
        assertEquals("Feedback", actualFeedback);
        assertEquals("Jan 1, 2020 8:00am GMT+0100", actualCreatedBy);
        assertEquals("Jan 1, 2020 9:00am GMT+0100", actualModifiedBy);
        assertEquals(1, actualDesignationID.intValue());
        assertEquals(1, actualServiceID.intValue());
        assertEquals(1, actualUserID.intValue());
        assertEquals(1L, actualBenCallID.longValue());
        assertEquals(1L, actualBeneficiaryRegID.longValue());
        assertEquals(1L, actualFeedbackID.longValue());
        assertEquals(1L, actualInstitutionID.longValue());
        assertEquals((short) 1, actualFeedbackStatusID.shortValue());
        assertEquals((short) 1, actualFeedbackTypeID.shortValue());
        assertEquals((short) 1, actualSeverityID.shortValue());
        assertTrue(actualDeleted);
        assertSame(feedbackRequestDetails, actualFeedbackRequestDetails);
        assertSame(createdDate, actualCreatedDate);
        assertSame(lastModDate, actualLastModDate);
        assertSame(serviceAvailDate, actualServiceAvailDate);
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>
     * {@link FeedbackDetails#FeedbackDetails(Long, Long, Integer, Short, Short, Short, String, Long, Integer, Integer, String, Timestamp, Boolean, String, Timestamp, String, Timestamp)}
     *   <li>{@link FeedbackDetails#setBenCallID(Long)}
     *   <li>{@link FeedbackDetails#setBeneficiaryRegID(Long)}
     *   <li>{@link FeedbackDetails#setCreatedBy(String)}
     *   <li>{@link FeedbackDetails#setCreatedDate(Timestamp)}
     *   <li>{@link FeedbackDetails#setDeleted(Boolean)}
     *   <li>{@link FeedbackDetails#setDesignationID(Integer)}
     *   <li>{@link FeedbackDetails#setFeedback(String)}
     *   <li>{@link FeedbackDetails#setFeedbackID(Long)}
     *   <li>{@link FeedbackDetails#setFeedbackRequestDetails(List)}
     *   <li>{@link FeedbackDetails#setFeedbackStatusID(Short)}
     *   <li>{@link FeedbackDetails#setFeedbackTypeID(Short)}
     *   <li>{@link FeedbackDetails#setInstitutionID(Long)}
     *   <li>{@link FeedbackDetails#setLastModDate(Timestamp)}
     *   <li>{@link FeedbackDetails#setModifiedBy(String)}
     *   <li>{@link FeedbackDetails#setServiceAvailDate(Timestamp)}
     *   <li>{@link FeedbackDetails#setServiceID(Integer)}
     *   <li>{@link FeedbackDetails#setSeverityID(Short)}
     *   <li>{@link FeedbackDetails#setUserID(Integer)}
     *   <li>{@link FeedbackDetails#setsMSPhoneNo(String)}
     *   <li>{@link FeedbackDetails#getBenCallID()}
     *   <li>{@link FeedbackDetails#getBeneficiaryRegID()}
     *   <li>{@link FeedbackDetails#getCreatedBy()}
     *   <li>{@link FeedbackDetails#getCreatedDate()}
     *   <li>{@link FeedbackDetails#getDeleted()}
     *   <li>{@link FeedbackDetails#getDesignationID()}
     *   <li>{@link FeedbackDetails#getFeedback()}
     *   <li>{@link FeedbackDetails#getFeedbackID()}
     *   <li>{@link FeedbackDetails#getFeedbackRequestDetails()}
     *   <li>{@link FeedbackDetails#getFeedbackStatusID()}
     *   <li>{@link FeedbackDetails#getFeedbackTypeID()}
     *   <li>{@link FeedbackDetails#getInstitutionID()}
     *   <li>{@link FeedbackDetails#getLastModDate()}
     *   <li>{@link FeedbackDetails#getModifiedBy()}
     *   <li>{@link FeedbackDetails#getServiceAvailDate()}
     *   <li>{@link FeedbackDetails#getServiceID()}
     *   <li>{@link FeedbackDetails#getSeverityID()}
     *   <li>{@link FeedbackDetails#getSubServiceID()}
     *   <li>{@link FeedbackDetails#getUserID()}
     * </ul>
     */
    @Test
    void testGettersAndSetters2() {
        // Arrange and Act
        FeedbackDetails actualFeedbackDetails = new FeedbackDetails(1L, 1L, 1, (short) 1, (short) 1, (short) 1, "Feedback",
                1L, 1, 1, "6625550144", mock(Timestamp.class), true, "Jan 1, 2020 8:00am GMT+0100", mock(Timestamp.class),
                "Jan 1, 2020 9:00am GMT+0100", mock(Timestamp.class));
        actualFeedbackDetails.setBenCallID(1L);
        actualFeedbackDetails.setBeneficiaryRegID(1L);
        actualFeedbackDetails.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        Timestamp createdDate = mock(Timestamp.class);
        actualFeedbackDetails.setCreatedDate(createdDate);
        actualFeedbackDetails.setDeleted(true);
        actualFeedbackDetails.setDesignationID(1);
        actualFeedbackDetails.setFeedback("Feedback");
        actualFeedbackDetails.setFeedbackID(1L);
        ArrayList<FeedbackRequestDetails> feedbackRequestDetails = new ArrayList<>();
        actualFeedbackDetails.setFeedbackRequestDetails(feedbackRequestDetails);
        actualFeedbackDetails.setFeedbackStatusID((short) 1);
        actualFeedbackDetails.setFeedbackTypeID((short) 1);
        actualFeedbackDetails.setInstitutionID(1L);
        Timestamp lastModDate = mock(Timestamp.class);
        actualFeedbackDetails.setLastModDate(lastModDate);
        actualFeedbackDetails.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        Timestamp serviceAvailDate = mock(Timestamp.class);
        actualFeedbackDetails.setServiceAvailDate(serviceAvailDate);
        actualFeedbackDetails.setServiceID(1);
        actualFeedbackDetails.setSeverityID((short) 1);
        actualFeedbackDetails.setUserID(1);
        actualFeedbackDetails.setsMSPhoneNo("6625550144");
        Long actualBenCallID = actualFeedbackDetails.getBenCallID();
        Long actualBeneficiaryRegID = actualFeedbackDetails.getBeneficiaryRegID();
        String actualCreatedBy = actualFeedbackDetails.getCreatedBy();
        Timestamp actualCreatedDate = actualFeedbackDetails.getCreatedDate();
        Boolean actualDeleted = actualFeedbackDetails.getDeleted();
        Integer actualDesignationID = actualFeedbackDetails.getDesignationID();
        String actualFeedback = actualFeedbackDetails.getFeedback();
        Long actualFeedbackID = actualFeedbackDetails.getFeedbackID();
        List<FeedbackRequestDetails> actualFeedbackRequestDetails = actualFeedbackDetails.getFeedbackRequestDetails();
        Short actualFeedbackStatusID = actualFeedbackDetails.getFeedbackStatusID();
        Short actualFeedbackTypeID = actualFeedbackDetails.getFeedbackTypeID();
        Long actualInstitutionID = actualFeedbackDetails.getInstitutionID();
        Timestamp actualLastModDate = actualFeedbackDetails.getLastModDate();
        String actualModifiedBy = actualFeedbackDetails.getModifiedBy();
        Timestamp actualServiceAvailDate = actualFeedbackDetails.getServiceAvailDate();
        Integer actualServiceID = actualFeedbackDetails.getServiceID();
        Short actualSeverityID = actualFeedbackDetails.getSeverityID();
        actualFeedbackDetails.getSubServiceID();
        Integer actualUserID = actualFeedbackDetails.getUserID();

        // Assert that nothing has changed
        assertEquals("Feedback", actualFeedback);
        assertEquals("Jan 1, 2020 8:00am GMT+0100", actualCreatedBy);
        assertEquals("Jan 1, 2020 9:00am GMT+0100", actualModifiedBy);
        assertEquals(1, actualDesignationID.intValue());
        assertEquals(1, actualServiceID.intValue());
        assertEquals(1, actualUserID.intValue());
        assertEquals(1L, actualBenCallID.longValue());
        assertEquals(1L, actualBeneficiaryRegID.longValue());
        assertEquals(1L, actualFeedbackID.longValue());
        assertEquals(1L, actualInstitutionID.longValue());
        assertEquals((short) 1, actualFeedbackStatusID.shortValue());
        assertEquals((short) 1, actualFeedbackTypeID.shortValue());
        assertEquals((short) 1, actualSeverityID.shortValue());
        assertTrue(actualDeleted);
        assertSame(feedbackRequestDetails, actualFeedbackRequestDetails);
        assertSame(createdDate, actualCreatedDate);
        assertSame(lastModDate, actualLastModDate);
        assertSame(serviceAvailDate, actualServiceAvailDate);
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>
     * {@link FeedbackDetails#FeedbackDetails(Long, Short, Short, Short, String, String)}
     *   <li>{@link FeedbackDetails#setBenCallID(Long)}
     *   <li>{@link FeedbackDetails#setBeneficiaryRegID(Long)}
     *   <li>{@link FeedbackDetails#setCreatedBy(String)}
     *   <li>{@link FeedbackDetails#setCreatedDate(Timestamp)}
     *   <li>{@link FeedbackDetails#setDeleted(Boolean)}
     *   <li>{@link FeedbackDetails#setDesignationID(Integer)}
     *   <li>{@link FeedbackDetails#setFeedback(String)}
     *   <li>{@link FeedbackDetails#setFeedbackID(Long)}
     *   <li>{@link FeedbackDetails#setFeedbackRequestDetails(List)}
     *   <li>{@link FeedbackDetails#setFeedbackStatusID(Short)}
     *   <li>{@link FeedbackDetails#setFeedbackTypeID(Short)}
     *   <li>{@link FeedbackDetails#setInstitutionID(Long)}
     *   <li>{@link FeedbackDetails#setLastModDate(Timestamp)}
     *   <li>{@link FeedbackDetails#setModifiedBy(String)}
     *   <li>{@link FeedbackDetails#setServiceAvailDate(Timestamp)}
     *   <li>{@link FeedbackDetails#setServiceID(Integer)}
     *   <li>{@link FeedbackDetails#setSeverityID(Short)}
     *   <li>{@link FeedbackDetails#setUserID(Integer)}
     *   <li>{@link FeedbackDetails#setsMSPhoneNo(String)}
     *   <li>{@link FeedbackDetails#getBenCallID()}
     *   <li>{@link FeedbackDetails#getBeneficiaryRegID()}
     *   <li>{@link FeedbackDetails#getCreatedBy()}
     *   <li>{@link FeedbackDetails#getCreatedDate()}
     *   <li>{@link FeedbackDetails#getDeleted()}
     *   <li>{@link FeedbackDetails#getDesignationID()}
     *   <li>{@link FeedbackDetails#getFeedback()}
     *   <li>{@link FeedbackDetails#getFeedbackID()}
     *   <li>{@link FeedbackDetails#getFeedbackRequestDetails()}
     *   <li>{@link FeedbackDetails#getFeedbackStatusID()}
     *   <li>{@link FeedbackDetails#getFeedbackTypeID()}
     *   <li>{@link FeedbackDetails#getInstitutionID()}
     *   <li>{@link FeedbackDetails#getLastModDate()}
     *   <li>{@link FeedbackDetails#getModifiedBy()}
     *   <li>{@link FeedbackDetails#getServiceAvailDate()}
     *   <li>{@link FeedbackDetails#getServiceID()}
     *   <li>{@link FeedbackDetails#getSeverityID()}
     *   <li>{@link FeedbackDetails#getSubServiceID()}
     *   <li>{@link FeedbackDetails#getUserID()}
     * </ul>
     */
    @Test
    void testGettersAndSetters3() {
        // Arrange and Act
        FeedbackDetails actualFeedbackDetails = new FeedbackDetails(1L, (short) 1, (short) 1, (short) 1, "Feedback",
                "Jan 1, 2020 8:00am GMT+0100");
        actualFeedbackDetails.setBenCallID(1L);
        actualFeedbackDetails.setBeneficiaryRegID(1L);
        actualFeedbackDetails.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        Timestamp createdDate = mock(Timestamp.class);
        actualFeedbackDetails.setCreatedDate(createdDate);
        actualFeedbackDetails.setDeleted(true);
        actualFeedbackDetails.setDesignationID(1);
        actualFeedbackDetails.setFeedback("Feedback");
        actualFeedbackDetails.setFeedbackID(1L);
        ArrayList<FeedbackRequestDetails> feedbackRequestDetails = new ArrayList<>();
        actualFeedbackDetails.setFeedbackRequestDetails(feedbackRequestDetails);
        actualFeedbackDetails.setFeedbackStatusID((short) 1);
        actualFeedbackDetails.setFeedbackTypeID((short) 1);
        actualFeedbackDetails.setInstitutionID(1L);
        Timestamp lastModDate = mock(Timestamp.class);
        actualFeedbackDetails.setLastModDate(lastModDate);
        actualFeedbackDetails.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        Timestamp serviceAvailDate = mock(Timestamp.class);
        actualFeedbackDetails.setServiceAvailDate(serviceAvailDate);
        actualFeedbackDetails.setServiceID(1);
        actualFeedbackDetails.setSeverityID((short) 1);
        actualFeedbackDetails.setUserID(1);
        actualFeedbackDetails.setsMSPhoneNo("6625550144");
        Long actualBenCallID = actualFeedbackDetails.getBenCallID();
        Long actualBeneficiaryRegID = actualFeedbackDetails.getBeneficiaryRegID();
        String actualCreatedBy = actualFeedbackDetails.getCreatedBy();
        Timestamp actualCreatedDate = actualFeedbackDetails.getCreatedDate();
        Boolean actualDeleted = actualFeedbackDetails.getDeleted();
        Integer actualDesignationID = actualFeedbackDetails.getDesignationID();
        String actualFeedback = actualFeedbackDetails.getFeedback();
        Long actualFeedbackID = actualFeedbackDetails.getFeedbackID();
        List<FeedbackRequestDetails> actualFeedbackRequestDetails = actualFeedbackDetails.getFeedbackRequestDetails();
        Short actualFeedbackStatusID = actualFeedbackDetails.getFeedbackStatusID();
        Short actualFeedbackTypeID = actualFeedbackDetails.getFeedbackTypeID();
        Long actualInstitutionID = actualFeedbackDetails.getInstitutionID();
        Timestamp actualLastModDate = actualFeedbackDetails.getLastModDate();
        String actualModifiedBy = actualFeedbackDetails.getModifiedBy();
        Timestamp actualServiceAvailDate = actualFeedbackDetails.getServiceAvailDate();
        Integer actualServiceID = actualFeedbackDetails.getServiceID();
        Short actualSeverityID = actualFeedbackDetails.getSeverityID();
        actualFeedbackDetails.getSubServiceID();
        Integer actualUserID = actualFeedbackDetails.getUserID();

        // Assert that nothing has changed
        assertEquals("Feedback", actualFeedback);
        assertEquals("Jan 1, 2020 8:00am GMT+0100", actualCreatedBy);
        assertEquals("Jan 1, 2020 9:00am GMT+0100", actualModifiedBy);
        assertEquals(1, actualDesignationID.intValue());
        assertEquals(1, actualServiceID.intValue());
        assertEquals(1, actualUserID.intValue());
        assertEquals(1L, actualBenCallID.longValue());
        assertEquals(1L, actualBeneficiaryRegID.longValue());
        assertEquals(1L, actualFeedbackID.longValue());
        assertEquals(1L, actualInstitutionID.longValue());
        assertEquals((short) 1, actualFeedbackStatusID.shortValue());
        assertEquals((short) 1, actualFeedbackTypeID.shortValue());
        assertEquals((short) 1, actualSeverityID.shortValue());
        assertTrue(actualDeleted);
        assertSame(feedbackRequestDetails, actualFeedbackRequestDetails);
        assertSame(createdDate, actualCreatedDate);
        assertSame(lastModDate, actualLastModDate);
        assertSame(serviceAvailDate, actualServiceAvailDate);
    }
}
