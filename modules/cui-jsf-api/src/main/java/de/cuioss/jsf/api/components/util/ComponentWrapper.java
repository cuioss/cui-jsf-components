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
package de.cuioss.jsf.api.components.util;

import de.cuioss.tools.string.Joiner;
import de.cuioss.tools.string.MoreStrings;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIInput;
import jakarta.faces.component.UIViewRoot;
import jakarta.faces.component.behavior.ClientBehavior;
import jakarta.faces.component.behavior.ClientBehaviorHolder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * <p>Wraps a {@link UIComponent} at runtime to provide convenience methods for rendering and decoding
 * operations, while caching expensive method calls for improved performance.</p>
 * 
 * <p>This wrapper caches frequently accessed properties and computed values that would otherwise
 * require expensive operations each time they're accessed, such as:</p>
 * <ul>
 *   <li>Component client ID ({@link UIComponent#getClientId()})</li>
 *   <li>Type checking results (whether the component is a {@link ClientBehaviorHolder} or {@link UIInput})</li>
 *   <li>Client behavior collections</li>
 * </ul>
 * 
 * <p><strong>Important Usage Note:</strong> Due to its caching behavior, this wrapper must 
 * <em>never be reused</em> across multiple operations or rendering cycles. A new instance 
 * should be created each time a component needs to be wrapped. This class is not reentrant 
 * and not thread-safe for reuse.</p>
 * 
 * <p>Usage example:</p>
 * <pre>
 * // During component rendering
 * public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
 *     ComponentWrapper&lt;UIComponent&gt; wrapper = new ComponentWrapper&lt;&gt;(component);
 *     ResponseWriter writer = context.getResponseWriter();
 *     
 *     // Use wrapper's convenience methods
 *     if (wrapper.shouldRenderClientId()) {
 *         writer.writeAttribute("id", wrapper.getClientId(), null);
 *     }
 *     
 *     // Access client behaviors if needed
 *     if (!wrapper.getClientBehaviors().isEmpty()) {
 *         // Handle behaviors
 *     }
 * }
 * </pre>
 *
 * @author Oliver Wolff
 * @param <T> The specific type of UIComponent being wrapped, must be at least {@link UIComponent}
 */
@RequiredArgsConstructor
public class ComponentWrapper<T extends UIComponent> {

    /**
     * <p>The wrapped component instance.</p>
     * <p>This is the actual JSF component that this wrapper is providing convenience methods for.</p>
     */
    @Getter
    private final T wrapped;

    /**
     * <p>Cached client ID of the wrapped component.</p>
     * <p>This field caches the result of {@link UIComponent#getClientId()} to avoid
     * repeated expensive calls.</p>
     */
    private String clientId;

    /**
     * <p>Cached result indicating whether the wrapped component implements {@link ClientBehaviorHolder}.</p>
     * <p>This field caches the type check result to avoid repeated {@code instanceof} operations.</p>
     */
    private Boolean clientBehaviorHolder;

    /**
     * <p>Cached result indicating whether the wrapped component is an instance of {@link UIInput}.</p>
     * <p>This field caches the type check result to avoid repeated {@code instanceof} operations.</p>
     */
    private Boolean uiInput;

    /**
     * <p>Cached map of client behaviors associated with the wrapped component.</p>
     * <p>This field caches the result of {@link ClientBehaviorHolder#getClientBehaviors()}
     * to avoid repeated calls.</p>
     */
    private Map<String, List<ClientBehavior>> clientBehaviors;

    /**
     * <p>Returns the client ID of the wrapped component.</p>
     * 
     * <p>This method caches the result of {@link UIComponent#getClientId()} on first call
     * to avoid the overhead of repeated calls, which can be expensive especially during
     * rendering.</p>
     *
     * @return The client ID of the wrapped component
     */
    public String getClientId() {
        if (null == clientId) {
            clientId = wrapped.getClientId();
        }
        return clientId;
    }

    /**
     * <p>Determines whether the wrapped component implements {@link ClientBehaviorHolder}.</p>
     * 
     * <p>This method caches the result of the type check to avoid repeated
     * {@code instanceof} operations.</p>
     *
     * @return {@code true} if the wrapped component implements {@link ClientBehaviorHolder},
     *         {@code false} otherwise
     */
    public boolean isClientBehaviorHolder() {
        if (null == clientBehaviorHolder) {
            clientBehaviorHolder = getWrapped() instanceof ClientBehaviorHolder;
        }
        return clientBehaviorHolder;
    }

    /**
     * <p>Determines whether the wrapped component is an instance of {@link UIInput}.</p>
     * 
     * <p>This method caches the result of the type check to avoid repeated
     * {@code instanceof} operations.</p>
     *
     * @return {@code true} if the wrapped component is an instance of {@link UIInput},
     *         {@code false} otherwise
     */
    public boolean isUIInput() {
        if (null == uiInput) {
            uiInput = getWrapped() instanceof UIInput;
        }
        return uiInput;
    }

    /**
     * <p>Returns the map of client behaviors associated with the wrapped component.</p>
     * 
     * <p>If the wrapped component implements {@link ClientBehaviorHolder}, this method
     * returns the result of {@link ClientBehaviorHolder#getClientBehaviors()}. Otherwise,
     * it returns an empty map.</p>
     * 
     * <p>The result is cached to avoid repeated calls.</p>
     *
     * @return A map of client behaviors associated with the wrapped component,
     *         never {@code null}
     */
    public Map<String, List<ClientBehavior>> getClientBehaviors() {
        if (null == clientBehaviors) {
            if (!isClientBehaviorHolder()) {
                clientBehaviors = Collections.emptyMap();
            } else {
                clientBehaviors = ((ClientBehaviorHolder) getWrapped()).getClientBehaviors();
            }
        }
        return clientBehaviors;
    }

    /**
     * <p>Determines whether the component's client ID should be rendered.</p>
     * 
     * <p>This method implements a heuristic to decide whether a component's client ID
     * should be explicitly rendered. It returns {@code true} if either:</p>
     * <ul>
     *   <li>The component has an explicitly set ID (not auto-generated by JSF)</li>
     *   <li>The component has at least one client behavior attached (which typically
     *       requires a client ID for DOM event binding)</li>
     * </ul>
     * 
     * <p>This is useful for optimizing the HTML output by omitting unnecessary
     * {@code id} attributes when they're not needed.</p>
     *
     * @return {@code true} if the component's client ID should be rendered,
     *         {@code false} otherwise
     */
    public boolean shouldRenderClientId() {
        final var componentId = getWrapped().getId();
        if (!MoreStrings.isEmpty(componentId) && !componentId.startsWith(UIViewRoot.UNIQUE_ID_PREFIX)) {
            return true;
        }
        return !getClientBehaviors().isEmpty();
    }

    /**
     * <p>Returns a client ID with an optional suffix appended.</p>
     * 
     * <p>This method is useful for creating related IDs for sub-elements of a component,
     * such as when a component needs to generate multiple HTML elements that need
     * related but distinct IDs.</p>
     * 
     * <p>If the extension is provided, it will be appended to the component's client ID
     * with an underscore separator. For example, if the component's client ID is "form:input"
     * and the extension is "container", the result will be "form:input_container".</p>
     *
     * @param idExtension An optional suffix to append to the client ID. If {@code null} or empty,
     *                   the regular client ID is returned without modification.
     * @return The client ID with the extension appended if provided
     */
    public String getSuffixedClientId(final String idExtension) {
        if (MoreStrings.isEmpty(idExtension)) {
            return getClientId();
        }
        return Joiner.on('_').join(getClientId(), idExtension);
    }

}
