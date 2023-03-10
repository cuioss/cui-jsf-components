package de.cuioss.jsf.api.components.support;

import static java.util.Objects.requireNonNull;

import de.cuioss.jsf.api.components.partial.ComponentBridge;
import de.cuioss.jsf.api.components.util.KeyMappingUtility;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Some attribute like disabled and the styleclass needs to be changed once.
 * PostAddToView does not fit, its a called on ajax requests also. This class
 * helps keeping track of said changes.
 *
 * @author Oliver Wolff
 */
@EqualsAndHashCode
@ToString(exclude = "componentBridge")
public class OneTimeCheck {

    private final ComponentBridge componentBridge;

    private static final String KEY = "oneTimeChange";

    private final String compoundKey;

    /**
     * @param componentBridge
     *            must not be null
     */
    public OneTimeCheck(final ComponentBridge componentBridge) {
        this.componentBridge = requireNonNull(componentBridge);
        this.compoundKey = KEY;
    }

    /**
     * Constructor. Needed only for cases, if a component uses more than one
     * {@link OneTimeCheck}
     *
     * @param componentBridge
     *            must not be null
     * @param extension
     *            to be appended to the key.
     */
    public OneTimeCheck(final ComponentBridge componentBridge, final String extension) {
        this.componentBridge = requireNonNull(componentBridge);
        this.compoundKey = KeyMappingUtility.mapKeyWithExtension(KEY, extension);
    }

    /**
     * @return the actual flag whether the check was done. Defaults to
     *         <code>false</code>
     */
    public boolean isChecked() {
        return (Boolean) this.componentBridge.stateHelper().eval(this.compoundKey, Boolean.FALSE);
    }

    /**
     * Sets the checked value
     *
     * @param checked
     *            to be set
     */
    public void setChecked(final boolean checked) {
        this.componentBridge.stateHelper().put(this.compoundKey, checked);
    }

    /**
     * Convenient method that reads the actual state and returns it. If it is
     * false it additionally sets the value to true
     *
     * @return the initial set value
     */
    public boolean readAndSetChecked() {
        final var current = isChecked();
        if (!current) {
            setChecked(true);
        }
        return current;
    }
}
