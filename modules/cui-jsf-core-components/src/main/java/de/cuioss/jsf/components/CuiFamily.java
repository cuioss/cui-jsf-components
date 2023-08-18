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
package de.cuioss.jsf.components;

import de.cuioss.jsf.api.components.base.CuiComponentBase;
import de.cuioss.jsf.components.blockelement.BlockElementDecorator;
import de.cuioss.jsf.components.html.fieldset.FieldsetComponent;
import de.cuioss.jsf.components.inlineconfirm.InlineConfirmComponent;
import de.cuioss.jsf.components.typewatch.TypewatchComponent;
import lombok.experimental.UtilityClass;

/**
 * Simple Container for identifying cui components that are not related to
 * twitter bootstrap
 *
 * @author Oliver Wolff
 */
@UtilityClass
public final class CuiFamily {

    /** The component for {@link FieldsetComponent} */
    public static final String FIELDSET_COMPONENT = "de.cuioss.jsf.api.html.fieldset";

    /** Default Renderer for {@link FieldsetComponent} */
    public static final String FIELDSET_RENDERER = "de.cuioss.jsf.api.html.fieldset_renderer";

    /** de.cuioss.jsf.api.html.family */
    public static final String COMPONENT_FAMILY = CuiComponentBase.COMPONENT_FAMILY;

    /** The component for {@link TypewatchComponent} */
    public static final String TYPEWATCH_COMPONENT = "de.cuioss.cui.components.typewatch";

    /** The component for {@link InlineConfirmComponent} */
    public static final String INLINE_CONFIRM_COMPONENT = "de.cuioss.cui.components.inline_confirm";

    /** Default Renderer for {@link InlineConfirmComponent} */
    public static final String INLINE_CONFIRM_RENDERER = "de.cuioss.cui.components.inline_confirm_renderer";

    /** The component for {@link BlockElementDecorator} */
    public static final String BLOCKELEMENT_COMPONENT = "de.cuioss.jsf.components.blockelement";

}
