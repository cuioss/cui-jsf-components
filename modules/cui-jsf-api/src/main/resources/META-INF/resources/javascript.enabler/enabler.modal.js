/**
 * Used for initializing modal-control components. The implementations assumes
 * JQuery being present, If present the initial handler, e.g. onclick will be removed.
 */
var intitializeModalControl = function () {
    jQuery('[data-modal-for]').each(function (i, filtered) {
        var element = jQuery(filtered);
        var forid = element.data("modal-for");
        var action = element.data("modal-action");
        var event = element.data("modal-event");
        // Remove existing handler
        element.prop("on" + event, null);
        element.on(event, function (e) {
            var modal = jQuery('[data-modal-dialog-id=' + forid + ']');
            if (modal.length === 0) {
                console.error("Invalid forId: " + forid + ", not existing element");
            } else {
                modal.modal(action);
                e.stopPropagation();
                e.preventDefault();
            }
            return false;
        });
    })
};

// Should be loaded at document-ready
jQuery(document).ready(function () {
    icw.cui.registerComponentEnabler(intitializeModalControl);
});