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

import de.cuioss.portal.common.bundle.ResourceBundleWrapper;
import de.cuioss.portal.common.priority.PortalPriorities;
import de.cuioss.tools.collect.MoreCollections;
import de.cuioss.tools.string.TextSplitter;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Provider;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serial;
import java.text.MessageFormat;
import java.util.MissingResourceException;

/**
 * Portal version of MessageProducer.
 * <h3>Implementation Note</h3>
 * <p>
 * All message-strings will be post-processed using {@link TextSplitter} prior
 * to displaying. This will smoothen possible ui glitches on the growl display
 * element, but may be problematic regarding unit-tests that will check the
 * result using {@link String#equals(Object)}. Especially the insertion of
 * invisible spaces: '\u200B' on the elements ".,;+-!?_" may be problematic. See
 * the unit-test of this class
 * </p>
 *
 * @author Oliver Wolff
 */
@RequestScoped
@Priority(PortalPriorities.PORTAL_CORE_LEVEL)
@EqualsAndHashCode(doNotUseGetters = true)
@ToString
public class MessageProducerBean implements MessageProducer {

    private static final int ABRIDGED_SIZE = 256;

    private static final int FORCE_BREAK_COUNT = 35;

    /**
     * Used for gracefully react on not existing message-keys.
     */
    public static final String MISSING_KEY_PREFIX = "Missing key : ";

    @Serial
    private static final long serialVersionUID = 4405826619024002836L;

    @Inject
    private ResourceBundleWrapper resourceBundle;

    @Inject
    private Provider<FacesContext> facesContextProvider;

    @Override
    public FacesMessage getMessageFor(final String messageKey, final FacesMessage.Severity severity, final Object... parameter) {
        try {
            var resultingMessage = resourceBundle.getString(messageKey);
            if (parameter.length > 0) {
                resultingMessage = MessageFormat.format(resultingMessage, parameter);
            }
            var splitter = new TextSplitter(resultingMessage, FORCE_BREAK_COUNT, ABRIDGED_SIZE);
            var cleaned = splitter.getTextWithEnforcedLineBreaks();
            return new FacesMessage(severity, cleaned, cleaned);
        } catch (final MissingResourceException e) {
            final var missingKey = MISSING_KEY_PREFIX + messageKey;
            return new FacesMessage(severity, missingKey, missingKey);
        }
    }

    @Override
    public void setFacesMessage(final String messageKey, final FacesMessage.Severity severity, final String componentId,
            final Object... parameter) {
        facesContextProvider.get().addMessage(componentId, getMessageFor(messageKey, severity, parameter));
    }

    @Override
    public void addMessage(String message, FacesMessage.Severity severity, String componentId, Object... parameter) {
        var resultingMessage = message;
        if (!MoreCollections.isEmpty(parameter)) {
            resultingMessage = MessageFormat.format(resultingMessage, parameter);
        }
        var splitter = new TextSplitter(resultingMessage, FORCE_BREAK_COUNT, ABRIDGED_SIZE);
        var cleaned = splitter.getTextWithEnforcedLineBreaks();
        facesContextProvider.get().addMessage(componentId, new FacesMessage(severity, cleaned, cleaned));
    }
}
