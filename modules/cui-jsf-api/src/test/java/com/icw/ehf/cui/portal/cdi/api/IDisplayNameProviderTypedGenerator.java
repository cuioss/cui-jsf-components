package com.icw.ehf.cui.portal.cdi.api;

import static de.cuioss.test.generator.Generators.nonEmptyStrings;

import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.uimodel.nameprovider.DisplayName;
import de.cuioss.uimodel.nameprovider.IDisplayNameProvider;

/**
 * Typed generator for {@link IDisplayNameProvider}
 */
@SuppressWarnings("rawtypes")
public class IDisplayNameProviderTypedGenerator implements TypedGenerator<IDisplayNameProvider> {

    @Override
    public Class<IDisplayNameProvider> getType() {
        return IDisplayNameProvider.class;
    }

    @Override
    public IDisplayNameProvider next() {
        return new DisplayName(nonEmptyStrings().next());
    }
}
