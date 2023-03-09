package com.icw.ehf.cui.core.api.components.model.datalist.impl;

import static com.icw.ehf.cui.core.api.components.model.datalist.AddStatus.ADDED;
import static com.icw.ehf.cui.core.api.components.model.datalist.AddStatus.CREATED;
import static com.icw.ehf.cui.core.api.components.model.datalist.AddStatus.PERSISTED;
import static de.cuioss.test.generator.Generators.integers;
import static de.cuioss.test.generator.Generators.nonEmptyStrings;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.icw.ehf.cui.core.api.components.model.datalist.EditStatus;
import com.icw.ehf.cui.core.api.components.model.datalist.ItemWrapper;

import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.test.valueobjects.ValueObjectTest;
import de.cuioss.test.valueobjects.api.contracts.VerifyConstructor;
import de.cuioss.test.valueobjects.api.object.ObjectTestConfig;

@VerifyConstructor(of = { "wrapped", "editStatus" }, required = "editStatus")
@ObjectTestConfig(equalsAndHashCodeOf = { "wrapped", "editStatus" })
class ItemWrapperImplTest extends ValueObjectTest<ItemWrapperImpl<String>> {

    private final TypedGenerator<String> strings = nonEmptyStrings();

    @Test
    void shouldHandleEmptyConstructor() {
        final ItemWrapper<String> wrapper = new ItemWrapperImpl<>();
        assertNull(wrapper.getWrapped());
        assertEquals(EditStatus.ADDED, wrapper.getEditStatus());
        assertEquals(CREATED, wrapper.getAddStatus());
        assertTrue(wrapper.isMarkedAsAdd());
    }

    @Test
    void shouldHandleInitalConstructor() {
        final var next = strings.next();
        final ItemWrapper<String> wrapper = new ItemWrapperImpl<>(next);
        assertEquals(next, wrapper.getWrapped());
        assertEquals(EditStatus.INITIAL, wrapper.getEditStatus());
        assertEquals(PERSISTED, wrapper.getAddStatus());
        assertFalse(wrapper.isMarkedAsAdd());
    }

    @Test
    void initialShouldHandleSaveAndModified() {
        final var model = anyModel();
        final ItemWrapper<SomeModel> wrapper = new ItemWrapperImpl<>(model);
        wrapper.doEdit(new SomeModel(model));
        wrapper.doSave();
        assertEquals(EditStatus.INITIAL, wrapper.getEditStatus());
        assertEquals(model, wrapper.getWrapped());
        wrapper.doEdit(new SomeModel(model));
        wrapper.getWrapped().setName(strings.next());
        wrapper.doSave();
        assertEquals(EditStatus.MODIFIED, wrapper.getEditStatus());
        assertNotEquals(model, wrapper.getWrapped());
        assertEquals(PERSISTED, wrapper.getAddStatus());
    }

    @Test
    void initialShouldHandleCancel() {
        final var model = anyModel();
        final ItemWrapper<SomeModel> wrapper = new ItemWrapperImpl<>(model);
        final var copy = new SomeModel(model);
        wrapper.doEdit(copy);
        wrapper.doCancel();
        assertEquals(EditStatus.INITIAL, wrapper.getEditStatus());
        assertEquals(model, wrapper.getWrapped());
        wrapper.doEdit(copy);
        wrapper.getWrapped().setName(strings.next());
        wrapper.doCancel();
        assertEquals(EditStatus.INITIAL, wrapper.getEditStatus());
        assertEquals(model, wrapper.getWrapped());
        assertEquals(PERSISTED, wrapper.getAddStatus());
    }

    @Test
    void addModeShouldHandleSaveAndModified() {
        final ItemWrapper<SomeModel> wrapper = new ItemWrapperImpl<>();
        wrapper.doEdit(new SomeModel());
        wrapper.doSave();
        assertEquals(EditStatus.ADDED, wrapper.getEditStatus());
        assertEquals(new SomeModel(), wrapper.getWrapped());
        assertTrue(wrapper.isMarkedAsAdd());
        wrapper.doEdit(new SomeModel());
        wrapper.getWrapped().setName(strings.next());
        wrapper.doSave();
        assertEquals(EditStatus.ADDED, wrapper.getEditStatus());
        assertNotEquals(new SomeModel(), wrapper.getWrapped());
        assertEquals(ADDED, wrapper.getAddStatus());
        assertTrue(wrapper.isMarkedAsAdd());
    }

    @Test
    void addModeShouldHandleCancel() {
        final ItemWrapper<SomeModel> wrapper = new ItemWrapperImpl<>();
        wrapper.doEdit(new SomeModel());
        wrapper.doCancel();
        assertEquals(EditStatus.ADDED, wrapper.getEditStatus());
        assertNull(wrapper.getWrapped());
        wrapper.doEdit(new SomeModel());
        wrapper.getWrapped().setName(strings.next());
        wrapper.doCancel();
        assertEquals(EditStatus.ADDED, wrapper.getEditStatus());
        assertNull(wrapper.getWrapped());
        assertEquals(CREATED, wrapper.getAddStatus());
        assertTrue(wrapper.isMarkedAsAdd());
    }

    @Test
    void shouldFailOnEditingDeleted() {
        final ItemWrapper<SomeModel> wrapper = new ItemWrapperImpl<>();
        wrapper.setEditStatus(EditStatus.DELETED);
        assertThrows(IllegalStateException.class, () -> {
            wrapper.doEdit(anyModel());
        });
    }

    @Test
    void shouldFailOnEditingWithoutPriorReset() {
        final ItemWrapper<SomeModel> wrapper = new ItemWrapperImpl<>();
        wrapper.setEditStatus(EditStatus.EDIT);
        wrapper.doEdit(anyModel());
        assertThrows(IllegalStateException.class, () -> {
            wrapper.doSave();
        });
    }

    private SomeModel anyModel() {
        return new SomeModel(strings.next(), integers().next());
    }
}
