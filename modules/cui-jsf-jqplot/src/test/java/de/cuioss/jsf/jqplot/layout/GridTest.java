package de.cuioss.jsf.jqplot.layout;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.jqplot.layout.Grid;

class GridTest {

    private Grid target;

    @Test
    void verifyDefaultSettings() {
        target = new Grid();
        final var expected = "grid: {drawGridLines:true,gridLineColor:\"#cccccc\",borderWidth:2.000}";
        final var actual = target.asJavaScriptObjectNotation();
        assertEquals(expected, actual);
    }
}
