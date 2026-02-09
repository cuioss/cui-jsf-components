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
 * <h2>Summary</h2>
 * <p>
 * Provides common helper classes / utilities that can be used for the creation
 * of {@link jakarta.faces.render.Renderer}
 * </p>
 * <h2>ConditionalResponseWriter</h2>
 * <p>
 * While A {@link jakarta.faces.render.Renderer} defines a procedural view on
 * creating partial html trees, like
 * {@link jakarta.faces.render.Renderer#encodeBegin(jakarta.faces.context.FacesContext, jakarta.faces.component.UIComponent)}
 * ,
 * {@link jakarta.faces.render.Renderer#encodeChildren(jakarta.faces.context.FacesContext, jakarta.faces.component.UIComponent)}
 * and
 * {@link jakarta.faces.render.Renderer#encodeEnd(jakarta.faces.context.FacesContext, jakarta.faces.component.UIComponent)}
 * The {@link de.cuioss.jsf.api.components.html.HtmlTreeBuilder} creates a
 * complete tree. In order to integrate this two approaches
 * {@link de.cuioss.jsf.api.components.html.HtmlTreeBuilder#withNodeChildBreakpoint()}
 * marks that part in the tree where the child elements are to be included. The
 * {@link de.cuioss.jsf.api.components.renderer.ConditionalResponseWriter} helps
 * now to write until this breakpoint, matches to
 * {@link jakarta.faces.render.Renderer#encodeBegin(jakarta.faces.context.FacesContext, jakarta.faces.component.UIComponent)}
 * and from that breakpoint on, matches to
 * {@link jakarta.faces.render.Renderer#encodeEnd(jakarta.faces.context.FacesContext, jakarta.faces.component.UIComponent)}
 * </p>
 * <h2>BaseDecoratorRenderer</h2>
 * <p>
 * Handles the default API-contract of {@link jakarta.faces.render.Renderer}. in
 * addition it creates an instance of
 * {@link de.cuioss.jsf.api.components.renderer.DecoratingResponseWriter} and
 * casts the {@link jakarta.faces.component.UIComponent} to the concrete type.
 * This simplifies the creation of concrete renderer a lot. You solely need to
 * implement one of
 * {@link de.cuioss.jsf.api.components.renderer.BaseDecoratorRenderer#doEncodeBegin(jakarta.faces.context.FacesContext, DecoratingResponseWriter, jakarta.faces.component.UIComponent)}
 * or
 * {@link de.cuioss.jsf.api.components.renderer.BaseDecoratorRenderer#doEncodeEnd(jakarta.faces.context.FacesContext, DecoratingResponseWriter, jakarta.faces.component.UIComponent)}
 * </p>
 * <h2>DecoratingResponseWriter</h2>
 * <p>
 * Used for providing additional convenience methods for response writer. The
 * api is similar to {@link de.cuioss.jsf.api.components.html.HtmlTreeBuilder}.
 * The component to be rendered is in a field of the
 * {@link de.cuioss.jsf.api.components.renderer.DecoratingResponseWriter} and
 * must therefore not be passed to the corresponding calls.
 * </p>
 *
 * @author Oliver Wolff
 */
package de.cuioss.jsf.api.components.renderer;
