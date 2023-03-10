package de.cuioss.jsf.api.validator;

import java.util.regex.Pattern;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.ValidatorException;

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
     * The initially compiled pattern in order to reuse. Compilation of the pattern is quite
     * expensive.
     */
    private Pattern compiledPattern;

    private Pattern getCompiledPattern() {
        if (compiledPattern == null) {
            compiledPattern = Pattern.compile(pattern,
                    Pattern.CASE_INSENSITIVE);
        }
        return compiledPattern;
    }

    @Override
    protected void validateTypeSave(final FacesContext context, final UIComponent component, final String value) {
        if (null != value) {
            final CharSequence inputStr = value;
            var matcher = getCompiledPattern().matcher(inputStr);
            if (!matcher.matches()) {
                throw new ValidatorException(createErrorMessage("ehf.cui.common.email.invalid"));
            }
        }
    }
}
