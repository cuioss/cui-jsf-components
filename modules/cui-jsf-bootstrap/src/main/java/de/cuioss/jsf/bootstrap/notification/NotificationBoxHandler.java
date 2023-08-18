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
package de.cuioss.jsf.bootstrap.notification;

import javax.faces.view.facelets.ComponentConfig;
import javax.faces.view.facelets.ComponentHandler;
import javax.faces.view.facelets.MetaRule;
import javax.faces.view.facelets.MetaRuleset;

import de.cuioss.jsf.api.components.events.ModelPayloadEvent;
import de.cuioss.jsf.api.components.util.MethodRule;

/**
 * This handler is responsible for adding the binding to the dismissibleListener
 *
 * @author Matthias Walliczek
 */
public class NotificationBoxHandler extends ComponentHandler {

    /** "dismissListener". */
    public static final String DISMISS_LISTENER_NAME = "dismissListener";

    private static final MetaRule DISMISS_METHOD = new MethodRule(DISMISS_LISTENER_NAME, null,
            new Class[] { ModelPayloadEvent.class });

    /**
     * @param config
     */
    public NotificationBoxHandler(final ComponentConfig config) {
        super(config);
    }

    @Override
    protected MetaRuleset createMetaRuleset(@SuppressWarnings("rawtypes") final Class type) { // Rawtype
                                                                                              // due
                                                                                              // to
                                                                                              // API
        final var metaRuleset = super.createMetaRuleset(type);

        metaRuleset.addRule(DISMISS_METHOD);

        return metaRuleset;
    }

}
