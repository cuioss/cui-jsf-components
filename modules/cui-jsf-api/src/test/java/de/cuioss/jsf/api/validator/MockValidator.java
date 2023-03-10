package de.cuioss.jsf.api.validator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

@SuppressWarnings("javadoc")
public class MockValidator extends AbstractValidator<String> {

    public static final String ERROR_KEY = "error";

    @Override
    protected void validateTypeSave(final FacesContext context, final UIComponent component, final String value) {
        if (ERROR_KEY.equals(value)) {
            throwValidatorException("key");
        }

    }

}
