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

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.FacesComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ListenerFor;
import javax.faces.event.PostAddToViewEvent;

import de.cuioss.jsf.api.components.partial.ForIdentifierProvider;
import de.cuioss.jsf.api.components.util.CuiState;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.layout.BasicBootstrapPanelComponent;
import de.cuioss.tools.collect.CollectionBuilder;
import lombok.ToString;
import lombok.experimental.Delegate;

/**
 * <p>
 * Replaces the HtmlMessage and integrates with the bootstrap based theme. In
 * addition to the usual behavior you can attach multiple ids to one message
 * element, see attribute forIdentifier. In opposite to HtmlMessage the styling
 * is to be done using styleClass attribute, not one of the attributes
 * errorClass, infoClass, etc. Depending on the severity of the FacesMessages,
 * the surrounding will get the class: "cui_msg_info", "cui_msg_warn",
 * "cui_msg_error" or "cui_msg_fatal".
 * </p>
 * <h2>Attributes</h2>
 * <ul>
 * <li>{@link ForIdentifierProvider}</li>
 * <li>showDetail: Flag indicating whether the detail portion of displayed
 * messages should be included. Default value is "false". If false, it is
 * displayed as tooltip.</li>
 * <li>showSummary: Flag indicating wether the summary portion of the
 * FacesMessage should be displayed. Default value is "true".</li>
 * <li>escape: Flag indicating that characters that are sensitive in HTML and
 * XML markup must be escaped. If omitted, this flag is assumed to be
 * "true".</li>
 * <li>rendered: Flag indicating whether or not this component should be
 * rendered (during Render Response Phase), or processed on any subsequent form
 * submit. The default value for this property is true.</li>
 * </ul>
 *
 * @author Matthias Walliczek
 */
@ToString
@ListenerFor(systemEventClass = PostAddToViewEvent.class)
@FacesComponent(BootstrapFamily.CUI_MESSAGE_COMPONENT)
@SuppressWarnings("squid:MaximumInheritanceDepth") // Artifact of Jsf-structure
public class CuiMessageComponent extends BasicBootstrapPanelComponent {

    private static final String SHOW_DETAIL_KEY = "showDetail";
    private static final String SHOW_SUMMARY_KEY = "showSummary";

    private static final String ESCAPE_KEY = "escape";

    @Delegate
    private final ForIdentifierProvider forIdentifierProvider;

    private final CuiState state;

    /**
     *
     */
    public CuiMessageComponent() {
        super.setRendererType(BootstrapFamily.CUI_MESSAGE_COMPONENT_RENDERER);
        forIdentifierProvider = new ForIdentifierProvider(this, ForIdentifierProvider.DEFAULT_FOR_IDENTIFIER);
        state = new CuiState(getStateHelper());
    }

    /**
     * Access the concrete message accessible through this component
     *
     * @return List of found FacesMessages, Empty list if none are found.
     */
    public List<FacesMessage> readMessages() {
        final var messages = new CollectionBuilder<FacesMessage>();
        for (final String resolvedForId : forIdentifierProvider.resolveIdentifierAsList()) {
            final var forComponent = findComponent(resolvedForId);
            if (forComponent == null) {
                continue;
            }
            messages.add(getFacesContext().getMessageList(forComponent.getClientId(getFacesContext())));
        }
        return messages.toImmutableList();
    }

    /**
     * Store flag for show summary
     *
     * @param showDetail
     */
    public void setShowSummary(final boolean showDetail) {
        state.put(SHOW_SUMMARY_KEY, showDetail);
    }

    /**
     * @return stored flag. Default is {@code true}
     */
    public boolean isShowSummary() {
        return state.getBoolean(SHOW_SUMMARY_KEY, true);
    }

    /**
     * Store flag for show details
     *
     * @param showDetail
     */
    public void setShowDetail(final boolean showDetail) {
        state.put(SHOW_DETAIL_KEY, showDetail);
    }

    /**
     * @return stored flag. Default is {@code false}
     */
    public boolean isShowDetail() {
        return state.getBoolean(SHOW_DETAIL_KEY, false);
    }

    /**
     * Store flag if content should be escaped
     *
     * @param escape
     */
    public void setEscape(final boolean escape) {
        state.put(ESCAPE_KEY, escape);
    }

    /**
     * @return stored flag. Default is {@code true}
     */
    public boolean isEscape() {
        return state.getBoolean(ESCAPE_KEY, true);
    }

    @Override
    public boolean isRendered() {
        return super.isRendered() && !readMessages().isEmpty();
    }

    /**
     * Factory method to instantiate a concrete instance of
     * {@link CuiMessageComponent}, usually used if you programmatically add it as a
     * child.
     *
     * @param facesContext must not be null
     * @return concrete instance of {@link CuiMessageComponent}
     */
    public static final CuiMessageComponent create(final FacesContext facesContext) {
        var result = (CuiMessageComponent) facesContext.getApplication()
                .createComponent(BootstrapFamily.CUI_MESSAGE_COMPONENT);
        result.setTransient(true);
        return result;
    }
}
