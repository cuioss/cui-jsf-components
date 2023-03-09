package com.icw.ehf.cui.components.chart.layout;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

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
