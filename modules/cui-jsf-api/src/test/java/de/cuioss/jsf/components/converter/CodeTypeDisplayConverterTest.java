package de.cuioss.jsf.components.converter;

import de.cuioss.jsf.api.converter.LocaleProducerMock;
import de.cuioss.jsf.components.converter.CodeTypeDisplayConverter;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.converter.AbstractConverterTest;
import de.cuioss.test.jsf.converter.TestItems;
import de.cuioss.uimodel.model.code.CodeType;
import de.cuioss.uimodel.model.code.CodeTypeImpl;

@JsfTestConfiguration(LocaleProducerMock.class)
class CodeTypeDisplayConverterTest extends AbstractConverterTest<CodeTypeDisplayConverter, CodeType> {

    @Override
    public void populate(final TestItems<CodeType> testItems) {
        testItems.addValidObjectWithStringResult(new CodeTypeImpl("1"), "1");
    }

}
