package com.iemr.helpline1097.data.co.calltype;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {M_Calltype.class})
@ExtendWith(SpringExtension.class)
class M_CalltypeDiffblueTest {
    @Autowired
    private M_Calltype m_Calltype;

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link M_Calltype#setCallType(String)}
     *   <li>{@link M_Calltype#setInvalidType(String)}
     *   <li>{@link M_Calltype#setRemarks(String)}
     *   <li>{@link M_Calltype#toString()}
     *   <li>{@link M_Calltype#getCallType()}
     *   <li>{@link M_Calltype#getId()}
     *   <li>{@link M_Calltype#getInvalidType()}
     *   <li>{@link M_Calltype#getRemarks()}
     * </ul>
     */
    @Test
    void testGettersAndSetters() {
        // Arrange
        M_Calltype m_Calltype = new M_Calltype();

        // Act
        m_Calltype.setCallType("Call Type");
        m_Calltype.setInvalidType("Invalid Type");
        m_Calltype.setRemarks("Remarks");
        String actualToStringResult = m_Calltype.toString();
        String actualCallType = m_Calltype.getCallType();
        m_Calltype.getId();
        String actualInvalidType = m_Calltype.getInvalidType();

        // Assert that nothing has changed
        assertEquals("Call Type", actualCallType);
        assertEquals("Invalid Type", actualInvalidType);
        assertEquals("Remarks", m_Calltype.getRemarks());
        assertEquals("{\"id\":null,\"callType\":\"Call Type\",\"remarks\":\"Remarks\",\"invalidType\":\"Invalid Type\"}",
                actualToStringResult);
    }

    /**
     * Method under test: {@link M_Calltype#M_Calltype()}
     */
    @Test
    void testNewM_Calltype() {
        // Arrange and Act
        M_Calltype actualM_Calltype = new M_Calltype();

        // Assert
        assertNull(actualM_Calltype.getId());
        assertNull(actualM_Calltype.getCallType());
        assertNull(actualM_Calltype.getInvalidType());
        assertNull(actualM_Calltype.getRemarks());
    }

    /**
     * Method under test: {@link M_Calltype#M_Calltype(String, String, String)}
     */
    @Test
    void testNewM_Calltype2() {
        // Arrange and Act
        M_Calltype actualM_Calltype = new M_Calltype("Call Type", "Remarks", "Invalid Type");

        // Assert
        assertEquals("Call Type", actualM_Calltype.getCallType());
        assertEquals("Invalid Type", actualM_Calltype.getInvalidType());
        assertEquals("Remarks", actualM_Calltype.getRemarks());
    }
}
