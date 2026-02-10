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
package de.cuioss.jsf.bootstrap.layout;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.common.logging.BootstrapLogMessages;
import de.cuioss.jsf.test.CoreJsfTestConfiguration;
import de.cuioss.jsf.test.EnableJSFCDIEnvironment;
import de.cuioss.jsf.test.EnableResourceBundleSupport;
import de.cuioss.test.jsf.component.AbstractUiComponentTest;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import de.cuioss.test.juli.LogAsserts;
import de.cuioss.test.juli.TestLogLevel;
import de.cuioss.test.juli.TestLoggerFactory;
import de.cuioss.test.juli.junit5.EnableTestLogger;
import jakarta.faces.component.UIForm;
import jakarta.faces.event.PreRenderComponentEvent;
import org.jboss.weld.junit5.ExplicitParamInjection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@VerifyComponentProperties(of = {"collapsed", "headerConverter", "headerEscape", "headerKey", "headerValue",
        "deferred", "childrenLoaded"})
@JsfTestConfiguration(CoreJsfTestConfiguration.class)
@EnableTestLogger
@EnableJSFCDIEnvironment
@EnableResourceBundleSupport
@ExplicitParamInjection
@DisplayName("Tests for BootstrapPanelComponent")
class BootstrapPanelComponentTest extends AbstractUiComponentTest<BootstrapPanelComponent> {

    @BeforeEach
    void configureComponents(ComponentConfigDecorator decorator) {
        CoreJsfTestConfiguration.configureComponents(decorator);
        decorator.registerUIComponent(BootstrapPanelComponent.class)
                .registerMockRenderer(BootstrapFamily.COMPONENT_FAMILY, BootstrapFamily.PANEL_RENDERER);
    }

    @Nested
    @DisplayName("Tests for form requirement")
    class FormRequirementTests {

        @Test
        @DisplayName("Should log error when no form is present")
        void shouldLogErrorWhenNoFormIsPresent() {
            // Arrange
            var component = new BootstrapPanelComponent();
            component.setDeferred(true);

            // Act
            component.processEvent(new PreRenderComponentEvent(component));

            // Assert
            LogAsserts.assertLogMessagePresentContaining(TestLogLevel.ERROR,
                    BootstrapLogMessages.ERROR.COMPONENT_REQUIRES_FORM.resolveIdentifierString());
        }

        @Test
        @DisplayName("Should not log error when form is present")
        void shouldNotLogErrorWhenFormIsPresent() {
            // Arrange
            var component = new BootstrapPanelComponent();
            component.setDeferred(true);
            var form = new UIForm();
            component.setParent(form);
            TestLoggerFactory.getTestHandler().clearRecords();

            // Act
            component.processEvent(new PreRenderComponentEvent(component));

            // Assert
            LogAsserts.assertNoLogMessagePresent(TestLogLevel.ERROR, BootstrapPanelComponent.class);
        }
    }

    @Nested
    @DisplayName("Tests for spinner icon rendering")
    class SpinnerIconTests {

        @Test
        @DisplayName("Should render spinner icon when deferred and collapsed")
        void shouldRenderSpinnerIconWhenDeferredAndCollapsed() {
            // Arrange
            var component = new BootstrapPanelComponent();
            component.setDeferred(true);
            component.setCollapsed(true);

            // Act & Assert
            assertTrue(component.shouldRenderSpinnerIcon(), "Should render spinner icon when deferred and collapsed");
        }

        @Test
        @DisplayName("Should not render spinner icon when children are loaded")
        void shouldNotRenderSpinnerIconWhenChildrenLoaded() {
            // Arrange
            var component = new BootstrapPanelComponent();
            component.setDeferred(true);
            component.setCollapsed(true);
            component.setChildrenLoaded(true);

            // Act & Assert
            assertFalse(component.shouldRenderSpinnerIcon(), "Should not render spinner icon when children are loaded");
        }
    }
}
