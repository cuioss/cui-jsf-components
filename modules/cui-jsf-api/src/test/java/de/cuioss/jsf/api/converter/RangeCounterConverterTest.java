package de.cuioss.jsf.api.converter;

import de.cuioss.test.jsf.converter.AbstractConverterTest;
import de.cuioss.test.jsf.converter.TestItems;
import de.cuioss.uimodel.model.RangeCounter;
import de.cuioss.uimodel.model.impl.BaseRangeCounter;

class RangeCounterConverterTest extends AbstractConverterTest<RangeCounterConverter, RangeCounter> {

    @Override
    public void populate(final TestItems<RangeCounter> testItems) {
        testItems.addValidObjectWithStringResult(new BaseRangeCounter(1, 2), "1/2")
                .addValidObjectWithStringResult(new BaseRangeCounter(1, null), "1");

    }

}
