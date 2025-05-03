/*
 * Copyright 2023 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.api.components.model.resultContent;

import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.test.juli.LogAsserts;
import de.cuioss.test.juli.TestLogLevel;
import de.cuioss.test.juli.junit5.EnableTestLogger;
import de.cuioss.test.valueobjects.contract.SerializableContractImpl;
import de.cuioss.tools.logging.CuiLogger;
import de.cuioss.uimodel.nameprovider.DisplayName;
import de.cuioss.uimodel.result.ResultDetail;
import de.cuioss.uimodel.result.ResultObject;
import de.cuioss.uimodel.result.ResultState;
import org.junit.jupiter.api.Test;

@EnableTestLogger
class ResultContentTest {

    private static final CuiLogger log = new CuiLogger(ResultContentTest.class);

    @Test
    void empty() {
        var underTest = new ResultContent();
        assertEquals(underTest, SerializableContractImpl.serializeAndDeserialize(underTest));
        assertNotNull(underTest.getNotificationBoxMessages());
        assertTrue(underTest.getNotificationBoxMessages().isEmpty());
        assertTrue(underTest.isRenderContent());
    }

    @Test
    void valid() {
        var underTest = new ResultContent(new ResultObject<>("", ResultState.VALID), log);
        assertEquals(underTest, SerializableContractImpl.serializeAndDeserialize(underTest));
        assertNotNull(underTest.getNotificationBoxMessages());
        assertTrue(underTest.getNotificationBoxMessages().isEmpty());
        assertTrue(underTest.isRenderContent());
    }

    @Test
    void error() {
        var underTest = new ResultContent(
                new ResultObject<>("", ResultState.ERROR, new ResultDetail(new DisplayName("Test")), null), log);
        assertEquals(underTest, SerializableContractImpl.serializeAndDeserialize(underTest));
        assertNotNull(underTest.getNotificationBoxMessages());
        assertFalse(underTest.getNotificationBoxMessages().isEmpty());
        assertFalse(underTest.isRenderContent());
        LogAsserts.assertLogMessagePresentContaining(TestLogLevel.ERROR, "silent");
    }

}
