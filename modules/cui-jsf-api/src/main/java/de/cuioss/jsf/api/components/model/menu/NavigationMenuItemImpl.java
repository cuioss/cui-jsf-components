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
package de.cuioss.jsf.api.components.model.menu;

import java.util.ArrayList;
import java.util.List;

import jakarta.faces.context.FacesContext;

import de.cuioss.jsf.api.application.navigation.NavigationUtils;
import de.cuioss.jsf.api.components.support.LabelResolver;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Basic Implementation of a {@link NavigationMenuItem}
 *
 * @author Sven Haag
 */
@EqualsAndHashCode
@ToString
@RequiredArgsConstructor
public abstract class NavigationMenuItemImpl implements NavigationMenuItem {

    private static final long serialVersionUID = 7357376149469833501L;

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private String iconStyleClass;

    @Getter
    @Setter
    private boolean disabled = false;

    @Getter
    @Setter
    private boolean rendered = true;

    @Getter
    private final Integer order;

    @Getter
    @Setter
    private String titleKey;

    @Getter
    @Setter
    private String titleValue;

    @Getter
    @Setter
    private String parentId;

    /**
     * Allows to configure a list of view ids that should trigger displaying the
     * menu item in "active" mode.
     */
    @Getter
    @Setter
    private List<String> activeForAdditionalViewId = new ArrayList<>();

    @Override
    public int compareTo(final NavigationMenuItem other) {
        return getOrder().compareTo(other.getOrder());
    }

    @Override
    public String getResolvedTitle() {
        return LabelResolver.builder().withLabelKey(titleKey).withLabelValue(titleValue).build()
                .resolve(FacesContext.getCurrentInstance());
    }

    @Override
    public boolean isTitleAvailable() {
        return null != getResolvedTitle();
    }

    @Override
    public boolean isActive() {
        return !getActiveForAdditionalViewId().isEmpty() && getActiveForAdditionalViewId()
                .contains(NavigationUtils.getCurrentView(FacesContext.getCurrentInstance()).getViewId());
    }

}
