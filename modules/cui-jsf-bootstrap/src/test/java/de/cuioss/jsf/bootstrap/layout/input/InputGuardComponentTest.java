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
package de.cuioss.jsf.bootstrap.layout.input;

import static de.cuioss.jsf.api.components.util.ComponentUtility.JAVAX_FACES_SOURCE;
import static de.cuioss.jsf.bootstrap.layout.input.InputGuardComponent.FIXED_ID;
import static de.cuioss.test.generator.Generators.letterStrings;
import static de.cuioss.tools.collect.CollectionLiterals.mutableList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.faces.component.UIComponent;
import javax.faces.event.PostAddToViewEvent;
import javax.faces.event.PreRenderComponentEvent;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.components.JsfComponentIdentifier;
import de.cuioss.jsf.api.components.base.AbstractBaseCuiComponent;
import de.cuioss.jsf.api.components.partial.AjaxProvider;
import de.cuioss.jsf.bootstrap.button.CommandButton;
import de.cuioss.jsf.bootstrap.layout.input.support.GuardButtonAttributes;
import de.cuioss.jsf.bootstrap.layout.input.support.MockUIInput;
import de.cuioss.jsf.bootstrap.layout.input.support.ResetGuardButtonAttributes;
import de.cuioss.jsf.bootstrap.layout.messages.CuiMessageComponent;
import de.cuioss.jsf.bootstrap.layout.messages.CuiMessageRenderer;
import de.cuioss.jsf.test.CoreJsfTestConfiguration;
import de.cuioss.test.jsf.component.AbstractComponentTest;
import de.cuioss.test.jsf.config.ComponentConfigurator;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import de.cuioss.test.jsf.mocks.CuiMockSearchExpressionHandler;

@VerifyComponentProperties(of = { "buttonAlign", "resetGuardIcon", "guardIcon", "guardButtonTitleKey",
        "guardButtonTitleValue", "guardButtonTitleConverter", "guardButtonTitleEscape", "resetGuardButtonTitleKey",
        "resetInputValue", "resetGuardButtonTitleValue", "resetGuardButtonTitleConverter", "update", "process",
        "resetGuardButtonTitleEscape", "renderButtons" })
@JsfTestConfiguration(CoreJsfTestConfiguration.class)
class InputGuardComponentTest extends AbstractComponentTest<InputGuardComponent> implements ComponentConfigurator {

    static final String DEFAULT_UPDATE_KEY = AjaxProvider.DATA_CUI_AJAX + AjaxProvider.UPDATE_KEY;

    static final String DEFAULT_PROCESS_KEY = AjaxProvider.DATA_CUI_AJAX + AjaxProvider.PROCESS_KEY;

    @Test
    void shouldProvideMetadata() {
        assertEquals(JsfComponentIdentifier.INPUT_FAMILY, anyComponent().getFamily());
        assertEquals(JsfComponentIdentifier.HIDDEN_RENDERER_TYPE, anyComponent().getRendererType());
    }

    @Test
    void shouldProvideFixedAttributes() {
        assertEquals(FIXED_ID, anyComponent().getId());
    }

    @Test
    void shouldHandleValueAttribute() {
        var component = anyComponent();
        component.setValue(Boolean.FALSE);
        component.prerender((LabeledContainerComponent) component.getParent());
        assertFalse(Boolean.parseBoolean(component.getValue().toString()));
    }

    @Test
    void shouldModifyContainerDisabled() {
        var component = anyComponent();
        var parent = (LabeledContainerComponent) component.getParent();
        component.setValue(Boolean.FALSE);
        component.prerender((LabeledContainerComponent) component.getParent());
        assertFalse(parent.isDisabled());
        component.setValue(Boolean.TRUE);
        component.prerender((LabeledContainerComponent) component.getParent());
        assertTrue(parent.isDisabled());
    }

    @Test
    void shouldNotModifyContainerDisabledIfNotRendered() {
        var component = anyComponent();
        var parent = (LabeledContainerComponent) component.getParent();
        component.setValue(Boolean.FALSE);
        component.setRendered(false);
        component.prerender((LabeledContainerComponent) component.getParent());
        assertFalse(parent.isDisabled());
    }

    @Test
    void shouldProvideAsContainerFacets() {
        var component = anyComponent();
        var parent = (LabeledContainerComponent) component.getParent();
        UIComponent mock = new AbstractBaseCuiComponent() {

            @Override
            public String getFamily() {
                return "mock";
            }
        };
        parent.getFacets().put(ContainerFacets.APPEND.getName(), mock);
        component.prerender((LabeledContainerComponent) component.getParent());
        assertNotEquals(mock, parent.getAppendFacet());
    }

    @Test
    void shouldAddGuardButtonToContainer() {
        var component = anyComponent();
        var parent = (LabeledContainerComponent) component.getParent();
        var title = letterStrings().next();
        component.setGuardButtonTitleValue(title);
        component.prerender((LabeledContainerComponent) component.getParent());
        var added = parent.getAppendFacet();
        assertTrue(added instanceof CommandButton);
        var button = (CommandButton) added;
        assertEquals(GuardButtonAttributes.ICON_DEFAULT, button.getIcon());
        assertEquals(title, button.getTitleValue());
        assertTrue(button.getPassThroughAttributes().containsKey(InputGuardComponent.DATA_GUARDED_BUTTON));
        assertEquals(Boolean.FALSE, button.getPassThroughAttributes().get(InputGuardComponent.DATA_GUARDED_TARGET));
    }

