/*
 * Copyright 2023 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.dev.ui.components;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.cuioss.test.generator.Generators;
import de.cuioss.test.jsf.component.AbstractComponentTest;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@VerifyComponentProperties(of = {"source", "sourcePath", "sourceContainerId", "enableClipboard", "type",
        "description"}, defaultValued = {"enableClipboard"})
class SourceCodeComponentTest extends AbstractComponentTest<SourceCodeComponent> {

    private static final String BASE = "/samples/";

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

    @ParameterizedTest
    @CsvSource({"test.properties,hello=world", "relativeResources.properties,hello=relative",
            "absoluteResources.properties,hello=absolute"})
    void shouldReadRelativeSourceFile(String path, String result) {
        getRequestConfigDecorator().setViewId(VIEW_ID);
        var component = new SourceCodeComponent();
        component.setSourcePath(path);
        var resolved = component.resolveSource();
        assertEquals(result, resolved);
    }

    @Test
    void shouldReadAbsoluteSourceFile() {
        getRequestConfigDecorator().setViewId(VIEW_ID);
        var component = new SourceCodeComponent();
        component.setSourcePath("/META-INF" + BASE + "absolut.properties");
        var resolved = component.resolveSource();
        assertEquals("hello=world2", resolved);
    }

    @Test
    void shouldFailToReadAbsoluteSourceFile() {
        getRequestConfigDecorator().setViewId(VIEW_ID);
        var component = new SourceCodeComponent();
        component.setSourcePath("/META-INF" + BASE + "notthere.properties");
        var resolved = component.resolveSource();
        assertTrue(resolved.startsWith("Unable lo load "), resolved);
    }

    @Test
    void shouldFailToLoadRelativeSourceFile() {
        getRequestConfigDecorator().setViewId(VIEW_ID);
        var component = new SourceCodeComponent();
        component.setSourcePath("not.there");
        var resolved = component.resolveSource();
        assertTrue(resolved.startsWith("Unable lo load "), resolved);
    }
}
