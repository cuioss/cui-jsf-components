/**
 * Used for initializing inline-confirm components. The implementations assumes
 * JQuery being present, If present the initial handler, e.g. onclick will be removed.
 */
var intitializeInlineConfirm = function () {
    jQuery('[data-inline-confirm-initial]').each(function (i, filtered) {
        var element = jQuery(filtered);
        var target = element.next('[data-inline-confirm-target]');
        // Remove existing handler
        element.prop("onclick", null);
        element.on("click", function (e) {
            if (target.length === 0) {
                console.error("Invalid inline-confirm-target: " + target + ", not existing element");
            } else {
                element.hide();
                target.show();
                target.toggleClass("fade-in");
                e.stopPropagation();
                e.preventDefault();
            }
            return false;
        });
        jQuery('[data-inline-confirm-cancel]', target).each(function (d, filtered2) {
            var cancel = jQuery(filtered2);
            cancel.prop("onclick", null);
            cancel.on("click", function (e) {
                target.hide();
                element.show();
                e.stopPropagation();
                e.preventDefault();
                return false;
            });
        });
    })
};

// Should be loaded at document-ready
jQuery(document).ready(function () {
    icw.cui.registerComponentEnabler(intitializeInlineConfirm);
});
