/**
 * <h2>Summary</h2>
 * <p>
 * Provides the cui specific locale management. The
 * {@link de.cuioss.jsf.api.application.locale.LocaleProducer} accesses the
 * {@link java.util.Locale} currently active for the user. The default implementation
 * {@link de.cuioss.jsf.api.application.locale.LocaleProducerImpl} resolves the
 * {@link java.util.Locale} from
 * {@link javax.faces.application.ViewHandler#calculateLocale(javax.faces.context.FacesContext)}
 * </p>
 * <h2>Configuration</h2>
 * In order to work in a non CDI-/portal- context you need to declare
 * {@link de.cuioss.jsf.api.application.locale.LocaleProducerImpl} as an
 * {@link javax.faces.bean.ApplicationScoped} bean with the name
 * {@link de.cuioss.jsf.api.application.locale.LocaleProducerImpl#BEAN_NAME}:
 *
 * <pre>
{@code
<managed-bean>
	<managed-bean-name>localeProducer</managed-bean-name>
	<managed-bean-class>de.cuioss.jsf.api.application.locale.LocaleProducerImpl</managed-bean-class>
	<managed-bean-scope>session</managed-bean-scope>
</managed-bean>
}
 * </pre>
 *
 * <h2>Usage with components and beans</h2>
 * <p>
 * It can be easily be accessed with
 * {@link de.cuioss.jsf.api.application.locale.LocaleProducerAccessor#getValue()}
 * </p>
 * <h2>Usage within xhtml</h2>
 * <p>
 * Access it like a usual {@link javax.faces.bean.ManagedBean}:
 * <code>locale="#{localeProducer.locale}</code>
 * </p>
 *
 * @author Oliver Wolff
 */
package de.cuioss.jsf.api.application.locale;
