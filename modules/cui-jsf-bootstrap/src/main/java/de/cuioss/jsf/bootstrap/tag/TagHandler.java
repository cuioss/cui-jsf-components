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
package de.cuioss.jsf.bootstrap.tag;

import de.cuioss.jsf.api.components.events.ModelPayloadEvent;
import de.cuioss.jsf.api.components.util.MethodRule;
import jakarta.faces.view.facelets.ComponentConfig;
import jakarta.faces.view.facelets.ComponentHandler;
import jakarta.faces.view.facelets.MetaRule;
import jakarta.faces.view.facelets.MetaRuleset;

/**
 * Component handler for {@link TagComponent} that manages method bindings
 * for the dispose listener functionality during view build time.
 * 
 * <p>Adds a MetaRule to process the disposeListener attribute, which expects
 * a method compatible with {@link ModelPayloadEvent}.</p>
 * 
 * <h2>Usage Example</h2>
 * <pre>
 * &lt;cui:tag contentValue="Status" 
 *         disposable="true" 
 *         disposeListener="#{bean.handleTagDisposed}" /&gt;
 * </pre>
 *
 * @author Oliver Wolff
 * @since 1.0
 */
public class TagHandler extends ComponentHandler {

    /** 
     * Name of the attribute for the dispose listener method binding.
     * Value: "disposeListener". 
     */
    public static final String DISPOSE_LISTENER_NAME = "disposeListener";

    /**
     * MetaRule that handles the binding of the dispose listener method to the component.
     * It expects a method with no return value (void) that accepts a
     * {@link ModelPayloadEvent} parameter.
     */
    private static final MetaRule DISPOSE_METHOD = new MethodRule(DISPOSE_LISTENER_NAME, null,
            new Class[]{ModelPayloadEvent.class});

    /**
     * Constructor that initializes the handler with the given configuration.
     * 
     * @param config the component configuration containing information about the
     *               tag handler being created
     */
    public TagHandler(final ComponentConfig config) {
        super(config);
    }

    /**
     * Extends the meta ruleset for the component by adding the dispose method rule.
     * This enables the processing of the disposeListener attribute in the view declaration.
     *
     * @param type the component class type
     * @return the extended MetaRuleset containing the dispose method rule
     */
    @Override
    protected MetaRuleset createMetaRuleset(final Class type) {
        final var metaRuleset = super.createMetaRuleset(type);
        metaRuleset.addRule(DISPOSE_METHOD);
        return metaRuleset;
    }
}
