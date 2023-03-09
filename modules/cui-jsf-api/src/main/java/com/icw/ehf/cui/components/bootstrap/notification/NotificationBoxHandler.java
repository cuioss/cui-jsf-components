package com.icw.ehf.cui.components.bootstrap.notification;

import javax.faces.view.facelets.ComponentConfig;
import javax.faces.view.facelets.ComponentHandler;
import javax.faces.view.facelets.MetaRule;
import javax.faces.view.facelets.MetaRuleset;

import com.icw.ehf.cui.core.api.components.events.ModelPayloadEvent;
import com.icw.ehf.cui.core.api.components.util.MethodRule;

/**
 * This handler is responsible for adding the binding to the dismissibleListener
 *
 * @author Matthias Walliczek
 */
public class NotificationBoxHandler extends ComponentHandler {

    /** "dismissListener". */
    public static final String DISMISS_LISTENER_NAME = "dismissListener";

    private static final MetaRule DISMISS_METHOD =
        new MethodRule(DISMISS_LISTENER_NAME, null, new Class[] { ModelPayloadEvent.class });

    /**
     * @param config
     */
    public NotificationBoxHandler(final ComponentConfig config) {
        super(config);
    }

    @Override
    protected MetaRuleset createMetaRuleset(@SuppressWarnings("rawtypes") final Class type) { // Rawtype
                                                                                              // due
                                                                                              // to
                                                                                              // API
        final var metaRuleset = super.createMetaRuleset(type);

        metaRuleset.addRule(DISMISS_METHOD);

        return metaRuleset;
    }

}
