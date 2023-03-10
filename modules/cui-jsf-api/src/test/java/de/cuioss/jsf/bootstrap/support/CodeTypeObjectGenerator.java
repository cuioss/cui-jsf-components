package de.cuioss.jsf.bootstrap.support;

import de.cuioss.test.generator.TypedGenerator;

/**
 * {@link CodeTypeGenerator} that registers for {@link Object} as target.
 *
 * @author Oliver Wolff
 */
public class CodeTypeObjectGenerator implements TypedGenerator<Object> {

    private final CodeTypeGenerator codeTypeGenerator = new CodeTypeGenerator();

    @Override
    public Object next() {
        return codeTypeGenerator.next();
    }

    @Override
    public Class<Object> getType() {
        return Object.class;
    }

}
