package com.icw.ehf.cui.core.api.components.partial;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.icw.ehf.cui.core.api.components.css.AlignHolder;

import de.cuioss.test.jsf.config.component.VerifyComponentProperties;

@VerifyComponentProperties(of = "align")
class AlignProviderImplTest extends AbstractPartialComponentTest {

    @SuppressWarnings("unused")
    @Test
    void shouldFailWithNullConstructor() {
        assertThrows(NullPointerException.class, () -> {
            new AlignProvider(null);
        });
    }

    @Test
    void shouldResolveDefaultForNoAlignSet() {
        assertEquals(AlignHolder.DEFAULT, anyComponent().resolveAlign());
    }

    @Test
    void shouldResolveAlign() {
        var any = anyComponent();
        any.setAlign(AlignHolder.LEFT.name());
        assertEquals(AlignHolder.LEFT, any.resolveAlign());
    }
}
