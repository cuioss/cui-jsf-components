package com.icw.ehf.cui.core.api.application.theme.theme;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.icw.ehf.cui.core.api.application.theme.ThemeResourceHandler;
import com.icw.ehf.cui.core.api.application.theme.impl.ThemeConfigurationImpl;
import com.icw.ehf.cui.core.api.application.theme.impl.ThemeNameProducerImpl;

import de.cuioss.test.jsf.config.BeanConfigurator;
import de.cuioss.test.jsf.config.decorator.BeanConfigDecorator;
import de.cuioss.test.jsf.junit5.JsfEnabledTestEnvironment;

class ThemeResourceHandlerTest extends JsfEnabledTestEnvironment implements BeanConfigurator {

    private ThemeResourceHandler underTest;

    private static final String LIBRARY_NAME = "com.icw.portal.css";

    private static final String STYLE_CSS = "application.css";

    @BeforeEach
    void setUpTest() {
        underTest = new ThemeResourceHandler(getApplication().getResourceHandler());
    }

    @Test
    void shouldReturnDefaultTheme() {
        assertNotNull(underTest.createResource(STYLE_CSS, LIBRARY_NAME));
    }

    @Test
    void shouldFailOnNonExistingTheme() {
        assertNull(underTest.createResource("error.css", LIBRARY_NAME));
    }

    @Test
    void shouldWrapResourceHandler() {
        assertNotNull(underTest.getWrapped());
    }

    @Override
    public void configureBeans(final BeanConfigDecorator decorator) {
        final var configurationBean = new ThemeConfigurationImpl();
        configurationBean.initBean();
        decorator.register(configurationBean, ThemeConfigurationImpl.BEAN_NAME).register(new ThemeNameProducerImpl(),
                ThemeNameProducerImpl.BEAN_NAME);
    }
}
