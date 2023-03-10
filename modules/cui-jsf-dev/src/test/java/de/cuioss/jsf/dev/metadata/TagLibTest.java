package de.cuioss.jsf.dev.metadata;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.dev.metadata.model.AttributeMetadata;
import de.cuioss.jsf.dev.metadata.model.ConverterMetadata;
import de.cuioss.jsf.dev.metadata.model.Tag;
import de.cuioss.jsf.dev.metadata.model.UIComponentMetadata;
import de.cuioss.jsf.dev.metadata.model.ValidatorMetadata;
import de.cuioss.test.valueobjects.ValueObjectTest;
import de.cuioss.test.valueobjects.api.property.PropertyReflectionConfig;

@PropertyReflectionConfig(skip = true)
class TagLibTest extends ValueObjectTest<TagLib> {

    private static final String CUI_NAMESPACE = "http://www.icw.de/commons-webui-test";

    private static final String TAGLIB = "/commons-webui-test.taglib.xml";

    @Test
    void testTagLib() {
        var tagLib = new TagLib(TAGLIB, TagLib.JSF_2_2_FACELET_TAGLIB_NAMESPACE);
        assertEquals(CUI_NAMESPACE, tagLib.getNamespace());
        assertTrue(tagLib.getComponentMetadata().size() > 18);
        assertTrue(tagLib.getConverterMetadata().size() > 2);
        assertTrue(tagLib.getValidatorMetadata().size() > 0);
        assertNotNull(tagLib.getBehaviorMetadata());
        assertNotNull(tagLib.getTagPath());
        for (UIComponentMetadata component : tagLib.getComponentMetadata()) {
            assertComponentContract(component);
        }
        for (ConverterMetadata converterMetadata : tagLib.getConverterMetadata()) {
            assertConverterContract(converterMetadata);
        }
        for (ValidatorMetadata validatorMetadata : tagLib.getValidatorMetadata()) {
            assertValidatorContract(validatorMetadata);
        }
    }

    @Override
    protected TagLib anyValueObject() {
        return new TagLib(TAGLIB, TagLib.JSF_2_2_FACELET_TAGLIB_NAMESPACE);
    }

    /**
     * Checks whether the given component adheres to the Cui-component contract
     *
     * @param converterMetadata
     */
    public static void assertConverterContract(final ConverterMetadata converterMetadata) {
        assertTagContract(converterMetadata);
        assertNotNull(converterMetadata.getConverterId(),
                "Converter must provide a converter-id, " + converterMetadata.getName());
    }

    /**
     * Checks whether the given component adheres to the Cui-component contract
     *
     * @param validatorMetadata
     */
    public static void assertValidatorContract(final ValidatorMetadata validatorMetadata) {
        assertTagContract(validatorMetadata);
        assertNotNull(validatorMetadata.getValidatorId(),
                "Validator must provide a validator-id, " + validatorMetadata.getName());
    }

    /**
     * Checks whether the given component adheres to the Cui-component contract
     *
     * @param component
     */
    public static void assertComponentContract(final UIComponentMetadata component) {
        assertTagContract(component);
        assertNotNull(component.getComponentType(), "Component must provide a component-type, " + component.getName());
        assertNotNull(component.getRendererType(), "Component must provide a renderer-type, " + component.getName());
    }

    /**
     * @param tag
     *            to be checked
     */
    public static void assertTagContract(final Tag tag) {
        assertNotNull(tag, "Tag must not be null");
        assertNotNull(tag.getName(), "Tag name not be null");
        assertNotNull(tag.getDescription(), "Tag must provide a description, " + tag.getName());
        for (AttributeMetadata attribute : tag.getAttributes()) {
            assertComponentAttributeContract(tag.getName(), attribute);
        }
    }

    /**
     * Checks whether the given attribute adheres to the Cui-component contract
     *
     * @param componentName
     * @param attribute
     */
    public static void assertComponentAttributeContract(final String componentName, final AttributeMetadata attribute) {
        assertNotNull(attribute, "Attribute must not be null, tag=" + componentName);
        assertNotNull(attribute.getName(), "Attribute must not be null, tag=" + componentName);
        assertNotNull(attribute.getDescription(),
                "Description must not be null, tag=" + componentName + ", attribute=" + attribute.getName());
        assertNotNull(attribute.getRequired(),
                "Required must not be null, tag=" + componentName + ", attribute=" + attribute.getName());
        assertNotNull(attribute.getType(),
                "Type must not be null, tag=" + componentName + ", attribute=" + attribute.getName());
    }
}
