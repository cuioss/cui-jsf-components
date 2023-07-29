package de.cuioss.jsf.jqplot.axes;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import de.cuioss.test.valueobjects.contract.SerializableContractImpl;

class AxesTest {

    private Axes target;

    @Test
    void shouldBeSerializable() {
        assertDoesNotThrow(() -> SerializableContractImpl.serializeAndDeserialize(createAny()));
    }

    @Test
    final void shouldVerifyDuplicates() {
        target = createEmpty();
        target.addInNotNull(Axis.createXAxis());
        var axis = Axis.createXAxis();
        assertThrows(IllegalArgumentException.class, () -> target.addInNotNull(axis));
    }

    private static Axes createEmpty() {
        return new Axes();
    }

    private static Axes createAny() {
        final var result = new Axes();
        result.addInNotNull(null);
        result.addInNotNull(Axis.createXAxis());
        result.addInNotNull(Axis.createYAxis());
        return result;
    }
}
