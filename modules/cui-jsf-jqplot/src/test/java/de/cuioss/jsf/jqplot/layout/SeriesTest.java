package de.cuioss.jsf.jqplot.layout;

import static de.cuioss.jsf.jqplot.ChartTestSupport.assertThatNoPluginsAreUsed;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.jqplot.axes.AxisType;
import de.cuioss.jsf.jqplot.layout.Series;
import de.cuioss.test.generator.Generators;
import de.cuioss.test.valueobjects.contract.SerializableContractImpl;

class SeriesTest {

    private Series target;

    @Test
    void shouldBeSerializable() {
        assertDoesNotThrow(() -> SerializableContractImpl.serializeAndDeserialize(Series.createAsListElement()));
    }

    @Test
    void shouldNotReturnObjectOnEmptyProperties() {
        target = Series.createAsSeriesDefaults();
        assertNull(target.asJavaScriptObjectNotation());
        assertThatNoPluginsAreUsed(target);
    }

    @Test
    void shouldProvideHideLine() {
        target = Series.createAsSeriesDefaults();
        target.setShowLine(TRUE);
        assertEquals("seriesDefaults: {showLine:true}", target.asJavaScriptObjectNotation());
        target.setShowLine(FALSE);
        assertEquals("seriesDefaults: {showLine:false}", target.asJavaScriptObjectNotation());
        assertThatNoPluginsAreUsed(target);
    }

    @Test
    void shouldProvideColorLine() {
        target = Series.createAsListElement();
        target.setColor("#FF5500");
        assertEquals("{color:\"#FF5500\"}", target.asJavaScriptObjectNotation());
        target.setColor(null);
        assertEquals("{color:\"transparent\"}", target.asJavaScriptObjectNotation());
        assertThatNoPluginsAreUsed(target);
    }

    @Test
    void shouldProvideShadow() {
        target = Series.createAsListElement();
        target.setShadow(TRUE);
        assertEquals("{shadow:true}", target.asJavaScriptObjectNotation());
        target.setShadow(FALSE);
        assertEquals("{shadow:false}", target.asJavaScriptObjectNotation());
        assertThatNoPluginsAreUsed(target);
    }

    @Test
    void shouldProvideLineWidth() {
        target = Series.createAsListElement();
        target.setLineWidth(10.1);
        assertEquals("{lineWidth:10.100}", target.asJavaScriptObjectNotation());
        target.setLineWidth(null);
        assertNull(target.asJavaScriptObjectNotation());
        assertThatNoPluginsAreUsed(target);
    }

    @Test
    void shouldProvideChangeSeriaAxis() {
        target = Series.createAsSeriesDefaults();
        var yaxis = Generators.fixedValues(AxisType.Y_AXES).next();
        assertThrows(IllegalArgumentException.class, () -> target.setXaxis(yaxis));

        target.setXaxis(AxisType.X2AXIS);

        assertEquals("seriesDefaults: {xaxis:\"x2axis\"}", target.asJavaScriptObjectNotation());

        var xaxis = Generators.fixedValues(AxisType.X_AXES).next();
        assertThrows(IllegalArgumentException.class, () -> target.setYaxis(xaxis));

        target.setYaxis(AxisType.Y2AXIS);
        assertEquals("seriesDefaults: {xaxis:\"x2axis\",yaxis:\"y2axis\"}", target.asJavaScriptObjectNotation());
    }
}
