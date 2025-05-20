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

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serial;
import java.util.List;

/**
 * <p>
 * Represents metadata for a JSF converter tag. This class extends the base {@link Tag}
 * class to add properties specific to JSF converters, particularly the converter ID.
 * </p>
 * 
 * <p>
 * In the JSF framework, converters transform string values from the UI to complex objects 
 * and vice versa. This metadata class captures information about such converters as defined
 * in a taglib XML file, mapping to elements within the &lt;converter&gt; section:
 * </p>
 * 
 * <pre>
 * &lt;tag&gt;
 *   &lt;tag-name&gt;dateTimeConverter&lt;/tag-name&gt;
 *   &lt;converter&gt;
 *     &lt;converter-id&gt;jakarta.faces.DateTime&lt;/converter-id&gt;
 *   &lt;/converter&gt;
 *   &lt;description&gt;...&lt;/description&gt;
 *   &lt;attribute&gt;...&lt;/attribute&gt;
 * &lt;/tag&gt;
 * </pre>
 * 
 * <p>
 * Sample usage:
 * </p>
 * <pre>
 * // Create a converter metadata object
 * ConverterMetadata converterMetadata = new ConverterMetadata(
 *     "dateTimeConverter",
 *     "Converts between String and java.util.Date",
 *     attributesList,
 *     "jakarta.faces.DateTime");
 *     
 * // Access converter-specific information
 * String converterId = converterMetadata.getConverterId();
 * </pre>
 * 
 * <p>
 * This class is immutable after construction and therefore thread-safe.
 * </p>
 *
 * @author Oliver Wolff
 * @since 1.0
 */
@EqualsAndHashCode(callSuper = true)
@ToString
public class ConverterMetadata extends Tag {

    @Serial
    private static final long serialVersionUID = 6616399666003213817L;

    /**
     * <p>
     * The converter ID for this JSF converter tag.
     * </p>
     * <p>
     * In JSF, the converter ID is a unique identifier used to create converter instances
     * through the application's converter factory. This corresponds to the &lt;converter-id&gt;
     * element in the taglib XML.
     * </p>
     * <p>
     * Example values include {@code "jakarta.faces.DateTime"}, {@code "jakarta.faces.Number"},
     * or custom converter IDs like {@code "de.cuioss.jsf.components.converter.StringTrimConverter"}.
     * </p>
     */
    @Getter
    private final String converterId;

    /**
     * <p>
     * Constructs a new ConverterMetadata instance with the specified properties.
     * </p>
     * <p>
     * This constructor initializes a metadata object representing a JSF converter tag.
     * It requires the basic tag information inherited from the {@link Tag} base class,
     * plus the converter-specific converter ID.
     * </p>
     *
     * @param name The tag name as defined in the taglib XML file, must not be null
     * @param description The description of the tag from the taglib XML file, may be null
     * @param attributes The list of attribute metadata objects for this tag, must not be null,
     *                  but may be empty
     * @param converterId The converter ID for this converter tag, typically not null
     */
    public ConverterMetadata(String name, String description, List<AttributeMetadata> attributes, String converterId) {
        super(name, description, attributes);
        this.converterId = converterId;
    }

}
