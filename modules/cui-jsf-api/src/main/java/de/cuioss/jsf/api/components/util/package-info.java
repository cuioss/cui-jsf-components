/**
 * <h2>Components Utility Package</h2>
 * <p>
 * This package provides utility classes and helper components that simplify working with
 * JSF components and the component tree. The utilities in this package focus on common tasks
 * related to component manipulation, state management, tree traversal, and component property access.
 * </p>
 * 
 * <h3>Core Utility Classes</h3>
 * <ul>
 *   <li>{@link de.cuioss.jsf.api.components.util.ComponentUtility} - Central utility class for
 *       component tree operations like finding parent forms, naming containers, handling AJAX requests,
 *       and manipulating EditableValueHolder components</li>
 *   <li>{@link de.cuioss.jsf.api.components.util.CuiState} - Extension to OmniFaces State helper
 *       that provides additional convenience methods for working with JSF component state</li>
 *   <li>{@link de.cuioss.jsf.api.components.util.KeyMappingUtility} - Helper for creating composite
 *       key strings, useful for unique component identifiers</li>
 * </ul>
 * 
 * <h3>Component Modification and Access</h3>
 * <ul>
 *   <li>{@link de.cuioss.jsf.api.components.util.ComponentModifier} - Interface defining common
 *       operations for manipulating JSF component properties like styleClass, disabled state, etc.</li>
 *   <li>{@link de.cuioss.jsf.api.components.util.ComponentWrapper} - Wrapper implementation for
 *       UIComponents that simplifies access to common component properties</li>
 *   <li>{@link de.cuioss.jsf.api.components.util.DisableUIComponentStrategy} - Strategy interface
 *       for components that can be disabled</li>
 * </ul>
 * 
 * <h3>Component Tree Traversal</h3>
 * <ul>
 *   <li>{@link de.cuioss.jsf.api.components.util.EditableValueHoldersVisitCallback} - VisitCallback 
 *       implementation that collects all EditableValueHolder components during tree traversal</li>
 *   <li>{@link de.cuioss.jsf.api.components.util.MethodRule} - Helper for JSF method expressions</li>
 * </ul>
 * 
 * <h3>Sub-packages</h3>
 * <ul>
 *   <li>{@link de.cuioss.jsf.api.components.util.modifier} - Component modifier implementations
 *       for different types of components</li>
 *   <li>{@link de.cuioss.jsf.api.components.util.styleclass} - Utilities for CSS style class
 *       manipulation and management</li>
 * </ul>
 * 
 * <h3>Usage Examples</h3>
 * <p>Finding the parent form of a component:</p>
 * <pre>
 * UIForm form = ComponentUtility.findCorrespondingForm(component);
 * </pre>
 * 
 * <p>Resetting all input fields in a form:</p>
 * <pre>
 * ComponentUtility.resetEditableValueHolder(form, facesContext);
 * </pre>
 * 
 * <p>Managing component state:</p>
 * <pre>
 * CuiState state = new CuiState(getStateHelper());
 * state.put("disabled", true);
 * boolean isDisabled = state.getBoolean("disabled");
 * </pre>
 *
 * @author Oliver Wolff
 */
package de.cuioss.jsf.api.components.util;
