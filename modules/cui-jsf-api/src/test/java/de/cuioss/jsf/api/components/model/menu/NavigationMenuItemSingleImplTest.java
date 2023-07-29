package de.cuioss.jsf.api.components.model.menu;

import static de.cuioss.test.generator.Generators.letterStrings;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.CoreJsfTestConfiguration;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.junit5.EnableJsfEnvironment;
import de.cuioss.test.valueobjects.ValueObjectTest;
import de.cuioss.test.valueobjects.api.contracts.VerifyConstructor;
import de.cuioss.test.valueobjects.api.generator.PropertyGenerator;

@VerifyConstructor(of = "order")
@PropertyGenerator(NavigationMenuItemGenerator.class)
@EnableJsfEnvironment
@JsfTestConfiguration(CoreJsfTestConfiguration.class)
class NavigationMenuItemSingleImplTest extends ValueObjectTest<NavigationMenuItemSingleImpl> {

    @Test
    void shouldResolveLabelValue() {
        var underTest = new NavigationMenuItemSingleImpl(1);
        var value = letterStrings(1, 10).next();
        underTest.setLabelValue(value);
        assertEquals(value, underTest.getResolvedLabel());
    }
}
