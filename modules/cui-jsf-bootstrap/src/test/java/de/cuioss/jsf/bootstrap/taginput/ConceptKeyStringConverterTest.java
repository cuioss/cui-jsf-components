/*
 * Copyright 2023 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.bootstrap.taginput;

import static de.cuioss.jsf.bootstrap.taginput.TagInputRendererTest.CODE_TYPE_1;
import static de.cuioss.jsf.bootstrap.taginput.TagInputRendererTest.CODE_TYPE_2;
import static de.cuioss.tools.collect.CollectionLiterals.immutableSortedSet;

import java.util.Collections;
import java.util.Set;

import de.cuioss.test.jsf.converter.AbstractConverterTest;
import de.cuioss.test.jsf.converter.TestItems;
import de.cuioss.uimodel.model.conceptkey.ConceptKeyType;

class ConceptKeyStringConverterTest extends AbstractConverterTest<ConceptKeyStringConverter, Set<ConceptKeyType>> {

    public ConceptKeyStringConverterTest() {
        setComponent(new TagInputComponent());
    }

    @Override
    public void populate(final TestItems<Set<ConceptKeyType>> testItems) {
        testItems.addValidObjectWithStringResult(immutableSortedSet(CODE_TYPE_1), "6964656e74696669657231");
        testItems.addValidObjectWithStringResult(immutableSortedSet(CODE_TYPE_1, CODE_TYPE_2),
                "6964656e74696669657231,6964656e74696669657232");
        testItems.addValidObject(Collections.singleton(new TestConceptKey()));
    }
}
