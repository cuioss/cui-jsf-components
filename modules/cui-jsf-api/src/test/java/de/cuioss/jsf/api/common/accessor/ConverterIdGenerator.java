package de.cuioss.jsf.api.common.accessor;

import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.test.jsf.generator.JsfProvidedConverter;

@SuppressWarnings("javadoc")
public class ConverterIdGenerator implements TypedGenerator<String> {

    @Override
    public String next() {
        return JsfProvidedConverter.CONVERTER_ID_GENERATOR.next();
    }

    @Override
    public Class<String> getType() {
        return String.class;
    }

}
