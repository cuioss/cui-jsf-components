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

@VerifyComponentProperties(of = { "headerKey", "headerValue", "headerConverter", "headerEscape", "headerTag" })
class HeaderProviderImplTest extends AbstractPartialComponentTest {

    @SuppressWarnings("unused")
    @Test
    void shouldFailWithNullConstructor() {
        assertThrows(NullPointerException.class, () -> {
            new HeaderProvider(null);
        });
    }

    @Test
    void shouldResolveNullForNoTitleSet() {
        assertNull(anyComponent().resolveHeader());
    }

    @Test
    void shouldResolveHeaderValue() {
        final var any = anyComponent();
        any.setHeaderValue(MESSAGE_VALUE);
        assertEquals(MESSAGE_VALUE, any.resolveHeader());
    }

    @Test
    void shouldResolveHeaderKey() {
        final var any = anyComponent();
        any.setHeaderKey(MESSAGE_KEY);
        assertEquals(MESSAGE_KEY, any.resolveHeader());
    }

    @Test
    void shouldUseConverterAsId() {
        getComponentConfigDecorator().registerConverter(ReverseConverter.class);
        final var any = anyComponent();
        any.setHeaderConverter(ReverseConverter.CONVERTER_ID);
        any.setHeaderValue("test");
        assertEquals("tset", any.resolveHeader());
    }

    @Test
    void shouldUseConverterAsConverter() {
        final var any = anyComponent();
        any.setHeaderConverter(new ReverseConverter());
        any.setHeaderValue("test");
        assertEquals("tset", any.resolveHeader());
    }

    @Test
    void shouldDetectTitleAvailabilty() {
        final var any = anyComponent();
        assertFalse(any.hasHeaderTitleSet());
        final var strings = letterStrings(1, 10);
        any.setHeaderKey(strings.next());
        assertTrue(any.hasHeaderTitleSet());
        any.setHeaderValue(strings.next());
        assertTrue(any.hasHeaderTitleSet());
        any.setHeaderKey(null);
        assertTrue(any.hasHeaderTitleSet());
    }

    @Test
    void shouldUseHeaderTag() {
        final var any = anyComponent();
        assertEquals(HeaderProvider.HEADERTAG_DEFAULT, any.getHeaderTag());
        any.setHeaderTag("h2");
        assertEquals("h2", any.getHeaderTag());
    }

    @Test
    void shouldRenderHeaderFacet() {
        final var any = anyComponent();
        assertFalse(any.shouldRenderHeaderFacet());
        any.getFacets().put("header", anyComponent());
        assertTrue(any.shouldRenderHeaderFacet());
    }

    @Test
    void shouldRenderHeader() {
        final var any = anyComponent();
        // false, because no facet or key/value is present
        assertFalse(any.shouldRenderHeader());
        any.getFacets().put("header", anyComponent());
        assertTrue(any.shouldRenderHeader());
        any.getFacet("header").setRendered(false);
        // false because facet is not rendered
        assertFalse(any.shouldRenderHeader());
        any.getFacets().clear();
        any.setHeaderKey("Foo");
        assertTrue(any.shouldRenderHeader());
    }
}
