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
package de.cuioss.jsf.bootstrap.taglist;

import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.support.ConceptKeyTypeGenerator;
import de.cuioss.jsf.bootstrap.support.ConceptKeyTypeSetGenerator;
import de.cuioss.jsf.bootstrap.tag.support.TagHelper;
import de.cuioss.test.generator.Generators;
import de.cuioss.test.jsf.component.AbstractComponentTest;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import org.junit.jupiter.api.Test;

import java.util.*;

@VerifyComponentProperties(of = {"state", "size", "contentEscape", "style", "styleClass"})
class TagListComponentTest extends AbstractComponentTest<TagListComponent> {

    private final ConceptKeyTypeSetGenerator conceptKeyTypeSetGenerator = new ConceptKeyTypeSetGenerator();

    private final ConceptKeyTypeGenerator conceptKeyTypeGenerator = new ConceptKeyTypeGenerator();

    @Test
    void shouldRenderDependingOnValue() {
        var underTest = anyComponent();
        assertFalse(underTest.isRendered());
        underTest.setValue(new ArrayList<>());
        assertFalse(underTest.isRendered());
        underTest.setValue(conceptKeyTypeGenerator.next());
        assertTrue(underTest.isRendered());
        underTest.setValue(conceptKeyTypeSetGenerator.nextNotEmpty());
        assertTrue(underTest.isRendered());
        underTest.setRendered(false);
        assertFalse(underTest.isRendered());
    }

    @Test
    void shouldReturnCollectionOnValue() {
        var underTest = anyComponent();
        assertTrue(TagHelper.getValueAsSet(underTest.getValue()).isEmpty());
        underTest.setValue(new ArrayList<>());
        assertTrue(TagHelper.getValueAsSet(underTest.getValue()).isEmpty());
        underTest.setValue(conceptKeyTypeGenerator.next());
        assertFalse(TagHelper.getValueAsSet(underTest.getValue()).isEmpty());
        underTest.setValue(conceptKeyTypeSetGenerator.nextNotEmpty());
        assertFalse(TagHelper.getValueAsSet(underTest.getValue()).isEmpty());
    }

    @Test
    void shouldHandleStringAsValue() {
        var underTest = anyComponent();
        assertTrue(TagHelper.getValueAsSet(underTest.getValue()).isEmpty());
        var name = Generators.nonEmptyStrings().next();
        underTest.setValue(name);
        var values = TagHelper.getValueAsSet(underTest.getValue());
        assertNotNull(values);
        assertFalse(values.isEmpty());
        assertEquals(name, values.iterator().next().getIdentifier());
        assertEquals(name, values.iterator().next().getResolved(Locale.GERMAN));
    }

    @Test
    void shouldHandleStringListAsValue() {
        var underTest = anyComponent();
        Set<String> names = new HashSet<>();
        int count = Generators.integers(0, 10).next();
        for (var i = 0; i < count; i++) {
            names.add(Generators.nonEmptyStrings().next());
        }
        underTest.setValue(names);
        var values = TagHelper.getValueAsSet(underTest.getValue());
        assertNotNull(values);
        assertEquals(count, values.size());
    }

    @Test
    void shouldFailWithLocaleValue() {
        var underTest = anyComponent();
        underTest.setValue(Locale.CANADA);
        assertThrows(IllegalArgumentException.class, () -> TagHelper.getValueAsSet(underTest.getValue()));
    }

    @Test
    void shouldFailWithLocaleCollection() {
        var underTest = anyComponent();
        List<Locale> locales = new ArrayList<>();
        locales.add(Locale.CANADA);
        locales.add(Locale.ENGLISH);
        underTest.setValue(locales);
        assertThrows(IllegalArgumentException.class, () -> TagHelper.getValueAsSet(underTest.getValue()));
    }

    @Test
    void shouldDetectNonStringValues() {
        var type = TagHelper.resolveFrom(Locale.CANADA);
        assertFalse(type.isPresent());
    }

    @Test
    void shouldDefaultToContentEscape() {
        assertTrue(anyComponent().getContentEscape());
    }

    @Test
    void shouldProvideCorrectMetadata() {
        assertEquals(BootstrapFamily.TAG_LIST_COMPONENT_RENDERER, anyComponent().getRendererType());
        assertEquals(BootstrapFamily.COMPONENT_FAMILY, anyComponent().getFamily());
    }
}
