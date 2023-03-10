package de.cuioss.jsf.dev.ui.components;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import de.cuioss.test.generator.Generators;
import de.cuioss.test.jsf.component.AbstractComponentTest;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;

@VerifyComponentProperties(
        of = { "source", "sourcePath", "sourceContainerId", "enableClipboard", "type", "description" },
        defaultValued = { "enableClipboard" })
class SourceCodeComponentTest extends AbstractComponentTest<SourceCodeComponent> {

    private static final String BASE = "/faces/samples/";

    private static final String VIEW_ID = BASE + "source.xhtml";

    private static final String WRAPPER_ID = "panelRef";

    @Test
    void shouldReturnSource() {
        var component = new SourceCodeComponent();
        var source = Generators.strings(5, 100).next();
        component.setSource(source);
        assertEquals(source, component.resolveSource());
    }

    @Test
    void shouldResolveSourceId() {
        getRequestConfigDecorator().setViewId(VIEW_ID);
        var component = new SourceCodeComponent();
        component.setSourceContainerId(WRAPPER_ID);
        var resolved = component.resolveSource();
        assertTrue(resolved.contains("Extract Sample Source"));
    }

    @Test
    void shouldReadRelativeSourceFile() {
        getRequestConfigDecorator().setViewId(VIEW_ID);
        var component = new SourceCodeComponent();
        component.setSourcePath("test.properties");
        var resolved = component.resolveSource();
        assertTrue(resolved.contains("hello=world"));
    }

    @Test
    void shouldReadAbsoluteSourceFile() {
        getRequestConfigDecorator().setViewId(VIEW_ID);
        var component = new SourceCodeComponent();
        component.setSourcePath("/META-INF" + BASE + "absolut.properties");
        var resolved = component.resolveSource();
        assertTrue(resolved.contains("hello=world2"));
    }

    @Test
    void shouldFailToReadAbsoluteSourceFile() {
        getRequestConfigDecorator().setViewId(VIEW_ID);
        var component = new SourceCodeComponent();
        component.setSourcePath("/META-INF" + BASE + "notthere.properties");
        var resolved = component.resolveSource();
        assertTrue(resolved.startsWith("Unable lo load "));
    }

    @Test
    void shouldFailToLoadRelativeSourceFile() {
        getRequestConfigDecorator().setViewId(VIEW_ID);
        var component = new SourceCodeComponent();
        component.setSourcePath("not.there");
        var resolved = component.resolveSource();
        assertTrue(resolved.startsWith("Unable lo load "));
    }
}
