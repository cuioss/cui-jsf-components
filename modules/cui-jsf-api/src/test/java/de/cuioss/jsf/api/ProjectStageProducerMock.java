package de.cuioss.jsf.api;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Produces;

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
