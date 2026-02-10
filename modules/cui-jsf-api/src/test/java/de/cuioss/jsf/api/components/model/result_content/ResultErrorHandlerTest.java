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
package de.cuioss.jsf.api.components.model.result_content;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.cuioss.jsf.api.common.logging.JsfApiLogMessages;
import de.cuioss.jsf.api.components.css.ContextState;
import de.cuioss.test.juli.LogAsserts;
import de.cuioss.test.juli.TestLogLevel;
import de.cuioss.test.juli.junit5.EnableTestLogger;
import de.cuioss.tools.logging.CuiLogger;
import de.cuioss.uimodel.nameprovider.IDisplayNameProvider;
import de.cuioss.uimodel.result.ResultDetail;
import de.cuioss.uimodel.result.ResultState;
import jakarta.faces.application.FacesMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@EnableTestLogger
@DisplayName("Tests for ResultErrorHandler")
class ResultErrorHandlerTest {

    private static final CuiLogger LOGGER = new CuiLogger(ResultErrorHandlerTest.class);

    private ResultErrorHandler underTest;
    private TestErrorController errorController;

    @BeforeEach
    void setUp() {
        underTest = new ResultErrorHandler();
        errorController = new TestErrorController();
    }

    @Test
    @DisplayName("Should log error and disable content rendering for ERROR state")
    void shouldLogErrorForErrorState() {
        // Arrange
        var cause = new IllegalStateException("test error");
        var detail = new ResultDetail(
                new de.cuioss.uimodel.nameprovider.DisplayName("Error occurred"), cause);

        // Act
        underTest.handleResultDetail(ResultState.ERROR, detail, null, errorController, LOGGER);

        // Assert
        assertFalse(errorController.renderContent, "Content should not be rendered for ERROR state");
        LogAsserts.assertSingleLogMessagePresentContaining(TestLogLevel.ERROR,
                JsfApiLogMessages.ERROR.ERROR_HANDLED_SILENT.resolveIdentifierString());
    }

    @Test
    @DisplayName("Should log warning for WARNING state with cause")
    void shouldLogWarningForWarningState() {
        // Arrange
        var cause = new IllegalStateException("test warning");
        var detail = new ResultDetail(
                new de.cuioss.uimodel.nameprovider.DisplayName("Warning occurred"), cause);

        // Act
        underTest.handleResultDetail(ResultState.WARNING, detail, null, errorController, LOGGER);

        // Assert
        assertTrue(errorController.renderContent, "Content should be rendered for WARNING state");
        LogAsserts.assertSingleLogMessagePresentContaining(TestLogLevel.WARN,
                JsfApiLogMessages.WARN.ERROR_HANDLED_SILENT.resolveIdentifierString());
    }

    @Test
    @DisplayName("Should log info for INFO state with cause")
    void shouldLogInfoForInfoState() {
        // Arrange
        var cause = new IllegalStateException("test info");
        var detail = new ResultDetail(
                new de.cuioss.uimodel.nameprovider.DisplayName("Info occurred"), cause);

        // Act
        underTest.handleResultDetail(ResultState.INFO, detail, null, errorController, LOGGER);

        // Assert
        assertTrue(errorController.renderContent, "Content should be rendered for INFO state");
        LogAsserts.assertSingleLogMessagePresentContaining(TestLogLevel.INFO,
                JsfApiLogMessages.INFO.ERROR_HANDLED_SILENT.resolveIdentifierString());
    }

    @Test
    @DisplayName("Should handle VALID state without logging")
    void shouldHandleValidState() {
        // Arrange
        var detail = new ResultDetail(
                new de.cuioss.uimodel.nameprovider.DisplayName("Valid result"));

        // Act
        underTest.handleResultDetail(ResultState.VALID, detail, null, errorController, LOGGER);

        // Assert
        assertTrue(errorController.renderContent, "Content should be rendered for VALID state");
    }

    /**
     * Simple test implementation of ErrorController for capturing state.
     */
    private static class TestErrorController implements ErrorController {

        boolean renderContent;

        @Override
        public void addNotificationBox(IDisplayNameProvider<?> value, ContextState state) {
            // no-op for test
        }

        @Override
        public void addGlobalFacesMessage(IDisplayNameProvider<?> value, FacesMessage.Severity severity) {
            // no-op for test
        }

        @Override
        public void addStickyMessage(IDisplayNameProvider<?> value, ContextState state) {
            // no-op for test
        }

        @Override
        public void setRenderContent(boolean renderContent) {
            this.renderContent = renderContent;
        }
    }
}
