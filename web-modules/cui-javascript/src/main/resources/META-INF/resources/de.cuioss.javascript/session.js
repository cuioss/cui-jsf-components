import { escapeClientId } from './cui_utilities.js';

// Assuming jQuery ($) is available globally

export class Session {
    constructor(linkId, intervalSec, callback) {
        this.linkId = linkId;
        this.interval = (intervalSec === undefined || intervalSec <= 0 ? 1 : intervalSec) * 1000; // Default to 1 sec if invalid
        this.callback = callback;
        this.timeoutId = null;
    }

    /**
     * Internal method to set the actual timeout.
     * Uses instance properties.
     */
    _setTimer() {
        if (this.timeoutId) {
            clearTimeout(this.timeoutId);
        }

        if (this.interval <= 0) { // Do not set timer if interval is not positive
            this.timeoutId = null;
            return;
        }

        if (this.callback) {
            this.timeoutId = setTimeout(this.callback, this.interval);
        } else if (this.linkId) { // Only set timeout if linkId is provided and no callback
            this.timeoutId = setTimeout(() => {
                const elementToClick = $(escapeClientId(this.linkId));
                if (elementToClick.length > 0) {
                    if (typeof elementToClick[0].click === 'function') {
                        elementToClick[0].click();
                    } else if (typeof elementToClick.click === 'function') {
                        elementToClick.click();
                    }
                }
            }, this.interval);
        } else {
             this.timeoutId = null; // No action possible
        }
    }

    /**
     * Starts the logout timeout.
     */
    start() {
        this._setTimer();
    }

    /**
     * Resets the logout timeout using previously stored settings.
     */
    reset() {
        // Stop any existing timer
        if (this.timeoutId) {
            clearTimeout(this.timeoutId);
            this.timeoutId = null;
        }
        // Re-apply the timer with the current instance settings
        this._setTimer();
    }

    /**
     * Stops the logout timeout.
     */
    stop() {
        if (this.timeoutId) {
            clearTimeout(this.timeoutId);
            this.timeoutId = null;
        }
    }

    /**
     * FOR TESTING PURPOSES ONLY.
     * Gets the current timeout ID.
     * @returns {object} The internal timeout ID.
     */
    _getTimeoutId() {
        return this.timeoutId;
    }
}
