package de.cuioss.jsf.api.validator;

import de.cuioss.jsf.api.CoreJsfTestConfiguration;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.validator.AbstractValidatorTest;
import de.cuioss.test.jsf.validator.TestItems;

@JsfTestConfiguration(CoreJsfTestConfiguration.class)
class MockValidatorTest extends AbstractValidatorTest<MockValidator, String> {

    @Override
    public void populate(final TestItems<String> testItems) {
        testItems.addInvalid(MockValidator.ERROR_KEY).addValid("valid");
    }

}
