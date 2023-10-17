package de.cuioss.jsf.api;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;

import de.cuioss.portal.common.stage.ProjectStage;
import lombok.Getter;
import lombok.Setter;

/** Simple Mock for dealing with {@link ProjectStage} for unit-testing. */
@ApplicationScoped
public class ProjectStageProducerMock {

    @Getter
    @Setter
    @Produces
    @RequestScoped
    ProjectStage projectStage = ProjectStage.PRODUCTION;
}
