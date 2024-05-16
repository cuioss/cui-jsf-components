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

import de.cuioss.jsf.api.components.support.LabelResolver;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Basic Implementation of a {@link NavigationMenuItem}
 *
 * @author Sven Haag
 */
@EqualsAndHashCode(callSuper = true)
@ToString
public class NavigationMenuItemContainerImpl extends NavigationMenuItemImpl implements NavigationMenuItemContainer {

    private static final long serialVersionUID = 5453628443565248832L;

    @Getter
    @Setter
    private List<NavigationMenuItem> children = new ArrayList<>();

    @Getter
    @Setter
    private String labelKey;

    @Getter
    @Setter
    private String labelValue;

    /**
     * @param order
     */
    public NavigationMenuItemContainerImpl(Integer order) {
        super(order);
    }

    @Override
    public String getResolvedLabel() {
        return LabelResolver.builder().withLabelKey(labelKey).withLabelValue(labelValue).build()
                .resolve(FacesContext.getCurrentInstance());
    }
}
