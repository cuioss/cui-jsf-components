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
package de.cuioss.jsf.bootstrap.taglist;

import de.cuioss.jsf.bootstrap.tag.TagComponent;

import java.util.Comparator;

/**
 * Comparator for {@link TagComponent} instances based on their title values.
 * Used within {@link TagListComponent} to sort tags alphabetically.
 * 
 * <h2>Comparison Logic</h2>
 * <ul>
 *   <li>Compares non-null title values lexicographically</li>
 *   <li>Returns 0 if either title is null or titles are identical</li>
 * </ul>
 *
 * @author Matthias Walliczek
 * @since 1.0
 * @see TagComponent
 * @see TagListComponent
 */
public class TagComponentComparator implements Comparator<TagComponent> {

    /**
     * {@inheritDoc}
     * 
     * <p>
     * Compares two TagComponent instances based on their title values.
     * </p>
     * 
     * @param o1 the first TagComponent to compare
     * @param o2 the second TagComponent to compare
     * @return a negative integer if o1's title value lexicographically precedes o2's,
     *         a positive integer if o1's title value follows o2's, or 0 if they are
     *         equal or cannot be compared (due to null values)
     */
    @Override
    public int compare(TagComponent o1, TagComponent o2) {
        if (o1.getTitleValue() != null && o2.getTitleValue() != null
                && !o1.getTitleValue().equals(o2.getTitleValue())) {
            return ((String) o1.getTitleValue()).compareTo((String) o2.getTitleValue());
        }
        return 0;
    }

}
