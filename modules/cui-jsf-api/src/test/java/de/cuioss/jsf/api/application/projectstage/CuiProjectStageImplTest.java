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
package de.cuioss.jsf.api.application.projectstage;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.faces.application.ProjectStage;

import org.junit.jupiter.api.Test;

import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.test.jsf.junit5.JsfEnabledTestEnvironment;
import de.cuioss.test.valueobjects.api.property.PropertyReflectionConfig;

@PropertyReflectionConfig(skip = true)
class CuiProjectStageImplTest extends JsfEnabledTestEnvironment {

    private final TypedGenerator<ProjectStage> projectStages = Generators.enumValues(ProjectStage.class);

    @Test
    void testIsDevelopment() {
        final var underTest = anyBean();
        setProjectStage(ProjectStage.Development);
        underTest.initBean();
        assertTrue(underTest.isDevelopment());
        setProjectStage(ProjectStage.Production);
        underTest.initBean();
        assertFalse(underTest.isDevelopment());
    }

    @Test
    void shouldBeProvidedByAccessor() {
        getBeanConfigDecorator().register(anyBean(), CuiProjectStageAccessor.BEAN_NAME);
        assertNotNull(new CuiProjectStageAccessor().getValue());
    }

    @Test
    void testIsProduction() {
        final var underTest = anyBean();
        setProjectStage(ProjectStage.Development);
        underTest.initBean();
        assertFalse(underTest.isProduction());
        setProjectStage(ProjectStage.Production);
        underTest.initBean();
        assertTrue(underTest.isProduction());
    }

    @Test
    void testIsConfiguration() {
        final var underTest = anyBean();
        underTest.initBean();
        assertFalse(underTest.isConfiguration());
    }

    @Test
    void testIsTest() {
        final var underTest = anyBean();
        setProjectStage(ProjectStage.SystemTest);
        underTest.initBean();
        assertTrue(underTest.isTest());
    }

    private CuiProjectStageImpl anyBean() {
        var projectStageImpl = new CuiProjectStageImpl();
        setProjectStage(projectStages.next());
        projectStageImpl.initBean();
        return projectStageImpl;
    }

    private void setProjectStage(final ProjectStage stage) {
        getApplicationConfigDecorator().setProjectStage(stage);
    }
}
