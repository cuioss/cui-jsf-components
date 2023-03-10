package de.cuioss.jsf.api.components.javascript;

import static de.cuioss.tools.string.MoreStrings.nullToEmpty;

import de.cuioss.tools.string.Joiner;
import de.cuioss.tools.string.Splitter;

/**
 * Provides a simple way to create a correctly escaped JQuery-selector
 *
 * @author Oliver Wolff
 */
public abstract class JQuerySelector implements ScriptProvider {

    /**
     * The javaScript template for creating the jQuery-selector:
     * "jQuery('#%s')".
     */
    public static final String SELECTOR_TEMPLATE = "jQuery('#%s')";

    /**
     * @return the corresponding jQuerySelectorString, e.g for a given component
     *         providing the id "a:b" it returns "jQuery('#a\\\\:b')" saying it
     *         takes care on the proper masking of the clientIds.
     */
    @Override
    public String script() {
        return String.format(SELECTOR_TEMPLATE, escapeClientId(getIdString()));
    }

    protected abstract String getIdString();

    /**
     * Escapes a given id String in order to be used within javascript, e.g for a given component
     * providing the id "a:b" it returns "'a\\\\:b'" saying it
     * takes care on the proper masking of the clientIds.
     *
     * @param idString
     * @return the escaped String
     */
    public static final String escapeClientId(final String idString) {
        final Iterable<String> splitted = Splitter.on(':').splitToList(nullToEmpty(idString));
        return Joiner.on("\\\\:").join(splitted);
    }

}
