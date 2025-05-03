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
package de.cuioss.jsf.api.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.cuioss.test.jsf.junit5.JsfEnabledTestEnvironment;
import de.cuioss.test.juli.LogAsserts;
import de.cuioss.test.juli.TestLogLevel;
import de.cuioss.test.juli.junit5.EnableTestLogger;
import jakarta.faces.application.ProjectStage;
import jakarta.faces.component.html.HtmlInputText;
import jakarta.faces.component.html.HtmlOutputText;
import org.junit.jupiter.api.Test;

@EnableTestLogger
class FallbackSanitizingConverterTest extends JsfEnabledTestEnvironment {

    @Test
    void shouldNotSanitizeWhenInput() {
        var component = new HtmlInputText();
        var result = new FallbackSanitizingConverter().convertToString(getFacesContext(), component, "<script>");
        assertEquals("<script>", result);
    }

    @Test
    void shouldNotSanitizeWhenOutputAndEscape() {
        var component = new HtmlOutputText();
        var result = new FallbackSanitizingConverter().convertToString(getFacesContext(), component, "<script>");
        assertEquals("<script>", result);
    }

    @Test
    void shouldSanitizeAndWarnWhenOutputAndEscape() {
        getApplicationConfigDecorator().setProjectStage(ProjectStage.Development);
        var component = new HtmlOutputText();
        component.setEscape(false);
        var result = new FallbackSanitizingConverter().convertToString(getFacesContext(), component, "<script>");
        assertEquals("", result);
        LogAsserts.assertSingleLogMessagePresentContaining(TestLogLevel.WARN, "CUI-101");
    }
}
