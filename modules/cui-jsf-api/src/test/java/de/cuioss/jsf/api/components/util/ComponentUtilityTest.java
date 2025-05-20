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
package de.cuioss.jsf.api.components.util;

import static de.cuioss.tools.collect.CollectionLiterals.immutableMap;
import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import de.cuioss.test.jsf.junit5.EnableJsfEnvironment;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.html.HtmlForm;
import jakarta.faces.component.html.HtmlInputText;
import jakarta.faces.component.html.HtmlPanelGroup;
import jakarta.faces.context.FacesContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

@EnableJsfEnvironment
@DisplayName("Tests for ComponentUtility")
class ComponentUtilityTest {

    private static final String FORM_COMPONENT = "jakarta.faces.HtmlForm";

    private static final String COMPONENT_ID = "that_is_me";

    private static final String NOT_THERE_COMPONENT_ID = "not:there";

    @Nested
    @DisplayName("Tests for request type detection")
    class RequestTypeTests {

        @Test
        @DisplayName("Should correctly identify self requests")
        void shouldIdentifySelfRequests() {
            // Arrange
            final UIComponent component = new HtmlInputText();
            component.setId(COMPONENT_ID);
            Map<String, String> map = immutableMap(ComponentUtility.JAVAX_FACES_SOURCE, COMPONENT_ID);

            // Act & Assert - matching component ID
            assertTrue(ComponentUtility.isSelfRequest(map, component),
                    "Should identify request as self request when component ID matches");

            // Arrange - different component ID
            map = immutableMap(ComponentUtility.JAVAX_FACES_SOURCE, NOT_THERE_COMPONENT_ID);

            // Act & Assert - non-matching component ID
            assertFalse(ComponentUtility.isSelfRequest(map, component),
                    "Should not identify request as self request when component ID doesn't match");
        }

        @Test
        @DisplayName("Should correctly identify AJAX requests")
        void shouldIdentifyAjaxRequests() {
            // Arrange - AJAX parameter set to true
            Map<String, String> map = immutableMap(ComponentUtility.JAVAX_FACES_PARTIAL_AJAX, Boolean.TRUE.toString());

            // Act & Assert - AJAX parameter true
            assertTrue(ComponentUtility.isAjaxRequest(map),
                    "Should identify request as AJAX when partial AJAX parameter is true");

            // Arrange - AJAX parameter set to false
            map = immutableMap(ComponentUtility.JAVAX_FACES_PARTIAL_AJAX, Boolean.FALSE.toString());

            // Act & Assert - AJAX parameter false
            assertFalse(ComponentUtility.isAjaxRequest(map),
                    "Should not identify request as AJAX when partial AJAX parameter is false");

            // Arrange - AJAX parameter not set
            map = new HashMap<>();

            // Act & Assert - AJAX parameter missing
            assertFalse(ComponentUtility.isAjaxRequest(map),
                    "Should not identify request as AJAX when partial AJAX parameter is missing");
        }
    }

    @Nested
    @DisplayName("Tests for component hierarchy navigation")
    class ComponentHierarchyTests {

        @Test
        @DisplayName("Should find corresponding form in component hierarchy")
        void shouldFindCorrespondingForm() {
            // Arrange - create component hierarchy
            final var form = new HtmlForm();
            final var panel = new HtmlPanelGroup();
            final UIComponent input = new HtmlInputText();
            panel.getChildren().add(input);
            form.getChildren().add(panel);

            // Act & Assert - find form from input
            assertEquals(form, ComponentUtility.findCorrespondingForm(input),
                    "Should find form from nested input component");

            // Act & Assert - find form from panel
            assertEquals(form, ComponentUtility.findCorrespondingForm(panel),
                    "Should find form from direct child panel");

            // Act & Assert - find form from form itself
            assertEquals(form, ComponentUtility.findCorrespondingForm(form),
                    "Should return form when component is the form itself");

            // Arrange - component not in hierarchy
            var component = new HtmlPanelGroup();

            // Act & Assert - component not in hierarchy
            assertThrows(IllegalArgumentException.class, () -> ComponentUtility.findCorrespondingForm(component),
                    "Should throw IllegalArgumentException when component is not in form hierarchy");

            // Act & Assert - null component
            assertThrows(IllegalArgumentException.class, () -> ComponentUtility.findCorrespondingForm(null),
                    "Should throw IllegalArgumentException when component is null");
        }

        @Test
        @DisplayName("Should find corresponding form or return null")
        void shouldFindCorrespondingFormOrNull() {
            // Arrange - create component hierarchy
            final var form = new HtmlForm();
            final var panel = new HtmlPanelGroup();
            final UIComponent input = new HtmlInputText();
            panel.getChildren().add(input);
            form.getChildren().add(panel);

            // Act & Assert - find form from input
            assertEquals(form, ComponentUtility.findCorrespondingFormOrNull(input),
                    "Should find form from nested input component");

            // Act & Assert - find form from panel
            assertEquals(form, ComponentUtility.findCorrespondingFormOrNull(panel),
                    "Should find form from direct child panel");

            // Act & Assert - find form from form itself
            assertEquals(form, ComponentUtility.findCorrespondingFormOrNull(form),
                    "Should return form when component is the form itself");

            // Act & Assert - component not in hierarchy
            assertNull(ComponentUtility.findCorrespondingFormOrNull(new HtmlPanelGroup()),
                    "Should return null when component is not in form hierarchy");

            // Act & Assert - null component
            assertNull(ComponentUtility.findCorrespondingFormOrNull(null),
                    "Should return null when component is null");
        }

