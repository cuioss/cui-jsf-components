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

import de.cuioss.jsf.api.CoreJsfTestConfiguration;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.converter.AbstractConverterTest;
import de.cuioss.test.jsf.converter.TestItems;
import de.cuioss.uimodel.nameprovider.DisplayMessageProvider;

@JsfTestConfiguration(CoreJsfTestConfiguration.class)
class DisplayMessageProviderConverterTest
        extends AbstractConverterTest<DisplayMessageProviderConverter, DisplayMessageProvider> {

    private static final String MESSAGE_KEY = "cc.document.file.upload.invalid.size.message";

    @Override
    public void populate(final TestItems<DisplayMessageProvider> testItems) {
        testItems.addValidObjectWithStringResult(
                new DisplayMessageProvider.Builder().messageKey(MESSAGE_KEY).add(10).build(), MESSAGE_KEY);

    }

}
