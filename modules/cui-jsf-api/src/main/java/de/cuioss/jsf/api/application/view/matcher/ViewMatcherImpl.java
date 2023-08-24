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

import java.util.List;

import de.cuioss.jsf.api.common.view.ViewDescriptor;
import de.cuioss.tools.collect.CollectionBuilder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * Default implementation of {@link ViewMatcher} that checks the given view
 * against a given list of partial view-paths by using
 * {@link String#startsWith(String)}. If a configured String of the given
 * matchList is empty it matches to false for that String.
 *
 * @author Oliver Wolff
 */
@EqualsAndHashCode
@ToString
public class ViewMatcherImpl implements ViewMatcher {

    private static final long serialVersionUID = -1211279289779853076L;

    /**
     * The list to be matched against
     */
    private final List<String> matchList;

    /**
     * @param matchList
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
