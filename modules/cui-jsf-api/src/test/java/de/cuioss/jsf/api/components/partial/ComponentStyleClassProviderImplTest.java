package de.cuioss.jsf.api.components.partial;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import de.cuioss.test.jsf.config.component.VerifyComponentProperties;

@VerifyComponentProperties(of = "styleClass")
class ComponentStyleClassProviderImplTest extends AbstractPartialComponentTest {

    @SuppressWarnings("unused")
    @Test
    void shouldFailWithNullConstructor() {
        assertThrows(NullPointerException.class, () -> {
            new ComponentStyleClassProviderImpl(null);
        });
    }

    @Test
    void shouldResolveBuilderForNoStyleClassSet() {
        assertNotNull(anyComponent().getStyleClassBuilder());
        assertFalse(anyComponent().getStyleClassBuilder().isAvailable());
    }

    @Test
    void shouldResolveBuilderStyleClass() {
        final var styleClass = "styleClassThingy";
        var any = anyComponent();
        any.setStyleClass(styleClass);
        assertNotNull(any.getStyleClassBuilder());
        assertTrue(any.getStyleClassBuilder().isAvailable());
        assertEquals(styleClass, any.getStyleClassBuilder().getStyleClass());
    }
}
