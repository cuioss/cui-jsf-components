package de.cuioss.jsf.api.application.message;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;

import lombok.Getter;

/**
 * @author Matthias Walliczek
 *
 */
public class MessageProducerMock extends MessageProducerImpl {

    /** */
    private static final long serialVersionUID = -7244733672736029893L;

    @Getter
    private final List<FacesMessage> globalMessages = new ArrayList<>();

    @Getter
    private final List<FacesMessage> componentMessages = new ArrayList<>();

    /**
     * @param expectedKey
     *            to be checked against
     */
    public void assertSingleGlobalMessageWithKeyPresent(String expectedKey) {
        assertEquals(1, globalMessages.size());
        assertEquals(expectedKey, globalMessages.get(0).getSummary());
    }

    @Override
    public FacesMessage getMessageFor(String messageKey, Severity severity, Object... parameter) {
        return new FacesMessage(severity, messageKey, messageKey);
    }

    @Override
    public void addGlobalMessage(String message, Severity severity, Object... parameter) {
        globalMessages.add(getMessageFor(message, severity, parameter));
    }

    @Override
    public void setFacesMessage(String messagekey, FacesMessage.Severity severity, String componentId,
            Object... parameter) {
        if (null == componentId || componentId.isEmpty()) {
            globalMessages.add(getMessageFor(messagekey, severity, parameter));
        } else {
            componentMessages.add(getMessageFor(messagekey, severity, parameter));
        }
    }
}
