package com.icw.ehf.cui.mock.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.faces.application.ProjectStage;

import org.junit.jupiter.api.Test;

import com.icw.ehf.cui.core.api.application.projectstage.CuiProjectStageAccessor;

import de.cuioss.test.jsf.config.decorator.BeanConfigDecorator;
import de.cuioss.test.jsf.junit5.AbstractBeanTest;
import de.cuioss.test.valueobjects.api.property.PropertyReflectionConfig;
import de.cuioss.uimodel.application.CuiProjectStage;

@PropertyReflectionConfig(of = "projectStage", defaultValued = "projectStage")
class CuiProjectStageMockTest extends AbstractBeanTest<CuiProjectStageMock> {

    @Test
    void shouldRegisterAsBean() {
        assertNull(BeanConfigDecorator.getBean(CuiProjectStageAccessor.BEAN_NAME, getFacesContext(),
                de.cuioss.uimodel.application.CuiProjectStage.class));
        new CuiProjectStageMock().configureBeans(getBeanConfigDecorator());
        assertNotNull(BeanConfigDecorator.getBean(CuiProjectStageAccessor.BEAN_NAME, getFacesContext(),
                CuiProjectStage.class));
    }

    @Test
    void shouldDefaultToProduction() {
        var underTest = new CuiProjectStageMock();
        assertEquals(ProjectStage.Production, underTest.getProjectStage());
        assertTrue(underTest.isProduction());
    }

    @Test
    void shouldHandleChanges() {
        var underTest = new CuiProjectStageMock();
        assertTrue(underTest.isProduction());
        assertFalse(underTest.isDevelopment());
        assertFalse(underTest.isTest());
        assertFalse(underTest.isConfiguration());
        underTest.setProjectStage(ProjectStage.Development);
        assertTrue(underTest.isDevelopment());
        assertFalse(underTest.isProduction());
        assertFalse(underTest.isTest());
        assertFalse(underTest.isConfiguration());
    }
}
