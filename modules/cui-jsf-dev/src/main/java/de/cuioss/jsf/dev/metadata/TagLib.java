package de.cuioss.jsf.dev.metadata;

import static com.google.common.base.Preconditions.checkState;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;

import de.cuioss.jsf.dev.metadata.model.AttributeMetadata;
import de.cuioss.jsf.dev.metadata.model.BehaviorMetadata;
import de.cuioss.jsf.dev.metadata.model.ConverterMetadata;
import de.cuioss.jsf.dev.metadata.model.TagStorage;
import de.cuioss.jsf.dev.metadata.model.UIComponentMetadata;
import de.cuioss.jsf.dev.metadata.model.ValidatorMetadata;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Oliver Wolff
 */
@EqualsAndHashCode
@ToString
public class TagLib implements Serializable {

    private static final long serialVersionUID = -8670139665218311536L;

    /**
     * Name space for JSF 2.2 schema
     */
    public static final String JSF_2_2_FACELET_TAGLIB_NAMESPACE =
        "http://xmlns.jcp.org/xml/ns/javaee";

    /**
     * Name space for JSF 2.0 schema
     */
    public static final String JSF_2_FACELET_TAGLIB_NAMESPACE =
        "http://java.sun.com/xml/ns/javaee";

    private static final String COMPONENT = "component";
    private static final String TAG = "tag";
    private final Namespace taglibNamespace;

    @Getter
    private final String tagPath;

    @Getter
    private String namespace;

    @Getter
    private final TagStorage<UIComponentMetadata> componentMetadata;

    @Getter
    private final TagStorage<ConverterMetadata> converterMetadata;

    @Getter
    private final TagStorage<ValidatorMetadata> validatorMetadata;

    @Getter
    private final TagStorage<BehaviorMetadata> behaviorMetadata;

    /**
     * Constructor
     *
     * @param tagLibPath tag lib name
     * @param tagLibNamespaceUrl jsf namespace
     */
    public TagLib(final String tagLibPath, final String tagLibNamespaceUrl) {

        taglibNamespace = Namespace.getNamespace(tagLibNamespaceUrl);

        tagPath = tagLibPath;

        final var resource = TagLib.class.getResource(tagPath);

        checkState(null != resource, "Unable to load path %d", tagPath);

        componentMetadata = new TagStorage<>();
        converterMetadata = new TagStorage<>();
        validatorMetadata = new TagStorage<>();
        behaviorMetadata = new TagStorage<>();

        parseTagLib(resource);

        componentMetadata.sortCollected();
        converterMetadata.sortCollected();
        validatorMetadata.sortCollected();
        behaviorMetadata.sortCollected();
    }

    /**
     * @param tagPath
     * @param resource
     */
    private void parseTagLib(final URL resource) {

        try {

            final var document = new SAXBuilder().build(resource);
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
                } else if (null != tag.getChild("handler-class",
                        taglibNamespace)) {
                    componentMetadata.add(createComponentMetadata(tag));
                } else if (null != tag.getChild("behavior", taglibNamespace)) {
                    behaviorMetadata.add(createBehaviorMetadata(tag));
                }
            }

        } catch (JDOMException | IOException e) {
            throw new IllegalArgumentException(String.format("Unable to access file '%s', due to '%s'",
                    tagPath, e.getMessage()), e);
        }
    }

    /**
     * @param tag2
     * @return
     */
    private ValidatorMetadata createValidatorMetadata(final Element tag) {
        var validatorDescriptor = tag
                .getChild("validator", taglibNamespace);
        var validatorId = validatorDescriptor.getChildTextTrim(
                "validator-id", taglibNamespace);
        return new ValidatorMetadata(extractTagName(tag),
                extractDescription(tag), extractAttrbutesMetadata(tag),
                validatorId);
    }

    /**
     * @param tag2
     * @return
     */
    private BehaviorMetadata createBehaviorMetadata(final Element tag) {
        var behaviorDescriptor = tag
                .getChild("behavior", taglibNamespace);
        var behaviorId = behaviorDescriptor.getChildTextTrim(
                "behavior-id", taglibNamespace);
        return new BehaviorMetadata(extractTagName(tag),
                extractDescription(tag), extractAttrbutesMetadata(tag),
                behaviorId);
    }

    /**
     * @param tag2
     * @return
     */
    private ConverterMetadata createConverterMetadata(final Element tag) {
        var converterDescriptor = tag
                .getChild("converter", taglibNamespace);
        var converterId = converterDescriptor.getChildTextTrim(
                "converter-id", taglibNamespace);
        return new ConverterMetadata(extractTagName(tag),
                extractDescription(tag), extractAttrbutesMetadata(tag),
                converterId);
    }

    /**
     * @param tag
     */
    private UIComponentMetadata createComponentMetadata(final Element tag) {
        var componentDescriptor = tag
                .getChild(COMPONENT, taglibNamespace);
        String componentType = null;
        String rendererType = null;
        if (null != componentDescriptor) {
            componentType = componentDescriptor.getChildTextTrim(
                    "component-type", taglibNamespace);
            rendererType = componentDescriptor.getChildTextTrim(
                    "renderer-type", taglibNamespace);
        }
        var handlerClass = tag.getChildTextTrim("handler-class",
                taglibNamespace);
        return new UIComponentMetadata(extractTagName(tag),
                extractAttrbutesMetadata(tag), extractDescription(tag),
                componentType, rendererType, handlerClass);
    }

    /**
     * @param tag
     * @return
     */
    private List<AttributeMetadata> extractAttrbutesMetadata(final Element tag) {
        List<AttributeMetadata> attributeList = new ArrayList<>();
        for (Element attribute : tag.getChildren("attribute", taglibNamespace)) {
            var requiredString = attribute.getChildTextTrim("required",
                    taglibNamespace);
            Boolean required = null;
            if (null != requiredString) {
                required = Boolean.valueOf(requiredString);
            }
            var type = attribute.getChildTextTrim("type", taglibNamespace);
            // Method binding are declared different
            if (null == type) {
                type = attribute.getChildTextTrim("method-signature",
                        taglibNamespace);
            }
            attributeList.add(new AttributeMetadata(extractName(attribute),
                    type, extractDescription(attribute), required));
        }
        attributeList.sort(Comparator.comparing(AttributeMetadata::getName));
        return attributeList;
    }

    /**
     * @param attribute
     * @return
     */
    private String extractDescription(final Element attribute) {
        return attribute.getChildText("description", taglibNamespace);
    }

    /**
     * @param attribute
     * @return
     */
    private String extractName(final Element attribute) {
        return attribute.getChildTextTrim("name", taglibNamespace);
    }

    /**
     * @param attribute
     * @return
     */
    private String extractTagName(final Element attribute) {
        return attribute.getChildTextTrim("tag-name", taglibNamespace);
    }

}