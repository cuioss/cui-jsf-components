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
package de.cuioss.jsf.api.components;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.faces.component.behavior.AjaxBehavior;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.CoreJsfTestConfiguration;
import de.cuioss.test.jsf.config.ComponentConfigurator;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import de.cuioss.test.jsf.junit5.JsfEnabledTestEnvironment;

@JsfTestConfiguration(CoreJsfTestConfiguration.class)
class JsfBehaviorComponentTest extends JsfEnabledTestEnvironment implements ComponentConfigurator {

    @Test
    void shouldProvideAjaxBehavior() {
        assertNotNull(JsfBehaviorComponent.ajaxBehavior(getFacesContext()));
    }

    @Override
    public void configureComponents(ComponentConfigDecorator decorator) {
        decorator.registerBehavior(AjaxBehavior.BEHAVIOR_ID, AjaxBehavior.class);
    }
}
