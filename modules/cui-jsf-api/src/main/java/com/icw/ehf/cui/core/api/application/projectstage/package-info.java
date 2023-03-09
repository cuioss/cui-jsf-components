/**
 * <h2>Summary</h2>
 * <p>
 * Simplifies accessing Project Stage information.
 * </p>
 * <h2>Configuration</h2>
 * <p>
 * In order to work in a non CDI-/portal- context you need to declare
 * {@link com.icw.ehf.cui.core.api.application.projectstage.CuiProjectStageImpl} as an
 * {@link javax.faces.bean.ApplicationScoped} bean with the name
 * {@link com.icw.ehf.cui.core.api.application.projectstage.CuiProjectStage#BEAN_NAME} :
 *
 * <pre>
{@code
<managed-bean>
	<managed-bean-name>cuiProjectStage</managed-bean-name>
	<managed-bean-class>com.icw.ehf.cui.core.api.application.projectstage.CuiProjectStageImpl</managed-bean-class>
	<managed-bean-scope>application</managed-bean-scope>
</managed-bean>
}
 * </pre>
 * </p>
 * <h2>Usage with components and beans</h2>
 * <p>
 * It can be easily be accessed with
 * {@link com.icw.ehf.cui.core.api.application.projectstage.CuiProjectStageAccessor#getValue()}
 * </p>
 * <h2>Usage within xhtml</h2>
 * <p>
 * Access it like a usual {@link javax.faces.bean.ManagedBean}:
 * <code>rendered="#{cuiProjectStage.development}"</code>
 * </p>
 *
 * @author Oliver Wolff
 */
package com.icw.ehf.cui.core.api.application.projectstage;
