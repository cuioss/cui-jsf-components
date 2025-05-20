/*
 * Copyright 2023 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.bootstrap.layout.messages;

import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.CssCuiBootstrap;
import jakarta.faces.component.FacesComponent;
import jakarta.faces.component.html.HtmlMessages;

/**
 * <p>
 * A Bootstrap-styled version of the standard JSF {@link HtmlMessages} component for displaying
 * all queued FacesMessages that are not associated with specific components.
 * </p>
 * <p>
 * This component automatically applies Bootstrap alert styling to messages based on their severity:
 * </p>
 * <ul>
 * <li>FATAL: "alert alert-danger"</li>
 * <li>ERROR: "alert alert-danger"</li>
 * <li>WARN: "alert alert-warning"</li>
 * <li>INFO: "alert alert-info"</li>
 * </ul>
 * <p>
 * The component wrapper is styled with "cui-messages" class to provide consistent styling
 * across the application.
 * </p>
 * 
 * <h2>Usage</h2>
 * <pre>
 * &lt;!-- Basic usage --&gt;
 * &lt;boot:messages /&gt;
 * 
 * &lt;!-- With customized display options --&gt;
 * &lt;boot:messages showDetail="true" showSummary="true" globalOnly="true" /&gt;
 * </pre>
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
