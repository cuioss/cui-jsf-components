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

import java.util.Locale;

import de.cuioss.jsf.api.CoreJsfTestConfiguration;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.converter.AbstractSanitizingConverterTest;
import de.cuioss.test.jsf.converter.TestItems;
import de.cuioss.uimodel.nameprovider.I18nDisplayNameProvider;

/**
 * @author Sven Haag
 */
@JsfTestConfiguration(CoreJsfTestConfiguration.class)
class I18nDisplayNameProviderConverterTest
        extends AbstractSanitizingConverterTest<I18nDisplayNameProviderConverter, I18nDisplayNameProvider> {

    @Override
    public void populate(final TestItems<I18nDisplayNameProvider> testItems) {
        testItems.addValidObjectWithStringResult(
                new I18nDisplayNameProvider.Builder().add(Locale.ENGLISH, "ENG").build(), "ENG");

        testItems.addValidObjectWithStringResult(new I18nDisplayNameProvider.Builder().add(Locale.TAIWAN, "TW").build(),
                "");
    }

    @Override
    protected I18nDisplayNameProvider createTestObjectWithMaliciousContent(String content) {
        return new I18nDisplayNameProvider.Builder().add(Locale.ENGLISH, content).build();
    }
}
