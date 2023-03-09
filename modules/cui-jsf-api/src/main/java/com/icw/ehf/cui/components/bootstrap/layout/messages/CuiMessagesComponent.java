package com.icw.ehf.cui.components.bootstrap.layout.messages;

import javax.faces.component.FacesComponent;
import javax.faces.component.html.HtmlMessages;

import com.icw.ehf.cui.components.bootstrap.BootstrapFamily;
import com.icw.ehf.cui.components.bootstrap.CssCuiBootstrap;

/**
 * Extends {@link HtmlMessages} and sets bootstrap aligned style classes.
 *
 * @author Matthias Walliczek
 *
 */
@FacesComponent(BootstrapFamily.CUI_MESSAGES_COMPONENT)
public class CuiMessagesComponent extends HtmlMessages {

    /**
     * Initialize and set bootstrap aligned style classes.
     */
    public CuiMessagesComponent() {
        super();
        super.setFatalClass("alert alert-danger");
        super.setErrorClass("alert alert-danger");
        super.setWarnClass("alert alert-warning");
        super.setInfoClass("alert alert-info");
        super.setStyleClass(CssCuiBootstrap.CUI_MESSAGES_CLASS.getStyleClass());
    }

    @Override
    public void setStyleClass(final String styleClass) {
        super.setStyleClass(
                CssCuiBootstrap.CUI_MESSAGES_CLASS.getStyleClassBuilder().append(styleClass).getStyleClass());
    }

}
