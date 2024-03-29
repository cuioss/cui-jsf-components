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
package de.cuioss.jsf.api.components.partial;

import de.cuioss.jsf.api.components.css.StyleClassBuilder;
import de.cuioss.jsf.api.components.css.StyleClassProvider;

import javax.faces.component.UIComponent;

/**
 * <h2>Summary</h2>
 * <p>
 * Implementors of this class manage the state and resolving of the
 * styleClass-attribute. The implementation relies on the correct use of
 * attribute names, saying they must exactly match the accessor methods.
 * </p>
 * <h2>styleClass</h2>
 * <p>
 * Space-separated list of CSS style class(es) to be applied additionally when
 * this element is rendered.
 * </p>
 * <p>
 *
 * <h2>Update to the state-management</h2>
 * In previous versions targeted at mojarra, the logic of modifying the final style-class was in the method
 * {@link #getStyleClass()}.
 * With myfaces on the other hand the default Renderer use {@link UIComponent#getAttributes()} for looking up the
 * styleClass, bypassing the corresponding get-method.
 * The Solution:
 * <ul>
 *      <li>The configured class, {@link #setStyleClass(String)} will be stored under the keys
 *      {@value ComponentStyleClassProviderImpl#KEY} and {@value ComponentStyleClassProviderImpl#LOCAL_STYLE_CLASS_KEY}</li>
 *     <li>Component-specific additions are to be provided via {@link #computeAndStoreFinalStyleClass(StyleClassBuilder)}.
 *     This must be done prior Rendering, usually by the concrete {@link javax.faces.render.Renderer}</li>
 * </ul>
 * <em>This workaround is only necessary for cases, where the rendering is done by the concrete implementation.</em>
 *
 * @author Oliver Wolff
 */
public interface ComponentStyleClassProvider extends StyleClassProvider {

    /**
     * @param styleClass the styleClass to set.
     *                   See Type-Documentation for details.
     */
    void setStyleClass(String styleClass);

    /**
     * Computes the final / effective styleClass and exposes it as 'styleClass'
     * in the {@link javax.faces.component.StateHelper}
     * See Class-Documentation for details.
     *
     * @param componentSpecificStyleClass The style-classes created by the corresponding renderer,
     *                                    excluding the user-defined style-class,
     */
    void computeAndStoreFinalStyleClass(StyleClassBuilder componentSpecificStyleClass);
}
