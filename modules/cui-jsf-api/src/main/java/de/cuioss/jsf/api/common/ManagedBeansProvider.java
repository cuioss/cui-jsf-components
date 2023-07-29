package de.cuioss.jsf.api.common;

import static de.cuioss.tools.string.MoreStrings.emptyToNull;
import static java.util.Objects.requireNonNull;

import javax.el.ELException;
import javax.faces.context.FacesContext;

/**
 * @deprecated use CuiBeanManager instead
 *
 *             Provide methods to retrieve managed beans from EL context.
 *
 */
@Deprecated // use CuiBeanManager instead
public final class ManagedBeansProvider {

    /** Wraps textual names to EL-expressions. */
    @SuppressWarnings("el-syntax")
    public static final String EL_WRAPPER = "#{%s}";

    /** String constant used for checking if given String is EL-Expression. */
    @SuppressWarnings("el-syntax")
    public static final String EL_START = "#{";

    /**
     * <p>
     * Retrieve a JSF managed bean instance by name. If the bean has never been
     * accessed before then it will likely be instantiated by the JSF framework
     * during the execution of this method.
     * </p>
     *
     * @param <T>            return type of retrieved object
     * @param managedBeanKey EL-String containing the name of the managed bean, e.g.
     *                       "#{managedBeanKey}". If it is "managedBeanKey" the
     *                       EL-Expression will be generated. Must not be null nor
     *                       empty
     * @param clazz          Class object that corresponds to the expected managed
     *                       bean type. Must not be null
     * @param facesContext   to be utilized. Must not be null
     * @return T retrieved object
     * @throws IllegalArgumentException when the supplied key does not resolve to
     *                                  any managed bean or when a managed bean is
     *                                  found but the object is not of type T
     * @throws NullPointerException     if one of the parameter is {@code null}
     * @throws IllegalStateException    may occur in corner cases, where
     *                                  {@link FacesContext} is not initialized
     *                                  properly
     */
    public static <T> T getManagedBean(final String managedBeanKey, final Class<T> clazz,
            final FacesContext facesContext) {
        requireNonNull(clazz, "Expected bean type must not be null");
        requireNonNull(facesContext, "FacesContext must not be null");
        var checkedManagedBeanKey = checkManagedBeanKey(managedBeanKey);

        T loadedBean = null;
        try {
            loadedBean = facesContext.getApplication().evaluateExpressionGet(facesContext, checkedManagedBeanKey,
                    clazz);
        } catch (NullPointerException | ELException e) {
            // These are rare cases, usually on startup with incomplete FacesContext
            // definitions
            throw new IllegalStateException(
                    "Could not load bean of type " + clazz.getName() + " and name " + checkedManagedBeanKey, e);
        }
        if (null == loadedBean) {
            throw new IllegalArgumentException(
                    "Could not load bean of type " + clazz.getName() + " and name " + checkedManagedBeanKey);
        }
        return loadedBean;
    }

    /**
     * In case the beanKey is not an el expression (starting not with '#{') this
     * method wraps the expression accordingly.
     *
     * @param managedBeanKey must not be null or empty
     * @return the key wrapped as an EL-Expression if needed, otherwise the given
     *         key.
     */
    public static String checkManagedBeanKey(final String managedBeanKey) {
        requireNonNull(emptyToNull(managedBeanKey), "managedBeanKey must not be null nor empty");
        var elKey = managedBeanKey;
        if (!elKey.startsWith(EL_START)) {
            elKey = String.format(EL_WRAPPER, elKey);
        }
        return elKey;
    }

    /**
     * Enforce utility class
     */
    private ManagedBeansProvider() {

    }
}
