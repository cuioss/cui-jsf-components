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
package de.cuioss.jsf.dev.metadata.model;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * Base abstract class for all JSF tag metadata types in the system. This class defines
 * the common properties and behavior that all tag metadata objects must implement.
 * </p>
 * 
 * <p>
 * The Tag class provides the foundation for the metadata model hierarchy, serving as the parent
 * for specific metadata types such as {@link UIComponentMetadata}, {@link ConverterMetadata},
 * {@link ValidatorMetadata}, and {@link BehaviorMetadata}.
 * </p>
 * 
 * <p>
 * Each Tag subclass represents metadata for a specific type of JSF tag, extracted from
 * taglib XML files. Tags are stored and managed by {@link TagStorage} instances within
 * the {@link de.cuioss.jsf.dev.metadata.TagLib} class.
 * </p>
 * 
 * <p>
 * The class implements {@link Comparable} to allow sorting of tags by name, which is
 * particularly useful for consistent display order in documentation and tooling.
 * </p>
 * 
 * <p>
 * Implementation note: This class uses Lombok's {@code @Data} annotation to generate
 * getters, equals, hashCode, and toString methods. Subclasses typically add additional
 * properties specific to their tag type.
 * </p>
 *
 * @author Oliver Wolff
 * @since 1.0
 */
@Data
public abstract class Tag implements Serializable, Comparable<Tag> {

    @Serial
    private static final long serialVersionUID = -6465433606569204179L;

    /**
     * <p>
     * The name of the tag as defined in the taglib XML file.
     * </p>
     * <p>
     * This corresponds to the &lt;tag-name&gt; element in the taglib XML and is used
     * to identify and reference the tag in JSF pages.
     * </p>
     */
    private final String name;

    /**
     * <p>
     * The description of the tag as defined in the taglib XML file.
     * </p>
     * <p>
     * This corresponds to the &lt;description&gt; element in the taglib XML and provides
     * human-readable documentation about the tag's purpose and usage.
     * </p>
     * <p>
     * May contain HTML markup for formatting.
     * </p>
     */
    private final String description;

    /**
     * <p>
     * The list of attributes defined for this tag in the taglib XML file.
     * </p>
     * <p>
     * This collection contains {@link AttributeMetadata} objects representing each
     * attribute defined in the taglib XML, including its name, type, description,
     * and required status.
     * </p>
     */
    private final List<AttributeMetadata> attributes;

    /**
     * <p>
     * Compares this tag to another tag based on their names.
     * </p>
     * <p>
     * This implementation provides a natural ordering for Tag objects based on
     * alphabetical ordering of tag names. It enables collections of tags to be
     * sorted in a consistent and intuitive way.
     * </p>
     * <p>
     * The comparison is case-sensitive and delegates to {@link String#compareTo(String)}.
     * </p>
     *
     * @param o The Tag to compare this Tag against
     * @return A negative integer, zero, or a positive integer if this tag's name
     *         is lexicographically less than, equal to, or greater than the name of
     *         the tag being compared
     */
    @Override
    public int compareTo(final Tag o) {
        return getName().compareTo(o.getName());
    }

}
