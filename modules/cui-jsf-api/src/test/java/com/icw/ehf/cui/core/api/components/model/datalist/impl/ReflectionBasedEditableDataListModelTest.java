package com.icw.ehf.cui.core.api.components.model.datalist.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.icw.ehf.cui.core.api.components.model.datalist.impl.support.ExplodingModel;
import com.icw.ehf.cui.core.api.components.model.datalist.impl.support.MissingCopyConstructor;
import com.icw.ehf.cui.core.api.components.model.datalist.impl.support.MissingDefaultConstructor;

class ReflectionBasedEditableDataListModelTest {

    @Test
    void shouldHandleGoodCase() {
        var model =
            new ReflectionBasedEditableDataListModel<>(SomeModel.class, null);
        assertNotNull(model.createEmptyItem());
        assertNotNull(model.createCopy(new SomeModel()));
    }

    @Test
    void shouldHandleMissingDefaultConstructor() {
        var model =
            new ReflectionBasedEditableDataListModel<>(MissingDefaultConstructor.class, null);
        assertThrows(IllegalStateException.class, () -> {
            model.createEmptyItem();

        });
    }

    @Test
    void shouldHandleMissingCopyConstructor() {
        var model =
            new ReflectionBasedEditableDataListModel<>(MissingCopyConstructor.class, null);
        assertThrows(IllegalStateException.class, () -> {
            model.createCopy(new MissingCopyConstructor(""));
        });
    }

    @Test
    void shouldHandleExplodingCopyConstructor() {
        var model =
            new ReflectionBasedEditableDataListModel<>(ExplodingModel.class, null);
        assertThrows(IllegalStateException.class, () -> {
            model.createCopy(new ExplodingModel(""));
        });
    }

    @Test
    void shouldHandleExplodingDefaultConstructor() {
        var model =
            new ReflectionBasedEditableDataListModel<>(ExplodingModel.class, new ArrayList<>());
        assertThrows(IllegalStateException.class, () -> {
            model.createEmptyItem();
        });
    }
}
