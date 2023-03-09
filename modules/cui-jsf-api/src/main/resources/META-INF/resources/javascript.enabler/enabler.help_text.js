/**
 * Used for initializing GuardedInput components.
 * The implementations assumes JQuery, jsf.js and cui.jsf being present.
 */
var intitializeHelpTexts = function () {
    jQuery('[data-help-input-button]').each(function () {
        var helpButton = jQuery(this);
        helpButton.prop('onclick', null); // Fix for IE 11
        helpButton.off('click').on('click', function (event) {
            var element = jQuery("[data-help-block]", helpButton.parents("[data-labeled-container]").first());
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
    icw.cui.registerComponentEnabler(intitializeHelpTexts);
});
