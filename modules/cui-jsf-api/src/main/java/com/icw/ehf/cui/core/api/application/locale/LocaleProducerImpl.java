package com.icw.ehf.cui.core.api.application.locale;

import java.io.Serializable;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Default implementation of {@link LocaleProducer} that mimics the jsf default behavior for locale
 * computation.
 *
 * @author Oliver Wolff
 */
@EqualsAndHashCode
@ToString
public class LocaleProducerImpl implements LocaleProducer, Serializable {

    private static final long serialVersionUID = 255784500483019492L;

    @Getter
    private Locale locale;

    /**
     * Bean name for looking up instances.
     */
    public static final String BEAN_NAME = "localeProducer";

    /**
     * Initializes the bean. See class documentation for expected result.
     */
    @PostConstruct
    public void initBean() {
        var context = FacesContext.getCurrentInstance();
        locale = context.getApplication().getViewHandler().calculateLocale(context);
    }

}
