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
 * Represents metadata for a JSF behavior tag. This class extends the base {@link Tag}
 * class to add properties specific to JSF behaviors, particularly the behavior ID.
 * </p>
 * 
 * <p>
 * In the JSF framework, behaviors allow attaching client-side functionality to components
 * via JavaScript. This metadata class captures information about such behaviors as defined
 * in a taglib XML file, mapping to elements within the &lt;behavior&gt; section:
 * </p>
 * 
 * <pre>
 * &lt;tag&gt;
 *   &lt;tag-name&gt;ajax&lt;/tag-name&gt;
 *   &lt;behavior&gt;
 *     &lt;behavior-id&gt;jakarta.faces.behavior.Ajax&lt;/behavior-id&gt;
 *   &lt;/behavior&gt;
 *   &lt;description&gt;...&lt;/description&gt;
 *   &lt;attribute&gt;...&lt;/attribute&gt;
 * &lt;/tag&gt;
 * </pre>
 * 
 * <p>
 * Sample usage:
 * </p>
 * <pre>
 * // Create a behavior metadata object
 * BehaviorMetadata behaviorMetadata = new BehaviorMetadata(
 *     "ajax",
 *     "Adds AJAX support to a component",
 *     attributesList,
 *     "jakarta.faces.behavior.Ajax");
 *     
 * // Access behavior-specific information
 * String behaviorId = behaviorMetadata.getBehaviorId();
 * </pre>
 * 
 * <p>
 * This class is immutable after construction and therefore thread-safe.
 * </p>
 *
 * @author Oliver Wolff
 * @since 1.0
 */
@ToString
@EqualsAndHashCode(callSuper = true)
public class BehaviorMetadata extends Tag {

    @Serial
    private static final long serialVersionUID = -1713844116234074377L;

    /**
     * <p>
     * The behavior ID for this JSF behavior tag.
     * </p>
     * <p>
     * In JSF, the behavior ID is a unique identifier used to create behavior instances
     * through the application's behavior factory. This corresponds to the &lt;behavior-id&gt;
     * element in the taglib XML.
     * </p>
     * <p>
     * Example values include {@code "jakarta.faces.behavior.Ajax"} for the standard Ajax behavior
     * or custom behavior IDs like {@code "de.cuioss.jsf.components.behavior.FocusBehavior"}.
     * </p>
     */
    @Getter
    private final String behaviorId;

    /**
     * <p>
     * A constant value indicating that renderer types are not applicable to behaviors.
     * </p>
     * <p>
     * Unlike UI components, behaviors don't use renderers. This field is included for
     * API consistency with other metadata classes and always returns the string
     * "Not defined for Behavior".
     * </p>
     */
    @Getter
    private final String rendererType = "Not defined for Behavior";

    /**
     * <p>
     * Constructs a new BehaviorMetadata instance with the specified properties.
     * </p>
     * <p>
     * This constructor initializes a metadata object representing a JSF behavior tag.
     * It requires the basic tag information inherited from the {@link Tag} base class,
     * plus the behavior-specific behavior ID.
     * </p>
     *
     * @param name The tag name as defined in the taglib XML file, must not be null
     * @param description The description of the tag from the taglib XML file, may be null
     * @param attributes The list of attribute metadata objects for this tag, must not be null,
     *                  but may be empty
     * @param targetbehaviorId The behavior ID for this behavior tag, typically not null
     */
    public BehaviorMetadata(final String name, final String description, final List<AttributeMetadata> attributes,
            final String targetbehaviorId) {
        super(name, description, attributes);
        behaviorId = targetbehaviorId;
    }

}
