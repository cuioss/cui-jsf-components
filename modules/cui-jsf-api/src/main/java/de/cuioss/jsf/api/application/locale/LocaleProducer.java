package de.cuioss.jsf.api.application.locale;

import java.util.Locale;

/**
 * Provide application specific calculation / resolving of current locale.<br>
 * Managed bean name should be {@link LocaleProducerImpl#BEAN_NAME}
 *
 * @author Eugen Fischer
 */
public interface LocaleProducer {

    /**
     * @return the actual locale for this context / view.
     */
    Locale getLocale();

}
