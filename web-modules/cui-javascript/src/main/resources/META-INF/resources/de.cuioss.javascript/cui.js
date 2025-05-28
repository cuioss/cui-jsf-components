// Assuming jQuery ($) and faces are globally available from the test environment / browser

import { Session } from './session.js';
import { TabControl } from './tabcontrol.js';

// Re-export Session and TabControl to make them part of the public API
export { Session, TabControl };

// --- CuiIdleHandler Class ---
export class CuiIdleHandler {
    constructor() {
        this.onIdleArray = [];
    }

    /**
     * Registers a callback to be executed when an idle event occurs.
     * @param {function} callback - The callback function.
     */
    register(callback) {
        if (typeof callback === 'function') {
            this.onIdleArray.push(callback);
        }
    }

    /**
     * Executes all registered onIdle callbacks and shows timeout modals.
     */
    execute() {
        // $ is assumed global
        if (typeof $ === 'function') {
            const modalEl = $('.modal');
            if (modalEl && typeof modalEl.modal === 'function') modalEl.modal('hide');

            const confirmDialog = $('[data-modal-dialog-id=confirmDialogTimeout]');
            if (confirmDialog && typeof confirmDialog.modal === 'function') confirmDialog.modal('show');

            const bodyEl = $(document.body);
            if (bodyEl && typeof bodyEl.addClass === 'function') bodyEl.addClass('modal-timeout');
        }

        this.onIdleArray.forEach((callback) => {
            if (typeof callback === 'function') callback();
        });
    }
}

// --- Standalone Utility Functions ---

// Private helper function for getData
function _decodeText(text) {
    if (typeof text !== 'string') return '';
    return decodeURIComponent(text.replace(/\+/g, " "));
}

/**
 * Extracts the get (or search) data from a given URL.
 * @param {string} url - The URL to parse. If empty, uses window.location.search.
 * @returns {object} An object containing the query parameters.
 */
export function getData(url) {
    let match;
    const search = /([^&=]+)=?([^&]*)/g;
    let query = url;

    if (!url && typeof window !== 'undefined' && window.location && window.location.search) {
        query = window.location.search.substring(1);
    } else if (url && url.indexOf("?") >= 0) {
        query = url.substring(url.indexOf("?") + 1);
    } else if (!url) {
        query = "";
    }

    const data = {};
    while ((match = search.exec(query)) !== null) {
        data[_decodeText(match[1])] = _decodeText(match[2]);
    }
    return data;
}

/**
 * Trims whitespace from the beginning and end of a string.
 * @param {string} str - The string to trim.
 * @returns {string} The trimmed string.
 */
export function trim(str) {
    if (typeof str !== 'string') return '';
    return str.replace(/^\s+|\s+$/g, "");
}

/**
 * Checks the length of an input field's value and calls actions.
 * @param {object|string} input - jQuery selector or element for the input.
 * @param {number} maxLength - The maximum length to check against.
 * @param {function} moreLengthAction - Action if length is >= maxLength.
 * @param {function} lessLengthAction - Action if length is < maxLength.
 */
export function checkLength(input, maxLength, moreLengthAction, lessLengthAction) {
    let currentValue = '';
    if (input && typeof $ === 'function') { // $ is assumed global
        const jQueryInput = $(input);
        if (typeof jQueryInput.val === 'function') {
            currentValue = trim(jQueryInput.val()); // Use exported trim
        }
    }

    if (currentValue && currentValue.length >= maxLength) {
        if (typeof moreLengthAction === 'function') moreLengthAction();
    } else {
        if (typeof lessLengthAction === 'function') lessLengthAction();
    }
}

function _handleAjaxError() {
    if (typeof $ === 'function') { // $ is assumed global
        const errorMessageElement = $('.cui-ajax-error-message');
        if (typeof errorMessageElement.show === 'function') {
            errorMessageElement.show();
        }
        setTimeout(() => {
            if (typeof errorMessageElement.hide === 'function') {
                errorMessageElement.hide();
            }
        }, 5000);
    }
}

/**
 * Adds error handlers for JSF and PrimeFaces AJAX errors.
 */
export function addErrorMessage() {
    // faces is assumed global
    if (typeof faces !== "undefined" && faces.ajax && typeof faces.ajax.addOnError === 'function') {
        faces.ajax.addOnError((data) => { // data is unused but part of API
            _handleAjaxError();
        });
    }
    // $ is assumed global
    if (typeof $ === 'function' && typeof $(document).on === 'function') {
        $(document).on("pfAjaxError", function (event, xhr ) {
            const statusText = 'statusText';
            if (xhr && xhr[statusText] !== 'abort') {
                _handleAjaxError();
            }
        });
    }
}

/**
 * Adds scroll-to-top button behavior.
 */
export function addScrollToTop() {
    // $ is assumed global
    if (typeof $ === 'function' && typeof $(window).scroll === 'function') {
        $(window).scroll(() => {
            if (typeof $(window).scrollTop === 'function' && typeof $('#scroll-to-top').attr === 'function' && typeof $('#scroll-to-top').removeAttr === 'function') {
                let position = $(window).scrollTop();
                const scrollElement = $('#scroll-to-top');
                if (position >= 200) {
                    scrollElement.attr('style', 'bottom:8px;');
                } else {
                    scrollElement.removeAttr('style');
                }
            }
        });
    }
}

/**
 * Registers a callback to be executed after AJAX updates.
 * @param {function} callback - The callback function.
 */
export function registerComponentEnabler(callback) {
    if (typeof callback === 'function') {
        callback();
    }
    // faces is assumed global
    if (typeof faces !== "undefined" && faces.ajax && typeof faces.ajax.addOnEvent === 'function') {
        faces.ajax.addOnEvent((data) => {
            if (data && data.status && data.status === 'success') {
                if (typeof callback === 'function') callback();
            }
        });
    }
    // $ is assumed global
    if (typeof $ === 'function' && typeof $(document).on === 'function') {
        $(document).on("pfAjaxComplete", function() {
            if (typeof callback === 'function') callback();
        });
    }
}

/**
 * Opens a URL in a new window/tab.
 * @param {string} applicationUrl - The URL to open.
 */
export function openExternalApplicationInNewWindow(applicationUrl) {
    if (typeof window !== 'undefined' && typeof window.open === 'function') {
        try {
            let openedViewer = window.open(applicationUrl);
            if (openedViewer && typeof openedViewer.focus === 'function') {
                openedViewer.focus();
            }
        } catch (e) {
            /* Focus might fail due to browser restrictions */
            console.warn("Could not focus the new window:", e);
        }
    }
}

// Removed _resetCoreState and _getCoreState as per instructions
// Removed _onIdleArray, registerOnIdle, executeOnIdle as they are now part of CuiIdleHandler
