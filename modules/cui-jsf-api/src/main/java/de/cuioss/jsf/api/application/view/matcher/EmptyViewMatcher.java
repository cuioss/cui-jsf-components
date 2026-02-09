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

import de.cuioss.jsf.api.common.view.ViewDescriptor;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.Serial;

/**
 * A minimal implementation of {@link ViewMatcher} that always returns the same result,
 * regardless of the view being evaluated.
 * <p>
 * This class serves as a constant {@link ViewMatcher} that will either always match
 * or always reject any view, depending on how it is configured. It's useful in
 * situations where:
 * <ul>
 *   <li>A default behavior is needed when no specific matcher is available</li>
 *   <li>A placeholder matcher is needed in test scenarios</li>
 *   <li>Logic requires a matcher that explicitly matches nothing or everything</li>
 * </ul>
 * 
 * <p>
 * This class is immutable and thread-safe.
 *
 * @author Oliver Wolff
 * @since 1.0
 * @see ViewMatcher
 * @see ViewMatcherImpl
 */
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class EmptyViewMatcher implements ViewMatcher {

    @Serial
    private static final long serialVersionUID = -3249573462984449100L;

    /**
     * The constant result that this matcher will always return.
     * If {@code true}, this matcher will match all views;
     * if {@code false}, it will match no views.
     */
    private final boolean match;

    /**
     * {@inheritDoc}
     * <p>
     * This implementation always returns the configured constant result,
     * ignoring the provided view descriptor entirely.
     *
     * @param descriptor The view descriptor to be evaluated (ignored in this implementation)
     * @return The constant result configured for this matcher, regardless of the input
     */
    @Override
    public boolean match(final ViewDescriptor descriptor) {
        return match;
    }
}
