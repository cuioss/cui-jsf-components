// Initialize Cui and sub-objects if they don't exist
window.Cui = window.Cui || {};
window.Cui.Session = window.Cui.Session || {};
window.Cui.Utilities = window.Cui.Utilities || {}; // Assuming Cui.Utilities is already defined on window.Cui

window.Cui.Session = class {
    /**
    * initialize logout timeout
    * @intervalSec : int value in seconds
    * @logoutUrl : logout url to redirect to
    * @callback : function to execute after timout exceeded
    */
    static startLogoutTimeout(intervalSec, linkId, callback) {
        this.interval = (intervalSec === undefined ? 1 : intervalSec) * 1000;
        this.id = linkId;
        this._storedCallback = callback; 
        this.setLogoutTimeout(callback);
    }

    /**
     * set logout timeout
     */
    static setLogoutTimeout(callback) { 
        const effectiveCallback = callback || this._storedCallback;

        if (this.timeout) {
            clearTimeout(this.timeout);
        }

        if (effectiveCallback) {
            if (this.interval > 0) {
                this.timeout = setTimeout(effectiveCallback, this.interval);
            }
        } else { 
            if (this.interval > 0) {
                this.timeout = setTimeout(() => {
                    if (typeof jQuery !== 'undefined' && window.Cui.Utilities && typeof window.Cui.Utilities.escapeClientId === 'function') {
                        const elementToClick = jQuery(window.Cui.Utilities.escapeClientId(this.id));
                        if (elementToClick.length > 0 && typeof elementToClick[0].click === 'function') {
                             elementToClick[0].click();
                        } else if (elementToClick.length > 0 && typeof elementToClick.click === 'function') {
                            elementToClick.click();
                        }
                    }
                }, this.interval);
            }
        }
    }

    /**
     * reset logout timeout
     */
    static resetLogoutTimeout() {
        this.stopLogoutTimeout();
        this.setLogoutTimeout(); 
    }

    /**
     * reset logout timeout
     */
    static stopLogoutTimeout() {
        if (this.timeout) {
            clearTimeout(this.timeout);
            this.timeout = null; 
        }
    }
};

// Initialize static properties on window.Cui.Session
window.Cui.Session.timeout = null; 
window.Cui.Session.interval = 0;
window.Cui.Session.id = '';
window.Cui.Session._storedCallback = null;
