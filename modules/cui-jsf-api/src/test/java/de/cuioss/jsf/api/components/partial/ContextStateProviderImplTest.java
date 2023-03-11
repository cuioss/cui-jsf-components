package de.cuioss.jsf.api.components.partial;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.components.css.ContextState;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;

@VerifyComponentProperties(of = "state")
class ContextStateProviderImplTest extends AbstractPartialComponentTest {

    @SuppressWarnings("unused")
    @Test
    void shouldFailWithNullConstructor() {
        assertThrows(NullPointerException.class, () -> new ContextStateProvider(null));
    }

    @Test
    void shouldResolveDefaultForNoStateSet() {
        assertEquals(ContextState.DEFAULT, anyComponent().resolveContextState());
    }

    @Test
    void shouldResolveState() {
        var any = anyComponent();
        any.setState(ContextState.INFO.name());
        assertEquals(ContextState.INFO, any.resolveContextState());
    }
}
