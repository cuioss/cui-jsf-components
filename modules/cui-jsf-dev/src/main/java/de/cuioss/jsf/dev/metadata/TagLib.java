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
package de.cuioss.jsf.dev.metadata;

import de.cuioss.jsf.dev.metadata.model.*;
import de.cuioss.portal.common.util.PortalResourceLoader;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.xml.XMLConstants;

/**
 * <p>
 * Parses and extracts metadata from JSF taglib XML files. This class enables inspection
 * of taglib components, converters, validators, and behaviors for documentation and
 * development tools.
 * </p>
 *
 * <p>
 * The class supports JSF 2.0, 2.2, and 4.0 (Jakarta) tag library formats through dedicated
 * namespace constants. After parsing, the metadata is accessible via getter methods that
 * return {@link TagStorage} instances containing the appropriate metadata objects.
 * </p>
 *
 * <p>
 * Sample usage:
 * </p>
 * <pre>
 * // Load a JSF 4.0 taglib
 * TagLib taglib = new TagLib("/META-INF/my-taglib.xml",
 *                          TagLib.JSF_4_0_FACELET_TAGLIB_NAMESPACE);
 *
 * // Access metadata
 * List&lt;UIComponentMetadata&gt; components = taglib.getComponentMetadata().getCollected();
 * List&lt;ConverterMetadata&gt; converters = taglib.getConverterMetadata().getCollected();
 * </pre>
 *
 * <p>
 * This class is immutable after construction and therefore thread-safe.
 * </p>
 *
 * @author Oliver Wolff
 * @since 1.0
 */
@EqualsAndHashCode
@ToString
public class TagLib implements Serializable {

    @Serial
    private static final long serialVersionUID = -8670139665218311536L;

    /**
     * Name space for JSF 2.2 schema, usable with the constructor for loading JSF 2.2 taglibs.
     */
    public static final String JSF_2_2_FACELET_TAGLIB_NAMESPACE = "http://xmlns.jcp.org/xml/ns/javaee";

    /**
     * Name space for JSF 4.0 schema (Jakarta EE), usable with the constructor for loading
     * Jakarta EE 4.0 taglibs.
     */
    public static final String JSF_4_0_FACELET_TAGLIB_NAMESPACE = "https://jakarta.ee/xml/ns/jakartaee";

    /**
     * Name space for JSF 2.0 schema, usable with the constructor for loading legacy JSF 2.0 taglibs.
     */
    public static final String JSF_2_FACELET_TAGLIB_NAMESPACE = "http://java.sun.com/xml/ns/javaee";

    /**
     * XML element name for component definitions in the taglib.
     */
    private static final String COMPONENT = "component";

    /**
     * XML element name for tag definitions in the taglib.
     */
    private static final String TAG = "tag";

    /**
     * The namespace used for parsing the taglib XML file. This is set in the constructor
     * based on the provided tagLibNamespaceUrl.
     */
    private final Namespace taglibNamespace;

    /**
     * The path to the taglib XML file. This is used for error reporting and debugging.
     */
    @Getter
    private final String tagPath;

    /**
     * The namespace declared by the taglib XML file. This represents the XML namespace
     * used for the custom tags defined in the taglib, not to be confused with the JSF namespace.
     */
    @Getter
    private String namespace;

    /**
     * Storage for all component metadata extracted from the taglib.
     * This includes both standard component tags and tags with handler classes.
     */
    @Getter
    private final TagStorage<UIComponentMetadata> componentMetadata;

    /**
     * Storage for all converter metadata extracted from the taglib.
     */
    @Getter
    private final TagStorage<ConverterMetadata> converterMetadata;

    /**
     * Storage for all validator metadata extracted from the taglib.
     */
    @Getter
    private final TagStorage<ValidatorMetadata> validatorMetadata;

    /**
     * Storage for all behavior metadata extracted from the taglib.
     */
    @Getter
    private final TagStorage<BehaviorMetadata> behaviorMetadata;

