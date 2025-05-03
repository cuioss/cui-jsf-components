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

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.CssBootstrap;
import de.cuioss.test.jsf.component.AbstractUiComponentTest;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import org.junit.jupiter.api.Test;

@VerifyComponentProperties(of = {"align"})
class QuickControlGroupComponentTest extends AbstractUiComponentTest<QuickControlGroupComponent> {

    @Test
    void ensureDefaultAlignment() {
        var underTest = anyComponent();
        assertEquals(CssBootstrap.QUICK_CONTROL_GROUP_RIGHT.getStyleClass(),
                underTest.resolveStyleClass().getStyleClass());
    }

    @Test
    void ensureSetAlignment() {
        var underTest = anyComponent();
        underTest.setAlign("fallback-to-default");
        assertEquals(CssBootstrap.QUICK_CONTROL_GROUP_RIGHT.getStyleClass(),
                underTest.resolveStyleClass().getStyleClass());
        underTest.setAlign("left");
        assertEquals(CssBootstrap.QUICK_CONTROL_GROUP_LEFT.getStyleClass(),
                underTest.resolveStyleClass().getStyleClass());
        underTest.setAlign("right");
        assertEquals(CssBootstrap.QUICK_CONTROL_GROUP_RIGHT.getStyleClass(),
                underTest.resolveStyleClass().getStyleClass());
    }

    @Test
    void ensureStyleClassWithAlignment() {
        var underTest = anyComponent();
        underTest.setAlign("left");
        underTest.setStyleClass("foo");
        assertEquals(CssBootstrap.QUICK_CONTROL_GROUP_LEFT.getStyleClassBuilder().append("foo").getStyleClass(),
                underTest.resolveStyleClass().getStyleClass());
    }

    @Test
    void shouldProvideCorrectMetadata() {
        assertEquals(BootstrapFamily.LAYOUT_RENDERER, anyComponent().getRendererType());
    }
}
