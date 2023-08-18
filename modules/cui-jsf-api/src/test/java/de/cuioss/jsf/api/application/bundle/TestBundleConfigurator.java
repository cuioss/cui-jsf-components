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
package de.cuioss.jsf.api.application.bundle;

import java.util.ArrayList;
import java.util.List;

import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.test.jsf.config.ApplicationConfigurator;
import de.cuioss.test.jsf.config.decorator.ApplicationConfigDecorator;

@SuppressWarnings("javadoc")
public class TestBundleConfigurator implements ApplicationConfigurator, TypedGenerator<ResourceBundleWrapper> {

    public static final String TEST_BUNDLE_BASE_PATH = "de.cuioss.jsf.components.bundle.";

    public static final String BUNDLE1_NAME = "bundle1";

    public static final String BUNDLE2_NAME = "bundle2";

    public static final String TEST_BUNDLE_NAME = "testBundle";

    @Override
    public void configureApplication(final ApplicationConfigDecorator decorator) {
        decorator.registerResourceBundle(BUNDLE1_NAME, TEST_BUNDLE_BASE_PATH + BUNDLE1_NAME);
        decorator.registerResourceBundle(BUNDLE2_NAME, TEST_BUNDLE_BASE_PATH + BUNDLE2_NAME);
        decorator.registerResourceBundle(TEST_BUNDLE_NAME, TEST_BUNDLE_BASE_PATH + TEST_BUNDLE_NAME);
    }

    public static ResourceBundleWrapperImpl getTestBundleWrapper() {
        final var wrapper = new ResourceBundleWrapperImpl();
        final List<String> bundleNames = new ArrayList<>();
        bundleNames.add(BUNDLE1_NAME);
        bundleNames.add(BUNDLE2_NAME);
        if (Generators.booleans().next()) {
            bundleNames.add(TEST_BUNDLE_NAME);
        }
        wrapper.setResourceBundleNames(bundleNames);
        return wrapper;
    }

    @Override
    public ResourceBundleWrapper next() {
        return getTestBundleWrapper();
    }

    @Override
    public Class<ResourceBundleWrapper> getType() {
        return ResourceBundleWrapper.class;
    }
}
