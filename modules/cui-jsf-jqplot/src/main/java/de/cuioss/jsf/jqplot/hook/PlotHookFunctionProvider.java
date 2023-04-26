package de.cuioss.jsf.jqplot.hook;

import java.io.Serializable;

/**
 * JQplot JavaScript Extensions.
 *
 * @see <a href="http://www.jqplot.com/docs/index/Hooks.html">Hooks</a>
 * @author Oliver Wolff
 */
public interface PlotHookFunctionProvider extends Serializable {

    /**
     * Identifier or name has no physical benefit but is useful to detect mistakes
     *
     * @return identifier or name
     */
    String getIdentifier();

    /**
     * @return hook function code
     */
    String getHookFunctionCode();

}
