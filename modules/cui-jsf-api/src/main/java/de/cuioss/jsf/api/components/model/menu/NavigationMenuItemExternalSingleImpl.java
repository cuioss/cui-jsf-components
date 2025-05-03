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

import de.cuioss.jsf.api.components.support.LabelResolver;
import jakarta.faces.context.FacesContext;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;

/**
 * Basic Implementation of a {@link NavigationMenuItem}
 *
 * @author Matthias Walliczek
 */
@EqualsAndHashCode(callSuper = true)
@ToString
public class NavigationMenuItemExternalSingleImpl extends NavigationMenuItemImpl
        implements NavigationMenuItemExternalSingle {

    @Serial
    private static final long serialVersionUID = -4292374413272966456L;

    @Getter
    @Setter
    private String hRef;

    @Getter
    @Setter
    private String target;

    @Getter
    @Setter
    private String onClickAction;

    @Getter
    @Setter
    private String labelKey;

    @Getter
    @Setter
    private String labelValue;

    /**
     * @param order
     */
    public NavigationMenuItemExternalSingleImpl(Integer order) {
        super(order);
    }

    @Override
    public String getResolvedLabel() {
        return LabelResolver.builder().withLabelKey(labelKey).withLabelValue(labelValue).build()
                .resolve(FacesContext.getCurrentInstance());
    }
}
