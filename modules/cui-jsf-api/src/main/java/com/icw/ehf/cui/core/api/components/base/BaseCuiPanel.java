package com.icw.ehf.cui.core.api.components.base;

import static java.util.Objects.requireNonNull;

import javax.faces.component.StateHelper;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.context.FacesContext;

import com.icw.ehf.cui.core.api.components.partial.ComponentBridge;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Base class for creating custom panel based components. Therefore it extends
 * {@link HtmlPanelGroup} and acts as {@link ComponentBridge}
 *
 * @author Oliver Wolff
 */
public class BaseCuiPanel extends HtmlPanelGroup implements ComponentBridge {

    /**
     * Default Constructor, defines a div panel {@link PanelType#DIV}
     */
    public BaseCuiPanel() {
        this(PanelType.DIV);
    }

    /**
     * @param panelType
     *            , must not be null
     */
    public BaseCuiPanel(final PanelType panelType) {
        super();
        requireNonNull(panelType);
        super.setLayout(panelType.getIdentifier());
    }

    @Override
    public StateHelper stateHelper() {
        return getStateHelper();
    }

    @Override
    public FacesContext facesContext() {
        return getFacesContext();
    }

    @Override
    public UIComponent facet(final String facetName) {
        return getFacet(facetName);
    }

    /**
     * Simple enum indicating whether to render a div or span element. It
     * corresponds with {@link HtmlPanelGroup#setLayout(String)}
     *
     * @author Oliver Wolff
     */
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public enum PanelType {

        /** Div block. */
        DIV("block"),

        /** Span block. */
        SPAN("span");

        @Getter
        private final String identifier;
    }
}
