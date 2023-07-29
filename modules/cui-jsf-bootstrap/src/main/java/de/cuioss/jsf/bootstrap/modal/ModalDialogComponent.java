package de.cuioss.jsf.bootstrap.modal;

import static de.cuioss.tools.base.Preconditions.checkArgument;
import static de.cuioss.tools.string.MoreStrings.isEmpty;

import javax.faces.application.ResourceDependency;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponent;

import org.omnifaces.util.State;

import de.cuioss.jsf.api.components.base.BaseCuiPanel;
import de.cuioss.jsf.api.components.partial.ComponentStyleClassProvider;
import de.cuioss.jsf.api.components.partial.ComponentStyleClassProviderImpl;
import de.cuioss.jsf.api.components.partial.ContextSizeProvider;
import de.cuioss.jsf.api.components.partial.ContextStateProvider;
import de.cuioss.jsf.api.components.partial.FooterProvider;
import de.cuioss.jsf.api.components.partial.HeaderProvider;
import de.cuioss.jsf.api.components.partial.StyleAttributeProvider;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.CssBootstrap;
import lombok.experimental.Delegate;

/**
 * <p>
 * Render a twitter bootstrap based modal-dialog. Supported facets are header,
 * and footer. In case of the header you can use headerKey / headerValue. The
 * same for footerKey /footerValue as text content for footer. There are
 * different general styles available (state): one of 'primary', 'success',
 * 'info', 'warning', 'danger'. If none of those is set it uses 'default'.
 * </p>
 * <p>
 * <em>Caution</em>: default setting of dialog is closable, if you deactivate
 * this you should care about close functionality of the dialog.
 * </p>
 * <h2>Attributes</h2>
 * <ul>
 * <li>size: Supported values are 'sm', 'lg', 'fluid' based on bootstrap screen
 * sizes. 'fluid' uses max width of screen. As default no size setting is
 * set.</li>
 * <li>id: The id is a required attribute. It must be unique within the page
 * context. It is used to identify the component from the corresponding
 * {@link ModalControl}. It is rendered as "data-modal-dialog-id" in order to
 * select the element for the javascript call</li>
 * <li>closable: Defines if the dialog is closable on click on overlay
 * background or default close button. If value is set to false you need to
 * provide a closing functionality by your own. Default value is 'true'</li>
 * <li>animation: Optional attribute to control the appearing of the dialog:
 * currently supported is 'fade'</li>
 * <li>{@link ContextStateProvider}</li>
 * <li>{@link HeaderProvider}</li>
 * <li>{@link StyleAttributeProvider}</li>
 * <li>{@link FooterProvider}</li>
 * <li>{@link StyleAttributeProvider}</li>
 * </ul>
 * <h2>Usage</h2>
 *
 * <pre>
 * &lt;boot:button labelValue="Open"&gt;
 *   &lt;cui:modalControl for="dialogId"/&gt;
 * &lt;/boot:button&gt;
 * &lt;boot:modalDialog id="dialogId" headerValue="Dialog-Header"&gt;
 *   Some Dialog Content
 * &lt;/boot:modalDialog&gt;
 * </pre>
 * <p>
 * <em>Opening</em>: Use {@link ModalControl} attached to button or other
 * control for opening a concrete dialog.
 * </p>
 *
 * @author Oliver Wolff
 *
 */
@ResourceDependency(library = "thirdparty.js", name = "bootstrap.js")
@FacesComponent(BootstrapFamily.MODAL_DIALOG_COMPONENT)
@SuppressWarnings("squid:MaximumInheritanceDepth") // Artifact of Jsf-structure
public class ModalDialogComponent extends BaseCuiPanel implements StyleAttributeProvider {

    private static final String CLOASABLE_KEY = "closable";
    private static final String ANIMATION_KEY = "animation";

    @Delegate
    private final ContextStateProvider contextStateProvider;

    @Delegate
    private final HeaderProvider headerProvider;

    @Delegate
    private final FooterProvider footerProvider;

    private final ComponentStyleClassProvider styleClassProvider;
    private final ContextSizeProvider contextSizeProvider;

    private final State state;

    @SuppressWarnings("javadoc")
    public ModalDialogComponent() {
        super.setRendererType(BootstrapFamily.MODAL_DIALOG_COMPONENT_RENDERER);
        contextStateProvider = new ContextStateProvider(this);

        headerProvider = new HeaderProvider(this);
        footerProvider = new FooterProvider(this);
        styleClassProvider = new ComponentStyleClassProviderImpl(this);

        state = new State(getStateHelper());
        contextSizeProvider = new ContextSizeProvider(this);
    }

    /**
     * @return boolean indicating whether the modalDialog is closable
     */
    public boolean isClosable() {
        return state.<Boolean>get(CLOASABLE_KEY, Boolean.TRUE);
    }

    @Override
    public String getStyleClass() {
        return CssBootstrap.MODAL.getStyleClassBuilder()
                .append(contextStateProvider.resolveContextState()
                        .getStyleClassBuilderWithPrefix(CssBootstrap.MODAL.getStyleClass()))
                .append(getAnimation()).append(styleClassProvider).getStyleClass();
    }

    @Override
    public void setStyleClass(final String styleClass) {
        styleClassProvider.setStyleClass(styleClass);
    }

    /**
     * @param closable
     */
    public void setClosable(final boolean closable) {
        state.put(CLOASABLE_KEY, closable);
    }

    @Override
    public String getFamily() {
        return BootstrapFamily.COMPONENT_FAMILY;
    }

    /**
     * @param size
     */
    public void setSize(final String size) {
        contextSizeProvider.setSize(size);
    }

    /**
     * @return the size as String
     */
    public String getSize() {
        return contextSizeProvider.getSize();
    }

    /**
     * @param animation
     */
    public void setAnimation(final String animation) {
        state.put(ANIMATION_KEY, animation);

    }

    /**
     * @return the animation String
     */
    public String getAnimation() {
        return state.get(ANIMATION_KEY);
    }

    /**
     * @return the {@link UIComponent#getId} or throws an
     *         {@link IllegalArgumentException} if none is set.
     */
    public String resolveDialogId() {
        var id = super.getId();
        checkArgument(!isEmpty(id),
                "The dialog uses the id of the component as uniques identifier, therefore it must be set");
        return id;
    }

}
