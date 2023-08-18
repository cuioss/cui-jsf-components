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
 * Defines Constants identifying standard JSF-Identifier
 *
 * @author Oliver Wolff
 *
 */
@UtilityClass
public class JsfComponentIdentifier {

    /** "javax.faces.Button" */
    public static final String BUTTON_RENDERER_TYPE = "javax.faces.Button";

    /** "javax.faces.Text" */
    public static final String TEXT_RENDERER_TYPE = "javax.faces.Text";

    /** "javax.faces.Hidden" */
    public static final String HIDDEN_RENDERER_TYPE = "javax.faces.Hidden";

    /** "javax.faces.Form" */
    public static final String FORM_RENDERER_TYPE = "javax.faces.Form";

    /** "javax.faces.Group" */
    public static final String GROUP_RENDERER_TYPE = "javax.faces.Group";

    /** "javax.faces.Link" */
    public static final String LINK_RENDERER_TYPE = "javax.faces.Link";

    /** "javax.faces.Checkbox". */
    public static final String CHECKBOX_RENDERER_TYPE = "javax.faces.Checkbox";

    /** "javax.faces.Input". */
    public static final String INPUT_FAMILY = "javax.faces.Input";
}
