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
package de.cuioss.jsf.jqplot.axes;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import de.cuioss.test.valueobjects.contract.SerializableContractImpl;
import org.junit.jupiter.api.Test;

class AxesTest {

    private Axes target;

    @Test
    void shouldBeSerializable() {
        assertDoesNotThrow(() -> SerializableContractImpl.serializeAndDeserialize(createAny()));
    }

    @Test
    final void shouldVerifyDuplicates() {
        target = createEmpty();
        target.addInNotNull(Axis.createXAxis());
        var axis = Axis.createXAxis();
        assertThrows(IllegalArgumentException.class, () -> target.addInNotNull(axis));
    }

    private static Axes createEmpty() {
        return new Axes();
    }

    private static Axes createAny() {
        final var result = new Axes();
        result.addInNotNull(null);
        result.addInNotNull(Axis.createXAxis());
        result.addInNotNull(Axis.createYAxis());
        return result;
    }
}