    /**
     * <p>
     * Constructs a new TagLib instance by loading and parsing the specified JSF taglib file.
     * </p>
     *
     * <p>
     * The constructor initializes all metadata storages, loads the specified resource,
     * parses it according to the specified namespace, and populates the metadata collections.
     * </p>
     *
     * @param tagLibPath The path to the taglib XML file to be parsed. Must be accessible
     *                  via the classpath, typically starting with "/META-INF/".
     * @param tagLibNamespaceUrl The JSF namespace URL for the taglib XML schema,
     *                          should be one of the JSF_*_FACELET_TAGLIB_NAMESPACE constants.
     * @throws IllegalStateException If the taglib file cannot be found at the specified path.
     * @throws IllegalArgumentException If there is an error parsing the taglib XML file.
     */
    public TagLib(final String tagLibPath, final String tagLibNamespaceUrl) {

        taglibNamespace = Namespace.getNamespace(tagLibNamespaceUrl);

        tagPath = tagLibPath;

        final var resource = PortalResourceLoader.getResource(tagLibPath, getClass());

        componentMetadata = new TagStorage<>();
        converterMetadata = new TagStorage<>();
        validatorMetadata = new TagStorage<>();
        behaviorMetadata = new TagStorage<>();

        parseTagLib(
                resource.orElseThrow(() -> new IllegalStateException("Unable to load TagLib from path: " + tagPath)));

        componentMetadata.sortCollected();
        converterMetadata.sortCollected();
        validatorMetadata.sortCollected();
        behaviorMetadata.sortCollected();
    }

    /**
     * <p>
     * Parses a taglib XML file and populates the metadata collections.
     * </p>
     *
     * <p>
     * This method uses JDOM to parse the XML file, extract the namespace and tag
     * definitions, and create appropriate metadata objects based on the tag type.
     * </p>
     *
     * @param resource The URL to the taglib XML file to be parsed.
     * @throws IllegalArgumentException If there is an error accessing or parsing the XML file.
     */
    private void parseTagLib(final URL resource) {

        try {
            final var saxBuilder = new SAXBuilder();
            saxBuilder.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            saxBuilder.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
            final var document = saxBuilder.build(resource);
            final var root = document.getRootElement();
            namespace = root.getChildText("namespace", taglibNamespace);
            final var tags = root.getChildren(TAG, taglibNamespace);

            for (final Element tag : tags) {
                if (null != tag.getChild(COMPONENT, taglibNamespace)) {
                    componentMetadata.add(createComponentMetadata(tag));
                } else if (null != tag.getChild("converter", taglibNamespace)) {
                    converterMetadata.add(createConverterMetadata(tag));
                } else if (null != tag.getChild("validator", taglibNamespace)) {
                    validatorMetadata.add(createValidatorMetadata(tag));
                } else if (null != tag.getChild("handler-class", taglibNamespace)) {
                    componentMetadata.add(createComponentMetadata(tag));
                } else if (null != tag.getChild("behavior", taglibNamespace)) {
                    behaviorMetadata.add(createBehaviorMetadata(tag));
                }
            }

        } catch (JDOMException | IOException e) {
            throw new IllegalArgumentException(
                    "Unable to access file '%s', due to '%s'".formatted(tagPath, e.getMessage()), e);
        }
    }

    /**
     * <p>
     * Creates a {@link ValidatorMetadata} object from a validator tag element.
     * </p>
     *
     * <p>
     * This method extracts the validator-id, tag name, description, and attributes
     * from the XML element and creates a new ValidatorMetadata instance.
     * </p>
     *
     * @param tag The XML element representing a validator tag.
     * @return A new ValidatorMetadata instance containing the extracted information.
     */
    private ValidatorMetadata createValidatorMetadata(final Element tag) {
        var validatorDescriptor = tag.getChild("validator", taglibNamespace);
        var validatorId = validatorDescriptor.getChildTextTrim("validator-id", taglibNamespace);
        return new ValidatorMetadata(extractTagName(tag), extractDescription(tag), extractAttrbutesMetadata(tag),
                validatorId);
    }

    /**
     * <p>
     * Creates a {@link BehaviorMetadata} object from a behavior tag element.
     * </p>
     *
     * <p>
     * This method extracts the behavior-id, tag name, description, and attributes
     * from the XML element and creates a new BehaviorMetadata instance.
     * </p>
     *
     * @param tag The XML element representing a behavior tag.
     * @return A new BehaviorMetadata instance containing the extracted information.
     */
    private BehaviorMetadata createBehaviorMetadata(final Element tag) {
        var behaviorDescriptor = tag.getChild("behavior", taglibNamespace);
        var behaviorId = behaviorDescriptor.getChildTextTrim("behavior-id", taglibNamespace);
        return new BehaviorMetadata(extractTagName(tag), extractDescription(tag), extractAttrbutesMetadata(tag),
                behaviorId);
    }

