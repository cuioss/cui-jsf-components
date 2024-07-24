/**
 * Used for initializing bootstrap alert components. The implementation
 * assumes JQuery and faces.js being present
 */

let initializeNotificationbox = function () {
    jQuery('.alert-dismissible > [data-dismiss="alert"][data-dismiss-listener="true"]').each(function () {
        $(this).parent().one('close.bs.alert', function () {
            faces.ajax.request($(this).attr('id'), null, {execute: '@this'});
        })
    });
};

// Should be loaded at document-ready
jQuery(document).ready(function () {
    Cui.Core.registerComponentEnabler(initializeNotificationbox);
});
