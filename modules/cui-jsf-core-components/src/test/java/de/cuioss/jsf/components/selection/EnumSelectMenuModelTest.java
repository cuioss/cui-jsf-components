package de.cuioss.jsf.components.selection;

import java.util.List;

import javax.faces.model.SelectItem;

import org.junit.jupiter.api.BeforeEach;

import de.cuioss.test.jsf.converter.AbstractConverterTest;
import de.cuioss.test.jsf.converter.TestItems;
import de.cuioss.tools.collect.CollectionBuilder;
import lombok.AccessLevel;
import lombok.Getter;

class EnumSelectMenuModelTest extends AbstractConverterTest<EnumSelectMenuModel<TestEnumeration>, TestEnumeration> {

    private List<SelectItem> values;

    @Getter
    private EnumSelectMenuModel<TestEnumeration> converter;

    @Getter(AccessLevel.PROTECTED)
    private TestItems<TestEnumeration> testItems;

    @Override
    @BeforeEach
    public void initConverter() {
        var builder = new CollectionBuilder<SelectItem>();
        for (TestEnumeration enumeration : TestEnumeration.values()) {
            builder.add(new SelectItem(enumeration));
        }
        values = builder.toMutableList();
        converter = new EnumSelectMenuModel<>(values, TestEnumeration.class);
        testItems = new TestItems<>();
        populate(testItems);
    }

    @Override
    public void populate(final TestItems<TestEnumeration> testItems) {
        testItems.addRoundtripValues(TestEnumeration.ONE.toString(), TestEnumeration.TWO.toString(),
                TestEnumeration.THREE.toString()).addInvalidString("notThere");
    }

}

enum TestEnumeration {
    ONE, TWO, THREE
}
