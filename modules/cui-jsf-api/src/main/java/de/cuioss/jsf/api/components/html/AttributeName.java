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
package de.cuioss.jsf.api.components.html;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * To be used in conjunction with {@link HtmlTreeBuilder}. Each constant
 * represents an attribute name.
 *
 * @author Oliver Wolff
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum AttributeName {

    /** "aria-controls" */
    ARIA_CONTROLS("aria-controls"),

    /** "aria-expanded" */
    ARIA_EXPANDED("aria-expanded"),

    /** "aria-hidden". */
    ARIA_HIDDEN("aria-hidden"),

    /** "aria-label". */
    ARIA_LABEL("aria-label"),

    /** "aria-labelledby" id of the label element, e.g. checkbox */
    ARIA_LABELLEDBY("aria-labelledby"),

    /** "aria-live". */
    ARIA_LIVE("aria-live"),

    /** "aria-multiselectable" */
    ARIA_MULTISELECTABLE("aria-multiselectable"),

    /** "aria-selected" works in conjunction with aria-multiselectable=true */
    ARIA_SELECTED("aria-selected"),

    /** checked for checkbox */
    CHECKED("checked"),

    /**
     * <b>class</b> attribute.<br>
     *
     * @see <a href="http://www.w3schools.com/html/html_classes.asp">HTML
     *      Classes</a>
     * @see <a href="http://www.w3schools.com/cssref/sel_class.asp">Definition and
     *      Usage</a>
     */
    CLASS("class"),

    /** "data-asyncupdate" */
    DATA_ASYNCUPDATE("data-asyncupdate"),

    /** "data-asyncupdate" */
    DATA_DEFERRED("data-deferred"),

    /** "data-content-loaded" */
    DATA_CONTENT_LOADED("data-content-loaded"),

    /** "data-backdrop" Special attribute for modal dialogs. */
    DATA_BACKDROP("data-backdrop"),

    /** "data-not-collapsed" */
    DATA_NOT_COLLAPSED("data-not-collapsed"),

    /** "data-dismiss" */
    DATA_DISMISS("data-dismiss"),

    /** "data-dismiss-listener" */
    DATA_DISMISS_LISTENER("data-dismiss-listener"),

    /** Data attribute item is active "data-item-active" */
    DATA_ITEM_ACTIVE("data-item-active"),

    /** Data attribute for selecting / accessing a dialog with JavaScript */
    DATA_MODAL_ID("data-modal-dialog-id"),

    /** "data-parent" */
    DATA_PARENT("data-parent"),

    /** "data-target" */
    DATA_TARGET("data-target"),

    /** "data-toggle" */
    DATA_TOGGLE("data-toggle"),

    /** "disabled" */
    DISABLED("disabled"),
    /**
     * <b>for</b> attribute.<br>
     *
     * @see <a href="http://www.w3schools.com/tags/att_label_for.asp">HTML
     *      &lt;for&gt; </a>
     */
    FOR("for"),

    /**
     * <b>href</b> attribute.<br>
     *
     * @see <a href="http://www.w3schools.com/tags/tag_href.asp"> Definition and
     *      Usage</a>
     */
    HREF("href"),

    /**
     * <b>id</b> attribute.<br>
     *
     * @see <a href="http://www.w3schools.com/tags/att_global_id.asp">HTML id
     *      Attribute</a>
     */
    ID("id"),

    /** JavaScript onclick handler: "onclick" */
    JS_ON_CLICK("onclick"),

    /**
     * <b>name</b> attribute.<br>
     *
     * @see <a href="http://www.w3schools.com/tags/att_a_name.asp">HTML name
     *      Attribute</a>
     */
    NAME("name"),

    /**
     * <b>role</b> attribute.<br>
     *
     * @see <a href="http://www.w3schools.com/tags/tag_role.asp">HTML &lt;role&gt;
     *      </a>
     */
    ROLE("role"),

    /**
     * <b>style</b> attribute.<br>
     *
     * @see <a href="//www.w3schools.com/tags/att_global_style.asp">Definition and
     *      Usage</a>
     */
    STYLE("style"),

    /**
     * The attribute for identifying the tab-index
     */
    TABINDEX("tabindex"),

    /**
     * <b>title</b> attribute.<br>
     *
     * @see <a href="http://www.w3schools.com/tags/tag_title.asp">HTML &lt;title&gt;
     *      </a>
     */
    TITLE("title"),

    /**
     * <b>type</b> attribute.<br>
     *
     * @see <a href="http://www.w3schools.com/tags/att_input_type.asp"> Definition
     *      and Usage</a>
     */
    TYPE("type"),

    /**
     * <b>value</b> attribute.<br>
     *
     * @see <a href="http://www.w3schools.com/tags/att_input_value.asp">HTML
     *      &lt;input&gt; value Attribute</a>
     */
    VALUE("value");

    @Getter
    private final String content;
}
