package de.cuioss.jsf.bootstrap.layout;

import javax.faces.component.FacesComponent;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ListenerFor;
import javax.faces.event.PreRenderComponentEvent;

import de.cuioss.jsf.api.components.css.StyleClassBuilder;
import de.cuioss.jsf.api.components.partial.ContextSizeProvider;
import de.cuioss.jsf.api.components.util.CuiState;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.CssBootstrap;
import lombok.experimental.Delegate;

/**
 * <p>
 * Renders a bootstrap conform div with the styleClass 'toolbar'.
 * </p>
 * <p>
 * It uses the affix plugin for positioning the toolbar, see Attributes for details
 * </ul>
 * <p>
 * More information and examples can be found in the <a href=
 * "https://cuioss.de/cui-reference-documentation/faces/pages/documentation/cui_components/demo/layout.jsf"
 * >Reference Documentation</a>
 * </p>
 * <h2>Attributes</h2>
 * <ul>
 * <li>{@link ContextSizeProvider}</li>
 * <li>pinToTop: Indicates whether to pin this component to the top. The default value for this
 * property is false.</li>
 * <li>pinTopOffset: Defines the offset of this component when pinned to top. The default value for
 * this property is 5.</li>
 * <li>pinToBottom: Indicates whether to pin this component to the bottom. The default value for
 * this property is false.</li>
 * <li>pinBottomOffset: Defines the offset of this component when pinned to bottom. The default
 * value for this property is 5.</li>
 * </ul>
 * <h2>Usage</h2>
 *
 * <pre>
 *  {@code <cui:toolbar />}
 * </pre>
 *
 * <h2>Styling</h2>
 * <ul>
 * <li>The marker css class is 'toolbar'</li>
 * </ul>
 *
 * @author Oliver Wolff
 *
 */
@FacesComponent(BootstrapFamily.TOOLBAR_COMPONENT)
@ListenerFor(systemEventClass = PreRenderComponentEvent.class)
@SuppressWarnings("squid:MaximumInheritanceDepth") // Artifact of Jsf-structure
public class ToolbarComponent extends AbstractLayoutComponent {

    private static final String PIN_BOTTOM_OFFSET_KEY = "pinBottomOffset";

    private static final String PIN_TOP_OFFSET_KEY = "pinTopOffset";

    private static final String PIN_TO_BOTTOM_KEY = "pinToBottom";

    private static final String PIN_TO_TOP_KEY = "pinToTop";

    private static final int DEFAULT_OFFSET = 5;

    /** "data-spy" */
    public static final String DATA_SPY_ATTRIBUTE = "data-spy";

    /** "affix" */
    public static final String DATA_SPY_VALUE = "affix";

    /** "data-offset-top" */
    public static final String DATA_OFFSET_TOP = "data-offset-top";

    /** "data-offset-bottom" */
    public static final String DATA_OFFSET_BOTTOM = "data-offset-bottom";

    private static final String TOOLBAR_CSS_PREFIX = "toolbar-";

    private final CuiState state;

    @Delegate
    private final ContextSizeProvider contextSizeProvider;

    /**
     *
     */
    public ToolbarComponent() {
        super();
        super.setRendererType(BootstrapFamily.LAYOUT_RENDERER);
        state = new CuiState(getStateHelper());
        contextSizeProvider = new ContextSizeProvider(this);
    }

    @Override
    public void processEvent(final ComponentSystemEvent event) {
        if (event instanceof PreRenderComponentEvent) {
            var attributes = super.getPassThroughAttributes(true);
            // ensure correct reaction on dynamic-changes
            attributes.remove(DATA_SPY_ATTRIBUTE);
            attributes.remove(DATA_OFFSET_TOP);
            attributes.remove(DATA_OFFSET_BOTTOM);
            var pinToBottom = getPinToBottom();
            var pinToTop = getPinToTop();
            if (pinToBottom || pinToTop) {
                attributes.put(DATA_SPY_ATTRIBUTE, DATA_SPY_VALUE);
                if (pinToTop) {
                    attributes.put(DATA_OFFSET_TOP, getPinTopOffset());
                }
                if (pinToBottom) {
                    attributes.put(DATA_OFFSET_BOTTOM, getPinBottomOffset());
                }
            }

        }
        super.processEvent(event);
    }

    @Override
    public StyleClassBuilder resolveStyleClass() {
        var styleClassBuilder = CssBootstrap.TOOLBAR.getStyleClassBuilder().append(super.getStyleClass());
        if (getPinToBottom() || getPinToTop()) {
            styleClassBuilder.append(CssBootstrap.TOOLBAR_PINNED);
        }
        var context = TOOLBAR_CSS_PREFIX + contextSizeProvider.resolveContextSize().name().toLowerCase();

        return styleClassBuilder.append(context);
    }

    /**
     * @param value
     *            to be set
     */
    public void setPinToTop(final boolean value) {
        state.put(PIN_TO_TOP_KEY, value);
    }

    /**
     * @return the current value for pinToTop
     */
    public boolean getPinToTop() {
        return state.getBoolean(PIN_TO_TOP_KEY, false);
    }

    /**
     * @param value
     *            to be set
     */
    public void setPinToBottom(final boolean value) {
        state.put(PIN_TO_BOTTOM_KEY, value);
    }

    /**
     * @return the current value for pinToBottom
     */
    public boolean getPinToBottom() {
        return state.getBoolean(PIN_TO_BOTTOM_KEY, false);
    }

    /**
     * @param value
     *            to be set
     */
    public void setPinTopOffset(final int value) {
        state.put(PIN_TOP_OFFSET_KEY, value);
    }

    /**
     * @return the current value for pinTopOffset
     */
    public int getPinTopOffset() {
        return state.getInt(PIN_TOP_OFFSET_KEY, DEFAULT_OFFSET);
    }

    /**
     * @param value
     *            to be set
     */
    public void setPinBottomOffset(final int value) {
        state.put(PIN_BOTTOM_OFFSET_KEY, value);
    }

    /**
     * @return the current value for pinTopOffset
     */
    public int getPinBottomOffset() {
        return state.getInt(PIN_BOTTOM_OFFSET_KEY, DEFAULT_OFFSET);
    }
}
