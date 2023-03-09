package com.icw.ehf.cui.components.typewatch;

import javax.faces.component.behavior.AjaxBehavior;
import javax.faces.component.behavior.ClientBehaviorContext;

/**
 * An {@link AjaxBehavior} that does not write a script block into the html page (like
 * onclick="jsf.ajax...") but only
 * registers to handle such requests at server side and allows to trigger the ajax request manually.
 */
public class NoScriptAjaxBehavior extends AjaxBehavior {

    @Override
    public String getScript(final ClientBehaviorContext behaviorContext) {
        return null;
    }
}
