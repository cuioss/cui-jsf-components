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
package de.cuioss.jsf.api.components.util.modifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.faces.component.UIComponentBase;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.components.util.modifier.support.TitleProviderImpl;

class ComponentModifierFactoryTest {

    @Test
    void shouldHandleComponentBase() {
        UIComponentBase component = new UIComponentBase() {

            @Override
            public String getFamily() {
                return null;
            }
        };
        assertNotNull(ComponentModifierFactory.findFittingWrapper(component));
        assertEquals(ReflectionBasedModifier.class, ComponentModifierFactory.findFittingWrapper(component).getClass());
    }

    @Test
    void shouldHandleCuiInterface() {
        UIComponentBase component = new TitleProviderImpl();
        assertNotNull(ComponentModifierFactory.findFittingWrapper(component));
        assertEquals(CuiInterfaceBasedModifier.class,
                ComponentModifierFactory.findFittingWrapper(component).getClass());
    }
}
