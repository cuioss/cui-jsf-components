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
package de.cuioss.jsf.components.inlineconfirm;

import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.jsf.api.components.support.DummyComponent;
import de.cuioss.jsf.components.CuiFamily;
import de.cuioss.test.jsf.component.AbstractComponentTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for InlineConfirmComponent")
class InlineConfirmComponentTest extends AbstractComponentTest<InlineConfirmComponent> {

    @Test
    @DisplayName("Should provide correct component metadata")
    void shouldProvideMetadata() {
        // Arrange
        var component = anyComponent();

        // Act & Assert - Check component family
        assertEquals(CuiFamily.COMPONENT_FAMILY, component.getFamily(),
                "Component should have the correct component family");

        // Act & Assert - Check renderer type
        assertEquals(CuiFamily.INLINE_CONFIRM_RENDERER, component.getRendererType(),
                "Component should have the correct renderer type");
    }

    @Test
    @DisplayName("Should throw exception when initial facet is missing")
    void shouldFailWithMissingFacet() {
        // Arrange
        var component = anyComponent();

        // Act & Assert
        assertThrows(IllegalArgumentException.class, component::getInitialFacet,
                "Component should throw IllegalArgumentException when initial facet is missing");
    }

    @Test
    @DisplayName("Should throw exception when child component is missing")
    void shouldFailWithMissingChild() {
        // Arrange
        var component = anyComponent();

        // Act & Assert
        assertThrows(IllegalArgumentException.class, component::getChildAsModifier,
                "Component should throw IllegalArgumentException when child component is missing");
    }

    @Test
    @DisplayName("Should correctly handle child and facet components")
    void shouldHandleChildAndFacet() {
        // Arrange
        var underTest = anyComponent();
        underTest.getFacets().put(InlineConfirmComponent.INITIAL_FACET_NAME, new DummyComponent());
        underTest.getChildren().add(new DummyComponent());

        // Act & Assert - Check initial facet
        assertNotNull(underTest.getInitialFacet(),
                "Component should return the initial facet when it exists");

        // Act & Assert - Check child component
        assertNotNull(underTest.getChildAsModifier(),
                "Component should return the child component when it exists");
    }
}
