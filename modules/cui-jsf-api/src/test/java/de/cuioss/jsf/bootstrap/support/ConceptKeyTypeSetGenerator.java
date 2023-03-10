package de.cuioss.jsf.bootstrap.support;

import static de.cuioss.tools.collect.CollectionLiterals.mutableSet;

import java.util.Set;

import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.uimodel.model.conceptkey.ConceptKeyType;

@SuppressWarnings({ "rawtypes", "javadoc" })
public class ConceptKeyTypeSetGenerator implements TypedGenerator<Set> {

    private final ConceptKeyTypeGenerator codeType = new ConceptKeyTypeGenerator();

    private final TypedGenerator<Integer> numbers = Generators.integers(0, 25);

    private final TypedGenerator<Integer> positiveNumbers = Generators.integers(1, 25);

    @Override
    public Set<ConceptKeyType> next() {
        final Set<ConceptKeyType> types = mutableSet();
        final int number = numbers.next();
        for (var i = 0; i < number; i++) {
            types.add(codeType.next());

        }
        return types;
    }

    /**
     * @return a the set that is not empty.
     */
    public Set<ConceptKeyType> nextNotEmpty() {
        final Set<ConceptKeyType> types = mutableSet();
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
