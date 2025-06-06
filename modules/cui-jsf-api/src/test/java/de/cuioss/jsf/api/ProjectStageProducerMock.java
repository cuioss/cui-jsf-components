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
package de.cuioss.jsf.api;

import de.cuioss.portal.common.stage.ProjectStage;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Produces;
import lombok.Getter;
import lombok.Setter;

/** Simple Mock for dealing with {@link ProjectStage} for unit-testing. */
@ApplicationScoped
public class ProjectStageProducerMock {

    @Getter
    @Setter
    @Produces
    @RequestScoped
    ProjectStage projectStage = ProjectStage.PRODUCTION;
}
