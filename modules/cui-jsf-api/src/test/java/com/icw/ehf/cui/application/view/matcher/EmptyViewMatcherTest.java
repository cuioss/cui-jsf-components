package com.icw.ehf.cui.application.view.matcher;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;

import org.junit.jupiter.api.Test;

import com.icw.ehf.cui.core.api.common.view.ViewDescriptor;
import com.icw.ehf.cui.core.api.common.view.ViewDescriptorImpl;

import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.TypedGenerator;

class EmptyViewMatcherTest {

    TypedGenerator<String> viewGenerator = Generators.letterStrings(2, 10);

    @Test
    void testMatch() {
        final ViewDescriptor descriptor = ViewDescriptorImpl.builder().withViewId(viewGenerator.next())
                .withUrlParameter(Collections.emptyList()).withLogicalViewId(viewGenerator.next()).build();
        assertFalse(new EmptyViewMatcher(false).match(descriptor));
        assertTrue(new EmptyViewMatcher(true).match(descriptor));
    }
}
