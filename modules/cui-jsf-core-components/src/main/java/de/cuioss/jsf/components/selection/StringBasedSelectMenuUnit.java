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
package de.cuioss.jsf.components.selection;

import java.util.List;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.model.SelectItem;

/**
 * Composite of {@linkplain AbstractSelectMenuModel} and converter
 */
public class StringBasedSelectMenuUnit extends AbstractSelectMenuModel<String> {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = -6200177576322860329L;

    /**
     * StringBasedSelectMenuUnit Constructor
     *
     * @param selectableValues {@linkplain List} of {@linkplain SelectItem}
     */
    public StringBasedSelectMenuUnit(List<SelectItem> selectableValues) {
        super(selectableValues);
    }

    @Override
    public String convertToObject(FacesContext context, UIComponent component, String value) {
        return value;
    }

    @Override
    public String convertToString(FacesContext context, UIComponent component, String value) {
        return value;
    }

}
