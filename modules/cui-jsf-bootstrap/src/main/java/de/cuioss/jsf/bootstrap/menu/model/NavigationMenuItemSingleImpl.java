package de.cuioss.jsf.bootstrap.menu.model;

import java.util.HashMap;
import java.util.Map;

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
public class NavigationMenuItemSingleImpl extends NavigationMenuItemImpl implements NavigationMenuItemSingle {

    private static final long serialVersionUID = -4292374413272966456L;

    @Getter
    @Setter
    private String outcome;

    @Getter
    @Setter
    private String target;

    @Getter
    @Setter
    private String onClickAction;

    @Getter
    private final Map<String, String> outcomeParameter = new HashMap<>();

    @Getter
    @Setter
    private String labelKey;

    @Getter
    @Setter
    private String labelValue;

    /**
     * @param order
     */
    public NavigationMenuItemSingleImpl(Integer order) {
        super(order);
    }

    @Override
    public String getResolvedLabel() {
        return LabelResolver.builder()
                .withLabelKey(labelKey)
                .withLabelValue(labelValue).build().resolve(FacesContext.getCurrentInstance());
    }
}
