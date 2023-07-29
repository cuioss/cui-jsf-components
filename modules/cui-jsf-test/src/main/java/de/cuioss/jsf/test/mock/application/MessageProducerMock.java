package de.cuioss.jsf.test.mock.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;

import de.cuioss.jsf.api.application.message.MessageProducer;
import de.cuioss.jsf.api.application.message.MessageProducerImpl;
import de.cuioss.test.jsf.config.BeanConfigurator;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.decorator.BeanConfigDecorator;
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
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public class MessageProducerMock extends MessageProducerImpl implements BeanConfigurator {

    private static final CuiLogger log = new CuiLogger(MessageProducerMock.class);

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
                .filter(facesMessage -> messageKey.equals(facesMessage.getSummary())).collect(Collectors.toList());
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
                .filter(facesMessage -> messageKey.equals(facesMessage.getSummary())).collect(Collectors.toList());
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
            var messageBuilder = new StringBuilder();
            messageBuilder.append(i).append(':').append(facesMessage.getSeverity()).append(':')
                    .append(facesMessage.getSummary());
            i++;
            builder.append('\n');
            builder.append(messageBuilder);
        }
        return builder.toString();
    }

    @Override
    public FacesMessage getMessageFor(final String messageKey, final Severity severity, final Object... parameter) {
        return new FacesMessage(severity, messageKey, messageKey);
    }

    @Override
    public void addGlobalMessage(final String messageKey, final Severity severity, final Object... parameter) {
        globalMessages.add(getMessageFor(messageKey, severity, parameter));
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
    public void configureBeans(final BeanConfigDecorator decorator) {
        decorator.register(this, MessageProducerImpl.BEAN_NAME);
    }

    @Override
    public void addMessage(final String message, final Severity severity, final String componentId,
            final Object... parameter) {
        if (MoreStrings.isEmpty(componentId)) {
            globalMessages.add(getMessageFor(message, severity, parameter));
        } else {
            componentMessages.add(getMessageFor(message, severity, parameter));
        }
        super.addMessage(message, severity, componentId, parameter);
    }
}
