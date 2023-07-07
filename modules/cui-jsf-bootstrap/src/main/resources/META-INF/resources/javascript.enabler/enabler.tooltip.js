/**
 * Used for initializing bootstraps tooltip components. The implementations
 * assumes JQuery and jsf.js being present
 */

let intitializeTooltips = function() {
    jQuery('[data-toggle="tooltip"]').tooltip();
};

// Should be loaded at document-ready
jQuery(document).ready(function() {
    Cui.Core.registerComponentEnabler(intitializeTooltips);
});
