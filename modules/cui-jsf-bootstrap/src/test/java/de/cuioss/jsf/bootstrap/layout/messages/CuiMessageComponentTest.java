package de.cuioss.jsf.bootstrap.layout.messages;

import de.cuioss.test.jsf.component.AbstractComponentTest;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;

@VerifyComponentProperties(of = { "style", "styleClass", "escape", "forIdentifier", "showDetail",
        "showSummary" }, defaultValued = { "forIdentifier" })
class CuiMessageComponentTest extends AbstractComponentTest<CuiMessageComponent> {

}
