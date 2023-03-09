package de.icw.cui.components.lazyloading;

import de.cuioss.test.jsf.component.AbstractComponentTest;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;

@VerifyComponentProperties(of = { "initialized", "notificationBoxValue", "notificationBoxState", "renderContent",
    "viewModel", "ignoreAutoUpdate" })
class LazyLoadingComponentTest extends AbstractComponentTest<LazyLoadingComponent> {

}
