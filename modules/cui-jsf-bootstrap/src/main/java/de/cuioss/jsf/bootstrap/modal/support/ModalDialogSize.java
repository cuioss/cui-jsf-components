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
package de.cuioss.jsf.bootstrap.modal.support;

import static de.cuioss.tools.string.MoreStrings.nullToEmpty;

import de.cuioss.jsf.api.components.css.StyleClassProvider;
import de.cuioss.tools.string.MoreStrings;
import lombok.Getter;

/**
 * <p>Represents the different size options available for Bootstrap modal dialogs. This enum
 * provides a type-safe way to specify modal dialog sizes in accordance with Bootstrap's
 * modal size classes.</p>
 * 
 * <h2>Available Sizes</h2>
 * <ul>
 *   <li>{@link #DEFAULT} - Standard modal size, no specific size class applied</li>
 *   <li>{@link #LG} - Large modal, applies "modal-lg" class</li>
 *   <li>{@link #SM} - Small modal, applies "modal-sm" class</li>
 *   <li>{@link #FLUID} - Full-width fluid modal, applies "modal-fluid" class</li>
 * </ul>
 * 
 * <h2>Usage</h2>
 * <p>This enum is typically used with modal dialog size provider interfaces
 * and can be set directly on modal dialog components:</p>
 * 
 * <pre>
 * &lt;boot:modalDialog id="largeDialog" size="lg"&gt;
 *   &lt;!-- dialog content --&gt;
 * &lt;/boot:modalDialog&gt;
 * </pre>
 * 
 * <p>Or programmatically:</p>
 * 
 * <pre>
 * modalDialog.setSize(ModalDialogSize.LG.name().toLowerCase());
 * </pre>
 * 
 * <p>The enum implements {@link StyleClassProvider} to provide the appropriate CSS class
 * for each size option.</p>
 *
 * @author Oliver Wolff
 * @since 1.0
 * @see de.cuioss.jsf.bootstrap.modal.ModalDialogComponent
 */
@Getter
public enum ModalDialogSize implements StyleClassProvider {

    /**
     * The default modal size with standard width and behavior.
     * Doesn't apply any additional CSS class.
     */
    DEFAULT(""),

    /**
     * Large modal dialog size. 
     * Applies the "modal-lg" CSS class to create a wider modal.
     */
    LG("lg"),

    /**
     * Small modal dialog size.
     * Applies the "modal-sm" CSS class to create a narrower modal.
     */
    SM("sm"),

    /**
     * Fluid modal dialog size that spans nearly full width of the viewport.
     * Applies the "modal-fluid" CSS class.
     */
    FLUID("fluid");

    /**
     * The prefix used for creating modal size CSS classes.
     */
    static final String PREFIX = "modal-";

    /**
     * The CSS class that should be applied for this size option.
     */
    private final String styleClass;

    /**
     * Constructor that creates the appropriate CSS class based on the size suffix.
     * 
     * @param suffix The size suffix to be appended to "modal-" prefix. Empty string for default size.
     */
    ModalDialogSize(final String suffix) {
        if (MoreStrings.isEmpty(suffix)) {
            styleClass = "";
        } else {
            styleClass = PREFIX + suffix;
        }
    }

    /**
     * <p>Converts a string representation to the corresponding {@link ModalDialogSize} enum value.</p>
     * 
     * <p>The case-insensitive string must match one of:</p>
     * <ul>
     *   <li>"default" (or empty/null) - for {@link #DEFAULT}</li>
     *   <li>"lg" - for {@link #LG}</li>
     *   <li>"sm" - for {@link #SM}</li>
     *   <li>"fluid" - for {@link #FLUID}</li>
     * </ul>
     * 
     * @param size The string representation of the size. Can be null or empty string,
     *             which will return {@link #DEFAULT}, otherwise must be one of
     *             "lg", "sm", "default" or "fluid" (case insensitive)
     * @return The corresponding {@link ModalDialogSize} enum value
     * @throws IllegalArgumentException If the provided string doesn't match any of the enum values
     */
    public static ModalDialogSize getFromString(final String size) {
        if (nullToEmpty(size).trim().isEmpty()) {
            return ModalDialogSize.DEFAULT;
        }
        return valueOf(size.toUpperCase());
    }
}
