package de.cuioss.jsf.jqplot.js.types;

import static java.util.Objects.requireNonNull;

import de.cuioss.jsf.jqplot.js.support.JsValue;

/**
 * JsNumber factory is able to create type safe JsValue with number content.
 * Currently supported {@link Double} and {@link Integer}
 *
 * @author Eugen Fischer
 */
public enum JsNumberFactory {

    /** */
    JS_DOUBLE(Double.class) {

        @Override
        protected JsValue createInstance(Number value) {
            return new JsDouble((Double) value);
        }
    },

    /** */
    JS_INTEGER(Integer.class) {

        @Override
        protected JsValue createInstance(Number value) {
            return new JsInteger((Integer) value);
        }
    };

    private final Class<?> clazz;

    /**
     * Private Constructor
     *
     * @param klass {@link Class} must not be null
     */
    JsNumberFactory(final Class<?> klass) {
        clazz = requireNonNull(klass, "Type must be defined");
    }

    /**
     * Create JsValue instance
     *
     * @param value Number value
     * @return {@link JsValue}
     */
    protected abstract JsValue createInstance(final Number value);

    private boolean match(final Class<?> klass) {
        return clazz.equals(klass);
    }

    /**
     * Factory method
     *
     * @param value Number type
     * @return typed JsNumber if type is supported, empty JsNumber if value is null
     * @throws UnsupportedOperationException if type is not supported
     */
    public static <T extends Number> JsNumber<T> create(T value) {
        if (null == value) {
            return new JsNumber<>(null);
        }

        final Class<? extends Number> klass = value.getClass();

        for (JsNumberFactory item : JsNumberFactory.values()) {
            if (item.match(klass)) {
                return new JsNumber<>(item.createInstance(value));
            }
        }

        throw new UnsupportedOperationException(String.format("Type [%s] is not supported", klass.getSimpleName()));

    }

}
