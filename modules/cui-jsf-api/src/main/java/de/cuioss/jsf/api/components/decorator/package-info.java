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
 * <h2>Component Decorator Package</h2>
 * <p>
 * Provides a framework for decorating JSF components using the decorator pattern.
 * Decorators are a specialized type of attached objects in JSF, conceptually similar to
 * {@link jakarta.faces.convert.Converter}, {@link jakarta.faces.validator.Validator},
 * and {@link jakarta.faces.component.behavior.ClientBehavior}.
 * </p>
 * <p>
 * The key difference from {@link jakarta.faces.component.behavior.ClientBehavior} is that
 * decorators don't react to client interactions but instead enhance or modify other components
 * in the component tree, typically their parent component.
 * </p>
 * 
 * <h3>Key Components</h3>
 * <ul>
 *   <li>{@link de.cuioss.jsf.api.components.decorator.AbstractParentDecorator} - Base class for creating decorators that modify their parent component</li>
 * </ul>
 * 
 * <h3>Typical Usage</h3>
 * <p>
 * Decorators are typically placed inside the component they should decorate:
 * </p>
 * <pre>
 * &lt;h:panelGroup&gt;
 *   &lt;cui:styleClassDecorator styleClass="panel panel-default"/&gt;
 *   Panel content here...
 * &lt;/h:panelGroup&gt;
 * </pre>
 * 
 * <h3>Implementation Pattern</h3>
 * <p>
 * Decorators are implemented by extending {@link de.cuioss.jsf.api.components.decorator.AbstractParentDecorator}
 * and implementing the decorate method to apply the desired modifications to the parent component.
 * The decoration is automatically applied when the component is added to the view.
 * </p>
 * 
 * <p>
 * Classes in this package generally depend on the JSF lifecycle and are therefore
 * not thread-safe by design, as they are intended to be used within the context of
 * a single request.
 * </p>
 *
 * @author Oliver Wolff
 * @since 1.0
 * @see jakarta.faces.component.behavior.ClientBehavior
 * @see de.cuioss.jsf.api.components.decorator.AbstractParentDecorator
 * @see de.cuioss.jsf.api.components.util.ComponentModifier
 */
package de.cuioss.jsf.api.components.decorator;
