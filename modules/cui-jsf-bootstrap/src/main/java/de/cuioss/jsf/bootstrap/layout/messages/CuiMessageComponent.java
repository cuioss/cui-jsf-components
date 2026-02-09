/*
 * Copyright © 2025 CUI-OpenSource-Software (info@cuioss.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.bootstrap.layout.messages;

import de.cuioss.jsf.api.components.partial.ForIdentifierProvider;
import de.cuioss.jsf.api.components.util.CuiState;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.layout.BasicBootstrapPanelComponent;
import de.cuioss.tools.collect.CollectionBuilder;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.FacesComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ListenerFor;
import jakarta.faces.event.PostAddToViewEvent;
import lombok.ToString;
import lombok.experimental.Delegate;

import java.util.List;

/**
 * <p>
 * Bootstrap-styled message component that displays validation messages and other feedback.
 * This component enhances the standard JSF message functionality with Bootstrap styling
 * and additional features.
 * </p>
 *
 * <h2>Features</h2>
 * <ul>
 * <li>Bootstrap-styled message presentation with contextual styling based on message severity</li>
 * <li>Support for multiple component IDs in a single message display</li>
 * <li>Configurable display of summary and detail portions</li>
 * <li>Automatic styling based on message severity (info, warning, error, fatal)</li>
 * <li>Only renders when messages are present</li>
 * </ul>
 *
 * <h2>Message Styling</h2>
 * <p>
 * The component applies the following CSS classes based on message severity:
 * </p>
 * <ul>
 * <li>Info messages: "cui_msg_info"</li>
 * <li>Warning messages: "cui_msg_warn"</li>
 * <li>Error messages: "cui_msg_error"</li>
 * <li>Fatal messages: "cui_msg_fatal"</li>
 * </ul>
 *
 * <h2>Attributes</h2>
 * <ul>
 * <li>{@link ForIdentifierProvider} - Target component ID(s) to display messages for</li>
 * <li>showDetail - Whether to show message details (default: false, shown as tooltip)</li>
 * <li>showSummary - Whether to show message summary (default: true)</li>
 * <li>escape - Whether to escape HTML in messages (default: true)</li>
 * </ul>
 *
 * <h2>Usage</h2>
 *
 * <pre>
 * &lt;!-- Basic usage for a single input --&gt;
 * &lt;boot:message forIdentifier="inputId" /&gt;
 *
 * &lt;!-- With multiple inputs --&gt;
 * &lt;boot:message forIdentifier="firstName secondName email" /&gt;
 *
 * &lt;!-- With customized display options --&gt;
 * &lt;boot:message forIdentifier="inputId"
 *             showDetail="true"
 *             showSummary="true"
 *             escape="false" /&gt;
 * </pre>
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
    public static CuiMessageComponent create(final FacesContext facesContext) {
        var result = (CuiMessageComponent) facesContext.getApplication()
                .createComponent(BootstrapFamily.CUI_MESSAGE_COMPONENT);
        result.setTransient(true);
        return result;
    }
}
