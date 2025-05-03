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

import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import org.junit.jupiter.api.Test;

@VerifyComponentProperties(of = "icon")
class IconProviderImplTest extends AbstractPartialComponentTest {

    @Test
    void shouldFailWithNullConstructor() {
        assertThrows(NullPointerException.class, () -> new IconProvider(null));
    }

    @Test
    void shouldFallbackOnNoIconSet() {
        assertEquals(IconProvider.FALLBACK_ICON_STRING, anyComponent().resolveIconCss());
    }

    @Test
    void shouldFallbackOnInvalidIconSet() {
        var any = anyComponent();
        any.setIcon(MESSAGE_KEY);
        assertEquals(IconProvider.FALLBACK_ICON_STRING, any.resolveIconCss());
    }

    @Test
    void shouldResolveIcon() {
        var any = anyComponent();
        any.setIcon("cui-icon-alarm");
        assertEquals("cui-icon cui-icon-alarm", any.resolveIconCss());
    }

    @Test
    void shouldDetectWhetherIconIsSet() {
        var any = anyComponent();
        assertFalse(any.isIconSet());
        any.setIcon("cui-icon-alarm");
        assertTrue(any.isIconSet());
    }
}
