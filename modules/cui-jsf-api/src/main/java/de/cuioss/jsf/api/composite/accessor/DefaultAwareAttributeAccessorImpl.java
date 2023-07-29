package de.cuioss.jsf.api.composite.accessor;

import java.io.Serializable;
import java.util.Map;

import de.cuioss.jsf.api.composite.AttributeAccessorImpl;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Extends {@link AttributeAccessorImpl} with the handling of defaults for the
 * corresponding attribute.
 *
 * @author Oliver Wolff
 * @param <T> identifying the concrete type of the attribute to be accessed,
 *            needs to at least {@link Serializable}
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DefaultAwareAttributeAccessorImpl<T extends Serializable> extends AttributeAccessorImpl<T> {

    private static final long serialVersionUID = -3779458673451938363L;

    @Getter(value = AccessLevel.PROTECTED)
    private final T defaultValue;

    /**
     * Constructor.
     *
     * @param name          of the attribute, must not be null or empty
     * @param valueType     The concrete type to be looked up.
     * @param alwaysResolve indicates whether to resolve once or on every access
     * @param defaultValue  may be null
     */
    public DefaultAwareAttributeAccessorImpl(final String name, final Class<T> valueType, final boolean alwaysResolve,
            final T defaultValue) {
        super(name, valueType, alwaysResolve);
        this.defaultValue = defaultValue;
    }

    @Override
    public T value(final Map<String, Object> attributeMap) {
        if (super.available(attributeMap)) {
            return super.value(attributeMap);
        }
        return defaultValue;
    }

    @Override
    public boolean available(final Map<String, Object> attributeMap) {
        return defaultValue != null || super.available(attributeMap);
    }

}
