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
package de.cuioss.jsf.bootstrap.support;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.uimodel.model.conceptkey.ConceptCategory;
import de.cuioss.uimodel.model.conceptkey.ConceptKeyType;
import de.cuioss.uimodel.model.conceptkey.impl.BaseConceptCategory;
import de.cuioss.uimodel.model.conceptkey.impl.ConceptKeyTypeImpl;
import de.cuioss.uimodel.nameprovider.I18nDisplayNameProvider;

public class ConceptKeyTypeGenerator implements TypedGenerator<ConceptKeyType> {

    public static final ConceptCategory TestConceptCategory = new BaseConceptCategory() {

        private static final long serialVersionUID = -1983692596664938641L;

    };

    private final TypedGenerator<String> strings = Generators.letterStrings(1, 10);

    @Override
    public ConceptKeyType next() {
        return ConceptKeyTypeImpl.builder().identifier(strings.next())
                .labelResolver(new I18nDisplayNameProvider(strings.next())).category(TestConceptCategory).build();
    }

    public Collection<ConceptKeyType> list(final int count) {
        final List<ConceptKeyType> result = new ArrayList<>();
        for (var i = 0; i < count; i++) {
            result.add(next());
        }
        return result;
    }

    @Override
    public Class<ConceptKeyType> getType() {
        return ConceptKeyType.class;
    }
}
