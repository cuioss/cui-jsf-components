package de.cuioss.jsf.api.application.bundle;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import de.cuioss.tools.collect.CollectionBuilder;
import de.cuioss.tools.logging.CuiLogger;
import de.cuioss.tools.string.Joiner;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * Aggregates a number of configured {@link ResourceBundle} in order to unify
 * the access to multiple ResourceBundles.
 * </p>
 * <em>Caution: </em> If {@link ResourceBundleWrapperImpl} is not
 * {@link RequestScoped} you need to configure resourceBundleNames as well in
 * order to enable restoring of the wrapper after Serialization.
 *
 * @author Oliver Wolff
 */
@EqualsAndHashCode(doNotUseGetters = true, of = "resourceBundleNames")
@ToString(doNotUseGetters = true, of = "resourceBundleNames")
public class ResourceBundleWrapperImpl implements ResourceBundleWrapper {

    private static final CuiLogger log = new CuiLogger(ResourceBundleWrapperImpl.class);

    private static final long serialVersionUID = -5584301399919580111L;

    @Setter
    @Getter
    private transient List<ResourceBundle> resourceBundles = new ArrayList<>();

    @Getter
    @Setter
    private List<String> resourceBundleNames;

    /** Set representation of keys to be used by {@link #getKeys()} */
    private Set<String> keySet;

    @Override
    public String getMessage(final String key) {
        for (final ResourceBundle bundle : getBundles()) {
            if (bundle.containsKey(key)) {
                return bundle.getString(key);
            }
        }
        throw new MissingResourceException(
                "No key '" + key + "' defined within any of the configuredBundles: "
                        + Joiner.on(", ").skipNulls().join(resourceBundles.stream()
                                .map(ResourceBundle::getBaseBundleName).collect(Collectors.toList())),
                "ResourceBundleWrapperImpl", key);
    }

    /**
     * @return The list of {@link ResourceBundle}s managed by this wrapper
     */
    private List<ResourceBundle> getBundles() {
        if (null == resourceBundles || resourceBundles.isEmpty()) {
            resourceBundles = new ArrayList<>();
            if (null == resourceBundleNames || resourceBundleNames.isEmpty()) {
                log.error("Unable to restore ResourceBundles. They have been lost because of serialization");
            } else {
                final var context = FacesContext.getCurrentInstance();
                final var application = context.getApplication();
                for (final String name : resourceBundleNames) {
                    resourceBundles.add(application.getResourceBundle(context, name));
                }
            }
        }
        return resourceBundles;
    }

    @Override
    public Set<String> keySet() {
        if (null == keySet) {
            final var builder = new CollectionBuilder<String>();
            for (final ResourceBundle bundle : getBundles()) {
                builder.add(bundle.keySet());
            }
            keySet = builder.toImmutableSet();
        }
        return keySet;
    }

    @Override
    public String getBundleContent() {
        return Joiner.on(", ").skipNulls()
                .join(resourceBundles.stream().map(ResourceBundle::getBaseBundleName).collect(Collectors.toList()));
    }
}
