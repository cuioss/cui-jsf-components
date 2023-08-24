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
package de.cuioss.jsf.api.components.css;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class AlignHolderTest {

    @Test
    void shouldDefaultToUndefined() {
        assertEquals(AlignHolder.DEFAULT, AlignHolder.getFromString(null));
        assertEquals(AlignHolder.DEFAULT, AlignHolder.getFromString(""));
        assertEquals(AlignHolder.DEFAULT, AlignHolder.getFromString("notThere"));
        assertNotNull(AlignHolder.DEFAULT.getStyleClassBuilder());
        assertNotNull(AlignHolder.DEFAULT.getStyleClass());
    }

    @Test
    void shouldDetermineRight() {
        assertEquals(AlignHolder.RIGHT, AlignHolder.getFromString("right"));
        assertEquals(AlignHolder.RIGHT, AlignHolder.getFromString("RIGHT"));
        assertEquals(AlignHolder.RIGHT, AlignHolder.getFromString("RigHt"));
    }

    @Test
    void shouldDetermineLeft() {
        assertEquals(AlignHolder.LEFT, AlignHolder.getFromString("left"));
        assertEquals(AlignHolder.LEFT, AlignHolder.getFromString("LEFT"));
        assertEquals(AlignHolder.LEFT, AlignHolder.getFromString("lEFt"));
    }
}
