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

import de.cuioss.uimodel.application.CuiProjectStage;
import jakarta.faces.application.ProjectStage;
import jakarta.faces.context.FacesContext;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serial;

/**
 * Implementation of {@link CuiProjectStage} that maps JSF {@link ProjectStage} information
 * to the CUI project stage model.
 * <p>
 * This class provides a bridge between JSF's built-in project stage system and the CUI
 * project stage abstraction. It maps:
 * <ul>
 *   <li>{@link ProjectStage#Development} to {@link #isDevelopment()}</li>
 *   <li>{@link ProjectStage#SystemTest} to {@link #isTest()}</li>
 *   <li>{@link ProjectStage#Production} to {@link #isProduction()}</li>
 * </ul>
 * Note that there is no JSF equivalent for the {@link #isConfiguration()} stage,
 * so this always returns false.
 * 
 * <p>
 * This class is package-private and is intended to be used only by {@link CuiProjectStageAccessor}.
 * The determination of the project stage happens only once during initialization.
 * 
 * <p>
 * This class is not thread-safe for initialization, but after initialization it is immutable
 * and therefore thread-safe for reading.
 *
 * @author Oliver Wolff
 * @author Sven Haag
 * @since 1.0
 * @see CuiProjectStage
 * @see ProjectStage
 * @see CuiProjectStageAccessor
 */
@EqualsAndHashCode
@ToString
class CuiProjectStageImpl implements CuiProjectStage {

    @Serial
    private static final long serialVersionUID = -2464134252511225231L;

    /**
     * Flag indicating whether the application is running in development mode.
     * Maps to {@link ProjectStage#Development}.
     */
    @Getter
    private boolean development;

    /**
     * Flag indicating whether the application is running in test mode.
     * Maps to {@link ProjectStage#SystemTest}.
     */
    @Getter
    private boolean test;

    /**
     * Flag indicating whether the application is running in configuration mode.
     * This has no JSF equivalent and always returns false.
     */
    @Getter
    private boolean configuration;

    /**
     * Flag indicating whether the application is running in production mode.
     * Maps to {@link ProjectStage#Production}.
     */
    @Getter
    private boolean production;

    /**
     * Initializes this instance by determining the current JSF project stage
     * and setting the corresponding flags.
     * <p>
     * This method should be called exactly once during the lifecycle of this object,
     * typically immediately after construction.
     * </p>
     */
    void initBean() {
        var projectStage = FacesContext.getCurrentInstance().getApplication().getProjectStage();
        development = ProjectStage.Development.equals(projectStage);
        test = ProjectStage.SystemTest.equals(projectStage);
        configuration = false; // no JSF equivalent in existence
        production = ProjectStage.Production.equals(projectStage);
    }
}
