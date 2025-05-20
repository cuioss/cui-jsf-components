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

import static de.cuioss.tools.collect.CollectionLiterals.mutableSet;

import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.uimodel.model.conceptkey.ConceptKeyType;

import java.util.Set;

@SuppressWarnings({"rawtypes"})
public class ConceptKeyTypeSetGenerator implements TypedGenerator<Set> {

    private final ConceptKeyTypeGenerator codeType = new ConceptKeyTypeGenerator();

    private final TypedGenerator<Integer> numbers = Generators.integers(0, 25);

    private final TypedGenerator<Integer> positiveNumbers = Generators.integers(1, 25);

    @Override
    public Set<ConceptKeyType> next() {
        final Set<ConceptKeyType> types = mutableSet();
        final int number = numbers.next();
        for (var i = 0; i < number; i++) {
            types.add(codeType.next());

        }
        return types;
    }

    /**
     * @return a the set that is not empty.
     */
    public Set<ConceptKeyType> nextNotEmpty() {
        final Set<ConceptKeyType> types = mutableSet();
        final int number = positiveNumbers.next();
        for (var i = 0; i < number; i++) {
            types.add(codeType.next());

        }
        return types;
    }

    @Override
    public Class<Set> getType() {
        return Set.class;
    }
}
