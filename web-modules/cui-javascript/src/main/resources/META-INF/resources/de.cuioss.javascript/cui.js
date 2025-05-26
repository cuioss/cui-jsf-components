// Initialize Cui and sub-objects if they don't exist on the window object
window.Cui = window.Cui || {};
window.Cui.Core = window.Cui.Core || {};
window.Cui.Utilities = window.Cui.Utilities || {}; // Should be loaded already
window.Cui.Session = window.Cui.Session || {};     // Should be loaded already
window.Cui.Portal = window.Cui.Portal || {};       // Should be loaded already
window.Cui.TabControl = window.Cui.TabControl || {}; // Should be loaded already

// Pass window.Cui.Core and window.Cui.Utilities to the IIFE
(function(Core, Utilities) {

    // Private helper function for getData, similar to the original decode
    function _decodeText(text) {
        if (typeof text !== 'string') return '';
        return decodeURIComponent(text.replace(/\+/g, " "));
    }

    /**
     * Extracts the get (or search) data from a given URL
     **/
    Core.getData = function(url) {
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
    };

    Core.trim = function(str) {
        if (typeof str !== 'string') return '';
        return str.replace(/^\s+|\s+$/g, "");
    };

    Core.checkLength = function(input, maxLength, moreLengthAction, lessLengthAction) {
        let currentValue = '';
        if (input && typeof jQuery === 'function') {
            const jQueryInput = jQuery(input);
            if (typeof jQueryInput.val === 'function') {
                 currentValue = Core.trim(jQueryInput.val()); // Core here is window.Cui.Core
            }
        }

        if (currentValue && currentValue.length >= maxLength) {
            if (typeof moreLengthAction === 'function') moreLengthAction();
        } else {
            if (typeof lessLengthAction === 'function') lessLengthAction();
        }
    };

    function _handleAjaxError() {
        if (typeof jQuery === 'function') {
            const errorMessageElement = jQuery('.cui-ajax-error-message');
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

    Core.addErrorMessage = function() {
        if (typeof faces !== "undefined" && faces.ajax && typeof faces.ajax.addOnError === 'function') {
            faces.ajax.addOnError((data) => { 
                _handleAjaxError();
            });
        }
        if (typeof jQuery === 'function' && typeof jQuery(document).on === 'function') {
            jQuery(document).on("pfAjaxError", function (event, xhr ) { 
                const statusText = 'statusText';
                if (xhr && xhr[statusText] !== 'abort') { 
                    _handleAjaxError();
                }
            });
        }
    };

    Core.addScrollToTop = function() {
        if (typeof jQuery === 'function' && typeof jQuery(window).scroll === 'function') {
            jQuery(window).scroll(() => {
                if (typeof jQuery(window).scrollTop === 'function' && typeof jQuery('#scroll-to-top').attr === 'function' && typeof jQuery('#scroll-to-top').removeAttr === 'function') {
                    let position = jQuery(window).scrollTop();
                    const scrollElement = jQuery('#scroll-to-top');
                    if (position >= 200) {
                        scrollElement.attr('style', 'bottom:8px;');
                    } else {
                        scrollElement.removeAttr('style');
                    }
                }
            });
        }
    };
    
    Core._onIdleArray = []; 

    Core.registerComponentEnabler = function(callback) {
        if (typeof callback === 'function') {
            callback();
        }
        if (typeof faces !== "undefined" && faces.ajax && typeof faces.ajax.addOnEvent === 'function') {
            faces.ajax.addOnEvent((data) => { 
                if (data && data.status && data.status === 'success') { 
                    if (typeof callback === 'function') callback();
                }
            });
        }
        if (typeof jQuery === 'function' && typeof jQuery(document).on === 'function') {
            jQuery(document).on("pfAjaxComplete", function() { 
                 if (typeof callback === 'function') callback();
            });
        }
    };

    Core.registerOnIdle = function(callback) {
        if (typeof callback === 'function') {
            Core._onIdleArray.push(callback); // Core here is window.Cui.Core
        }
    };

    Core.executeOnIdle = function() {
        if (typeof jQuery === 'function') {
            const modalEl = jQuery('.modal');
            if (modalEl && typeof modalEl.modal === 'function') modalEl.modal('hide');
            
            const confirmDialog = jQuery('[data-modal-dialog-id=confirmDialogTimeout]');
            if (confirmDialog && typeof confirmDialog.modal === 'function') confirmDialog.modal('show');

            const bodyEl = jQuery(document.body);
            if (bodyEl && typeof bodyEl.addClass === 'function') bodyEl.addClass('modal-timeout');
        }
        
        Core._onIdleArray.forEach((callback) => { // Core here is window.Cui.Core
            if (typeof callback === 'function') callback(); 
        });
    };

    Core.openExternalApplicationInNewWindow = function(applicationUrl) {
        if (typeof window !== 'undefined' && typeof window.open === 'function') {
            try {
                let openedViewer = window.open(applicationUrl);
                if (openedViewer && typeof openedViewer.focus === 'function') { 
                    openedViewer.focus();
                }
            } catch (e) {
                /* Focus might fail */
            }
        }
    };

})(window.Cui.Core, window.Cui.Utilities);
