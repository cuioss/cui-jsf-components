package com.icw.ehf.cui.core.api.components.partial;

import java.io.Serializable;

/**
 * <h2>Summary</h2>
 * <p>
 * Implementors of this class manage the state and resolving of the title attribute. The
 * implementation relies on the correct user of the attribute names, saying they must exactly match
 * the accessor methods.
 * </p>
 * <h2>titleKey</h2>
 * <p>
 * The key for looking up the text for the title-attribute. Although this attribute is not required
 * you must provide either this or #titleValue if you want a title to be displayed.
 * </p>
 * <h2>titleValue</h2>
 * <p>
 * The Object displayed for the title-attribute. This is a replacement for #titleKey. If both are
 * present titleValue takes precedence. This object is usually a String. If not, the developer
 * must ensure that a corresponding converter is either registered for the type or must provide a
 * converter using #titleConverter.
 * </p>
 * <h2>titleConverter</h2>
 * <p>
 * The optional converterId to be used in case of titleValue is set and needs conversion.
 * </p>
 *
 * @author Oliver Wolff
 */
public interface TitleProvider {

    /**
     * @param titleKey to be set.
     */
    void setTitleKey(String titleKey);

    /**
     * @return the resolved titleKey.
     */
    String getTitleKey();

    /**
     * @param titelValue to be set.
     */
    void setTitleValue(Serializable titelValue);

    /**
     * @return the titelValue.
     */
    Serializable getTitleValue();

    /**
     * @return the titelConverter
     */
    Object getTitleConverter();

    /**
     * @param titelConverter the titelConverter to set
     */
    void setTitleConverter(Object titelConverter);

    /**
     * @return the resolved title is available, otherwise it will return null.
     */
    String resolveTitle();

    /**
     * @return boolean indicating whether a title is there (set)
     */
    boolean isTitleSet();

    /**
     * @param title as defined within components but this one will always throw an
     *            {@link UnsupportedOperationException} indicating that the developer should always
     *            use {@link #setTitleValue(Serializable)} instead
     */
    void setTitle(String title);

}
