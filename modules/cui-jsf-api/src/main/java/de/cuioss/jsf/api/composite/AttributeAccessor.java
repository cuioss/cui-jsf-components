package de.cuioss.jsf.api.composite;

import java.io.Serializable;
import java.util.Map;

/**
 * Small helper class that unifies the access on component attributes. The name
 * of the attribute is enclosed in the implementation In case of a different
 * type for the attribute and type-parameter the methods will throw an
 * {@link IllegalStateException}
 *
 * @author Oliver Wolff
 * @param <T> defining the return type
 */
public interface AttributeAccessor<T> extends Serializable {

    /**
     * Typesafe access on a certain attribute. The concrete attribute name is
     * encapsulated in the implementation.
     *
     * @param attributeMap must not be null
     * @return the type attribute or null if the attribute is not available
     */
    T value(Map<String, Object> attributeMap);

    /**
     * @param attributeMap must not be null
     * @return boolean indicating whether the attribute is available (not null) at
     *         all
     */
    boolean available(Map<String, Object> attributeMap);
}
