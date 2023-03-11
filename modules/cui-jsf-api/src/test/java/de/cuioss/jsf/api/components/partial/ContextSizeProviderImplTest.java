package de.cuioss.jsf.api.components.partial;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.components.css.ContextSize;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;

@VerifyComponentProperties(of = "size")
class ContextSizeProviderImplTest extends AbstractPartialComponentTest {

    @SuppressWarnings("unused")
    @Test
    void shouldFailWithNullConstructor() {
        assertThrows(NullPointerException.class, () -> new ContextSizeProvider(null));
    }

    @Test
    void shouldResolveDefaultForNoSizeSet() {
        assertEquals(ContextSize.DEFAULT, anyComponent().resolveContextSize());
    }

    @Test
    void shouldResolveSize() {
        var any = anyComponent();
        any.setSize(ContextSize.XL.name());
        assertEquals(ContextSize.XL, any.resolveContextSize());
    }
}
