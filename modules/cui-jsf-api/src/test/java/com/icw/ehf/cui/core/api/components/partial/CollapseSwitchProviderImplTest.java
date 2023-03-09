package com.icw.ehf.cui.core.api.components.partial;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.myfaces.test.el.MockValueExpression;
import org.junit.jupiter.api.Test;

import com.icw.ehf.cui.core.api.ui.model.ToggleSwitch;

import de.cuioss.test.jsf.config.component.VerifyComponentProperties;

@VerifyComponentProperties(of = { "collapsible", "collapseSwitch", "collapsed" })
class CollapseSwitchProviderImplTest extends AbstractPartialComponentTest {

    @SuppressWarnings("unused")
    @Test
    void shouldFailWithNullConstructor() {
        assertThrows(NullPointerException.class, () -> {
            new CollapseSwitchProvider(null);
        });
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
