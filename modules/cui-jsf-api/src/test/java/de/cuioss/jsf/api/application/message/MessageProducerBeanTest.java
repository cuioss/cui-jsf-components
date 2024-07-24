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
package de.cuioss.jsf.api.application.message;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.application.FacesMessage;
import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.EnableJSFCDIEnvironment;
import de.cuioss.jsf.api.EnableResourceBundleSupport;
import de.cuioss.jsf.api.ProjectStageProducerMock;
import de.cuioss.portal.common.stage.ProjectStage;
import de.cuioss.test.jsf.util.JsfEnvironmentConsumer;
import de.cuioss.test.jsf.util.JsfEnvironmentHolder;
import de.cuioss.test.valueobjects.junit5.contracts.ShouldHandleObjectContracts;
import lombok.Getter;
import lombok.Setter;

@EnableJSFCDIEnvironment
@EnableResourceBundleSupport
class MessageProducerBeanTest implements ShouldHandleObjectContracts<MessageProducerBean>, JsfEnvironmentConsumer {

    @Setter
    @Getter
    private JsfEnvironmentHolder environmentHolder;

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
    void shouldSetGlobalErrorMessage() {
        underTest.setGlobalErrorMessage(MESSAGE_KEY, MESSAGE_PARAMETER);
        assertMessageContent(FacesMessage.SEVERITY_ERROR, DETAIL_MESSAGE);
    }

    @Test
    void shouldAddGlobalErrorMessage() {
        underTest.addGlobalMessage(MESSAGE_PARAMETER, FacesMessage.SEVERITY_ERROR);
        assertMessageContent(FacesMessage.SEVERITY_ERROR, MESSAGE_PARAMETER);
    }

    @Test
    void shouldAddErrorMessage() {
        underTest.addMessage(MESSAGE_PARAMETER, FacesMessage.SEVERITY_ERROR, COMPONENT_ID);
        final var messages = getFacesContext().getMessageList(COMPONENT_ID);
        assertEquals(1, messages.size());
        final var facesMessage = messages.get(0);
        assertEquals(FacesMessage.SEVERITY_ERROR, facesMessage.getSeverity());
        assertEquals(MESSAGE_PARAMETER, facesMessage.getDetail());
    }

    @Test
    void shouldGracesfullyActOnInvalidMessageKey() {

        projectStage.setProjectStage(ProjectStage.DEVELOPMENT);

        final var message = underTest.getMessageFor(MESSAGE_KEY_NOT_THERE, FacesMessage.SEVERITY_INFO);
        assertTrue(message.getDetail().contains(MESSAGE_KEY_NOT_THERE));
        assertTrue(message.getDetail().startsWith(MessageProducerBean.MISSING_KEY_PREFIX));
    }

    @Test
    void shouldSetGlobalInfoMessage() {
        underTest.setGlobalInfoMessage(MESSAGE_KEY, MESSAGE_PARAMETER);
        assertMessageContent(FacesMessage.SEVERITY_INFO, DETAIL_MESSAGE);
    }

    @Test
    void shouldCreateInfoMessage() {
        assertNotNull(underTest.getInfoMessageFor(MESSAGE_KEY, MESSAGE_PARAMETER));
    }

    @Test
    void shouldCreateErrorMessage() {
        assertNotNull(underTest.getErrorMessageFor(MESSAGE_KEY, MESSAGE_PARAMETER));
    }

    @Test
    void shouldSetGlobalWarnMessage() {
        underTest.setGlobalWarningMessage(MESSAGE_KEY, MESSAGE_PARAMETER);
        assertMessageContent(FacesMessage.SEVERITY_WARN, DETAIL_MESSAGE);
    }

    @Test
    void shouldSetComponentMessage() {
        underTest.setFacesMessage(MESSAGE_KEY, FacesMessage.SEVERITY_INFO, COMPONENT_ID, MESSAGE_PARAMETER);
        final var messages = getFacesContext().getMessageList(COMPONENT_ID);
        final var facesMessage = messages.get(0);
        assertEquals(FacesMessage.SEVERITY_INFO, facesMessage.getSeverity());
        assertEquals(DETAIL_MESSAGE, facesMessage.getDetail());
    }

    @Test
    void testNoMsgParams() {
        assertDoesNotThrow(() -> underTest.addMessage("no-args", FacesMessage.SEVERITY_WARN, COMPONENT_ID),
                "missing varargs should work");
        assertDoesNotThrow(() -> underTest.addMessage("no-args", FacesMessage.SEVERITY_WARN, COMPONENT_ID),
                "empty array as varargs should work");
    }

    /**
     * Asserts that one message exists with the given content
     */
    private void assertMessageContent(final FacesMessage.Severity severity, final String detailText) {
        final var messages = getFacesContext().getMessageList();
        assertEquals(1, messages.size());
        final var facesMessage = messages.get(0);
        assertEquals(severity, facesMessage.getSeverity());
        assertEquals(detailText, facesMessage.getDetail());
    }
}
