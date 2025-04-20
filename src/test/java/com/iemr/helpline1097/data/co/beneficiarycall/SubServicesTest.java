package com.iemr.helpline1097.data.co.beneficiarycall;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(MockitoExtension.class)
class SubServicesTest {
    @InjectMocks
    private SubServices subServices;

    @Test
    void testGettersAndSetters() {
        // Arrange
        SubServices subServices = new SubServices();

        // Act
        String actualToStringResult = subServices.toString();

        // Assert
        assertEquals(
                "{\"subServiceID\":null,\"subServiceName\":null,\"subServiceDesc\":null,\"deleted\":null,\"createdBy\":null}",
                actualToStringResult);
        assertNull(subServices.getSubServiceID());
    }

    @Test
    void testNewSubServices() {
        // Arrange and Act
        SubServices actualSubServices = new SubServices();

        // Assert
        assertNull(actualSubServices.getDeleted());
        assertNull(actualSubServices.getSubServiceID());
        assertNull(actualSubServices.getCreatedBy());
        assertNull(actualSubServices.getModifiedBy());
        assertNull(actualSubServices.getSubServiceDesc());
        assertNull(actualSubServices.getSubServiceName());
        assertNull(actualSubServices.getCreatedDate());
        assertNull(actualSubServices.getLastModDate());
        assertNull(actualSubServices.getBenCallServicesMappingHistories());
        assertNull(actualSubServices.getCategoryDetails());
    }

    @Test
    void testNewSubServices2() {
        // Arrange and Act
        SubServices actualSubServices = new SubServices(1, "Sub Service Name", "Sub Service Desc", true);

        // Assert
        assertEquals("Sub Service Desc", actualSubServices.getSubServiceDesc());
        assertEquals("Sub Service Name", actualSubServices.getSubServiceName());
        assertEquals(1, actualSubServices.getSubServiceID().intValue());
        assertTrue(actualSubServices.getDeleted());
    }
}
