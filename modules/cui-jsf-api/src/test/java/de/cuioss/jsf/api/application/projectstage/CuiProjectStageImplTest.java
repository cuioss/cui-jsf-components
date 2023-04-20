package de.cuioss.jsf.api.application.projectstage;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.faces.application.ProjectStage;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.application.projectstage.CuiProjectStageAccessor;
import de.cuioss.jsf.api.application.projectstage.CuiProjectStageImpl;
import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.test.jsf.junit5.JsfEnabledTestEnvironment;
import de.cuioss.test.valueobjects.api.property.PropertyReflectionConfig;

@PropertyReflectionConfig(skip = true)
class CuiProjectStageImplTest extends JsfEnabledTestEnvironment {

    private final TypedGenerator<ProjectStage> projectStages = Generators.enumValues(ProjectStage.class);

    @Test
    void testIsDevelopment() {
        final var underTest = anyBean();
        setProjectStage(ProjectStage.Development);
        underTest.initBean();
        assertTrue(underTest.isDevelopment());
        setProjectStage(ProjectStage.Production);
        underTest.initBean();
        assertFalse(underTest.isDevelopment());
    }

    @Test
    void shouldBeProvidedByAccessor() {
        getBeanConfigDecorator().register(anyBean(), CuiProjectStageAccessor.BEAN_NAME);
        assertNotNull(new CuiProjectStageAccessor().getValue());
    }

    @Test
    void testIsProduction() {
        final var underTest = anyBean();
        setProjectStage(ProjectStage.Development);
        underTest.initBean();
        assertFalse(underTest.isProduction());
        setProjectStage(ProjectStage.Production);
        underTest.initBean();
        assertTrue(underTest.isProduction());
    }

    @Test
    void testIsConfiguration() {
        final var underTest = anyBean();
        underTest.initBean();
        assertFalse(underTest.isConfiguration());
    }

    @Test
    void testIsTest() {
        final var underTest = anyBean();
        setProjectStage(ProjectStage.SystemTest);
        underTest.initBean();
        assertTrue(underTest.isTest());
    }

    private CuiProjectStageImpl anyBean() {
        var projectStageImpl = new CuiProjectStageImpl();
        setProjectStage(projectStages.next());
        projectStageImpl.initBean();
        return projectStageImpl;
    }

    private void setProjectStage(final ProjectStage stage) {
        getApplicationConfigDecorator().setProjectStage(stage);
    }
}
