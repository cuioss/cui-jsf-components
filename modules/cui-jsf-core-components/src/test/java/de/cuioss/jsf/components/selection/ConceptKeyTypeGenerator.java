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
package de.cuioss.jsf.components.selection;

import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.uimodel.model.conceptkey.AugmentationKeyConstans;
import de.cuioss.uimodel.model.conceptkey.ConceptKeyType;
import de.cuioss.uimodel.model.conceptkey.impl.BaseConceptCategory;
import de.cuioss.uimodel.model.conceptkey.impl.ConceptKeyTypeImpl;
import de.cuioss.uimodel.nameprovider.I18nDisplayNameProvider;

@SuppressWarnings("javadoc")
public class ConceptKeyTypeGenerator implements TypedGenerator<ConceptKeyType> {

    public static final ConceptKeyType TEST_CODE = ConceptKeyTypeImpl.builder().identifier("test")
            .labelResolver(new I18nDisplayNameProvider("test-resolved")).category(new BaseConceptCategory()).build();

    public static final ConceptKeyType TEST_CODE2 = ConceptKeyTypeImpl.builder().identifier("test2")
            .labelResolver(new I18nDisplayNameProvider("test-resolved2")).category(new BaseConceptCategory()).build();

    public static final ConceptKeyType TEST_DEFAULT_CODE = ConceptKeyTypeImpl.builder().identifier("default_code")
            .labelResolver(new I18nDisplayNameProvider("default_code-resolved"))
            .augmentation(AugmentationKeyConstans.DEFAULT_VALUE, "true").category(new BaseConceptCategory()).build();

    private final TypedGenerator<String> strings = Generators.letterStrings(1, 5);

    @Override
    public ConceptKeyType next() {
        return ConceptKeyTypeImpl.builder().identifier(strings.next())
                .labelResolver(new I18nDisplayNameProvider(strings.next())).category(new BaseConceptCategory()).build();
    }

    @Override
    public Class<ConceptKeyType> getType() {
        return ConceptKeyType.class;
    }

}
