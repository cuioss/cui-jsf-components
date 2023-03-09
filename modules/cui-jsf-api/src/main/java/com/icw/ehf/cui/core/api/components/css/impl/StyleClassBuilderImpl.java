package com.icw.ehf.cui.core.api.components.css.impl;

import static de.cuioss.tools.string.MoreStrings.nullToEmpty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.icw.ehf.cui.core.api.components.css.StyleClassBuilder;
import com.icw.ehf.cui.core.api.components.css.StyleClassProvider;

import de.cuioss.tools.string.Joiner;
import de.cuioss.tools.string.Splitter;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Basic implementation of {@link StyleClassBuilder}.
 *
 * @author Oliver Wolff
 */
@EqualsAndHashCode
@ToString
public class StyleClassBuilderImpl implements StyleClassBuilder, Serializable {

    private static final char SEPARATOR = ' ';
    private static final long serialVersionUID = 8303808310618200785L;
    private final List<String> classes;

    /**
     * Default Constructor
     */
    public StyleClassBuilderImpl() {
        classes = new ArrayList<>();
    }

    /**
     * @param initialClass
     *            to be added to the builder. It can be a single class, or a
     *            number of space separated classes.
     */
    public StyleClassBuilderImpl(final String initialClass) {
        this();
        safelyAddStyleClass(initialClass);
    }

    /**
     * @param styleClass
     */
    private void safelyAddStyleClass(final String styleClass) {
        if (!isTrimmedNullOrEmpty(styleClass)) {
            for (String splittedClass : Splitter.on(SEPARATOR).splitToList(styleClass.trim())) {
                var trimmedClass = splittedClass.trim();
                if (!classes.contains(trimmedClass)) {
                    classes.add(trimmedClass);
                }
            }
        }
    }

    /**
     * @param styleClass
     */
    private void safelyRemoveStyleClass(final String styleClass) {
        if (!isTrimmedNullOrEmpty(styleClass)) {
            for (String splittedClass : Splitter.on(SEPARATOR).splitToList(styleClass.trim())) {
                var trimmedClass = splittedClass.trim();
                classes.remove(trimmedClass);
            }
        }
    }

    /**
     * @param styleClass
     */
    private void safelyToggleStyleClass(final String styleClass) {
        if (!isTrimmedNullOrEmpty(styleClass)) {
            for (String splittedClass : Splitter.on(SEPARATOR).splitToList(styleClass.trim())) {
                var trimmedClass = splittedClass.trim();
                if (classes.contains(trimmedClass)) {
                    classes.remove(trimmedClass);
                } else {
                    classes.add(trimmedClass);
                }
            }
        }
    }

    @Override
    public String getStyleClass() {
        if (classes.isEmpty()) {
            return "";
        }
        return Joiner.on(SEPARATOR).skipNulls().join(classes);
    }

    @Override
    public StyleClassBuilder append(final String styleClass) {
        safelyAddStyleClass(styleClass);
        return this;
    }

    @Override
    public StyleClassBuilder append(final StyleClassBuilder styleClassBuilder) {
        if (null != styleClassBuilder) {
            safelyAddStyleClass(styleClassBuilder.getStyleClass());
        }
        return this;
    }

    @Override
    public StyleClassBuilder append(final StyleClassProvider styleClassProvider) {
        if (null != styleClassProvider) {
            safelyAddStyleClass(styleClassProvider.getStyleClass());
        }
        return this;
    }

    @Override
    public StyleClassBuilder appendIfTrue(final StyleClassProvider styleClassProvider, final boolean condition) {
        if (condition) {
            append(styleClassProvider);
        }
        return this;
    }

    @Override
    public boolean isAvailable() {
        return !classes.isEmpty();
    }

    @Override
    public StyleClassBuilder remove(final String styleClass) {
        safelyRemoveStyleClass(styleClass);
        return this;

    }

    @Override
    public StyleClassBuilder remove(final StyleClassBuilder styleClassBuilder) {
        if (null != styleClassBuilder) {
            safelyRemoveStyleClass(styleClassBuilder.getStyleClass());
        }
        return this;
    }

    @Override
    public StyleClassBuilder remove(final StyleClassProvider styleClassProvider) {
        if (null != styleClassProvider) {
            safelyRemoveStyleClass(styleClassProvider.getStyleClass());
        }
        return this;
    }

    @Override
    public StyleClassBuilder toggle(final String styleClass) {
        safelyToggleStyleClass(styleClass);
        return this;
    }

    @Override
    public StyleClassBuilder toggle(final StyleClassBuilder styleClassBuilder) {
        if (null != styleClassBuilder) {
            safelyToggleStyleClass(styleClassBuilder.getStyleClass());
        }
        return this;
    }

    @Override
    public StyleClassBuilder toggle(final StyleClassProvider styleClassProvider) {
        if (null != styleClassProvider) {
            safelyToggleStyleClass(styleClassProvider.getStyleClass());
        }
        return this;
    }

    private static boolean isTrimmedNullOrEmpty(final String toBeCheckd) {
        return nullToEmpty(toBeCheckd).trim().isEmpty();
    }
}
