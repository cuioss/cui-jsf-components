// Module-scoped variable for state
let _defaultPopup = null;

/**
 * Checks if a window is closed.
 * Internal utility function.
 * @param {object} win - The window object to check.
 * @returns {boolean} True if the window is closed or does not exist.
 */
function _isWindowClosed(win) {
    if (!win) return true;
    return win.closed;
}

/**
 * Closes the open popups by name.
 * @param {string} windowNames - Comma-separated string of window names.
 */
export function closePopupsByNames(windowNames) {
    if (windowNames) {
        const names = windowNames.split(',');
        const arrayLength = names.length;
        for (let i = 0; i < arrayLength; i++) {
            const name = names[i];
            // Ensure window object and window.name are accessible
            if (typeof window !== 'undefined' && window.name !== name) {
                if (typeof window.open === 'function') {
                    const popup = window.open('', name); // Attempt to get a reference to the existing window
                    if (popup && !_isWindowClosed(popup)) {
                        if (popup.location && typeof popup.location.reload === 'function') {
                            // It's unusual to reload a popup before closing,
                            // but this matches the original logic.
                            popup.location.reload();
                        }
                        popup.name = ''; // Resetting name of the popup
                        if (typeof popup.close === 'function') {
                            popup.close();
                        }
                    }
                }
            }
        }
    }
}

/**
 * Closes the locally tracked default popup tab.
 */
export function closeLocalPopup() {
    if (_defaultPopup) {
        if (!_isWindowClosed(_defaultPopup)) {
            if (typeof _defaultPopup.close === 'function') {
                _defaultPopup.close();
            }
        }
        // Optionally, clear _defaultPopup after attempting to close
        // _defaultPopup = null; // Depending on desired re-usability or state tracking
    }
}

/**
 * FOR TESTING PURPOSES ONLY.
 * Sets the internal default popup.
 * @param {object} popup - The mock popup object.
 */
export function _setTabControlDefaultPopup(popup) {
    _defaultPopup = popup;
}

/**
 * FOR TESTING PURPOSES ONLY.
 * Resets the internal state of the tabcontrol module.
 */
export function _resetTabControlState() {
    _defaultPopup = null;
}

/**
 * FOR TESTING PURPOSES ONLY.
 * Gets the current internal state.
 * @returns {object} The internal state.
 */
export function _getTabControlState() {
    return {
        defaultPopup: _defaultPopup
    };
}
