package com.iemr.helpline1097.data.co.feedback;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class FeedbackStatusDiffblueTest {
    /**
     * Method under test: default or parameterless constructor of
     * {@link FeedbackStatus}
     */
    @Test
    void testNewFeedbackStatus() {
        // Arrange and Act
        FeedbackStatus actualFeedbackStatus = new FeedbackStatus();

        // Assert
        assertNull(actualFeedbackStatus.getDeleted());
        assertNull(actualFeedbackStatus.getFeedbackStatusID());
        assertNull(actualFeedbackStatus.getProviderServiceMapID());
        assertNull(actualFeedbackStatus.getCreatedBy());
        assertNull(actualFeedbackStatus.getFeedbackStatus());
        assertNull(actualFeedbackStatus.getFeedbackStatusDesc());
        assertNull(actualFeedbackStatus.getModifiedBy());
        assertNull(actualFeedbackStatus.getCreatedDate());
        assertNull(actualFeedbackStatus.getLastModDate());
    }
}
