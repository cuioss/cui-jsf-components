package de.cuioss.jsf.jqplot.portal.theme;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.test.valueobjects.ValueObjectTest;

class GridThemeTest extends ValueObjectTest<GridTheme> implements TypedGenerator<GridTheme> {

    private GridTheme underTest;

    @Test
    void shouldProvideDefaultSettings() {
        underTest = GridTheme.createBy(null);
        assertEquals(GridTheme.DEFAULT_GRID_SETTINGS, underTest);
    }

    @Test
    void shouldAccecptValidCssStructureAndRetrieveBackgroundColor() {
        final var cssText = "someRule{" + "-jqplot-drawGridlines:false;" + "-jqplot-backgroundColor:green" + "}";
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
