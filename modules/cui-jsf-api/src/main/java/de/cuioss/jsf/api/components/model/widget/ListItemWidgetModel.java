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
package de.cuioss.jsf.api.components.model.widget;

import java.util.List;

import de.cuioss.uimodel.nameprovider.LabeledKey;

/**
 * Display a list of entries as except.
 *
 * @author Matthias Walliczek
 */
public interface ListItemWidgetModel extends WidgetModel {

    /**
     * @return the list of items to display.
     */
    List<? extends ListItem> getItems();

    /**
     * @return the message string to display in case item list is empty.
     */
    LabeledKey getNoItemsMessage();

    /**
     * @return true if a show more button should be rendered.
     */
    boolean isRenderShowMoreButton();
}
