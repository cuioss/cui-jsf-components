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
package de.cuioss.jsf.test;

import de.cuioss.portal.common.stage.ProjectStage;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Produces;
import lombok.Getter;
import lombok.Setter;

/**
 * A mock implementation that produces configurable {@link ProjectStage} instances for testing.
 * <p>
 * This class allows test cases to control the project stage, enabling verification of
 * stage-dependent behavior without modifying production configuration. It defaults to
 * {@link ProjectStage#PRODUCTION} but can be set to any other stage during tests.
 * </p>
 * <p>
 * This mock is automatically included when using the {@link EnableJSFCDIEnvironment} annotation,
 * making it available for injection in test classes.
 * </p>
 * <p>
 * Usage example:
 * <pre>
 * {@code
 * @EnableJSFCDIEnvironment
 * class ProjectStageTest {
 *     
 *     @Inject
 *     private ProjectStageProducerMock stageProducer;
 *     
 *     @Inject
 *     private ProjectStage projectStage;
 *     
 *     @Test
 *     void shouldBehaveDifferentlyInDevelopment() {
 *         // Change project stage for this test
 *         stageProducer.setProjectStage(ProjectStage.DEVELOPMENT);
 *         
 *         // Test development-specific behavior
 *         assertEquals(ProjectStage.DEVELOPMENT, projectStage);
 *     }
 * }
 * }
 * </pre>
 * </p>
 * <p>
 * This class is conditionally thread-safe. Thread safety depends on clients
 * properly synchronizing access when modifying the project stage.
 * </p>
 *
 * @author Oliver Wolff
 * @since 1.0
 */
@ApplicationScoped
public class ProjectStageProducerMock {

    /**
     * The project stage to be used for testing.
     * <p>
     * By default, this is set to {@link ProjectStage#PRODUCTION}. Tests can modify
     * this value to verify stage-specific behavior.
     * </p>
     */
    @Getter
    @Setter
    @Produces
    @RequestScoped
    ProjectStage projectStage = ProjectStage.PRODUCTION;
}
