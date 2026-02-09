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
package de.cuioss.jsf.test;

import de.cuioss.portal.common.bundle.ResourceBundleWrapper;
import jakarta.enterprise.context.RequestScoped;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serial;
import java.util.Collections;
import java.util.Set;

/**
 * Mock implementation of {@link ResourceBundleWrapper} that returns keys as values.
 * <p>
 * This class serves as a predictable test replacement for resource bundles in unit tests.
 * Instead of performing actual message lookups or translations, it simply returns the
 * provided key as the value. This behavior makes it easy to verify that the correct
 * keys are being requested without needing actual message resources.
 * </p>
 * <p>
 * The implementation is minimal and stateless, returning:
 * </p>
 * <ul>
 * <li>The key itself when {@link #getString(String)} is called</li>
 * <li>An empty set for {@link #keySet()}</li>
 * <li>A fixed string "mirror" for {@link #getBundleContent()}</li>
 * </ul>
 * <p>
 * This class is typically used in conjunction with {@link EnableResourceBundleSupport}
 * to provide resource bundle capabilities in a test environment.
 * </p>
 * <p>
 * Usage example:
 * </p>
 * <pre>
 * {@code
 * @EnableJSFCDIEnvironment
 * @EnableResourceBundleSupport  // Registers MirrorCuiRessourcBundle
 * class ResourceTest {
 *     
 *     @Inject
 *     private ResourceBundleWrapper resourceBundle;
 *     
 *     @Test
 *     void shouldReturnKeyAsValue() {
 *         String key = "some.message.key";
 *         assertEquals(key, resourceBundle.getString(key));
 *     }
 * }
 * }
 * </pre>
 * <p>
 * This class is thread-safe as it contains no mutable state.
 * </p>
 *
 * @author Oliver Wolff
 * @since 1.0
 */
@RequestScoped
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public class MirrorCuiRessourcBundle implements ResourceBundleWrapper {

    @Serial
    private static final long serialVersionUID = 7035144185855294606L;

    /**
     * Returns the key as the value.
     * <p>
     * This implementation simply returns the provided key, making it easy to
     * verify that the expected keys are being requested.
     * </p>
     *
     * @param key the resource key to look up
     * @return the key itself, unchanged
     */
    @Override
    public String getString(String key) {
        return key;
    }

    /**
     * Returns an empty set of keys.
     * <p>
     * Since this is a mock implementation, it doesn't have actual keys.
     * </p>
     *
     * @return an empty, unmodifiable set
     */
    @Override
    public Set<String> keySet() {
        return Collections.emptySet();
    }

    /**
     * Returns a fixed string indicating this is the mirror implementation.
     *
     * @return the string "mirror" 
     */
    @Override
    public String getBundleContent() {
        return "mirror";
    }

}
