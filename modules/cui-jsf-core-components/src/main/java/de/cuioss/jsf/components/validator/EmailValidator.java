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

import de.cuioss.jsf.api.validator.AbstractValidator;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.ValidatorException;
import lombok.Getter;
import lombok.Setter;

import java.util.regex.Pattern;

/**
 * <p>A JSF validator implementation that validates strings to ensure they represent
 * valid email addresses. The validator uses a configurable regular expression pattern
 * to perform the validation.</p>
 * 
 * <p>By default, the validator uses a simple pattern that checks for the presence of an '@'
 * character with text before and after it. This basic validation can be enhanced by
 * setting a more sophisticated pattern if needed.</p>
 * 
 * <p>When validation fails, the validator throws a {@link ValidatorException} with
 * an error message using the key "de.cuioss.common.email.invalid".</p>
 * 
 * <h2>Usage Example</h2>
 * 
 * <pre>
 * &lt;h:inputText id="email" value="#{bean.email}"&gt;
 *   &lt;f:validator validatorId="emailValidator" /&gt;
 * &lt;/h:inputText&gt;
 * </pre>
 * 
 * <p>To use a custom pattern:</p>
 * 
 * <pre>
 * &lt;h:inputText id="email" value="#{bean.email}"&gt;
 *   &lt;f:validator validatorId="emailValidator"&gt;
 *     &lt;f:attribute name="pattern" value="^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$" /&gt;
 *   &lt;/f:validator&gt;
 * &lt;/h:inputText&gt;
 * </pre>
 *
 * @author e0623
 * @since 1.0
 */
@FacesValidator("emailValidator")
public class EmailValidator extends AbstractValidator<String> {

    /**
     * The regular expression pattern used to validate email addresses.
     * Default is "^[^@]+@[^@]+$" which checks for text@text format.
     * This pattern can be customized to implement more stringent validation rules.
     */
    @Getter
    @Setter
    private String pattern = "^[^@]+@[^@]+$";

    /**
     * The initially compiled pattern that is reused for validation.
     * Pattern compilation is a relatively expensive operation, so we compile it once and reuse it.
     */
    private Pattern compiledPattern;

    /**
     * Returns the compiled regex pattern for validation.
     * If the pattern hasn't been compiled yet, it compiles it with case-insensitive flag.
     * 
     * @return the compiled Pattern object used for validation
     */
    private Pattern getCompiledPattern() {
        if (compiledPattern == null) {
            compiledPattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        }
        return compiledPattern;
    }

    /**
     * {@inheritDoc}
     * 
     * <p>Validates that the given value represents a valid email address according to
     * the configured pattern. Null values are considered valid (use required="true"
     * if you need to enforce non-null values).</p>
     * 
     * @param context the FacesContext for the current request
     * @param component the component whose value is being validated
     * @param value the value to be validated
     * @throws ValidatorException if the value is not a valid email address
     */
    @Override
    protected void validateTypeSave(final FacesContext context, final UIComponent component, final String value) {
        if (null != value) {
            var matcher = getCompiledPattern().matcher(value);
            if (!matcher.matches()) {
                throw new ValidatorException(createErrorMessage("de.cuioss.common.email.invalid"));
            }
        }
    }
}
