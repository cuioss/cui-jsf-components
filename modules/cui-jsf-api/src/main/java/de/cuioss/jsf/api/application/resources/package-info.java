/**
 * <h3>Provides classes dealing with resources</h3>
 * <p>
 * The corresponding modifications will only take place in production
 * environments. This will contain choosing the min version of resource and
 * adding a cache-buster to the resource request.
 * </p>
 * <p>
 * In order to work it needs the corresponding beans available:
 * {@link de.cuioss.jsf.api.application.resources.CuiResourceManager} with the
 * name
 * {@link de.cuioss.jsf.api.application.resources.CuiResourceManager#BEAN_NAME}
 * and an implementation of
 * {@link de.cuioss.jsf.api.application.resources.CuiResourceConfiguration} with
 * the name
 * {@link de.cuioss.jsf.api.application.resources.impl.CuiResourceConfigurationImpl#BEAN_NAME}
 * </p>
 * The ResourceHandler itself must be registered within faces-config: <code>
 * &lt;resource-handler&gt;de.cuioss.jsf.api.application.resources.CuiResourceHandler&lt;/resource-handler&gt;
 * </code> The configuration should look something like: <code>
 *     &lt;managed-bean&gt;<br>
 *         &lt;managed-bean-name&gt;cuiResourceConfiguration&lt;/managed-bean-name&gt;<br>
 *         &lt;managed-bean-class&gt;de.cuioss.jsf.api.application.resources.impl.CuiResourceConfigurationImpl&lt;/managed-bean-class&gt;<br>
 *         &lt;managed-bean-scope&gt;application&lt;/managed-bean-scope&gt;<br>
 *        &lt;managed-property&gt;<br>
 *             &lt;property-name&gt;handledSuffixes&lt;/property-name&gt;<br>
 *             &lt;property-class&gt;java.util.List&lt;/property-class&gt;<br>
 *           &lt;list-entries&gt;<br>
 *               &lt;value-class&gt;java.lang.String&lt;/value-class&gt;<br>
 *               &lt;value&gt;css&lt;/value&gt;<br>
 *              &lt;value&gt;js&lt;/value&gt;<br>
 *              &lt;value&gt;eot&lt;/value&gt;<br>
 *              &lt;value&gt;svg&lt;/value&gt;<br>
 *              &lt;value&gt;ttf&lt;/value&gt;<br>
 *              &lt;value&gt;woff&lt;/value&gt;<br>
 *          &lt;/list-entries&gt;<br>
 *      &lt;/managed-property&gt;<br>
 *      &lt;managed-property&gt;<br>
 *          &lt;property-name&gt;handledLibraries&lt;/property-name&gt;<br>
 *          &lt;property-class&gt;java.util.List&lt;/property-class&gt;<br>
 *          &lt;list-entries&gt;<br>
 *              &lt;value-class&gt;java.lang.String&lt;/value-class&gt;<br>
 *              &lt;value&gt;de.cuioss.portal.css&lt;/value&gt;<br>
 *              &lt;value&gt;de.cuioss.cui.fonts&lt;/value&gt;<br>
 *              &lt;value&gt;javascript&lt;/value&gt;<br>
 *              &lt;value&gt;com.bootstrap.js&lt;/value&gt;<br>
 *              &lt;value&gt;thirdparty.legacy.js&lt;/value&gt;<br>
 *          &lt;/list-entries&gt;<br>
 *      &lt;/managed-property&gt;<br>
 *      &lt;managed-property&gt;<br>
 *          &lt;property-name&gt;version&lt;/property-name&gt;<br>
 *          &lt;property-class&gt;java.lang.String&lt;/property-class&gt;<br>
 *          &lt;value&gt;1.0&lt;/value&gt;<br>
 *      &lt;/managed-property&gt;<br>
 *  &lt;/managed-bean&gt;<br>
 *  </code>
 * <p>
 * The {@link de.cuioss.jsf.api.application.resources.CuiResourceManager}
 * registers itself as an {@link javax.faces.bean.ApplicationScoped} bean
 *
 * @author Oliver Wolff
 *
 */
package de.cuioss.jsf.api.application.resources;
