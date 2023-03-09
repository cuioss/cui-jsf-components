package com.icw.ehf.cui.core.api.validator;

import com.icw.ehf.cui.core.api.CoreJsfTestConfiguration;

import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.validator.AbstractValidatorTest;
import de.cuioss.test.jsf.validator.TestItems;

@JsfTestConfiguration(CoreJsfTestConfiguration.class)
class EmailValidatorTest extends AbstractValidatorTest<EmailValidator, String> {

    @Override
    public void populate(final TestItems<String> testItems) {
        testItems.addInvalid("a").addValid("test@icw.de")
                .addValid("7f9a39ff-d8e8-6de3-c81d-77c8c6b20445@example.com")
                .addInvalid("abc").addInvalid("ab-c@de-f@ghi").addValid("ab-c@de-fghi")
                .addValid("abc@def").addValid("123@456").addValid("?%&äö.ü-_@?%&_ä.ö-ü");
    }

}
