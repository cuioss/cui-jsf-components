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
package de.cuioss.jsf.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.application.FacesMessage.Severity;

import de.cuioss.jsf.api.application.message.MessageProducer;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.tools.base.Preconditions;
import de.cuioss.tools.collect.MoreCollections;
import de.cuioss.tools.logging.CuiLogger;
import de.cuioss.tools.string.MoreStrings;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Mock implementation of {@link MessageProducer} that will solely store the
 * messages inside. It provides access on the messages, see
 * {@link #getComponentMessages()} and {@link #getComponentMessages()} and an
 * assert: {@link #assertSingleGlobalMessageWithKeyPresent(String)}. The keys
 * will not be resolved and can therefore be queried directly. It can be easily
 * configured as bean by using {@link JsfTestConfiguration}
 *
 * @author Matthias Walliczek
 */
@RequestScoped
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public class MessageProducerMock implements MessageProducer {

    private static final CuiLogger log = new CuiLogger(MessageProducerMock.class);

    @Serial
    private static final long serialVersionUID = -7244733672736029893L;

    @Getter
    private final List<FacesMessage> globalMessages = new ArrayList<>();

    @Getter
    private final List<FacesMessage> componentMessages = new ArrayList<>();

    /**
     * @param expectedKey to be checked against
     */
    public void assertSingleGlobalMessageWithKeyPresent(final String expectedKey) {
        var messagesAsString = createStringViewMessage(globalMessages);
        assertEquals(1, globalMessages.size(), "Expected one message, but was " + messagesAsString);
        assertEquals(expectedKey, globalMessages.get(0).getSummary());
    }

    /**
     * Asserts, that the given global message is present at least once.
     *
     * @param severity   of the message. required!
     * @param messageKey of the message. required!
     */
    public void assertGlobalMessageWithKeyPresent(final FacesMessage.Severity severity, final String messageKey) {
        Preconditions.checkArgument(null != severity, "severity required");
        Preconditions.checkArgument(null != messageKey, "messageKey required");
        assertFalse(getGlobalMessagesForKey(severity, messageKey).isEmpty(),
                "No global message found with severity=" + severity + " and messageKey=" + messageKey
                        + ". Available messages:\n" + createStringViewMessage(globalMessages));
    }

    /**
     * Asserts, that the given component message is present at least once.
     *
     * @param severity   of the message. required!
     * @param messageKey of the message. required!
     */
    public void assertComponentMessageWithKeyPresent(final FacesMessage.Severity severity, final String messageKey) {
        Preconditions.checkArgument(null != severity, "severity required");
        Preconditions.checkArgument(null != messageKey, "messageKey required");
        assertFalse(getComponentMessagesForKey(severity, messageKey).isEmpty(),
                "No component message found with severity=" + severity + " and messageKey=" + messageKey
                        + ". Available messages:\n" + createStringViewMessage(componentMessages));
    }

    /**
     * @param severity   of the message. required!
     * @param messageKey of the message. required!
     *
     * @return all global messages found with the given severity and message key
     */
    public List<FacesMessage> getGlobalMessagesForKey(final FacesMessage.Severity severity, final String messageKey) {
        return globalMessages.stream().filter(facesMessage -> severity.equals(facesMessage.getSeverity()))
                .filter(facesMessage -> messageKey.equals(facesMessage.getSummary())).toList();
    }

    /**
     * @param severity   of the message. required!
     * @param messageKey of the message. required!
     *
     * @return all component messages found with the given severity and message key
     */
    public List<FacesMessage> getComponentMessagesForKey(final FacesMessage.Severity severity,
            final String messageKey) {
        return componentMessages.stream().filter(facesMessage -> severity.equals(facesMessage.getSeverity()))
                .filter(facesMessage -> messageKey.equals(facesMessage.getSummary())).toList();
    }

    /**
     * @param partialText to be checked against
     */
    public void assertMessagePresentContaining(final String partialText) {
        assertNotEquals(0, globalMessages.size(), "No Message present");

        for (FacesMessage message : globalMessages) {
            if (message.getSummary().contains(partialText)) {
                log.debug("Message '{}' found, returning", message);
                return;
            }
        }
        fail("No message found containing '" + partialText + "' in messages:"
                + createStringViewMessage(globalMessages));
    }

    /**
     * Clears all contained messages.
     */
    public void clear() {
        globalMessages.clear();
        componentMessages.clear();
    }

    /**
     * Assert that no message was added.
     */
    public void assertThatNoMessageIsPresent() {
        var messagesAsString = createStringViewMessage(globalMessages);
        assertEquals(0, globalMessages.size(), "Expected no message present but found " + messagesAsString);
    }

    private String createStringViewMessage(List<FacesMessage> facesMessages) {
        if (MoreCollections.isEmpty(facesMessages)) {
            return "no Message found";
        }
        var builder = new StringBuilder();

        var i = 1;
        for (FacesMessage facesMessage : facesMessages) {
            String messageBuilder = String.valueOf(i) + ':' + facesMessage.getSeverity() + ':' +
                facesMessage.getSummary();
            i++;
            builder.append('\n');
            builder.append(messageBuilder);
        }
        return builder.toString();
    }

    @Override
    public void setFacesMessage(final String messageKey, final FacesMessage.Severity severity, final String componentId,
            final Object... parameter) {
        if (MoreStrings.isEmpty(componentId)) {
            globalMessages.add(getMessageFor(messageKey, severity, parameter));
        } else {
            componentMessages.add(getMessageFor(messageKey, severity, parameter));
        }
    }

    @Override
    public void addMessage(final String message, final Severity severity, final String componentId,
            final Object... parameter) {
        if (MoreStrings.isEmpty(componentId)) {
            globalMessages.add(getMessageFor(message, severity, parameter));
        } else {
            componentMessages.add(getMessageFor(message, severity, parameter));
        }
    }

    @Override
    public FacesMessage getMessageFor(final String messageKey, final Severity severity, final Object... parameter) {
        return new FacesMessage(severity, messageKey, messageKey);
    }

}
