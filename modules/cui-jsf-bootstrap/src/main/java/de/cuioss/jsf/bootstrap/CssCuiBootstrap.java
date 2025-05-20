/*
 * Copyright 2023 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.bootstrap;

import de.cuioss.jsf.api.components.css.StyleClassProvider;
import de.cuioss.jsf.bootstrap.icon.LabeledIconComponent;
import de.cuioss.jsf.bootstrap.layout.messages.CuiMessagesComponent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum providing CUI-specific CSS class names that extend or complement Bootstrap styling.
 * Implements {@link StyleClassProvider} for consistent component integration.
 *
 * @author Oliver Wolff
 * @since 1.0
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum CssCuiBootstrap implements StyleClassProvider {

    /**
     * "cui-collapsible"
     */
    CUI_COLLAPSIBLE("cui-collapsible"),

    /**
     * "cui-collapsible-icon"
     */
    CUI_COLLAPSIBLE_ICON("cui-icon cui-collapsible-icon"),

    /**
     * "cui-deferred-loading-icon"
     */
    CUI_DEFERRED_LOADING_ICON("cui-deferred-loading-icon"),

    /**
     * cui-icon-restart
     */
    CUI_ICON_RESTART("cui-icon-restart"),

    /**
     * cui-icon-unlock
     */
    CUI_ICON_UNLOCK("cui-icon-unlock"),

    /**
     * CUI-Message: state: error
     */
    CUI_MESSAGE_ERROR("cui_msg_error"),

    /**
     * CUI-Message: state: fatal
     */
    CUI_MESSAGE_FATAL("cui_msg_fatal"),

    /**
     * CUI-Message: state: info
     */
    CUI_MESSAGE_INFO("cui_msg_info"),

    /**
     * CUI-Message: list
     */
    CUI_MESSAGE_LIST("cui_message_list"),

    /**
     * CUI-Message: state: warn
     */
    CUI_MESSAGE_WARN("cui_msg_warn"),

    /**
     * used for {@link CuiMessagesComponent}
     */
    CUI_MESSAGES_CLASS("cui-messages"),

    /**
     * "cui-panel"
     */
    CUI_PANEL("cui-panel"),

    /**
     * "cui-lazy-loading"
     */
    CUI_LAZY_LOADING("cui-lazy-loading"),

    /**
     * "cui_required"
     */
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

    /**
     * The tag component, maps to bootstrap label: "cui-tag"
     */
    TAG("cui-tag"),

    /**
     * Tag component Disposed/remove-button
     */
    TAG_DISPOSE_BUTTON("cui-icon cui-tag-dispose-button"),

    /**
     * Navigation Menu Item for text
     */
    CUI_NAVIGATION_MENU_TEXT("menu-item-text"),

    /**
     * Needed for PrimeFaces
     */
    UI_HIDDEN_CONTAINER("ui-hidden-container"),

    INPUT_HELP_TEXT_ACTION("input-help-text-action");

    @Getter
    private final String styleClass;

}
