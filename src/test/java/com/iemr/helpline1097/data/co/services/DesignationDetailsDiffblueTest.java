package com.iemr.helpline1097.data.co.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.sql.Timestamp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {DesignationDetails.class})
@ExtendWith(SpringExtension.class)
class DesignationDetailsDiffblueTest {
    @Autowired
    private DesignationDetails designationDetails;

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link DesignationDetails#setCreatedBy(String)}
     *   <li>{@link DesignationDetails#setCreatedDate(Timestamp)}
     *   <li>{@link DesignationDetails#setDeleted(Boolean)}
     *   <li>{@link DesignationDetails#setDesignationDesc(String)}
     *   <li>{@link DesignationDetails#setDesignationID(Integer)}
     *   <li>{@link DesignationDetails#setDesignationName(String)}
     *   <li>{@link DesignationDetails#setLastModDate(Timestamp)}
     *   <li>{@link DesignationDetails#setModifiedBy(String)}
     *   <li>{@link DesignationDetails#toString()}
     *   <li>{@link DesignationDetails#getCreatedBy()}
     *   <li>{@link DesignationDetails#getCreatedDate()}
     *   <li>{@link DesignationDetails#getDesignationDesc()}
     *   <li>{@link DesignationDetails#getDesignationID()}
     *   <li>{@link DesignationDetails#getDesignationName()}
     *   <li>{@link DesignationDetails#getLastModDate()}
     *   <li>{@link DesignationDetails#getModifiedBy()}
     *   <li>{@link DesignationDetails#isDeleted()}
     * </ul>
     */
    @Test
    void testGettersAndSetters() {
        // Arrange
        DesignationDetails designationDetails = new DesignationDetails();

        // Act
        designationDetails.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        Timestamp createdDate = mock(Timestamp.class);
        designationDetails.setCreatedDate(createdDate);
        designationDetails.setDeleted(true);
        designationDetails.setDesignationDesc("Designation Desc");
        designationDetails.setDesignationID(1);
        designationDetails.setDesignationName("Designation Name");
        Timestamp lastModDate = mock(Timestamp.class);
        designationDetails.setLastModDate(lastModDate);
        designationDetails.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        String actualToStringResult = designationDetails.toString();
        String actualCreatedBy = designationDetails.getCreatedBy();
        Timestamp actualCreatedDate = designationDetails.getCreatedDate();
        String actualDesignationDesc = designationDetails.getDesignationDesc();
        Integer actualDesignationID = designationDetails.getDesignationID();
        String actualDesignationName = designationDetails.getDesignationName();
        Timestamp actualLastModDate = designationDetails.getLastModDate();
        String actualModifiedBy = designationDetails.getModifiedBy();
        Boolean actualIsDeletedResult = designationDetails.isDeleted();

        // Assert that nothing has changed
        assertEquals("Designation Desc", actualDesignationDesc);
        assertEquals("Designation Name", actualDesignationName);
        assertEquals("Jan 1, 2020 8:00am GMT+0100", actualCreatedBy);
        assertEquals("Jan 1, 2020 9:00am GMT+0100", actualModifiedBy);
        assertEquals(
                "{\"designationID\":1,\"designationName\":\"Designation Name\",\"designationDesc\":\"Designation Desc\",\"deleted"
                        + "\":true,\"createdBy\":\"Jan 1, 2020 8:00am GMT+0100\"}",
                actualToStringResult);
        assertEquals(1, actualDesignationID.intValue());
        assertTrue(actualIsDeletedResult);
        assertSame(createdDate, actualCreatedDate);
        assertSame(lastModDate, actualLastModDate);
    }

    /**
     * Method under test: {@link DesignationDetails#DesignationDetails()}
     */
    @Test
    void testNewDesignationDetails() {
        // Arrange and Act
        DesignationDetails actualDesignationDetails = new DesignationDetails();

        // Assert
        assertNull(actualDesignationDetails.getDeleted());
        assertNull(actualDesignationDetails.isDeleted());
        assertNull(actualDesignationDetails.getDesignationID());
        assertNull(actualDesignationDetails.getCreatedBy());
        assertNull(actualDesignationDetails.getDesignationDesc());
        assertNull(actualDesignationDetails.getDesignationName());
        assertNull(actualDesignationDetails.getModifiedBy());
        assertNull(actualDesignationDetails.getCreatedDate());
        assertNull(actualDesignationDetails.getLastModDate());
    }

    /**
     * Method under test:
     * {@link DesignationDetails#DesignationDetails(Integer, String)}
     */
    @Test
    void testNewDesignationDetails2() {
        // Arrange and Act
        DesignationDetails actualDesignationDetails = new DesignationDetails(1, "Designation Name");

        // Assert
        assertEquals("Designation Name", actualDesignationDetails.getDesignationName());
        assertEquals(1, actualDesignationDetails.getDesignationID().intValue());
    }
}
