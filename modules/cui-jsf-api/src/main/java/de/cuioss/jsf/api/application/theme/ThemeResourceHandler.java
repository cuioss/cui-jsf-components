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
package de.cuioss.jsf.api.application.theme;

import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.application.ResourceHandlerWrapper;

import de.cuioss.jsf.api.application.theme.accessor.ThemeConfigurationAccessor;
import de.cuioss.jsf.api.application.theme.accessor.ThemeNameProducerAccessor;
import de.cuioss.jsf.api.application.theme.impl.ThemeConfigurationImpl;
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