    /**
     * <p>
     * Creates a {@link ConverterMetadata} object from a converter tag element.
     * </p>
     *
     * <p>
     * This method extracts the converter-id, tag name, description, and attributes
     * from the XML element and creates a new ConverterMetadata instance.
     * </p>
     *
     * @param tag The XML element representing a converter tag.
     * @return A new ConverterMetadata instance containing the extracted information.
     */
    private ConverterMetadata createConverterMetadata(final Element tag) {
        var converterDescriptor = tag.getChild("converter", taglibNamespace);
        var converterId = converterDescriptor.getChildTextTrim("converter-id", taglibNamespace);
        return new ConverterMetadata(extractTagName(tag), extractDescription(tag), extractAttrbutesMetadata(tag),
                converterId);
    }

    /**
     * <p>
     * Creates a {@link UIComponentMetadata} object from a component tag element.
     * </p>
     *
     * <p>
     * This method extracts the component-type, renderer-type, handler-class, tag name,
     * description, and attributes from the XML element and creates a new
     * UIComponentMetadata instance.
     * </p>
     *
     * <p>
     * The method handles both standard component tags and tags with handler classes.
     * </p>
     *
     * @param tag The XML element representing a component tag.
     * @return A new UIComponentMetadata instance containing the extracted information.
     */
    private UIComponentMetadata createComponentMetadata(final Element tag) {
        var componentDescriptor = tag.getChild(COMPONENT, taglibNamespace);
        String componentType = null;
        String rendererType = null;
        if (null != componentDescriptor) {
            componentType = componentDescriptor.getChildTextTrim("component-type", taglibNamespace);
            rendererType = componentDescriptor.getChildTextTrim("renderer-type", taglibNamespace);
        }
        var handlerClass = tag.getChildTextTrim("handler-class", taglibNamespace);
        return new UIComponentMetadata(extractTagName(tag), extractAttrbutesMetadata(tag), extractDescription(tag),
                componentType, rendererType, handlerClass);
    }

    /**
     * <p>
     * Extracts attribute metadata from a tag element.
     * </p>
     *
     * <p>
     * This method processes all attribute elements within the given tag element,
     * extracting the name, type (or method-signature), description, and required status
     * for each attribute and creating AttributeMetadata objects. The resulting list is
     * sorted alphabetically by attribute name.
     * </p>
     *
     * @param tag The XML element containing attribute definitions.
     * @return A sorted list of AttributeMetadata objects representing the tag's attributes.
     */
    private List<AttributeMetadata> extractAttrbutesMetadata(final Element tag) {
        List<AttributeMetadata> attributeList = new ArrayList<>();
        for (Element attribute : tag.getChildren("attribute", taglibNamespace)) {
            var requiredString = attribute.getChildTextTrim("required", taglibNamespace);
            Boolean required = null;
            if (null != requiredString) {
                required = Boolean.valueOf(requiredString);
            }
            var type = attribute.getChildTextTrim("type", taglibNamespace);
            // Method binding are declared different
            if (null == type) {
                type = attribute.getChildTextTrim("method-signature", taglibNamespace);
            }
            attributeList
                    .add(new AttributeMetadata(extractName(attribute), type, extractDescription(attribute), required));
        }
        attributeList.sort(Comparator.comparing(AttributeMetadata::getName));
        return attributeList;
    }

    /**
     * <p>
     * Extracts the description from an XML element.
     * </p>
     *
     * <p>
     * This method retrieves the text content of the "description" child element,
     * which may contain HTML markup for formatting.
     * </p>
     *
     * @param attribute The XML element containing a description.
     * @return The description text, or null if no description is present.
     */
    private String extractDescription(final Element attribute) {
        return attribute.getChildText("description", taglibNamespace);
    }

    /**
     * <p>
     * Extracts the name from an XML element.
     * </p>
     *
     * <p>
     * This method retrieves the trimmed text content of the "name" child element.
     * </p>
     *
     * @param attribute The XML element containing a name.
     * @return The name value, or null if no name is present.
     */
    private String extractName(final Element attribute) {
        return attribute.getChildTextTrim("name", taglibNamespace);
    }

    /**
     * <p>
     * Extracts the tag name from an XML element.
     * </p>
     *
     * <p>
     * This method retrieves the trimmed text content of the "tag-name" child element.
     * </p>
     *
     * @param attribute The XML element containing a tag-name.
     * @return The tag name value, or null if no tag name is present.
     */
    private String extractTagName(final Element attribute) {
        return attribute.getChildTextTrim("tag-name", taglibNamespace);
    }

}
