package de.cuioss.jsf.api.converter.nameprovider;

import de.cuioss.jsf.api.CoreJsfTestConfiguration;
import de.cuioss.test.generator.Generators;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.converter.AbstractSanitizingConverterTest;
import de.cuioss.test.jsf.converter.TestItems;
import de.cuioss.uimodel.nameprovider.DisplayName;

@JsfTestConfiguration(CoreJsfTestConfiguration.class)
class DisplayNameConverterTest extends AbstractSanitizingConverterTest<DisplayNameConverter, DisplayName> {

    @Override
    public void populate(final TestItems<DisplayName> testItems) {
        var any = Generators.letterStrings(1, 5).next();
        testItems.addValidObjectWithStringResult(new DisplayName(any), any);
    }

    @Override
    protected DisplayName createTestObjectWithMaliciousContent(String content) {
        return new DisplayName(content);
    }
}
