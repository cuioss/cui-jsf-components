package com.icw.ehf.cui.components.bootstrap;

import com.icw.ehf.cui.components.bootstrap.icon.LabeledIconComponent;
import com.icw.ehf.cui.components.bootstrap.layout.messages.CuiMessagesComponent;
import com.icw.ehf.cui.core.api.components.css.StyleClassBuilder;
import com.icw.ehf.cui.core.api.components.css.StyleClassProvider;
import com.icw.ehf.cui.core.api.components.css.impl.StyleClassBuilderImpl;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Provides css-classes that are related to Bootstrap classes but are from the
 * cui-namespace
 *
 * @author Oliver Wolff
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum CssCuiBootstrap implements StyleClassProvider {

    /** "cui-collapsible" */
    CUI_COLLAPSIBLE("cui-collapsible"),

    /** "cui-collapsible-icon" */
    CUI_COLLAPSIBLE_ICON("cui-icon cui-collapsible-icon"),

    /** "cui-deferred-loading-icon" */
    CUI_DEFERRED_LOADING_ICON("cui-deferred-loading-icon"),

    /** cui-icon-restart */
    CUI_ICON_RESTART("cui-icon-restart"),

    /** cui-icon-unlock */
    CUI_ICON_UNLOCK("cui-icon-unlock"),

    /** CUI-Message: state: error */
    CUI_MESSAGE_ERROR("cui_msg_error"),

    /** CUI-Message: state: fatal */
    CUI_MESSAGE_FATAL("cui_msg_fatal"),

    /** CUI-Message: state: info */
    CUI_MESSAGE_INFO("cui_msg_info"),

    /** CUI-Message: list */
    CUI_MESSAGE_LIST("cui_message_list"),

    /** CUI-Message: state: warn */
    CUI_MESSAGE_WARN("cui_msg_warn"),

    /** used for {@link CuiMessagesComponent} */
    CUI_MESSAGES_CLASS("cui-messages"),

    /** "cui-panel" */
    CUI_PANEL("cui-panel"),

    /** "cui-lazy-loading" */
    CUI_LAZY_LOADING("cui-lazy-loading"),

    /** "cui_required" */
    CUI_REQUIRED("cui_required"),

    /**
     * {@link LabeledIconComponent}: style-class for the text field:
     * "cui-labeled-icon-text"
     */
    LABELED_ICON_TEXT("cui-labeled-icon-text"),

    /**
     * {@link LabeledIconComponent}: style-class for the wrapping span:
     * "cui-labeled-icon"
     */
    LABELED_ICON_WRAPPER("cui-labeled-icon"),

    /** The tag component, maps to bootstrap label: "cui-tag" */
    TAG("cui-tag"),

    /** Tag component Disposed/remove-button */
    TAG_DISPOSE_BUTTON("cui-icon cui-tag-dispose-button"),

    /** Navigation Menu Item for text */
    CUI_NAVIGATION_MENU_TEXT("menu-item-text"),

    /** Needed for PrimeFaces */
    UI_HIDDEN_CONTAINER("ui-hidden-container"),

    INPUT_HELP_TEXT_ACTION("input-help-text-action");

    @Getter
    private final String styleClass;

    @Override
    public StyleClassBuilder getStyleClassBuilder() {
        return new StyleClassBuilderImpl(styleClass);
    }
}
