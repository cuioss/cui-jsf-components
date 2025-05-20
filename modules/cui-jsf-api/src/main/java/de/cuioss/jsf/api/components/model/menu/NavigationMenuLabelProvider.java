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
package de.cuioss.jsf.api.components.model.menu;

/**
 * <h2>Interface for menu items that provide display labels</h2>
 * <p>
 * This interface defines the contract for menu elements that need to display
 * a label that may differ from their title. It is particularly useful for
 * container-type menu items where the label might be used for the container header
 * while titles might be used for accessibility or tooltips.
 * </p>
 * <p>
 * The interface supports both direct label values and resource bundle keys
 * for internationalization, and provides a method to retrieve the resolved
 * label text ready for display.
 * </p>
 * <p>
 * This separation between titles and labels allows for more flexible UI
 * representations of menu elements.
 * </p>
 *
 * @author Sven Haag
 */
public interface NavigationMenuLabelProvider {

    /**
     * <p>Retrieves the explicit label value.</p>
     * <p>This is a direct label value that can be used instead of a 
     * resource bundle key for non-internationalized labels.</p>
     *
     * @return The explicit label value. May be {@code null} if no direct
     * value was specified.
     */
    String getLabelValue();

    /**
     * <p>Retrieves the resource bundle key for the label.</p>
     * <p>This key can be used to look up internationalized label text
     * in a resource bundle.</p>
     *
     * @return The resource bundle key for the label. May be {@code null}
     * if no key was specified.
     */
    String getLabelKey();

    /**
     * <p>Retrieves the resolved label text ready for display.</p>
     * <p>This method returns the actual text that should be displayed in the UI,
     * which may be resolved from a resource bundle if a label key was specified
     * or directly from the label value otherwise.</p>
     *
     * @return The resolved, display-ready label text. May be {@code null}
     * if no label is available.
     */
    String getResolvedLabel();
}
