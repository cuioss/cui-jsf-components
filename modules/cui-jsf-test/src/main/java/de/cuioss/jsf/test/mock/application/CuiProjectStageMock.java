package de.cuioss.jsf.test.mock.application;

import javax.faces.application.ProjectStage;

import de.cuioss.jsf.api.application.projectstage.CuiProjectStageAccessor;
import de.cuioss.test.jsf.config.BeanConfigurator;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.decorator.BeanConfigDecorator;
import de.cuioss.uimodel.application.CuiProjectStage;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * It can be easily configured as bean by using {@link JsfTestConfiguration}
 *
 * @author Oliver Wolff
 * @author Sven Haag
 */
@EqualsAndHashCode(of = "projectStage")
@ToString(of = "projectStage")
public class CuiProjectStageMock implements CuiProjectStage, BeanConfigurator {

    private static final long serialVersionUID = -6665739575126496753L;

    @Getter
    @Setter
    private ProjectStage projectStage = ProjectStage.Production;

    @Override
    public void configureBeans(final BeanConfigDecorator decorator) {
        decorator.register(this, CuiProjectStageAccessor.BEAN_NAME);
    }

    @Override
    public boolean isDevelopment() {
        return ProjectStage.Development.equals(projectStage);
    }

    @Override
    public boolean isProduction() {
        return ProjectStage.Production.equals(projectStage);
    }

    @Override
    public boolean isConfiguration() {
        return false;
    }

    @Override
    public boolean isTest() {
        return ProjectStage.SystemTest.equals(projectStage);
    }
}
