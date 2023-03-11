package de.cuioss.jsf.api.application.message;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.faces.application.FacesMessage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.CoreJsfTestConfiguration;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.junit5.EnableJsfEnvironment;
import de.cuioss.test.jsf.junit5.JsfEnabledTestEnvironment;

@JsfTestConfiguration(CoreJsfTestConfiguration.class)
@EnableJsfEnvironment(useIdentityResourceBundle = false)
class MessageProducerImplTest extends JsfEnabledTestEnvironment {

    private static final String MESSAGE_KEY = "some.key";

    private static final String MESSAGE_VALUE = "some.value";

    public static final String COMPONENT_ID = "componentId";

    private MessageProducer messageProducer;

    @BeforeEach
    void setUpBefore() {
        messageProducer = new MessageProducerAccessor().getValue();
    }

    @Test
    void shouldBeProvidedByAccessor() {
        assertNotNull(new MessageProducerAccessor().getValue());
    }

    @Test
    void shouldWriteSingleGlobalErrorMessage() {
        messageProducer.setGlobalErrorMessage(MESSAGE_KEY);
        assertSingleGlobaleMessagePresent(MESSAGE_VALUE, FacesMessage.SEVERITY_ERROR);
    }

    @Test
    void shouldProduceErrorMessage() {
        var message = messageProducer.getErrorMessageFor(MESSAGE_KEY);
        assertEquals(FacesMessage.SEVERITY_ERROR, message.getSeverity());
        assertEquals(MESSAGE_VALUE, message.getSummary());
    }

    @Test
    void shouldProduceInfoMessage() {
        var message = messageProducer.getInfoMessageFor(MESSAGE_KEY);
        assertEquals(FacesMessage.SEVERITY_INFO, message.getSeverity());
        assertEquals(MESSAGE_VALUE, message.getSummary());
    }

    @Test
    void shouldGracefullyHandleMissingReourceException() {
        var message = messageProducer.getInfoMessageFor("not.there");
        assertEquals(FacesMessage.SEVERITY_INFO, message.getSeverity());
        assertTrue(message.getSummary().contains("not.there"));
    }

    @Test
    void shouldWriteSingleGlobalInfoMessage() {
        messageProducer.setGlobalInfoMessage(MESSAGE_KEY);
        assertSingleGlobaleMessagePresent(MESSAGE_VALUE, FacesMessage.SEVERITY_INFO);
    }

    @Test
    void shouldWriteSingleGlobalWarnMessage() {
        messageProducer.setGlobalWarningMessage(MESSAGE_KEY);
        assertSingleGlobaleMessagePresent(MESSAGE_VALUE, FacesMessage.SEVERITY_WARN);
    }

    @Test
    void shouldWriteSingleGlobalFatalMessage() {
        messageProducer.setGlobalMessage(MESSAGE_KEY, FacesMessage.SEVERITY_FATAL);
        assertSingleGlobaleMessagePresent(MESSAGE_VALUE, FacesMessage.SEVERITY_FATAL);
    }

    @Test
    void shouldSetTextErrorMessageToComponent() {
        messageProducer.addMessage(MESSAGE_VALUE, FacesMessage.SEVERITY_ERROR, COMPONENT_ID);
        var messages = getFacesContext().getMessageList(COMPONENT_ID);
        assertEquals(1, messages.size());
        var message = messages.iterator().next();
        assertEquals(FacesMessage.SEVERITY_ERROR, message.getSeverity());
        assertEquals(MESSAGE_VALUE, message.getSummary());
    }

    @Test
    void shouldSetTextErrorMessage() {
        messageProducer.addGlobalMessage(MESSAGE_VALUE, FacesMessage.SEVERITY_ERROR);
        assertSingleGlobaleMessagePresent(MESSAGE_VALUE, FacesMessage.SEVERITY_ERROR);
    }

    @Test
    void shouldFailGlobalErrorMessageOnMissingMessage() {
        assertThrows(NullPointerException.class, () -> messageProducer.addGlobalMessage(null, FacesMessage.SEVERITY_FATAL));
    }

    @Test
    void shouldFailGlobalErrorMessageOnMissingSeverity() {
        assertThrows(NullPointerException.class, () -> messageProducer.addGlobalMessage(MESSAGE_VALUE, null));
    }

    /**
     * Assert that one and only one message should be set.
     *
     * @param messageKey
     */
    private void assertSingleGlobaleMessagePresent(final String messageKey, final FacesMessage.Severity severity) {
        var messages = getFacesContext().getMessageList();
        assertEquals(1, messages.size());
        var message = messages.iterator().next();
        assertEquals(severity, message.getSeverity());
        assertEquals(messageKey, message.getSummary());
    }

}
