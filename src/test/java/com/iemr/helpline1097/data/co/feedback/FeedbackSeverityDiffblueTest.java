package com.iemr.helpline1097.data.co.feedback;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {FeedbackSeverity.class})
@ExtendWith(SpringExtension.class)
class FeedbackSeverityDiffblueTest {
    @Autowired
    private FeedbackSeverity feedbackSeverity;

    /**
     * Method under test: {@link FeedbackSeverity#FeedbackSeverity()}
     */
    @Test
    void testNewFeedbackSeverity() {
        // Arrange and Act
        FeedbackSeverity actualFeedbackSeverity = new FeedbackSeverity();

        // Assert
        assertNull(actualFeedbackSeverity.getDeleted());
        assertNull(actualFeedbackSeverity.getSeverityID());
        assertNull(actualFeedbackSeverity.getCreatedBy());
        assertNull(actualFeedbackSeverity.getModifiedBy());
        assertNull(actualFeedbackSeverity.getSeverityDesc());
        assertNull(actualFeedbackSeverity.getSeverityTypeName());
        assertNull(actualFeedbackSeverity.getCreatedDate());
        assertNull(actualFeedbackSeverity.getLastModDate());
    }

    /**
     * Method under test: {@link FeedbackSeverity#FeedbackSeverity(Integer, String)}
     */
    @Test
    void testNewFeedbackSeverity2() {
        // Arrange and Act
        FeedbackSeverity actualFeedbackSeverity = new FeedbackSeverity(1, "S1");

        // Assert
        assertEquals("S1", actualFeedbackSeverity.getSeverityTypeName());
        assertEquals(1, actualFeedbackSeverity.getSeverityID().intValue());
    }

    /**
     * Method under test: {@link FeedbackSeverity#toString()}
     */
    @Test
    void testToString() {
        // Arrange, Act and Assert
        assertEquals(
                "{\"severityID\":null,\"severityTypeName\":null,\"severityDesc\":null,\"deleted\":null,\"createdBy\":null}",
                (new FeedbackSeverity()).toString());
    }
}
