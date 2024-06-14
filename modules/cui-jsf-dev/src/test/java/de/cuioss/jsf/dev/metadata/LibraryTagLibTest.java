package de.cuioss.jsf.dev.metadata;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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