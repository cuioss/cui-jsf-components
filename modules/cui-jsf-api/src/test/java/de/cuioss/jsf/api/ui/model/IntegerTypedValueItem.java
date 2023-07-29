package de.cuioss.jsf.api.ui.model;

@SuppressWarnings("javadoc")

public class IntegerTypedValueItem extends TypedSelectItem<Integer> {

    private static final long serialVersionUID = -1407890244114211617L;

    public IntegerTypedValueItem() {
    }

    public IntegerTypedValueItem(final Integer value) {
        super(value);
    }

    public IntegerTypedValueItem(final Integer value, final String label) {
        super(value, label);
    }
}
