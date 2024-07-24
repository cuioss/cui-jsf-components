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
package de.cuioss.jsf.bootstrap.button;

import de.cuioss.jsf.api.components.base.BaseCuiCommandButton;
import de.cuioss.jsf.api.components.html.AttributeName;
import de.cuioss.jsf.api.components.html.AttributeValue;
import de.cuioss.jsf.api.components.util.ComponentUtility;
import de.cuioss.jsf.bootstrap.BootstrapFamily;

import jakarta.faces.component.FacesComponent;
import jakarta.faces.component.html.HtmlCommandButton;
import jakarta.faces.context.FacesContext;

/**
 * Variant of {@link HtmlCommandButton} that can be directly used as a 'Close'
 * button within a form context.
 *
 * @author Oliver Wolff
 */
@FacesComponent(BootstrapFamily.CLOSE_COMMAND_BUTTON_COMPONENT)
@SuppressWarnings("squid:MaximumInheritanceDepth") // Artifact of Jsf-structure
public class CloseCommandButton extends BaseCuiCommandButton {

    public CloseCommandButton() {
        super.setRendererType(BootstrapFamily.CLOSE_COMMAND_BUTTON_RENDERER);
        getPassThroughAttributes(true).put(AttributeName.ARIA_LABEL.getContent(),
            AttributeValue.ARIA_CLOSE.getContent());
    }

    @Override
    public String getFamily() {
        return BootstrapFamily.COMPONENT_FAMILY;
    }

    /**
     * Shortcut for creating and casting a component of type
     * {@link CloseCommandButton}.
     *
     * @param facesContext to be set
     * @return a newly created {@link CloseCommandButton}
     */
    public static CloseCommandButton createComponent(final FacesContext facesContext) {
        return ComponentUtility.createComponent(facesContext, BootstrapFamily.CLOSE_COMMAND_BUTTON_COMPONENT);
    }
}
