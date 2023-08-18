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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.application.projectstage.CuiProjectStageAccessor;
import de.cuioss.jsf.api.converter.FallbackSanitizingConverter;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import de.cuioss.uimodel.application.CuiProjectStage;

@VerifyComponentProperties(of = { "contentKey", "contentValue", "contentEscape", "contentConverter" })
class ContentProviderImplTest extends AbstractPartialComponentTest {

    @SuppressWarnings("unused")
    @Test
    void shouldFailWithNullConstructor() {
        assertThrows(NullPointerException.class, () -> new ContentProvider(null));
    }

    @Test
    void shouldResolveNullForNoContentSet() {
        assertNull(anyComponent().resolveContent());
    }

    @Test
    void shouldResolveContentValue() {
        var any = anyComponent();
        any.setContentValue(MESSAGE_VALUE);
        assertEquals(MESSAGE_VALUE, any.resolveContent());
    }

    @Test
    void shouldResolveContentKey() {
        var any = anyComponent();
        any.setContentKey(MESSAGE_KEY);
        assertEquals(MESSAGE_KEY, any.resolveContent());
    }

    @Test
    void shouldSanitizeWithFallback() {
        getBeanConfigDecorator().register(new CuiProjectStage() {

            private static final long serialVersionUID = 5774194992001533462L;

            @Override
            public boolean isDevelopment() {
                return true;
            }

            @Override
            public boolean isTest() {
                return false;
            }

            @Override
            public boolean isConfiguration() {
                return false;
            }

            @Override
            public boolean isProduction() {
                return false;
            }
        }, CuiProjectStageAccessor.BEAN_NAME);
        var any = anyComponent();
        any.setContentValue("<script>");
        any.setContentEscape(false);
        any.setContentConverter(new FallbackSanitizingConverter());
        assertEquals("", any.resolveContent());
    }

    @Test
    void shouldNotSanitizeWithFallbackWhenEscaped() {
        var any = anyComponent();
        any.setContentValue("<script>");
        any.setContentEscape(true);
        any.setContentConverter(new FallbackSanitizingConverter());
        assertEquals("<script>", any.resolveContent());
    }
}
