package de.cuioss.jsf.components.chart.renderer.marker;

import static de.cuioss.jsf.components.chart.ChartTestSupport.assertThatNoPluginsAreUsed;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import de.cuioss.test.valueobjects.junit5.contracts.ShouldHandleObjectContracts;

class MarkerRendererOptionsTest implements ShouldHandleObjectContracts<MarkerRendererOptions> {

    @Override
    public MarkerRendererOptions getUnderTest() {
        return new MarkerRendererOptions();
    }

    @Test
    void shouldNotReturnObjectOnEmptyProperties() {
        final var target = new MarkerRendererOptions();
        assertNull(target.asJavaScriptObjectNotation());
        assertThatNoPluginsAreUsed(target);
    }

    @Test
    void shouldProvideShadowing() {
        final var target = new MarkerRendererOptions();
        target.setShadow(true);
        assertEquals("markerOptions: {shadow:true}", target.asJavaScriptObjectNotation());
        target.setShadow(false);
        assertEquals("markerOptions: {shadow:false}", target.asJavaScriptObjectNotation());
        target.setShadow(null);
        assertNull(target.asJavaScriptObjectNotation());
        // all shadow properties
        target.setShadow(true).setShadowAlpha("0.07").setShadowAngle(10.0).setShadowDepth(5).setShadowOffset(7);
        assertEquals(
                "markerOptions: {shadow:true,shadowAlpha:\"0.07\",shadowAngle:10.000,shadowDepth:5,shadowOffset:7}",
                target.asJavaScriptObjectNotation());
        assertThatNoPluginsAreUsed(target);
    }

    @Test
    void shouldProvideDifferentMarkerStyle() {
        final var target = new MarkerRendererOptions();
        target.setStyle(PointStyle.CIRCLE);
        assertEquals("markerOptions: {style:\"circle\"}", target.asJavaScriptObjectNotation());
        target.setStyle(PointStyle.FILLEDCIRCLE);
        assertEquals("markerOptions: {style:\"filledCircle\"}", target.asJavaScriptObjectNotation());
        target.setStyle(PointStyle.DASH);
        assertEquals("markerOptions: {style:\"dash\"}", target.asJavaScriptObjectNotation());
        target.setStyle(PointStyle.DIAMOND);
        assertEquals("markerOptions: {style:\"diamond\"}", target.asJavaScriptObjectNotation());
        target.setStyle(PointStyle.FILLEDDIAMOND);
        assertEquals("markerOptions: {style:\"filledDiamond\"}", target.asJavaScriptObjectNotation());
        target.setStyle(PointStyle.PLUS);
        assertEquals("markerOptions: {style:\"plus\"}", target.asJavaScriptObjectNotation());
        target.setStyle(PointStyle.SQUARE);
        assertEquals("markerOptions: {style:\"square\"}", target.asJavaScriptObjectNotation());
        target.setStyle(PointStyle.FILLEDSQUARE);
        assertEquals("markerOptions: {style:\"filledSquare\"}", target.asJavaScriptObjectNotation());
        target.setStyle(PointStyle.X);
        assertEquals("markerOptions: {style:\"x\"}", target.asJavaScriptObjectNotation());
        assertThatNoPluginsAreUsed(target);
    }

}
