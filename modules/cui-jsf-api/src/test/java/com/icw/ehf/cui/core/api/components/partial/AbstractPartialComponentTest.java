package com.icw.ehf.cui.core.api.components.partial;

import com.icw.ehf.cui.core.api.CoreJsfTestConfiguration;

import de.cuioss.test.jsf.component.AbstractComponentTest;
import de.cuioss.test.jsf.config.JsfTestConfiguration;

/**
 * Base class for the concrete tests of the partial components.
 *
 * @author Oliver Wolff
 */
@JsfTestConfiguration(CoreJsfTestConfiguration.class)
public abstract class AbstractPartialComponentTest
        extends AbstractComponentTest<MockPartialComponent> {

    protected static final String MESSAGE_KEY = "some.key";

    protected static final String MESSAGE_VALUE = "some.value";
}
