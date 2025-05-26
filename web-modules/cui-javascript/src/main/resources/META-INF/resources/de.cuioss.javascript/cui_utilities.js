// Initialize Cui and sub-objects if they don't exist
window.Cui = window.Cui || {};
window.Cui.Utilities = window.Cui.Utilities || {};

window.Cui.Utilities = class {
    /**
     * Taken from https://github.com/primefaces/primefaces/blob/master/primefaces/src/main/resources/META-INF/resources/primefaces/core/core.js
     * @param id to be escaped
     * @returns the escaped client id
     */
    static escapeClientId(id) {
        return "#" + id.replace(/:/g, "\\:");
    }

    static parseBoolean(value) {
        if (typeof value !== 'string') return false; // Robustness
        return value.toLowerCase() === "true";
    }

    static invertBoolean(value) {
        // Ensure 'this' refers to the class 'window.Cui.Utilities' for static context
        return (!this.parseBoolean(value)).toString();
    }

    /**
    * JavaScript based method for copying the content of an element to the clipboard.
    * It only works for browsers at least: 
    * <ul>
    * <li>IE10+ (although this document indicates some support was there from IE5.5+).</li>
    * <li>Google Chrome 43+ (~April 2015)</li>
    * <li>Mozilla Firefox 41+ (shipping ~September 2015)</li>
    * <li>Opera 29+ (based on Chromium 42, ~April 2015)</li>
    * </ul>
    * see http://stackoverflow.com/a/30810322
     * @param textAreaIdentifier clientID for accessing the source to be pasted from
     * @param buttonIdentifier clientID for accessing the the element that was pressed
    */
    static copyToClipboard(textAreaIdentifier, buttonIdentifier) {
        let errorMsg = 'Copying text command failed. You need at least IE10+, Chrome 43+, Firefox 41+ or Opera 29+';
        let success = "Copied Successfully";

        // Ensure 'this' refers to the class 'window.Cui.Utilities' for static context
        let textarea = jQuery(this.escapeClientId(textAreaIdentifier));
        let button = jQuery(this.escapeClientId(buttonIdentifier));
        let initialVisible = textarea.is(':visible');
        if (!initialVisible) {
            textarea.show();
        }
        textarea.select();
        let tooltipMessage = success;
        try {
            let successful = document.execCommand('copy');
            if (!successful) {
                tooltipMessage = errorMsg;
                console.log(errorMsg);
            }
        } catch (err) {
            tooltipMessage = errorMsg;
            console.log(errorMsg);
        }

        if (!initialVisible) {
            textarea.hide();
        }
        if (button && typeof button.prop === 'function' && typeof button.tooltip === 'function') { 
            button.prop('title', tooltipMessage);
            button.tooltip('show');
        }
    }
};
