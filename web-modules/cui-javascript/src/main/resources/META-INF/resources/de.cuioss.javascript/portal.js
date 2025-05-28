import { escapeClientId, invertBoolean, parseBoolean } from './cui_utilities.js';

// Assuming jQuery ($) and faces are globally available from the test environment / browser

/**
 * Maps key codes to names.
 * @param {number} key - The key code.
 * @returns {string} The name of the key or "undefined".
 */
export function getKeyCodeByName(key) {
    switch (key) {
        case 27:
            return "esc";
        case 9:
            return "tab";
        case 46:
            return "delete";
        case 13:
            return "enter";
        default:
            return "undefined";
    }
}

/**
 * Attaches key event to an element with data-cui-click-binding key code.
 */
export function attachKeyEvent() {
    // Assuming jQuery ($) is global
    $(window).on("keyup", (e) => {
        const keyName = getKeyCodeByName(e.which); // Uses the local getKeyCodeByName
        if (keyName !== "undefined") {
            const keyBound = $("[data-cui-click-binding='" + keyName + "']");
            if (keyBound.length > 1)
                throw new Error("Element with the same key binding already exists.");
            if (keyBound.length > 0) {
                const elementForEvents = keyBound.get(0);
                let jQueryEvents;
                // jQuery._data is a private API, but the original code used it.
                // It's often mocked in tests.
                if (elementForEvents && typeof $._data === 'function') {
                    jQueryEvents = $._data(elementForEvents, 'events');
                }

                const hasJQClickEvents = jQueryEvents && jQueryEvents.click && jQueryEvents.click.length > 0;
                const hasJSClickEvents = typeof keyBound.is === 'function' && keyBound.is("[onclick]");

                if (hasJQClickEvents || hasJSClickEvents) {
                    if (typeof keyBound.trigger === 'function') {
                        keyBound.trigger("click");
                    }
                } else {
                    if (window.location && typeof keyBound.attr === 'function') {
                        window.location.href = keyBound.attr("href");
                    }
                }
            }
        }
    });
}

/**
 * Used in conjunction with cui:tag.
 * @param {string} target - The ID of the target tag.
 */
export function cuiTagDispose(target) {
    // escapeClientId is imported. $ is assumed global.
    const tag = $(escapeClientId(target));
    const disposeInfo = $(escapeClientId(target) + "_disposed-info");

    if (typeof disposeInfo.val === 'function') {
        disposeInfo.val("true");
    }
    if (typeof tag.hide === 'function') {
        tag.hide("slow");
    }
}

/**
 * Handles panel collapse logic.
 * @param {string} target - The ID of the target panel.
 * @param {string|boolean} asyncUpdate - Whether to perform an async update.
 * @param {function} [callback] - Optional callback function.
 */
export function cuicollapsePanel(target, asyncUpdate, callback) {
    // escapeClientId, invertBoolean, parseBoolean are imported.
    // $ and faces are assumed global.

    const collapseInfo = $(escapeClientId(target) + "_collapse-info");
    if (typeof collapseInfo.val === 'function') {
        const currentValue = collapseInfo.val();
        collapseInfo.val(invertBoolean(currentValue));
    }

    const targetObject = $(escapeClientId(target) + "_body-collapse");
    if (typeof targetObject.collapse === 'function') {
        targetObject.collapse('toggle');
    }

    const bootstrapTransitionLength = 350; // Standard Bootstrap transition time
    setTimeout(() => {
        if (typeof targetObject.parent === 'function') {
            const parent = targetObject.parent();
            let icon, panelCollapse, collapsed;

            if (typeof parent.find === 'function') {
                icon = parent.find("> div.panel-heading span.cui_panel_toggle");
                panelCollapse = parent.find(".panel-collapse");
                if (panelCollapse && typeof panelCollapse.hasClass === 'function') {
                    collapsed = panelCollapse.hasClass("in");
                }
            }

            if (icon && typeof icon.toggleClass === 'function') {
                icon.toggleClass("cui-icon-triangle_e", !collapsed);
                icon.toggleClass("cui-icon-triangle_s", collapsed);
            }

            if (parseBoolean(String(asyncUpdate))) { // Ensure asyncUpdate is treated as boolean string
                if (typeof faces !== "undefined" && faces.ajax && typeof faces.ajax.request === 'function' &&
                    parent && typeof parent.attr === 'function') {
                    faces.ajax.request(parent.attr('id'), null, { execute: '@this' });
                }
            }
            if (callback && typeof callback === 'function') {
                callback();
            }
        }
    }, bootstrapTransitionLength);
}
