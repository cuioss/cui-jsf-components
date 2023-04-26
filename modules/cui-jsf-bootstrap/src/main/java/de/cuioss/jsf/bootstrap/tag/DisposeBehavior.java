package de.cuioss.jsf.bootstrap.tag;

import javax.faces.component.behavior.ClientBehaviorBase;
import javax.faces.component.behavior.ClientBehaviorContext;

/**
 * @author Oliver Wolff
 */
public class DisposeBehavior extends ClientBehaviorBase {

    /**
     * The javaScript to be called if dispose is clicked:
     * cui_tag_dispose('%s').
     */
    public static final String ON_CLICK_TEMPLATE = "cui_tag_dispose('%s')";

    @Override
    public String getScript(final ClientBehaviorContext behaviorContext) {
        final var parent = behaviorContext.getComponent().getParent();
        final var id = parent.getId();
        final var clientId = parent.getClientId();
        return String.format(ON_CLICK_TEMPLATE, clientId.replace(id, TagComponent.DISPOSE_INFO_SUFFIX));
    }

}
