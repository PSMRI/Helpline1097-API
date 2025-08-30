package com.iemr.helpline1097.data.co.feedback;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class InstituteTypeDiffblueTest {
    /**
     * Method under test: default or parameterless constructor of
     * {@link InstituteType}
     */
    @Test
    void testNewInstituteType() {
        // Arrange and Act
        InstituteType actualInstituteType = new InstituteType();

        // Assert
        assertNull(actualInstituteType.getDeleted());
        assertNull(actualInstituteType.getInstitutionTypeID());
        assertNull(actualInstituteType.getProviderServiceMapID());
        assertNull(actualInstituteType.getCreatedBy());
        assertNull(actualInstituteType.getInstitutionType());
        assertNull(actualInstituteType.getInstitutionTypeDesc());
        assertNull(actualInstituteType.getModifiedBy());
        assertNull(actualInstituteType.getCreatedDate());
        assertNull(actualInstituteType.getLastModDate());
    }
}
