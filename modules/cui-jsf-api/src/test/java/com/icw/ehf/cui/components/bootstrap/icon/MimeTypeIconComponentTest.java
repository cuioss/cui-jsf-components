package com.icw.ehf.cui.components.bootstrap.icon;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.icw.ehf.cui.components.bootstrap.BootstrapFamily;
import com.icw.ehf.cui.components.bootstrap.icon.support.CssMimeTypeIcon;
import com.icw.ehf.cui.core.api.CoreJsfTestConfiguration;

import de.cuioss.test.jsf.component.AbstractUiComponentTest;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;

@VerifyComponentProperties(
        of = { "decoratorClass", "mimeTypeIcon", "mimeTypeString", "size", "titleKey", "titleValue" })
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
