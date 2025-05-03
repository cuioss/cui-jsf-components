/*
 * Copyright 2023 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
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
import org.apache.myfaces.test.el.MockValueExpression;
import org.junit.jupiter.api.Test;

@VerifyComponentProperties(of = {"collapsible", "collapseSwitch", "collapsed"})
class CollapseSwitchProviderImplTest extends AbstractPartialComponentTest {

    @Test
    void shouldFailWithNullConstructor() {
        assertThrows(NullPointerException.class, () -> new CollapseSwitchProvider(null));
    }

    @Test
    void shouldHandleValueBinding() {
        var any = anyComponent();
        var beanToggler = new ToggleSwitch(Boolean.FALSE);
        // bind switch
        var expression = new MockValueExpression("#{collapseSwitch}", ToggleSwitch.class);
        expression.setValue(getFacesContext().getELContext(), beanToggler);
        any.setValueExpression("collapseSwitch", expression);
        assertEquals(Boolean.FALSE, beanToggler.isToggled());
        assertEquals(Boolean.FALSE, any.resolveCollapsed());
        // toggle in component -> check bean
        any.setCollapsedState(true);
        assertEquals(Boolean.TRUE, beanToggler.isToggled());
        assertEquals(Boolean.TRUE, any.resolveCollapsed());
        // toggle in bean -> check component
        beanToggler.setToggled(false);
        assertEquals(Boolean.FALSE, beanToggler.isToggled());
        assertEquals(Boolean.FALSE, any.resolveCollapsed());
        // bind bool to EL
        expression = new MockValueExpression("#{collapsed}", Boolean.class);
        expression.setValue(getFacesContext().getELContext(), Boolean.TRUE);
        any.setValueExpression("collapsed", expression);
        assertTrue(any.isCollapsed());
        // TODO how to test a value binding "#{true}" ???
    }
}
