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

import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.jsf.api.EnableJSFCDIEnvironment;
import de.cuioss.jsf.api.EnableResourceBundleSupport;
import de.cuioss.jsf.api.ProjectStageProducerMock;
import de.cuioss.portal.common.stage.ProjectStage;
import de.cuioss.test.valueobjects.junit5.contracts.ShouldHandleObjectContracts;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import lombok.Getter;
import org.jboss.weld.junit5.ExplicitParamInjection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@EnableJSFCDIEnvironment
@ExplicitParamInjection
@EnableResourceBundleSupport
@DisplayName("Tests for MessageProducerBean")
class MessageProducerBeanTest implements ShouldHandleObjectContracts<MessageProducerBean> {

    private static final String MESSAGE_PARAMETER = "Test";

    private static final String MESSAGE_KEY = "cc.document.file.upload.invalid.size.message";

    private static final String MESSAGE_KEY_NOT_THERE = "message.is.not.there";

    private static final String DETAIL_MESSAGE = "The file you want to upload exceeds the maximum file size of Test.";

    private static final String COMPONENT_ID = "some.id";

    @Inject
    private ProjectStageProducerMock projectStage;

    @Inject
    @Getter
    private MessageProducerBean underTest;

    @Test
    @DisplayName("Should set global error message with key and parameter")
    void shouldSetGlobalErrorMessage(FacesContext facesContext) {
        // Act
        underTest.setGlobalErrorMessage(MESSAGE_KEY, MESSAGE_PARAMETER);

        // Assert
        assertMessageContent(facesContext, FacesMessage.SEVERITY_ERROR, DETAIL_MESSAGE);
    }

    @Test
    @DisplayName("Should add global error message with direct text")
    void shouldAddGlobalErrorMessage(FacesContext facesContext) {
        // Act
        underTest.addGlobalMessage(MESSAGE_PARAMETER, FacesMessage.SEVERITY_ERROR);

        // Assert
        assertMessageContent(facesContext, FacesMessage.SEVERITY_ERROR, MESSAGE_PARAMETER);
    }

    @Test
    @DisplayName("Should add error message to specific component")
    void shouldAddErrorMessage(FacesContext facesContext) {
        // Act
        underTest.addMessage(MESSAGE_PARAMETER, FacesMessage.SEVERITY_ERROR, COMPONENT_ID);

        // Assert
        final var messages = facesContext.getMessageList(COMPONENT_ID);
        assertEquals(1, messages.size(), "Should add exactly one message to the component");
        final var facesMessage = messages.getFirst();
        assertEquals(FacesMessage.SEVERITY_ERROR, facesMessage.getSeverity(),
                "Message should have ERROR severity");
        assertEquals(MESSAGE_PARAMETER, facesMessage.getDetail(),
                "Message detail should match the provided parameter");
    }

    @Test
    @DisplayName("Should handle invalid message keys gracefully")
    void shouldGracefullyHandleInvalidMessageKey() {
        // Arrange
        projectStage.setProjectStage(ProjectStage.DEVELOPMENT);

        // Act
        final var message = underTest.getMessageFor(MESSAGE_KEY_NOT_THERE, FacesMessage.SEVERITY_INFO);

        // Assert
        assertTrue(message.getDetail().contains(MESSAGE_KEY_NOT_THERE),
                "Message detail should contain the missing key");
        assertTrue(message.getDetail().startsWith(MessageProducerBean.MISSING_KEY_PREFIX),
                "Message should start with the missing key prefix");
    }

    @Test
    @DisplayName("Should set global info message with key and parameter")
    void shouldSetGlobalInfoMessage(FacesContext facesContext) {
        // Act
        underTest.setGlobalInfoMessage(MESSAGE_KEY, MESSAGE_PARAMETER);

        // Assert
        assertMessageContent(facesContext, FacesMessage.SEVERITY_INFO, DETAIL_MESSAGE);
    }

    @Test
    @DisplayName("Should create info message from key and parameter")
    void shouldCreateInfoMessage() {
        // Act & Assert
        assertNotNull(underTest.getInfoMessageFor(MESSAGE_KEY, MESSAGE_PARAMETER),
                "Should create a non-null info message");
    }

    @Test
    @DisplayName("Should create error message from key and parameter")
    void shouldCreateErrorMessage() {
        // Act & Assert
        assertNotNull(underTest.getErrorMessageFor(MESSAGE_KEY, MESSAGE_PARAMETER),
                "Should create a non-null error message");
    }

    @Test
    @DisplayName("Should set global warning message with key and parameter")
    void shouldSetGlobalWarnMessage(FacesContext facesContext) {
        // Act
        underTest.setGlobalWarningMessage(MESSAGE_KEY, MESSAGE_PARAMETER);

        // Assert
        assertMessageContent(facesContext, FacesMessage.SEVERITY_WARN, DETAIL_MESSAGE);
    }

    @Test
    @DisplayName("Should set message with key and severity to specific component")
    void shouldSetComponentMessage(FacesContext facesContext) {
        // Act
        underTest.setFacesMessage(MESSAGE_KEY, FacesMessage.SEVERITY_INFO, COMPONENT_ID, MESSAGE_PARAMETER);

        // Assert
        final var messages = facesContext.getMessageList(COMPONENT_ID);
        final var facesMessage = messages.getFirst();
        assertEquals(FacesMessage.SEVERITY_INFO, facesMessage.getSeverity(),
                "Message should have INFO severity");
        assertEquals(DETAIL_MESSAGE, facesMessage.getDetail(),
                "Message detail should match the resolved message");
    }

    @Test
    @DisplayName("Should handle missing message parameters")
    void shouldHandleMissingMessageParameters() {
        // Act & Assert
        assertDoesNotThrow(() -> underTest.addMessage("no-args", FacesMessage.SEVERITY_WARN, COMPONENT_ID),
                "Missing varargs should work");
        assertDoesNotThrow(() -> underTest.addMessage("no-args", FacesMessage.SEVERITY_WARN, COMPONENT_ID),
                "Empty array as varargs should work");
    }

    /**
     * Asserts that one message exists with the given content
     */
    private void assertMessageContent(FacesContext facesContext, final FacesMessage.Severity severity, final String detailText) {
        final var messages = facesContext.getMessageList();
        assertEquals(1, messages.size(), "Should have exactly one message");
        final var facesMessage = messages.getFirst();
        assertEquals(severity, facesMessage.getSeverity(), "Message should have the expected severity");
        assertEquals(detailText, facesMessage.getDetail(), "Message detail should match expected text");
    }
}