package com.icw.ehf.cui.components.bootstrap.icon;

import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.uimodel.model.Gender;

@SuppressWarnings("javadoc")
public class GenderGenerator implements TypedGenerator<Gender> {

    private final TypedGenerator<Gender> generator = Generators.enumValues(Gender.class);

    @Override
    public Gender next() {
        return generator.next();
    }

    @Override
    public Class<Gender> getType() {
        return Gender.class;
    }

}
