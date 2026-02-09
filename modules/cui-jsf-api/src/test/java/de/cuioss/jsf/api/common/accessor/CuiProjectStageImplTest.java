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
package de.cuioss.jsf.api.common.accessor;

import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.test.jsf.config.decorator.ApplicationConfigDecorator;
import de.cuioss.test.jsf.junit5.EnableJsfEnvironment;
import de.cuioss.test.valueobjects.api.property.PropertyReflectionConfig;
import jakarta.faces.application.ProjectStage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

// Class is documented by @DisplayName
@DisplayName("Tests for CuiProjectStageImpl")
@PropertyReflectionConfig(skip = true)
@EnableJsfEnvironment
class CuiProjectStageImplTest {

    private final TypedGenerator<ProjectStage> projectStages = Generators.enumValues(ProjectStage.class);

    @Nested
    @DisplayName("Project Stage Detection Tests")
    class ProjectStageDetectionTests {

        @Test
        @DisplayName("Should correctly identify Development stage")
        void shouldIdentifyDevelopmentStage() {
            // Arrange - create bean first
            final var underTest = new CuiProjectStageImpl();

            // Act - set stage and initialize
            setProjectStage(ProjectStage.Development);
            underTest.initBean();

            // Assert
            assertTrue(underTest.isDevelopment(), "Should identify Development stage");

            // Act - change stage and reinitialize
            setProjectStage(ProjectStage.Production);
            underTest.initBean();

            // Assert
            assertFalse(underTest.isDevelopment(), "Should not identify Production as Development");
        }

        @Test
        @DisplayName("Should correctly identify Production stage")
        void shouldIdentifyProductionStage() {
            // Arrange - create bean first
            final var underTest = new CuiProjectStageImpl();

            // Act - set stage and initialize
            setProjectStage(ProjectStage.Development);
            underTest.initBean();

            // Assert
            assertFalse(underTest.isProduction(), "Should not identify Development as Production");

            // Act - change stage and reinitialize
            setProjectStage(ProjectStage.Production);
            underTest.initBean();

            // Assert
            assertTrue(underTest.isProduction(), "Should identify Production stage");
        }

        @Test
        @DisplayName("Should correctly identify Test stage")
        void shouldIdentifyTestStage() {
            // Arrange - create bean first
            final var underTest = new CuiProjectStageImpl();

            // Act - set stage and initialize
            setProjectStage(ProjectStage.SystemTest);
            underTest.initBean();

            // Assert
            assertTrue(underTest.isTest(), "Should identify SystemTest as Test stage");
        }

        @Test
        @DisplayName("Should correctly handle Configuration stage")
        void shouldHandleConfigurationStage() {
            // Arrange
            final var underTest = anyBean();

            // Act
            underTest.initBean();

            // Assert
            assertFalse(underTest.isConfiguration(), "Should not identify as Configuration stage");
        }
    }

    @Nested
    @DisplayName("Accessor Tests")
    class AccessorTests {

        @Test
        @DisplayName("Should provide project stage through accessor")
        void shouldProvideProjectStageViaAccessor() {
            // Arrange
            final var accessor = new CuiProjectStageAccessor();

            // Act
            final var result = accessor.getValue();

            // Assert
            assertNotNull(result, "Project stage should be provided by accessor");
        }
    }

    /**
     * Creates a CuiProjectStageImpl instance with a random project stage
     * @return initialized CuiProjectStageImpl
     */
    private CuiProjectStageImpl anyBean() {
        var projectStageImpl = new CuiProjectStageImpl();
        setProjectStage(projectStages.next());
        projectStageImpl.initBean();
        return projectStageImpl;
    }

    // Store the application config decorator for use in setProjectStage
    private ApplicationConfigDecorator applicationConfigDecorator;

    /**
     * Sets the project stage in the JSF application
     * @param stage the ProjectStage to set
     */
    private void setProjectStage(final ProjectStage stage) {
        applicationConfigDecorator.setProjectStage(stage);
    }

    /**
     * Initializes the application config decorator
     * @param decorator the ApplicationConfigDecorator to use
     */
    @BeforeEach
    void initApplicationConfigDecorator(ApplicationConfigDecorator decorator) {
        this.applicationConfigDecorator = decorator;
    }
}
