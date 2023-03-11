package de.cuioss.jsf.api.components.html;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * To be used in conjunction with {@link HtmlTreeBuilder}. Each constant
 * represents an attribute value.
 *
 * @author Oliver Wolff
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum AttributeValue {

    /** "Close". */
    ARIA_CLOSE("Close"),

    /** "polite" */
    ARIA_POLITE("polite"),

    /**
     * Action source event binding
     */
    CUI_CLICK_BINDING("data-cui-click-binding"),

    /**
     * Prefix String for data-attribute: "data-"
     */
    DATA_PREFIX("data-"),

    /**
     * "data-toggle"
     */
    DATA_TOGGLE("data-toggle"),

    /** "false" */
    FALSE("false"),

    /**
     * Html tag <b>hidden</b> attribute.<br>
     *
     * @see <a href="http://www.w3schools.com/tags/att_global_hidden.asp">HTML
     *      hidden Attribute</a>
     */
    HIDDEN("hidden"),

    /**
     * Input type="button"
     */
    INPUT_BUTTON("button"),

    /**
     * Input type="checkbox"
     */
    INPUT_CHECKBOX("checkbox"),

    /**
     * Input type="text"
     */
    INPUT_TEXT("text"),

    /**
     * Dialog entry for the role-attribute
     */
    ROLE_DIALOG("dialog"),

    /**
     * "display:none"
     */
    STYLE_DISPLAY_NONE("display:none;"),

    /** "tablist" */
    TABLIST("tablist"),

    /** "tooltip". */
    TOOLTIP("tooltip"),

    /** "true" */
    TRUE("true"),

    /**
     * Input type="submit"
     */
    TYPE_SUBMIT("submit");

    @Getter
    private final String content;

}
