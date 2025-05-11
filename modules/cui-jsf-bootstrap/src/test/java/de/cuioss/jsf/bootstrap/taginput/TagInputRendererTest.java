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
package de.cuioss.jsf.bootstrap.taginput;

import static de.cuioss.tools.collect.CollectionLiterals.*;
import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.jsf.api.components.html.AttributeName;
import de.cuioss.jsf.api.components.html.AttributeValue;
import de.cuioss.jsf.api.components.html.HtmlTreeBuilder;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.api.components.javascript.NotQuotableWrapper;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.CssBootstrap;
import de.cuioss.jsf.bootstrap.selectize.Selectize;
import de.cuioss.jsf.bootstrap.support.ConceptKeyTypeGenerator;
import de.cuioss.jsf.bootstrap.tag.support.MissingTagConceptKeyCategory;
import de.cuioss.jsf.bootstrap.taglist.TagTestUtils;
import de.cuioss.jsf.test.CoreJsfTestConfiguration;
import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import de.cuioss.test.jsf.config.decorator.RequestConfigDecorator;
import de.cuioss.test.jsf.renderer.AbstractComponentRendererTest;
import de.cuioss.tools.codec.Hex;
import de.cuioss.tools.string.MoreStrings;
import de.cuioss.uimodel.model.code.CodeType;
import de.cuioss.uimodel.model.conceptkey.ConceptKeyType;
import de.cuioss.uimodel.model.conceptkey.impl.ConceptKeyTypeImpl;
import de.cuioss.uimodel.nameprovider.I18nDisplayNameProvider;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.ConverterException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@JsfTestConfiguration(CoreJsfTestConfiguration.class)
class TagInputRendererTest extends AbstractComponentRendererTest<TagInputRenderer> {

    private static final String CLIENT_ID = "j_id__v_0";

    private static final ConceptKeyType CODE_TYPE_3_UNDEFINED = ConceptKeyTypeGenerator.TestConceptCategory
            .createUndefinedConceptKey("identifier3");

    private static final String HARMFUL = "<script>alert('hello')</script>";

    private final TypedGenerator<Integer> numberGenerator = Generators.integers(1, 2);

    static final ConceptKeyType CODE_TYPE_1 = ConceptKeyTypeImpl.builder().identifier("identifier1")
            .labelResolver(new I18nDisplayNameProvider("resolved1"))
            .category(ConceptKeyTypeGenerator.TestConceptCategory).build();

    private static final ConceptKeyType CODE_TYPE_1_HARMFUL = ConceptKeyTypeImpl.builder()
            .identifier("identifier1" + HARMFUL)
            .labelResolver(new I18nDisplayNameProvider.Builder().defaultValue("resolved1" + HARMFUL).build())
            .category(ConceptKeyTypeGenerator.TestConceptCategory).build();

    static final ConceptKeyType CODE_TYPE_2 = ConceptKeyTypeImpl.builder().identifier("identifier2")
            .labelResolver(new I18nDisplayNameProvider("resolved2"))
            .category(ConceptKeyTypeGenerator.TestConceptCategory).build();

    private static final ConceptKeyType CODE_TYPE_CLIENT_CREATED = ConceptKeyTypeImpl.builder()
            .identifier(Selectize.CLIENT_CREATED_SUFFIX + "hello").labelResolver(new I18nDisplayNameProvider("hello"))
            .category(new MissingTagConceptKeyCategory()).build();

    private static final String IDENTIFIER1_HEX = Hex.encodeHexString("identifier1".getBytes());

    private static final String IDENTIFIER2_HEX = Hex.encodeHexString("identifier2".getBytes());

    private static final String IDENTIFIER3_HEX = Hex.encodeHexString("identifier3".getBytes());

    private static final String IDENTIFIER_HELLO_HEX = Hex.encodeHexString("_client_created_hello".getBytes());

