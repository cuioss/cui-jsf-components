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
package de.cuioss.jsf.api.components.model.datalist.impl;

import static de.cuioss.jsf.api.components.model.datalist.AddStatus.PERSISTED;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import de.cuioss.jsf.api.components.model.datalist.EditEvent;
import de.cuioss.jsf.api.components.model.datalist.EditStatus;
import de.cuioss.jsf.api.components.model.datalist.EditableDataListModel;
import de.cuioss.jsf.api.components.model.datalist.ItemWrapper;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Base class for implementing {@link EditableDataListModel}, should be the
 * usual starting point for concrete implementations.
 *
 * @author Oliver Wolff
 * @param <T> identifying the type of items to be created. Must be at least
 *            {@link Serializable} and implement {@link Object#hashCode()} and
 *            {@link Object#equals(Object)} correctly.
 */
@ToString(doNotUseGetters = true)
@EqualsAndHashCode(doNotUseGetters = true)
public abstract class AbstractEditableDataListModel<T extends Serializable> implements EditableDataListModel<T> {

    private static final long serialVersionUID = 2719782028119450821L;
    private List<ItemWrapper<T>> displayItems;

    @Override
    public List<ItemWrapper<T>> getDisplayItems() {
        if (null == displayItems) {
            initDisplayItems();
        }
        return displayItems;
    }

    @Override
    public void editItem(final ItemWrapper<T> item) {
        cancelAllEditItems();
        item.doEdit(createCopy(item.getWrapped()));
    }

    @Override
    public ItemWrapper<T> addItem() {
        final var newWrapper = getNewItemWrapper();
        cancelAllEditItems();
        newWrapper.doEdit(createEmptyItem());
        return addNewDisplayItem(newWrapper);
    }

    /**
     * Adds the item to the model while keeping the indexes intact. The status of
     * the added item is {@linkplain EditStatus#ADDED}.
     *
     * @param item to be added to the model
     * @return added display item ({@link ItemWrapperImpl})
     */
    public ItemWrapper<T> addItem(final T item) {
        return addNewDisplayItem(new ItemWrapperImpl<>(item, EditStatus.ADDED));
    }

    private ItemWrapper<T> addNewDisplayItem(final ItemWrapper<T> newWrapper) {
        if (isNewCreatedItemShouldBeAddedAsFirst()) {
            getDisplayItems().add(0, newWrapper);
        } else {
            getDisplayItems().add(newWrapper);
        }
        handleListIndices();
        return newWrapper;
    }

    @Override
    public void cancelEditItem(final ItemWrapper<T> item) {
        item.doCancel();
        if (!PERSISTED.equals(item.getAddStatus()) && null == item.getInitialWrapped()) {
            getDisplayItems().remove(item.getListIndex());
            handleListIndices();
        }
    }

    @Override
    public void saveEditItem(final ItemWrapper<T> item) {
        final var oldValue = item.getInitialWrapped();
        item.doSave();
        final var newValue = item.getWrapped();

        if (null != oldValue && !oldValue.equals(newValue)) {
            elementModified(EditEvent.MODIFIED, oldValue, newValue);
        } else if (null == oldValue && null != newValue) {
            elementModified(EditEvent.ADDED, null, newValue);
        }
    }

    @Override
    public void markForDelete(final ItemWrapper<T> item) {
        if (!PERSISTED.equals(item.getAddStatus())) {
            getDisplayItems().remove(item.getListIndex());
            handleListIndices();
        } else {
            item.markForDelete();
        }
        elementModified(EditEvent.MARK_DELETE, item.getWrapped(), null);
    }

    @Override
    public void undoMarkForDelete(final ItemWrapper<T> item) {
        item.undoMarkForDelete();
        elementModified(EditEvent.UNMARK_DELETE, null, item.getWrapped());
    }

    protected void cancelAllEditItems() {
        getDisplayItems().stream().filter(ItemWrapper::isEditMode).forEach(ItemWrapper::doCancel);
        removeNullItems();
    }

    /**
     * This removes new items that were in edit mode and have been canceled.
     */
    private void removeNullItems() {
        getDisplayItems().removeIf(item -> !PERSISTED.equals(item.getAddStatus()) && null == item.getWrapped());
    }

    @Override
    public boolean isEveryItemSavedOrCanceled() {
        for (final ItemWrapper<T> item : getDisplayItems()) {
            if (item.isEditMode()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<T> getDeletedItems() {
        return getDisplayItems().stream()
                .filter(item -> item.isMarkedForDelete() && PERSISTED.equals(item.getAddStatus()))
                .map(ItemWrapper::getWrapped).toList();
    }

    @Override
    public List<T> getResultItems() {
        return getDisplayItems().stream().filter(item -> !item.isMarkedForDelete()).map(ItemWrapper::getWrapped)
                .toList();
    }

    @Override
    public boolean hasChanges() {
        if (!getDeletedItems().isEmpty()) {
            return true;
        }
        final var loaded = getLoadedItems();
        final var current = getResultItems();
        if (loaded.size() != current.size()) {
            return true;
        }

        return !new HashSet<>(loaded).containsAll(current) || !new HashSet<>(current).containsAll(loaded);
    }

    private void handleListIndices() {
        var index = 0;
        for (final ItemWrapper<T> item : getDisplayItems()) {
            item.setListIndex(index);
            index++;
        }
    }

    protected ItemWrapper<T> getNewItemWrapper() {
        return new ItemWrapperImpl<>();
    }

    protected ItemWrapper<T> getNewItemWrapper(final T item) {
        return new ItemWrapperImpl<>(item);
    }

    /**
     * Initializes the display items with {@link #getLoadedItems()}.
     */
    public void initDisplayItems() {

        if (displayItems == null) {
            displayItems = new ArrayList<>(0);
        } else {
            displayItems.clear();
        }

        for (final T item : getLoadedItems()) {
            displayItems.add(getNewItemWrapper(item));
        }

        handleListIndices();
    }
}
