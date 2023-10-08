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
package de.cuioss.jsf.api.common.accessor;

import javax.faces.application.ProjectStage;
import javax.faces.context.FacesContext;

import de.cuioss.uimodel.application.CuiProjectStage;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Helper class utilized for accessing Project Stage information in a convenient
 * way. It needs to be registered as an Application Scoped bean, because the
 * Project stage does not change between startup.
 *
 * @author Oliver Wolff
 * @author Sven Haag
 */
@EqualsAndHashCode
@ToString
class CuiProjectStageImpl implements CuiProjectStage {

    private static final long serialVersionUID = -2464134252511225231L;

    @Getter
    private boolean development;

    @Getter
    private boolean test;

    @Getter
    private boolean configuration;

    @Getter
    private boolean production;

    /**
     * Initializes the bean. See class documentation for expected result.
     */
    void initBean() {
        var projectStage = FacesContext.getCurrentInstance().getApplication().getProjectStage();
        development = ProjectStage.Development.equals(projectStage);
        test = ProjectStage.SystemTest.equals(projectStage);
        configuration = false; // no JSF equivalent in existence
        production = ProjectStage.Production.equals(projectStage);
    }
}