    private static final String DEFAULT_OPTIONS = "{\"label\":\"resolved1\",\"value\":\"" + IDENTIFIER1_HEX
            + "\"},{\"label\":\"resolved2\",\"value\":\"" + IDENTIFIER2_HEX + "\"}";

    private static final NotQuotableWrapper OPTIONS_AS_NON_QUOTABLE_WRAPPER = new NotQuotableWrapper(
            "[" + DEFAULT_OPTIONS + "]");

    @Test
    void shouldRenderMinimal(FacesContext facesContext) throws IOException {
        final var component = new TagInputComponent();
        final var expected = createInputNodeWithoutValue();
        addDataAttributes(expected, component, null);
        assertRenderResult(component, expected.getDocument(), facesContext);
    }

    @Test
    void shouldRenderDisabled(FacesContext facesContext) throws IOException {
        final var component = new TagInputComponent();
        component.setDisabled(true);
        component.setValue(Set.of(CODE_TYPE_1));
        final var expected = new HtmlTreeBuilder();
        expected.withNode(Node.UL).withStyleClass(CssBootstrap.LIST_INLINE.getStyleClass());
        TagTestUtils.insertTag(expected);
        addDataAttributes(expected, component, null);
        assertRenderResult(component, expected.getDocument(), facesContext);
    }

    @Test
    void shouldConsiderUserCreate(FacesContext facesContext) throws IOException {
        final var component = new TagInputComponent();
        final var expected = createInputNodeWithoutValue();
        component.setLetUserCreateTags(false);
        addDataAttributes(expected, component, null);
        assertRenderResult(component, expected.getDocument(), facesContext);
    }

    @Test
    void shouldConsiderSourceSet(FacesContext facesContext) throws IOException {
        final var component = new TagInputComponent();
        final var expected = createInputNodeWithoutValue();
        final Set<ConceptKeyType> sourceSet = immutableSet(CODE_TYPE_1, CODE_TYPE_2);
        component.setSourceSet(sourceSet);
        component.setLetUserCreateTags(false);
        addDataAttributes(expected, component, OPTIONS_AS_NON_QUOTABLE_WRAPPER.getValue().toString());
        assertRenderResult(component, expected.getDocument(), facesContext);
    }

    @Test
    void shouldSanitizeOutput(FacesContext facesContext) throws IOException {
        final var component = new TagInputComponent();
        final var expected = createInputNodeWithoutValue();
        final Set<ConceptKeyType> sourceSet = immutableSet(CODE_TYPE_1_HARMFUL, CODE_TYPE_2);
        component.setSourceSet(sourceSet);
        component.setLetUserCreateTags(false);
        final var options = new NotQuotableWrapper("[{\"label\":\"resolved1\",\"value\":\""
                + Hex.encodeHexString(CODE_TYPE_1_HARMFUL.getIdentifier().getBytes())
                + "\"},{\"label\":\"resolved2\",\"value\":\"" + IDENTIFIER2_HEX + "\"}]");
        addDataAttributes(expected, component, options.getValue().toString());
        assertRenderResult(component, expected.getDocument(), facesContext);
    }

    @Test
    void shouldSetIdentifierAsValue(FacesContext facesContext) throws IOException {
        final var component = new TagInputComponent();
        final var expected = createInputNodeWithoutValue().withAttribute(AttributeName.VALUE,
                immutableList(CODE_TYPE_1, CODE_TYPE_2).toString());
        final Set<ConceptKeyType> sourceSet = immutableSet(CODE_TYPE_1, CODE_TYPE_2);
        component.setSourceSet(sourceSet);
        component.setLetUserCreateTags(false);
        component.setValue(sourceSet);
        addDataAttributes(expected, component, OPTIONS_AS_NON_QUOTABLE_WRAPPER.getValue().toString());
        assertRenderResult(component, expected.getDocument(), facesContext);
    }

