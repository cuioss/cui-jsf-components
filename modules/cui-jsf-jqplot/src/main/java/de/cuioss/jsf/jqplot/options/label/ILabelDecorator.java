package de.cuioss.jsf.jqplot.options.label;

import java.io.Serializable;

/**
 * Interface provide common label / text relevant settings
 *
 * @author Eugen Fischer
 * @param <F> fluent api bounded type
 */
public interface ILabelDecorator<F extends Serializable> {

    /**
     * wether or not to show the label
     *
     * @param value {@link Boolean}
     * @return fluent api sytle
     */
    F setShowLabel(final Boolean value);

    /**
     * string passed to the formatter
     *
     * @param value {@link String}
     * @return fluent api sytle
     */
    F setFormatString(final String value);

    /**
     * css spec for the font-family css attribute
     *
     * @param value {@link String}
     * @return fluent api sytle
     */
    F setFontFamily(final String value);

    /**
     * css spec for the font-size css attribute.
     *
     * @param value {@link String}
     * @return fluent api sytle
     */
    F setFontSize(final String value);

    /**
     * css spec for the font-size css attribute.
     *
     * @param value {@link String}
     * @return fluent api sytle
     */
    F setTextColor(final String value);

    /**
     * angle of text
     *
     * @param value Integer degrees
     * @return fluent api style
     */
    F setAngle(final Integer value);

    /**
     * true to escape HTML entities in the label
     *
     * @param value {@link Boolean}
     * @return fluent api sytle
     */
    F setEscapeHTML(final Boolean value);
}
