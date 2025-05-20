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

import lombok.experimental.UtilityClass;

/**
 * Defines constants identifying standard JSF component and renderer types used throughout the 
 * JSF framework and component libraries.
 * <p>
 * These constants represent the standard renderer types and component families as defined
 * by the JSF specification. They provide a consistent way to reference these identifiers
 * across the application and avoid hardcoding string literals in component implementations.
 * </p>
 * <p>
 * This class is thread-safe as it contains only immutable static final constants.
 * </p>
 * 
 * @author Oliver Wolff
 * @since 1.0
 * @see jakarta.faces.render.Renderer
 * @see jakarta.faces.component.UIComponent
 */
@UtilityClass
public class JsfComponentIdentifier {

    /**
     * The standard renderer type for button components.
     * <p>
     * Value: "jakarta.faces.Button"
     * </p>
     * 
     * @see jakarta.faces.component.html.HtmlCommandButton
     */
    public static final String BUTTON_RENDERER_TYPE = "jakarta.faces.Button";

    /**
     * The standard renderer type for text output components.
     * <p>
     * Value: "jakarta.faces.Text"
     * </p>
     * 
     * @see jakarta.faces.component.html.HtmlOutputText
     */
    public static final String TEXT_RENDERER_TYPE = "jakarta.faces.Text";

    /**
     * The standard renderer type for hidden input components.
     * <p>
     * Value: "jakarta.faces.Hidden"
     * </p>
     * 
     * @see jakarta.faces.component.html.HtmlInputHidden
     */
    public static final String HIDDEN_RENDERER_TYPE = "jakarta.faces.Hidden";

    /**
     * The standard renderer type for form components.
     * <p>
     * Value: "jakarta.faces.Form"
     * </p>
     * 
     * @see jakarta.faces.component.html.HtmlForm
     */
    public static final String FORM_RENDERER_TYPE = "jakarta.faces.Form";

    /**
     * The standard renderer type for group components.
     * <p>
     * Value: "jakarta.faces.Group"
     * </p>
     * 
     * @see jakarta.faces.component.html.HtmlPanelGroup
     */
    public static final String GROUP_RENDERER_TYPE = "jakarta.faces.Group";

    /**
     * The standard renderer type for link components.
     * <p>
     * Value: "jakarta.faces.Link"
     * </p>
     * 
     * @see jakarta.faces.component.html.HtmlCommandLink
     * @see jakarta.faces.component.html.HtmlOutputLink
     */
    public static final String LINK_RENDERER_TYPE = "jakarta.faces.Link";

    /**
     * The standard renderer type for checkbox components.
     * <p>
     * Value: "jakarta.faces.Checkbox"
     * </p>
     * 
     * @see jakarta.faces.component.html.HtmlSelectBooleanCheckbox
     */
    public static final String CHECKBOX_RENDERER_TYPE = "jakarta.faces.Checkbox";

    /**
     * The standard component family for input components.
     * <p>
     * Value: "jakarta.faces.Input"
     * </p>
     * 
     * @see jakarta.faces.component.UIInput
     */
    public static final String INPUT_FAMILY = "jakarta.faces.Input";
}
