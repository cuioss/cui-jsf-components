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
 * Specialized button component that functions as a "close" button with proper accessibility attributes.
 * Extends {@link BaseCuiCommandButton} with preconfigured ARIA labels.
 * 
 * <h3>Usage</h3>
 * <pre>
 * &lt;boot:closeCommandButton id="closeBtn" action="#{bean.close}" /&gt;
 * &lt;boot:closeCommandButton id="customCloseBtn" action="#{bean.closeAndSave}" styleClass="my-close-button" /&gt;
 * </pre>
 *
 * @author Oliver Wolff
 * @since 1.0
 * @see CloseCommandButtonRenderer
 */
@FacesComponent(BootstrapFamily.CLOSE_COMMAND_BUTTON_COMPONENT)
@SuppressWarnings("squid:MaximumInheritanceDepth") // Artifact of Jsf-structure
public class CloseCommandButton extends BaseCuiCommandButton {

    /**
     * Default constructor that initializes the renderer type to
     * {@link BootstrapFamily#CLOSE_COMMAND_BUTTON_RENDERER} and sets
     * appropriate accessibility attributes.
     */
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
     * Factory method to create a new instance of the component using the 
     * current faces context.
     * <p>
     * This method provides a convenient way to programmatically create
     * a close button component.
     *
     * @param facesContext the current faces context, must not be null
     * @return a new {@link CloseCommandButton} instance
     */
    public static CloseCommandButton createComponent(final FacesContext facesContext) {
        return ComponentUtility.createComponent(facesContext, BootstrapFamily.CLOSE_COMMAND_BUTTON_COMPONENT);
    }
}
