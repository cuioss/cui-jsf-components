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
/**
 * <h2>ComponentModifier</h2> If you create components that dynamically change
 * attributes of other components like style, styleClass, disabled, etc. you
 * will find that JSF does not define interfaces for that purpose.
 * Usually developer / library vendors use some wild casting here.
 * To smoothen this, we introduced some interfaces like:
 * <ul>
 * <li>{@link de.cuioss.jsf.api.components.partial.ComponentStyleClassProvider}</li>
 * <li>{@link de.cuioss.jsf.api.components.partial.StyleAttributeProvider}</li>
 * <li>{@link de.cuioss.jsf.api.components.partial.TitleProvider}</li>
 * </ul>
 * The central Interface is
 * {@link de.cuioss.jsf.api.components.util.ComponentModifier}.
 * In addition, of said interfaces it provides some more mechanisms to simplify interacting with
 * {@link jakarta.faces.component.UIComponent}s at runtime.
 * <p>
 * To retrieve a modifier for a concrete component, you can wrap any component by calling
 * {@link de.cuioss.jsf.api.components.util.modifier.ComponentModifierFactory#findFittingWrapper(jakarta.faces.component.UIComponent)}
 *
 * @author Oliver Wolff
 */
package de.cuioss.jsf.api.components.util.modifier;
