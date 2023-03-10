package de.cuioss.jsf.runtime.converter.nameprovider;

import static de.cuioss.tools.collect.CollectionLiterals.immutableList;

import de.cuioss.jsf.api.CoreJsfTestConfiguration;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.converter.AbstractConverterTest;
import de.cuioss.test.jsf.converter.TestItems;
import de.cuioss.uimodel.nameprovider.LabeledKey;

@JsfTestConfiguration(CoreJsfTestConfiguration.class)
class LabeledKeyConverterTest extends
        AbstractConverterTest<LabeledKeyConverter, LabeledKey> {

    private static final String MESSAGE_KEY = "common.abb.day";

    @Override
    public void populate(final TestItems<LabeledKey> testItems) {
        testItems.addValidObjectWithStringResult(new LabeledKey(MESSAGE_KEY), MESSAGE_KEY)
                .addValidObjectWithStringResult(new LabeledKey(MESSAGE_KEY, immutableList("1")), MESSAGE_KEY);

    }

}
