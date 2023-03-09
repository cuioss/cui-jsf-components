package com.icw.ehf.cui.components.bootstrap.icon;

import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.TypedGenerator;

/**
 * @author Oliver Wolff
 */
public class MimeTypeIconGenerator implements TypedGenerator<MimeTypeIcon> {

    private final TypedGenerator<MimeTypeIcon> generator = Generators.enumValues(MimeTypeIcon.class);

    @Override
    public MimeTypeIcon next() {
        return generator.next();
    }

    @Override
    public Class<MimeTypeIcon> getType() {
        return MimeTypeIcon.class;
    }

}
