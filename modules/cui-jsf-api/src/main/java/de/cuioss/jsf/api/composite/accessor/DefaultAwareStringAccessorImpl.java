package de.cuioss.jsf.api.composite.accessor;

import java.util.Map;

import de.cuioss.tools.string.MoreStrings;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Extends {@link StringAttributeAccessor} with the handling of defaults for the
 * corresponding attribute. In order to simplify usage empty String are
 * considered null.
 *
 * @author Oliver Wolff
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DefaultAwareStringAccessorImpl extends StringAttributeAccessor {

    private static final long serialVersionUID = -3779458673451938363L;

    @Getter(value = AccessLevel.PROTECTED)
    private final String defaultValue;

    /**
     * Constructor.
     *
     * @param name          of the attribute, must not be null or empty
     * @param alwaysResolve indicates whether to resolve once or on every access
     * @param defaultValue  may be null
     */
    public DefaultAwareStringAccessorImpl(final String name, final boolean alwaysResolve, final String defaultValue) {
        super(name, alwaysResolve, true);
        this.defaultValue = MoreStrings.emptyToNull(defaultValue);
    }

    @Override
    public String value(final Map<String, Object> attributeMap) {
        if (null != super.value(attributeMap)) {
            return super.value(attributeMap);
        }
        return defaultValue;
    }

    @Override
    public boolean available(final Map<String, Object> attributeMap) {
        return defaultValue != null || super.available(attributeMap);
    }

}
