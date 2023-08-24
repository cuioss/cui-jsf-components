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

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Basic Implementation of a {@link NavigationMenuItem}
 *
 * @author Sven Haag
 */
@EqualsAndHashCode(callSuper = true)
@ToString
public class NavigationMenuItemSeparatorImpl extends NavigationMenuItemImpl implements NavigationMenuItemSeparator {

    private static final long serialVersionUID = 2808000723947735968L;

    /**
     * @param order
     */
    public NavigationMenuItemSeparatorImpl(Integer order) {
        super(order);
    }

    /**
     * Factory method
     *
     * @param order for the instance
     *
     * @return provide instance of {@linkplain NavigationMenuItemSeparatorImpl}
     */
    public static NavigationMenuItemSeparator getInstance(Integer order) {
        return new NavigationMenuItemSeparatorImpl(order);
    }
}
