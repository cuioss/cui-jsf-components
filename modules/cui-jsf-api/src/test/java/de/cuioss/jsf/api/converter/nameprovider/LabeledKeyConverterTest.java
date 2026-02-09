/*
 * Copyright © 2025 CUI-OpenSource-Software (info@cuioss.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.api.converter.nameprovider;

import static de.cuioss.tools.collect.CollectionLiterals.immutableList;

import de.cuioss.jsf.api.CoreJsfTestConfiguration;
import de.cuioss.jsf.api.EnableJSFCDIEnvironment;
import de.cuioss.jsf.api.EnableResourceBundleSupport;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.converter.AbstractConverterTest;
import de.cuioss.test.jsf.converter.TestItems;
import de.cuioss.test.jsf.junit5.EnableJsfEnvironment;
import de.cuioss.uimodel.nameprovider.LabeledKey;
import org.jboss.weld.junit5.ExplicitParamInjection;

@EnableJsfEnvironment
@JsfTestConfiguration(CoreJsfTestConfiguration.class)
@EnableJSFCDIEnvironment
@EnableResourceBundleSupport
@ExplicitParamInjection
class LabeledKeyConverterTest extends AbstractConverterTest<LabeledKeyConverter, LabeledKey> {

    protected static final String MESSAGE_KEY = "de.cuioss.common.email.invalid";

    protected static final String MESSAGE_VALUE = "invalid e-Mail Address syntax";

    @Override
    public void populate(final TestItems<LabeledKey> testItems) {
        testItems.addValidObjectWithStringResult(new LabeledKey(MESSAGE_KEY), MESSAGE_VALUE)
                .addValidObjectWithStringResult(new LabeledKey(MESSAGE_KEY, immutableList("1")), MESSAGE_VALUE);

    }

}
