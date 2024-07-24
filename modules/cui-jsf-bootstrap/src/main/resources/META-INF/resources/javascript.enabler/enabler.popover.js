/**
 * Used to initialize bootstrap's popover components.
 */
let initializePopovers = function() {
    jQuery('[data-toggle="popover"]').each(function(i, filtered) {
        let element = jQuery(filtered);
        let isHtmlContent = element.data("html") + "" === "true";
        let content = element.data("content");
        let contentWrapper = element.data("contentwrapper");
        let placement = element.data("placement");
        let selector = element.data("selector");
        let title = element.data("title");
        let trigger = element.data("trigger");
        let viewport = element.data("viewport");

        if (contentWrapper) {
            content = jQuery(Cui.Utilities.escapeClientId(contentWrapper)).html();
        }
        if (selector) {
            selector = Cui.Utilities.escapeClientId(selector);
        }
        if (viewport) {
            viewport = Cui.Utilities.escapeClientId(viewport);
        }

        element.popover({
            html: isHtmlContent,
            placement: placement,
            content: content,
            selector: selector,
            title: title,
            trigger: trigger,
            viewport: viewport
        });
    })
};

// Should be loaded at document-ready
jQuery(document).ready(function() {
    Cui.Core.registerComponentEnabler(initializePopovers);
});
