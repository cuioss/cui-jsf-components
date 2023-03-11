package de.cuioss.jsf.api.application;

import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;

import de.cuioss.tools.logging.CuiLogger;

/**
 * CuiVersionLoggerEventListener run once at jsf application start<br>
 * and log cui version information. The content is places at project manifest.
 *
 * @author Eugen Fischer
 */
public class CuiVersionLoggerEventListener implements SystemEventListener {

    private static final CuiLogger log = new CuiLogger(CuiVersionLoggerEventListener.class);

    @Override
    public void processEvent(final SystemEvent event) {
        final var pack = CuiVersionLoggerEventListener.class.getPackage();
        log.info("Running on {} ( Version : {} )",
                pack.getImplementationTitle() != null ? pack.getImplementationTitle().toUpperCase()
                        : "cuioss-Common-Ui",
                pack.getImplementationVersion() != null ? pack.getImplementationVersion().toUpperCase()
                        : "unknown");
    }

    @Override
    public boolean isListenerForSource(final Object source) {
        return true;
    }

}
