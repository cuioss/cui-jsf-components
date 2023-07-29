/**
 * <h2>ComponentModifier</h2> If you create components that dynamically change
 * attributes of other components like style, styleClass, disabled, etc. you
 * will find that JSF does not defines interfaces for that purpose. Usually
 * developer / library vendors use some wild casting here. In order to smoothen
 * this we introduced some interfaces like:
 * <ul>
 * <li>{@link de.cuioss.jsf.api.components.partial.ComponentStyleClassProvider}</li>
 * <li>{@link de.cuioss.jsf.api.components.partial.StyleAttributeProvider}</li>
 * <li>{@link de.cuioss.jsf.api.components.partial.TitleProvider}</li>
 * </ul>
 * The central Interface is
 * {@link de.cuioss.jsf.api.components.util.ComponentModifier}. In addition of
 * said interfaces it provides some more mechanisms to simplify interacting with
 * {@link javax.faces.component.UIComponent}s at runtime.
 *
 * In order to retrieve a modifier for a concrete component you can wrap any
 * component by calling
 * {@link de.cuioss.jsf.api.components.util.modifier.ComponentModifierFactory#findFittingWrapper(javax.faces.component.UIComponent)}
 *
 * @author Oliver Wolff
 *
 */
package de.cuioss.jsf.api.components.util.modifier;
