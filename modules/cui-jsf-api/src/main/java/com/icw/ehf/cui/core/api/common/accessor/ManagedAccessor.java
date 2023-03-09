package com.icw.ehf.cui.core.api.common.accessor;

import java.io.Serializable;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

/**
 * @deprecated use CuiBeanManager instead
 *
 *             Utility used for accessing transient values in a non transient way. The
 *             {@link ManagedAccessor} itself implements {@link Serializable} but the wrapped value
 *             is to be
 *             kept in a transient field. In order to work properly the concrete accessor must take
 *             care on
 *             this.
 *             <p>
 *             To sum it up, the purpose of accesors are
 *             <ul>
 *             <li>Failsafe implementation of {@link Serializable} / transient contract</li>
 *             <li>Implicit lazy loading of the Attributes / beans / whatever: <em>Caution: </em>
 *             Some Accessors
 *             , like {@link ManagedBeanAccessor} do not always store / cache internally (in a
 *             transient field),
 *             therefore there is no lazy loading.</li>
 *             <li>Hiding of the loading mechanics of the object to be loaded.</li>
 *             <li>Unifying usage of common objects like {@link ResourceBundle}, etc.</li>
 *             </ul>
 *
 * @author Oliver Wolff
 * @param <T> identifying the concrete type
 */
@Deprecated // use CuiBeanManager instead
public interface ManagedAccessor<T> extends Serializable {

    /**
     * Accesses the managed value.
     *
     * @return the managed value.
     * @throws IllegalArgumentException when the supplied key does not resolve to any managed bean
     *             or when a managed bean is found but the object is not of the expected type
     * @throws IllegalStateException may occur in corner cases, where {@link FacesContext} is not
     *             initialized properly
     */
    T getValue();
}
