package com.iemr.helpline1097.data.everwell;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class EverwellGuidelinesDiffblueTest {
    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link EverwellGuidelines#getAdherencePercentage()}
     *   <li>{@link EverwellGuidelines#getCategory()}
     *   <li>{@link EverwellGuidelines#getId()}
     *   <li>{@link EverwellGuidelines#getModifiedBy()}
     *   <li>{@link EverwellGuidelines#getProviderServiceMapID()}
     * </ul>
     */
    @Test
    void testGettersAndSetters() {
        // Arrange
        EverwellGuidelines everwellGuidelines = new EverwellGuidelines();

        // Act
        Integer actualAdherencePercentage = everwellGuidelines.getAdherencePercentage();
        String actualCategory = everwellGuidelines.getCategory();
        Integer actualId = everwellGuidelines.getId();
        String actualModifiedBy = everwellGuidelines.getModifiedBy();

        // Assert
        assertNull(actualAdherencePercentage);
        assertNull(everwellGuidelines.getProviderServiceMapID());
        assertNull(actualId);
        assertNull(actualCategory);
        assertNull(actualModifiedBy);
    }
}
