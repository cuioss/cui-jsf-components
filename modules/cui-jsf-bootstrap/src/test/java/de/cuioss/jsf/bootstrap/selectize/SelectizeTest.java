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
package de.cuioss.jsf.bootstrap.selectize;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.cuioss.jsf.api.components.util.ComponentWrapper;
import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import de.cuioss.test.jsf.junit5.EnableJsfEnvironment;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.html.HtmlForm;
import jakarta.faces.component.html.HtmlInputText;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@EnableJsfEnvironment
@DisplayName("Tests for Selectize")
class SelectizeTest {

    private static final String KEY1 = "key1";

    private static final String KEY2 = "key2";

    private static final String EXTENSION = "extension";

    private ComponentWrapper<UIComponent> componentWrapper;

    @BeforeEach
    void setUpBefore(ComponentConfigDecorator decorator) {
        decorator.registerMockRendererForHtmlForm().registerMockRendererForHtmlInputText();

        var form = new HtmlForm();
        var wrapped = new HtmlInputText();
        wrapped.setId("mock");
        form.setId("form");
        form.getChildren().add(wrapped);
        componentWrapper = new ComponentWrapper<>(wrapped);
    }

    @Nested
    @DisplayName("Tests for script generation")
    class ScriptGenerationTests {

        @Test
        @DisplayName("Should generate minimal script correctly")
        void shouldCreateMinimalSelectize() {
            // Arrange & Act
            String script = Selectize.builder()
                    .withComponentWrapper(componentWrapper)
                    .build()
                    .script();

            // Assert
            assertEquals("jQuery('#form\\\\:mock').selectize();", script,
                    "Should generate correct minimal script");
        }

        @Test
        @DisplayName("Should generate script with ID extension correctly")
        void shouldCreateSelectizeWithExtension() {
            // Arrange & Act
            String script = Selectize.builder()
                    .withComponentWrapper(componentWrapper)
                    .withIdExtension(EXTENSION)
                    .build()
                    .script();

            // Assert
            assertEquals("jQuery('#form\\\\:mock_extension').selectize();", script,
                    "Should generate correct script with ID extension");
        }

        @Test
        @DisplayName("Should generate script with single option correctly")
        void shouldCreateSelectizeWithOption() {
            // Arrange & Act
            String script = Selectize.builder()
                    .withComponentWrapper(componentWrapper)
                    .withOption(KEY1, 1)
                    .build()
                    .script();

            // Assert
            assertEquals("jQuery('#form\\\\:mock').selectize({key1:'1'});", script,
                    "Should generate correct script with single option");
        }

        @Test
        @DisplayName("Should generate script with multiple options correctly")
        void shouldCreateSelectizeWithOptions() {
            // Arrange
            Map<String, Serializable> options = new HashMap<>();
            options.put(KEY1, 1);
            options.put(KEY2, Boolean.TRUE);

            // Act
            String script = Selectize.builder()
                    .withComponentWrapper(componentWrapper)
                    .withOptions(options)
                    .build()
                    .script();

            // Assert
            assertEquals("jQuery('#form\\\\:mock').selectize({key1:'1',key2:'true'});", script,
                    "Should generate correct script with multiple options");
        }
    }

}
