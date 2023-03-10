package de.cuioss.jsf.api.components.model.datalist.impl;

import static de.cuioss.test.generator.Generators.integers;
import static de.cuioss.test.generator.Generators.letterStrings;

import java.util.ArrayList;
import java.util.List;

import de.cuioss.jsf.api.components.model.datalist.EditEvent;
import lombok.Getter;

@SuppressWarnings("javadoc")
public class SomeModelEditableDataListModel extends AbstractEditableDataListModel<SomeModel> {

    private static final long serialVersionUID = 1781925834449349626L;

    @Getter
    private SomeModel oldValue;

    @Getter
    private SomeModel newValue;

    @Getter
    private EditEvent editEvent;

    @Getter
    private final int count = integers(1, 10).next();

    @Getter
    private final List<SomeModel> loadedItems;

    public SomeModelEditableDataListModel() {
        loadedItems = new ArrayList<>();
        final var strings = letterStrings(1, 64);
        for (var i = 0; i < count; i++) {
            loadedItems.add(new SomeModel(strings.next(), integers().next()));
        }
    }

    @Override
    public SomeModel createEmptyItem() {
        return new SomeModel();
    }

    @Override
    public SomeModel createCopy(final SomeModel item) {
        return new SomeModel(item);
    }

    public void resetEventData() {
        oldValue = null;
        newValue = null;
        editEvent = null;
    }

    @Override
    public void elementModified(final EditEvent editEvent, final SomeModel oldValue, final SomeModel newValue) {
        this.editEvent = editEvent;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

}
