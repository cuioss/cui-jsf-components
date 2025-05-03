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
package de.cuioss.jsf.components.converter;

import static de.cuioss.tools.collect.CollectionLiterals.mutableList;

import de.cuioss.test.jsf.converter.AbstractConverterTest;
import de.cuioss.test.jsf.converter.TestItems;
import de.cuioss.uimodel.model.code.CodeType;
import de.cuioss.uimodel.model.code.CodeTypeImpl;

import java.util.List;

class CodeTypeListDisplayConverterTest extends AbstractConverterTest<CodeTypeListDisplayConverter, List<CodeType>> {

    @Override
    public void populate(final TestItems<List<CodeType>> testItems) {
        testItems.addValidObjectWithStringResult(mutableList(new CodeTypeImpl("a"), new CodeTypeImpl("b")), "a;b");
    }
}
