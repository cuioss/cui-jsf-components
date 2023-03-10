package de.cuioss.jsf.api.components.partial;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.components.css.AlignHolder;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;

@VerifyComponentProperties(of = "iconAlign")
class IconAlignProviderImplTest extends AbstractPartialComponentTest {

    @SuppressWarnings("unused")
    @Test
    void shouldFailWithNullConstructor() {
        assertThrows(NullPointerException.class, () -> {
            new IconAlignProvider(null);
        });
    }

    @Test
    void shouldResolveDefaultForNoAlignSet() {
        assertEquals(AlignHolder.DEFAULT, anyComponent().resolveIconAlign());
    }

    @Test
    void shouldResolveAlign() {
        var any = anyComponent();
        any.setIconAlign(AlignHolder.LEFT.name());
        assertEquals(AlignHolder.LEFT, any.resolveIconAlign());
    }
}
