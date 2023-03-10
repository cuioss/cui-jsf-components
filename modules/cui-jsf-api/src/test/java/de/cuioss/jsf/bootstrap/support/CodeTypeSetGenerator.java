package de.cuioss.jsf.bootstrap.support;

import static de.cuioss.tools.collect.CollectionLiterals.mutableSet;

import java.util.Set;

import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.uimodel.model.code.CodeType;

@SuppressWarnings({ "rawtypes", "javadoc" })
public class CodeTypeSetGenerator implements TypedGenerator<Set> {

    private final CodeTypeGenerator codeType = new CodeTypeGenerator();

    private final TypedGenerator<Integer> numbers = Generators.integers(0, 25);

    private final TypedGenerator<Integer> positiveNumbers = Generators.integers(1, 25);

    @Override
    public Set next() {
        final Set<CodeType> types = mutableSet();
        final int number = numbers.next();
        for (var i = 0; i < number; i++) {
            types.add(codeType.next());

        }
        return types;
    }

    /**
     * @return a the set that is not empty.
     */
    public Set nextNotEmpty() {
        final Set<CodeType> types = mutableSet();
        final int number = positiveNumbers.next();
        for (var i = 0; i < number; i++) {
            types.add(codeType.next());

        }
        return types;
    }

    @Override
    public Class<Set> getType() {
        return Set.class;
    }

}
