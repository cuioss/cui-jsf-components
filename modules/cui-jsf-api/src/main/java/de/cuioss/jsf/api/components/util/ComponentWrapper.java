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

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorHolder;

import de.cuioss.tools.string.Joiner;
import de.cuioss.tools.string.MoreStrings;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * <p>
 * Wraps a {@link UIComponent} at runtime in order to provide some convenience
 * methods for rendering / decoding.
 * </p>
 * <p>
 * <em>Caution:</em> This wrapper essentially caches some calls that are quite
 * expensive like {@link UIComponent#getClientId()}. Therefore it must
 * <em>NEVER</em> be reused and is not reentrant. It must always be instantiated
 * newly.
 * </p>
 *
 * @author Oliver Wolff
 * @param <T> Must be at least {@link UIComponent}
 */
@RequiredArgsConstructor
public class ComponentWrapper<T extends UIComponent> {

    @Getter
    private final T wrapped;

    private String clientId;

    private Boolean clientBehaviorHolder;

    private Boolean uiInput;

    private Map<String, List<ClientBehavior>> clientBehaviors;

    /**
     * @return the clientId for the wrapped component.
     */
    public String getClientId() {
        if (null == clientId) {
            clientId = wrapped.getClientId();
        }
        return clientId;
    }

    /**
     * @return boolean indicating whether the wrapped component is a
     *         {@link ClientBehaviorHolder}
     */
    public boolean isClientBehaviorHolder() {
        if (null == clientBehaviorHolder) {
            clientBehaviorHolder = getWrapped() instanceof ClientBehaviorHolder;
        }
        return clientBehaviorHolder;
    }

    /**
     * @return boolean indicating whether the wrapped component is a {@link UIInput}
     */
    public boolean isUIInput() {
        if (null == uiInput) {
            uiInput = getWrapped() instanceof UIInput;
        }
        return uiInput;
    }

    /**
     * @return the map of {@link ClientBehavior} associated with this component. In
     *         case the component is not {@link ClientBehaviorHolder} it returns an
     *         empty Map but never null.
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
     * @return boolean indicating whether to render a client id. <br>
     *         Returns true if:
     *         <ul>
     *         <li>An id is set that is not generated: must not start with
     *         {@link UIViewRoot#UNIQUE_ID_PREFIX}</li>
     *         <li>If the component in hand provides at least one
     *         {@link ClientBehavior}, assuming that the {@link ClientBehavior}
     *         needs a clientId.</li>
     *         </ul>
     *         otherwise it will return false
     */
    public boolean shouldRenderClientId() {
        final var componentId = getWrapped().getId();
        if (!MoreStrings.isEmpty(componentId) && !componentId.startsWith(UIViewRoot.UNIQUE_ID_PREFIX)) {
            return true;
        }
        return !getClientBehaviors().isEmpty();
    }

    /**
     * @param idExtension if not null or empty it will be appended to the derived
     *                    ClientId. In addition there will be an underscore
     *                    appended: The result will be component.getClientId() + "_"
     *                    + idExtension
     * @return the created id
     */
    public String getSuffixedClientId(final String idExtension) {
        if (MoreStrings.isEmpty(idExtension)) {
            return getClientId();
        }
        return Joiner.on('_').join(getClientId(), idExtension);
    }

}
