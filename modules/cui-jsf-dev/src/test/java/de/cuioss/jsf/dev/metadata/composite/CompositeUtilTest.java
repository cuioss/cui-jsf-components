package de.cuioss.jsf.dev.metadata.composite;

import static org.junit.jupiter.api.Assertions.assertNull;

import javax.faces.component.html.HtmlInputText;

import org.junit.jupiter.api.Test;

import de.cuioss.test.jsf.config.ComponentConfigurator;
import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import de.cuioss.test.jsf.junit5.JsfEnabledTestEnvironment;

class CompositeUtilTest extends JsfEnabledTestEnvironment implements ComponentConfigurator {

    @Test
    void shouldLoadCompositeInfo() {
        var info = CompositeUtil.loadMetadataInfo(getFacesContext(), "dev-composite", "namespace.xhtml");
        assertNull(info); // Expected, no path present
    }

    @Override
    public void configureComponents(ComponentConfigDecorator decorator) {
        decorator.registerCompositeComponent("dev-composite", "namespace.xhtml", new HtmlInputText());
    }

}
