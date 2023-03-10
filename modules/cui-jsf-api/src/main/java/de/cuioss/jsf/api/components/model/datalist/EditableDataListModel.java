package de.cuioss.jsf.api.components.model.datalist;

import java.io.Serializable;
import java.util.List;

/**
 * Helper class used for managing the objects used by EditableDataListComponent
 *
 * @author Matthias Walliczek
 * @param <T> identifying the type of items to be created. Must be at least {@link Serializable} and
 *            implement {@link Object#hashCode()} and {@link Object#equals(Object)} correctly.
 */
public interface EditableDataListModel<T extends Serializable> extends Serializable {

    /**
     * Create an empty item to be used for adding a new element.
     *
     * @return the item to be created. Must never be {@code null}
     */
    T createEmptyItem();

    /**
     * Creates a copy for the given item
     *
     * @param item to be copied. Must never be {@code null}
     * @return a new instance of the object. Must never be {@code null}
     */
    T createCopy(T item);

    /**
     * @return all items that are initially loaded. May be empty, but never {@code null}
     */
    List<T> getLoadedItems();

    /**
     * @return all items that are to be displayed in the editable list, saying
     *         {@link ItemWrapper#getEditStatus()}. May be empty,
     *         but never {@code null}
     */
    List<ItemWrapper<T>> getDisplayItems();

    /**
     * @return all items that are deleted from the editable list, saying
     *         {@link ItemWrapper#getEditStatus()} is {@link EditStatus#DELETED}. May be empty,
     *         but never {@code null}
     */
    List<T> getDeletedItems();

    /**
     * @return While {@link #getDisplayItems()} returns all items including the ones marked as
     *         {@link EditStatus#DELETED} this method filters them, saying it contains all elements
     *         of {@link #getDisplayItems()} minus the one derived from {@link #getDeletedItems()}
     */
    List<T> getResultItems();

    /**
     * Close all other opened items and set the given one to edit mode
     *
     * @param item to be edited
     */
    void editItem(ItemWrapper<T> item);

    /**
     * Cancels the editing of the given item. If it was added using {@link #addItem()} it
     * will removed from the {@link #getDisplayItems()} as well.
     *
     * @param item to be edited
     */
    void cancelEditItem(ItemWrapper<T> item);

    /**
     * Saves the editing of the given item to the local model.
     *
     * @param item to be edited
     */
    void saveEditItem(ItemWrapper<T> item);

    /**
     * Adds an item and puts it in edit mode.
     *
     * @return the newly created item
     */
    ItemWrapper<T> addItem();

    /**
     * Removes the given item. In case the item was added using {@link #addItem()} it
     * will simply be removed, in all other cases it will be added to the delete list, see
     * {@link #getDeletedItems()}
     *
     * @param item
     *            to be marked for delete
     */
    void markForDelete(ItemWrapper<T> item);

    /**
     * Undo a previous {@link #markForDelete(ItemWrapper)} call
     *
     * @param item
     *            to be unmarked for delete
     */
    void undoMarkForDelete(ItemWrapper<T> item);

    /**
     * @return boolean indicating whether there is at least one item still in
     *         {@link ItemWrapper#isEditMode()} {@code false}, else {@code true}
     */
    boolean isEveryItemSavedOrCanceled();

    /**
     * @return boolean indicating whether the model has changes compared do its initial state
     */
    boolean hasChanges();

    /**
     * Callback interface for reacting on model changes, In order to detect modification
     * {@link Object#equals(Object)} will be used.
     *
     * @param editEvent identifying what kind of change took place, one of {@link EditEvent#ADDED},
     *            {@link EditEvent#MARK_DELETE}, {@link EditEvent#MODIFIED},
     *            {@link EditEvent#UNMARK_DELETE}
     * @param oldValue the value prior to the modification may be null in cases of
     *            {@link EditEvent#ADDED}, {@link EditEvent#UNMARK_DELETE}
     * @param newValue the value after modification, may be null in case of
     *            {@link EditEvent#MARK_DELETE}
     */
    default void elementModified(final EditEvent editEvent, final T oldValue, final T newValue) {

    }

    /**
     * @return {@code true} if new created item should be added as first element.
     */
    default boolean isNewCreatedItemShouldBeAddedAsFirst() {
        return true;
    }

    /**
     * Checks if an element should be handled read only (display no edit or delete button).
     *
     * @param element the element to check.
     * @return true if the element should be handled read only.
     */
    default boolean isReadOnly(T element) {
        return false;
    }


    /**
     * @return boolean indication whether the current item can be deleted.
     */
    default boolean isDeletable(T element) {
        return true;
    }
}
