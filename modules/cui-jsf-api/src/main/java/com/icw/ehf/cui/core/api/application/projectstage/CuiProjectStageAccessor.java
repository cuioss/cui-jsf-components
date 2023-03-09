package com.icw.ehf.cui.core.api.application.projectstage;

import com.icw.ehf.cui.core.api.common.accessor.ManagedBeanAccessor;

import de.cuioss.uimodel.application.CuiProjectStage;

/**
 * Accesses instances of {@link CuiProjectStage}
 *
 * @author Oliver Wolff
 */
public class CuiProjectStageAccessor extends ManagedBeanAccessor<CuiProjectStage> {

    /**
     * Bean name for looking up instances.
     */
    public static final String BEAN_NAME = "cuiProjectStage";

    private static final long serialVersionUID = 706263142443297439L;

    /**
     * Constructor.
     */
    public CuiProjectStageAccessor() {
        super(BEAN_NAME, CuiProjectStage.class, false);
    }
}
