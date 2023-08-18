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
package de.cuioss.jsf.bootstrap.icon;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.icon.support.CssMimeTypeIcon;
import de.cuioss.jsf.test.CoreJsfTestConfiguration;
import de.cuioss.test.jsf.component.AbstractUiComponentTest;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;

@VerifyComponentProperties(of = { "decoratorClass", "mimeTypeIcon", "mimeTypeString", "size", "titleKey",
        "titleValue" })
@JsfTestConfiguration(CoreJsfTestConfiguration.class)
class MimeTypeIconComponentTest extends AbstractUiComponentTest<MimeTypeIconComponent> {

    @Test
    void shouldDefaultToNoDecorator() {
        assertEquals(CssMimeTypeIcon.CUI_STACKED_ICON_NO_DECORATOR.getStyleClass(), anyComponent().getDecoratorClass());
    }

    @Test
    void shouldResolveToDefaultIcon() {
        assertEquals(MimeTypeIcon.UNDEFINED, anyComponent().resolveMimeTypeIcon());
    }

    @Test
    void shouldProvideModelOverStringRepresentation() {
        var component = anyComponent();
        component.setMimeTypeIcon(MimeTypeIcon.AUDIO_BASIC);
        component.setMimeTypeString(MimeTypeIcon.AUDIO_MPEG.name());
        assertEquals(MimeTypeIcon.AUDIO_BASIC, component.resolveMimeTypeIcon());
    }

    @Test
    void shouldProvideModelFromString() {
        var component = anyComponent();
        component.setMimeTypeString(MimeTypeIcon.AUDIO_MPEG.name());
        assertEquals(MimeTypeIcon.AUDIO_MPEG, component.resolveMimeTypeIcon());
    }

    @Test
    void shouldProvideCorrectMetadata() {
        assertEquals(BootstrapFamily.MIME_TYPE_ICON_COMPONENT_RENDERER, anyComponent().getRendererType());
    }
}
