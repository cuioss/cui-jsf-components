package com.icw.ehf.cui.components.chart.plugin.highlighter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import de.cuioss.test.valueobjects.junit5.contracts.ShouldHandleObjectContracts;

class TooltipContentEditorTest implements ShouldHandleObjectContracts<TooltipContentEditor> {

    @Test
    void shouldProvideExtensionPoint() {
        final var target = new TooltipContentEditor();
        assertEquals("function tooltipContentEditor(str,seriesIndex,pointIndex,plot){return \"\";};",
                target.getHookFunctionCode());
        target.setFunctionContent("{var bla = 10;};");
        assertEquals("function tooltipContentEditor(str,seriesIndex,pointIndex,plot){var bla = 10;};",
                target.getHookFunctionCode());
    }

    @Override
    public TooltipContentEditor getUnderTest() {
        return new TooltipContentEditor();
    }
}
