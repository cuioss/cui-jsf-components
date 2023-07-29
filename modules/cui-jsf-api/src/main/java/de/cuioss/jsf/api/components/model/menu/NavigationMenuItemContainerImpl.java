package de.cuioss.jsf.api.components.model.menu;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import de.cuioss.jsf.api.components.support.LabelResolver;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Basic Implementation of a {@link NavigationMenuItem}
 *
 * @author Sven Haag
 */
@EqualsAndHashCode(callSuper = true)
@ToString
public class NavigationMenuItemContainerImpl extends NavigationMenuItemImpl implements NavigationMenuItemContainer {

    private static final long serialVersionUID = 5453628443565248832L;

    @Getter
    @Setter
    private List<NavigationMenuItem> children = new ArrayList<>();

    @Getter
    @Setter
    private String labelKey;

    @Getter
    @Setter
    private String labelValue;

    /**
     * @param order
     */
    public NavigationMenuItemContainerImpl(Integer order) {
        super(order);
    }

    @Override
    public String getResolvedLabel() {
        return LabelResolver.builder().withLabelKey(labelKey).withLabelValue(labelValue).build()
                .resolve(FacesContext.getCurrentInstance());
    }
}
