package com.iemr.helpline1097.data.co.feedbackResponse;

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

@ContextConfiguration(classes = {FeedbackResponse.class})
@ExtendWith(SpringExtension.class)
class FeedbackResponseDiffblueTest {
    @Autowired
    private FeedbackResponse feedbackResponse;

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link FeedbackResponse#setAuthUserID(Integer)}
     *   <li>{@link FeedbackResponse#setComments(String)}
     *   <li>{@link FeedbackResponse#setCreatedBy(String)}
     *   <li>{@link FeedbackResponse#setCreatedDate(Timestamp)}
     *   <li>{@link FeedbackResponse#setDeleted(Boolean)}
     *   <li>{@link FeedbackResponse#setFeedbackRequestID(Long)}
     *   <li>{@link FeedbackResponse#setFeedbackResponseID(Long)}
     *   <li>{@link FeedbackResponse#setLastModDate(Timestamp)}
     *   <li>{@link FeedbackResponse#setModifiedBy(String)}
     *   <li>{@link FeedbackResponse#setResponseSummary(String)}
     *   <li>{@link FeedbackResponse#toString()}
     *   <li>{@link FeedbackResponse#getAuthUserID()}
     *   <li>{@link FeedbackResponse#getComments()}
     *   <li>{@link FeedbackResponse#getCreatedBy()}
     *   <li>{@link FeedbackResponse#getCreatedDate()}
     *   <li>{@link FeedbackResponse#getDeleted()}
     *   <li>{@link FeedbackResponse#getFeedbackRequestID()}
     *   <li>{@link FeedbackResponse#getFeedbackResponseID()}
     *   <li>{@link FeedbackResponse#getLastModDate()}
     *   <li>{@link FeedbackResponse#getModifiedBy()}
     *   <li>{@link FeedbackResponse#getResponseSummary()}
     * </ul>
     */
    @Test
    void testGettersAndSetters() {
        // Arrange
        FeedbackResponse feedbackResponse = new FeedbackResponse();

        // Act
        feedbackResponse.setAuthUserID(1);
        feedbackResponse.setComments("Comments");
        feedbackResponse.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        Timestamp createdDate = mock(Timestamp.class);
        feedbackResponse.setCreatedDate(createdDate);
        feedbackResponse.setDeleted(true);
        feedbackResponse.setFeedbackRequestID(1L);
        feedbackResponse.setFeedbackResponseID(1L);
        Timestamp lastModDate = mock(Timestamp.class);
        feedbackResponse.setLastModDate(lastModDate);
        feedbackResponse.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        feedbackResponse.setResponseSummary("Response Summary");
        String actualToStringResult = feedbackResponse.toString();
        Integer actualAuthUserID = feedbackResponse.getAuthUserID();
        String actualComments = feedbackResponse.getComments();
        String actualCreatedBy = feedbackResponse.getCreatedBy();
        Timestamp actualCreatedDate = feedbackResponse.getCreatedDate();
        Boolean actualDeleted = feedbackResponse.getDeleted();
        Long actualFeedbackRequestID = feedbackResponse.getFeedbackRequestID();
        Long actualFeedbackResponseID = feedbackResponse.getFeedbackResponseID();
        Timestamp actualLastModDate = feedbackResponse.getLastModDate();
        String actualModifiedBy = feedbackResponse.getModifiedBy();

        // Assert that nothing has changed
        assertEquals("Comments", actualComments);
        assertEquals("Jan 1, 2020 8:00am GMT+0100", actualCreatedBy);
        assertEquals("Jan 1, 2020 9:00am GMT+0100", actualModifiedBy);
        assertEquals("Response Summary", feedbackResponse.getResponseSummary());
        assertEquals(
                "{\"FeedbackResponseID\":\"1\",\"FeedbackRequestID\":\"1\",\"ResponseSummary\":\"Response Summary\",\"AuthUserID\":1"
                        + ",\"Comments\":\"Comments\",\"Deleted\":true,\"CreatedBy\":\"Jan 1, 2020 8:00am GMT+0100\"}",
                actualToStringResult);
        assertEquals(1, actualAuthUserID.intValue());
        assertEquals(1L, actualFeedbackRequestID.longValue());
        assertEquals(1L, actualFeedbackResponseID.longValue());
        assertTrue(actualDeleted);
        assertSame(createdDate, actualCreatedDate);
        assertSame(lastModDate, actualLastModDate);
    }

    /**
     * Method under test: {@link FeedbackResponse#FeedbackResponse()}
     */
    @Test
    void testNewFeedbackResponse() {
        // Arrange and Act
        FeedbackResponse actualFeedbackResponse = new FeedbackResponse();

        // Assert
        assertNull(actualFeedbackResponse.getDeleted());
        assertNull(actualFeedbackResponse.getAuthUserID());
        assertNull(actualFeedbackResponse.getFeedbackRequestID());
        assertNull(actualFeedbackResponse.getFeedbackResponseID());
        assertNull(actualFeedbackResponse.getComments());
        assertNull(actualFeedbackResponse.getCreatedBy());
        assertNull(actualFeedbackResponse.getModifiedBy());
        assertNull(actualFeedbackResponse.getResponseSummary());
        assertNull(actualFeedbackResponse.getCreatedDate());
        assertNull(actualFeedbackResponse.getLastModDate());
    }

    /**
     * Method under test:
     * {@link FeedbackResponse#FeedbackResponse(Long, Long, String, Integer, String, Boolean, String, Timestamp, String, Timestamp)}
     */
    @Test
    void testNewFeedbackResponse2() {
        // Arrange
        Timestamp createdDate = mock(Timestamp.class);
        Timestamp lastModDate = mock(Timestamp.class);

        // Act
        FeedbackResponse actualFeedbackResponse = new FeedbackResponse(1L, 1L, "Response Summary", 1, "Comments", true,
                "Jan 1, 2020 8:00am GMT+0100", createdDate, "Jan 1, 2020 9:00am GMT+0100", lastModDate);

        // Assert
        assertEquals("Comments", actualFeedbackResponse.getComments());
        assertEquals("Jan 1, 2020 8:00am GMT+0100", actualFeedbackResponse.getCreatedBy());
        assertEquals("Jan 1, 2020 9:00am GMT+0100", actualFeedbackResponse.getModifiedBy());
        assertEquals("Response Summary", actualFeedbackResponse.getResponseSummary());
        assertEquals(1, actualFeedbackResponse.getAuthUserID().intValue());
        assertEquals(1L, actualFeedbackResponse.getFeedbackRequestID().longValue());
        assertEquals(1L, actualFeedbackResponse.getFeedbackResponseID().longValue());
        assertTrue(actualFeedbackResponse.getDeleted());
        assertSame(createdDate, actualFeedbackResponse.getCreatedDate());
        assertSame(lastModDate, actualFeedbackResponse.getLastModDate());
    }
}
