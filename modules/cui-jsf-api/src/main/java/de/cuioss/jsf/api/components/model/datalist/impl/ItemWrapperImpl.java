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

import static de.cuioss.tools.base.Preconditions.checkState;
import static java.util.Objects.requireNonNull;

import java.io.Serializable;

import de.cuioss.jsf.api.components.model.datalist.AddStatus;
import de.cuioss.jsf.api.components.model.datalist.EditStatus;
import de.cuioss.jsf.api.components.model.datalist.ItemWrapper;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @param <T> identifying the type of items to be created. Must be at least
 *            {@link Serializable} and implement {@link Object#hashCode()} and
 *            {@link Object#equals(Object)} correctly.
 *
 * @author Oliver Wolff
 */
@EqualsAndHashCode(of = { "wrapped", "editStatus" })
@ToString(of = { "wrapped", "editStatus" })
public class ItemWrapperImpl<T extends Serializable> implements ItemWrapper<T> {

    private static final long serialVersionUID = 7020783590024372831L;

    @Getter
    private T wrapped;

    /**
     * Used for tracking the initially wrapped item.
     */
    @Getter
    private T initialWrapped;

    @Getter
    @Setter
    private EditStatus editStatus;

    @Getter
    private AddStatus addStatus;

    private EditStatus initalEditStatus;

    @Getter
    @Setter
    private int listIndex;

    /**
     * @param wrapped    may be null
     * @param editStatus must not be null.
     */
    public ItemWrapperImpl(final T wrapped, final EditStatus editStatus) {
        requireNonNull(editStatus);
        this.wrapped = wrapped;
        this.editStatus = editStatus;
        addStatus = EditStatus.ADDED.equals(editStatus) ? AddStatus.CREATED : AddStatus.PERSISTED;
    }

    /**
     * Shortcut for using {@link #ItemWrapperImpl(Serializable, EditStatus)} with
     * {@link EditStatus#INITIAL}
     *
     * @param wrapped may be null, although this does not make sense
     */
    public ItemWrapperImpl(final T wrapped) {
        this(wrapped, EditStatus.INITIAL);
    }

    /**
     * * Shortcut for using {@link #ItemWrapperImpl(Serializable, EditStatus)} with
     * {@link EditStatus#ADDED} and the wrapped object being {@code null}
     */
    public ItemWrapperImpl() {
        this(null, EditStatus.ADDED);
    }

    @Override
    public void doSave() {
        requireNonNull(wrapped, "Invalid usage: wrapped should not be null");
        switch (initalEditStatus) {
        case ADDED:
            editStatus = EditStatus.ADDED;
            addStatus = AddStatus.ADDED;
            break;
        case EDIT:
            throw new IllegalStateException("Invalid usage: You must not doCancel or doSave prior to doEdit");
        default:
            editStatus = wrapped.equals(initialWrapped) ? EditStatus.INITIAL : EditStatus.MODIFIED;
            break;
        }
        initialWrapped = null;
    }

    @Override
    public void doCancel() {
        wrapped = initialWrapped;
        editStatus = initalEditStatus;
    }

    @Override
    public void markForDelete() {
        initalEditStatus = editStatus;
        editStatus = EditStatus.DELETED;
    }

    @Override
    public void undoMarkForDelete() {
        editStatus = initalEditStatus;
    }

    @Override
    public void doEdit(final T copyToEdit) {
        requireNonNull(copyToEdit);
        checkState(!EditStatus.DELETED.equals(getEditStatus()), "Invalid usage: deleted elements can not be edited.");
        initialWrapped = wrapped;
        wrapped = copyToEdit;
        initalEditStatus = editStatus;
        editStatus = EditStatus.EDIT;
    }

    @Override
    public boolean isEditMode() {
        return EditStatus.EDIT.equals(getEditStatus());
    }

    @Override
    public boolean isMarkedForDelete() {
        return EditStatus.DELETED.equals(getEditStatus());
    }

    @Override
    public boolean isMarkedAsAdd() {
        return !AddStatus.PERSISTED.equals(getAddStatus());
    }

    @Override
    public boolean isModifiedItem() {
        return EditStatus.MODIFIED.equals(getEditStatus());
    }
}
