package de.cuioss.jsf.bootstrap.tag;

import javax.faces.view.facelets.ComponentConfig;
import javax.faces.view.facelets.ComponentHandler;
import javax.faces.view.facelets.MetaRule;
import javax.faces.view.facelets.MetaRuleset;

import de.cuioss.jsf.api.components.events.ModelPayloadEvent;
import de.cuioss.jsf.api.components.util.MethodRule;

/**
 * This handler is responsible for adding the binding to the disposeListener
 *
 * @author Oliver Wolff
 */
public class TagHandler extends ComponentHandler {

    /** "disposeListener". */
    public static final String DISPOSE_LISTENER_NAME = "disposeListener";

    private static final MetaRule DISPOSE_METHOD = new MethodRule(DISPOSE_LISTENER_NAME, null,
            new Class[] { ModelPayloadEvent.class });

    /**
     * @param config
     */
    public TagHandler(final ComponentConfig config) {
        super(config);
    }

    @Override
    protected MetaRuleset createMetaRuleset(@SuppressWarnings("rawtypes") final Class type) {
        final var metaRuleset = super.createMetaRuleset(type);

        metaRuleset.addRule(DISPOSE_METHOD);

        return metaRuleset;
    }

}
