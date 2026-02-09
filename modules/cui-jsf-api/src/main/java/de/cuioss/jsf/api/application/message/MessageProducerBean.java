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
import lombok.NonNull;
import lombok.ToString;

import java.io.Serial;
import java.text.MessageFormat;
import java.util.MissingResourceException;

/**
 * The default implementation of {@link MessageProducer} that handles resource bundle lookups
 * and message formatting for displaying JSF messages.
 * <p>
 * This implementation enhances message display by applying text processing to prevent UI
 * rendering issues. Specifically, it:
 * <ul>
 *   <li>Enforces line breaks for long messages</li>
 *   <li>Abridges extremely long messages</li>
 *   <li>Prevents word-breaking in the UI by inserting zero-width spaces at appropriate locations</li>
 * </ul>
 * 
 * <h3>Implementation Note</h3>
 * <p>
 * All message-strings will be post-processed using {@link TextSplitter} prior
 * to displaying. This will smoothen possible UI glitches on the growl display
 * element, but may be problematic regarding unit-tests that will check the
 * result using {@link String#equals(Object)}. Especially the insertion of
 * invisible spaces: '\u200B' on the elements ".,;+-!?_" may be problematic. See
 * the unit-test of this class for examples.
 * 
 * <p>
 * This class is annotated with {@link Priority} to ensure it is loaded at the appropriate
 * time within the portal's component hierarchy.
 * 
 * <p>
 * This class is not thread-safe. It is designed to be used within a single request context
 * due to its {@link RequestScoped} nature.
 *
 * @author Oliver Wolff
 * @since 1.0
 */
@RequestScoped
@Priority(PortalPriorities.PORTAL_CORE_LEVEL)
@EqualsAndHashCode(doNotUseGetters = true)
@ToString
public class MessageProducerBean implements MessageProducer {

    /**
     * The maximum size of a message before it gets abridged.
     */
    private static final int ABRIDGED_SIZE = 256;

    /**
     * The number of characters after which a line break is enforced.
     */
    private static final int FORCE_BREAK_COUNT = 35;

    /**
     * Prefix used when a message key cannot be found in the resource bundle.
     * This allows for graceful degradation when message keys are missing.
     */
    public static final String MISSING_KEY_PREFIX = "Missing key : ";

    @Serial
    private static final long serialVersionUID = 4405826619024002836L;

    /**
     * The resource bundle wrapper used to look up message templates.
     */
    private final ResourceBundleWrapper resourceBundle;

    /**
     * Provider for the FacesContext to ensure availability during request processing.
     */
    private final Provider<FacesContext> facesContextProvider;

    /**
     * Constructs a new MessageProducerBean with the specified dependencies.
     *
     * @param resourceBundle the resource bundle wrapper for message lookups, must not be null
     * @param facesContextProvider the provider for the FacesContext, must not be null
     */
    @Inject
    public MessageProducerBean(final ResourceBundleWrapper resourceBundle,
            final Provider<FacesContext> facesContextProvider) {
        this.resourceBundle = resourceBundle;
        this.facesContextProvider = facesContextProvider;
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation performs the following steps:
     * <ol>
     *   <li>Looks up the message template in the resource bundle using the provided key</li>
     *   <li>Applies any parameters using {@link MessageFormat#format(String, Object...)}</li>
     *   <li>Processes the text using {@link TextSplitter} to ensure proper line breaks</li>
     *   <li>Returns a new {@link FacesMessage} with the processed text</li>
     * </ol>
     * If the message key is not found in the resource bundle, returns a message indicating the missing key.
     * 
     * @param messageKey The key to look up in the resource bundle, must not be {@code null}
     * @param severity The severity level of the message, must not be {@code null}
     * @param parameter Optional parameters for message formatting
     * @return A new {@link FacesMessage} with the processed message text
     * @throws NullPointerException if messageKey or severity is null
     */
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

    /**
     * {@inheritDoc}
     * <p>
     * This implementation creates a {@link FacesMessage} using {@link #getMessageFor(String, FacesMessage.Severity, Object...)}
     * and adds it to the current {@link FacesContext}.
     * 
     * @param messageKey The key to look up in the resource bundle, must not be {@code null}
     * @param severity The severity level of the message, must not be {@code null}
     * @param componentId The ID of the component to associate the message with, or {@code null} for a global message
     * @param parameter Optional parameters for message formatting
     * @throws NullPointerException if messageKey or severity is null
     */
    @Override
    public void setFacesMessage(final String messageKey, final FacesMessage.Severity severity, final String componentId,
            final Object... parameter) {
        facesContextProvider.get().addMessage(componentId, getMessageFor(messageKey, severity, parameter));
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation applies the provided parameters to the message using {@link MessageFormat#format(String, Object...)},
     * processes the text using {@link TextSplitter}, and adds the resulting message to the current {@link FacesContext}.
     * 
     * @param message The message text, must not be {@code null}
     * @param severity The severity level of the message, must not be {@code null}
     * @param componentId The ID of the component to associate the message with, or {@code null} for a global message
     * @param parameter Optional parameters for message formatting
     * @throws NullPointerException if message or severity is null
     */
    @Override
    public void addMessage(@NonNull String message, FacesMessage.@NonNull Severity severity, String componentId, Object... parameter) {
        var resultingMessage = message;
        if (!MoreCollections.isEmpty(parameter)) {
            resultingMessage = MessageFormat.format(resultingMessage, parameter);
        }
        var splitter = new TextSplitter(resultingMessage, FORCE_BREAK_COUNT, ABRIDGED_SIZE);
        var cleaned = splitter.getTextWithEnforcedLineBreaks();
        facesContextProvider.get().addMessage(componentId, new FacesMessage(severity, cleaned, cleaned));
    }
}
