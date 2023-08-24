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
package de.cuioss.jsf.api.converter.nameprovider;

import static de.cuioss.tools.collect.CollectionLiterals.immutableList;

import de.cuioss.jsf.api.CoreJsfTestConfiguration;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.converter.AbstractConverterTest;
import de.cuioss.test.jsf.converter.TestItems;
import de.cuioss.uimodel.nameprovider.LabeledKey;

@JsfTestConfiguration(CoreJsfTestConfiguration.class)
class LabeledKeyConverterTest extends AbstractConverterTest<LabeledKeyConverter, LabeledKey> {

    private static final String MESSAGE_KEY = "common.abb.day";

    @Override
    public void populate(final TestItems<LabeledKey> testItems) {
        testItems.addValidObjectWithStringResult(new LabeledKey(MESSAGE_KEY), MESSAGE_KEY)
                .addValidObjectWithStringResult(new LabeledKey(MESSAGE_KEY, immutableList("1")), MESSAGE_KEY);

    }

}
