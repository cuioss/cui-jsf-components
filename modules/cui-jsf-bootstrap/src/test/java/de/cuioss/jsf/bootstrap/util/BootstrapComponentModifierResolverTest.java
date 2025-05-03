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
package de.cuioss.jsf.bootstrap.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import de.cuioss.jsf.api.components.util.modifier.ComponentModifierFactory;
import de.cuioss.jsf.bootstrap.composite.EditableDataListComponent;
import de.cuioss.test.jsf.junit5.EnableJsfEnvironment;
import jakarta.faces.component.html.HtmlInputText;
import org.junit.jupiter.api.Test;

@EnableJsfEnvironment
class BootstrapComponentModifierResolverTest {

    @Test
    void shouldHandleEditableDataList() {
        var wrapper = ComponentModifierFactory.findFittingWrapper(new EditableDataListComponent());
        assertInstanceOf(EditableDataListComponentWrapper.class, wrapper);
        ComponentModifierAssert.assertContracts(wrapper, wrapper.getComponent());
    }

    @Test
    void shouldIgnoreInvalidComponent() {
        assertFalse(new BootstrapComponentModifierResolver().wrap(new HtmlInputText()).isPresent());
    }

}
