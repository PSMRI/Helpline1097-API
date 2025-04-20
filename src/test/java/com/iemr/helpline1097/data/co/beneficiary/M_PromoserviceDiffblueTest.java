package com.iemr.helpline1097.data.co.beneficiary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class M_PromoserviceDiffblueTest {
    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link M_Promoservice#M_Promoservice()}
     *   <li>{@link M_Promoservice#setFamilyFriends(String)}
     *   <li>{@link M_Promoservice#setHealthcareWorker(String)}
     *   <li>{@link M_Promoservice#setNotDisclosed(String)}
     *   <li>{@link M_Promoservice#setOthers(String)}
     *   <li>{@link M_Promoservice#setPamphlet(String)}
     *   <li>{@link M_Promoservice#setRadio(String)}
     *   <li>{@link M_Promoservice#setTelevision(String)}
     *   <li>{@link M_Promoservice#toString()}
     *   <li>{@link M_Promoservice#getFamilyFriends()}
     *   <li>{@link M_Promoservice#getHealthcareWorker()}
     *   <li>{@link M_Promoservice#getId()}
     *   <li>{@link M_Promoservice#getNotDisclosed()}
     *   <li>{@link M_Promoservice#getOthers()}
     *   <li>{@link M_Promoservice#getPamphlet()}
     *   <li>{@link M_Promoservice#getRadio()}
     *   <li>{@link M_Promoservice#getTelevision()}
     * </ul>
     */
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

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>
     * {@link M_Promoservice#M_Promoservice(String, String, String, String, String, String, String)}
     *   <li>{@link M_Promoservice#setFamilyFriends(String)}
     *   <li>{@link M_Promoservice#setHealthcareWorker(String)}
     *   <li>{@link M_Promoservice#setNotDisclosed(String)}
     *   <li>{@link M_Promoservice#setOthers(String)}
     *   <li>{@link M_Promoservice#setPamphlet(String)}
     *   <li>{@link M_Promoservice#setRadio(String)}
     *   <li>{@link M_Promoservice#setTelevision(String)}
     *   <li>{@link M_Promoservice#toString()}
     *   <li>{@link M_Promoservice#getFamilyFriends()}
     *   <li>{@link M_Promoservice#getHealthcareWorker()}
     *   <li>{@link M_Promoservice#getId()}
     *   <li>{@link M_Promoservice#getNotDisclosed()}
     *   <li>{@link M_Promoservice#getOthers()}
     *   <li>{@link M_Promoservice#getPamphlet()}
     *   <li>{@link M_Promoservice#getRadio()}
     *   <li>{@link M_Promoservice#getTelevision()}
     * </ul>
     */
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
