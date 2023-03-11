package de.cuioss.jsf.api.components.partial;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import de.cuioss.test.jsf.config.component.VerifyComponentProperties;

@VerifyComponentProperties(of = "disabled")
class DisabledComponentProviderImplTest extends AbstractPartialComponentTest {

    @SuppressWarnings("unused")
    @Test
    void shouldFailWithNullConstructor() {
        assertThrows(NullPointerException.class, () -> new DisabledComponentProvider(null));
    }

    @Test
    void shouldRetrieveDefaultForDisabled() {
        assertEquals(Boolean.FALSE, anyComponent().isDisabled());
    }
}
