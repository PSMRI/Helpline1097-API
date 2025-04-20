package com.iemr.helpline1097.data.co.beneficiary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class M_PromoserviceTest {
    
    @Test
    void testGettersAndSetters() {
        // Arrange and Act
        M_Promoservice actualM_Promoservice = new M_Promoservice();
        actualM_Promoservice.setFamilyFriends("Family Friends");
        actualM_Promoservice.setHealthcareWorker("Healthcare Worker");
        actualM_Promoservice.setNotDisclosed("Not Disclosed");
        actualM_Promoservice.setOthers("Others");
        actualM_Promoservice.setPamphlet("Pamphlet");
        actualM_Promoservice.setRadio("Radio");
        actualM_Promoservice.setTelevision("Television");
        String actualToStringResult = actualM_Promoservice.toString();
        String actualFamilyFriends = actualM_Promoservice.getFamilyFriends();
        String actualHealthcareWorker = actualM_Promoservice.getHealthcareWorker();
        actualM_Promoservice.getId();
        String actualNotDisclosed = actualM_Promoservice.getNotDisclosed();
        String actualOthers = actualM_Promoservice.getOthers();
        String actualPamphlet = actualM_Promoservice.getPamphlet();
        String actualRadio = actualM_Promoservice.getRadio();

        // Assert that nothing has changed
        assertEquals("Family Friends", actualFamilyFriends);
        assertEquals("Healthcare Worker", actualHealthcareWorker);
        assertEquals("Not Disclosed", actualNotDisclosed);
        assertEquals("Others", actualOthers);
        assertEquals("Pamphlet", actualPamphlet);
        assertEquals("Radio", actualRadio);
        assertEquals("Television", actualM_Promoservice.getTelevision());
        assertEquals(
                "{\"id\":null,\"pamphlet\":\"Pamphlet\",\"radio\":\"Radio\",\"television\":\"Television\",\"familyFriends\":\"Family"
                        + " Friends\",\"healthcareWorker\":\"Healthcare Worker\",\"others\":\"Others\",\"notDisclosed\":\"Not Disclosed\"}",
                actualToStringResult);
    }

    @Test
    void testGettersAndSetters2() {
        // Arrange and Act
        M_Promoservice actualM_Promoservice = new M_Promoservice("Pamphlet", "Radio", "Television", "Family Friends",
                "Healthcare Worker", "Others", "Not Disclosed");
        actualM_Promoservice.setFamilyFriends("Family Friends");
        actualM_Promoservice.setHealthcareWorker("Healthcare Worker");
        actualM_Promoservice.setNotDisclosed("Not Disclosed");
        actualM_Promoservice.setOthers("Others");
        actualM_Promoservice.setPamphlet("Pamphlet");
        actualM_Promoservice.setRadio("Radio");
        actualM_Promoservice.setTelevision("Television");
        String actualToStringResult = actualM_Promoservice.toString();
        String actualFamilyFriends = actualM_Promoservice.getFamilyFriends();
        String actualHealthcareWorker = actualM_Promoservice.getHealthcareWorker();
        actualM_Promoservice.getId();
        String actualNotDisclosed = actualM_Promoservice.getNotDisclosed();
        String actualOthers = actualM_Promoservice.getOthers();
        String actualPamphlet = actualM_Promoservice.getPamphlet();
        String actualRadio = actualM_Promoservice.getRadio();

        // Assert that nothing has changed
        assertEquals("Family Friends", actualFamilyFriends);
        assertEquals("Healthcare Worker", actualHealthcareWorker);
        assertEquals("Not Disclosed", actualNotDisclosed);
        assertEquals("Others", actualOthers);
        assertEquals("Pamphlet", actualPamphlet);
        assertEquals("Radio", actualRadio);
        assertEquals("Television", actualM_Promoservice.getTelevision());
        assertEquals(
                "{\"id\":null,\"pamphlet\":\"Pamphlet\",\"radio\":\"Radio\",\"television\":\"Television\",\"familyFriends\":\"Family"
                        + " Friends\",\"healthcareWorker\":\"Healthcare Worker\",\"others\":\"Others\",\"notDisclosed\":\"Not Disclosed\"}",
                actualToStringResult);
    }
}
