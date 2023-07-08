/** Selectize.js init for the cui:tagInput Component */
let initSelectize = function() {
    jQuery("[data-selectize='true']").each(function() {
        let component = jQuery(this);

        if (component[0].selectize !== undefined) {
            return; // already Selectized
        }

        let options = {};
        options["valueField"] = "value";
        options["labelField"] = "label";
        options["searchField"] = "label";
        options["sortField"] = [{ field: 'label', direction: 'asc' }];

        options["maxItems"] = component.data("maxitems");
        options["wrapperClass"] = component.data("wrapperclass");
        options["delimiter"] = component.data("delimiter");
        options["persist"] = component.data("persist");
        options["options"] = component.data("options");

        if (component.data("cancreate")) {
            options["create"] = function(input) {
                return { value: '_client_created_' + input, label: input }
            };
        }

        let plugins = [];
        if (component.data("removebutton")) {
            plugins.push("remove_button");
            options["mode"] = "multi";
        }
        options["plugins"] = plugins;

        component.selectize(options);
    });
};

/**
 * Registering this script to all AJAX requests/responses and page load.
 */
jQuery(document).ready(function() {
    Cui.Core.registerComponentEnabler(initSelectize);
});
