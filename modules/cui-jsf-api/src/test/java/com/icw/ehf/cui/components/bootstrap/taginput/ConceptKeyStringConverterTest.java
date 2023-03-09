package com.icw.ehf.cui.components.bootstrap.taginput;

import static com.icw.ehf.cui.components.bootstrap.taginput.TagInputRendererTest.CODE_TYPE_1;
import static com.icw.ehf.cui.components.bootstrap.taginput.TagInputRendererTest.CODE_TYPE_2;
import static de.cuioss.tools.collect.CollectionLiterals.immutableSortedSet;

import java.util.Collections;
import java.util.Set;

import de.cuioss.test.jsf.converter.AbstractConverterTest;
import de.cuioss.test.jsf.converter.TestItems;
import de.cuioss.uimodel.model.conceptkey.ConceptKeyType;

class ConceptKeyStringConverterTest
        extends AbstractConverterTest<ConceptKeyStringConverter, Set<ConceptKeyType>> {

    public ConceptKeyStringConverterTest() {
        setComponent(new TagInputComponent());
    }

    @Override
    public void populate(final TestItems<Set<ConceptKeyType>> testItems) {
        testItems.addValidObjectWithStringResult(immutableSortedSet(CODE_TYPE_1),
                "6964656e74696669657231");
        testItems.addValidObjectWithStringResult(immutableSortedSet(CODE_TYPE_1, CODE_TYPE_2),
                "6964656e74696669657231,6964656e74696669657232");
        testItems.addValidObject(Collections.singleton(new TestConceptKey()));
    }
}
