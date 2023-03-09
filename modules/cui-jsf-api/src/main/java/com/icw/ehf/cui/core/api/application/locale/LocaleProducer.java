package com.icw.ehf.cui.core.api.application.locale;

import java.util.Locale;

/**
 * Provide application specific calculation / resolving of current locale.<br/>
 * Managed bean name should be {@link LocaleProducerImpl#BEAN_NAME}
 *
 * @author i000576
 */
public interface LocaleProducer {

    /**
     * @return the actual locale for this context / view.
     */
    Locale getLocale();

}
