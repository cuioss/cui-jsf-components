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
package de.cuioss.jsf.test.mock.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.faces.application.ProjectStage;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.application.projectstage.CuiProjectStageAccessor;
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
