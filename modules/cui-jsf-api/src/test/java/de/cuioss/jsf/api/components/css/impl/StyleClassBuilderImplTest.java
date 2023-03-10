package de.cuioss.jsf.api.components.css.impl;

import static de.cuioss.test.generator.Generators.letterStrings;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.components.css.CssCommon;
import de.cuioss.test.valueobjects.ValueObjectTest;

class StyleClassBuilderImplTest extends ValueObjectTest<StyleClassBuilderImpl> {

    private static final String COMBINED_CLASS = "some class";

    private static final String CSS_CLASS = "cssClass";

    @Test
    void shouldBeEmptyOnDefaultConstructor() {
        var builder = new StyleClassBuilderImpl();
        assertFalse(builder.isAvailable());
        assertTrue(builder.getStyleClass().isEmpty());
    }

    @Test
    void shouldBeEmptyOnCssConstructorWithEmptyString() {
        var builder = new StyleClassBuilderImpl("");
        assertFalse(builder.isAvailable());
        assertTrue(builder.getStyleClass().isEmpty());
        builder = new StyleClassBuilderImpl(null);
        assertFalse(builder.isAvailable());
        assertTrue(builder.getStyleClass().isEmpty());
    }

    @Test
    void shouldIgnoreEmptyElements() {
        var builder = new StyleClassBuilderImpl();
        builder.append("");
        builder.append(new StyleClassBuilderImpl());
        assertFalse(builder.isAvailable());
        assertTrue(builder.getStyleClass().isEmpty());
    }

    @Test
    void shouldHandleCssString() {
        var builder = new StyleClassBuilderImpl(COMBINED_CLASS);
        assertTrue(builder.isAvailable());
        assertEquals(COMBINED_CLASS, builder.getStyleClass());
    }

    @Test
    void shouldIgnoreDuplicates() {
        var builder = new StyleClassBuilderImpl();
        builder.append(COMBINED_CLASS).append(COMBINED_CLASS);
        assertTrue(builder.isAvailable());
        assertEquals(COMBINED_CLASS, builder.getStyleClass());
    }

    @Test
    void shouldCreateCssString() {
        var builder = new StyleClassBuilderImpl();
        builder.append(CSS_CLASS).append(COMBINED_CLASS);
        assertTrue(builder.isAvailable());
        assertEquals(CSS_CLASS + " " + COMBINED_CLASS, builder.getStyleClass());
    }

    @Test
    void shouldRemoveClassString() {
        var builder = new StyleClassBuilderImpl();
        builder.append(CSS_CLASS).append(COMBINED_CLASS).remove(COMBINED_CLASS);
        assertTrue(builder.isAvailable());
        assertEquals(CSS_CLASS, builder.getStyleClass());
    }

    @Test
    void shouldToggleClassString() {
        var builder = new StyleClassBuilderImpl();
        builder.append(CSS_CLASS).append(COMBINED_CLASS).toggle(COMBINED_CLASS);
        assertTrue(builder.isAvailable());
        assertEquals(CSS_CLASS, builder.getStyleClass());
    }

    @Test
    void shouldHandleStyleClassProvider() {
        var builder = new StyleClassBuilderImpl();
        builder.append(CssCommon.DISABLED);
        assertTrue(builder.isAvailable());
        assertEquals(CssCommon.DISABLED.getStyleClass(), builder.getStyleClass());
        builder.remove(CssCommon.DISABLED);
        assertFalse(builder.isAvailable());
        builder.toggle(CssCommon.DISABLED);
        assertTrue(builder.isAvailable());
        assertEquals(CssCommon.DISABLED.getStyleClass(), builder.getStyleClass());
        builder.toggle(CssCommon.DISABLED);
        assertFalse(builder.isAvailable());
    }

    @Test
    void shouldHandleStyleClassBuilder() {
        var builder = new StyleClassBuilderImpl();
        builder.append(CssCommon.DISABLED);
        assertTrue(builder.isAvailable());
        assertEquals(CssCommon.DISABLED.getStyleClass(), builder.getStyleClass());
        builder.remove(CssCommon.DISABLED.getStyleClassBuilder());
        assertFalse(builder.isAvailable());
        builder.toggle(CssCommon.DISABLED.getStyleClassBuilder());
        assertTrue(builder.isAvailable());
        assertEquals(CssCommon.DISABLED.getStyleClass(), builder.getStyleClass());
        builder.toggle(CssCommon.DISABLED.getStyleClassBuilder());
        assertFalse(builder.isAvailable());
    }

    @Test
    void shouldHandleDuplicateEmpty() {
        var builder = new StyleClassBuilderImpl();
        builder.append("  ");
        builder.append(CssCommon.DISABLED);
        assertTrue(builder.isAvailable());
        assertEquals(CssCommon.DISABLED.getStyleClass(), builder.getStyleClass());
    }

    @Override
    protected StyleClassBuilderImpl anyValueObject() {
        return new StyleClassBuilderImpl(letterStrings().next());
    }

    @Test
    void shouldAppendIfTrue() {
        var builder = new StyleClassBuilderImpl();
        builder.append(CSS_CLASS).appendIfTrue(CssCommon.DISABLED, true).appendIfTrue(CssCommon.PULL_LEFT, false)
                .appendIfTrue(null, true);
        assertTrue(builder.isAvailable());
        assertEquals(CSS_CLASS + " " + CssCommon.DISABLED.getStyleClass(), builder.getStyleClass());
    }
}
