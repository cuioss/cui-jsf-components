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
package de.cuioss.jsf.api.application.bundle;

import static org.junit.jupiter.api.Assertions.assertTrue;

import de.cuioss.jsf.api.EnableJSFCDIEnvironment;
import de.cuioss.jsf.api.EnableResourceBundleSupport;
import de.cuioss.portal.common.bundle.ResourceBundleWrapper;
import jakarta.inject.Inject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@EnableJSFCDIEnvironment
@EnableResourceBundleSupport
@DisplayName("Tests for CuiJSfResourceBundleLocator")
class CuiJSfResourceBundleLocatorTest {

    @Inject
    private ResourceBundleWrapper bundleWrapper;

    @Test
    @DisplayName("Should contain required message keys")
    void shouldContainRequiredMessageKeys() {
        // Act & Assert
        assertTrue(bundleWrapper.keySet().contains("message.error.request"),
                "Bundle should contain the error message key");
    }

}