    @Test
    void shouldAddUndefinedToIdentifierAsValue(FacesContext facesContext) throws IOException {
        final var component = new TagInputComponent();
        final Set<ConceptKeyType> value = immutableSortedSet(CODE_TYPE_1, CODE_TYPE_2, CODE_TYPE_3_UNDEFINED);
        final var expected = createInputNodeWithoutValue().withAttribute(AttributeName.VALUE, value.toString());
        component.setSourceSet(immutableSortedSet(CODE_TYPE_1, CODE_TYPE_2));
        component.setLetUserCreateTags(false);
        component.setValue(value);
        final var options = new NotQuotableWrapper(
                "[" + DEFAULT_OPTIONS + ",{\"label\":\"identifier3\",\"value\":\"" + IDENTIFIER3_HEX + "\"}]");
        addDataAttributes(expected, component, options.getValue().toString());
        assertRenderResult(component, expected.getDocument(), facesContext);
    }

    @Test
    void shouldDecodeToNullIfNoValueIsSet(FacesContext facesContext) {
        final var component = new TagInputComponent();
        getRenderer().decode(facesContext, component);
        assertTrue(component.getSubmittedValue().toString().isEmpty());
    }

    @Test
    void shouldDecodeSingleValue(FacesContext facesContext, RequestConfigDecorator requestConfig) {
        final var component = new TagInputComponent();
        final Set<ConceptKeyType> sourceSet = immutableSet(CODE_TYPE_1, CODE_TYPE_2);
        component.setSourceSet(sourceSet);
        requestConfig.setRequestParameter(component.getClientId(), IDENTIFIER1_HEX);
        getRenderer().decode(facesContext, component);
        assertNotNull(component.getSubmittedValue());
        @SuppressWarnings("unchecked")
        final var submitted = (Set<CodeType>) component.getConvertedValue(facesContext,
                component.getSubmittedValue());
        assertEquals(1, submitted.size());
        assertEquals(CODE_TYPE_1, submitted.iterator().next());
    }

    @Test
    void shouldGetConvertedValueForMultipleValues(FacesContext facesContext, RequestConfigDecorator requestConfig) {
        final var component = new TagInputComponent();
        final Set<ConceptKeyType> sourceSet = immutableSet(CODE_TYPE_1, CODE_TYPE_2);
        component.setSourceSet(sourceSet);
        requestConfig.setRequestParameter(component.getClientId(),
                IDENTIFIER1_HEX + "," + IDENTIFIER2_HEX);
        getRenderer().decode(facesContext, component);
        assertNotNull(component.getSubmittedValue());
        @SuppressWarnings("unchecked")
        final var submitted = (Set<CodeType>) getRenderer().getConvertedValue(facesContext, component,
                component.getSubmittedValue());
        assertEquals(2, submitted.size());
        assertEquals(sourceSet, submitted);
    }

    @Test
    void shouldDecodeMultipleValues(FacesContext facesContext, RequestConfigDecorator requestConfig) {
        final var component = new TagInputComponent();
        final Set<ConceptKeyType> sourceSet = immutableSet(CODE_TYPE_1, CODE_TYPE_2);
        component.setSourceSet(sourceSet);
        requestConfig.setRequestParameter(component.getClientId(),
                IDENTIFIER1_HEX + "," + IDENTIFIER2_HEX);
        getRenderer().decode(facesContext, component);
        assertNotNull(component.getSubmittedValue());
        @SuppressWarnings("unchecked")
        final var submitted = (Set<CodeType>) component.getConvertedValue(facesContext,
                component.getSubmittedValue());
        assertEquals(2, submitted.size());
        assertEquals(sourceSet, submitted);
    }

