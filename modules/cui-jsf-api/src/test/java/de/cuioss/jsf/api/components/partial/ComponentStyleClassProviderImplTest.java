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
package de.cuioss.jsf.api.components.partial;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import de.cuioss.test.jsf.config.component.VerifyComponentProperties;

@VerifyComponentProperties(of = "styleClass")
class ComponentStyleClassProviderImplTest extends AbstractPartialComponentTest {

    @Test
    void shouldFailWithNullConstructor() {
        assertThrows(NullPointerException.class, () -> new ComponentStyleClassProviderImpl(null));
    }

    @Test
    void shouldResolveBuilderForNoStyleClassSet() {
        assertNotNull(anyComponent().getStyleClassBuilder());
        assertFalse(anyComponent().getStyleClassBuilder().isAvailable());
    }

    @Test
    void shouldResolveBuilderStyleClass() {
        final var styleClass = "styleClassThingy";
        var any = anyComponent();
        any.setStyleClass(styleClass);
        assertNotNull(any.getStyleClassBuilder());
        assertTrue(any.getStyleClassBuilder().isAvailable());
        assertEquals(styleClass, any.getStyleClassBuilder().getStyleClass());
    }
}
