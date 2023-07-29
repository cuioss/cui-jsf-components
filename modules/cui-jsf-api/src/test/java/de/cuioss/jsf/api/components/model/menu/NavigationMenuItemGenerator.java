package de.cuioss.jsf.api.components.model.menu;

import static de.cuioss.tools.collect.CollectionLiterals.immutableList;

import java.util.List;

import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.TypedGenerator;

/**
 * Generates an instance of a {@linkplain NavigationMenuItem} implementation.
 *
 * @author Sven Haag
 *
 */
public class NavigationMenuItemGenerator implements TypedGenerator<NavigationMenuItem> {

    private static final TypedGenerator<String> stringGenerator = Generators.strings();
    private static final TypedGenerator<Boolean> boolGenerator = Generators.booleans();

    private static final List<Class<? extends NavigationMenuItem>> possibleItems = immutableList(
            NavigationMenuItemSingleImpl.class,
            NavigationMenuItemContainerImpl.class,
            NavigationMenuItemSeparatorImpl.class);

    @Override
    public NavigationMenuItem next() {
        NavigationMenuItem item = null;

        final Class<? extends NavigationMenuItem> navMenuItemClass = Generators.fixedValues(possibleItems).next();
        if (navMenuItemClass == NavigationMenuItemSingleImpl.class) {
            item = new NavigationMenuItemSingleImpl(Generators.integers(1, 100).next());
            final var navigationMenuItemSingle = (NavigationMenuItemSingleImpl) item;
            navigationMenuItemSingle.setId(stringGenerator.next());
            navigationMenuItemSingle.setRendered(boolGenerator.next());
            navigationMenuItemSingle.setDisabled(boolGenerator.next());
            navigationMenuItemSingle.setTitleKey(stringGenerator.next());
            navigationMenuItemSingle.setTitleValue(stringGenerator.next());
            navigationMenuItemSingle.setIconStyleClass(stringGenerator.next());

            ((NavigationMenuItemSingleImpl) item).setOnClickAction(stringGenerator.next());
            ((NavigationMenuItemSingleImpl) item).setOutcome(stringGenerator.next());
        } else if (navMenuItemClass == NavigationMenuItemContainerImpl.class) {
            item = new NavigationMenuItemContainerImpl(Generators.integers(1, 100).next());
            final var navigationMenuItemContainer = (NavigationMenuItemContainerImpl) item;
            navigationMenuItemContainer.setId(stringGenerator.next());
            navigationMenuItemContainer.setRendered(boolGenerator.next());
            navigationMenuItemContainer.setDisabled(boolGenerator.next());
            navigationMenuItemContainer.setTitleKey(stringGenerator.next());
            navigationMenuItemContainer.setTitleValue(stringGenerator.next());
            navigationMenuItemContainer.setIconStyleClass(stringGenerator.next());
        } else if (navMenuItemClass == NavigationMenuItemSeparatorImpl.class) {
            item = new NavigationMenuItemSeparatorImpl(Generators.integers(1, 100).next());
            final var navigationMenuItemSeparator = (NavigationMenuItemSeparatorImpl) item;
            navigationMenuItemSeparator.setId(stringGenerator.next());
            navigationMenuItemSeparator.setRendered(boolGenerator.next());
            navigationMenuItemSeparator.setDisabled(boolGenerator.next());
            navigationMenuItemSeparator.setTitleKey(stringGenerator.next());
            navigationMenuItemSeparator.setTitleValue(stringGenerator.next());
            navigationMenuItemSeparator.setIconStyleClass(stringGenerator.next());
        }

        return item;
    }

    @Override
    public Class<NavigationMenuItem> getType() {
        return NavigationMenuItem.class;
    }
}
