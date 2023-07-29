package de.cuioss.jsf.components.selection;

import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.uimodel.model.conceptkey.AugmentationKeyConstans;
import de.cuioss.uimodel.model.conceptkey.ConceptKeyType;
import de.cuioss.uimodel.model.conceptkey.impl.BaseConceptCategory;
import de.cuioss.uimodel.model.conceptkey.impl.ConceptKeyTypeImpl;
import de.cuioss.uimodel.nameprovider.I18nDisplayNameProvider;

@SuppressWarnings("javadoc")
public class ConceptKeyTypeGenerator implements TypedGenerator<ConceptKeyType> {

    public static final ConceptKeyType TEST_CODE = ConceptKeyTypeImpl.builder().identifier("test")
            .labelResolver(new I18nDisplayNameProvider("test-resolved")).category(new BaseConceptCategory()).build();

    public static final ConceptKeyType TEST_CODE2 = ConceptKeyTypeImpl.builder().identifier("test2")
            .labelResolver(new I18nDisplayNameProvider("test-resolved2")).category(new BaseConceptCategory()).build();

    public static final ConceptKeyType TEST_DEFAULT_CODE = ConceptKeyTypeImpl.builder().identifier("default_code")
            .labelResolver(new I18nDisplayNameProvider("default_code-resolved"))
            .augmentation(AugmentationKeyConstans.DEFAULT_VALUE, "true").category(new BaseConceptCategory()).build();

    private final TypedGenerator<String> strings = Generators.letterStrings(1, 5);

    @Override
    public ConceptKeyType next() {
        return ConceptKeyTypeImpl.builder().identifier(strings.next())
                .labelResolver(new I18nDisplayNameProvider(strings.next())).category(new BaseConceptCategory()).build();
    }

    @Override
    public Class<ConceptKeyType> getType() {
        return ConceptKeyType.class;
    }

}
