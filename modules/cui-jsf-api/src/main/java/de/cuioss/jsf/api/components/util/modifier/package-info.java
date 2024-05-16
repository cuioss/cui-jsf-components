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
