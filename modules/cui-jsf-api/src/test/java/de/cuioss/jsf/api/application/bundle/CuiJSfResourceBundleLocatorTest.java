package de.cuioss.jsf.api.application.bundle;

import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.EnableJSFCDIEnvironment;
import de.cuioss.jsf.api.EnableResourceBundleSupport;
import de.cuioss.portal.common.bundle.ResourceBundleWrapper;

@EnableJSFCDIEnvironment
@EnableResourceBundleSupport
class CuiJSfResourceBundleLocatorTest {

    @Inject
    private ResourceBundleWrapper bundleWrapper;

    @Test
    void test() {
        assertTrue(bundleWrapper.keySet().contains("message.error.request"));
    }

}
