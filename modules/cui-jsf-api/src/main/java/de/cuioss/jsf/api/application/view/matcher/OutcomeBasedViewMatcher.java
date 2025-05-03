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
package de.cuioss.jsf.api.application.view.matcher;

import de.cuioss.jsf.api.application.navigation.NavigationUtils;
import de.cuioss.jsf.api.common.view.ViewDescriptor;
import jakarta.faces.context.FacesContext;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.Serial;

/**
 * {@link ViewMatcher} that looks up an outcome for a concrete view.
 *
 * @author Oliver Wolff
 *
 */
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class OutcomeBasedViewMatcher implements ViewMatcher {

    @Serial
    private static final long serialVersionUID = -8359315030575513107L;

    @NonNull
    private final String outcome;

    private ViewDescriptor descriptor;

    @Override
    public boolean match(ViewDescriptor viewDescriptor) {
        return getDescriptor().getLogicalViewId().equals(viewDescriptor.getLogicalViewId());
    }

    private ViewDescriptor getDescriptor() {
        if (null == descriptor) {
            descriptor = NavigationUtils.lookUpToViewDescriptorBy(FacesContext.getCurrentInstance(), outcome);
        }
        return descriptor;
    }

}