    @Test
    void shouldModifyGuardButton() {
        var component = anyComponent();
        var title1 = letterStrings().next();
        component.setGuardButtonTitleValue(title1);
        var parent = (LabeledContainerComponent) component.getParent();
        component.prerender(parent);
        var button = (CommandButton) component.provideFacetContent(ContainerFacets.APPEND).get();
        assertEquals(GuardButtonAttributes.ICON_DEFAULT, button.getIcon());
        assertEquals(title1, button.getTitleValue());
        assertTrue(button.getPassThroughAttributes().containsKey(InputGuardComponent.DATA_GUARDED_BUTTON));
        assertEquals(Boolean.FALSE, button.getPassThroughAttributes().get(InputGuardComponent.DATA_GUARDED_TARGET));
        // Now Modify
        component.setValue(Boolean.FALSE);
        var title2 = letterStrings().next();
        component.setResetGuardButtonTitleValue(title2);
        component.prerender((LabeledContainerComponent) component.getParent());
        var button2 = (CommandButton) component.provideFacetContent(ContainerFacets.APPEND).get();
        assertEquals(ResetGuardButtonAttributes.ICON_DEFAULT, button2.getIcon());
        assertEquals(title2, button2.getTitleValue());
        assertTrue(button.getPassThroughAttributes().containsKey(InputGuardComponent.DATA_GUARDED_BUTTON));
        assertEquals(Boolean.TRUE, button2.getPassThroughAttributes().get(InputGuardComponent.DATA_GUARDED_TARGET));
    }

    @Test
    void shouldNotAddGuardButtonToContainer() {
        var component = anyComponent();
        component.setRenderButtons(false);
        var parent = (LabeledContainerComponent) component.getParent();
        var title = letterStrings().next();
        component.setGuardButtonTitleValue(title);
        component.prerender((LabeledContainerComponent) component.getParent());
        assertNull(parent.getFacet(ContainerFacets.APPEND.getName()));
    }

    @Test
    void shouldProvideAjaxAttributes() {
        var ajaxId = "thisId";
        CuiMockSearchExpressionHandler.retrieve(getFacesContext()).setResolvedClientIds(mutableList(ajaxId));
        var component = anyComponent();
        component.prerender((LabeledContainerComponent) component.getParent());
        var map = component.getPassThroughAttributes();
        assertTrue(map.containsKey(DEFAULT_PROCESS_KEY));
        assertTrue(map.containsKey(DEFAULT_UPDATE_KEY));
        assertEquals(ajaxId, map.get(DEFAULT_PROCESS_KEY));
        assertEquals(ajaxId, map.get(DEFAULT_UPDATE_KEY));
    }

    @Test
    void shouldAddResetGuardButtonToContainer() {
        var component = anyComponent();
        var parent = (LabeledContainerComponent) component.getParent();
        var title = letterStrings().next();
        component.setResetGuardButtonTitleValue(title);
        component.setValue(Boolean.FALSE);
        component.prerender((LabeledContainerComponent) component.getParent());
        var added = parent.getAppendFacet();
        assertTrue(added instanceof CommandButton);
        var button = (CommandButton) added;
        assertEquals(ResetGuardButtonAttributes.ICON_DEFAULT, button.getIcon());
        assertEquals(title, button.getTitleValue());
    }

    @Test
    void shouldNotAddResetGuardButtonToContainer() {
        var component = anyComponent();
        component.setRenderButtons(false);
        component.setValue(Boolean.FALSE);
        var parent = (LabeledContainerComponent) component.getParent();
        var title = letterStrings().next();
        component.setGuardButtonTitleValue(title);
        component.prerender((LabeledContainerComponent) component.getParent());
        assertNull(parent.getFacet(ContainerFacets.APPEND.getName()));
    }

    @Test
    void shouldHandleFacetProviding() {
        var component = anyComponent();
        var parent = (LabeledContainerComponent) component.getParent();
        component.postAddToView(parent);
        component.prerender(parent);
        assertTrue(component.provideFacetContent(ContainerFacets.APPEND).isPresent());
        assertFalse(component.provideFacetContent(ContainerFacets.LABEL).isPresent());
        assertFalse(component.provideFacetContent(ContainerFacets.PREPEND).isPresent());
        component.setRendered(false);
        assertFalse(component.provideFacetContent(ContainerFacets.APPEND).isPresent());
        component.setRendered(true);
        component.setRenderButtons(false);
        assertFalse(component.provideFacetContent(ContainerFacets.APPEND).isPresent());
    }

