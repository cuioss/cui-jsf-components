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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.bootstrap.CssCuiBootstrap;
import de.cuioss.test.jsf.component.AbstractComponentTest;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;

@VerifyComponentProperties(of = { "rendered", "showDetail", "showSummary", "for" })
class CuiMessagesComponentTest extends AbstractComponentTest<CuiMessagesComponent> {

    private static final String STYLE_CLASS = CssCuiBootstrap.CUI_MESSAGES_CLASS.getStyleClass();

    @Test
    void shouldProvideDefaultClasses() {
        var component = anyComponent();
        assertEquals(STYLE_CLASS, component.getStyleClass());
        assertNotNull(component.getFatalClass());
        assertNotNull(component.getWarnClass());
        assertNotNull(component.getErrorClass());
        assertNotNull(component.getInfoClass());
    }

    @Test
    void shouldMofiyStyleClasses() {
        var component = anyComponent();
        component.setStyleClass("added");
        assertEquals(STYLE_CLASS + " added", component.getStyleClass());
        assertNotNull(component.getFatalClass());
        assertNotNull(component.getWarnClass());
        assertNotNull(component.getErrorClass());
        assertNotNull(component.getInfoClass());
    }
}
