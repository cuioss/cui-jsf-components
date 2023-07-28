/**
 * <p>
 * Cui provides a full featured Server side Url History. In essence you use on any page the action
 * or outcome attribute "back" in order to navigate back from the previous page with a redirect.
 * </p>
 * <h2>Configuration: faces-config.xml</h2>
 * <h3>NavigationHandler</h3>
 *
 * <pre>
 * {@code <navigation-handler>de.cuioss.jsf.api.application.history.HistoryNavigationHandler</navigation-handler>}
 * </pre>
 *
 * <h3>Configuration of history manager</h3>
 *
 * <pre>
 * {@code   <managed-bean>
        <managed-bean-name>historyConfiguration</managed-bean-name>
        <managed-bean-class>de.cuioss.jsf.api.application.history.impl.HistoryConfigurationImpl</managed-bean-class>
        <managed-bean-scope>application</managed-bean-scope>
        <managed-property>
            <description>The fallback navigation case is utilized if no history
                is present, e.g. because of deep-linking</description>
            <property-name>fallbackOutcome</property-name>
            <value>home</value>
        </managed-property>
        <managed-property>
            <property-name>excludeParameter</property-name>
            <property-class>java.util.List</property-class>
            <list-entries>
                <value-class>java.lang.String</value-class>
                <value>samlresponse</value>
            </list-entries>
        </managed-property>
  </managed-bean>}
 * </pre>
 *
 * <h3>Actual History Manager Bean</h3>
 *
 * <pre>
 * {@code   <managed-bean>
        <managed-bean-name>historyManagerBean</managed-bean-name>
        <managed-bean-class>de.cuioss.jsf.api.application.history.impl.HistoryManagerBean</managed-bean-class>
        <managed-bean-scope>session</managed-bean-scope>
 </managed-bean>}
 * </pre>
 *
 * <h2>Usage</h2>
 * Each page that should be historized should use
 *
 * <pre>
 * {@code <f:event listener="#&#123;historyManagerBean.addCurrentUriToHistory&#125;" type="postAddToView" />}
 * </pre>
 *
 * This is usually done in a high level template for all pages. In case you want a certain page opt
 * out
 * the handling you can use:
 *
 * <pre>
 * {@code <f:event listener="#&#123;historyManagerBean.vetoCurrentUriToHistory&#125;" type="postAddToView" />}
 * </pre>
 *
 * Now you can utilize the back functionality quite easily:
 *
 * <pre>
 * {@code <boot:button icon="ui-icon-cancel" id="cancel" titleKey="common.button.cancel" labelKey="common.button.cancel" outcome="back" />}
 * </pre>
 *
 * or
 *
 * <pre>
 * {@code <boot:commandButton icon="ui-icon-disk" id="saveSubscriptions"
 *     titleKey="common.button.save"
 *     labelKey="common.button.save" action="back"
 *     actionListener=#&#123;subscriptionsDialogPageBean.saveSubscriptions&#125;" />}
 * </pre>
 *
 * @author Oliver Wolff
 */
package de.cuioss.jsf.api.application.history;
