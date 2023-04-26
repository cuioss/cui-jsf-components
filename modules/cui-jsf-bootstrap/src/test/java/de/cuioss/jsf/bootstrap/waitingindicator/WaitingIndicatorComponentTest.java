package de.cuioss.jsf.bootstrap.waitingindicator;

import de.cuioss.test.jsf.component.AbstractComponentTest;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;

@VerifyComponentProperties(of = { "style", "size", "styleClass" }, defaultValued = { "size" })
class WaitingIndicatorComponentTest extends AbstractComponentTest<WaitingIndicatorComponent> {

}
