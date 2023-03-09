package com.icw.ehf.cui.core.api.components.partial;

import javax.faces.component.StateHelper;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;

import com.icw.ehf.cui.core.api.components.html.Node;

import lombok.experimental.Delegate;

/**
 * Uses all available provider
 *
 * @author Oliver Wolff
 */
@SuppressWarnings("javadoc")
public class MockPartialComponent extends UIComponentBase implements ComponentBridge {

    @Delegate
    private final TitleProvider titleProvider;

    @Delegate
    private final CloseButtonTitleProvider closeButtonTitleProvider;

    @Delegate
    private final ContentProvider contentProvider;

    @Delegate
    private final ContextSizeProvider contextSizeProvider;

    @Delegate
    private final ContextStateProvider contextStateProvider;

    @Delegate
    private final ComponentStyleClassProvider styleClassProvider;

    @Delegate
    private final HeaderProvider headerProvider;

    @Delegate
    private final FooterProvider footerProvider;

    @Delegate
    private final IconProvider iconProvider;

    @Delegate
    private final LabelProvider labelProvider;

    @Delegate
    private final AlignProvider alignProvider;

    @Delegate
    private final IconAlignProvider iconAlignProvider;

    @Delegate
    private final ModelProvider modelProvider;

    @Delegate
    private final StyleAttributeProvider styleAttributeProvider;

    @Delegate
    private final PlaceholderProvider placeholderProvider;

    @Delegate
    private final DisabledComponentProvider disabledComponentProvider;

    @Delegate
    private final CollapseSwitchProvider collapseSwitchProvider;

    @Delegate
    private final ActiveIndexManagerProvider activeIndexManagerProvider;

    @Delegate
    private final DeferredProvider deferredProvider;

    @Delegate
    private final HtmlElementProvider htmlElementProvider;

    @Delegate
    private final AjaxProvider ajaxProvider;

    public MockPartialComponent() {
        titleProvider = new TitleProviderImpl(this);
        closeButtonTitleProvider = new CloseButtonTitleProvider(this);
        contentProvider = new ContentProvider(this);
        contextSizeProvider = new ContextSizeProvider(this);
        contextStateProvider = new ContextStateProvider(this);
        styleClassProvider = new ComponentStyleClassProviderImpl(this);
        headerProvider = new HeaderProvider(this);
        footerProvider = new FooterProvider(this);
        iconProvider = new IconProvider(this);
        labelProvider = new LabelProvider(this);
        alignProvider = new AlignProvider(this);
        iconAlignProvider = new IconAlignProvider(this);
        modelProvider = new ModelProvider(this);
        styleAttributeProvider = new StyleAttributeProviderImpl(this);
        placeholderProvider = new PlaceholderProvider(this);
        disabledComponentProvider = new DisabledComponentProvider(this);
        collapseSwitchProvider = new CollapseSwitchProvider(this);
        activeIndexManagerProvider = new ActiveIndexManagerProvider(this);
        deferredProvider = new DeferredProvider(this);
        htmlElementProvider = new HtmlElementProvider(this, Node.DIV);
        ajaxProvider = new AjaxProvider(this);
    }

    @Override
    public StateHelper stateHelper() {
        return this.getStateHelper();
    }

    @Override
    public FacesContext facesContext() {
        return getFacesContext();
    }

    @Override
    public String getFamily() {
        return "MockPartialComponent";
    }

    @Override
    public UIComponent facet(String facetName) {
        return getFacet(facetName);
    }
}
