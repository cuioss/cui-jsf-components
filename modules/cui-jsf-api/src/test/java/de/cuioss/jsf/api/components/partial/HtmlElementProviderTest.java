package de.cuioss.jsf.api.components.partial;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;

@VerifyComponentProperties(of = HtmlElementProvider.KEY)
class HtmlElementProviderTest extends AbstractPartialComponentTest {

    @SuppressWarnings("unused")
    @Test
    void shouldFailWithNullConstructor() {
        assertThrows(NullPointerException.class, () -> {
            new HtmlElementProvider(null, null);
        });
    }

    @Test
    void shouldUseDefault() {
        assertEquals(Node.DIV, anyComponent().resolveHtmlElement());
    }

    @Test
    void shouldResolveComponent() {
        var underTest = anyComponent();
        underTest.setHtmlElement(Node.NAV.getContent());
        assertEquals(Node.NAV, underTest.resolveHtmlElement());
    }

    @Test
    void shouldFailOnInvalidElement() {
        var underTest = anyComponent();
        underTest.setHtmlElement("boom");
        assertThrows(IllegalArgumentException.class, () -> {
            underTest.resolveHtmlElement();
        });
    }
}
