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
package de.cuioss.jsf.bootstrap.tag.support;

import static de.cuioss.jsf.bootstrap.selectize.Selectize.CLIENT_CREATED_SUFFIX;
import static de.cuioss.tools.collect.CollectionLiterals.*;
import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.jsf.api.components.javascript.JavaScriptOptions;
import de.cuioss.jsf.bootstrap.support.ConceptKeyTypeGenerator;
import de.cuioss.jsf.bootstrap.taginput.ConceptKeyStringConverter;
import de.cuioss.jsf.bootstrap.taginput.TagInputComponent;
import de.cuioss.jsf.bootstrap.taginput.TestConceptKey;
import de.cuioss.jsf.bootstrap.taginput.TestTagItemConverter;
import de.cuioss.test.jsf.converter.AbstractConverterTest;
import de.cuioss.test.jsf.converter.TestItems;
import de.cuioss.tools.codec.Hex;
import de.cuioss.tools.string.Joiner;
import de.cuioss.uimodel.model.conceptkey.ConceptKeyType;
import de.cuioss.uimodel.model.conceptkey.impl.BaseConceptCategory;
import de.cuioss.uimodel.model.conceptkey.impl.ConceptKeyTypeImpl;
import de.cuioss.uimodel.nameprovider.I18nDisplayNameProvider;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.ConverterException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@DisplayName("Tests for ConceptKeyStringConverter")
class ConceptKeyStringConverterTest
        extends AbstractConverterTest<ConceptKeyStringConverter, Collection<ConceptKeyType>> {

    private static final String DELIMITER = JavaScriptOptions.OPTION_VALUE_DELIMITER;

    private static final String IDENTIFIER1 = "test1";

    private static final String IDENTIFIER2 = "test2";

    private static final String IDENTIFIER1_HEX = Hex.encodeHexString(IDENTIFIER1.getBytes());

    private static final String IDENTIFIER2_HEX = Hex.encodeHexString(IDENTIFIER2.getBytes());

    private final ConceptKeyTypeGenerator conceptKeyTypeGenerator = new ConceptKeyTypeGenerator();

    private static final ConceptKeyType IDENTIFIER1_CONCEPT_KEY = ConceptKeyTypeImpl.builder().identifier(IDENTIFIER1)
            .labelResolver(new I18nDisplayNameProvider(IDENTIFIER1)).category(new BaseConceptCategory()).build();

    private static final ConceptKeyType IDENTIFIER2_CONCEPT_KEY = ConceptKeyTypeImpl.builder().identifier(IDENTIFIER2)
            .labelResolver(new I18nDisplayNameProvider(IDENTIFIER2)).category(new BaseConceptCategory()).build();

    private static final ConceptKeyType IDENTIFIER1_CLIENT_CREATED_CONCEPT_KEY = ConceptKeyTypeImpl.builder()
            .identifier(CLIENT_CREATED_SUFFIX + IDENTIFIER1).labelResolver(new I18nDisplayNameProvider(IDENTIFIER1))
            .category(new MissingTagConceptKeyCategory()).build();

    @Override
    public void populate(final TestItems<Collection<ConceptKeyType>> testItems) {
        // No roundtrip values sensible here
    }

    @Nested
    @DisplayName("Tests for converting to string")
    class StringConversionTests {

        @Test
        @DisplayName("Should convert known keys to string with custom delimiter")
        void shouldConvertKnownKeysToString(FacesContext facesContext) {
            // Arrange
            final var component = new TagInputComponent();
            final Set<ConceptKeyType> items = immutableSet(IDENTIFIER1_CONCEPT_KEY, IDENTIFIER2_CONCEPT_KEY);
            component.setDelimiter(" # ");
            component.setSourceSet(items);

            // Act
            final var result = getConverter().getAsString(facesContext, component, items);

            // Assert
            assertEquals(IDENTIFIER1_HEX + " # " + IDENTIFIER2_HEX, result,
                    "Should convert keys using custom delimiter");
        }

        @Test
        @DisplayName("Should convert unknown keys to string with default delimiter")
        void shouldConvertUnknownKeysToString(FacesContext facesContext) {
            // Arrange
            final var component = new TagInputComponent();
            final Collection<ConceptKeyType> items = immutableList(IDENTIFIER1_CONCEPT_KEY, IDENTIFIER2_CONCEPT_KEY);
            component.setSourceSet(null);

            // Act
            final var result = getConverter().getAsString(facesContext, component, items);

            // Assert
            assertEquals(IDENTIFIER1_HEX + DELIMITER + IDENTIFIER2_HEX, result,
                    "Should convert keys using default delimiter");
        }
    }

    @Nested
    @DisplayName("Tests for converting to object")
    class ObjectConversionTests {

        @Test
        @DisplayName("Should throw exception when converting string with unknown values")
        void shouldFailConvertingStringOfUnknownValues(FacesContext facesContext) {
            // Arrange
            final var component = new TagInputComponent();
            final var value = IDENTIFIER1_CONCEPT_KEY + DELIMITER + IDENTIFIER2_CONCEPT_KEY;
            var converter = getConverter();

            // Act & Assert
            assertThrows(ConverterException.class, () -> converter.getAsObject(facesContext, component, value),
                    "Should throw ConverterException for unknown values");
        }

        @Test
        @DisplayName("Should convert string to objects from source set")
        void shouldConvertStringFromSourceSet(FacesContext facesContext) {
            // Arrange
            final var component = new TagInputComponent();
            final var value = IDENTIFIER1_HEX + DELIMITER + IDENTIFIER2_HEX;
            component.setSourceSet(immutableSet(IDENTIFIER1_CONCEPT_KEY, IDENTIFIER2_CONCEPT_KEY));

            // Act
            final Collection<ConceptKeyType> result = getConverter().getAsObject(facesContext, component, value);

            // Assert
            assertTrue(result.containsAll(mutableList(IDENTIFIER1_CONCEPT_KEY, IDENTIFIER2_CONCEPT_KEY)),
                    "Result should contain all expected concept keys");
        }

        @Test
        @DisplayName("Should convert string to objects from client created set")
        void shouldConvertStringFromClientCreated(FacesContext facesContext) {
            // Arrange
            final var component = new TagInputComponent();
            final var value = CLIENT_CREATED_SUFFIX + IDENTIFIER1;
            component.setClientCreated(immutableSet(IDENTIFIER1_CLIENT_CREATED_CONCEPT_KEY));

            // Act
            final Collection<ConceptKeyType> result = getConverter().getAsObject(facesContext, component, value);

            // Assert
            assertTrue(result.containsAll(mutableList(IDENTIFIER1_CLIENT_CREATED_CONCEPT_KEY)),
                    "Result should contain client created concept key");
            assertTrue(component.getClientCreated().containsAll(mutableList(IDENTIFIER1_CLIENT_CREATED_CONCEPT_KEY)),
                    "Component's client created set should contain the concept key");
        }

        @Test
        @DisplayName("Should convert hex encoded string from client created transient view")
        void shouldConvertStringFromClientCreatedTransientView(FacesContext facesContext) {
            // Arrange
            final var component = new TagInputComponent();
            final var value = Hex.encodeHexString((CLIENT_CREATED_SUFFIX + IDENTIFIER1).getBytes());

            // Act
            final Collection<ConceptKeyType> result = getConverter().getAsObject(facesContext, component, value);

            // Assert
            assertTrue(result.containsAll(mutableList(IDENTIFIER1_CLIENT_CREATED_CONCEPT_KEY)),
                    "Result should contain client created concept key");
            assertTrue(component.getClientCreated().containsAll(mutableList(IDENTIFIER1_CLIENT_CREATED_CONCEPT_KEY)),
                    "Component's client created set should contain the concept key");
        }
    }

    @Nested
    @DisplayName("Tests for item converter integration")
    class ItemConverterTests {

        @Test
        @DisplayName("Should not use item converter when converting to string")
        void shouldNotUseItemConverterToString(FacesContext facesContext) {
            // Arrange
            final var ck1 = conceptKeyTypeGenerator.next();
            final var ck2 = conceptKeyTypeGenerator.next();
            final List<ConceptKeyType> ckTypes = immutableList(ck1, ck2, IDENTIFIER1_CLIENT_CREATED_CONCEPT_KEY);
            facesContext.getApplication().addConverter(TestTagItemConverter.ID, TestTagItemConverter.class.getName());
            final var component = new TagInputComponent();
            component.setClientCreated(immutableSet(IDENTIFIER1_CLIENT_CREATED_CONCEPT_KEY));
            component.setItemConverterId(TestTagItemConverter.ID);

            // Act
            final var result = getConverter().getAsString(facesContext, component, ckTypes);

            // Assert
            final var ck1Hex = Hex.encodeHexString(ck1.getIdentifier().getBytes());
            final var ck2Hex = Hex.encodeHexString(ck2.getIdentifier().getBytes());
            assertEquals(Joiner.on(',').join(ck1Hex, ck2Hex, "5f636c69656e745f637265617465645f7465737431"), result,
                    "Should convert to string without using item converter");
        }

        @Test
        @DisplayName("Should use item converter when converting to object")
        void shouldUseItemConverterToObject(FacesContext facesContext) {
            // Arrange
            facesContext.getApplication().addConverter(TestTagItemConverter.ID, TestTagItemConverter.class.getName());
            final var component = new TagInputComponent();
            component.setItemConverterId(TestTagItemConverter.ID);

            // Act
            final Set<ConceptKeyType> result = getConverter().getAsObject(facesContext, component,
                    "5f636c69656e745f637265617465645f7465737431");

            // Assert
            assertNotNull(result, "TestConceptKey expected instead of null when using item converter");
            assertInstanceOf(TestConceptKey.class, result.iterator().next(),
                    "Result should contain TestConceptKey instance");
            assertEquals(1, component.getClientCreated().size(),
                    "Component should have one client created item");
        }
    }
}
