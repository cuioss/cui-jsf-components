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
package de.cuioss.jsf.components.selection;

import de.cuioss.test.jsf.converter.AbstractConverterTest;
import de.cuioss.test.jsf.converter.TestItems;
import de.cuioss.tools.collect.CollectionBuilder;
import jakarta.faces.model.SelectItem;
import lombok.AccessLevel;
import lombok.Getter;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

class EnumSelectMenuModelTest extends AbstractConverterTest<EnumSelectMenuModel<TestEnumeration>, TestEnumeration> {

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
        List<SelectItem> values = builder.toMutableList();
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
