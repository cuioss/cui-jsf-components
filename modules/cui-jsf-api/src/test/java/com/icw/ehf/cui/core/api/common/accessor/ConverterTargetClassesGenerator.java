package com.icw.ehf.cui.core.api.common.accessor;

import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.test.jsf.generator.JsfProvidedConverter;

@SuppressWarnings({ "javadoc", "rawtypes" })
public class ConverterTargetClassesGenerator implements TypedGenerator<Class> {

    @Override
    public Class<?> next() {
        return JsfProvidedConverter.TARGET_TYPE_GENERATOR.next();
    }

    @Override
    public Class<Class> getType() {
        return Class.class;
    }

}
