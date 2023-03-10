/**
 * <h2>Summary</h2>
 * <p>
 * Provides helper / convenience classes for dealing with certain aspects
 * regarding components like handling of labels and so on.
 * </p>
 * <h2>LabelResolver</h2>
 * <p>
 * The handling of labelKeys is one of the central aspects of all cui-based
 * components It eases the usage: labelKey="some.label.key" is much simpler than
 * using <code>labelValue="#{msgs['key']}"</code> In order to work the
 * {@link de.cuioss.jsf.api.components.support.LabelResolver} uses the
 * {@link de.cuioss.jsf.api.application.bundle.CuiResourceBundle}
 * approach.
 * </p>
 * <h3>Algorithm</h3>
 * <ul>
 * <li>If a value for the attribute "labelValue" exists: Return it. It checks
 * whether either a converter is available or the corresponding converterId
 * Attribute</li>
 * <li>If a value for the attribute "labelKey" exists: look it up in the
 * {@link de.cuioss.jsf.api.application.bundle.CuiResourceBundle}</li>
 * <li>If none of the above takes place, throw
 * {@link java.lang.IllegalStateException} depending whether strictMode is set
 * to <code>true</code>. strictMode is set to <code>false</code> it will return
 * null on that case.</li>
 * </ul>
 * <h3>Usage</h3>
 * <p>
 * Fluent-style-api:
 * {@code LabelResolver.builder().withLabelKey(labelKey).withStrictMode(false).withLabelValue(labelValue)
					.build().resolve(getFacesContext());}
 * </p>
 *
 * @author Oliver Wolff
 */
package de.cuioss.jsf.api.components.support;
