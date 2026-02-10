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
package de.cuioss.jsf.bootstrap.taginput;

import static de.cuioss.jsf.bootstrap.taginput.TagInputRendererTest.CODE_TYPE_1;
import static de.cuioss.jsf.bootstrap.taginput.TagInputRendererTest.CODE_TYPE_2;
import static de.cuioss.tools.collect.CollectionLiterals.immutableSortedSet;
import static org.junit.jupiter.api.Assertions.assertThrows;

import de.cuioss.jsf.bootstrap.common.logging.BootstrapLogMessages;
import de.cuioss.test.jsf.converter.AbstractConverterTest;
import de.cuioss.test.jsf.converter.TestItems;
import de.cuioss.test.juli.LogAsserts;
import de.cuioss.test.juli.TestLogLevel;
import de.cuioss.test.juli.junit5.EnableTestLogger;
import de.cuioss.uimodel.model.conceptkey.ConceptKeyType;
import jakarta.faces.component.html.HtmlInputText;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.ConverterException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

@EnableTestLogger
class ConceptKeyStringConverterTest extends AbstractConverterTest<ConceptKeyStringConverter, Set<ConceptKeyType>> {

    public ConceptKeyStringConverterTest() {
        setComponent(new TagInputComponent());
    }

    @Override
    public void populate(final TestItems<Set<ConceptKeyType>> testItems) {
        testItems.addValidObjectWithStringResult(immutableSortedSet(CODE_TYPE_1), "6964656e74696669657231");
        testItems.addValidObjectWithStringResult(immutableSortedSet(CODE_TYPE_1, CODE_TYPE_2),
                "6964656e74696669657231,6964656e74696669657232");
        testItems.addValidObject(Set.of(new TestConceptKey()));
    }

    @Test
    @DisplayName("Should log error for invalid component type")
    void shouldLogErrorForInvalidComponentType(FacesContext facesContext) {
        // Arrange
        var converter = new ConceptKeyStringConverter();

        // Act & Assert
        var invalidComponent = new HtmlInputText();
        assertThrows(ConverterException.class,
                () -> converter.getAsObject(facesContext, invalidComponent, "someValue"));
        LogAsserts.assertSingleLogMessagePresentContaining(TestLogLevel.ERROR,
                BootstrapLogMessages.ERROR.INVALID_COMPONENT_TYPE.resolveIdentifierString());
    }
}
