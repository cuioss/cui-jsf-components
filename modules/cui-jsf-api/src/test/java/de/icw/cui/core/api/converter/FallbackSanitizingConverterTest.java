package de.icw.cui.core.api.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlOutputText;

import org.junit.jupiter.api.Test;

import com.icw.ehf.cui.core.api.application.projectstage.CuiProjectStageAccessor;

import de.cuioss.test.jsf.junit5.JsfEnabledTestEnvironment;
import de.cuioss.test.juli.LogAsserts;
import de.cuioss.test.juli.TestLogLevel;
import de.cuioss.test.juli.junit5.EnableTestLogger;
import de.cuioss.uimodel.application.CuiProjectStage;

@EnableTestLogger
class FallbackSanitizingConverterTest extends JsfEnabledTestEnvironment {

    @Test
    void shouldNotSanitizeWhenInput() {
        var component = new HtmlInputText();
        var result = new FallbackSanitizingConverter().convertToString(getFacesContext(), component, "<script>");
        assertEquals("<script>", result);
    }

    @Test
    void shouldNotSanitizeWhenOutputAndEscape() {
        var component = new HtmlOutputText();
        var result = new FallbackSanitizingConverter().convertToString(getFacesContext(), component, "<script>");
        assertEquals("<script>", result);
    }

    @Test
    void shouldSanitizeAndWarnWhenOutputAndEscape() {
        getBeanConfigDecorator().register(new CuiProjectStage() {

            private static final long serialVersionUID = 1L;

            @Override
            public boolean isDevelopment() {
                return true;
            }

            @Override
            public boolean isTest() {
                return false;
            }

            @Override
            public boolean isConfiguration() {
                return false;
            }

            @Override
            public boolean isProduction() {
                return false;
            }
        }, CuiProjectStageAccessor.BEAN_NAME);
        var component = new HtmlOutputText();
        component.setEscape(false);
        var result = new FallbackSanitizingConverter().convertToString(getFacesContext(), component, "<script>");
        assertEquals("", result);
        LogAsserts.assertSingleLogMessagePresentContaining(TestLogLevel.WARN, "CUI-101");
    }
}
