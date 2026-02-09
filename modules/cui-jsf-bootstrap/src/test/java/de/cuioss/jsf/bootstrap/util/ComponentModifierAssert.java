/*
 * Copyright © 2025 CUI-OpenSource-Software (info@cuioss.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.bootstrap.util;

import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.jsf.api.components.util.ComponentModifier;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIInput;
import jakarta.faces.component.html.HtmlPanelGroup;

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
            assertThrows(UnsupportedOperationException.class, componentModifier::getForIndentifiers);
        }
    }

    public static void assertEditableValueHolder(final ComponentModifier componentModifier) {
        if (componentModifier.isEditableValueHolder()) {
            // FIXME owolff componentModifier.isDisabled();
            componentModifier.isRequired();
        } else {
            assertThrows(UnsupportedOperationException.class, componentModifier::isRequired);
            assertThrows(UnsupportedOperationException.class, componentModifier::isValid);
        }
    }

    public static void assertRoleContract(final ComponentModifier componentModifier) {
        if (componentModifier.isSupportsRole()) {
            componentModifier.setRole("role");
            componentModifier.getRole();
        } else {
            assertThrows(UnsupportedOperationException.class, () -> componentModifier.setRole("role"));
            assertThrows(UnsupportedOperationException.class, componentModifier::getRole);
        }
    }

    public static void assertResetValueContract(final ComponentModifier componentModifier) {
        if (componentModifier.isSupportsResetValue()) {
            componentModifier.resetValue();
        } else {
            assertThrows(UnsupportedOperationException.class, componentModifier::resetValue);
        }
    }

    public static void assertTitleContract(final ComponentModifier componentModifier) {
        if (componentModifier.isSupportsTitle()) {
            componentModifier.setTitle("title");
            componentModifier.getTitle();
        } else {
            assertThrows(UnsupportedOperationException.class, () -> componentModifier.setTitle("title"));
            assertThrows(UnsupportedOperationException.class, componentModifier::getTitle);
        }
    }

    public static void assertStyleClassContract(final ComponentModifier componentModifier) {
        if (componentModifier.isSupportsStyleClass()) {
            componentModifier.setStyleClass("styleClass");
            componentModifier.getStyleClass();
        } else {
            assertThrows(UnsupportedOperationException.class, () -> componentModifier.setStyleClass("styleClass"));
            assertThrows(UnsupportedOperationException.class, componentModifier::getStyleClass);
        }
    }

    public static void assertStyleContract(final ComponentModifier componentModifier) {
        if (componentModifier.isSupportsStyle()) {
            componentModifier.setStyle("styleClass");
            componentModifier.getStyle();
        } else {
            assertThrows(UnsupportedOperationException.class, () -> componentModifier.setStyle("styleClass"));
            assertThrows(UnsupportedOperationException.class, componentModifier::getStyle);
        }
    }

    public static void assertDisabledContract(final ComponentModifier componentModifier) {
        if (componentModifier.isSupportsDisabled()) {
            componentModifier.setDisabled(true);
            componentModifier.isDisabled();
        } else {
            assertThrows(UnsupportedOperationException.class, () -> componentModifier.setDisabled(false));
            assertThrows(UnsupportedOperationException.class, componentModifier::isDisabled);
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
