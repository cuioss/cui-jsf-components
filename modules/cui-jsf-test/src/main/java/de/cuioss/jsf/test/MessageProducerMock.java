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
package de.cuioss.jsf.test;

import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.jsf.api.application.message.MessageProducer;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.tools.base.Preconditions;
import de.cuioss.tools.collect.MoreCollections;
import de.cuioss.tools.logging.CuiLogger;
import de.cuioss.tools.string.MoreStrings;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.application.FacesMessage.Severity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

/**
 * Mock implementation of {@link MessageProducer} for testing JSF message handling.
 * <p>
 * This class captures messages created during a test rather than displaying them in the UI,
 * allowing verification of message creation behavior. Messages are stored in separate lists
 * for global and component-specific messages and can be accessed for assertions.
 * </p>
 * <p>
 * The mock preserves the original message keys without performing resource bundle resolution,
 * making it easy to verify that specific message keys were used. It provides convenience
 * methods for common assertion patterns.
 * </p>
 * <p>
 * Usage example:
 * <pre>
 * {@code
 * @EnableJSFCDIEnvironment
 * @EnableResourceBundleSupport
 * class MessageTest {
 *     
 *     @Inject
 *     private MessageProducerMock messageProducer;
 *     
 *     @Inject
 *     private ComponentUnderTest component;
 *     
 *     @Test
 *     void shouldDisplayErrorOnInvalidInput() {
 *         // Given
 *         component.setValue("invalid");
 *         
 *         // When
 *         component.validate();
 *         
 *         // Then
 *         messageProducer.assertGlobalMessageWithKeyPresent(
 *             FacesMessage.SEVERITY_ERROR, 
 *             "validation.error.key");
 *     }
 * }
 * }
 * </pre>
 * <p>
 * This class is thread-safe within the scope of a single request, as defined by
 * its {@link RequestScoped} context.
 * </p>
 *
 * @author Matthias Walliczek
 * @since 1.0
 */
@RequestScoped
@EqualsAndHashCode(callSuper = false)
@ToString
@SuppressWarnings("java:S5960") // Assertions are intended to be used in test code. This is test code.
public class MessageProducerMock implements MessageProducer {

    private static final CuiLogger LOGGER = new CuiLogger(MessageProducerMock.class);

    @Serial
    private static final long serialVersionUID = -7244733672736029893L;

    /**
     * Collection of all global messages created during the test.
     * <p>
     * Global messages are those created without a specific component ID.
     * </p>
     */
    @Getter
    private final List<FacesMessage> globalMessages = new ArrayList<>();

    /**
     * Collection of all component-specific messages created during the test.
     * <p>
     * Component messages are those created with a specific component ID.
     * </p>
     */
    @Getter
    private final List<FacesMessage> componentMessages = new ArrayList<>();

    /**
     * Verifies that exactly one global message exists with the expected key.
     * <p>
     * This method will trigger a JUnit assertion failure if there is not exactly
     * one global message with the given key.
     * </p>
     *
     * @param expectedKey the message key to check for, must not be null
     * @throws AssertionError if the assertion fails
     */
    public void assertSingleGlobalMessageWithKeyPresent(final String expectedKey) {
        var messagesAsString = createStringViewMessage(globalMessages);
        assertEquals(1, globalMessages.size(), "Expected one message, but was " + messagesAsString);
        assertEquals(expectedKey, globalMessages.getFirst().getSummary());
    }

    /**
     * Asserts that at least one global message exists with the given severity and key.
     * <p>
     * This method will trigger a JUnit assertion failure if no matching message is found.
     * </p>
     *
     * @param severity   the expected message severity, must not be null
     * @param messageKey the expected message key, must not be null
     * @throws AssertionError if the assertion fails
     * @throws IllegalArgumentException if any parameter is null
     */
    public void assertGlobalMessageWithKeyPresent(final FacesMessage.Severity severity, final String messageKey) {
        Preconditions.checkArgument(null != severity, "severity required");
        Preconditions.checkArgument(null != messageKey, "messageKey required");
        assertFalse(getGlobalMessagesForKey(severity, messageKey).isEmpty(),
                "No global message found with severity=" + severity + " and messageKey=" + messageKey
                        + ". Available messages:\n" + createStringViewMessage(globalMessages));
    }

