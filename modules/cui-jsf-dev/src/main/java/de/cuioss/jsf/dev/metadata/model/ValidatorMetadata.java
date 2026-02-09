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

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serial;
import java.util.List;

/**
 * <p>
 * Represents metadata for a JSF validator tag. This class extends the base {@link Tag}
 * class to add properties specific to JSF validators, particularly the validator ID.
 * </p>
 * 
 * <p>
 * In the JSF framework, validators check input values against business rules and constraints.
 * This metadata class captures information about such validators as defined in a taglib XML file,
 * mapping to elements within the &lt;validator&gt; section:
 * </p>
 * 
 * <pre>
 * &lt;tag&gt;
 *   &lt;tag-name&gt;validateLength&lt;/tag-name&gt;
 *   &lt;validator&gt;
 *     &lt;validator-id&gt;jakarta.faces.Length&lt;/validator-id&gt;
 *   &lt;/validator&gt;
 *   &lt;description&gt;...&lt;/description&gt;
 *   &lt;attribute&gt;...&lt;/attribute&gt;
 * &lt;/tag&gt;
 * </pre>
 * 
 * <p>
 * Sample usage:
 * </p>
 * <pre>
 * // Create a validator metadata object
 * ValidatorMetadata validatorMetadata = new ValidatorMetadata(
 *     "validateLength",
 *     "Validates that the string length of the component is within a specified range",
 *     attributesList,
 *     "jakarta.faces.Length");
 *     
 * // Access validator-specific information
 * String validatorId = validatorMetadata.getValidatorId();
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
public class ValidatorMetadata extends Tag {

    @Serial
    private static final long serialVersionUID = 6616399666003213817L;

    /**
     * <p>
     * The validator ID for this JSF validator tag.
     * </p>
     * <p>
     * In JSF, the validator ID is a unique identifier used to create validator instances
     * through the application's validator factory. This corresponds to the &lt;validator-id&gt;
     * element in the taglib XML.
     * </p>
     * <p>
     * Example values include {@code "jakarta.faces.Length"}, {@code "jakarta.faces.Required"},
     * or custom validator IDs like {@code "de.cuioss.jsf.components.validator.EmailValidator"}.
     * </p>
     */
    @Getter
    private final String validatorId;

    /**
     * <p>
     * Constructs a new ValidatorMetadata instance with the specified properties.
     * </p>
     * <p>
     * This constructor initializes a metadata object representing a JSF validator tag.
     * It requires the basic tag information inherited from the {@link Tag} base class,
     * plus the validator-specific validator ID.
     * </p>
     *
     * @param name The tag name as defined in the taglib XML file, must not be null
     * @param description The description of the tag from the taglib XML file, may be null
     * @param attributes The list of attribute metadata objects for this tag, must not be null,
     *                  but may be empty
     * @param validatorId The validator ID for this validator tag, typically not null
     */
    public ValidatorMetadata(String name, String description, List<AttributeMetadata> attributes, String validatorId) {
        super(name, description, attributes);
        this.validatorId = validatorId;
    }

}
