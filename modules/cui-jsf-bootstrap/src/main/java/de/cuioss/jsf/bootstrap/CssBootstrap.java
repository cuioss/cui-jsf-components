/*
 * Copyright © 2025 CUI-OpenSource-Software (info@cuioss.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.bootstrap;

import de.cuioss.jsf.api.components.css.StyleClassProvider;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum providing standard Bootstrap CSS class names as constants.
 * Implements {@link StyleClassProvider} for easy integration with components.
 *
 * @author Oliver Wolff
 * @since 1.0
 */
@SuppressWarnings("LombokGetterMayBeUsed")
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum CssBootstrap implements StyleClassProvider {

    /** "alert". */
    ALERT("alert"),

    /** "alert-dismissible". */
    ALERT_DISMISSIBLE("alert-dismissible"),

    /** Badge Class */
    BADGE("badge"),

    /** Button class "btn" */
    BUTTON("btn"),

    /** The close style for button: "close" */
    BUTTON_CLOSE("close"),

    /** "btn-default" */
    BUTTON_DEFAULT("btn-default"),

    /** Text node within a button. */
    BUTTON_TEXT("btn-text"),

    /** "checkbox" */
    CHECKBOX("checkbox"),

    /** "collapse" CSS class indicating a collapsed element */
    COLLAPSE("collapse"),

    /** "collapse in" CSS classes indicating an expanded element */
    COLLAPSE_IN("collapse in"),

    /** "control-label". */
    CONTROL_LABEL("control-label"),

    /** CUI-Message */
    CUI_MESSAGE("help-block"),

    /** CUI-Additional-Message */
    CUI_ADDITIONAL_MESSAGE("additional-help-block"),

    /** Dashboard */
    DASHBOARD("dashboard-wrapper"),

    /** "form-control". */
    FORM_CONTROL("form-control"),

    /** "form-control-static". */
    FORM_CONTROL_STATIC("form-control-static"),

    /** Wraps a form group. */
    FORM_GROUP("form-group"),

    /** form-horizontal */
    FORM_HORIZONTAL("form-horizontal"),

    /** "has-error". */
    HAS_ERROR("has-error"),

    /** "has-warning" */
    HAS_WARNING("has-warning"),

    /** {@code .hidden} aka {@code display:none} */
    HIDDEN("hidden"),

    /** "input-group" */
    INPUT_GROUP("input-group"),

    /** "input-group-addon" */
    INPUT_GROUP_ADDON("input-group-addon"),

    /** "input-group-btn" */
    INPUT_GROUP_BUTTON("input-group-btn"),

    /** "divider" */
    LIST_DIVIDER("divider"),

    /** "dropdown-menu" */
    LIST_DROP_DOWN_MENU("dropdown-menu"),

    /** "dropdown-submenu" indicating a non-top-level container menu item */
    LIST_DROP_DOWN_SUBMENU("dropdown-submenu"),

    /** "dropdown-toggle" */
    LIST_DROP_DOWN_TOGGLE("dropdown-toggle"),

    /** "dropdown" */
    LIST_DROPDOWN("dropdown"),

    /** "list-group" */
    LIST_GROUP("list-group"),

    /** "list-group-item" */
    LIST_GROUP_ITEM("list-group-item"),

    /** "list-group-item-add" */
    LIST_GROUP_ITEM_ADD("list-group-item-add"),

    /** "list-group-item-addon" */
    LIST_GROUP_ITEM_ADDON("list-group-item-addon"),

    /** "list-group-item-edit" */
    LIST_GROUP_ITEM_EDIT("list-group-item-edit"),

    /** "list-inline". */
    LIST_INLINE("list-inline"),

    // Bootstrap Modal Dialog
    /** "modal" */
    MODAL("modal"),

    /** "modal-content" */
    MODAL_CONTENT("modal-content"),

    /** "modal-dialog" */
    MODAL_DIALOG("modal-dialog"),

    /** "modal-body" */
    MODAL_DIALOG_BODY("modal-body"),

    /** "modal-footer" */
    MODAL_DIALOG_FOOTER("modal-footer"),

    /** "modal-footer-text" */
    MODAL_DIALOG_FOOTER_TEXT("modal-footer-text"),

    /** "modal-header" */
    MODAL_DIALOG_HEADER("modal-header"),

    /** "modal-title" */
    MODAL_DIALOG_TITLE("modal-title"),

    /** "panel" */
    PANEL("panel"),

    /** "panel-body" */
    PANEL_BODY("panel-body"),

    /** "panel-collapse" */
    PANEL_COLLAPSE("panel-collapse"),

    /** "panel-default" */
    PANEL_DEFAULT("panel-default"),

    /** "panel-footer" */
    PANEL_FOOTER("panel-footer"),

    /** "panel-group" */
    PANEL_GROUP("panel-group"),

    /** "panel-heading" */
    PANEL_HEADING("panel-heading"),

    /** "panel-title" */
    PANEL_TITLE("panel-title"),

    /** "quick-control-group-left" */
    QUICK_CONTROL_GROUP_LEFT("quick-control-group-left"),

    /** "quick-control-group-right" */
    QUICK_CONTROL_GROUP_RIGHT("quick-control-group-right"),

    /** "row" */
    ROW("row"),

    /** "sr-only" */
    SR_ONLY("sr-only"),

    /** "toolbar" */
    TOOLBAR("toolbar"),

    /** "pinned" */
    TOOLBAR_PINNED("pinned");

    @Getter
    private final String styleClass;

}
