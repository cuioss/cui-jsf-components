package com.icw.ehf.cui.core.api.application.theme;

import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.application.ResourceHandlerWrapper;

import com.icw.ehf.cui.core.api.application.theme.accessor.ThemeConfigurationAccessor;
import com.icw.ehf.cui.core.api.application.theme.accessor.ThemeNameProducerAccessor;
import com.icw.ehf.cui.core.api.application.theme.impl.ThemeConfigurationImpl;

import de.cuioss.tools.logging.CuiLogger;
import lombok.Getter;

/**
 * Deal with Color in the jsf web page
 *
 * @author Oliver Wolff
 */
public class ThemeResourceHandler extends ResourceHandlerWrapper {

    private static final CuiLogger log = new CuiLogger(ThemeResourceHandler.class);

    /** The fixed, configured, values for this handlers. */
    private final ThemeConfigurationAccessor configurationAccessor = new ThemeConfigurationAccessor();

    /** The userSpecific value. */
    private final ThemeNameProducerAccessor themeProducerAccessor = new ThemeNameProducerAccessor();

    /** The wrapped resource Handler to be delegated to. */
    @Getter
    private final ResourceHandler wrapped;

    /**
     * Constructor.
     *
     * @param wrapped handler
     */
    public ThemeResourceHandler(ResourceHandler wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public Resource createResource(String resourceName, String libraryName) {
        ThemeConfiguration configuration;
        try {
            configuration = configurationAccessor.getValue();
        } catch (IllegalArgumentException e) {
            log.error("loading of ThemeConfiguration failed: ", e);
            configuration = new ThemeConfigurationImpl();
            ((ThemeConfigurationImpl) configuration).initBean();
        }
        if (libraryName != null && libraryName.equals(configuration.getCssLibrary())
                && resourceName.equals(configuration.getCssName())) {
            String themeName;
            try {
                var producer = themeProducerAccessor.getValue();
                themeName = producer.getTheme();
            } catch (IllegalArgumentException e) {
                log.error("loading of ThemeNameProducer failed: ", e);
                themeName = configuration.getDefaultTheme();
            }
            var newName = configuration.getCssForThemeName(themeName);
            return wrapped.createResource(newName, libraryName);
        }
        return wrapped.createResource(resourceName, libraryName);
    }
}
