package de.cuioss.jsf.api.components.model.widget;

import javax.faces.event.PostAddToViewEvent;

import de.cuioss.jsf.api.components.model.lazyloading.LazyLoadingModel;
import de.cuioss.uimodel.nameprovider.IDisplayNameProvider;

/**
 * Data model for the widget component to be used at patient overview. See
 * https://wiki.icw.int/display/PE/PEP+Information+Architecture.
 *
 * @author Matthias Walliczek
 */
public interface WidgetModel extends LazyLoadingModel {

    /**
     * @return the title key of the widget.
     */
    IDisplayNameProvider<?> getTitle();

    /**
     * @return the name of the icon or null.
     */
    String getTitleIcon();

    /**
     * @return the title of the widget.
     *         {@linkplain #getTitleValue()} takes precedence over {@linkplain #getTitle()}.
     */
    String getTitleValue();

    /**
     * @return the action outcome of the core widget function.
     */
    String getCoreAction();

    /**
     * @return the action outcome of the primary widget function.
     */
    String getPrimaryAction();

    /**
     * @return the action title of the primary widget function.
     */
    IDisplayNameProvider<?> getPrimaryActionTitle();

    /**
     * @return should the core action link be disabled?
     */
    boolean isDisableCoreAction();

    /**
     * @return should the primary action link be rendered?
     */
    boolean isRenderPrimaryAction();

    /**
     * @return should the primary action link be disabled?
     */
    boolean isDisablePrimaryAction();

    /**
     * @return the id used for sorting
     */
    String getId();

    /**
     * @return should the widget be rendered?
     */
    boolean isRendered();

    /**
     * Start the initialization during {@link PostAddToViewEvent}. Should return immediate.
     */
    void startInitialize();
}
