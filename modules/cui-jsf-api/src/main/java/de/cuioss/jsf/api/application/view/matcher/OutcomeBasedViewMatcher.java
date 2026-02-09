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
package de.cuioss.jsf.api.application.view.matcher;

import de.cuioss.jsf.api.application.navigation.NavigationUtils;
import de.cuioss.jsf.api.common.view.ViewDescriptor;
import jakarta.faces.context.FacesContext;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.Serial;

/**
 * A {@link ViewMatcher} implementation that determines if a view matches
 * based on a navigation outcome.
 * <p>
 * This matcher resolves a navigation outcome to a logical view ID using 
 * {@link NavigationUtils#lookUpToViewDescriptorBy(FacesContext, String)},
 * and then compares this logical view ID with the one from the view being evaluated.
 * 
 * <p>
 * This is useful for scenarios where:
 * <ul>
 *   <li>You need to check if the current view corresponds to a specific navigation outcome</li>
 *   <li>You want to group views based on their navigation outcomes rather than direct view IDs</li>
 *   <li>The navigation rules in faces-config.xml define the relationships between views</li>
 * </ul>
 * 
 * <p>
 * The view descriptor for the outcome is lazily initialized on the first call to
 * {@link #match(ViewDescriptor)} and then cached for subsequent calls.
 * 
 * <p>
 * This class is not thread-safe as it contains mutable state (the cached descriptor).
 *
 * @author Oliver Wolff
 * @since 1.0
 * @see ViewMatcher
 * @see NavigationUtils
 */
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class OutcomeBasedViewMatcher implements ViewMatcher {

    @Serial
    private static final long serialVersionUID = -8359315030575513107L;

    /**
     * The navigation outcome used to resolve the target view descriptor.
     * This cannot be null.
     */
    @NonNull
    private final String outcome;

    /**
     * Cached view descriptor resolved from the outcome.
     * This is lazily initialized when first needed.
     */
    private ViewDescriptor descriptor;

    /**
     * {@inheritDoc}
     * <p>
     * This implementation compares the logical view ID of the provided view descriptor
     * with the logical view ID resolved from this matcher's navigation outcome.
     *
     * @param viewDescriptor The view descriptor to match against
     * @return {@code true} if the logical view IDs match, {@code false} otherwise
     */
    @Override
    public boolean match(ViewDescriptor viewDescriptor) {
        return getDescriptor().getLogicalViewId().equals(viewDescriptor.getLogicalViewId());
    }

    /**
     * Lazily initializes and returns the view descriptor for this matcher's outcome.
     * <p>
     * This method uses {@link NavigationUtils} to resolve the outcome to a view descriptor,
     * caching the result for future calls.
     *
     * @return The view descriptor corresponding to this matcher's outcome
     */
    private ViewDescriptor getDescriptor() {
        if (null == descriptor) {
            descriptor = NavigationUtils.lookUpToViewDescriptorBy(FacesContext.getCurrentInstance(), outcome);
        }
        return descriptor;
    }
}
