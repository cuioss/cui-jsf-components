package de.cuioss.jsf.api.common.accessor;

import static java.util.Objects.requireNonNull;

import javax.faces.context.FacesContext;

import de.cuioss.jsf.api.common.ManagedBeansProvider;

/**
 * @deprecated use CuiBeanManager instead
 * <p>
 *             {@link ManagedAccessor} for managed Beans utilizing {@link ManagedBeansProvider}.
 *
 * @author Oliver Wolff
 * @param <T> identifying the concrete type
 */
@Deprecated // use CuiBeanManager instead
public class ManagedBeanAccessor<T> implements ManagedAccessor<T> {

    private static final long serialVersionUID = -2141438851655379869L;

    /** The 'safely' stored value. */
    private transient T value;

    /** Flag defining whether to store locally or always resolve from managed Bean context. */
    private final boolean alwaysResolve;

    /**
     * Holder for the value type to be provided by this accessor.
     */
    private final Class<T> valueType;

    /** EL String for the actual bean lookup. */
    private final String beanLookupName;

    /**
     * Constructor.
     *
     * @param beanLookupName must not be null, must determine an existing bean. It can be either an
     *            EL-expression or the plain name.
     * @param valueType must not be null, holder for the value type to be provided by this accessor.
     * @param alwaysResolve Flag defining whether to store locally or always resolve from managed
     *            Bean context.
     */
    public ManagedBeanAccessor(final String beanLookupName, final Class<T> valueType, final boolean alwaysResolve) {
        this.valueType = requireNonNull(valueType);
        this.beanLookupName = ManagedBeansProvider.checkManagedBeanKey(beanLookupName);
        this.alwaysResolve = alwaysResolve;
    }

    @Override
    public final T getValue() {
        if (null == value || alwaysResolve) {
            value =
                ManagedBeansProvider.getManagedBean(beanLookupName, valueType,
                        FacesContext.getCurrentInstance());
        }
        return value;
    }
}
