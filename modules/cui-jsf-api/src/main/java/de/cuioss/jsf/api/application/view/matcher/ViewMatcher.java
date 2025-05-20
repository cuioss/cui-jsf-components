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
package de.cuioss.jsf.api.application.view.matcher;

import de.cuioss.jsf.api.common.view.ViewDescriptor;

import java.io.Serializable;

/**
 * Strategy interface for matching JSF views against application-defined criteria.
 * <p>
 * View matchers are used to determine if a given view (represented by a {@link ViewDescriptor})
 * matches specific criteria, which are typically configured at the application level.
 * This pattern is useful for cases such as:
 * <ul>
 *   <li>Determining if a view belongs to a specific application section</li>
 *   <li>Filtering views based on URL patterns</li>
 *   <li>Applying security constraints or UI customizations to groups of views</li>
 *   <li>Implementing navigation rules based on view patterns</li>
 * </ul>
 * 
 * <p>
 * Implementations should be thread-safe if they are to be shared between multiple threads.
 *
 * @author Oliver Wolff
 * @since 1.0
 * @see ViewDescriptor
 * @see ViewMatcherImpl
 * @see EmptyViewMatcher
 * @see OutcomeBasedViewMatcher
 */
public interface ViewMatcher extends Serializable {

    /**
     * Determines whether the provided view matches the criteria defined by this matcher.
     * <p>
     * The specific matching logic depends on the implementation. Common strategies include
     * matching by view ID patterns, URL parameters, navigation outcomes, or any combination
     * of these attributes.
     *
     * @param viewDescriptor The view descriptor to be evaluated, must not be null
     * @return {@code true} if the view matches the defined criteria, {@code false} otherwise
     * @throws NullPointerException if viewDescriptor is null
     */
    boolean match(ViewDescriptor viewDescriptor);
}
