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

import static de.cuioss.tools.collect.CollectionLiterals.immutableList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import de.cuioss.jsf.api.components.support.ActiveIndexManagerImpl;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import org.jboss.weld.junit5.ExplicitParamInjection;
import org.junit.jupiter.api.Test;

import java.util.List;

@VerifyComponentProperties
@ExplicitParamInjection
class ActiveIndexManagerProviderImplTest extends AbstractPartialComponentTest {

    @Test
    void shouldFailWithNullConstructor() {
        assertThrows(NullPointerException.class, () -> new ActiveIndexManagerProvider(null));
    }

    @Test
    void shouldResolveIndexes() {
        List<Integer> indexes = immutableList(1, 2, 3, 4);
        var any = anyComponent();
        any.setActiveIndexManager(new ActiveIndexManagerImpl(indexes));
        assertEquals(indexes, any.resolveActiveIndexes());
        any.getActiveIndexManager().setActiveIndex(9);
        assertEquals(immutableList(9), any.resolveActiveIndexes());
        any.getActiveIndexManager().setActiveIndex(immutableList(5, 6));
        assertEquals(immutableList(5, 6), any.resolveActiveIndexes());
        any.getActiveIndexManager().setActiveIndexesString("10 11");
        assertEquals(immutableList(10, 11), any.resolveActiveIndexes());
        any.getActiveIndexManager().resetToDefaultIndex();
        assertEquals(indexes, any.resolveActiveIndexes());
    }
}
