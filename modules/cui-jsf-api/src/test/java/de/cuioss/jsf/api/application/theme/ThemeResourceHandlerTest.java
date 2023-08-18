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
package de.cuioss.jsf.api.application.theme;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.application.theme.impl.ThemeConfigurationImpl;
import de.cuioss.jsf.api.application.theme.impl.ThemeNameProducerImpl;
import de.cuioss.test.jsf.config.BeanConfigurator;
import de.cuioss.test.jsf.config.decorator.BeanConfigDecorator;
import de.cuioss.test.jsf.junit5.JsfEnabledTestEnvironment;

class ThemeResourceHandlerTest extends JsfEnabledTestEnvironment implements BeanConfigurator {

    private ThemeResourceHandler underTest;

    private static final String LIBRARY_NAME = "de.cuioss.portal.css";

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
