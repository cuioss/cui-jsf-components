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
package de.cuioss.jsf.bootstrap.waitingindicator;

import de.cuioss.jsf.api.components.base.AbstractBaseCuiComponent;
import de.cuioss.jsf.api.components.partial.ContextSizeProvider;
import de.cuioss.jsf.api.components.util.ComponentUtility;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import jakarta.faces.component.FacesComponent;
import jakarta.faces.context.FacesContext;
import lombok.experimental.Delegate;

/**
 * Component that displays a visual waiting indicator (spinner) for loading or processing states.
 * Can be sized using Bootstrap sizing options (xs, sm, md, lg), with default size "md".
 * 
 * <h2>Usage</h2>
 * <pre>
 * &lt;boot:waitingIndicator /&gt;
 * &lt;boot:waitingIndicator size="lg" /&gt;
 * </pre>
 * 
 * @author Oliver Wolff
 * @since 1.0
 * @see WaitingIndicatorRenderer
 */
@FacesComponent(BootstrapFamily.WAITING_INDICATOR_COMPONENT)
public class WaitingIndicatorComponent extends AbstractBaseCuiComponent {

    @Delegate
    private final ContextSizeProvider contextSizeProvider;

    /**
     * Default constructor that initializes the renderer type to
     * {@link BootstrapFamily#WAITING_INDICATOR_RENDERER} and sets up the
     * {@link ContextSizeProvider} with a default size of "md".
     */
    public WaitingIndicatorComponent() {
        super.setRendererType(BootstrapFamily.WAITING_INDICATOR_RENDERER);
        contextSizeProvider = new ContextSizeProvider(this, "md");
    }

    /**
     * {@inheritDoc}
     * 
     * @return {@link BootstrapFamily#COMPONENT_FAMILY}
     */
    @Override
    public String getFamily() {
        return BootstrapFamily.COMPONENT_FAMILY;
    }

    /**
     * Factory method to create a new instance of the component using the 
     * current faces context.
     *
     * @param facesContext the current faces context, must not be null
     * @return a new {@link WaitingIndicatorComponent} instance
     */
    public static WaitingIndicatorComponent createComponent(final FacesContext facesContext) {
        return ComponentUtility.createComponent(facesContext, BootstrapFamily.WAITING_INDICATOR_COMPONENT);
    }
}
