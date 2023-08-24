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
package de.cuioss.jsf.dev.metadata.composite;

import static org.junit.jupiter.api.Assertions.assertNull;

import javax.faces.component.html.HtmlInputText;

import org.junit.jupiter.api.Test;

import de.cuioss.test.jsf.config.ComponentConfigurator;
import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import de.cuioss.test.jsf.junit5.JsfEnabledTestEnvironment;

class CompositeUtilTest extends JsfEnabledTestEnvironment implements ComponentConfigurator {

    @Test
    void shouldLoadCompositeInfo() {
        var info = CompositeUtil.loadMetadataInfo(getFacesContext(), "dev-composite", "namespace.xhtml");
        assertNull(info); // Expected, no path present
    }

    @Override
    public void configureComponents(ComponentConfigDecorator decorator) {
        decorator.registerCompositeComponent("dev-composite", "namespace.xhtml", new HtmlInputText());
    }

}
