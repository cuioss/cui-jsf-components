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
package de.cuioss.jsf.bootstrap.composite;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.cuioss.jsf.api.components.model.datalist.EditableDataListModel;
import de.cuioss.jsf.api.components.model.datalist.ItemWrapper;
import de.cuioss.jsf.api.components.model.datalist.impl.ItemWrapperImpl;
import de.cuioss.test.generator.Generators;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("javadoc")
public class MockEditableDataListModel implements EditableDataListModel<Serializable> {

    private static final long serialVersionUID = -5295825178881746472L;

    @Getter
    @Setter
    private List<Serializable> loadedItems = new ArrayList<>();

    @Getter
    @Setter
    private List<Serializable> deletedItems = new ArrayList<>();

    @Getter
    @Setter
    private List<Serializable> resultItems = new ArrayList<>();

    @Getter
    @Setter
    private List<ItemWrapper<Serializable>> displayItems = new ArrayList<>();

    @Getter
    @Setter
    private boolean everyItemSavedOrCanceled = true;

    @Override
    public Serializable createEmptyItem() {
        return null;
    }

    @Override
    public Serializable createCopy(final Serializable item) {
        return null;
    }

    @Override
    public void editItem(final ItemWrapper<Serializable> item) {
    }

    @Override
    public void cancelEditItem(final ItemWrapper<Serializable> item) {
    }

    @Override
    public void saveEditItem(final ItemWrapper<Serializable> item) {
    }

    /**
     * Add new item with non empty string.
     *
     * @return added item of type ItemWrapperImpl
     */
    @Override
    public ItemWrapper<Serializable> addItem() {
        final var item = new ItemWrapperImpl<Serializable>(Generators.nonEmptyStrings().next());
        displayItems.add(item);
        return item;
    }

    @Override
    public void markForDelete(final ItemWrapper<Serializable> item) {
    }

    @Override
    public void undoMarkForDelete(final ItemWrapper<Serializable> item) {
    }

    @Override
    public boolean hasChanges() {
        return false;
    }
}
