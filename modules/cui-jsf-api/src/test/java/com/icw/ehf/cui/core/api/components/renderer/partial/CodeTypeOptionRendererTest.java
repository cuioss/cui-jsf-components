package com.icw.ehf.cui.core.api.components.renderer.partial;

import static de.cuioss.tools.collect.CollectionLiterals.immutableList;

import java.io.IOException;
import java.io.StringWriter;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputText;

import org.apache.myfaces.test.mock.MockResponseWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.icw.ehf.cui.core.api.components.html.HtmlTreeBuilder;
import com.icw.ehf.cui.core.api.components.renderer.DecoratingResponseWriter;

import de.cuioss.test.jsf.junit5.JsfEnabledTestEnvironment;
import de.cuioss.test.jsf.renderer.util.HtmlTreeAsserts;
import de.cuioss.uimodel.model.code.CodeTypeImpl;

class CodeTypeOptionRendererTest extends JsfEnabledTestEnvironment {

    private static final String RESOLVED = "resolved";

    private static final String IDENTIFIER = "identifier";

    private static final String OPTION_1_HTML = "<option value=\"identifier\">resolved</option>";

    private static final String OPTION_2_HTML = "<option value=\"resolved\">identifier</option>";

    private StringWriter output;

    private UIComponent component;

    private DecoratingResponseWriter<UIComponent> responseWriter;

    @SuppressWarnings("resource")
    @BeforeEach
    void before() {
        output = new StringWriter();
        component = new HtmlInputText();
        component.setId("id");
        getFacesContext().setResponseWriter(new MockResponseWriter(output));
        responseWriter = new DecoratingResponseWriter<>(getFacesContext(), component);
    }

    @Test
    void shouldRenderSingleElement() throws IOException {
        var type = new CodeTypeImpl(RESOLVED, IDENTIFIER);
        var renderer = new CodeTypeOptionRenderer(type);
        renderer.render(responseWriter, null, false);
        assertWritten(OPTION_1_HTML);
    }

    @Test
    void shouldRenderMultipleElements() throws IOException {
        var type = new CodeTypeImpl(RESOLVED, IDENTIFIER);
        var type2 = new CodeTypeImpl(IDENTIFIER, RESOLVED);
        var renderer = new CodeTypeOptionRenderer(immutableList(type, type2));
        renderer.render(responseWriter, null, false);
        assertWritten(OPTION_1_HTML + OPTION_2_HTML);
    }

    private void assertWritten(final String expected) {
        var builderExpected = new HtmlTreeBuilder(expected);
        var builderActutal = new HtmlTreeBuilder(output.toString());
        HtmlTreeAsserts.assertHtmlTreeEquals(builderExpected.getDocument(), builderActutal.getDocument());
    }
}
