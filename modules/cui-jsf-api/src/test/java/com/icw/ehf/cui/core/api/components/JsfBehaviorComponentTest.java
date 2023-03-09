package com.icw.ehf.cui.core.api.components;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.faces.component.behavior.AjaxBehavior;

import org.junit.jupiter.api.Test;

import com.icw.ehf.cui.core.api.CoreJsfTestConfiguration;

import de.cuioss.test.jsf.config.ComponentConfigurator;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import de.cuioss.test.jsf.junit5.JsfEnabledTestEnvironment;

@JsfTestConfiguration(CoreJsfTestConfiguration.class)
class JsfBehaviorComponentTest extends JsfEnabledTestEnvironment implements ComponentConfigurator {

    @Test
    void shouldProvideAjaxBehavior() {
        assertNotNull(JsfBehaviorComponent.ajaxBehavior(getFacesContext()));
    }

    @Override
    public void configureComponents(ComponentConfigDecorator decorator) {
        decorator.registerBehavior(AjaxBehavior.BEHAVIOR_ID, AjaxBehavior.class);
    }
}
