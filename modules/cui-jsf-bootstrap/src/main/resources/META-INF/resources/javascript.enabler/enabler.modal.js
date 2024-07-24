/**
 * Used for initializing modal-control components. The implementations assume
 * JQuery being present, If present the initial handler, e.g., onclick will be removed.
 */
let initializeModalControl = function () {
    jQuery('[data-modal-for]').each(function (i, filtered) {
        let element = jQuery(filtered);
        let forId = element.data("modal-for");
        let action = element.data("modal-action");
        let event = element.data("modal-event");
        // Remove existing handler
        element.prop("on" + event, null);
        element.on(event, function (e) {
            let modal = jQuery('[data-modal-dialog-id=' + forId + ']');
            if (modal.length === 0) {
                console.error("Invalid forId: " + forId + ", not existing element");
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
    Cui.Core.registerComponentEnabler(initializeModalControl);
});