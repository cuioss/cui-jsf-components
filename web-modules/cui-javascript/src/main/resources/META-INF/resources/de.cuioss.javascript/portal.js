// Initialize Cui and sub-objects if they don't exist
window.Cui = window.Cui || {};
window.Cui.Portal = window.Cui.Portal || {};
window.Cui.Utilities = window.Cui.Utilities || {}; // Assuming Cui.Utilities is already defined on window.Cui

window.Cui.Portal = class {

    /**
     * Attaches key event to an element with data-cui-click-binding key code.
     */
    static attachKeyEvent() {
        jQuery(window).on("keyup", (e) => { 
            const keyName = this.getKeyCodeByName(e.which); // Use 'this' for static call within same class
            if (keyName !== "undefined") { 
                const keyBound = jQuery("[data-cui-click-binding='" + keyName + "']");
                if (keyBound.length > 1)
                    throw new Error("Element with the same key binding already exists.");
                if (keyBound.length > 0) {
                    const elementForEvents = keyBound.get(0);
                    let jQueryEvents;
                    if (elementForEvents && typeof jQuery._data === 'function') {
                        jQueryEvents = jQuery._data(elementForEvents, 'events');
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

    static getKeyCodeByName(key) { 
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
     * Used in conjunction with cui:tag.
     */
    static cuiTagDispose(target) { 
        // Assuming Cui.Utilities is on window.Cui
        if (typeof jQuery !== 'undefined' && window.Cui.Utilities && typeof window.Cui.Utilities.escapeClientId === 'function') {
            const tag = jQuery(window.Cui.Utilities.escapeClientId(target));
            const disposeInfo = jQuery(window.Cui.Utilities.escapeClientId(target) + "_disposed-info");
            
            if (typeof disposeInfo.val === 'function') {
                disposeInfo.val("true");
            }
            if (typeof tag.hide === 'function') {
                tag.hide("slow");
            }
        }
    }

    static cuicollapsePanel(target, asyncUpdate, callback) { 
        // Assuming Cui.Utilities is on window.Cui
        if (typeof jQuery !== 'undefined' && window.Cui.Utilities && 
            typeof window.Cui.Utilities.escapeClientId === 'function' &&
            typeof window.Cui.Utilities.invertBoolean === 'function' && 
            typeof window.Cui.Utilities.parseBoolean === 'function') {

            const collapseInfo = jQuery(window.Cui.Utilities.escapeClientId(target) + "_collapse-info");
            if (typeof collapseInfo.val === 'function') {
                 const currentValue = collapseInfo.val();
                 collapseInfo.val(window.Cui.Utilities.invertBoolean(currentValue));
            }

            const targetObject = jQuery(window.Cui.Utilities.escapeClientId(target) + "_body-collapse");
            if (typeof targetObject.collapse === 'function') {
                targetObject.collapse('toggle');
            }
            
            const bootstrapTransitionLength = 350;
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

                    if (window.Cui.Utilities.parseBoolean(asyncUpdate)) {
                        if (typeof faces !== "undefined" && faces.ajax && typeof faces.ajax.request === 'function' && 
                            parent && typeof parent.attr === 'function') { // Check parent exists
                            faces.ajax.request(parent.attr('id'), null, { execute: '@this' });
                        }
                    }
                    if (callback && typeof callback === 'function') {
                        callback();
                    }
                }
            }, bootstrapTransitionLength);
        }
    }
};
