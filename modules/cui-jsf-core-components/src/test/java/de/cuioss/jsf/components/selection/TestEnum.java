package de.cuioss.jsf.components.selection;

import de.cuioss.uimodel.nameprovider.LabelKeyProvider;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Oliver Wolff
 */
@RequiredArgsConstructor
public enum TestEnum implements LabelKeyProvider {

    /** */
    DAY("common.abb.day"),
    /** */
    MONTH("common.abb.month"),
    /** */
    YEAR("common.abb.year");

    @Getter
    private final String labelKey;
}
