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
import de.cuioss.jsf.api.EnableJSFCDIEnvironment;
import de.cuioss.jsf.api.EnableResourceBundleSupport;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.converter.AbstractConverterTest;
import de.cuioss.test.jsf.converter.TestItems;
import de.cuioss.test.jsf.junit5.EnableJsfEnvironment;
import de.cuioss.uimodel.nameprovider.DisplayMessageProvider;
import org.jboss.weld.junit5.ExplicitParamInjection;
import org.junit.jupiter.api.DisplayName;

@EnableJsfEnvironment
@JsfTestConfiguration(CoreJsfTestConfiguration.class)
@EnableJSFCDIEnvironment
@EnableResourceBundleSupport
@ExplicitParamInjection
@DisplayName("Tests for DisplayMessageProviderConverter")
class DisplayMessageProviderConverterTest
        extends AbstractConverterTest<DisplayMessageProviderConverter, DisplayMessageProvider> {

    // Message key to be resolved from resource bundle
    protected static final String MESSAGE_KEY = "de.cuioss.common.email.invalid";

    // Expected resolved message value
    protected static final String MESSAGE_VALUE = "invalid e-Mail Address syntax";

    @Override
    @DisplayName("Configure test cases for DisplayMessageProvider conversion")
    public void populate(final TestItems<DisplayMessageProvider> testItems) {
        // Test that a DisplayMessageProvider with a message key and parameter is correctly converted to a string
        // The parameter (10) is not used in the message, so it doesn't affect the output
        testItems.addValidObjectWithStringResult(
                new DisplayMessageProvider.Builder().messageKey(MESSAGE_KEY).add(10).build(), MESSAGE_VALUE);
    }
}
