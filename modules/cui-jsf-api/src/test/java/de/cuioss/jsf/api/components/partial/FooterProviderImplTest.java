package de.cuioss.jsf.api.components.partial;

import static de.cuioss.test.generator.Generators.letterStrings;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import de.cuioss.test.jsf.mocks.ReverseConverter;

@VerifyComponentProperties(of = { "footerKey", "footerValue", "footerConverter", "footerEscape" })
class FooterProviderImplTest extends AbstractPartialComponentTest {

    @SuppressWarnings("unused")
    @Test
    void shouldFailWithNullConstructor() {
        assertThrows(NullPointerException.class, () -> {
            new FooterProvider(null);
        });
    }

    @Test
    void shouldResolveNullForNoTitleSet() {
        assertNull(anyComponent().resolveFooter());
    }

    @Test
    void shouldresolveFooterValue() {
        final var any = anyComponent();
        any.setFooterValue(MESSAGE_VALUE);
        assertEquals(MESSAGE_VALUE, any.resolveFooter());
    }

    @Test
    void shouldresolveFooterKey() {
        final var any = anyComponent();
        any.setFooterKey(MESSAGE_KEY);
        assertEquals(MESSAGE_KEY, any.resolveFooter());
    }

    @Test
    void shouldUseConverterAsId() {
        getComponentConfigDecorator().registerConverter(ReverseConverter.class);
        final var any = anyComponent();
        any.setFooterConverter(ReverseConverter.CONVERTER_ID);
        any.setFooterValue("test");
        assertEquals("tset", any.resolveFooter());
    }

    @Test
    void shouldUseConverterAsConverter() {
        final var any = anyComponent();
        any.setFooterConverter(new ReverseConverter());
        any.setFooterValue("test");
        assertEquals("tset", any.resolveFooter());
    }

    @Test
    void shouldDetectTitleAvailabilty() {
        final var any = anyComponent();
        assertFalse(any.hasFooterTitleSet());
        final var strings = letterStrings(1, 10);
        any.setFooterKey(strings.next());
        assertTrue(any.hasFooterTitleSet());
        any.setFooterValue(strings.next());
        assertTrue(any.hasFooterTitleSet());
        any.setFooterKey(null);
        assertTrue(any.hasFooterTitleSet());
    }

    @Test
    void shouldUseFacet() {
        final var any = anyComponent();
        assertFalse(any.shouldRenderFooterFacet());
        any.getFacets().put("footer", anyComponent());
        assertTrue(any.shouldRenderFooterFacet());
    }

    @Test
    void shouldRenderFootereader() {
        final var any = anyComponent();
        // false, because no facet or key/value is present
        assertFalse(any.shouldRenderFooter());
        any.getFacets().put("footer", anyComponent());
        assertTrue(any.shouldRenderFooter());
        any.getFacet("footer").setRendered(false);
        // false because facet is not rendered
        assertFalse(any.shouldRenderFooter());
        any.getFacets().clear();
        any.setFooterKey("Foo");
        assertTrue(any.shouldRenderFooter());
    }
}