    @Test
    void shouldDecodeUndefinedValue(FacesContext facesContext, RequestConfigDecorator requestConfig) {
        final var component = new TagInputComponent();
        final Set<ConceptKeyType> sourceSet = immutableSet(CODE_TYPE_1, CODE_TYPE_2);
        component.setSourceSet(sourceSet);
        component.setValue(immutableSet(CODE_TYPE_1, CODE_TYPE_2, CODE_TYPE_3_UNDEFINED));
        requestConfig.setRequestParameter(component.getClientId(),
                IDENTIFIER1_HEX + "," + Hex.encodeHexString("identifier3".getBytes()));
        getRenderer().decode(facesContext, component);
        assertNotNull(component.getSubmittedValue());
        @SuppressWarnings("unchecked")
        final var submitted = (Set<ConceptKeyType>) component.getConvertedValue(facesContext,
                component.getSubmittedValue());
        assertEquals(2, submitted.size());
        final var iterator = submitted.iterator();
        assertEquals(CODE_TYPE_1, iterator.next());
        assertEquals(CODE_TYPE_3_UNDEFINED, iterator.next());
    }

    @Test
    void shouldIgnoreEmptyIdentifier(FacesContext facesContext, RequestConfigDecorator requestConfig) {
        final var component = new TagInputComponent();
        final Set<ConceptKeyType> sourceSet = immutableSet(CODE_TYPE_1, CODE_TYPE_2);
        component.setSourceSet(sourceSet);
        requestConfig.setRequestParameter(component.getClientId(),
                IDENTIFIER1_HEX + "," + IDENTIFIER2_HEX + ",");
        getRenderer().decode(facesContext, component);
        assertNotNull(component.getSubmittedValue());
        @SuppressWarnings("unchecked")
        final var submitted = (Set<CodeType>) component.getConvertedValue(facesContext,
                component.getSubmittedValue());
        assertEquals(2, submitted.size());
        assertEquals(sourceSet, submitted);
    }

    @Test
    void shouldFailToDecodeEmptyClientCreated(FacesContext facesContext, RequestConfigDecorator requestConfig) {
        final var component = new TagInputComponent();
        requestConfig.setRequestParameter(component.getClientId(), Selectize.CLIENT_CREATED_SUFFIX);
        getRenderer().decode(facesContext, component);
        var submittedValue = component.getSubmittedValue();
        assertThrows(ConverterException.class,
                () -> component.getConvertedValue(facesContext, submittedValue));
    }

    @Test
    void shouldFailToDecodeNotAvailableTag(FacesContext facesContext, RequestConfigDecorator requestConfig) {
        final var component = new TagInputComponent();
        requestConfig.setRequestParameter(component.getClientId(), CODE_TYPE_1.getIdentifier());
        getRenderer().decode(facesContext, component);
        var submittedValue = component.getSubmittedValue();
        assertThrows(ConverterException.class,
                () -> component.getConvertedValue(facesContext, submittedValue));
    }

    @Test
    void shouldDecodeClientCreatedTagsSanitized(FacesContext facesContext, RequestConfigDecorator requestConfig) {
        final var component = new TagInputComponent();
        final var harmful = CODE_TYPE_CLIENT_CREATED.getIdentifier() + HARMFUL;
        requestConfig.setRequestParameter(component.getClientId(), harmful);
        getRenderer().decode(facesContext, component);
        component.setParent(facesContext.getViewRoot());
        component.validate(facesContext);
        assertNotNull(component.getValue());
        final Set<?> submitted = new HashSet<>(component.getValue());
        assertEquals(1, submitted.size());
        assertEquals(CODE_TYPE_CLIENT_CREATED, submitted.iterator().next());
    }

    @Test
    void shouldDecodeClientCreatedTags(FacesContext facesContext, RequestConfigDecorator requestConfig) throws IOException {
        final var component = new TagInputComponent();
        final Set<ConceptKeyType> sourceSet = immutableSet(CODE_TYPE_1, CODE_TYPE_2);
        component.setSourceSet(sourceSet);
        component.setLetUserCreateTags(false);
        requestConfig.setRequestParameter(component.getClientId(),
                CODE_TYPE_CLIENT_CREATED.getIdentifier());
        getRenderer().decode(facesContext, component);
        assertNotNull(component.getSubmittedValue());
        @SuppressWarnings("unchecked")
        final var submitted = (Set<ConceptKeyType>) component.getConvertedValue(facesContext,
                component.getSubmittedValue());
        assertEquals(1, submitted.size());
        assertEquals(CODE_TYPE_CLIENT_CREATED, submitted.iterator().next());
        // imitate validation and Update Model Values phase
        component.setValue(submitted);
        // client created should be set accordingly
        assertEquals(1, component.getClientCreated().size());
        assertEquals(CODE_TYPE_CLIENT_CREATED, component.getClientCreated().iterator().next());
        // For the next render the client created should be part options:
        final var expected = createInputNodeWithoutValue().withAttribute(AttributeName.VALUE, submitted.toString());
        final var options = "[" + DEFAULT_OPTIONS + ",{\"label\":\"hello\",\"value\":\"" + IDENTIFIER_HELLO_HEX
                + "\"}]";
        addDataAttributes(expected, component, options);
        assertRenderResult(component, expected.getDocument(), facesContext);
    }

