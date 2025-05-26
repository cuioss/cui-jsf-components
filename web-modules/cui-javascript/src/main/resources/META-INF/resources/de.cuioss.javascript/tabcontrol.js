// Initialize Cui and sub-objects if they don't exist
window.Cui = window.Cui || {};
window.Cui.TabControl = window.Cui.TabControl || {};

window.Cui.TabControl = {
    _defaultPopup: null, 

    _isWindowClosed: function(win) { 
        if (!win) return true; 
        return win.closed;
    },

    /**
    * Closes the open popups by name
    */
    closePopupsByNames: function(windowNames) { 
        if (windowNames) {
            const names = windowNames.split(',');
            const arrayLength = names.length;
            for (let i = 0; i < arrayLength; i++) {
                const name = names[i];
                // Ensure window object and window.name are accessible
                if (typeof window !== 'undefined' && window.name !== name) {
                    if (typeof window.open === 'function') {
                        const popup = window.open('', name); 
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
    },

    /**
     * Closes opened popup tab.
     */
    closeLocalPopup: function() { 
        if (this._defaultPopup) {
            if (!this._isWindowClosed(this._defaultPopup)) {
                if (typeof this._defaultPopup.close === 'function') {
                    this._defaultPopup.close();
                }
            }
        }
    }
};
