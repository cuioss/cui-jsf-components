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

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.cuioss.tools.collect.MoreCollections;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.application.FacesMessage.Severity;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Provider;
import lombok.Getter;
import lombok.NonNull;

import java.io.Serial;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Matthias Walliczek
 *
 */
@RequestScoped
public class MessageProducerMock implements MessageProducer {

    /** */
    @Serial
    private static final long serialVersionUID = -7244733672736029893L;

    @Getter
    private final List<FacesMessage> globalMessages = new ArrayList<>();

    @Getter
    private final List<FacesMessage> componentMessages = new ArrayList<>();

    @Inject
    private Provider<FacesContext> facesContextProvider;

    /**
     * @param expectedKey to be checked against
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
    public void setFacesMessage(String messagekey, FacesMessage.Severity severity, String componentId,
            Object... parameter) {
        if (null == componentId || componentId.isEmpty()) {
            globalMessages.add(getMessageFor(messagekey, severity, parameter));
        } else {
            componentMessages.add(getMessageFor(messagekey, severity, parameter));
        }
    }

    @Override
    public void addMessage(@NonNull String message, @NonNull Severity severity, String componentId, Object... parameter) {
        var resultingMessage = message;
        if (!MoreCollections.isEmpty(parameter)) {
            resultingMessage = MessageFormat.format(resultingMessage, parameter);
        }
        var facesMessage = new FacesMessage(severity, resultingMessage, resultingMessage);
        if (null == componentId || componentId.isEmpty()) {
            globalMessages.add(facesMessage);
        } else {
            componentMessages.add(facesMessage);
        }
        facesContextProvider.get().addMessage(componentId, facesMessage);
    }

}
