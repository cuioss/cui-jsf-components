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
import static org.junit.jupiter.api.Assertions.assertThrows;

import de.cuioss.jsf.api.components.css.AlignHolder;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import org.junit.jupiter.api.Test;

@VerifyComponentProperties(of = "align")
class AlignProviderImplTest extends AbstractPartialComponentTest {

    @Test
    void shouldFailWithNullConstructor() {
        assertThrows(NullPointerException.class, () -> new AlignProvider(null));
    }

    @Test
    void shouldResolveDefaultForNoAlignSet() {
        assertEquals(AlignHolder.DEFAULT, anyComponent().resolveAlign());
    }

    @Test
    void shouldResolveAlign() {
        var any = anyComponent();
        any.setAlign(AlignHolder.LEFT.name());
        assertEquals(AlignHolder.LEFT, any.resolveAlign());
    }
}
