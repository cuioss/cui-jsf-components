/**
 * Used for initializing bootstrap tooltip components. The implementation
 * assumes JQuery and faces.js being present
 */

let initializeTooltips = function () {
    jQuery('[data-toggle="tooltip"]').tooltip();
};

// Should be loaded at document-ready
jQuery(document).ready(function () {
    Cui.Core.registerComponentEnabler(initializeTooltips);
});