    @Test
    void shouldConsiderMaxItems(FacesContext facesContext) throws IOException {
        final var component = new TagInputComponent();
        final var expected = createInputNodeWithoutValue();
        final int maxItems = numberGenerator.next();
        component.setMaxItems(maxItems);
        addDataAttributes(expected, component, null);
        assertRenderResult(component, expected.getDocument(), facesContext);
    }

    @Test
    void shouldConsiderRemoveButton(FacesContext facesContext) throws IOException {
        final var component = new TagInputComponent();
        final var expected = createInputNodeWithoutValue();
        component.setDisplayRemoveButton(false);
        addDataAttributes(expected, component, null);
        expected.withAttribute(PassthroughAttributes.REMOVE_BUTTON, "false");
        assertRenderResult(component, expected.getDocument(), facesContext);
    }

    @Test
    void shouldConvertNullValue(FacesContext facesContext) {
        final var result = new TagInputComponent().getConvertedValue(facesContext, null);
        assertInstanceOf(Collection.class, result);
        assertTrue(((Collection<?>) result).isEmpty());
    }

    private static HtmlTreeBuilder createInputNodeWithoutValue() {
        return new HtmlTreeBuilder().withNode(Node.INPUT).withAttribute(AttributeName.TYPE, AttributeValue.INPUT_TEXT)
                .withAttribute(AttributeName.ID, CLIENT_ID).withAttribute(AttributeName.NAME, CLIENT_ID);
    }

    private static void addDataAttributes(final HtmlTreeBuilder htmlTreeBuilder, final TagInputComponent component,
            final String options) {
        htmlTreeBuilder.withAttribute(PassthroughAttributes.PERSIST, "false")
                .withAttribute(PassthroughAttributes.REMOVE_BUTTON, "true")
                .withAttribute(PassthroughAttributes.SELECTIZE, String.valueOf(!component.isDisabled()))
                .withAttribute(PassthroughAttributes.WRAPPER_CLASS,
                        component.isLetUserCreateTags()
                                ? TagInputComponent.CSS_CLASS_CAN_CREATE + " " + Selectize.OPTION_VALUE_DEFAULT_WRAPPER
                                : TagInputComponent.CSS_CLASS_CANNOT_CREATE + " "
                                + Selectize.OPTION_VALUE_DEFAULT_WRAPPER)
                .withAttribute(PassthroughAttributes.CAN_CREATE, String.valueOf(component.isLetUserCreateTags()))
                .withAttribute(PassthroughAttributes.DELIMITER, component.getDelimiter())
                .withAttribute(PassthroughAttributes.MAX_ITEMS, String.valueOf(component.getMaxItems()))
                .withAttribute(PassthroughAttributes.OPTIONS, MoreStrings.isEmpty(options) ? "[]" : options);
    }

    @Override
    protected UIComponent getComponent() {
        return new TagInputComponent();
    }

    @BeforeEach
    void setUp(ComponentConfigDecorator decorator) {
        decorator.registerMockRenderer(BootstrapFamily.COMPONENT_FAMILY, BootstrapFamily.TAG_COMPONENT_RENDERER);
        decorator.registerConverter(TestConverterWithException.class);
    }
}
