package com.iemr.helpline1097.data.co.feedbackRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.sql.Timestamp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {FeedbackRequest.class})
@ExtendWith(SpringExtension.class)
class FeedbackRequestDiffblueTest {
    @Autowired
    private FeedbackRequest feedbackRequest;

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link FeedbackRequest#setComments(String)}
     *   <li>{@link FeedbackRequest#setCreatedBy(String)}
     *   <li>{@link FeedbackRequest#setCreatedDate(Timestamp)}
     *   <li>{@link FeedbackRequest#setDeleted(Boolean)}
     *   <li>{@link FeedbackRequest#setFeedbackRequestID(Long)}
     *   <li>{@link FeedbackRequest#setFeedbackSupSummary(String)}
     *   <li>{@link FeedbackRequest#setLastModDate(Timestamp)}
     *   <li>{@link FeedbackRequest#setModifiedBy(String)}
     *   <li>{@link FeedbackRequest#setSupUserID(Integer)}
     *   <li>{@link FeedbackRequest#toString()}
     *   <li>{@link FeedbackRequest#getComments()}
     *   <li>{@link FeedbackRequest#getCreatedBy()}
     *   <li>{@link FeedbackRequest#getCreatedDate()}
     *   <li>{@link FeedbackRequest#getDeleted()}
     *   <li>{@link FeedbackRequest#getFeedbackRequestID()}
     *   <li>{@link FeedbackRequest#getFeedbackSupSummary()}
     *   <li>{@link FeedbackRequest#getLastModDate()}
     *   <li>{@link FeedbackRequest#getModifiedBy()}
     *   <li>{@link FeedbackRequest#getSupUserID()}
     * </ul>
     */
    @Test
    void testGettersAndSetters() {
        // Arrange
        FeedbackRequest feedbackRequest = new FeedbackRequest();

        // Act
        feedbackRequest.setComments("Comments");
        feedbackRequest.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        Timestamp createdDate = mock(Timestamp.class);
        feedbackRequest.setCreatedDate(createdDate);
        feedbackRequest.setDeleted(true);
        feedbackRequest.setFeedbackRequestID(1L);
        feedbackRequest.setFeedbackSupSummary("Feedback Sup Summary");
        Timestamp lastModDate = mock(Timestamp.class);
        feedbackRequest.setLastModDate(lastModDate);
        feedbackRequest.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        feedbackRequest.setSupUserID(1);
        String actualToStringResult = feedbackRequest.toString();
        String actualComments = feedbackRequest.getComments();
        String actualCreatedBy = feedbackRequest.getCreatedBy();
        Timestamp actualCreatedDate = feedbackRequest.getCreatedDate();
        Boolean actualDeleted = feedbackRequest.getDeleted();
        Long actualFeedbackRequestID = feedbackRequest.getFeedbackRequestID();
        String actualFeedbackSupSummary = feedbackRequest.getFeedbackSupSummary();
        Timestamp actualLastModDate = feedbackRequest.getLastModDate();
        String actualModifiedBy = feedbackRequest.getModifiedBy();

        // Assert that nothing has changed
        assertEquals("Comments", actualComments);
        assertEquals("Feedback Sup Summary", actualFeedbackSupSummary);
        assertEquals("Jan 1, 2020 8:00am GMT+0100", actualCreatedBy);
        assertEquals("Jan 1, 2020 9:00am GMT+0100", actualModifiedBy);
        assertEquals(
                "{\"FeedbackRequestID\":\"1\",\"FeedbackSupSummary\":\"Feedback Sup Summary\",\"SupUserID\":1,\"Comments\":\"Comments"
                        + "\",\"Deleted\":true,\"CreatedBy\":\"Jan 1, 2020 8:00am GMT+0100\"}",
                actualToStringResult);
        assertEquals(1, feedbackRequest.getSupUserID().intValue());
        assertEquals(1L, actualFeedbackRequestID.longValue());
        assertTrue(actualDeleted);
        assertSame(createdDate, actualCreatedDate);
        assertSame(lastModDate, actualLastModDate);
    }

    /**
     * Method under test: {@link FeedbackRequest#FeedbackRequest()}
     */
    @Test
    void testNewFeedbackRequest() {
        // Arrange and Act
        FeedbackRequest actualFeedbackRequest = new FeedbackRequest();

        // Assert
        assertNull(actualFeedbackRequest.getDeleted());
        assertNull(actualFeedbackRequest.getSupUserID());
        assertNull(actualFeedbackRequest.getFeedbackRequestID());
        assertNull(actualFeedbackRequest.getComments());
        assertNull(actualFeedbackRequest.getCreatedBy());
        assertNull(actualFeedbackRequest.getFeedbackSupSummary());
        assertNull(actualFeedbackRequest.getModifiedBy());
        assertNull(actualFeedbackRequest.getCreatedDate());
        assertNull(actualFeedbackRequest.getLastModDate());
    }

    /**
     * Method under test:
     * {@link FeedbackRequest#FeedbackRequest(Long, String, Integer, String, Boolean, String, Timestamp, String, Timestamp)}
     */
    @Test
    void testNewFeedbackRequest2() {
        // Arrange
        Timestamp createdDate = mock(Timestamp.class);
        Timestamp lastModDate = mock(Timestamp.class);

        // Act
        FeedbackRequest actualFeedbackRequest = new FeedbackRequest(1L, "Feedback Sup Summary", 1, "Comments", true,
                "Jan 1, 2020 8:00am GMT+0100", createdDate, "Jan 1, 2020 9:00am GMT+0100", lastModDate);

        // Assert
        assertEquals("Comments", actualFeedbackRequest.getComments());
        assertEquals("Feedback Sup Summary", actualFeedbackRequest.getFeedbackSupSummary());
        assertEquals("Jan 1, 2020 8:00am GMT+0100", actualFeedbackRequest.getCreatedBy());
        assertEquals("Jan 1, 2020 9:00am GMT+0100", actualFeedbackRequest.getModifiedBy());
        assertEquals(1, actualFeedbackRequest.getSupUserID().intValue());
        assertEquals(1L, actualFeedbackRequest.getFeedbackRequestID().longValue());
        assertTrue(actualFeedbackRequest.getDeleted());
        assertSame(createdDate, actualFeedbackRequest.getCreatedDate());
        assertSame(lastModDate, actualFeedbackRequest.getLastModDate());
    }
}
