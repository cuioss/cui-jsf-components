package de.cuioss.jsf.api.components.model.datalist;

/**
 * Defines the current status of persisting for that precise new created
 * {@link Object}. Is is always used in conjunction with {@link ItemWrapper}
 *
 * @author Matthias Walliczek
 */
public enum AddStatus {

    /**
     * Initial created, not saved in the list
     */
    CREATED,

    /**
     * Saved in the list, not persisted
     */
    ADDED,

    /**
     * Already persisted
     */
    PERSISTED
}
