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

import static de.cuioss.tools.string.MoreStrings.isEmpty;

import de.cuioss.jsf.api.common.view.ViewDescriptor;
import de.cuioss.tools.collect.CollectionBuilder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.io.Serial;
import java.util.List;

/**
 * Standard implementation of {@link ViewMatcher} that checks if a view matches 
 * against a list of view path prefixes.
 * <p>
 * This implementation determines if a view matches by comparing its logical view ID
 * against a list of path prefixes. If the logical view ID starts with any of the
 * configured prefixes, the view is considered a match.
 * 
 * <p>
 * The matcher is configured with a list of string prefixes. During initialization,
 * empty or blank prefixes are filtered out, ensuring that only valid matching
 * patterns are retained.
 * 
 * <p>
 * Common use cases for this matcher include:
 * <ul>
 *   <li>Identifying views within specific application sections or modules</li>
 *   <li>Implementing security constraints for groups of related views</li>
 *   <li>Applying UI customizations to views in specific directories</li>
 * </ul>
 * 
 * <p>
 * This class is immutable and thread-safe.
 *
 * @author Oliver Wolff
 * @since 1.0
 * @see ViewMatcher
 * @see ViewDescriptor
 */
@EqualsAndHashCode
@ToString
public class ViewMatcherImpl implements ViewMatcher {

    @Serial
    private static final long serialVersionUID = -1211279289779853076L;

    /**
     * The list of path prefixes to match against.
     * This list is immutable and contains no empty or blank entries.
     */
    private final List<String> matchList;

    /**
     * Creates a new ViewMatcherImpl with the specified list of path prefixes.
     * <p>
     * The constructor filters out any empty or blank entries from the provided list,
     * creating an immutable list of valid matching prefixes.
     *
     * @param matchList A list of path prefixes to match against, must not be null
     * @throws NullPointerException if matchList is null
     */
    public ViewMatcherImpl(@NonNull List<String> matchList) {
        var builder = new CollectionBuilder<String>();
        for (String element : matchList) {
            if (!isEmpty(element) && !element.trim().isEmpty()) {
                builder.add(element);
            }
        }
        this.matchList = builder.toImmutableList();
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation checks if the logical view ID of the provided view descriptor
     * starts with any of the configured path prefixes. If any prefix matches, the method
     * returns true; otherwise, it returns false.
     *
     * @param viewDescriptor The view descriptor to match against, must not be null
     * @return true if the view's logical ID starts with any of the configured prefixes,
     *         false otherwise
     */
    @Override
    public boolean match(final ViewDescriptor viewDescriptor) {
        for (String matcher : matchList) {
            if (viewDescriptor.getLogicalViewId().startsWith(matcher)) {
                return true;
            }
        }
        return false;
    }
}
