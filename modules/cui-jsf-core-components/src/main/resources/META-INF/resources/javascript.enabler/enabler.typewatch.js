let getTypeWatchOptionsFrom = function (component) {
    let prefix = "typewatch-";
    let allowSubmit = component.data(prefix + "allowsubmit");
    let wait = component.data(prefix + "wait");
    let highlight = component.data(prefix + "highlight");
    let captureLength = component.data(prefix + "capturelength");

    let options = {};
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
        let cfg = {source: component.attr("id"), event: "valueChange"};
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

let initTypeWatch = function () {
    jQuery("[data-typewatch]").each(function () {
        let component = jQuery(this);
        if (component.data("typewatched")) {
            return;
        }
        let options = getTypeWatchOptionsFrom(component);
        component.typeWatch(options);
        component.data("typewatched", "true");
    });
};

/**
 * Registering this script to all AJAX requests/responses and page load.
 */
jQuery(document).ready(function () {
    Cui.Core.registerComponentEnabler(initTypeWatch);
});
