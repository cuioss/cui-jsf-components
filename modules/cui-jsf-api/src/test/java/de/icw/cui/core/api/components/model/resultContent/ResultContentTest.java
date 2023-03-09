package de.icw.cui.core.api.components.model.resultContent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import de.cuioss.test.juli.LogAsserts;
import de.cuioss.test.juli.TestLogLevel;
import de.cuioss.test.juli.junit5.EnableTestLogger;
import de.cuioss.test.valueobjects.contract.SerializableContractImpl;
import de.cuioss.tools.logging.CuiLogger;
import de.cuioss.uimodel.nameprovider.DisplayName;
import de.cuioss.uimodel.result.ResultDetail;
import de.cuioss.uimodel.result.ResultObject;
import de.cuioss.uimodel.result.ResultState;

@EnableTestLogger
class ResultContentTest {

    private static final CuiLogger log = new CuiLogger(ResultContentTest.class);

    @Test
    void testEmpty() {
        var underTest = new ResultContent();
        assertEquals(underTest, SerializableContractImpl.serializeAndDeserialize(underTest));
        assertNotNull(underTest.getNotificationBoxMessages());
        assertTrue(underTest.getNotificationBoxMessages().isEmpty());
        assertTrue(underTest.isRenderContent());
    }

    @Test
    void testValid() {
        var underTest = new ResultContent(new ResultObject<>("", ResultState.VALID), log);
        assertEquals(underTest, SerializableContractImpl.serializeAndDeserialize(underTest));
        assertNotNull(underTest.getNotificationBoxMessages());
        assertTrue(underTest.getNotificationBoxMessages().isEmpty());
        assertTrue(underTest.isRenderContent());
    }

    @Test
    void testError() {
        var underTest = new ResultContent(
                new ResultObject<>("", ResultState.ERROR, new ResultDetail(new DisplayName("Test")), null), log);
        assertEquals(underTest, SerializableContractImpl.serializeAndDeserialize(underTest));
        assertNotNull(underTest.getNotificationBoxMessages());
        assertFalse(underTest.getNotificationBoxMessages().isEmpty());
        assertFalse(underTest.isRenderContent());
        LogAsserts.assertLogMessagePresentContaining(TestLogLevel.ERROR, "silent");
    }

}
