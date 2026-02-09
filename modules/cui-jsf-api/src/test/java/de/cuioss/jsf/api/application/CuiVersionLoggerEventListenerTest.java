/*
 * Copyright © 2025 CUI-OpenSource-Software (info@cuioss.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.api.application;

import static org.junit.jupiter.api.Assertions.assertTrue;

import de.cuioss.jsf.api.common.logging.JsfApiLogMessages;
import de.cuioss.test.juli.LogAsserts;
import de.cuioss.test.juli.TestLogLevel;
import de.cuioss.test.juli.junit5.EnableTestLogger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@EnableTestLogger
@DisplayName("Tests for CuiVersionLoggerEventListener")
class CuiVersionLoggerEventListenerTest {

    @Test
    @DisplayName("Should always be listener for any source")
    void shouldBeListenerForAnySource() {
        // Arrange
        var listener = new CuiVersionLoggerEventListener();

        // Act & Assert
        assertTrue(listener.isListenerForSource(new Object()),
                "Should return true for any source");
    }

    @Test
    @DisplayName("Should log version info on processEvent")
    void shouldLogVersionInfoOnProcessEvent() {
        // Arrange
        var listener = new CuiVersionLoggerEventListener();

        // Act - event parameter is not used by the listener
        listener.processEvent(null);

        // Assert
        LogAsserts.assertSingleLogMessagePresentContaining(TestLogLevel.INFO,
                JsfApiLogMessages.INFO.VERSION_INFO.resolveIdentifierString());
    }
}
