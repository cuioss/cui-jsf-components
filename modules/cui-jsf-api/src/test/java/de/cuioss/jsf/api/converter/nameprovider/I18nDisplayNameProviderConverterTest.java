package de.cuioss.jsf.api.converter.nameprovider;

import java.util.Locale;

import de.cuioss.jsf.api.CoreJsfTestConfiguration;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.converter.AbstractSanitizingConverterTest;
import de.cuioss.test.jsf.converter.TestItems;
import de.cuioss.uimodel.nameprovider.I18nDisplayNameProvider;

/**
 * @author Sven Haag
 */
@JsfTestConfiguration(CoreJsfTestConfiguration.class)
class I18nDisplayNameProviderConverterTest extends
        AbstractSanitizingConverterTest<I18nDisplayNameProviderConverter, I18nDisplayNameProvider> {

    @Override
    public void populate(final TestItems<I18nDisplayNameProvider> testItems) {
        testItems.addValidObjectWithStringResult(new I18nDisplayNameProvider.Builder()
                .add(Locale.ENGLISH, "ENG").build(), "ENG");

        testItems.addValidObjectWithStringResult(new I18nDisplayNameProvider.Builder()
                .add(Locale.TAIWAN, "TW").build(), "");
    }

    @Override
    protected I18nDisplayNameProvider createTestObjectWithMaliciousContent(String content) {
        return new I18nDisplayNameProvider.Builder().add(Locale.ENGLISH, content).build();
    }
}
