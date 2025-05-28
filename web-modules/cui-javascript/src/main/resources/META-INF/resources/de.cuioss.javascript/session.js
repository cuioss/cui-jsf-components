import { escapeClientId } from './cui_utilities.js';

// Module-scoped variables to hold state
let _timeout = null;
let _interval = 0;
let _linkId = '';
let _storedCallback = null;

/**
 * Internal function to set the actual timeout.
 * Uses module-scoped variables.
 */
function _setLogoutTimeout() {
    if (_timeout) {
        clearTimeout(_timeout);
    }

    if (_storedCallback) {
        if (_interval > 0) {
            _timeout = setTimeout(_storedCallback, _interval);
        }
    } else {
        if (_interval > 0) {
            _timeout = setTimeout(() => {
                // Assuming jQuery ($) and escapeClientId are available
                // escapeClientId is imported, $ is assumed global for now
                const elementToClick = $(escapeClientId(_linkId));
                if (elementToClick.length > 0 && typeof elementToClick[0].click === 'function') {
                    elementToClick[0].click();
                } else if (elementToClick.length > 0 && typeof elementToClick.click === 'function') {
                    elementToClick.click();
                }
            }, _interval);
        }
    }
}

/**
* Initialize logout timeout.
* @param {number} intervalSec - Interval in seconds.
* @param {string} linkId - The ID of the link to click if no callback.
* @param {function} [callback] - Function to execute after timeout.
*/
export function startLogoutTimeout(intervalSec, linkId, callback) {
    _interval = (intervalSec === undefined ? 1 : intervalSec) * 1000;
    _linkId = linkId;
    _storedCallback = callback;
    _setLogoutTimeout();
}

/**
 * Reset logout timeout using previously stored settings.
 */
export function resetLogoutTimeout() {
    if (_timeout) {
        clearTimeout(_timeout);
        _timeout = null;
    }
    // Re-apply the timeout with the stored interval, linkId, and callback
    _setLogoutTimeout();
}

/**
 * Stop the logout timeout.
 */
export function stopLogoutTimeout() {
    if (_timeout) {
        clearTimeout(_timeout);
        _timeout = null;
    }
}

/**
 * FOR TESTING PURPOSES ONLY.
 * Resets the internal state of the session module.
 */
export function _resetSessionState() {
    if (_timeout) {
        clearTimeout(_timeout);
    }
    _timeout = null;
    _interval = 0;
    _linkId = '';
    _storedCallback = null;
}

/**
 * FOR TESTING PURPOSES ONLY.
 * Gets the current internal state.
 * @returns {object} The internal state.
 */
export function _getSessionState() {
    return {
        timeout: _timeout,
        interval: _interval,
        linkId: _linkId,
        storedCallback: _storedCallback
    };
}
