/*
 * Copyright 2023 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.components.validator;

import java.util.regex.Pattern;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.ValidatorException;

import de.cuioss.jsf.api.validator.AbstractValidator;
import lombok.Getter;
import lombok.Setter;

/**
 * Validates strings to be a valid email address.
 *
 * @author e0623
 */
@FacesValidator("emailValidator")
public class EmailValidator extends AbstractValidator<String> {

    @Getter
    @Setter
    private String pattern = "^[^@]+@[^@]+$";

    /**
     * The initially compiled pattern in order to reuse. Compilation of the pattern
     * is quite expensive.
     */
    private Pattern compiledPattern;

    private Pattern getCompiledPattern() {
        if (compiledPattern == null) {
            compiledPattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        }
        return compiledPattern;
    }

    @Override
    protected void validateTypeSave(final FacesContext context, final UIComponent component, final String value) {
        if (null != value) {
            final CharSequence inputStr = value;
            var matcher = getCompiledPattern().matcher(inputStr);
            if (!matcher.matches()) {
                throw new ValidatorException(createErrorMessage("de.cuioss.common.email.invalid"));
            }
        }
    }
}