        @Test
        @DisplayName("Should find nearest naming container in hierarchy")
        void shouldFindNearestNamingContainer() {
            // Arrange - create component hierarchy
            final var form = new HtmlForm();
            final var panel = new HtmlPanelGroup();
            final UIComponent input = new HtmlInputText();
            panel.getChildren().add(input);
            form.getChildren().add(panel);

            // Act & Assert - find container from input
            assertEquals(form, ComponentUtility.findNearestNamingContainer(input),
                    "Should find naming container from nested input component");

            // Act & Assert - find container from panel
            assertEquals(form, ComponentUtility.findNearestNamingContainer(panel),
                    "Should find naming container from direct child panel");

            // Act & Assert - find container from container itself
            assertEquals(form, ComponentUtility.findNearestNamingContainer(form),
                    "Should return container when component is the container itself");

            // Arrange - component not in hierarchy
            var component = new HtmlPanelGroup();

            // Act & Assert - component not in hierarchy
            assertThrows(IllegalArgumentException.class, () -> ComponentUtility.findNearestNamingContainer(component),
                    "Should throw IllegalArgumentException when component is not in container hierarchy");

            // Act & Assert - null component
            assertThrows(IllegalArgumentException.class, () -> ComponentUtility.findNearestNamingContainer(null),
                    "Should throw IllegalArgumentException when component is null");
        }

        @Test
        @DisplayName("Should determine if component is within a form")
        void shouldDetermineIfComponentIsInForm() {
            // Arrange - create component in form
            final var form = new HtmlForm();
            final UIComponent input = new HtmlInputText();
            form.getChildren().add(input);

            // Act & Assert - component in form
            assertTrue(ComponentUtility.isInForm(input),
                    "Should return true when component is within a form");

            // Arrange - component not in form
            final UIComponent input2 = new HtmlInputText();

            // Act & Assert - component not in form
            assertFalse(ComponentUtility.isInForm(input2),
                    "Should return false when component is not within a form");

            // Act & Assert - null component
            assertFalse(ComponentUtility.isInForm(null),
                    "Should return false when component is null");
        }
    }

    @Nested
    @DisplayName("Tests for component creation")
    class ComponentCreationTests {

        @Test
        @DisplayName("Should create component from component type")
        void shouldCreateComponent(FacesContext facesContext, ComponentConfigDecorator componentConfig) {
            // Arrange
            componentConfig.registerUIComponent(FORM_COMPONENT, HtmlForm.class);

            // Act
            final var form = ComponentUtility.createComponent(facesContext, FORM_COMPONENT);

            // Assert
            assertNotNull(form, "Created component should not be null");
        }

        @Test
        @DisplayName("Should create renderer from family and renderer type")
        void shouldCreateRenderer(FacesContext facesContext, ComponentConfigDecorator componentConfig) {
            // Arrange
            componentConfig.registerMockRenderer("family", "jakarta.faces.Form");

            // Act
            final var renderer = ComponentUtility.createRenderer(facesContext, "family", "jakarta.faces.Form");

            // Assert
            assertNotNull(renderer, "Created renderer should not be null");
        }
    }

    @Nested
    @DisplayName("Tests for EditableValueHolder operations")
    class EditableValueHolderTests {

        @Test
        @DisplayName("Should reset editable value holders without exceptions")
        void shouldResetEditableValueHolders(FacesContext facesContext) {
            // Arrange
            final var form = createFormWithEditableValueHolder();

            // Act & Assert
            assertDoesNotThrow(() -> ComponentUtility.resetEditableValueHolder(form, facesContext),
                    "Should reset editable value holders without throwing exceptions");
        }

        @Test
        @DisplayName("Should set editable value holders valid without exceptions")
        void shouldSetEditableValueHoldersValid(FacesContext facesContext) {
            // Arrange
            final var form = createFormWithEditableValueHolder();

            // Act & Assert
            assertDoesNotThrow(() -> ComponentUtility.resetEditableValueHolder(form, facesContext),
                    "Should set editable value holders valid without throwing exceptions");
        }
    }

    private HtmlForm createFormWithEditableValueHolder() {
        final var form = new HtmlForm();
        final var inputText1 = new HtmlInputText();
        inputText1.setValid(true);
        inputText1.setValue(COMPONENT_ID);
        inputText1.setParent(form);
        final var inputText2 = new HtmlInputText();
        inputText1.setValid(false);
        inputText2.setValue(COMPONENT_ID);
        inputText2.setParent(form);
        return form;
    }

    @BeforeEach
    void configureComponents(final ComponentConfigDecorator decorator) {
        decorator.registerMockRendererForHtmlInputText();
    }
}