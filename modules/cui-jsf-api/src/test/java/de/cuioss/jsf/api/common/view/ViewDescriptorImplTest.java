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
package de.cuioss.jsf.api.common.view;

import static de.cuioss.test.generator.Generators.strings;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.test.valueobjects.ValueObjectTest;
import de.cuioss.test.valueobjects.api.contracts.VerifyBuilder;
import de.cuioss.tools.net.ParameterFilter;
import org.junit.jupiter.api.Test;

import java.util.Collections;

@VerifyBuilder(methodPrefix = "with", exclude = {"viewDefined", "shortIdentifier"})
class ViewDescriptorImplTest extends ValueObjectTest<ViewDescriptorImpl> {

    private final TypedGenerator<String> stringGenerator = strings(1, 55);

    @Test
    void shouldBuildWithCopyConstructor() {
        final var built = ViewDescriptorImpl.builder().withLogicalViewId(stringGenerator.next())
                .withUrlParameter(Collections.emptyList()).withViewId(stringGenerator.next()).build();
        final var copy = new ViewDescriptorImpl(built, null);
        assertEquals(built, copy);
    }

    @Test
    void shouldBuildWithCopyConstructorAndFilter() {
        final var built = ViewDescriptorImpl.builder().withLogicalViewId(stringGenerator.next())
                .withUrlParameter(Collections.emptyList()).withViewId(stringGenerator.next()).build();
        final var copy = new ViewDescriptorImpl(built, new ParameterFilter(Collections.emptyList(), true));
        assertEquals(built, copy);
    }

    @Test
    void shouldBuildDetermineViewDefined() {
        final var built = ViewDescriptorImpl.builder().build();
        assertFalse(built.isViewDefined());
        final var copy = new ViewDescriptorImpl(built, null);
        assertFalse(copy.isViewDefined());
    }
}
