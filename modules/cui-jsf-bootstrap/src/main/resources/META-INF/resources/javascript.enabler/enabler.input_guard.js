/**
 * Used for initializing GuardedInput components.
 * The implementation assumes JQuery, faces.js and cui.jsf being present.
 */
let intitializeInputGuards = function () {
    jQuery('[data-input-guard-button]').each(function () {
        let guardButton = jQuery(this);
        guardButton.prop('onclick', null); // Fix for IE 11
        guardButton.off('click').on('click', function (event) {
            let targetValue = guardButton.data('input-guard-target-value');

            let guardInput = guardButton.closest('.input-group').find('[data-input-guard-value]');

            guardInput.val(targetValue);

            let updateTarget = guardInput.data('cui-ajax-update');
            let processTarget = guardInput.data('cui-ajax-process');
            faces.ajax.request(guardInput.attr('id'), null, {
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
    Cui.Core.registerComponentEnabler(intitializeInputGuards);
});
