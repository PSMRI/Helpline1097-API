package com.iemr.helpline1097.data.co.feedback;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class DesignationTest {
    
    @Test
    void testNewDesignation() {
        // Arrange and Act
        Designation actualDesignation = new Designation();

        // Assert
        assertNull(actualDesignation.getDeleted());
        assertNull(actualDesignation.getDesignationID());
        assertNull(actualDesignation.getCreatedBy());
        assertNull(actualDesignation.getDesignationDesc());
        assertNull(actualDesignation.getDesignationName());
        assertNull(actualDesignation.getModifiedBy());
        assertNull(actualDesignation.getCreatedDate());
        assertNull(actualDesignation.getLastModDate());
    }
}
