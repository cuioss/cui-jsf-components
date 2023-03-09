package com.icw.ehf.cui.core.api.components.model.datalist;

/**
 * Defines the kind of the edit that too places
 *
 * @author Oliver Wolff
 */
public enum EditEvent {

    /**
     * The item was added by the EditableDataListComponent by calling
     * {@link EditableDataListModel#createEmptyItem()}
     */
    ADDED,

    /** Defines an item that was loaded form the BE-system but modified in some way. */
    MODIFIED,

    /** The item is unmarked to be deleted. */
    UNMARK_DELETE,

    /** The item is marked to be deleted. */
    MARK_DELETE
}
