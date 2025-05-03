/**
 * <h2>Summary</h2>
 * <p>
 * Provides classes and utilities, simplifying the implementation of Composite
 * Components.
 * </p>
 *
 * <h2>Attribute Accessor</h2>
 * <p>
 * The {@link de.cuioss.jsf.api.composite.AttributeAccessor} streamlines the
 * access on Attributes within component classes. <br>
 * Say you want to access an attribute like styleClass of type String. You need
 * to lookup within the attribute map the corresponding key: <br>
 * <code>if(getAttributes().containsKey("styleClass")) {<br>
 *            String styleClass = (String) getAttributes().get("styleClass");<br>
 *       };<br></code> Using the attribute accessor you can use this: <br>
 * <code>
 *   private final StringAttributeAccessor styleAccessor =
 *        new StringAttributeAccessor("styleClass", true, true);<br>
 *       styleClass = styleAccessor.value(getAttributes());<br>
 *      with styleAccessor.available(getAttributes()) as guard.
 *       </code>
 * </p>
 *
 *
 * @author Oliver Wolff
 *
 */
package de.cuioss.jsf.api.composite;
