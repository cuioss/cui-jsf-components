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
package de.cuioss.jsf.api.application.message;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.cuioss.jsf.api.CoreJsfTestConfiguration;
import de.cuioss.jsf.api.EnableJSFCDIEnvironment;
import de.cuioss.jsf.api.EnableResourceBundleSupport;
import de.cuioss.jsf.api.common.logging.JsfApiLogMessages;
import de.cuioss.jsf.api.converter.nameprovider.LabeledKeyConverter;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import de.cuioss.test.juli.LogAsserts;
import de.cuioss.test.juli.TestLogLevel;
import de.cuioss.test.juli.junit5.EnableTestLogger;
import de.cuioss.test.valueobjects.junit5.contracts.ShouldHandleObjectContracts;
import de.cuioss.uimodel.nameprovider.LabeledKey;
import de.cuioss.uimodel.result.ResultDetail;
import de.cuioss.uimodel.result.ResultObject;
import de.cuioss.uimodel.result.ResultState;
import jakarta.inject.Inject;
import lombok.Getter;
import org.jboss.weld.junit5.ExplicitParamInjection;
import org.jboss.weld.junit5.auto.AddBeanClasses;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@EnableJSFCDIEnvironment
@EnableResourceBundleSupport
@AddBeanClasses(MessageProducerMock.class)
@JsfTestConfiguration({CoreJsfTestConfiguration.class})
@EnableTestLogger
@ExplicitParamInjection
@DisplayName("Tests for DisplayNameMessageProducer")
class DisplayNameMessageProducerTest
        implements ShouldHandleObjectContracts<DisplayNameMessageProducer> {

    private static final String STRING_RESULT = "invalid e-Mail Address syntax";

    private static final String MESSAGE_KEY = "de.cuioss.common.email.invalid";

    private static final LabeledKey DETAIL = new LabeledKey(MESSAGE_KEY);

    @Inject
    private MessageProducerMock messageProducerMock;

    @Inject
    @Getter
    private DisplayNameMessageProducer underTest;

    @Nested
    @DisplayName("Tests for showAsGlobalMessageAndLog method")
    class ShowAsGlobalMessageAndLogTests {

        @Test
        @DisplayName("Should display error message for ERROR state")
        void shouldDisplayErrorMessage(ComponentConfigDecorator componentConfig) {
            // Arrange
            componentConfig.registerConverter(LabeledKeyConverter.class, LabeledKey.class);
            var result = new ResultObject<>(STRING_RESULT, ResultState.ERROR, new ResultDetail(DETAIL));

            // Act
            underTest.showAsGlobalMessageAndLog(result);

            // Assert
            assertEquals(1, messageProducerMock.getGlobalMessages().size(),
                    "Should add exactly one global message");
            messageProducerMock.assertSingleGlobalMessageWithKeyPresent(STRING_RESULT);
        }

        @Test
        @DisplayName("Should display warning message for WARNING state")
        void shouldDisplayWarningMessage(ComponentConfigDecorator componentConfig) {
            // Arrange
            componentConfig.registerConverter(LabeledKeyConverter.class, LabeledKey.class);
            var result = new ResultObject<>(STRING_RESULT, ResultState.WARNING, new ResultDetail(DETAIL));

            // Act
            underTest.showAsGlobalMessageAndLog(result);

            // Assert
            assertEquals(1, messageProducerMock.getGlobalMessages().size(),
                    "Should add exactly one global message");
            messageProducerMock.assertSingleGlobalMessageWithKeyPresent(STRING_RESULT);
        }

        @Test
        @DisplayName("Should display info message for INFO state")
        void shouldDisplayInfoMessage(ComponentConfigDecorator componentConfig) {
            // Arrange
            componentConfig.registerConverter(LabeledKeyConverter.class, LabeledKey.class);
            var result = new ResultObject<>(STRING_RESULT, ResultState.INFO, new ResultDetail(DETAIL));

            // Act
            underTest.showAsGlobalMessageAndLog(result);

            // Assert
            assertEquals(1, messageProducerMock.getGlobalMessages().size(),
                    "Should add exactly one global message");
            messageProducerMock.assertSingleGlobalMessageWithKeyPresent(STRING_RESULT);
        }

        @Test
        @DisplayName("Should not display message for VALID state")
        void shouldNotDisplayMessageForValidState() {
            // Arrange
            var result = new ResultObject<>(STRING_RESULT, ResultState.VALID, null);

            // Act
            underTest.showAsGlobalMessageAndLog(result);

            // Assert
            assertEquals(0, messageProducerMock.getGlobalMessages().size(),
                    "Should not add any global messages for VALID state");
        }

        @Test
        @DisplayName("Should log error for ERROR state with cause")
        void shouldLogErrorForErrorStateWithCause(ComponentConfigDecorator componentConfig) {
            // Arrange
            componentConfig.registerConverter(LabeledKeyConverter.class, LabeledKey.class);
            var cause = new IllegalStateException("test error");
            var result = new ResultObject<>(STRING_RESULT, ResultState.ERROR,
                    new ResultDetail(DETAIL, cause));

            // Act
            underTest.showAsGlobalMessageAndLog(result);

            // Assert
            LogAsserts.assertSingleLogMessagePresentContaining(TestLogLevel.ERROR,
                    JsfApiLogMessages.ERROR.SILENT_ERROR.resolveIdentifierString());
        }

        @Test
        @DisplayName("Should log warning for WARNING state with cause")
        void shouldLogWarningForWarningStateWithCause(ComponentConfigDecorator componentConfig) {
            // Arrange
            componentConfig.registerConverter(LabeledKeyConverter.class, LabeledKey.class);
            var cause = new IllegalStateException("test warning");
            var result = new ResultObject<>(STRING_RESULT, ResultState.WARNING,
                    new ResultDetail(DETAIL, cause));

            // Act
            underTest.showAsGlobalMessageAndLog(result);

            // Assert
            LogAsserts.assertSingleLogMessagePresentContaining(TestLogLevel.WARN,
                    JsfApiLogMessages.WARN.SILENT_ERROR.resolveIdentifierString());
        }

        @Test
        @DisplayName("Should log info for INFO state with cause")
        void shouldLogInfoForInfoStateWithCause(ComponentConfigDecorator componentConfig) {
            // Arrange
            componentConfig.registerConverter(LabeledKeyConverter.class, LabeledKey.class);
            var cause = new IllegalStateException("test info");
            var result = new ResultObject<>(STRING_RESULT, ResultState.INFO,
                    new ResultDetail(DETAIL, cause));

            // Act
            underTest.showAsGlobalMessageAndLog(result);

            // Assert
            LogAsserts.assertSingleLogMessagePresentContaining(TestLogLevel.INFO,
                    JsfApiLogMessages.INFO.SILENT_ERROR.resolveIdentifierString());
        }
    }
}
