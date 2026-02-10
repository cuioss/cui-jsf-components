/*
 * Copyright © 2025 CUI-OpenSource-Software (info@cuioss.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
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

import de.cuioss.jsf.api.common.logging.JsfApiLogMessages;
import de.cuioss.jsf.api.components.support.ActiveIndexManager;
import de.cuioss.jsf.api.components.support.ActiveIndexManagerImpl;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import de.cuioss.test.juli.LogAsserts;
import de.cuioss.test.juli.TestLogLevel;
import de.cuioss.test.juli.junit5.EnableTestLogger;
import org.jboss.weld.junit5.ExplicitParamInjection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

@VerifyComponentProperties
@EnableTestLogger
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

    @Test
    @DisplayName("Should log warning when ActiveIndexManager throws exception")
    void shouldLogWarningOnActiveIndexManagerError() {
        // Arrange
        var any = anyComponent();
        any.setActiveIndexManager(new ActiveIndexManager() {
            @Override
            public List<Integer> getActiveIndexes() {
                throw new IllegalStateException("test error");
            }

            @Override
            public String getActiveIndexesString() {
                return null;
            }

            @Override
            public void setActiveIndexesString(String s) {
            }

            @Override
            public void resetToDefaultIndex() {
            }

            @Override
            public void setActiveIndex(Integer... i) {
            }

            @Override
            public void setActiveIndex(List<Integer> i) {
            }

            @Override
            public void setDefaultIndex(List<Integer> i) {
            }

            @Override
            public void toggleSingleIndex() {
            }

            @Override
            public boolean hasActiveIndex() {
                return false;
            }
        });

        // Act
        var result = any.resolveActiveIndexes();

        // Assert
        assertEquals(immutableList(0), result, "Should return fallback index list");
        LogAsserts.assertSingleLogMessagePresentContaining(TestLogLevel.WARN,
                JsfApiLogMessages.WARN.ACTIVE_INDEX_MANAGER_ERROR.resolveIdentifierString());
    }
}
