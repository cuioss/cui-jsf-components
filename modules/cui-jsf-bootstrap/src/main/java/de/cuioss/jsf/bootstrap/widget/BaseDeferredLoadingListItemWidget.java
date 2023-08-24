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
package de.cuioss.jsf.bootstrap.widget;

import java.util.ArrayList;
import java.util.List;

import de.cuioss.jsf.api.components.model.widget.BaseDeferredLoadingWidget;
import de.cuioss.jsf.api.components.model.widget.ListItem;
import de.cuioss.jsf.api.components.model.widget.ListItemWidgetModel;
import de.cuioss.tools.string.MoreStrings;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Abstract extension of the {@link BaseDeferredLoadingWidget} for list items,
 * presenting the listItemWidget composite component to be rendered.
 *
 * @author Matthias Walliczek
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public abstract class BaseDeferredLoadingListItemWidget extends BaseDeferredLoadingWidget<ArrayList<ListItem>>
        implements ListItemWidgetModel {

    private static final long serialVersionUID = 8583462862065649466L;

    @Override
    public List<ListItem> getItems() {
        return getContent();
    }

    /**
     * @return the id of the in this module defined composite component that should
     *         be used as default for this implementations of this abstract widget
     *         class. May be overridden by a different id of a more specific
     *         composite component.
     */
    @Override
    public String getCompositeComponentId() {
        return "cui-composite:listItemWidget";
    }

    @Override
    public boolean isRenderShowMoreButton() {
        return !MoreStrings.isEmpty(getCoreAction()) && null != getItems() && !getItems().isEmpty();
    }
}
