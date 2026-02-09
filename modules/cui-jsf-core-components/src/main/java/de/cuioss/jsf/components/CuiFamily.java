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
package de.cuioss.jsf.components;

import de.cuioss.jsf.api.components.base.CuiComponentBase;
import de.cuioss.jsf.components.blockelement.BlockElementDecorator;
import de.cuioss.jsf.components.html.fieldset.FieldsetComponent;
import de.cuioss.jsf.components.inlineconfirm.InlineConfirmComponent;
import de.cuioss.jsf.components.typewatch.TypewatchComponent;
import lombok.experimental.UtilityClass;

/**
 * <p>Defines constants for component family and renderer identifiers for
 * the CUI JSF core components that are not related to Twitter Bootstrap.</p>
 *
 * <p>This class provides a centralized registry of component and renderer
 * constants used for component registration and lookup within the JSF context.
 * These constants are utilized when registering components and renderers
 * with the JSF runtime.</p>
 *
 * <p>Components defined here include:</p>
 * <ul>
 *   <li>{@link FieldsetComponent} - HTML5 fieldset component</li>
 *   <li>{@link TypewatchComponent} - Delay input event processing component</li>
 *   <li>{@link InlineConfirmComponent} - Inline confirmation component</li>
 *   <li>{@link BlockElementDecorator} - Block element decoration</li>
 * </ul>
 *
 * <p>This class is thread-safe as it contains only constants and no mutable state.</p>
 *
 * @author Oliver Wolff
 * @since 1.0
 */
@UtilityClass
public final class CuiFamily {

    /**
     * The component identifier for {@link FieldsetComponent}. Used to register
     * and lookup the component within the JSF context.
     */
    public static final String FIELDSET_COMPONENT = "de.cuioss.jsf.api.html.fieldset";

    /**
     * Default renderer identifier for {@link FieldsetComponent}. Used to associate
     * the renderer with the component during JSF initialization.
     */
    public static final String FIELDSET_RENDERER = "de.cuioss.jsf.api.html.fieldset_renderer";

    /**
     * The common component family identifier for all CUI components.
     * Inherits from {@link CuiComponentBase#COMPONENT_FAMILY} to maintain consistency
     * across the framework.
     */
    public static final String COMPONENT_FAMILY = CuiComponentBase.COMPONENT_FAMILY;

    /**
     * The component identifier for {@link TypewatchComponent}. This component
     * provides functionality to delay input event processing until a user
     * stops typing.
     */
    public static final String TYPEWATCH_COMPONENT = "de.cuioss.cui.components.typewatch";

    /**
     * The component identifier for {@link InlineConfirmComponent}. This component
     * provides an inline confirmation mechanism for user actions.
     */
    public static final String INLINE_CONFIRM_COMPONENT = "de.cuioss.cui.components.inline_confirm";

    /**
     * Default renderer identifier for {@link InlineConfirmComponent}. Used to associate
     * the renderer with the component during JSF initialization.
     */
    public static final String INLINE_CONFIRM_RENDERER = "de.cuioss.cui.components.inline_confirm_renderer";

    /**
     * The component identifier for {@link BlockElementDecorator}. This component
     * provides decoration capabilities for block elements.
     */
    public static final String BLOCKELEMENT_COMPONENT = "de.cuioss.jsf.components.blockelement";

}
