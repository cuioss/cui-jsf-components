/*
 * Copyright © 2025 CUI-OpenSource-Software (info@cuioss.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
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
 * Client behavior that handles disposal of {@link TagComponent}s when triggered.
 * Generates JavaScript to call cui_tag_dispose() function, marking the tag as
 * disposed via a hidden input field.
 *
 * <p>This behavior is automatically added to the close button of disposable tags
 * by the {@link TagComponent#accessCloseButton()} method.</p>
 *
 *
 * @author Oliver Wolff
 * @since 1.0
 * @see TagComponent
 */
public class DisposeBehavior extends ClientBehaviorBase {

    /**
     * The JavaScript template to be used when the dispose action is triggered.
     * The format parameter will be replaced with the client ID of the hidden field
     * that tracks the disposed state.
     * <p>
     * Format: cui_tag_dispose('%s')
     * </p>
     */
    public static final String ON_CLICK_TEMPLATE = "cui_tag_dispose('%s')";

    /**
     * Generates the JavaScript code needed to trigger the tag disposal function.
     * The generated script calls the cui_tag_dispose() function with the client ID
     * of the hidden input field that will be set to indicate the tag's disposed state.
     *
     * @param behaviorContext the context containing information about the component
     *                        and event that triggered this behavior
     * @return a JavaScript snippet that triggers the tag disposal function
     */
    @Override
    public String getScript(final ClientBehaviorContext behaviorContext) {
        final var parent = behaviorContext.getComponent().getParent();
        final var id = parent.getId();
        final var clientId = parent.getClientId();
        return ON_CLICK_TEMPLATE.formatted(clientId.replace(id, TagComponent.DISPOSE_INFO_SUFFIX));
    }
}
