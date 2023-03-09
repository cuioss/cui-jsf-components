/**
 * <h2>Summary</h2>
 * <p>
 * Provides common helper classes / utilities that can be used for the creation
 * of {@link javax.faces.render.Renderer}
 * </p>
 * <h2>ConditionalResponseWriter</h2>
 * <p>
 * While A {@link javax.faces.render.Renderer} defines a procedural view on
 * creating partial html trees, like
 * {@link javax.faces.render.Renderer#encodeBegin(javax.faces.context.FacesContext, javax.faces.component.UIComponent)}
 * ,
 * {@link javax.faces.render.Renderer#encodeChildren(javax.faces.context.FacesContext, javax.faces.component.UIComponent)}
 * and
 * {@link javax.faces.render.Renderer#encodeEnd(javax.faces.context.FacesContext, javax.faces.component.UIComponent)}
 * The {@link com.icw.ehf.cui.core.api.components.html.HtmlTreeBuilder} creates
 * a complete tree. In order to integrate this two approaches
 * {@link com.icw.ehf.cui.core.api.components.html.HtmlTreeBuilder#withNodeChildBreakpoint()}
 * marks that part in the tree where the child elements are to be included. The
 * {@link com.icw.ehf.cui.core.api.components.renderer.ConditionalResponseWriter}
 * helps now to write until this breakpoint, matches to
 * {@link javax.faces.render.Renderer#encodeBegin(javax.faces.context.FacesContext, javax.faces.component.UIComponent)}
 * and from that breakpoint on, matches to
 * {@link javax.faces.render.Renderer#encodeEnd(javax.faces.context.FacesContext, javax.faces.component.UIComponent)}
 * </p>
 * <h2>BaseDecoratorRenderer</h2>
 * <p>
 * Handles the default API-contract of {@link javax.faces.render.Renderer}. in addition it creates
 * an instance of
 * {@link com.icw.ehf.cui.core.api.components.renderer.DecoratingResponseWriter}
 * and casts the {@link javax.faces.component.UIComponent} to the concrete type.
 * This simplifies the creation of concrete renderer a lot. You solely need to
 * implement one of
 * {@link com.icw.ehf.cui.core.api.components.renderer.BaseDecoratorRenderer#doEncodeBegin(javax.faces.context.FacesContext, DecoratingResponseWriter, javax.faces.component.UIComponent)}
 * or
 * {@link com.icw.ehf.cui.core.api.components.renderer.BaseDecoratorRenderer#doEncodeEnd(javax.faces.context.FacesContext, DecoratingResponseWriter, javax.faces.component.UIComponent)}
 * </p>
 * <h2>DecoratingResponseWriter</h2>
 * <p>
 * Used for providing additional convenience methods for response writer. The
 * api is similar to {@link com.icw.ehf.cui.core.api.components.html.HtmlTreeBuilder}. The component
 * to be rendered is in
 * a field of the
 * {@link com.icw.ehf.cui.core.api.components.renderer.DecoratingResponseWriter}
 * and must therefore not be passed to the corresponding calls.
 * </p>
 *
 * @author Oliver Wolff
 */
package com.icw.ehf.cui.core.api.components.renderer;
