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
package de.cuioss.jsf.bootstrap.checkbox;

import de.cuioss.jsf.api.components.base.BaseCuiHtmlSelectBooleanCheckboxComponent;
import de.cuioss.jsf.api.components.css.StyleClassBuilder;
import de.cuioss.jsf.api.components.css.impl.StyleClassBuilderImpl;
import de.cuioss.jsf.api.components.partial.TitleProvider;
import de.cuioss.jsf.api.components.partial.TitleProviderImpl;
import de.cuioss.jsf.api.components.support.LabelResolver;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.common.partial.ColumnProvider;
import de.cuioss.tools.collect.MapBuilder;
import lombok.experimental.Delegate;
import org.omnifaces.util.State;

import javax.faces.component.FacesComponent;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ListenerFor;
import javax.faces.event.PreRenderComponentEvent;
import java.io.Serializable;
import java.util.*;

/**
 * @author Oliver Wolff
 * @author Sven Haag
 */
@ListenerFor(systemEventClass = PreRenderComponentEvent.class)
@FacesComponent(BootstrapFamily.SWITCH_COMPONENT)
@SuppressWarnings("squid:MaximumInheritanceDepth") // Artifact of Jsf-structure
public class SwitchComponent extends BaseCuiHtmlSelectBooleanCheckboxComponent {

    private static final String OFF_TEXT_VALUE = "offTextValue";
    private static final String OFF_TEXT_KEY = "offTextKey";
    private static final String OFF_TEXT_CONVERTER = "offTextConverter";

    private static final String ON_TEXT_VALUE = "onTextValue";
    private static final String ON_TEXT_KEY = "onTextKey";
    private static final String ON_TEXT_CONVERTER = "onTextConverter";

    private static final Integer DEFAULT_COLUMN_SIZE = 6;

    private static final String DATA_SWITCH_DISABLED = "data-switch-disabled";

    static final String CONTAINER_SUFFIX = "container";

    @Delegate
    private final TitleProvider titleProvider;

    @Delegate
    private final ColumnProvider columnProvider;

    private final State state = new State(getStateHelper());

    /**
     * Default Constructor
     */
    public SwitchComponent() {
        setRendererType(BootstrapFamily.SWITCH_RENDERER);
        titleProvider = new TitleProviderImpl(this);
        columnProvider = new ColumnProvider(this, DEFAULT_COLUMN_SIZE);
    }

    /**
     * @return the value for offTextKey
     */
    public String getOffTextKey() {
        return state.get(OFF_TEXT_KEY);
    }

    /**
     * @param offTextKey to be set
     */
    public void setOffTextKey(final String offTextKey) {
        state.put(OFF_TEXT_KEY, offTextKey);
    }

    /**
     * @return the value for offTextValue
     */
    public Serializable getOffTextValue() {
        return state.get(OFF_TEXT_VALUE);
    }

    /**
     * @param offTextValue to be set
     */
    public void setOffTextValue(final Serializable offTextValue) {
        state.put(OFF_TEXT_VALUE, offTextValue);
    }

    /**
     * @return labelConverter converter for offTextValue
     */
    public Object getOffTextConverter() {
        return state.get(OFF_TEXT_CONVERTER);
    }

    /**
     * @param labelConverter converter for offTextValue
     */
    public void setOffTextConverter(final Object labelConverter) {
        state.put(OFF_TEXT_CONVERTER, labelConverter);
    }

    /**
     * @return the String for the offText, resolved by using
     * {@link #getOffTextKey()} and {@link #getOffTextValue()}
     */
    public String resolveOffText() {
        return LabelResolver.builder().withLabelKey(getOffTextKey()).withLabelValue(getOffTextValue())
            .withConverter(getOffTextConverter()).withStrictMode(false).build().resolve(getFacesContext());
    }

    /**
     * @return the value for onTextKey
     */
    public String getOnTextKey() {
        return state.get(ON_TEXT_KEY);
    }

    /**
     * @param onTextKey to be set
     */
    public void setOnTextKey(final String onTextKey) {
        state.put(ON_TEXT_KEY, onTextKey);
    }

    /**
     * @return the value for onTextValue
     */
    public Serializable getOnTextValue() {
        return state.get(ON_TEXT_VALUE);
    }

    /**
     * @param onTextValue to be set
     */
    public void setOnTextValue(final Serializable onTextValue) {
        state.put(ON_TEXT_VALUE, onTextValue);
    }

    /**
     * @return the String for the onText, resolved by using {@link #getOnTextKey()}
     * and {@link #getOnTextValue()}
     */
    public String resolveOnText() {
        return LabelResolver.builder().withLabelKey(getOnTextKey()).withLabelValue(getOnTextValue())
            .withConverter(getOnTextConverter()).build().resolve(getFacesContext());
    }

    /**
     * @return labelConverter converter for onTextValue
     */
    public Object getOnTextConverter() {
        return state.get(ON_TEXT_CONVERTER);
    }

    /**
     * @param labelConverter converter for onTextValue
     */
    public void setOnTextConverter(final Object labelConverter) {
        state.put(ON_TEXT_CONVERTER, labelConverter);
    }

    /**
     * @return <code>null</code> because no style should be set on the actual
     * checkbox
     */
    @Override
    public String getStyle() {
        return null;
    }

    /**
     * @return the original style
     */
    public String resolveStyle() {
        return super.getStyle();
    }

    /**
     * @return <code>null</code> because no styleClass should be set on the actual
     * checkbox
     */
    @Override
    public String getStyleClass() {
        return null;
    }

    /**
     * @return the original styleClass
     */
    @Override
    public StyleClassBuilder getStyleClassBuilder() {
        return new StyleClassBuilderImpl().append(super.getStyleClass());
    }

    /**
     * @return passThroughAttributes for the container
     */
    public Map<String, Object> resolvePassThroughAttributes() {
        return new MapBuilder<String, Object>().putAll(getPassThroughAttributes())
                .put(DATA_SWITCH_DISABLED, String.valueOf(isDisabled())).toImmutableMap();
    }

    @Override
    public void processEvent(final ComponentSystemEvent event) {
        replaceClientBehaviorIds();
        super.processEvent(event);
    }

    private void replaceClientBehaviorIds() {
        for (final List<ClientBehavior> clientBehavior : getClientBehaviors().values()) {
            if (clientBehavior instanceof javax.faces.component.behavior.AjaxBehavior ajaxBehavior) {
                ajaxBehavior.setRender(replaceIds(ajaxBehavior.getRender()));
            }
        }
    }

    private Collection<String> replaceIds(final Collection<String> ids) {
        if (null == ids) {
            return Collections.emptyList();
        }

        final List<String> changedIds = new ArrayList<>();
        for (String id : ids) {
            changedIds.add(replaceId(id));
        }
        return changedIds;
    }

    private String replaceId(final String id) {
        final var clientId = getClientId();
        final var containerId = clientId + "_" + CONTAINER_SUFFIX;

        if ("@this".equalsIgnoreCase(id) || getClientId().equals(id)) {
            return containerId;
        }
        return id;
    }

    @Override
    public String getFamily() {
        return BootstrapFamily.COMPONENT_FAMILY;
    }
}
