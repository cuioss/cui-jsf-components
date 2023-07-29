package de.cuioss.jsf.api.components.partial;

import static de.cuioss.test.generator.Generators.integers;
import static de.cuioss.test.generator.Generators.letterStrings;
import static de.cuioss.tools.collect.CollectionLiterals.mutableList;
import static de.cuioss.tools.string.MoreStrings.isEmpty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.tools.string.Joiner;

class ForIdentifierProviderImplTest {

    private final TypedGenerator<Integer> integers = integers(1, 5);

    private final TypedGenerator<String> ids = letterStrings(5, 10);

    private ForIdentifierProvider target;

    @SuppressWarnings("unused")
    @Test
    final void shouldVerifyMandatoryConstructorParameter() {
        assertThrows(NullPointerException.class,
                () -> new ForIdentifierProvider(null, ForIdentifierProvider.DEFAULT_FOR_IDENTIFIER));
    }

    @Test
    void shouldNotRetrieveTargetComponentIdAsLongNoWasDefined() {
        target = new ForIdentifierProvider(new MockPartialComponent(), "");
        assertTrue(isEmpty(target.getForIdentifier()));
        assertFalse(target.resolveFirstIdentifier().isPresent());
        assertTrue(target.resolveIdentifierAsList().isEmpty());
    }

    @Test
    void shouldNotRetrieveTargetComponentId() {
        final var expected_id = ids.next();
        target = createAnyValid();
        target.setForIdentifier(expected_id);
        assertEquals(expected_id, target.getForIdentifier());
        assertTrue(target.resolveFirstIdentifier().isPresent());
        assertEquals(expected_id, target.resolveFirstIdentifier().get());
        assertTrue(target.resolveIdentifierAsList().contains(expected_id));
    }

    @Test
    void shouldSupportMultipleTargetComponentIds() {
        final var first_id = ids.next();
        final var separatedIds = anyIds();
        final List<String> allIds = new ArrayList<>();
        allIds.add(first_id);
        allIds.addAll(Arrays.asList(separatedIds));
        final var expected_ids = Joiner.on(" ").join(allIds);
        target = createAnyValid();
        target.setForIdentifier(expected_ids);
        assertEquals(expected_ids, target.getForIdentifier());
        assertTrue(target.resolveFirstIdentifier().isPresent());
        assertEquals(first_id, target.resolveFirstIdentifier().get());
        assertTrue(target.resolveIdentifierAsList().contains(first_id));
        assertTrue(target.resolveIdentifierAsList().containsAll(mutableList(separatedIds)));
    }

    private static ForIdentifierProvider createAnyValid() {
        return new ForIdentifierProvider(new MockPartialComponent(), ForIdentifierProvider.DEFAULT_FOR_IDENTIFIER);
    }

    private String[] anyIds() {
        final var maxCount = integers.next();
        final List<String> tempIds = new ArrayList<>(maxCount);
        for (var i = 0; i < maxCount; i++) {
            tempIds.add(ids.next());
        }
        return tempIds.toArray(new String[tempIds.size()]);
    }
}
