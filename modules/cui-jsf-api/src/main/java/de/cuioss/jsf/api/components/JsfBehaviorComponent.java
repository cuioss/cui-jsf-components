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
package de.cuioss.jsf.api.components;

import static java.util.Objects.requireNonNull;

import jakarta.faces.component.behavior.AjaxBehavior;
import jakarta.faces.context.FacesContext;

import lombok.experimental.UtilityClass;

/**
 * Repository for the standard JSF behavior
 *
 * @author Oliver Wolff
 *
 */
@UtilityClass
public class JsfBehaviorComponent {

    /**
     * @param context must not be {@code null}
     * @return a newly instantiated AjaxBehavior
     */
    public static final AjaxBehavior ajaxBehavior(FacesContext context) {
        requireNonNull(context);
        return (AjaxBehavior) context.getApplication().createBehavior(AjaxBehavior.BEHAVIOR_ID);
    }
}
