/**
 * Bootstrap 3 Triple Nested Sub-Menus
 * This script will activate Triple level multi drop-down menus in Bootstrap 3.
 */

let initializeSubMenus = function () {

    jQuery('ul.dropdown-menu [data-toggle=dropdown]').on('click', function (event) {
        // Avoid following the href location when clicking
        event.preventDefault();
        // Avoid having the menu to close when clicking
        event.stopPropagation();
        // Re-add .open to parent sub-menu item
        jQuery(this).parent().addClass('open');
        jQuery(this).parent().find("ul").parent().find("li.dropdown").addClass('open');
    });
};

// Should be loaded at document-ready
jQuery(document).ready(function () {
    Cui.Core.registerComponentEnabler(initializeSubMenus);
});
