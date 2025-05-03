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
package de.cuioss.jsf.jqplot.portal.theme;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.test.valueobjects.ValueObjectTest;
import org.junit.jupiter.api.Test;

class GridThemeTest extends ValueObjectTest<GridTheme> implements TypedGenerator<GridTheme> {

    private GridTheme underTest;

    @Test
    void shouldProvideDefaultSettings() {
        underTest = GridTheme.createBy(null);
        assertEquals(GridTheme.DEFAULT_GRID_SETTINGS, underTest);
    }

    @Test
    void shouldAccecptValidCssStructureAndRetrieveBackgroundColor() {
        final var cssText = """
                someRule{\
                -jqplot-drawGridlines:false;\
                -jqplot-backgroundColor:green\
                }""";
        underTest = GridTheme.createBy(from(cssText));

        assertEquals("green", underTest.getBackgroundColor());

        final var backgroundColorAsJs = underTest.getBackgroundColorAsJs();
        assertEquals("\"green\"", backgroundColorAsJs);
        // check lazy loaded
        assertEquals(underTest.getBackgroundColorAsJs(), backgroundColorAsJs);
    }

    private static CssRule from(final String cssText) {
        return CssRule.createBy(cssText);
    }

    @Override
    public GridTheme next() {
        return GridTheme.DEFAULT_GRID_SETTINGS;
    }

    @Override
    public Class<GridTheme> getType() {
        return GridTheme.class;
    }

    @Override
    protected GridTheme anyValueObject() {
        return next();
    }

}
