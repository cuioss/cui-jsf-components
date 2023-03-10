package de.cuioss.jsf.components.chart.axes;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.components.chart.renderer.DateAxisRenderer;
import de.cuioss.test.valueobjects.contract.SerializableContractImpl;

class AxisTest {

    @Test
    void shouldBeSerializable() {
        assertDoesNotThrow(() -> SerializableContractImpl.serializeAndDeserialize(Axis.createXAxis()));
    }

    @Test
    void shouldNotReturnObjectOnEmptyProperties() {
        final var target = Axis.createYAxis();
        assertNull(target.asJavaScriptObjectNotation());
    }

    @Test
    void shouldAcceptRenderer() {
        final var target = Axis.createXAxis2();
        target.setRenderer(new DateAxisRenderer());
        assertEquals("x2axis: {renderer:$.jqplot.DateAxisRenderer}", target.asJavaScriptObjectNotation());
    }

    @Test
    void shouldProvideLabel() {
        final var target = Axis.createYAxis2();
        target.setLabel("some label");
        assertEquals("y2axis: {label:\"some label\",showLabel:true}", target.asJavaScriptObjectNotation());
    }
}
