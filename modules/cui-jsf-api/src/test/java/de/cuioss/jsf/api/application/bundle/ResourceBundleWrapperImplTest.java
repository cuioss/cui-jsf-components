package de.cuioss.jsf.api.application.bundle;

import static de.cuioss.tools.collect.CollectionLiterals.mutableList;
import static de.cuioss.tools.collect.CollectionLiterals.mutableSet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Collections;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Set;

import org.junit.jupiter.api.Test;

import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.junit5.EnableJsfEnvironment;
import de.cuioss.test.jsf.junit5.JsfEnabledTestEnvironment;
import de.cuioss.test.valueobjects.api.generator.PropertyGenerator;
import de.cuioss.test.valueobjects.api.property.PropertyReflectionConfig;

@JsfTestConfiguration(TestBundleConfigurator.class)
@PropertyGenerator(TestBundleConfigurator.class)
@PropertyReflectionConfig(exclude = "keys")
@EnableJsfEnvironment(useIdentityResourceBundle = false)
class ResourceBundleWrapperImplTest extends JsfEnabledTestEnvironment {

    public static final String TEST_BUNDLE_BASE_PATH = "de.cuioss.jsf.components.bundle.";

    public static final String BUNDLE1_NAME = "bundle1";

    public static final String BUNDLE2_NAME = "bundle2";

    private final Set<String> containedKeys = mutableSet("bundle1.property1", "bundle1.property2",
            "bundle1.property3", "bundle2.property1", "bundle2.property2", "bundle2.property3", "common.property");

    @Test
    void testGetMessage() {
        var bundleWrapper = TestBundleConfigurator.getTestBundleWrapper();
        assertEquals("bundle1.value1", bundleWrapper.getMessage("bundle1.property1"));
        assertEquals("bundle2.value1", bundleWrapper.getMessage("bundle2.property1"));
        assertEquals("From property1", bundleWrapper.getMessage("common.property"));
    }

    @Test
    void shouldFailOnInvalidKey() {
        assertThrows(MissingResourceException.class, () -> TestBundleConfigurator.getTestBundleWrapper().getMessage("not.there"));
    }

    @Test
    void testGetKeys() {
        final List<String> keys = Collections.list(TestBundleConfigurator.getTestBundleWrapper().getKeys());
        assertNotNull(keys);
        assertTrue(keys.size() > 6);
        assertTrue(keys.containsAll(containedKeys));
    }

    @Test
    void shouldDeseserializeCorrectly() {
        var bundleWrapper = TestBundleConfigurator.getTestBundleWrapper();
        bundleWrapper.setResourceBundleNames(mutableList(BUNDLE1_NAME, BUNDLE2_NAME));
        bundleWrapper.getResourceBundles().clear();
        assertEquals("bundle1.value1", bundleWrapper.getMessage("bundle1.property1"));
        assertEquals("bundle2.value1", bundleWrapper.getMessage("bundle2.property1"));
        assertEquals("From property1", bundleWrapper.getMessage("common.property"));
        // Now mimic invalid configuration
        bundleWrapper.getResourceBundles().clear();
        bundleWrapper.getResourceBundleNames().clear();
        try {
            bundleWrapper.getMessage("bundle1.property1");
            fail("Should have thrown exception");
        } catch (final MissingResourceException e) {
            assertNotNull(e);
        }
    }

}
