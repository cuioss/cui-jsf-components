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

import jakarta.faces.component.behavior.ClientBehaviorBase;
import jakarta.faces.component.behavior.ClientBehaviorContext;

/**
 * @author Oliver Wolff
 */
public class DisposeBehavior extends ClientBehaviorBase {

    /**
     * The javaScript to be called if dispose is clicked: cui_tag_dispose('%s').
     */
    public static final String ON_CLICK_TEMPLATE = "cui_tag_dispose('%s')";

    @Override
    public String getScript(final ClientBehaviorContext behaviorContext) {
        final var parent = behaviorContext.getComponent().getParent();
        final var id = parent.getId();
        final var clientId = parent.getClientId();
        return ON_CLICK_TEMPLATE.formatted(clientId.replace(id, TagComponent.DISPOSE_INFO_SUFFIX));
    }

}
