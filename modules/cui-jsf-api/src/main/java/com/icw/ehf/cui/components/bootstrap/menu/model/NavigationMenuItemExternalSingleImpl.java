package com.icw.ehf.cui.components.bootstrap.menu.model;

import javax.faces.context.FacesContext;

import com.icw.ehf.cui.core.api.components.support.LabelResolver;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Basic Implementation of a {@link NavigationMenuItem}
 *
 * @author Matthias Walliczek
 */
@EqualsAndHashCode(callSuper = true)
@ToString
public class NavigationMenuItemExternalSingleImpl extends NavigationMenuItemImpl
        implements NavigationMenuItemExternalSingle {

    private static final long serialVersionUID = -4292374413272966456L;

    @Getter
    @Setter
    private String hRef;

    @Getter
    @Setter
    private String target;

    @Getter
    @Setter
    private String onClickAction;

    @Getter
    @Setter
    private String labelKey;

    @Getter
    @Setter
    private String labelValue;

    /**
     * @param order
     */
    public NavigationMenuItemExternalSingleImpl(Integer order) {
        super(order);
    }

    @Override
    public String getResolvedLabel() {
        return LabelResolver.builder()
                .withLabelKey(labelKey)
                .withLabelValue(labelValue).build().resolve(FacesContext.getCurrentInstance());
    }
}
