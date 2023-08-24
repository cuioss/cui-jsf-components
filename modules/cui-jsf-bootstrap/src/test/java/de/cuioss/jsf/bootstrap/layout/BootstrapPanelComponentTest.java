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
package de.cuioss.jsf.bootstrap.layout;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.faces.component.UIForm;
import javax.faces.event.PreRenderComponentEvent;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.test.CoreJsfTestConfiguration;
import de.cuioss.test.jsf.component.AbstractUiComponentTest;
import de.cuioss.test.jsf.config.ComponentConfigurator;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import de.cuioss.test.juli.LogAsserts;
import de.cuioss.test.juli.TestLogLevel;
import de.cuioss.test.juli.TestLoggerFactory;
import de.cuioss.test.juli.junit5.EnableTestLogger;

@VerifyComponentProperties(of = { "collapsed", "headerConverter", "headerEscape", "headerKey", "headerValue",
        "deferred", "childrenLoaded" })
@JsfTestConfiguration(CoreJsfTestConfiguration.class)
@EnableTestLogger
class BootstrapPanelComponentTest extends AbstractUiComponentTest<BootstrapPanelComponent>
        implements ComponentConfigurator {

    @Test
    void shouldAddFormTest() {
        var component = new BootstrapPanelComponent();
        component.setDeferred(true);
        component.processEvent(new PreRenderComponentEvent(component));
        LogAsserts.assertLogMessagePresentContaining(TestLogLevel.ERROR, "needs to have a form tag in its ancestry");
        TestLoggerFactory.getTestHandler().clearRecords();

        var form = new UIForm();
        component.setParent(form);
        component.processEvent(new PreRenderComponentEvent(component));
        LogAsserts.assertNoLogMessagePresent(TestLogLevel.ERROR, BootstrapPanelComponent.class);
    }

    @Test
    void shouldRenderSpinnerTest() {
        var component = new BootstrapPanelComponent();
        component.setDeferred(true);
        component.setCollapsed(true);
        assertTrue(component.shouldRenderSpinnerIcon());
        component.setChildrenLoaded(true);
        assertFalse(component.shouldRenderSpinnerIcon());
    }

    @Override
    public void configureComponents(final ComponentConfigDecorator decorator) {
        decorator.registerUIComponent(BootstrapPanelComponent.class)
                .registerMockRenderer(BootstrapFamily.COMPONENT_FAMILY, BootstrapFamily.PANEL_RENDERER);
    }
}
