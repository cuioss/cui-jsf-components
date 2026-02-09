/*
 * Copyright © 2025 CUI-OpenSource-Software (info@cuioss.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.api.application;

import static de.cuioss.jsf.api.common.logging.JsfApiLogMessages.INFO;

import de.cuioss.tools.logging.CuiLogger;
import jakarta.faces.event.SystemEvent;
import jakarta.faces.event.SystemEventListener;

/**
 * CuiVersionLoggerEventListener runs once at JSF application start and logs CUI version information.
 * The version information is read from the project manifest file.
 * <p>
 * This listener is automatically registered with the JSF application and will log the implementation
 * title and version when the application starts.
 * </p>
 * <p>
 * This class is thread-safe as it contains no mutable state.
 * </p>
 *
 * @author Eugen Fischer
 * @since 1.0
 */
public class CuiVersionLoggerEventListener implements SystemEventListener {

    private static final CuiLogger LOGGER = new CuiLogger(CuiVersionLoggerEventListener.class);

    /**
     * Processes the system event by logging the CUI version information.
     * <p>
     * This method retrieves the implementation title and version from the package
     * information and logs it using the INFO.VERSION_INFO log record.
     * </p>
     *
     * @param event the system event being processed, not used in this implementation
     */
    @Override
    public void processEvent(final SystemEvent event) {
        final var pack = CuiVersionLoggerEventListener.class.getPackage();
        LOGGER.info(INFO.VERSION_INFO, pack.getImplementationTitle() != null ? pack.getImplementationTitle().toUpperCase()
                        : "cuioss-Common-Ui",
                pack.getImplementationVersion() != null ? pack.getImplementationVersion().toUpperCase() : "unknown");
    }

    /**
     * Determines whether this listener is interested in events from the specified source.
     * <p>
     * This implementation always returns true, meaning this listener will process
     * events from any source.
     * </p>
     *
     * @param source the source that is inquiring about the listener's interest
     * @return always returns true, indicating this listener will process events from any source
     */
    @Override
    public boolean isListenerForSource(final Object source) {
        return true;
    }

}
