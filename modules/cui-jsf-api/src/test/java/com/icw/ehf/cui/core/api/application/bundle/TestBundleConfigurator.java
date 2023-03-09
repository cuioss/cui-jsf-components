package com.icw.ehf.cui.core.api.application.bundle;

import java.util.ArrayList;
import java.util.List;

import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.test.jsf.config.ApplicationConfigurator;
import de.cuioss.test.jsf.config.decorator.ApplicationConfigDecorator;

@SuppressWarnings("javadoc")
public class TestBundleConfigurator
        implements ApplicationConfigurator, TypedGenerator<ResourceBundleWrapper> {

    public static final String TEST_BUNDLE_BASE_PATH = "com.icw.ehf.cui.components.bundle.";

    public static final String BUNDLE1_NAME = "bundle1";

    public static final String BUNDLE2_NAME = "bundle2";

    public static final String TEST_BUNDLE_NAME = "testBundle";

    @Override
    public void configureApplication(final ApplicationConfigDecorator decorator) {
        decorator.registerResourceBundle(BUNDLE1_NAME, TEST_BUNDLE_BASE_PATH + BUNDLE1_NAME);
        decorator.registerResourceBundle(BUNDLE2_NAME, TEST_BUNDLE_BASE_PATH + BUNDLE2_NAME);
        decorator.registerResourceBundle(TEST_BUNDLE_NAME,
                TEST_BUNDLE_BASE_PATH + TEST_BUNDLE_NAME);
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
