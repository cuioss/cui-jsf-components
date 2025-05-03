/**
 * <h2>Summary</h2>
 * Provides common helper classes / utilities that can be used
 * for the creation of partial html-trees. The central class is
 * {@link de.cuioss.jsf.api.components.html.HtmlTreeBuilder} with a number of
 * fluent-style methods for creating the trees. A typical using-code is
 *
 * <pre>
 * {@code
 * new HtmlTreeBuilder().withNode(Node.DIV).withTextContent("Some Text Child");
 * }
 * </pre>
 * <p>
 * The result can be directly passed to a
 * {@link jakarta.faces.context.ResponseWriter} in order to integrate with the
 * creation of {@link jakarta.faces.render.Renderer}
 *
 * @author Oliver Wolff
 */
package de.cuioss.jsf.api.components.html;
