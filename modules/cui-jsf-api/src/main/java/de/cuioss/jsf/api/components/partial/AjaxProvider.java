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
package de.cuioss.jsf.api.components.partial;

import de.cuioss.jsf.api.components.util.CuiState;
import de.cuioss.tools.logging.CuiLogger;
import de.cuioss.tools.string.Joiner;
import lombok.NonNull;

import jakarta.faces.component.UIComponent;
import jakarta.faces.component.search.SearchExpressionContext;
import jakarta.faces.component.search.SearchExpressionHandler;
import java.util.HashMap;
import java.util.Map;

import static de.cuioss.tools.string.MoreStrings.isEmpty;
import static jakarta.faces.component.search.SearchExpressionContext.createSearchExpressionContext;

/**
 * <h2>Summary</h2>
 * <p>
 * This class manages the state and resolving of the ajax-related attributes.
 * The implementation relies on the correct use of attribute names, saying they
 * must exactly match the accessor methods.
 * </p>
 * <h2>process</h2>
 * <p>
 * Defines which components to process on update. It behaves like the 'execute'
 * attribute on f:ajax regarding resolution of the component ids
 * </p>
 * <h2>update</h2>
 * <p>
 * Defines which components to process on update. It behaves like the 'render'
 * attribute on f:ajax regarding resolution of the component ids
 * </p>
 * The attributes for 'defaultProcess' 'defaultUpdate' and 'dataPrefix' can be
 * configured by using the corresponding fluent methods
 * {@link #ajaxDefaultProcess(String)}, {@link #ajaxDefaultUpdate(String)},
 * {@link #ajaxDataPrefix(String)}
 *
 * @author Oliver Wolff
 */
public class AjaxProvider {

    private static final CuiLogger log = new CuiLogger(AjaxProvider.class);

    /**
     * "data-cui-ajax-".
     */
    public static final String DATA_CUI_AJAX = "data-cui-ajax-";
    /**
     * "update".
     */
    public static final String UPDATE_KEY = "update";
    /**
     * "process".
     */
    public static final String PROCESS_KEY = "process";

    private final CuiState state;
    private final ComponentBridge bridge;

    private String defaultProcess;
    private String defaultUpdate;
    private String dataPrefix = DATA_CUI_AJAX;

    /**
     * @param bridge must not be null
     */
    public AjaxProvider(@NonNull ComponentBridge bridge) {
        state = new CuiState(bridge.stateHelper());
        this.bridge = bridge;
    }

    /**
     * @param dataPrefix to be set. If not, set its defaults to "data-cui-ajax-"
     * @return instance of the current {@link AjaxProvider} in order to use it in a
     * fluent way.
     */
    public AjaxProvider ajaxDataPrefix(String dataPrefix) {
        this.dataPrefix = dataPrefix;
        return this;
    }

    /**
     * @param defaultProcess to be set
     * @return instance of the current {@link AjaxProvider} in order to use it in a
     * fluent way.
     */
    public AjaxProvider ajaxDefaultProcess(String defaultProcess) {
        this.defaultProcess = defaultProcess;
        return this;
    }

    /**
     * @param defaultUpdate to be set
     * @return instance of the current {@link AjaxProvider} in order to use it in a
     * fluent way.
     */
    public AjaxProvider ajaxDefaultUpdate(String defaultUpdate) {
        this.defaultUpdate = defaultUpdate;
        return this;
    }

    /**
     * @return process
     */
    public String getProcess() {
        return state.get(PROCESS_KEY, defaultProcess);
    }

    /**
     * @param process to be set
     */
    public void setProcess(final String process) {
        state.put(PROCESS_KEY, process);
    }

    /**
     * @return update
     */
    public String getUpdate() {
        return state.get(UPDATE_KEY, defaultUpdate);
    }

    /**
     * @param update to be set
     */
    public void setUpdate(final String update) {
        state.put(UPDATE_KEY, update);
    }

    /**
     * Resolves the attributes 'process' and 'update' as data-attributes prefixed as
     * defined with {@link #ajaxDataPrefix(String)}
     *
     * @param component the source component
     * @return the resolved map. Is never {@code null} but may be empty
     */
    public Map<String, String> resolveAjaxAttributesAsMap(UIComponent component) {
        Map<String, String> result = new HashMap<>();
        var update = getUpdate();
        var process = getProcess();
        var searchContext = createSearchExpressionContext(bridge.facesContext(), component);
        var handler = bridge.facesContext().getApplication().getSearchExpressionHandler();

        if (!isEmpty(process)) {
            addAllAttributes(PROCESS_KEY, process, searchContext, handler, result);
        }
        if (!isEmpty(update)) {
            addAllAttributes(UPDATE_KEY, update, searchContext, handler, result);
        }

        log.trace("Created pass through-map {}", result);
        return result;
    }

    private void addAllAttributes(String elementName, String elementIdentifier, SearchExpressionContext searchContext,
                                  SearchExpressionHandler handler, Map<String, String> attributes) {
        var foundIds = handler.resolveClientIds(searchContext, elementIdentifier);
        if (!foundIds.isEmpty()) {
            attributes.put(dataPrefix + elementName, Joiner.on(' ').join(foundIds));
        }
    }
}
