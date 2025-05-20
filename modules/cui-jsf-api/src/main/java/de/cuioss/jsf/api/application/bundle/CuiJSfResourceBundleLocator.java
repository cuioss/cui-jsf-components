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

import de.cuioss.portal.common.bundle.ResourceBundleLocator;
import de.cuioss.portal.common.priority.PortalPriorities;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.util.Optional;

/**
 * Implementation of {@link ResourceBundleLocator} that provides access to the core JSF message bundle
 * "de.cuioss.jsf.api.l18n.messages" for the CUI JSF components.
 * <p>
 * This locator is configured with the priority level {@link PortalPriorities#PORTAL_CORE_LEVEL},
 * ensuring it is loaded appropriately within the portal's resource bundle hierarchy.
 * </p>
 * <p>
 * This class is thread-safe as it is immutable and contains no mutable state.
 * </p>
 *
 * @author Matthias Walliczek
 * @since 1.0
 */
@Priority(PortalPriorities.PORTAL_CORE_LEVEL)
@ApplicationScoped
@EqualsAndHashCode
public class CuiJSfResourceBundleLocator implements ResourceBundleLocator {

    @Serial
    private static final long serialVersionUID = -8478481710191113463L;

    /**
     * The path to the core JSF message bundle for CUI components.
     * This constant defines the location of the properties file containing 
     * the localized messages.
     */
    private static final String PATH = "de.cuioss.jsf.api.l18n.messages";

    /**
     * Provides the path to the JSF message bundle.
     * <p>
     * This implementation always returns a non-empty Optional containing the path 
     * to the core JSF message bundle.
     * </p>
     * 
     * @return An {@link Optional} containing the bundle path, never empty
     */
    @Override
    public Optional<String> getBundlePath() {
        return Optional.of(PATH);
    }

    /**
     * Returns a string representation of this locator.
     * <p>
     * The string representation includes the class name and the bundle path.
     * </p>
     * 
     * @return A string representing this locator
     */
    @Override
    public String toString() {
        return "%s: Path='%s'".formatted(getClass().getName(), PATH);
    }
}
