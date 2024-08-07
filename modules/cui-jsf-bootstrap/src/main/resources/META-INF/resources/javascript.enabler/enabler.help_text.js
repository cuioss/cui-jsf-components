/**
 * Used for initializing GuardedInput components.
 * The implementation assumes JQuery, faces.js and cui.jsf being present.
 */
let initializeHelpTexts = function () {
    jQuery('[data-help-input-button]').each(function () {
        let helpButton = jQuery(this);
        helpButton.prop('onclick', null); // Fix for IE 11
        helpButton.off('click').on('click', function (event) {
            let element = jQuery("[data-help-block]", helpButton.parents("[data-labeled-container]").first());
            element.toggle();

            if (event) {
                event.stopPropagation();
            }

            return false;
        });
    });

};

// Should be loaded at document-ready
jQuery(document).ready(function () {
    Cui.Core.registerComponentEnabler(initializeHelpTexts);
});
