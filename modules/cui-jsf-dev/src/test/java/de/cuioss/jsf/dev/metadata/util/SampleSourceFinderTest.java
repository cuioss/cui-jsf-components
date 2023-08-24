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
package de.cuioss.jsf.dev.metadata.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

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
