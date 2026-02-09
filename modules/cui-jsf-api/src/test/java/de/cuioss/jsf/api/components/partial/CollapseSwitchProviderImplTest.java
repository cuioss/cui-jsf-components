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
package de.cuioss.jsf.api.components.partial;

import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.jsf.api.ui.model.ToggleSwitch;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import jakarta.faces.context.FacesContext;
import org.apache.myfaces.test.el.MockValueExpression;
import org.jboss.weld.junit5.ExplicitParamInjection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@VerifyComponentProperties(of = {"collapsible", "collapseSwitch", "collapsed"})
@ExplicitParamInjection
@DisplayName("Tests for CollapseSwitchProvider implementation")
class CollapseSwitchProviderImplTest extends AbstractPartialComponentTest {

    @Test
    @DisplayName("Should throw NullPointerException when constructed with null")
    void shouldFailWithNullConstructor() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> new CollapseSwitchProvider(null),
                "Constructor should reject null component");
    }

    @Nested
    @DisplayName("Tests for value binding behavior")
    class ValueBindingTests {

        @Test
        @DisplayName("Should properly handle value binding between component and model")
        void shouldHandleValueBindingBetweenComponentAndModel(FacesContext facesContext) {
            // Arrange
            var any = anyComponent();
            var beanToggler = new ToggleSwitch(Boolean.FALSE);

            // Act - bind switch
            var expression = new MockValueExpression("#{collapseSwitch}", ToggleSwitch.class);
            expression.setValue(facesContext.getELContext(), beanToggler);
            any.setValueExpression("collapseSwitch", expression);

            // Assert - initial state
            assertEquals(Boolean.FALSE, beanToggler.isToggled(), "Initial bean toggle state should be false");
            assertEquals(Boolean.FALSE, any.resolveCollapsed(), "Initial component collapsed state should be false");

            // Act - toggle in component
            any.setCollapsedState(true);

            // Assert - component change affects bean
            assertEquals(Boolean.TRUE, beanToggler.isToggled(), "Bean toggle state should be updated to true");
            assertEquals(Boolean.TRUE, any.resolveCollapsed(), "Component collapsed state should be true");

            // Act - toggle in bean
            beanToggler.setToggled(false);

            // Assert - bean change affects component
            assertEquals(Boolean.FALSE, beanToggler.isToggled(), "Bean toggle state should be false");
            assertEquals(Boolean.FALSE, any.resolveCollapsed(), "Component collapsed state should be updated to false");

            // Act - bind boolean to EL
            expression = new MockValueExpression("#{collapsed}", Boolean.class);
            expression.setValue(facesContext.getELContext(), Boolean.TRUE);
            any.setValueExpression("collapsed", expression);

            // Assert - direct boolean binding
            assertTrue(any.isCollapsed(), "Component should be collapsed with direct boolean binding");

            // Note: Testing a literal value binding like "#{true}" would require mocking the EL context evaluation
            // which is beyond the scope of this test
        }
    }
}
