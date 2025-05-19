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
package de.cuioss.jsf.dev.metadata.model;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * Represents metadata for a single attribute of a JSF tag. This class encapsulates
 * all the information about an attribute as defined in a taglib XML file.
 * </p>
 * 
 * <p>
 * AttributeMetadata objects are typically contained within a {@link Tag} instance's
 * attributes collection. Each instance provides information about the attribute's
 * name, type, description, and whether it's required.
 * </p>
 * 
 * <p>
 * The information in this class corresponds to the &lt;attribute&gt; elements in
 * a taglib XML file, capturing details like:
 * </p>
 * <pre>
 * &lt;attribute&gt;
 *   &lt;name&gt;styleClass&lt;/name&gt;
 *   &lt;type&gt;java.lang.String&lt;/type&gt;
 *   &lt;description&gt;CSS style class to apply&lt;/description&gt;
 *   &lt;required&gt;false&lt;/required&gt;
 * &lt;/attribute&gt;
 * </pre>
 * 
 * <p>
 * Sample usage:
 * </p>
 * <pre>
 * // Create an attribute metadata object
 * AttributeMetadata attribute = new AttributeMetadata(
 *     "styleClass",
 *     "java.lang.String",
 *     "CSS style class to apply",
 *     false);
 *     
 * // Check if the attribute is required
 * if (Boolean.TRUE.equals(attribute.getRequired())) {
 *     // Handle required attribute
 * }
 * </pre>
 * 
 * <p>
 * This class is immutable after construction and therefore thread-safe.
 * It uses Lombok's {@code @Data} annotation to generate getters, equals,
 * hashCode, and toString methods.
 * </p>
 *
 * @author Oliver Wolff
 * @since 1.0
 */
@Data
public class AttributeMetadata implements Serializable {

    @Serial
    private static final long serialVersionUID = 7576492765093982094L;

    /**
     * <p>
     * The name of the attribute as defined in the taglib XML file.
     * </p>
     * <p>
     * This corresponds to the &lt;name&gt; element in the attribute definition and
     * represents the attribute name as it would be used in a JSF page, for example
     * {@code styleClass} in {@code <ui:component styleClass="myClass" />}.
     * </p>
     */
    private final String name;
    
    /**
     * <p>
     * The Java type of the attribute as defined in the taglib XML file.
     * </p>
     * <p>
     * This corresponds to the &lt;type&gt; element in the attribute definition, or
     * if it's a method expression, to the &lt;method-signature&gt; element. 
     * Examples include {@code java.lang.String}, {@code java.lang.Boolean}, or 
     * method signatures like {@code void method(javax.faces.event.ActionEvent)}.
     * </p>
     */
    private final String type;
    
    /**
     * <p>
     * The description of the attribute as defined in the taglib XML file.
     * </p>
     * <p>
     * This corresponds to the &lt;description&gt; element in the attribute definition
     * and provides human-readable documentation about the attribute's purpose,
     * expected values, and usage.
     * </p>
     * <p>
     * May contain HTML markup for formatting.
     * </p>
     */
    private final String description;
    
    /**
     * <p>
     * Indicates whether the attribute is required as defined in the taglib XML file.
     * </p>
     * <p>
     * This corresponds to the &lt;required&gt; element in the attribute definition.
     * A value of {@code Boolean.TRUE} indicates that the attribute must be specified
     * when using the tag. A value of {@code Boolean.FALSE} indicates that the attribute
     * is optional. A {@code null} value typically indicates that the required status
     * was not specified in the taglib and should be treated as optional.
     * </p>
     */
    private final Boolean required;

}
