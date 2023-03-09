/**
 * <h2>Summary</h2>
 * <p>
 * Provides classes for dealing with
 * {@link javax.faces.application.FacesMessage}s
 * </p>
 * <h2>MessageProducer</h2>
 * <p>
 * Provides methods for simplified,
 * {@link java.util.ResourceBundle}-based creating / setting of
 * {@link javax.faces.application.FacesMessage}s. Provided classes are:
 * </p>
 * <ul>
 * <li>{@link com.icw.ehf.cui.core.api.application.message.MessageProducer}: Defines
 * methods for creating / setting of Jsf-messages</li>
 * <li>{@link com.icw.ehf.cui.core.api.application.message.MessageProducerImpl}:
 * Default implementation, needs to be configured within faces-config</li>
 * <li>
 * {@link com.icw.ehf.cui.core.api.application.message.MessageProducerAccessor}:
 * Accessor for concrete instances of
 * {@link com.icw.ehf.cui.core.api.application.message.MessageProducer}. Assumes a
 * configured bean under the name of
 * {@link com.icw.ehf.cui.core.api.application.message.MessageProducerImpl#BEAN_NAME}</li>
 * </ul>
 * <h3>Configuration faces-config</h3>
 *
 * <pre>
 * {@code   <managed-bean>
 *         <managed-bean-name>messageProducer</managed-bean-name>
 *         <managed-bean-class>com.icw.ehf.cui.core.api.application.message.MessageProducerImpl</managed-bean-class>
 *         <managed-bean-scope>request</managed-bean-scope>
 *         <managed-property>
 *             <property-name>resourceBundle</property-name>
 *             <value>#(msgs)</value>
 *         </managed-property>
 * </managed-bean>}
 * </pre>
 *
 *
 * @author Oliver Wolff
 */
package com.icw.ehf.cui.core.api.application.message;
