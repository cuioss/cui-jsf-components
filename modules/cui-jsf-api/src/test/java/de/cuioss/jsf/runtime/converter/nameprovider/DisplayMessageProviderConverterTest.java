package de.cuioss.jsf.runtime.converter.nameprovider;

import de.cuioss.jsf.api.CoreJsfTestConfiguration;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.converter.AbstractConverterTest;
import de.cuioss.test.jsf.converter.TestItems;
import de.cuioss.uimodel.nameprovider.DisplayMessageProvider;

@JsfTestConfiguration(CoreJsfTestConfiguration.class)
class DisplayMessageProviderConverterTest
        extends AbstractConverterTest<DisplayMessageProviderConverter, DisplayMessageProvider> {

    private static final String MESSAGE_KEY = "cc.document.file.upload.invalid.size.message";

    @Override
    public void populate(final TestItems<DisplayMessageProvider> testItems) {
        testItems.addValidObjectWithStringResult(new DisplayMessageProvider.Builder()
                .messageKey(MESSAGE_KEY).add(10).build(), MESSAGE_KEY);

    }

}
