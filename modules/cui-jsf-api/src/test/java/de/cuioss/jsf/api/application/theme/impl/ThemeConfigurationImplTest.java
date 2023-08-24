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
package de.cuioss.jsf.api.application.theme.impl;

import static de.cuioss.test.generator.Generators.strings;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.junit.EnableGeneratorController;
import de.cuioss.test.jsf.junit5.JsfEnabledTestEnvironment;

@EnableGeneratorController
class ThemeConfigurationImplTest extends JsfEnabledTestEnvironment {

    private final String defaultTheme = "Blue";

    private final String cssTemplate = "application-%s.css";

    private final String defaultCss = cssTemplate.formatted(defaultTheme.toLowerCase());

    @Test
    void shouldWorkWithDefaultvalues() {
        var underTest = new ThemeConfigurationImpl();
        underTest.initBean();
        assertEquals(defaultTheme, underTest.getDefaultTheme());
        assertEquals(defaultCss, underTest.getCssForThemeName(defaultTheme));
        assertEquals(defaultCss, underTest.getCssForThemeName(null));
        assertEquals(defaultCss, underTest.getCssForThemeName(anyNotExisitingThemeName()));
        var theme = anyExisitingThemeName();
        assertEquals(cssTemplate.formatted(theme.toLowerCase()), underTest.getCssForThemeName(theme));
    }

    @Test
    void shouldFailWithWrongCssName() {
        var underTest = new ThemeConfigurationImpl();
        underTest.setCssName(Generators.strings().next() + ".abc");
        assertThrows(IllegalStateException.class, underTest::initBean);
    }

    private String anyExisitingThemeName() {
        return Generators.fixedValues(new ThemeConfigurationImpl().getAvailableThemes()).next();
    }

    private String anyNotExisitingThemeName() {
        var underTest = new ThemeConfigurationImpl();
        var candidate = strings(2, 12).next();
        if (underTest.getAvailableThemes().contains(candidate)) {
            return anyNotExisitingThemeName();
        }
        return candidate;
    }
}
