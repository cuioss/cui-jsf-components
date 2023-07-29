package de.cuioss.jsf.api.composite.accessor;

/**
 * Shorthand for creating a {@link StringAttributeAccessor} for the attribute
 * 'styleClass'
 *
 * @author Oliver Wolff
 */
public class StyleClassAttributeAccessor extends StringAttributeAccessor {

    private static final long serialVersionUID = 4957486497287214005L;

    /** "styleClass". */
    public static final String NAME = "styleClass";

    /**
     *
     */
    public StyleClassAttributeAccessor() {
        super(NAME, true, true);
    }

}
