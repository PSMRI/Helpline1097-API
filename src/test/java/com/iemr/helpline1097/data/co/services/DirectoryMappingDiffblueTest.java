package com.iemr.helpline1097.data.co.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;

import com.iemr.helpline1097.utils.mapper.OutputMapper;

import java.sql.Timestamp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {DirectoryMapping.class})
@ExtendWith(SpringExtension.class)
class DirectoryMappingDiffblueTest {
    @Autowired
    private DirectoryMapping directoryMapping;

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link DirectoryMapping#DirectoryMapping(Long, InstitutionDetails)}
     *   <li>{@link DirectoryMapping#toString()}
     * </ul>
     */
    @Test
    void testGettersAndSetters() {
        // Arrange
        DistrictBlock block = new DistrictBlock();
        block.setBlockID(1);
        block.setBlockName("Block Name");
        block.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        block.setCreatedDate(mock(Timestamp.class));
        block.setDeleted(true);
        block.setDistrictID(1);
        block.setLastModDate(mock(Timestamp.class));
        block.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        block.setOutputMapper(new OutputMapper());
        block.setStateID(1);

        DistrictBranchMapping branch = new DistrictBranchMapping();
        branch.setBlockID(1);
        branch.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        branch.setCreatedDate(mock(Timestamp.class));
        branch.setDeleted(true);
        branch.setDistrictBranchID(1);
        branch.setHabitat("Habitat");
        branch.setLastModDate(mock(Timestamp.class));
        branch.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        branch.setPanchayatName("Panchayat Name");
        branch.setPinCode("Pin Code");
        branch.setVillageName("Village Name");

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

        Districts m_district = new Districts();
        m_district.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        m_district.setCreatedDate(mock(Timestamp.class));
        m_district.setDeleted(true);
        m_district.setDistrictID(1);
        m_district.setDistrictName("District Name");
        m_district.setLastModDate(mock(Timestamp.class));
        m_district.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        m_district.setOutputMapper(new OutputMapper());
        m_district.setStateID(1);
        m_district.setStates(states);

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

        InstitutionDetails institution = new InstitutionDetails();
        institution.setAddress("42 Main St");
        institution.setBlock(block);
        institution.setBlockID(1);
        institution.setBranch(branch);
        institution.setContactNo1("Contact No1");
        institution.setContactNo2("Contact No2");
        institution.setContactNo3("Contact No3");
        institution.setContactPerson1("Contact Person1");
        institution.setContactPerson1Email("jane.doe@example.org");
        institution.setContactPerson2("Contact Person2");
        institution.setContactPerson2Email("jane.doe@example.org");
        institution.setContactPerson3("Contact Person3");
        institution.setContactPerson3Email("jane.doe@example.org");
        institution.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        institution.setCreatedDate(mock(Timestamp.class));
        institution.setDeleted(true);
        institution.setDistrictBranchMappingID(1);
        institution.setDistrictID(1);
        institution.setInstitutionID(1);
        institution.setInstitutionName("Institution Name");
        institution.setLastModDate(mock(Timestamp.class));
        institution.setM_district(m_district);
        institution.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        institution.setOutputMapper(new OutputMapper());
        institution.setStateID(1);
        institution.setStates(states2);
        institution.setWebsite("Website");

        // Act and Assert
        assertEquals(
                "{\"instituteDirMapID\":\"1\",\"institutionID\":null,\"institutionDetails\":{\"institutionID\":1,\"institutionName"
                        + "\":\"Institution Name\",\"stateID\":1,\"states\":{\"stateID\":1,\"stateName\":\"MD\",\"stateCode\":\"MD\",\"countryID\""
                        + ":1,\"deleted\":true,\"createdBy\":\"Jan 1, 2020 8:00am GMT+0100\"},\"districtID\":1,\"m_district\":{\"districtID"
                        + "\":1,\"stateID\":1,\"districtName\":\"District Name\",\"deleted\":true,\"createdBy\":\"Jan 1, 2020 8:00am"
                        + " GMT+0100\"},\"blockID\":1,\"block\":{\"blockID\":1,\"districtID\":1,\"blockName\":\"Block Name\",\"stateID\":1,"
                        + "\"deleted\":true,\"createdBy\":\"Jan 1, 2020 8:00am GMT+0100\"},\"districtBranchMappingID\":1,\"branch\":{"
                        + "\"districtBranchID\":1,\"blockID\":1,\"panchayatName\":\"Panchayat Name\",\"villageName\":\"Village Name\",\"habitat"
                        + "\":\"Habitat\",\"pinCode\":\"Pin Code\",\"deleted\":true,\"createdBy\":\"Jan 1, 2020 8:00am GMT+0100\"},\"address\":\"42"
                        + " Main St\",\"contactPerson1\":\"Contact Person1\",\"contactNo1\":\"Contact No1\",\"contactPerson1Email\":\"jane"
                        + ".doe@example.org\",\"contactPerson2\":\"Contact Person2\",\"contactNo2\":\"Contact No2\",\"contactPerson2Email"
                        + "\":\"jane.doe@example.org\",\"contactPerson3\":\"Contact Person3\",\"contactNo3\":\"Contact No3\",\"contactPerson3Email"
                        + "\":\"jane.doe@example.org\",\"website\":\"Website\",\"deleted\":true,\"createdBy\":\"Jan 1, 2020 8:00am"
                        + " GMT+0100\"},\"instituteDirectoryID\":null,\"directory\":null,\"instituteSubDirectoryID\":null,\"subDirectory"
                        + "\":null,\"instituteRouteDirectoryID\":null,\"deleted\":null,\"createdBy\":null}",
                (new DirectoryMapping(1L, institution)).toString());
    }

    /**
     * Method under test: {@link DirectoryMapping#DirectoryMapping()}
     */
    @Test
    void testNewDirectoryMapping() {
        // Arrange and Act
        DirectoryMapping actualDirectoryMapping = new DirectoryMapping();

        // Assert
        assertNull(actualDirectoryMapping.getDirectory());
        assertNull(actualDirectoryMapping.getSubDirectory());
        assertNull(actualDirectoryMapping.getInstitutionDetails());
        assertNull(actualDirectoryMapping.getDeleted());
        assertNull(actualDirectoryMapping.getInstituteDirectoryID());
        assertNull(actualDirectoryMapping.getInstituteRouteDirectoryID());
        assertNull(actualDirectoryMapping.getInstituteSubDirectoryID());
        assertNull(actualDirectoryMapping.getInstitutionID());
        assertNull(actualDirectoryMapping.getInstituteDirMapID());
        assertNull(actualDirectoryMapping.getCreatedBy());
        assertNull(actualDirectoryMapping.getModifiedBy());
        assertNull(actualDirectoryMapping.getCreatedDate());
        assertNull(actualDirectoryMapping.getLastModDate());
    }

    /**
     * Method under test:
     * {@link DirectoryMapping#DirectoryMapping(Long, Integer, String, String, String, String, String)}
     */
    @Test
    void testNewDirectoryMapping2() {
        // Arrange and Act
        DirectoryMapping actualDirectoryMapping = new DirectoryMapping(1L, 1, "Institution Name", "42 Main St",
                "Contact No1", "Contact No2", "Contact No3");

        // Assert
        InstitutionDetails institutionDetails = actualDirectoryMapping.getInstitutionDetails();
        assertEquals("42 Main St", institutionDetails.getAddress());
        assertEquals("Contact No1", institutionDetails.getContactNo1());
        assertEquals("Contact No2", institutionDetails.getContactNo2());
        assertEquals("Contact No3", institutionDetails.getContactNo3());
        assertEquals("Institution Name", institutionDetails.getInstitutionName());
        assertNull(institutionDetails.getBlock());
        assertEquals(1, institutionDetails.getInstitutionID().intValue());
        assertEquals(1L, actualDirectoryMapping.getInstituteDirMapID().longValue());
    }
}
