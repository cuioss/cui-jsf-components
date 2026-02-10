/*
 * Copyright © 2025 CUI-OpenSource-Software (info@cuioss.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.bootstrap.icon;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.test.CoreJsfTestConfiguration;
import de.cuioss.jsf.test.EnableJSFCDIEnvironment;
import de.cuioss.jsf.test.EnableResourceBundleSupport;
import de.cuioss.test.jsf.component.AbstractUiComponentTest;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import de.cuioss.uimodel.model.Gender;
import org.jboss.weld.junit5.ExplicitParamInjection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@JsfTestConfiguration(CoreJsfTestConfiguration.class)
@VerifyComponentProperties(of = {"gender", "genderString", "size", "state", "titleValue"})
@EnableJSFCDIEnvironment
@EnableResourceBundleSupport
@ExplicitParamInjection
@DisplayName("Tests for GenderIconComponent")
class GenderIconComponentTest extends AbstractUiComponentTest<GenderIconComponent> {

    @BeforeEach
    void configureCuiComponents(ComponentConfigDecorator decorator) {
        CoreJsfTestConfiguration.configureComponents(decorator);
    }

    private static final String ICON_PREFIX = "cui-icon ";
    private static final String GENDER_UNKNOWN = ICON_PREFIX + Gender.UNKNOWN.getCssClass();
    public static final String GENDER_UNKNOWN_TITLE = "cui.model.gender.unknown.title";
    private static final String GENDER_MALE_CSS = ICON_PREFIX + Gender.MALE.getCssClass();
    public static final String GENDER_MALE_TITLE = "cui.model.gender.male.title";

    @Nested
    @DisplayName("Default behavior tests")
    class DefaultBehaviorTests {

        @Test
        @DisplayName("Should resolve to UNKNOWN when no gender is set")
        void shouldResolveToUnknownIfNoneIsSet() {
            // Arrange
            var component = anyComponent();

            // Act & Assert
            assertEquals(GENDER_UNKNOWN, component.resolveIconCss(),
                    "Icon CSS should be for UNKNOWN gender");
            assertEquals(GENDER_UNKNOWN_TITLE, component.resolveTitle(),
                    "Title should be for UNKNOWN gender");
        }

        @Test
        @DisplayName("Should default to UNKNOWN for invalid gender string")
        void shouldDefaultToUnknownForInvalidGenderString() {
            // Arrange
            var component = anyComponent();

            // Act
            component.setGenderString("not.there");

            // Assert
            assertEquals(GENDER_UNKNOWN, component.resolveIconCss(),
                    "Icon CSS should be for UNKNOWN gender with invalid string");
            assertEquals(GENDER_UNKNOWN_TITLE, component.resolveTitle(),
                    "Title should be for UNKNOWN gender with invalid string");
        }
    }

    @Nested
    @DisplayName("Gender resolution tests")
    class GenderResolutionTests {

        @Test
        @DisplayName("Should resolve to specified Gender object")
        void shouldResolveToGenderObject() {
            // Arrange
            var component = anyComponent();

            // Act
            component.setGender(Gender.MALE);

            // Assert
            assertEquals(GENDER_MALE_CSS, component.resolveIconCss(),
                    "Icon CSS should be for MALE gender");
            assertEquals(GENDER_MALE_TITLE, component.resolveTitle(),
                    "Title should be for MALE gender");
        }

        @Test
        @DisplayName("Should resolve to gender from string representation")
        void shouldResolveToGenderFromString() {
            // Arrange
            var component = anyComponent();

            // Act
            component.setGenderString("m");

            // Assert
            assertEquals(GENDER_MALE_CSS, component.resolveIconCss(),
                    "Icon CSS should be for MALE gender from string 'm'");
            assertEquals(GENDER_MALE_TITLE, component.resolveTitle(),
                    "Title should be for MALE gender from string 'm'");
        }

        @Test
        @DisplayName("Should prioritize Gender object over gender string")
        void shouldPrioritizeGenderObjectOverString() {
            // Arrange
            var component = anyComponent();

            // Act
            component.setGender(Gender.MALE);
            component.setGenderString("f");

            // Assert
            assertEquals(GENDER_MALE_CSS, component.resolveIconCss(),
                    "Icon CSS should be for MALE gender when both are set");
            assertEquals(GENDER_MALE_TITLE, component.resolveTitle(),
                    "Title should be for MALE gender when both are set");
        }
    }

    @Nested
    @DisplayName("Component metadata tests")
    class MetadataTests {

        @Test
        @DisplayName("Should provide correct renderer type")
        void shouldProvideCorrectRendererType() {
            // Arrange
            var component = anyComponent();

            // Act & Assert
            assertEquals(BootstrapFamily.ICON_COMPONENT_RENDERER, component.getRendererType(),
                    "Renderer type should match ICON_COMPONENT_RENDERER");
        }
    }
}
