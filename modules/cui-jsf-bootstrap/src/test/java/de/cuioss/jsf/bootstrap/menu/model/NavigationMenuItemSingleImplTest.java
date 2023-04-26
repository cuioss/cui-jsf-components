package de.cuioss.jsf.bootstrap.menu.model;

import de.cuioss.test.valueobjects.ValueObjectTest;
import de.cuioss.test.valueobjects.api.contracts.VerifyConstructor;
import de.cuioss.test.valueobjects.api.generator.PropertyGenerator;

@VerifyConstructor(of = "order")
@PropertyGenerator(NavigationMenuItemGenerator.class)
class NavigationMenuItemSingleImplTest extends ValueObjectTest<NavigationMenuItemSingleImpl> {
}
