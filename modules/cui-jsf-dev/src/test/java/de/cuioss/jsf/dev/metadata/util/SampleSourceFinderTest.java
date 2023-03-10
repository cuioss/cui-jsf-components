package de.cuioss.jsf.dev.metadata.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.net.URL;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.dev.metadata.composite.util.SampleSourceFinder;

class SampleSourceFinderTest {

    @Test
    void testGetSampleSource() {
        var resource = this.getClass().getResource("/header.xhtml");
        var finder = new SampleSourceFinder(new File(resource.getFile()), "smpH1");
        var sampleSource = finder.getSampleSource();
        assertTrue(sampleSource.contains("markup:h1"));
        assertFalse(sampleSource.contains("<?xml"));
        resource = this.getClass().getResource("/printMetadata.xhtml");
        finder = new SampleSourceFinder(new File(resource.getFile()), "prntMtdRef");
        sampleSource = finder.getSampleSource();
        assertTrue(sampleSource.contains("f:facet"));
        resource = this.getClass().getResource("/panel.xhtml");
        finder = new SampleSourceFinder(new File(resource.getFile()), "panelRef");
        sampleSource = finder.getSampleSource();
        assertTrue(sampleSource.contains("f:facet name=\"content\""));
    }
}
