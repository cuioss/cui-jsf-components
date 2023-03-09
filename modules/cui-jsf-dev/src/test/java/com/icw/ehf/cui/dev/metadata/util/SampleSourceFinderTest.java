package com.icw.ehf.cui.dev.metadata.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.net.URL;

import com.icw.ehf.cui.dev.metadata.composite.util.SampleSourceFinder;
import org.junit.jupiter.api.Test;

class SampleSourceFinderTest {

    @Test
    void testGetSampleSource() {
        URL resource = this.getClass().getResource("/header.xhtml");
        SampleSourceFinder finder = new SampleSourceFinder(new File(resource.getFile()), "smpH1");
        String sampleSource = finder.getSampleSource();
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
