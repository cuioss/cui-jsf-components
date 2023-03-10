package de.cuioss.jsf.components.selection;

import java.util.HashMap;
import java.util.Map;

import de.cuioss.test.jsf.converter.AbstractConverterTest;
import de.cuioss.test.jsf.converter.TestItems;

class MapInstanceConverterTest extends
        AbstractConverterTest<MapInstanceConverter<String, String>, String> {

    @Override
    public void configure(final MapInstanceConverter<String, String> toBeConfigured) {
        Map<String, String> instanceMap = new HashMap<>();
        instanceMap.put("ONE", "eins");
        instanceMap.put("TWO", "zwei");
        toBeConfigured.setInstanceMap(instanceMap);
    }

    @Override
    public void populate(final TestItems<String> testItems) {
        testItems.addValidString("ONE");
        testItems.addValidString("TWO");
    }

}
