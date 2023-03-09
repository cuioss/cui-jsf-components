package com.icw.ehf.cui.components.bootstrap.common.partial;

import javax.faces.component.StateHelper;

import com.icw.ehf.cui.core.api.components.css.StyleClassBuilder;
import com.icw.ehf.cui.core.api.components.partial.ComponentBridge;
import com.icw.ehf.cui.core.api.components.util.CuiState;

import lombok.NonNull;

/**
 * <h2>Summary</h2>
 * <p>
 * Implementors of this class manage the state and resolving of the bootstrap column-css. It is
 * computed out of the given attributes.
 * </p>
 * <h2>labelSize</h2>
 * <p>
 * The size of the column. Must be between 1-12. Is required.
 * </p>
 * <h2>labelStyleClass</h2>
 * <p>
 * Additional styleClass attributes for the column. Is not required.
 * </p>
 *
 * @author Oliver Wolff
 */
public class LabelColumnProvider {

    /** The key for the {@link StateHelper} */
    private static final String SIZE_KEY = "labelSize";

    /** The key for the {@link StateHelper} */
    private static final String STYLE_CLASS_KEY = "labelStyleClass";

    private final CuiState state;

    /**
     * Defines the default size for this partial, in case no explicit size is given by the client.
     */
    private final Integer defaultSize;

    /**
     * @param bridge must not be null
     * @param defaultSize the default size for this partial, in case no explicit size is given by
     *            the client.
     */
    public LabelColumnProvider(@NonNull ComponentBridge bridge, @NonNull Integer defaultSize) {
        state = new CuiState(bridge.stateHelper());
        this.defaultSize = defaultSize;
    }

    /**
     * @return The size of the column
     */
    public Integer getLabelSize() {
        return state.get(SIZE_KEY, defaultSize);
    }

    /**
     * @param size the size to set. Must be between 1-12
     */
    public void setLabelSize(final Integer size) {
        state.put(SIZE_KEY, size);
    }

    /**
     * @return the styleClass
     */
    public String getLabelStyleClass() {
        return state.get(STYLE_CLASS_KEY);
    }

    /**
     * @param styleClass the styleClass to set
     */
    public void setLabelStyleClass(final String styleClass) {
        state.put(STYLE_CLASS_KEY, styleClass);
    }

    /**
     * @param renderAsColumn
     * @return the resolved column-css.
     */
    public StyleClassBuilder resolveLabelCss(final boolean renderAsColumn) {
        return new ColumnCssResolver(getLabelSize(), null, renderAsColumn, getLabelStyleClass())
                .resolveColumnCss();
    }
}