    /**
     * Asserts that at least one component-specific message exists with the given severity and key.
     * <p>
     * This method will trigger a JUnit assertion failure if no matching message is found.
     * </p>
     *
     * @param severity   the expected message severity, must not be null
     * @param messageKey the expected message key, must not be null
     * @throws AssertionError if the assertion fails
     * @throws IllegalArgumentException if any parameter is null
     */
    public void assertComponentMessageWithKeyPresent(final FacesMessage.Severity severity, final String messageKey) {
        Preconditions.checkArgument(null != severity, "severity required");
        Preconditions.checkArgument(null != messageKey, "messageKey required");
        assertFalse(getComponentMessagesForKey(severity, messageKey).isEmpty(),
                "No component message found with severity=" + severity + " and messageKey=" + messageKey
                        + ". Available messages:\n" + createStringViewMessage(componentMessages));
    }

    /**
     * Retrieves all global messages with the specified severity and message key.
     * <p>
     * This method allows for more detailed inspection of messages beyond simple assertions.
     * </p>
     *
     * @param severity   the message severity to filter by, must not be null
     * @param messageKey the message key to filter by, must not be null
     * @return a list of matching {@link FacesMessage} objects, may be empty but never null
     */
    public List<FacesMessage> getGlobalMessagesForKey(final FacesMessage.Severity severity, final String messageKey) {
        return globalMessages.stream().filter(facesMessage -> severity.equals(facesMessage.getSeverity()))
                .filter(facesMessage -> messageKey.equals(facesMessage.getSummary())).toList();
    }

    /**
     * Retrieves all component-specific messages with the specified severity and message key.
     * <p>
     * This method allows for more detailed inspection of messages beyond simple assertions.
     * </p>
     *
     * @param severity   the message severity to filter by, must not be null
     * @param messageKey the message key to filter by, must not be null
     * @return a list of matching {@link FacesMessage} objects, may be empty but never null
     */
    public List<FacesMessage> getComponentMessagesForKey(final FacesMessage.Severity severity,
            final String messageKey) {
        return componentMessages.stream().filter(facesMessage -> severity.equals(facesMessage.getSeverity()))
                .filter(facesMessage -> messageKey.equals(facesMessage.getSummary())).toList();
    }

    /**
     * Asserts that at least one global message contains the specified text.
     * <p>
     * This is useful for verifying messages when the exact key is not important,
     * but the content must contain some specific text.
     * </p>
     *
     * @param partialText the text fragment to search for in messages
     * @throws AssertionError if no message containing the text is found
     */
    public void assertMessagePresentContaining(final String partialText) {
        assertNotEquals(0, globalMessages.size(), "No Message present");

        for (FacesMessage message : globalMessages) {
            if (message.getSummary().contains(partialText)) {
                LOGGER.debug("Message '%s' found, returning", message);
                return;
            }
        }
        fail("No message found containing '" + partialText + "' in messages:"
                + createStringViewMessage(globalMessages));
    }

    /**
     * Removes all stored messages from this mock.
     * <p>
     * This is useful for clearing the state between test cases or test phases.
     * </p>
     */
    public void clear() {
        globalMessages.clear();
        componentMessages.clear();
    }

    /**
     * Verifies that no messages have been added.
     * <p>
     * This method will trigger a JUnit assertion failure if any messages exist.
     * </p>
     *
     * @throws AssertionError if any messages are present
     */
    public void assertThatNoMessageIsPresent() {
        var messagesAsString = createStringViewMessage(globalMessages);
        assertEquals(0, globalMessages.size(), "Expected no message present but found " + messagesAsString);
    }

    /**
     * Creates a human-readable string representation of a list of messages.
     * <p>
     * Used internally for creating informative assertion failure messages.
     * </p>
     *
     * @param facesMessages the list of messages to format
     * @return a formatted string containing message details
     */
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

    /**
     * {@inheritDoc}
     * <p>
     * In this mock implementation, the message is stored for later verification
     * rather than being displayed.
     * </p>
     */
    @Override
    public void setFacesMessage(final String messageKey, final FacesMessage.Severity severity, final String componentId,
            final Object... parameter) {
        if (MoreStrings.isEmpty(componentId)) {
            globalMessages.add(getMessageFor(messageKey, severity, parameter));
        } else {
            componentMessages.add(getMessageFor(messageKey, severity, parameter));
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * In this mock implementation, the message is stored for later verification
     * rather than being displayed.
     * </p>
     */
    @Override
    public void addMessage(final @NonNull String message, final @NonNull Severity severity, final String componentId,
            final Object... parameter) {
        if (MoreStrings.isEmpty(componentId)) {
            globalMessages.add(getMessageFor(message, severity, parameter));
        } else {
            componentMessages.add(getMessageFor(message, severity, parameter));
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * In this mock implementation, the returned message uses the message key directly 
     * without resource bundle resolution. This makes testing easier by allowing direct
     * key comparison.
     * </p>
     */
    @Override
    public FacesMessage getMessageFor(final String messageKey, final Severity severity, final Object... parameter) {
        return new FacesMessage(severity, messageKey, messageKey);
    }

}
