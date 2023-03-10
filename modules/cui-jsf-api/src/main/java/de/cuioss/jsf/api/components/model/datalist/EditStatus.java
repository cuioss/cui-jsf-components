package de.cuioss.jsf.api.components.model.datalist;

/**
 * Defines the current status of editing for that precise {@link Object}. Is is always used in
 * conjunction with {@link ItemWrapper}
 *
 * @author Oliver Wolff
 */
public enum EditStatus {

    /** The initially loaded, unmodified item */
    INITIAL,

    /** The item is currently being edited. */
    EDIT,

    /**
     * The item was added by the EditableDataListComponent by calling
     * {@link EditableDataListModel#createEmptyItem()}
     */
    ADDED,

    /** Defines an item that was loaded from the BE-system but modified in some way. */
    MODIFIED,

    /** The item is marked to be deleted. */
    DELETED
}
