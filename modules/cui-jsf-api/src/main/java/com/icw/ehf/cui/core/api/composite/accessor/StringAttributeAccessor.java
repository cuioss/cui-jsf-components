package com.icw.ehf.cui.core.api.composite.accessor;

import java.util.Map;

import com.icw.ehf.cui.core.api.composite.AttributeAccessorImpl;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Implementation for String based attributes. Depending on
 * considerEmptyStringAsNull it interprets empty String as null.
 *
 * @author Oliver Wolff
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class StringAttributeAccessor extends AttributeAccessorImpl<String> {

    private static final long serialVersionUID = 490377061753452475L;

    private final boolean considerEmptyStringAsNull;

    /**
     * Constructor.
     *
     * @param name
     *            of the attribute to accessed. Must not be null or empty
     * @param alwaysResolve
     *            Flag defining whether to store locally or always resolve from
     *            the attribute map
     * @param considerEmptyStringAsNull
     *            indicates how to treat empty Strings
     */
    public StringAttributeAccessor(final String name, final boolean alwaysResolve,
            final boolean considerEmptyStringAsNull) {
        super(name, String.class, alwaysResolve);
        this.considerEmptyStringAsNull = considerEmptyStringAsNull;
    }

    @Override
    public String value(final Map<String, Object> attributeMap) {
        var found = super.value(attributeMap);
        if (considerEmptyStringAsNull && null != found && found.isEmpty()) {
            found = null;
        }
        return found;
    }

    @Override
    public boolean available(final Map<String, Object> attributeMap) {
        return null != this.value(attributeMap);
    }
}
