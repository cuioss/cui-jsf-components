package de.cuioss.jsf.jqplot.portal.theme;

import static de.cuioss.test.generator.Generators.fixedValues;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.jqplot.portal.theme.CssRule;
import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.test.valueobjects.ValueObjectTest;
import de.cuioss.test.valueobjects.api.property.PropertyReflectionConfig;

@PropertyReflectionConfig(of = "selector")
class CssRuleTest extends ValueObjectTest<CssRule> {

    private final TypedGenerator<String> validRules = fixedValues("name{prop1:value1}", "name{prop2:value2}",
            "name{prop3:value3}");

    private CssRule target;

    @Test
    final void shouldFailOnMissingReqiredParameter() {
        assertThrows(IllegalArgumentException.class, () -> {
            CssRule.createBy(null);
        });
    }

    @Test
    final void shouldFailOnMissingContent() {
        assertThrows(IllegalArgumentException.class, () -> {
            target = CssRule.createBy("");
        });
    }

    @Test
    final void shouldProvideAvailablePropertyValue() {
        target = CssRule.createBy("selector-name{-property-Name:propertyValue}");
        assertEquals("selector-name", target.getSelector());
        assertTrue(target.getProperties().contains("-property-name"));
        assertEquals("propertyValue", target.getPropertyValue("-property-name"));
    }

    @Override
    protected CssRule anyValueObject() {
        return CssRule.createBy(validRules.next());
    }
}
