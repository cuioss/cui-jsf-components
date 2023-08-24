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
package de.cuioss.jsf.api.application.resources;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;

import de.cuioss.jsf.api.application.projectstage.CuiProjectStageAccessor;
import de.cuioss.jsf.api.application.resources.accessor.CuiResourceConfigurationAccessor;
import de.cuioss.jsf.api.application.resources.accessor.CuiResourceManagerAccessor;
import de.cuioss.jsf.api.application.resources.util.LibraryInventory;
import de.cuioss.tools.io.StructuredFilename;
import de.cuioss.tools.logging.CuiLogger;

/**
 * Deals with cui-specific resources. The corresponding modifications will only
 * take place in production environments. This will contain choosing the min
 * version of resource and adding a cache-buster to the resource request.
 */
public class CuiResourceHandler extends AbstractVersionResourceHandler {

    private static final String MINIMIZED_SUFFIX = ".min";

    private static final CuiLogger log = new CuiLogger(CuiResourceHandler.class);

    private final CuiResourceConfigurationAccessor configurationAccessor = new CuiResourceConfigurationAccessor();

    private final CuiResourceManagerAccessor resourceManagerAccessor = new CuiResourceManagerAccessor();

    private final CuiProjectStageAccessor projectStageProducerAccessor = new CuiProjectStageAccessor();

    /**
     * The name for the mojarra application logger. used for temporarily disabling.
     */
    public static final String FACES_LOGGER = "javax.enterprise.resource.webcontainer.jsf.application";

    public CuiResourceHandler(final ResourceHandler wrapped) {
        super(wrapped);
    }

    @Override
    public Resource createResource(final String resourceName, final String libraryName) {
        if (shouldHandle(resourceName, libraryName)) {
            final var determinedResourceName = determineResourceName(resourceName, libraryName);
            return super.createResource(determinedResourceName, libraryName);
        }
        return super.createResource(resourceName, libraryName);
    }

    @Override
    protected boolean shouldHandleRequestedResource(String resourceName, String libraryName) {
        return shouldHandle(resourceName, libraryName);
    }

    @Override
    protected String getNewResourceVersion() {
        return configurationAccessor.getValue().getVersion();
    }

    /**
     * Checks whether the resourceHandler should handle the given request
     *
     * @param resourceName
     * @param libraryName
     * @return boolean indicating whether the resourceHandler should handle (modify)
     *         the given resource Request
     */
    private boolean shouldHandle(final String resourceName, final String libraryName) {
        if (projectStageProducerAccessor.getValue().isDevelopment()) {
            return false;
        }
        var structuredFilename = new StructuredFilename(resourceName);
        var configuration = configurationAccessor.getValue();
        return configuration.getHandledLibraries().contains(libraryName)
                && configuration.getHandledSuffixes().contains(structuredFilename.getSuffix());
    }

    /**
     * Computes / checks whether the Resource name needs to be adapted / suffixed
     * with min, if corresponding version is available.
     *
     * @param resourceName to be checked against
     * @param libraryName  to be checked against
     * @return the computed resourceName;
     */
    private String determineResourceName(final String resourceName, final String libraryName) {
        var filename = new StructuredFilename(resourceName);
        var libraryInventory = resourceManagerAccessor.getValue().getLibraryInventory(libraryName);
        if (!libraryInventory.containsMapping(resourceName)) {
            // Special case: resource-name contains .min already
            if (filename.getNamePart().endsWith(MINIMIZED_SUFFIX)) {
                libraryInventory.addMapping(resourceName, resourceName);
            } else {
                registerResourceName(libraryInventory, filename);
            }
        }
        return libraryInventory.getResourceMapping(resourceName);
    }

    /**
     * Checks whether there is a minified version available. If so it will be
     * registered to the given {@link LibraryInventory} otherwise the original
     * filename will be registered.
     *
     * @param libraryInventory
     * @param filename
     */
    private void registerResourceName(final LibraryInventory libraryInventory, final StructuredFilename filename) {
        var minResourceName = filename.getAppendedName(MINIMIZED_SUFFIX);
        synchronized (CuiResourceHandler.class) {
            // This is a hack and I know it *g*
            // Because of the way we check the existence of overwritable
            // resources JSF would log for each request a warning
            // "resource not found". This might confuse
            // people. Therefore we temporarily switch the faces application
            // logger to severe and restore it later again.
            var facesLogger = Logger.getLogger(FACES_LOGGER);
            var oldLevel = facesLogger.getLevel();
            facesLogger.setLevel(Level.SEVERE);
            try {
                var check = getWrapped().createResource(minResourceName, libraryInventory.getLibraryName());
                if (null == check) {
                    libraryInventory.addMapping(filename.getOriginalName(), filename.getOriginalName());
                } else {
                    libraryInventory.addMapping(filename.getOriginalName(), minResourceName);
                }
            } catch (RuntimeException e) {
                // We need to intercept in order to reset the logger properly
                log.error("Exception with resource " + filename);
                throw e;
            } finally {
                // Restore the faces logger again
                facesLogger.setLevel(oldLevel);
            }
        }
    }

}
