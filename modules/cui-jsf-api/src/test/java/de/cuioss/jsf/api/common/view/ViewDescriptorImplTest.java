package de.cuioss.jsf.api.common.view;

import static de.cuioss.test.generator.Generators.strings;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Collections;

import org.junit.jupiter.api.Test;

import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.test.valueobjects.ValueObjectTest;
import de.cuioss.test.valueobjects.api.contracts.VerifyBuilder;
import de.cuioss.tools.net.ParameterFilter;

@VerifyBuilder(methodPrefix = "with", exclude = { "viewDefined", "shortIdentifier" })
class ViewDescriptorImplTest extends ValueObjectTest<ViewDescriptorImpl> {

    private final TypedGenerator<String> stringGenerator = strings(1, 55);

    @Test
    void shouldBuildWithCopyConstructor() {
        final var built = ViewDescriptorImpl.builder().withLogicalViewId(stringGenerator.next())
                .withUrlParameter(Collections.emptyList()).withViewId(stringGenerator.next()).build();
        final var copy = new ViewDescriptorImpl(built, null);
        assertEquals(built, copy);
    }

    @Test
    void shouldBuildWithCopyConstructorAndFilter() {
        final var built = ViewDescriptorImpl.builder().withLogicalViewId(stringGenerator.next())
                .withUrlParameter(Collections.emptyList()).withViewId(stringGenerator.next()).build();
        final var copy = new ViewDescriptorImpl(built, new ParameterFilter(Collections.emptyList(), true));
        assertEquals(built, copy);
    }

    @Test
    void shouldBuildDetermineViewDefined() {
        final var built = ViewDescriptorImpl.builder().build();
        assertFalse(built.isViewDefined());
        final var copy = new ViewDescriptorImpl(built, null);
        assertFalse(copy.isViewDefined());
    }
}
