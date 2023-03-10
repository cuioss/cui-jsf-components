package de.cuioss.jsf.bootstrap.support;

import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.uimodel.model.code.CodeType;
import de.cuioss.uimodel.model.code.CodeTypeImpl;

@SuppressWarnings("javadoc")
public class CodeTypeGenerator implements TypedGenerator<CodeType> {

    private final TypedGenerator<String> strings = Generators.letterStrings(1, 10);

    @Override
    public CodeType next() {
        return new CodeTypeImpl(strings.next());
    }

    @Override
    public Class<CodeType> getType() {
        return CodeType.class;
    }

}
