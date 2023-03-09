package com.icw.ehf.cui.components.bootstrap.menu.model;

import de.cuioss.test.valueobjects.ValueObjectTest;
import de.cuioss.test.valueobjects.api.contracts.VerifyConstructor;
import de.cuioss.test.valueobjects.api.generator.PropertyGenerator;

@VerifyConstructor(of = "order")
@PropertyGenerator(NavigationMenuItemGenerator.class)
class NavigationMenuItemContainerImplTest extends ValueObjectTest<NavigationMenuItemContainerImpl> {
}
