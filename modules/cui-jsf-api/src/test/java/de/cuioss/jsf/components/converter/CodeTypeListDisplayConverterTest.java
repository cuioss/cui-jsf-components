package de.cuioss.jsf.components.converter;

import static de.cuioss.tools.collect.CollectionLiterals.mutableList;

import java.util.List;

import de.cuioss.jsf.api.converter.LocaleProducerMock;
import de.cuioss.jsf.components.converter.CodeTypeListDisplayConverter;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.converter.AbstractConverterTest;
import de.cuioss.test.jsf.converter.TestItems;
import de.cuioss.uimodel.model.code.CodeType;
import de.cuioss.uimodel.model.code.CodeTypeImpl;

@JsfTestConfiguration(LocaleProducerMock.class)
class CodeTypeListDisplayConverterTest
        extends AbstractConverterTest<CodeTypeListDisplayConverter, List<CodeType>> {

    @Override
    public void populate(final TestItems<List<CodeType>> testItems) {
        testItems.addValidObjectWithStringResult(mutableList(new CodeTypeImpl("a"), new CodeTypeImpl("b")),
                "a;b");
    }
}
