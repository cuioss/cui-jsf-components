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
package de.cuioss.jsf.dev.metadata.composite.util;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * Simple helper class for displaying label-value pairs in JSF components, particularly
 * in component metadata displays. The label is used as a resource key for the ResourceBundle 'cui_msg'.
 * </p>
 * 
 * <p>
 * This class provides a standardized way to represent paired text information where:
 * </p>
 * <ul>
 *   <li>The label serves as an identifier or description of the data point</li>
 *   <li>The value represents the actual data being displayed</li>
 * </ul>
 * 
 * <p>
 * It is commonly used in the CUI JSF component framework for:
 * </p>
 * <ul>
 *   <li>Displaying component metadata properties</li>
 *   <li>Creating form field labels with their corresponding values</li>
 *   <li>Generating documentation entries for component attributes</li>
 * </ul>
 * 
 * <p>
 * Usage example:
 * </p>
 * <pre>{@code
 * // Create a label-value pair for display
 * LabelValueDisplay nameDisplay = new LabelValueDisplay("component.name.label", "Button");
 * 
 * // In a JSF component
 * <h:outputText value="#{nameDisplay.label}: #{nameDisplay.value}" />
 * 
 * // With resource bundle lookup in a custom component
 * <ui:param name="resolvedLabel" value="#{i18n[nameDisplay.label]}" />
 * <span>#{resolvedLabel}: #{nameDisplay.value}</span>
 * }</pre>
 * 
 * <p>
 * <em>Implementation Note:</em> This class is implemented using Lombok annotations for
 * automatic generation of constructors, accessors, and common methods:
 * </p>
 * <ul>
 *   <li>{@code @ToString} - Generates a toString() method</li>
 *   <li>{@code @EqualsAndHashCode} - Generates equals() and hashCode() methods</li>
 *   <li>{@code @RequiredArgsConstructor} - Generates a constructor with required fields</li>
 *   <li>{@code @Getter} - Generates getter methods for fields</li>
 * </ul>
 * 
 * <p>
 * <em>Serialization:</em> This class implements {@link Serializable} to support
 * JSF state saving mechanisms when used in view components.
 * </p>
 *
 * @author Oliver Wolff
 * @since 1.0
 */
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class LabelValueDisplay implements Serializable {

    @Serial
    private static final long serialVersionUID = -539225580197700564L;

    /**
     * <p>
     * The label part of this label-value pair.
     * </p>
     * 
     * <p>
     * The label is typically used as a resource key for message lookups in the
     * ResourceBundle 'cui_msg'. It identifies the type or purpose of the associated value.
     * </p>
     * 
     * <p>
     * This field is final and initialized through the constructor to ensure immutability.
     * </p>
     */
    @Getter
    private final String label;

    /**
     * <p>
     * The value part of this label-value pair.
     * </p>
     * 
     * <p>
     * The value represents the actual data being displayed, such as a component property,
     * attribute value, or configuration setting.
     * </p>
     * 
     * <p>
     * This field is final and initialized through the constructor to ensure immutability.
     * </p>
     */
    @Getter
    private final String value;
}
