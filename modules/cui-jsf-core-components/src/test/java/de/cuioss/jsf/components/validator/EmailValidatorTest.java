/*
 * Copyright © 2025 CUI-OpenSource-Software (info@cuioss.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.components.validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import de.cuioss.jsf.test.CoreJsfTestConfiguration;
import de.cuioss.jsf.test.EnableJSFCDIEnvironment;
import de.cuioss.jsf.test.EnableResourceBundleSupport;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import de.cuioss.test.jsf.validator.AbstractValidatorTest;
import de.cuioss.test.jsf.validator.TestItems;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.ValidatorException;
import org.jboss.weld.junit5.ExplicitParamInjection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@JsfTestConfiguration(CoreJsfTestConfiguration.class)
@EnableJSFCDIEnvironment
@EnableResourceBundleSupport
@ExplicitParamInjection
@DisplayName("Tests for EmailValidator")
class EmailValidatorTest extends AbstractValidatorTest<EmailValidator, String> {

    @BeforeEach
    void setUp(ComponentConfigDecorator decorator) {
        CoreJsfTestConfiguration.configureComponents(decorator);
    }

    @Override
    @DisplayName("Configure test items for email validation")
    public void populate(final TestItems<String> testItems) {
        // Add valid email addresses
        testItems.addValid("test@cuioss.de")
                .addValid("7f9a39ff-d8e8-6de3-c81d-77c8c6b20445@example.com")
                .addValid("ab-c@de-fghi")
                .addValid("abc@def")
                .addValid("123@456")
                .addValid("?%&äö.ü-_@?%&_ä.ö-ü");

        // Add invalid email addresses
        testItems.addInvalid("a")
                .addInvalid("abc")
                .addInvalid("ab-c@de-f@ghi");
    }

    @Nested
    @DisplayName("Email format validation tests")
    class EmailFormatTests {

        @Test
        @DisplayName("Should validate standard email formats")
        void shouldValidateStandardEmailFormats(FacesContext facesContext) {
            // Arrange
            var validator = getValidator();
            var component = getComponent();

            // Act & Assert - Valid emails
            assertDoesNotThrow(() ->
                            validator.validate(facesContext, component, "user@example.com"),
                    "Standard email format should be valid");

            assertDoesNotThrow(() ->
                            validator.validate(facesContext, component, "user.name@example.co.uk"),
                    "Email with dots and subdomains should be valid");

            // Act & Assert - Invalid emails
            assertThrows(ValidatorException.class, () ->
                            validator.validate(facesContext, component, "user@"),
                    "Email without domain should be invalid");

            assertThrows(ValidatorException.class, () ->
                            validator.validate(facesContext, component, "@example.com"),
                    "Email without local part should be invalid");
        }

        @Test
        @DisplayName("Should validate emails with special characters")
        void shouldValidateEmailsWithSpecialCharacters(FacesContext facesContext) {
            // Arrange
            var validator = getValidator();
            var component = getComponent();

            // Act & Assert
            assertDoesNotThrow(() ->
                            validator.validate(facesContext, component, "user+tag@example.com"),
                    "Email with plus sign should be valid");

            assertDoesNotThrow(() ->
                            validator.validate(facesContext, component, "user-name@example.com"),
                    "Email with hyphen should be valid");

            assertDoesNotThrow(() ->
                            validator.validate(facesContext, component, "user_name@example.com"),
                    "Email with underscore should be valid");
        }
    }
}