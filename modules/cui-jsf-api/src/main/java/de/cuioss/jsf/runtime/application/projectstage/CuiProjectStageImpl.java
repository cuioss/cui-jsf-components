package de.cuioss.jsf.runtime.application.projectstage;

import javax.annotation.PostConstruct;
import javax.faces.application.ProjectStage;
import javax.faces.context.FacesContext;

import de.cuioss.uimodel.application.CuiProjectStage;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Helper class utilized for accessing Project Stage information in a convenient way. It needs to be
 * registered as an Application Scoped bean, because the Project stage does not change between
 * startup.
 *
 * @author Oliver Wolff
 * @author Sven Haag, Sven Haag
 */
@EqualsAndHashCode
@ToString
public class CuiProjectStageImpl implements CuiProjectStage {

    private static final long serialVersionUID = -2464134252511225231L;

    @Getter
    private boolean development;

    @Getter
    private boolean test;

    @Getter
    private boolean configuration;

    @Getter
    private boolean production;

    /**
     * Initializes the bean. See class documentation for expected result.
     */
    @PostConstruct
    public void initBean() {
        var projectStage = FacesContext.getCurrentInstance().getApplication().getProjectStage();
        development = ProjectStage.Development.equals(projectStage);
        test = ProjectStage.SystemTest.equals(projectStage);
        configuration = false; // no JSF equivalent in existence
        production = ProjectStage.Production.equals(projectStage);
    }
}
