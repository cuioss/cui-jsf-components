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
 * This handler is responsible for adding the binding to the disposeListener
 *
 * @author Oliver Wolff
 */
public class TagHandler extends ComponentHandler {

    /** "disposeListener". */
    public static final String DISPOSE_LISTENER_NAME = "disposeListener";

    private static final MetaRule DISPOSE_METHOD = new MethodRule(DISPOSE_LISTENER_NAME, null,
            new Class[]{ModelPayloadEvent.class});

    /**
     * @param config
     */
    public TagHandler(final ComponentConfig config) {
        super(config);
    }

    @Override
    protected MetaRuleset createMetaRuleset(final Class type) {
        final var metaRuleset = super.createMetaRuleset(type);

        metaRuleset.addRule(DISPOSE_METHOD);

        return metaRuleset;
    }

}
