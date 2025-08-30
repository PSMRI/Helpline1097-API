package com.iemr.helpline1097.data.co.feedback;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {FeedbackType.class})
@ExtendWith(SpringExtension.class)
class FeedbackTypeDiffblueTest {
    @Autowired
    private FeedbackType feedbackType;

    /**
     * Method under test: {@link FeedbackType#FeedbackType()}
     */
    @Test
    void testNewFeedbackType() {
        // Arrange and Act
        FeedbackType actualFeedbackType = new FeedbackType();

        // Assert
        assertNull(actualFeedbackType.getDeleted());
        assertNull(actualFeedbackType.getFeedbackTypeID());
        assertNull(actualFeedbackType.getCreatedBy());
        assertNull(actualFeedbackType.getFeedbackDesc());
        assertNull(actualFeedbackType.getFeedbackTypeName());
        assertNull(actualFeedbackType.getModifiedBy());
        assertNull(actualFeedbackType.getCreatedDate());
        assertNull(actualFeedbackType.getLastModDate());
    }

    /**
     * Method under test: {@link FeedbackType#FeedbackType(Integer, String)}
     */
    @Test
    void testNewFeedbackType2() {
        // Arrange and Act
        FeedbackType actualFeedbackType = new FeedbackType(1, "Feedback Type Name");

        // Assert
        assertEquals("Feedback Type Name", actualFeedbackType.getFeedbackTypeName());
        assertEquals(1, actualFeedbackType.getFeedbackTypeID().intValue());
    }

    /**
     * Method under test: {@link FeedbackType#toString()}
     */
    @Test
    void testToString() {
        // Arrange, Act and Assert
        assertEquals(
                "{\"feedbackTypeID\":null,\"feedbackTypeName\":null,\"feedbackDesc\":null,\"deleted\":null,\"createdBy\":null}",
                (new FeedbackType()).toString());
    }
}
