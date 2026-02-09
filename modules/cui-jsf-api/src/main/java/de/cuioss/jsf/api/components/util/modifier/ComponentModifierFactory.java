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
package de.cuioss.jsf.api.components.util.modifier;

import static java.util.Objects.requireNonNull;

import de.cuioss.jsf.api.components.util.ComponentModifier;
import de.cuioss.tools.logging.CuiLogger;
import jakarta.faces.component.UIComponent;
import lombok.experimental.UtilityClass;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * <p>Factory for creating component modifiers that provide a unified interface for
 * manipulating JSF components.</p>
 * 
 * <p>This factory uses a layered approach to find the most appropriate {@link ComponentModifier}
 * implementation for a given component:</p>
 * 
 * <ol>
 *   <li>First, it checks for custom implementations registered via the Java Service Provider
 *       Interface (SPI) mechanism</li>
 *   <li>If no SPI-registered modifier is found, it checks if the component implements any of
 *       the known CUI interfaces and creates an interface-based modifier</li>
 *   <li>As a fallback, it creates a reflection-based modifier that can work with any component</li>
 * </ol>
 * 
 * <p>This approach ensures that components can be manipulated in a consistent way regardless
 * of their specific implementation details, while still allowing for optimized handling of
 * known component types.</p>
 * 
 * <p>Example usage:</p>
 * <pre>
 * UIComponent myComponent = ...;
 * ComponentModifier modifier = ComponentModifierFactory.findFittingWrapper(myComponent);
 * 
 * // Now use the consistent API to manipulate the component
 * if (modifier.supportsStyleClass()) {
 *     modifier.addStyleClass("my-custom-class");
 * }
 * 
 * if (modifier.supportsDisabled()) {
 *     modifier.setDisabled(true);
 * }
 * </pre>
 *
 * @author Oliver Wolff
 */
@UtilityClass
public class ComponentModifierFactory {

    /**
     * Logger for this class, following CUI logging standards.
     */
    private static final CuiLogger LOGGER = new CuiLogger(ComponentModifierFactory.class);

    /**
     * <p>Retrieves the most appropriate {@link ComponentModifier} implementation for
     * the given UIComponent.</p>
     * 
     * <p>The method follows a three-step resolution process:</p>
     * <ol>
     *   <li>Check for modifiers registered through the Java SPI mechanism</li>
     *   <li>Check for interface-based modifiers that can handle components implementing
     *       standard CUI interfaces</li>
     *   <li>Fall back to a reflection-based modifier that works with any component</li>
     * </ol>
     *
     * <p>This ensures that components always get the most optimized modifier implementation
     * available while still guaranteeing that every component can be handled.</p>
     *
     * @param toBeWrapped The UIComponent to find a modifier for. Must not be {@code null}.
     * 
     * @return An appropriate {@link ComponentModifier} implementation for the given component.
     *         Will never return {@code null}, as a reflection-based fallback is always available.
     *         
     * @throws NullPointerException If toBeWrapped is {@code null}
     */
    public static ComponentModifier findFittingWrapper(final UIComponent toBeWrapped) {
        requireNonNull(toBeWrapped);

        LOGGER.trace("Resolving for %s. First try from SPI", toBeWrapped.getClass());

        var iterator = loadResolver();
        while (iterator.hasNext()) {
            var resolved = iterator.next().wrap(toBeWrapped);
            if (resolved.isPresent()) {
                LOGGER.trace("Resolved %s for %s", resolved.get().getClass(), toBeWrapped.getClass());
                return resolved.get();
            }
        }
        LOGGER.trace("Not Found by SPI, checking interfaces-based");
        var wrapper = CuiInterfaceBasedModifier.wrap(toBeWrapped);
        return wrapper.orElse(new ReflectionBasedModifier(toBeWrapped));
    }

    /**
     * <p>Loads all {@link ComponentModifierResolver} implementations registered via the
     * Java Service Provider Interface (SPI) mechanism.</p>
     * 
     * <p>This method uses the standard Java {@link ServiceLoader} to discover
     * implementations of the {@link ComponentModifierResolver} interface that
     * are registered in META-INF/services.</p>
     * 
     * @return An iterator of available {@link ComponentModifierResolver} implementations
     */
    static Iterator<ComponentModifierResolver> loadResolver() {
        ServiceLoader<ComponentModifierResolver> loader = ServiceLoader.load(ComponentModifierResolver.class);
        return loader.iterator();
    }
}
