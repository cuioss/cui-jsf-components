var getTypeWatchOptionsFrom = function (component) {
    var prefix = "typewatch-";
    var allowSubmit = component.data(prefix + "allowsubmit");
    var wait = component.data(prefix + "wait");
    var highlight = component.data(prefix + "highlight");
    var captureLength = component.data(prefix + "capturelength");

    var options = {};
    if (allowSubmit) {
        options["allowSubmit"] = allowSubmit;
    }
    if (wait) {
        options["wait"] = wait;
    }
    if (highlight) {
        options["highlight"] = highlight;
    }
    if (captureLength) {
        options["captureLength"] = captureLength;
    }

    // create a new global scoped function which always acts in non-strict mode only.
    // in other words: you can't have 'use strict' in the function body.
    options["callback"] = function () {
        var cfg = {source: component.attr("id"), event: "valueChange"};
        if (component.data(prefix + "process")) {
            cfg["process"] = component.data(prefix + "process")
        }
        if (component.data(prefix + "update")) {
            cfg["update"] = component.data(prefix + "update")
        }
        PrimeFaces.ab(cfg);
    };

    return options;
};

var initTypeWatch = function () {
    jQuery("[data-typewatch]").each(function () {
        var component = jQuery(this);
        if (component.data("typewatched")) {
            return;
        }
        var options = getTypeWatchOptionsFrom(component);
        component.typeWatch(options);
        component.data("typewatched", "true");
    });
};

/**
 * Registering this script to all AJAX requests/responses and page load.
 */
jQuery(document).ready(function () {
    icw.cui.registerComponentEnabler(initTypeWatch);
});
