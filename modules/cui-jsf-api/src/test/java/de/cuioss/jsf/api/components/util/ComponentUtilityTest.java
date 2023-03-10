package de.cuioss.jsf.api.components.util;

import static de.cuioss.tools.collect.CollectionLiterals.immutableMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.HashMap;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlForm;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlPanelGroup;

import org.junit.jupiter.api.Test;

import de.cuioss.test.jsf.config.ComponentConfigurator;
import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import de.cuioss.test.jsf.junit5.JsfEnabledTestEnvironment;

class ComponentUtilityTest extends JsfEnabledTestEnvironment implements ComponentConfigurator {

    private static final String FORM_COMPONENT = "javax.faces.HtmlForm";

    private static final String COMPONENT_ID = "that_is_me";

    private static final String NOT_THERE_COMPONENT_ID = "not:there";

    @Test
    void testIsSelfRequest() {
        final UIComponent component = new HtmlInputText();
        component.setId(COMPONENT_ID);
        Map<String, String> map = immutableMap(ComponentUtility.JAVAX_FACES_SOURCE, COMPONENT_ID);
        assertTrue(ComponentUtility.isSelfRequest(map, component));
        map = immutableMap(ComponentUtility.JAVAX_FACES_SOURCE, NOT_THERE_COMPONENT_ID);
        assertFalse(ComponentUtility.isSelfRequest(map, component));
    }

    @Test
    void testIsAjaxRequest() {
        Map<String, String> map = immutableMap(ComponentUtility.JAVAX_FACES_PARTIAL_AJAX, Boolean.TRUE.toString());
        assertTrue(ComponentUtility.isAjaxRequest(map));
        map = immutableMap(ComponentUtility.JAVAX_FACES_PARTIAL_AJAX, Boolean.FALSE.toString());
        assertFalse(ComponentUtility.isAjaxRequest(map));
        map = new HashMap<>();
        assertFalse(ComponentUtility.isAjaxRequest(map));
    }

    @Test
    void testFindCorrespondingForm() {
        final var form = new HtmlForm();
        final var panel = new HtmlPanelGroup();
        final UIComponent input = new HtmlInputText();
        panel.getChildren().add(input);
        form.getChildren().add(panel);
        assertEquals(form, ComponentUtility.findCorrespondingForm(input));
        assertEquals(form, ComponentUtility.findCorrespondingForm(panel));
        assertEquals(form, ComponentUtility.findCorrespondingForm(form));
        try {
            ComponentUtility.findCorrespondingForm(new HtmlPanelGroup());
            fail("Should have thrown exception");
        } catch (final IllegalArgumentException e) {
            // Expected
        }
        try {
            ComponentUtility.findCorrespondingForm(null);
            fail("Should have thrown exception");
        } catch (final IllegalArgumentException e) {
            // Expected
        }
    }

    @Test
    void testFindCorrespondingFormOrNull() {
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
    void testFindNearestNamingContainer() {
        final var form = new HtmlForm();
        final var panel = new HtmlPanelGroup();
        final UIComponent input = new HtmlInputText();
        panel.getChildren().add(input);
        form.getChildren().add(panel);
        assertEquals(form, ComponentUtility.findNearestNamingContainer(input));
        assertEquals(form, ComponentUtility.findNearestNamingContainer(panel));
        assertEquals(form, ComponentUtility.findNearestNamingContainer(form));
        try {
            ComponentUtility.findNearestNamingContainer(new HtmlPanelGroup());
            fail("Should have thrown exception");
        } catch (final IllegalArgumentException e) {
            // Expected
        }
        try {
            ComponentUtility.findNearestNamingContainer(null);
            fail("Should have thrown exception");
        } catch (final IllegalArgumentException e) {
            // Expected
        }
    }

    @Test
    void shouldCreateComponent() {
        getComponentConfigDecorator().registerUIComponent(FORM_COMPONENT, HtmlForm.class);
        final var form = ComponentUtility.createComponent(getFacesContext(), FORM_COMPONENT);
        assertNotNull(form);
    }

    @Test
    void shouldCreateRenderer() {
        getComponentConfigDecorator().registerMockRenderer("family", "javax.faces.Form");
        final var renderer =
            ComponentUtility.createRenderer(getFacesContext(), "family", "javax.faces.Form");
        assertNotNull(renderer);
    }

    @Test
    void testResetEditiableValueHolder() {
        final var form = createFormWithEditableValueHolder();
        ComponentUtility.resetEditableValueHolder(form, getFacesContext());
    }

    @Test
    void testSetEditiableValueHoldersValid() {
        final var form = createFormWithEditableValueHolder();
        ComponentUtility.resetEditableValueHolder(form, getFacesContext());
    }

    @Test
    void testIsInForm() {
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