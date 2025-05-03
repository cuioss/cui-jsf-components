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

import de.cuioss.test.jsf.config.ComponentConfigurator;
import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import de.cuioss.test.jsf.junit5.JsfEnabledTestEnvironment;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.html.HtmlForm;
import jakarta.faces.component.html.HtmlInputText;
import jakarta.faces.component.html.HtmlPanelGroup;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class ComponentUtilityTest extends JsfEnabledTestEnvironment implements ComponentConfigurator {

    private static final String FORM_COMPONENT = "jakarta.faces.HtmlForm";

    private static final String COMPONENT_ID = "that_is_me";

    private static final String NOT_THERE_COMPONENT_ID = "not:there";

    @Test
    void isSelfRequest() {
        final UIComponent component = new HtmlInputText();
        component.setId(COMPONENT_ID);
        Map<String, String> map = immutableMap(ComponentUtility.JAVAX_FACES_SOURCE, COMPONENT_ID);
        assertTrue(ComponentUtility.isSelfRequest(map, component));
        map = immutableMap(ComponentUtility.JAVAX_FACES_SOURCE, NOT_THERE_COMPONENT_ID);
        assertFalse(ComponentUtility.isSelfRequest(map, component));
    }

    @Test
    void isAjaxRequest() {
        Map<String, String> map = immutableMap(ComponentUtility.JAVAX_FACES_PARTIAL_AJAX, Boolean.TRUE.toString());
        assertTrue(ComponentUtility.isAjaxRequest(map));
        map = immutableMap(ComponentUtility.JAVAX_FACES_PARTIAL_AJAX, Boolean.FALSE.toString());
        assertFalse(ComponentUtility.isAjaxRequest(map));
        map = new HashMap<>();
        assertFalse(ComponentUtility.isAjaxRequest(map));
    }

    @Test
    void findCorrespondingForm() {
        final var form = new HtmlForm();
        final var panel = new HtmlPanelGroup();
        final UIComponent input = new HtmlInputText();
        panel.getChildren().add(input);
        form.getChildren().add(panel);
        assertEquals(form, ComponentUtility.findCorrespondingForm(input));
        assertEquals(form, ComponentUtility.findCorrespondingForm(panel));
        assertEquals(form, ComponentUtility.findCorrespondingForm(form));
        var component = new HtmlPanelGroup();
        assertThrows(IllegalArgumentException.class, () -> ComponentUtility.findCorrespondingForm(component));
        assertThrows(IllegalArgumentException.class, () -> ComponentUtility.findCorrespondingForm(null));
    }

    @Test
    void findCorrespondingFormOrNull() {
        final var form = new HtmlForm();
        final var panel = new HtmlPanelGroup();
        final UIComponent input = new HtmlInputText();
        panel.getChildren().add(input);
        form.getChildren().add(panel);
        assertEquals(form, ComponentUtility.findCorrespondingFormOrNull(input));
        assertEquals(form, ComponentUtility.findCorrespondingFormOrNull(panel));
        assertEquals(form, ComponentUtility.findCorrespondingFormOrNull(form));
        assertNull(ComponentUtility.findCorrespondingFormOrNull(new HtmlPanelGroup()));
        assertNull(ComponentUtility.findCorrespondingFormOrNull(null));
    }

    @Test
    void findNearestNamingContainer() {
        final var form = new HtmlForm();
        final var panel = new HtmlPanelGroup();
        final UIComponent input = new HtmlInputText();
        panel.getChildren().add(input);
        form.getChildren().add(panel);
        assertEquals(form, ComponentUtility.findNearestNamingContainer(input));
        assertEquals(form, ComponentUtility.findNearestNamingContainer(panel));
        assertEquals(form, ComponentUtility.findNearestNamingContainer(form));
        var component = new HtmlPanelGroup();
        assertThrows(IllegalArgumentException.class, () -> ComponentUtility.findNearestNamingContainer(component));
        assertThrows(IllegalArgumentException.class, () -> ComponentUtility.findNearestNamingContainer(null));
    }

    @Test
    void shouldCreateComponent() {
        getComponentConfigDecorator().registerUIComponent(FORM_COMPONENT, HtmlForm.class);
        final var form = ComponentUtility.createComponent(getFacesContext(), FORM_COMPONENT);
        assertNotNull(form);
    }

    @Test
    void shouldCreateRenderer() {
        getComponentConfigDecorator().registerMockRenderer("family", "jakarta.faces.Form");
        final var renderer = ComponentUtility.createRenderer(getFacesContext(), "family", "jakarta.faces.Form");
        assertNotNull(renderer);
    }

    @Test
    void resetEditableValueHolder() {
        final var form = createFormWithEditableValueHolder();
        assertDoesNotThrow(() -> ComponentUtility.resetEditableValueHolder(form, getFacesContext()));
    }

    @Test
    void setEditableValueHoldersValid() {
        final var form = createFormWithEditableValueHolder();
        assertDoesNotThrow(() -> ComponentUtility.resetEditableValueHolder(form, getFacesContext()));
    }

    @Test
    void isInForm() {
        final var form = new HtmlForm();
        final UIComponent input = new HtmlInputText();
        form.getChildren().add(input);
        assertTrue(ComponentUtility.isInForm(input));
        final UIComponent input2 = new HtmlInputText();
        assertFalse(ComponentUtility.isInForm(input2));
        assertFalse(ComponentUtility.isInForm(null));
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

    @Override
    public void configureComponents(final ComponentConfigDecorator decorator) {
        decorator.registerMockRendererForHtmlInputText();
    }
}
