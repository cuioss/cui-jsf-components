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
package de.cuioss.jsf.dev.metadata;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class LibraryTagLibTest {

    @Test
    void shouldHandleFacesHtml() {
        var loaded = LibraryTagLib.FACES_HTML.load();
        assertNotNull(loaded);
        var editableDataList = loaded.getComponentMetadata().getByName("inputHidden");
        assertTrue(editableDataList.isPresent());
    }


    @Test
    void shouldHandleFacesCore() {
        var loaded = LibraryTagLib.FACES_CORE.load();
        assertNotNull(loaded);
        var tag = loaded.getConverterMetadata().getByName("convertDateTime");
        assertTrue(tag.isPresent());
    }

    @Test
    void shouldHandleCuiBootstrap() {
        var loaded = LibraryTagLib.CUI_BOOTSTRAP.load();
        assertNotNull(loaded);
        var tag = loaded.getComponentMetadata().getByName("editableDataList");
        assertTrue(tag.isPresent());
    }

    @Test
    void shouldHandleCuiCore() {
        var loaded = LibraryTagLib.CUI_CORE.load();
        assertNotNull(loaded);
        var tag = loaded.getComponentMetadata().getByName("fieldset");
        assertTrue(tag.isPresent());
    }

    @Test
    void shouldHandleOmnifaces() {
        var loaded = LibraryTagLib.OMNI_FACES.load();
        assertNotNull(loaded);
        var tag = loaded.getComponentMetadata().getByName("tree");
        assertTrue(tag.isPresent());
    }

    @Test
    void shouldHandlePrimefaces() {
        var loaded = LibraryTagLib.PRIME_FACES.load();
        assertNotNull(loaded);
        var tag = loaded.getComponentMetadata().getByName("dataTable");
        assertTrue(tag.isPresent());
    }
}