package com.iemr.helpline1097.data.co.services;

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

@ContextConfiguration(classes = {CategoryDetails.class})
@ExtendWith(SpringExtension.class)
class CategoryDetailsDiffblueTest {
    @Autowired
    private CategoryDetails categoryDetails;

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link CategoryDetails#setCategoryDesc(String)}
     *   <li>{@link CategoryDetails#setCategoryID(Integer)}
     *   <li>{@link CategoryDetails#setCategoryName(String)}
     *   <li>{@link CategoryDetails#setCreatedBy(String)}
     *   <li>{@link CategoryDetails#setCreatedDate(Timestamp)}
     *   <li>{@link CategoryDetails#setDeleted(Boolean)}
     *   <li>{@link CategoryDetails#setLastModDate(Timestamp)}
     *   <li>{@link CategoryDetails#setModifiedBy(String)}
     *   <li>{@link CategoryDetails#toString()}
     *   <li>{@link CategoryDetails#getCategoryDesc()}
     *   <li>{@link CategoryDetails#getCategoryID()}
     *   <li>{@link CategoryDetails#getCategoryName()}
     *   <li>{@link CategoryDetails#getCreatedBy()}
     *   <li>{@link CategoryDetails#getCreatedDate()}
     *   <li>{@link CategoryDetails#getDeleted()}
     *   <li>{@link CategoryDetails#getLastModDate()}
     *   <li>{@link CategoryDetails#getModifiedBy()}
     *   <li>{@link CategoryDetails#getSubServiceID()}
     * </ul>
     */
    @Test
    void testGettersAndSetters() {
        // Arrange
        CategoryDetails categoryDetails = new CategoryDetails();

        // Act
        categoryDetails.setCategoryDesc("Category Desc");
        categoryDetails.setCategoryID(1);
        categoryDetails.setCategoryName("Category Name");
        categoryDetails.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        Timestamp createdDate = mock(Timestamp.class);
        categoryDetails.setCreatedDate(createdDate);
        categoryDetails.setDeleted(true);
        Timestamp lastModDate = mock(Timestamp.class);
        categoryDetails.setLastModDate(lastModDate);
        categoryDetails.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        String actualToStringResult = categoryDetails.toString();
        String actualCategoryDesc = categoryDetails.getCategoryDesc();
        Integer actualCategoryID = categoryDetails.getCategoryID();
        String actualCategoryName = categoryDetails.getCategoryName();
        String actualCreatedBy = categoryDetails.getCreatedBy();
        Timestamp actualCreatedDate = categoryDetails.getCreatedDate();
        Boolean actualDeleted = categoryDetails.getDeleted();
        Timestamp actualLastModDate = categoryDetails.getLastModDate();
        String actualModifiedBy = categoryDetails.getModifiedBy();
        categoryDetails.getSubServiceID();

        // Assert that nothing has changed
        assertEquals("Category Desc", actualCategoryDesc);
        assertEquals("Category Name", actualCategoryName);
        assertEquals("Jan 1, 2020 8:00am GMT+0100", actualCreatedBy);
        assertEquals("Jan 1, 2020 9:00am GMT+0100", actualModifiedBy);
        assertEquals(
                "{\"categoryID\":1,\"subServiceID\":null,\"subServices\":null,\"categoryName\":\"Category Name\",\"categoryDesc\""
                        + ":\"Category Desc\",\"deleted\":true,\"createdBy\":\"Jan 1, 2020 8:00am GMT+0100\"}",
                actualToStringResult);
        assertEquals(1, actualCategoryID.intValue());
        assertTrue(actualDeleted);
        assertSame(createdDate, actualCreatedDate);
        assertSame(lastModDate, actualLastModDate);
    }

    /**
     * Method under test: {@link CategoryDetails#CategoryDetails()}
     */
    @Test
    void testNewCategoryDetails() {
        // Arrange and Act
        CategoryDetails actualCategoryDetails = new CategoryDetails();

        // Assert
        assertNull(actualCategoryDetails.getSubServices());
        assertNull(actualCategoryDetails.getDeleted());
        assertNull(actualCategoryDetails.getCategoryID());
        assertNull(actualCategoryDetails.getSubServiceID());
        assertNull(actualCategoryDetails.getCategoryDesc());
        assertNull(actualCategoryDetails.getCategoryName());
        assertNull(actualCategoryDetails.getCreatedBy());
        assertNull(actualCategoryDetails.getModifiedBy());
        assertNull(actualCategoryDetails.getCreatedDate());
        assertNull(actualCategoryDetails.getLastModDate());
        assertNull(actualCategoryDetails.getBenCallServicesMappingHistories());
        assertNull(actualCategoryDetails.getSubCategories());
    }

    /**
     * Method under test: {@link CategoryDetails#CategoryDetails(Integer, String)}
     */
    @Test
    void testNewCategoryDetails2() {
        // Arrange and Act
        CategoryDetails actualCategoryDetails = new CategoryDetails(1, "Category Name");

        // Assert
        assertEquals("Category Name", actualCategoryDetails.getCategoryName());
        assertEquals(1, actualCategoryDetails.getCategoryID().intValue());
    }
}
