package de.cuioss.jsf.api.components.util.modifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.faces.component.UIComponentBase;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.components.util.modifier.support.TitleProviderImpl;
import de.cuioss.jsf.bootstrap.composite.EditableDataListComponent;

class ComponentModifierFactoryTest {

    @Test
    void shouldHandleComponentBase() {
        UIComponentBase component = new UIComponentBase() {

            @Override
            public String getFamily() {
                return null;
            }
        };
        assertNotNull(ComponentModifierFactory.findFittingWrapper(component));
        assertEquals(ReflectionBasedModifier.class, ComponentModifierFactory.findFittingWrapper(component).getClass());
    }

    @Test
    void shouldHandleCuiInterface() {
        UIComponentBase component = new TitleProviderImpl();
        assertNotNull(ComponentModifierFactory.findFittingWrapper(component));
        assertEquals(CuiInterfaceBasedModifier.class,
                ComponentModifierFactory.findFittingWrapper(component).getClass());
    }

    @Test
    void shouldHandleCuiCompositeWrapper() {
        UIComponentBase component = new EditableDataListComponent();
        assertNotNull(ComponentModifierFactory.findFittingWrapper(component));
    }
}
