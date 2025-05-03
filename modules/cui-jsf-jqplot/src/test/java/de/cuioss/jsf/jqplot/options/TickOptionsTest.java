/*
 * Copyright 2023 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.jqplot.options;

import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.test.valueobjects.junit5.contracts.ShouldHandleObjectContracts;
import de.cuioss.tools.collect.CollectionBuilder;
import org.junit.jupiter.api.Test;

class TickOptionsTest implements ShouldHandleObjectContracts<TickOptions> {

    @Override
    public TickOptions getUnderTest() {
        return new TickOptions();
    }

    @Test
    void shouldNotReturnObjectOnEmptyProperties() {
        final var target = new TickOptions();
        assertNull(target.asJavaScriptObjectNotation());
    }

    @Test
    void shouldProvideFontFamily() {
        final var target = new TickOptions();
        target.setFontFamily("Arial");
        assertEquals("tickOptions: {fontFamily:\"Arial\"}", target.asJavaScriptObjectNotation());
    }

    @Test
    void shouldProvideFontSize() {
        final var target = new TickOptions();
        target.setFontSize("15px");
        assertEquals("tickOptions: {fontSize:\"15px\"}", target.asJavaScriptObjectNotation());
    }

    @Test
    void shouldProvideAngle() {
        final var target = new TickOptions();
        target.setAngle(10);
        assertEquals("tickOptions: {angle:10}", target.asJavaScriptObjectNotation());
    }

    @Test
    void shouldProvideLabelSettings() {
        final var target = new TickOptions().setFontFamily("Arial").setFontSize("15px").setTextColor("red")
                .setAngle(10);
        assertEquals("tickOptions: {fontFamily:\"Arial\",fontSize:\"15px\",angle:10,textColor:\"red\"}",
                target.asJavaScriptObjectNotation());
    }

    @Test
    void shouldHandleAllProperties() {
        final var target = new TickOptions().setShowMark(true).setShowGridline(true).setEscapeHTML(true)
                .setFontFamily("Arial").setFontSize("15px").setAngle(10).setShowLabel(false);
        var builder = new CollectionBuilder<String>();

        builder.add("escapeHTML:true").add("fontFamily:\"Arial\"").add("fontSize:\"15px\"").add("angle:10")
                .add("showLabel:false").add("showMark:true").add("showGridline:true");
        var actual = target.asJavaScriptObjectNotation();
        for (String element : builder.toMutableList()) {
            assertTrue(actual.contains(element));
        }
    }

}
