/**
 * Used for initializing GuardedInput components.
 * The implementations assumes JQuery, jsf.js and cui.jsf being present.
 */
var intitializeInputGuards = function () {
    jQuery('[data-input-guard-button]').each(function () {
        var guardButton = jQuery(this);
        guardButton.prop('onclick', null); // Fix for IE 11
        guardButton.off('click').on('click', function (event) {
            var targetValue = guardButton.data('input-guard-target-value');

            var guardInput = guardButton.closest('.input-group').find('[data-input-guard-value]');

            guardInput.val(targetValue);
            
            var updateTarget = guardInput.data('cui-ajax-update');
            var processTarget = guardInput.data('cui-ajax-process');
            jsf.ajax.request(guardInput.attr('id'), null, {
                execute: processTarget,
                target: guardInput.attr('id'),
                render: updateTarget,
                resetValues: targetValue
            });
            if (event) {
                event.stopPropagation();
            }

            return false;
        });
    });

};

// Should be loaded at document-ready
jQuery(document).ready(function () {
    icw.cui.registerComponentEnabler(intitializeInputGuards);
});
