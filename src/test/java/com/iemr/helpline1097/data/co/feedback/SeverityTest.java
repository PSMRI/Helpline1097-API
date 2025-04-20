package com.iemr.helpline1097.data.co.feedback;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class SeverityTest {
    
    @Test
    void testNewSeverity() {
        // Arrange and Act
        Severity actualSeverity = new Severity();

        // Assert
        assertNull(actualSeverity.getDeleted());
        assertNull(actualSeverity.getProviderServiceMapID());
        assertNull(actualSeverity.getSeverityID());
        assertNull(actualSeverity.getCreatedBy());
        assertNull(actualSeverity.getModifiedBy());
        assertNull(actualSeverity.getSeverityDesc());
        assertNull(actualSeverity.getSeverityTypeName());
        assertNull(actualSeverity.getCreatedDate());
        assertNull(actualSeverity.getLastModDate());
    }
}
