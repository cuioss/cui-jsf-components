/**
 * <h2>Summary</h2>
 * <p>
 * The {@link com.icw.ehf.cui.core.api.application.bundle.CuiResourceBundle} provides the composite
 * handling of multiple {@link java.util.ResourceBundle}s in a uniform way. The concept name is
 * Unified-Resource-Bundle.
 * </p>
 * <h2>Introduction</h2>
 * <p>
 * Every ICW JSF application uses many resource bundles inside. <br />
 * This results in the situation when one page uses different EL-expressions to refer to bundles in
 * order to resolve labels, i.e. <code>#{cui_msg['key']}</code> or <code>#{msg['key']}</code> or
 * <code>#{vendor['key']}</code> and so on. It is not that easy to handle i18nized strings in xhtml
 * with this approach.
 * </p>
 * <p>
 * The concept of the Unified-Resource-Bundle allows to use one EL-expression to refer to all
 * bundles within the application in a unified way / notation.
 * </p>
 * <h2>Unified access to resource bundles</h2>
 * <p>
 * CUI applications should initialize
 * {@link com.icw.ehf.cui.core.api.application.bundle.CuiResourceBundle} in faces-context as request
 * scoped bean with the name
 * {@link com.icw.ehf.cui.core.api.application.bundle.CuiResourceBundle#BEAN_NAME} . This bean
 * extends {@link java.util.ResourceBundle} class which allows to use it as common resourceBundle.
 * </p>
 *
 * <pre>
 * {@code
<managed-bean>
    <managed-bean-name>msgs</managed-bean-name>
         <managed-bean-class>com.icw.ehf.cui.core.api.application.bundle.CuiResourceBundle</managed-bean-class>
         <managed-bean-scope>request</managed-bean-scope>
         <managed-property>
             <property-name>resourceBundleWrapper</property-name>
             <value>#(resourceBundleWrapper)</value>
         </managed-property>
 </managed-bean>
}
 * </pre>
 * <p>
 * This bean allows us to refer to the {@link java.util.ResourceBundle} as usual EL-expression:
 * <code>#{msgs['key']}</code>
 * </p>
 * <p>
 * CuiResourceBundle requires the implementation of
 * {@link com.icw.ehf.cui.core.api.application.bundle.ResourceBundleWrapper} as parameter. This
 * interface has an own method to resolve messages:
 * {@link com.icw.ehf.cui.core.api.application.bundle.ResourceBundleWrapper#getMessage(String)} It
 * can be implemented within a portal application or the basic CUI implementation can be used.
 * </p>
 * <p>
 * The basic implementation
 * {@link com.icw.ehf.cui.core.api.application.bundle.ResourceBundleWrapperImpl} requires a
 * {@link java.util.List} of {@link java.util.ResourceBundle}s that are configured as
 * <code>resourceBundles</code> and a {@link java.util.List} of {@link java.lang.String} that are
 * configured as <code>resourceBundleNames</code> variables in faces-context. The second
 * configuration is necessary in order to enable deserialization of the
 * {@link com.icw.ehf.cui.core.api.application.bundle.ResourceBundleWrapperImpl}
 * </p>
 *
 * <pre>
{@code
<managed-bean>
    <managed-bean-name>resourceBundleWrapper</managed-bean-name>
    <managed-bean-class>com.icw.ehf.cui.core.api.application.bundle.ResourceBundleWrapperImpl</managed-bean-class>
    <managed-bean-scope>request</managed-bean-scope>
    <managed-property>
        <property-name>resourceBundles</property-name>
           <list-entries>
               <value>#(cui_msg)</value>
               <value>#(msg)</value>
               <value>#(vendor)</value>
             </list-entries>
     </managed-property>
     <managed-property>
        <property-name>resourceBundleNames</property-name>
           <list-entries>
               <value>cui_msg</value>
               <value>msg</value>
               <value>vendor</value>
             </list-entries>
     </managed-property>
</managed-bean>
}
 * </pre>
 * <p>
 * The implementation of
 * {@link com.icw.ehf.cui.core.api.application.bundle.ResourceBundleWrapper#getMessage(String)}
 * method iterates through the bundles variables and tries to resolve label in every of them. Once a
 * message is resolved it will be returned.
 * </p>
 * <h2>Usage in components and beans</h2>
 * <p>
 * Because the {@link com.icw.ehf.cui.core.api.application.bundle.CuiResourceBundle} is exposed as a
 * managed bean you can access it with
 * {@link com.icw.ehf.cui.core.api.application.bundle.CuiResourceBundleAccessor}
 * </p>
 * <h2>Profit</h2>
 * <p>
 * The developer has still access to all resource bundles in the application, all "own" pages from
 * any portal applications will still work.<br />
 * The unified way to access label is the same like the access to usual resource bundle.<br />
 * It is possible to override keys in different bundles, because first bundle in the list will win.
 * <br />
 * No need to remember all bundle names since it is configured once. Easy re-factoring: keys can be
 * moved in other bundles without changes in the xhtml.<br />
 * Since the beans are request scoped there is no need to care about locale-changes.
 * </p>
 *
 * @author Oliver Wolff
 */
package com.icw.ehf.cui.core.api.application.bundle;
