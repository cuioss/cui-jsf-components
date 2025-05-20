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
package de.cuioss.jsf.api.components;

import static java.util.Objects.requireNonNull;

import jakarta.faces.component.behavior.AjaxBehavior;
import jakarta.faces.context.FacesContext;
import lombok.experimental.UtilityClass;

/**
 * Repository for standard JSF behavior components.
 * <p>
 * This utility class provides factory methods for creating JSF behavior components
 * such as {@link AjaxBehavior}. It simplifies the process of programmatically creating
 * behavior components by handling the details of component creation through the
 * JSF application.
 * </p>
 * <p>
 * Behaviors in JSF are attachable objects that provide additional functionality
 * to components, such as AJAX capabilities, client-side validation, or other
 * dynamic behaviors.
 * </p>
 * <p>
 * Usage example:
 * </p>
 * <pre>
 * FacesContext context = FacesContext.getCurrentInstance();
 * AjaxBehavior ajaxBehavior = JsfBehaviorComponent.ajaxBehavior(context);
 * ajaxBehavior.setExecute(Arrays.asList("@this"));
 * ajaxBehavior.setRender(Arrays.asList("messages"));
 * component.addClientBehavior("click", ajaxBehavior);
 * </pre>
 * <p>
 * This class is thread-safe as it is a utility class with no mutable state.
 * </p>
 *
 * @author Oliver Wolff
 * @since 1.0
 * @see jakarta.faces.component.behavior.Behavior
 * @see jakarta.faces.component.behavior.AjaxBehavior
 * @see jakarta.faces.component.behavior.ClientBehaviorHolder
 */
@UtilityClass
public class JsfBehaviorComponent {

    /**
     * Creates a new instance of an {@link AjaxBehavior}.
     * <p>
     * This method uses the FacesContext to create an AjaxBehavior instance through
     * the standard JSF application behavior factory mechanism.
     * </p>
     * <p>
     * The returned behavior can be configured further and attached to components that
     * implement {@link jakarta.faces.component.behavior.ClientBehaviorHolder}.
     * </p>
     *
     * @param context the FacesContext used for behavior creation, must not be {@code null}
     * @return a newly instantiated AjaxBehavior
     * @throws NullPointerException if context is null
     */
    public static AjaxBehavior ajaxBehavior(FacesContext context) {
        requireNonNull(context);
        return (AjaxBehavior) context.getApplication().createBehavior(AjaxBehavior.BEHAVIOR_ID);
    }
}
