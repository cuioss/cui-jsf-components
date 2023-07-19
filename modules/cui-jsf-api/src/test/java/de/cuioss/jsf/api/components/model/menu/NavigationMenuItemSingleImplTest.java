package de.cuioss.jsf.api.components.model.menu;

import de.cuioss.jsf.api.components.model.menu.NavigationMenuItemSingleImpl;
import de.cuioss.test.valueobjects.ValueObjectTest;
import de.cuioss.test.valueobjects.api.contracts.VerifyConstructor;
import de.cuioss.test.valueobjects.api.generator.PropertyGenerator;

@VerifyConstructor(of = "order")
@PropertyGenerator(NavigationMenuItemGenerator.class)
class NavigationMenuItemSingleImplTest extends ValueObjectTest<NavigationMenuItemSingleImpl> {
}
