package de.cuioss.jsf.api.components.model.resultContent;

import javax.faces.application.FacesMessage;

import de.cuioss.jsf.api.components.css.ContextState;
import de.cuioss.uimodel.nameprovider.IDisplayNameProvider;
import de.cuioss.uimodel.result.ResultObject;

/**
 * Handle errors and warning of a {@link ResultObject}. Allow displaying of a
 * notification box or a GlobalFacesMessage and set if the content should be
 * rendered depending on the result state and error.
 */
public interface ErrorController {

    /**
     * Set the value and state of a notification box.
     *
     * @param value if not null, a notification box will be rendered and this value
     *              will be displayed. Otherwise no notification box will be
     *              rendered.
     * @param state the state of the notification box.
     */
    void addNotificationBox(IDisplayNameProvider<?> value, ContextState state);

    /**
     * Add a GlobalFacesMessage.
     *
     * @param value    the text of the faces message.
     * @param severity the severity.
     */
    void addGlobalFacesMessage(IDisplayNameProvider<?> value, FacesMessage.Severity severity);

    /**
     * Add a sticky message.
     *
     * @param value the text of the sticky message.
     * @param state the state of the sticky box.
     */
    void addStickyMessage(IDisplayNameProvider<?> value, ContextState state);

    /**
     * Set if the content should be rendered.
     *
     * @param renderContent true if a should be rendered, otherwise false.
     */
    void setRenderContent(boolean renderContent);

}
