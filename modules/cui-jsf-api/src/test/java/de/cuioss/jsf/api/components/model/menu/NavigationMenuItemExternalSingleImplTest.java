package de.cuioss.jsf.api.components.model.menu;

import static de.cuioss.test.generator.Generators.letterStrings;
import static de.cuioss.tools.collect.CollectionLiterals.immutableList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.CoreJsfTestConfiguration;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.junit5.EnableJsfEnvironment;
import de.cuioss.test.valueobjects.ValueObjectTest;
import de.cuioss.test.valueobjects.api.contracts.VerifyConstructor;

@VerifyConstructor(of = "order")
@EnableJsfEnvironment
@JsfTestConfiguration(CoreJsfTestConfiguration.class)
class NavigationMenuItemExternalSingleImplTest extends ValueObjectTest<NavigationMenuItemExternalSingleImpl> {

    @Test
    void shouldResolveLabelValue() {
        var underTest = new NavigationMenuItemExternalSingleImpl(1);
        var value = letterStrings(1, 10).next();
        underTest.setLabelValue(value);
        assertEquals(value, underTest.getResolvedLabel());
    }

    @Test
    void shouldResolveTitle() {
        var underTest = new NavigationMenuItemExternalSingleImpl(1);
        var value = letterStrings(1, 10).next();
        underTest.setTitleValue(value);
        assertEquals(value, underTest.getResolvedTitle());
    }

    @Test
    void shouldDetectTitle() {
        var underTest = new NavigationMenuItemExternalSingleImpl(1);
        assertFalse(underTest.isTitleAvailable());
        var value = letterStrings(1, 10).next();
        underTest.setTitleValue(value);
        assertTrue(underTest.isTitleAvailable());
        assertEquals(value, underTest.getResolvedTitle());
    }

    @Test
    void shouldCompareCorrectly() {
        var one = new NavigationMenuItemExternalSingleImpl(1);
        var two = new NavigationMenuItemExternalSingleImpl(2);
        assertEquals(-1, one.compareTo(two));
    }

    @Test
    void shouldDetectActiveView() {
        var one = new NavigationMenuItemExternalSingleImpl(1);
        assertFalse(one.isActive());

        one.setActiveForAdditionalViewId(immutableList("myview"));
        assertFalse(one.isActive());
    }

}
