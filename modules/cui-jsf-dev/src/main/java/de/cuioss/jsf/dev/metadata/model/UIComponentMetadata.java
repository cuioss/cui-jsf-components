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
 * Represents metadata for a JSF UI component tag. This class extends the base {@link Tag}
 * class to add properties specific to JSF UI components, such as component type,
 * renderer type, and handler class.
 * </p>
 * 
 * <p>
 * In the JSF framework, UI components are the visual elements rendered to the user
 * interface. This metadata class captures information about such components as defined
 * in a taglib XML file, mapping to elements within the &lt;component&gt; section:
 * </p>
 * 
 * <pre>
 * &lt;tag&gt;
 *   &lt;tag-name&gt;button&lt;/tag-name&gt;
 *   &lt;component&gt;
 *     &lt;component-type&gt;jakarta.faces.Button&lt;/component-type&gt;
 *     &lt;renderer-type&gt;jakarta.faces.Button&lt;/renderer-type&gt;
 *   &lt;/component&gt;
 *   &lt;description&gt;...&lt;/description&gt;
 *   &lt;attribute&gt;...&lt;/attribute&gt;
 * &lt;/tag&gt;
 * </pre>
 * 
 * <p>
 * For component tags that use a tag handler instead of a component definition, the
 * handlerClass property will contain the handler class name, and componentType and
 * rendererType will be null.
 * </p>
 * 
 * <p>
 * Sample usage:
 * </p>
 * <pre>
 * // Create a component metadata object
 * UIComponentMetadata componentMetadata = new UIComponentMetadata(
 *     "button",
 *     attributesList,
 *     "Button component for performing actions",
 *     "jakarta.faces.Button",
 *     "jakarta.faces.Button",
 *     null);
 *     
 * // Access component-specific information
 * String componentType = componentMetadata.getComponentType();
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
public class UIComponentMetadata extends Tag {

    @Serial
    private static final long serialVersionUID = 8405697485171286343L;

    /**
     * <p>
     * The component type identifier for this UI component tag.
     * </p>
     * <p>
     * In JSF, the component type is a unique identifier used to create component instances
     * through the application's component factory. This corresponds to the &lt;component-type&gt;
     * element in the taglib XML.
     * </p>
     * <p>
     * May be {@code null} for tags that use a handler class instead of a standard component.
     * </p>
     */
    @Getter
    private final String componentType;

    /**
     * <p>
     * The renderer type identifier for this UI component tag.
     * </p>
     * <p>
     * In JSF, the renderer type identifies which renderer should be used to generate
     * the component's markup. This corresponds to the &lt;renderer-type&gt; element in
     * the taglib XML.
     * </p>
     * <p>
     * May be {@code null} for components that don't use a renderer or for tags that
     * use a handler class.
     * </p>
     */
    @Getter
    private final String rendererType;

    /**
     * <p>
     * The handler class name for this UI component tag.
     * </p>
     * <p>
     * For tags that don't use the standard component/renderer pattern, the handler class
     * defines custom tag processing logic. This corresponds to the &lt;handler-class&gt;
     * element in the taglib XML.
     * </p>
     * <p>
     * May be {@code null} for standard component tags that don't require a custom handler.
     * </p>
     */
    @Getter
    private final String handlerClass;

    /**
     * <p>
     * Constructs a new UIComponentMetadata instance with the specified properties.
     * </p>
     * <p>
     * This constructor initializes a metadata object representing a JSF UI component tag.
     * It requires the basic tag information inherited from the {@link Tag} base class,
     * plus component-specific details like component type and renderer type.
     * </p>
     *
     * @param name The tag name as defined in the taglib XML file, must not be null
     * @param attributes The list of attribute metadata objects for this tag, must not be null,
     *                  but may be empty
     * @param description The description of the tag from the taglib XML file, may be null
     * @param componentType The component type identifier for this tag, may be null for tags
     *                     that use a handler class
     * @param rendererType The renderer type identifier for this tag, may be null for components
     *                    that don't use a renderer or for tags that use a handler class
     * @param handlerClass The handler class name for this tag, may be null for standard
     *                    component tags
     */
    public UIComponentMetadata(final String name, final List<AttributeMetadata> attributes, final String description,
            final String componentType, final String rendererType, final String handlerClass) {
        super(name, description, attributes);
        this.componentType = componentType;
        this.rendererType = rendererType;
        this.handlerClass = handlerClass;
    }
}
