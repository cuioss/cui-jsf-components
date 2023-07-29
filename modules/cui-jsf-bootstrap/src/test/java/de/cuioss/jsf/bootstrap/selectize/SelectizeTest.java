package de.cuioss.jsf.bootstrap.selectize;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlForm;
import javax.faces.component.html.HtmlInputText;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.components.util.ComponentWrapper;
import de.cuioss.test.jsf.config.ComponentConfigurator;
import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import de.cuioss.test.jsf.junit5.JsfEnabledTestEnvironment;

class SelectizeTest extends JsfEnabledTestEnvironment implements ComponentConfigurator {

    private static final String KEY1 = "key1";

    private static final String KEY2 = "key2";

    private static final String EXTENSION = "extension";

    private ComponentWrapper<UIComponent> componentWrapper;

    @BeforeEach
    void setUpBefore() {
        var form = new HtmlForm();
        var wrapped = new HtmlInputText();
        wrapped.setId("mock");
        form.setId("form");
        form.getChildren().add(wrapped);
        componentWrapper = new ComponentWrapper<>(wrapped);
    }

    @Test
    void shouldCreateMinimalSelectize() {
        assertEquals("jQuery('#form\\\\:mock').selectize();",
                Selectize.builder().withComponentWrapper(componentWrapper).build().script());
    }

    @Test
    void shouldCreateSelectizeWithExtension() {
        assertEquals("jQuery('#form\\\\:mock_extension').selectize();",
                Selectize.builder().withComponentWrapper(componentWrapper).withIdExtension(EXTENSION).build().script());
    }

    @Test
    void shouldCreateSelectizeWithOption() {
        assertEquals("jQuery('#form\\\\:mock').selectize({key1:'1'});",
                Selectize.builder().withComponentWrapper(componentWrapper).withOption(KEY1, 1).build().script());
    }

    @Test
    void shouldCreateSelectizeWithOptions() {
        Map<String, Serializable> options = new HashMap<>();
        options.put(KEY1, 1);
        options.put(KEY2, Boolean.TRUE);
        assertEquals("jQuery('#form\\\\:mock').selectize({key1:'1',key2:'true'});",
                Selectize.builder().withComponentWrapper(componentWrapper).withOptions(options).build().script());
    }

    @Override
    public void configureComponents(final ComponentConfigDecorator decorator) {
        decorator.registerMockRendererForHtmlForm().registerMockRendererForHtmlInputText();
    }
}
