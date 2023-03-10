package de.cuioss.jsf.api.composite.accessor;

import java.util.Map;

import de.cuioss.jsf.api.composite.AttributeAccessor;
import de.cuioss.jsf.api.composite.AttributeAccessorImpl;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Shorthand for creating an {@link AttributeAccessor} for Boolean-types. In
 * addition it can be easily configured to inverse the boolean logic.
 *
 * @author Oliver Wolff
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BooleanAttributeAccessor extends AttributeAccessorImpl<Boolean> {

    private static final long serialVersionUID = 5261144187854006347L;

    private final boolean invert;

    /**
     * @param name
     *            of the boolean
     * @param alwaysResolve false: cache the value once it is resolved
     * @param invertBoolean
     *            flag indicating whether to invert the boolean logic.
     */
    public BooleanAttributeAccessor(final String name, final boolean alwaysResolve,
            final boolean invertBoolean) {
        super(name, Boolean.class, alwaysResolve);
        this.invert = invertBoolean;
    }

    @Override
    public Boolean value(final Map<String, Object> attributeMap) {
        var actual = super.value(attributeMap);
        if (actual != null && this.invert) {
            actual = !actual.booleanValue();
        }
        return actual;
    }

}
