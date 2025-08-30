package com.iemr.helpline1097.data.co.feedback;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(MockitoExtension.class)
class FeedbackSeverityTest {
    @InjectMocks
    private FeedbackSeverity feedbackSeverity;

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
    
    @Test
    void testNewFeedbackSeverity2() {
        // Arrange and Act
        FeedbackSeverity actualFeedbackSeverity = new FeedbackSeverity(1, "S1");

        // Assert
        assertEquals("S1", actualFeedbackSeverity.getSeverityTypeName());
        assertEquals(1, actualFeedbackSeverity.getSeverityID().intValue());
    }

    @Test
    void testToString() {
        // Arrange, Act and Assert
        assertEquals(
                "{\"severityID\":null,\"severityTypeName\":null,\"severityDesc\":null,\"deleted\":null,\"createdBy\":null}",
                (new FeedbackSeverity()).toString());
    }
}
