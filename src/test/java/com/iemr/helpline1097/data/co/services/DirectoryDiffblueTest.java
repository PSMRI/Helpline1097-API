package com.iemr.helpline1097.data.co.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {Directory.class})
@ExtendWith(SpringExtension.class)
class DirectoryDiffblueTest {
    @Autowired
    private Directory directory;

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link Directory#toString()}
     *   <li>{@link Directory#getProviderServiceMapID()}
     * </ul>
     */
    @Test
    void testGettersAndSetters() {
        // Arrange
        Directory directory = new Directory();

        // Act
        String actualToStringResult = directory.toString();

        // Assert
        assertEquals(
                "{\"instituteDirectoryID\":null,\"instituteDirectoryName\":null,\"instituteDirectoryDesc\":null,\"deleted\""
                        + ":null,\"createdBy\":null,\"providerServiceMapID\":null}",
                actualToStringResult);
        assertNull(directory.getProviderServiceMapID());
    }

    /**
     * Method under test: {@link Directory#Directory()}
     */
    @Test
    void testNewDirectory() {
        // Arrange and Act
        Directory actualDirectory = new Directory();

        // Assert
        assertNull(actualDirectory.getDeleted());
        assertNull(actualDirectory.getInstituteDirectoryID());
        assertNull(actualDirectory.getProviderServiceMapID());
        assertNull(actualDirectory.getCreatedBy());
        assertNull(actualDirectory.getInstituteDirectoryDesc());
        assertNull(actualDirectory.getInstituteDirectoryName());
        assertNull(actualDirectory.getModifiedBy());
        assertNull(actualDirectory.getCreatedDate());
        assertNull(actualDirectory.getLastModDate());
    }

    /**
     * Method under test: {@link Directory#Directory(Integer, String)}
     */
    @Test
    void testNewDirectory2() {
        // Arrange and Act
        Directory actualDirectory = new Directory(1, "/directory");

        // Assert
        assertEquals("/directory", actualDirectory.getInstituteDirectoryName());
        assertEquals(1, actualDirectory.getInstituteDirectoryID().intValue());
    }
}
