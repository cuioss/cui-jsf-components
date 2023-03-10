package de.cuioss.jsf.api.components.util.modifier;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.html.HtmlPanelGroup;

import de.cuioss.jsf.api.components.util.ComponentModifier;

@SuppressWarnings({ "javadoc" })
public class ComponentModifierAssert {

    public static void assertContracts(final ComponentModifier componentModifier, final UIComponent uiComponent) {
        assertDisabledContract(componentModifier);
        assertStyleClassContract(componentModifier);
        assertStyleContract(componentModifier);
        assertTitleContract(componentModifier);
        assertRoleContract(componentModifier);
        // UIInput contract
        assertEditableValueHolder(componentModifier);
        assertResetValueContract(componentModifier);
        // Multiple forId contract
        assertCompositeInputContract(componentModifier);
        // Check wrappsComoponentClass
        assertFalse(componentModifier.wrapsComponentClass(UIComponent.class));
        assertTrue(componentModifier.wrapsComponentClass(uiComponent.getClass()));
    }

    public static void assertCompositeInputContract(final ComponentModifier componentModifier) {
        if (componentModifier.isCompositeInput()) {
            componentModifier.getForIndentifiers();
        } else {
            try {
                componentModifier.getForIndentifiers();
                fail("Should have thrown UnsupportedOperationException + " + componentModifier.getClass());
            } catch (final UnsupportedOperationException e) {
                // NOOP
            }
        }
    }

    public static void assertEditableValueHolder(final ComponentModifier componentModifier) {
        if (componentModifier.isEditableValueHolder()) {
            // FIXME owolff componentModifier.isDisabled();
            componentModifier.isRequired();
        } else {
            try {
                componentModifier.isRequired();
                fail("Should have thrown UnsupportedOperationException + " + componentModifier.getClass());
            } catch (final UnsupportedOperationException e) {
                // NOOP
            }
            try {
                componentModifier.isValid();
                fail("Should have thrown UnsupportedOperationException + " + componentModifier.getClass());
            } catch (final UnsupportedOperationException e) {
                // NOOP
            }
        }
    }

    public static void assertRoleContract(final ComponentModifier componentModifier) {
        if (componentModifier.isSupportsRole()) {
            componentModifier.setRole("role");
            componentModifier.getRole();
        } else {
            try {
                componentModifier.setRole("role");
                fail("Should have thrown UnsupportedOperationException + " + componentModifier.getClass());
            } catch (final UnsupportedOperationException e) {
                // NOOP
            }
            try {
                componentModifier.getRole();
                fail("Should have thrown UnsupportedOperationException + " + componentModifier.getClass());
            } catch (final UnsupportedOperationException e) {
                // NOOP
            }
        }
    }

    public static void assertResetValueContract(final ComponentModifier componentModifier) {
        if (componentModifier.isSupportsResetValue()) {
            componentModifier.resetValue();
        } else {
            try {
                componentModifier.resetValue();
                fail("Should have thrown UnsupportedOperationException + " + componentModifier.getClass());
            } catch (final UnsupportedOperationException e) {
                // NOOP
            }
        }
    }

    public static void assertTitleContract(final ComponentModifier componentModifier) {
        if (componentModifier.isSupportsTitle()) {
            componentModifier.setTitle("title");
            componentModifier.getTitle();
        } else {
            try {
                componentModifier.setTitle("title");
                fail("Should have thrown UnsupportedOperationException + " + componentModifier.getClass());
            } catch (final UnsupportedOperationException e) {
                // NOOP
            }
            try {
                componentModifier.getTitle();
                fail("Should have thrown UnsupportedOperationException + " + componentModifier.getClass());
            } catch (final UnsupportedOperationException e) {
                // NOOP
            }
        }
    }

    public static void assertStyleClassContract(final ComponentModifier componentModifier) {
        if (componentModifier.isSupportsStyleClass()) {
            componentModifier.setStyleClass("styleClass");
            componentModifier.getStyleClass();
        } else {
            try {
                componentModifier.setStyleClass("styleClass");
                fail("Should have thrown UnsupportedOperationException + " + componentModifier.getClass());
            } catch (final UnsupportedOperationException e) {
                // NOOP
            }
            try {
                componentModifier.getStyleClass();
                fail("Should have thrown UnsupportedOperationException + " + componentModifier.getClass());
            } catch (final UnsupportedOperationException e) {
                // NOOP
            }
        }
    }

    public static void assertStyleContract(final ComponentModifier componentModifier) {
        if (componentModifier.isSupportsStyle()) {
            componentModifier.setStyle("styleClass");
            componentModifier.getStyle();
        } else {
            try {
                componentModifier.setStyle("styleClass");
                fail("Should have thrown UnsupportedOperationException + " + componentModifier.getClass());
            } catch (final UnsupportedOperationException e) {
                // NOOP
            }
            try {
                componentModifier.getStyle();
                fail("Should have thrown UnsupportedOperationException + " + componentModifier.getClass());
            } catch (final UnsupportedOperationException e) {
                // NOOP
            }
        }
    }

    public static void assertDisabledContract(final ComponentModifier componentModifier) {
        if (componentModifier.isSupportsDisabled()) {
            componentModifier.setDisabled(true);
            componentModifier.isDisabled();
        } else {
            try {
                componentModifier.setDisabled(false);
                fail("Should have thrown UnsupportedOperationException + " + componentModifier.getClass());
            } catch (final UnsupportedOperationException e) {
                // NOOP
            }
            try {
                componentModifier.isDisabled();
                fail("Should have thrown UnsupportedOperationException + " + componentModifier.getClass());
            } catch (final UnsupportedOperationException e) {
                // NOOP
            }
        }
    }

    /**
     * Some component which extends UIInput class
     */
    static class SomeThirdPartyUiComeponent extends UIInput {
    }

    static class ExtendedPanelGroup extends HtmlPanelGroup {
    }
}
