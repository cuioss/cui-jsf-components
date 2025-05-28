// Assuming global window object is available

export class TabControl {
    constructor(defaultPopup = null) {
        this.defaultPopup = defaultPopup;
    }

    /**
     * Checks if a window is closed.
     * Internal utility method.
     * @param {object} win - The window object to check.
     * @returns {boolean} True if the window is closed or does not exist.
     */
    _isWindowClosed(win) {
        if (!win) return true;
        return win.closed;
    }

    /**
     * Closes the open popups by name.
     * @param {string} windowNames - Comma-separated string of window names.
     */
    closePopupsByNames(windowNames) {
        if (windowNames) {
            const names = windowNames.split(',');
            const arrayLength = names.length;
            for (let i = 0; i < arrayLength; i++) {
                const name = names[i];
                // Ensure window object and window.name are accessible
                if (typeof window !== 'undefined' && window.name !== name) {
                    if (typeof window.open === 'function') {
                        const popup = window.open('', name); // Attempt to get a reference to the existing window
                        if (popup && !this._isWindowClosed(popup)) {
                            if (popup.location && typeof popup.location.reload === 'function') {
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
    closeLocalPopup() {
        if (this.defaultPopup) {
            if (!this._isWindowClosed(this.defaultPopup)) {
                if (typeof this.defaultPopup.close === 'function') {
                    this.defaultPopup.close();
                }
            }
            // Optionally, clear this.defaultPopup after attempting to close
            // this.defaultPopup = null; // Depending on desired re-usability
        }
    }

    /**
     * Sets the internal default popup.
     * @param {object} popup - The mock popup object.
     */
    setDefaultPopup(popup) {
        this.defaultPopup = popup;
    }
}
