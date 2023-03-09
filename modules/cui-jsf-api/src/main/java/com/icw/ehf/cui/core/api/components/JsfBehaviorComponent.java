package com.icw.ehf.cui.core.api.components;

import static java.util.Objects.requireNonNull;

import javax.faces.component.behavior.AjaxBehavior;
import javax.faces.context.FacesContext;

import lombok.experimental.UtilityClass;

/**
 * Repository for the standard JSF behavior
 *
 * @author Oliver Wolff
 *
 */
@UtilityClass
public class JsfBehaviorComponent {

    /**
     * @param context must not be {@code null}
     * @return a newly instantiated AjaxBehavior
     */
    public static final AjaxBehavior ajaxBehavior(FacesContext context) {
        requireNonNull(context);
        return (AjaxBehavior) context.getApplication().createBehavior(AjaxBehavior.BEHAVIOR_ID);
    }
}
