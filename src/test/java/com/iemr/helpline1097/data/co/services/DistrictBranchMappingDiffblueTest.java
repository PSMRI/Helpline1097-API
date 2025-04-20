package com.iemr.helpline1097.data.co.services;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class DistrictBranchMappingDiffblueTest {
    /**
     * Method under test: default or parameterless constructor of
     * {@link DistrictBranchMapping}
     */
    @Test
    void testNewDistrictBranchMapping() {
        // Arrange and Act
        DistrictBranchMapping actualDistrictBranchMapping = new DistrictBranchMapping();

        // Assert
        assertNull(actualDistrictBranchMapping.getDeleted());
        assertNull(actualDistrictBranchMapping.getBlockID());
        assertNull(actualDistrictBranchMapping.getDistrictBranchID());
        assertNull(actualDistrictBranchMapping.getCreatedBy());
        assertNull(actualDistrictBranchMapping.getHabitat());
        assertNull(actualDistrictBranchMapping.getModifiedBy());
        assertNull(actualDistrictBranchMapping.getPanchayatName());
        assertNull(actualDistrictBranchMapping.getPinCode());
        assertNull(actualDistrictBranchMapping.getVillageName());
        assertNull(actualDistrictBranchMapping.getCreatedDate());
        assertNull(actualDistrictBranchMapping.getLastModDate());
    }
}
