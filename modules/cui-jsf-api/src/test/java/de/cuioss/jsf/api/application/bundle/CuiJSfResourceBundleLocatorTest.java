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

import static org.junit.jupiter.api.Assertions.assertTrue;

import de.cuioss.jsf.api.EnableJSFCDIEnvironment;
import de.cuioss.jsf.api.EnableResourceBundleSupport;
import de.cuioss.portal.common.bundle.ResourceBundleWrapper;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

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
