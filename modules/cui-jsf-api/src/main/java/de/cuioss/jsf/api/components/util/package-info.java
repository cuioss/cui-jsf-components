/**
 * <h2>Summary</h2>
 * <p>
 * Provides common helper classes / utilities that can be used for the creation
 * of {@link jakarta.faces.component.UIComponent}
 * </p>
 * <h2>ComponentModifier</h2>
 * <p>
 * Helper class that lays an interface over some generic methods of
 * JSF-Components like is/set Disable and set/getStyleclass.
 * </p>
 * <h2>ComponentUtility</h2>
 * <p>
 * Provides some more advanced utilities for interacting with the
 * component-tree, e.g.
 * {@link de.cuioss.jsf.api.components.util.ComponentUtility#findCorrespondingForm(jakarta.faces.component.UIComponent)}
 * ,
 * {@link de.cuioss.jsf.api.components.util.ComponentUtility#findNearestNamingContainer(jakarta.faces.component.UIComponent)}
 * {@link de.cuioss.jsf.api.components.util.ComponentUtility#resetEditableValueHolder(jakarta.faces.component.UIForm, jakarta.faces.context.FacesContext)}
 * or
 * {@link de.cuioss.jsf.api.components.util.ComponentUtility#setEditableValueHoldersValid(jakarta.faces.component.UIForm, jakarta.faces.context.FacesContext)}
 * </p>
 *
 * @author Oliver Wolff
 */
package de.cuioss.jsf.api.components.util;
