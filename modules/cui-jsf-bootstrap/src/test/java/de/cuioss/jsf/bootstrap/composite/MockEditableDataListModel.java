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
