package de.cuioss.jsf.api.components.model.resultContent;

import de.cuioss.jsf.api.components.css.ContextState;
import de.cuioss.tools.logging.CuiLogger;
import de.cuioss.uimodel.result.ResultDetail;
import de.cuioss.uimodel.result.ResultObject;
import de.cuioss.uimodel.result.ResultState;

/**
 * Default Error-handler for dealing with {@link ResultObject}s
 */
public class ResultErrorHandler {

    private static final String MSG = "Error occurred but was handled silent.";

    /**
     * Handles a potential error.
     *
     * @param state the state of the {@link ResultDetail}.
     * @param detail the detail of the {@link ResultDetail}.
     * @param errorCode currently not used
     * @param errorController an {@link ErrorController} to allow setting a notification box or a
     *            GlobalFacesMessage.
     * @param log a {@link CuiLogger} to log the cause.
     */
    public void handleResultDetail(ResultState state, ResultDetail detail, Enum<?> errorCode,
            ErrorController errorController,
            CuiLogger log) {
        ContextState contextState;
        errorController.setRenderContent(true);
        var cause = detail.getCause();
        switch (state) {
            case ERROR:
                log.error(MSG, cause.orElse(null));
                contextState = ContextState.DANGER;
                errorController.setRenderContent(false);
                break;
            case WARNING:
                if (cause.isPresent()) {
                    log.warn(MSG, cause.get());
                }
                contextState = ContextState.WARNING;
                break;
            case INFO:
                if (cause.isPresent()) {
                    log.info(MSG, cause.get());
                }
                contextState = ContextState.INFO;
                break;
            case VALID:
                contextState = ContextState.SUCCESS;
                break;
            default:
                throw new UnsupportedOperationException("No resolving defined for -> " + state,
                    cause.orElse(null));
        }

        errorController.addNotificationBox(detail.getDetail(), contextState);
    }

}
