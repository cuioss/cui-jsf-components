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
package de.cuioss.jsf.api.components.support;

import static de.cuioss.tools.collect.CollectionLiterals.immutableList;
import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.jsf.api.CoreJsfTestConfiguration;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import org.junit.jupiter.api.Test;

import java.util.List;

@JsfTestConfiguration(CoreJsfTestConfiguration.class)
class ActiveIndexManagerImplTest {

    @Test
    void resetToDefault() {
        ActiveIndexManager underTest = new ActiveIndexManagerImpl(immutableList(1, 2));
        underTest.setActiveIndex(3, 4);
        assertTrue(underTest.getActiveIndexes().containsAll(immutableList(3, 4)));
        underTest.resetToDefaultIndex();
        assertTrue(underTest.getActiveIndexes().containsAll(immutableList(1, 2)));
        assertEquals("1 2", underTest.getActiveIndexesString());
        underTest.setDefaultIndex(immutableList(5));
        underTest.resetToDefaultIndex();
        assertEquals(immutableList(5), underTest.getActiveIndexes());
    }

    @Test
    void setActiveIndex() {
        ActiveIndexManager underTest = new ActiveIndexManagerImpl(immutableList(5));
        assertTrue(underTest.hasActiveIndex());
        underTest.setActiveIndex();
        assertFalse(underTest.hasActiveIndex());
        underTest.setActiveIndex((List<Integer>) null);
        assertFalse(underTest.hasActiveIndex());
        underTest.setActiveIndex(3, 4);
        assertTrue(underTest.getActiveIndexes().containsAll(immutableList(3, 4)));
        underTest.setActiveIndex((Integer) null);
        assertNotNull(underTest.getActiveIndexes());
        assertFalse(underTest.hasActiveIndex());
    }

    @Test
    void toggleSingleIndex() {
        ActiveIndexManager underTest = new ActiveIndexManagerImpl(immutableList(5));
        assertEquals("5", underTest.getActiveIndexesString());
        underTest.toggleSingleIndex();
        assertEquals("", underTest.getActiveIndexesString());
        underTest.setActiveIndex();
        underTest.toggleSingleIndex();
        underTest.toggleSingleIndex();
        assertEquals("", underTest.getActiveIndexesString());
    }
}
