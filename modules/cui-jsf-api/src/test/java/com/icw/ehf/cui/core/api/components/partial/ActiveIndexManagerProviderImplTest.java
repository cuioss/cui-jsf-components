package com.icw.ehf.cui.core.api.components.partial;

import static de.cuioss.tools.collect.CollectionLiterals.immutableList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.icw.ehf.cui.core.api.components.support.ActiveIndexManagerImpl;

import de.cuioss.test.jsf.config.component.VerifyComponentProperties;

@VerifyComponentProperties
class ActiveIndexManagerProviderImplTest extends AbstractPartialComponentTest {

    @SuppressWarnings("unused")
    @Test
    void shouldFailWithNullConstructor() {
        assertThrows(NullPointerException.class, () -> {
            new ActiveIndexManagerProvider(null);
        });
    }

    @Test
    void shouldResolveIndexes() {
        List<Integer> indexes = immutableList(1, 2, 3, 4);
        var any = anyComponent();
        any.setActiveIndexManager(new ActiveIndexManagerImpl(indexes));
        assertEquals(indexes, any.resolveActiveIndexes());
        any.getActiveIndexManager().setActiveIndex(9);
        assertEquals(immutableList(9), any.resolveActiveIndexes());
        any.getActiveIndexManager().setActiveIndex(immutableList(5, 6));
        assertEquals(immutableList(5, 6), any.resolveActiveIndexes());
        any.getActiveIndexManager().setActiveIndexesString("10 11");
        assertEquals(immutableList(10, 11), any.resolveActiveIndexes());
        any.getActiveIndexManager().resetToDefaultIndex();
        assertEquals(indexes, any.resolveActiveIndexes());
    }
}