    @Test
    void shouldHandlePluginState() {
        var component = anyComponent();
        assertEquals(PluginStateInfo.NO_STATE_INFO, component.provideContainerStateInfo());
        component.setRendered(false);
        assertEquals(PluginStateInfo.NO_STATE_INFO, component.provideContainerStateInfo());
        component.setValue(Boolean.FALSE);
        assertEquals(PluginStateInfo.NO_STATE_INFO, component.provideContainerStateInfo());
        component.setRendered(true);
        assertEquals(PluginStateInfo.WARNING, component.provideContainerStateInfo());
    }

    @Test
    void shouldHandleDecodeWithoutPayload() {
        var component = prepareCompleteSetup();
        var input = accessRelatedInput(component);
        // Initial Values
        assertFalse(input.isResetValueCalled());
        assertEquals(Boolean.TRUE, component.getValue());
        assertNull(input.getValue());
        component.decode(getFacesContext());
        // nothing should have happened
        assertFalse(input.isResetValueCalled());
        assertEquals(Boolean.TRUE, component.getValue());
        assertNull(input.getSubmittedValue());
        component.setRendered(false);
        component.decode(getFacesContext());
        // nothing should have happened
        assertFalse(input.isResetValueCalled());
        assertEquals(Boolean.TRUE, component.getValue());
        assertNull(input.getSubmittedValue());
        component.setRendered(true);
        var clientId = component.getClientId(getFacesContext());
        getRequestConfigDecorator().setRequestParameter(JAVAX_FACES_SOURCE, clientId);
        component.decode(getFacesContext());
        // nothing should have happened
        assertFalse(input.isResetValueCalled());
        assertEquals(Boolean.TRUE, component.getValue());
        assertNull(input.getSubmittedValue());
    }

    @Test
    void shouldHandleDecodeWithoutChanges() {
        var component = prepareCompleteSetup();
        var input = accessRelatedInput(component);
        var clientId = component.getClientId(getFacesContext());
        getRequestConfigDecorator().setRequestParameter(JAVAX_FACES_SOURCE, clientId);
        // same value
        getRequestConfigDecorator().setRequestParameter(clientId, Boolean.TRUE.toString());
        component.decode(getFacesContext());
        // nothing should have happened
        assertFalse(input.isResetValueCalled());
        assertEquals(Boolean.TRUE, component.getValue());
        assertNull(input.getSubmittedValue());
    }

    @Test
    void shouldHandleDecodeWithChanges() {
        var component = prepareCompleteSetup();
        var input = accessRelatedInput(component);
        component.setValue(Boolean.FALSE);
        var clientId = component.getClientId(getFacesContext());
        getRequestConfigDecorator().setRequestParameter(JAVAX_FACES_SOURCE, clientId);
        getRequestConfigDecorator().setRequestParameter(clientId, Boolean.TRUE.toString());
        component.decode(getFacesContext());
        assertTrue(input.isResetValueCalled());
        assertEquals(Boolean.TRUE.toString(), component.getSubmittedValue());
    }

    @Test
    void shouldHandleDecodeWithDisableResetValue() {
        var component = prepareCompleteSetup();
        component.setResetInputValue(false);
        var input = accessRelatedInput(component);
        var clientId = component.getClientId(getFacesContext());
        getRequestConfigDecorator().setRequestParameter(JAVAX_FACES_SOURCE, clientId);
        getRequestConfigDecorator().setRequestParameter(clientId, Boolean.FALSE.toString());
        component.decode(getFacesContext());
        assertFalse(input.isResetValueCalled());
        assertEquals(Boolean.FALSE.toString(), component.getSubmittedValue());
    }

    @Test
    void shouldFailDecodeOnInvalidNesting() {
        var component = new InputGuardComponent();
        component.setValue(Boolean.FALSE);
        var clientId = component.getClientId(getFacesContext());
        getRequestConfigDecorator().setRequestParameter(JAVAX_FACES_SOURCE, clientId);
        getRequestConfigDecorator().setRequestParameter(clientId, Boolean.TRUE.toString());
        var facesContext = getFacesContext();
        assertThrows(IllegalStateException.class, () -> component.decode(facesContext));
    }

    private MockUIInput accessRelatedInput(InputGuardComponent component) {
        return (MockUIInput) component.getParent().getChildren().stream().filter(child -> "input".equals(child.getId()))
                .findFirst().get();
    }

    private InputGuardComponent prepareCompleteSetup() {
        var component = anyComponent();
        var parent = (LabeledContainerComponent) component.getParent();
        parent.setId("parent");
        var input = new MockUIInput();
        input.setId("input");
        parent.getChildren().add(input);
        parent.processEvent(new PostAddToViewEvent(parent));
        parent.processEvent(new PreRenderComponentEvent(parent));
        return component;
    }

    @Override
    public void configure(InputGuardComponent toBeConfigured) {
        var parent = new LabeledContainerComponent();
        parent.setId("labeledContainer");
        parent.getChildren().add(toBeConfigured);
    }

    @Override
    public void configureComponents(ComponentConfigDecorator decorator) {
        decorator.registerUIComponent(CommandButton.class).registerUIComponent(CuiMessageComponent.class)
                .registerRenderer(CuiMessageRenderer.class).registerRenderer(LabeledContainerRenderer.class)
                .registerMockRendererForHtmlInputText();
    }
}
