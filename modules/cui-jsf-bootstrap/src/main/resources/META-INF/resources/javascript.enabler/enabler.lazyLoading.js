function intitializeLazyLoading() {
    jQuery(".cui-lazy-loading").each(function() {
        let lazyloading = jQuery(this);
        let lazyloadingId = lazyloading.attr("id");
        if (!lazyloading.data("content-loaded")) {
            lazyloading.data("content-loaded", "true");
            PrimeFaces.ab({
                source: lazyloadingId, process: lazyloadingId,
                update: lazyloadingId, ignoreAutoUpdate: lazyloading.data("ignore-auto-update"),
                async: lazyloading.data("async"), params: [{ name: lazyloadingId + "_isloaded", value: true }]
            });
        }
    });
}

jQuery(document).ready(function() {
    Cui.Core.registerComponentEnabler(intitializeLazyLoading);

    $(document).on("pfAjaxSend", function(event, xhr, options) {
        handleEvent(options, "begin");
    });
    $(document).on("pfAjaxComplete", function(event, xhr, options) {
        handleEvent(options, "complete");
    });

    function handleEvent(options, status) {
        if (!options || !options.data) {
            // if no options or no data is present, we can not do anything
            // that should not happen in normal development
            return;
        }
        let dataArray = options.data.split("&");
        let dataMap = {};
        for (const element of dataArray) {
            if (element.indexOf("=") >= 0) {
                let keyValue = element.split("=");
                dataMap[keyValue[0]] = decodeURIComponent(keyValue[1]);
            }
        }
        if (dataMap["javax.faces.partial.render"]) {
            let updateString = dataMap["javax.faces.partial.render"];
            if (updateString == "@none") {
                return;
            }
            // split by "+" (urlencoded space)
            let updateStringSplit = updateString.split("+");
            for (const element of updateStringSplit) {
                handleUpdateIds(Cui.Utilities.escapeClientId(element), status, dataMap, options);
            }
        }
    }

    function isUiTreeTable(id) {
        return jQuery(id).hasClass("ui-treetable") && jQuery(id).hasClass("ui-widget");
    }

    function isUiDataTable(id) {
        return jQuery(id).hasClass("ui-datatable") && jQuery(id).hasClass("ui-widget");
    }

    function handleLazyLoadingContainer(id, status, container, dataMap, options, includedInUpdate) {
        let element = jQuery("[data-lazyloading-content]", container);
        let lazyloadingId = container.attr("id");
        if (includedInUpdate) {
            options.data = options.data + "&" + encodeURIComponent(lazyloadingId + "_isloaded") + "=true";
        }
        if (isUiTreeTable(id) && dataMap.hasOwnProperty(encodeURIComponent(jQuery(id).attr("id") + "_expand"))) {
            return;
        }

        if (container && container.length == 1 && container.data("lazyloading-waiting-indicator-id")) {
            let waitingIndicator = jQuery(Cui.Utilities.escapeClientId(container.data("lazyloading-waiting-indicator-id")));
            if (waitingIndicator) {
                if (status == "begin") {
                    waitingIndicator.show();
                } else {
                    waitingIndicator.hide();
                }
            }
        }
        // special treatment of primefaces datatable: when changing the filter, only the body should be exchanged
        // with the waiting indicator
        if (isUiDataTable(id) && dataMap["javax.faces.behavior.event"] == "filter") {
            element = jQuery(".ui-datatable-data.ui-widget-content", jQuery(id)).add("[data-lazyloading-content] .alert", container);
        } else if (isUiTreeTable(id)) {
            element = jQuery(".ui-treetable-data.ui-widget-content", jQuery(id)).add("[data-lazyloading-content] .alert", container);
        }
        if (status == "begin") {
            element.hide();
        } else {
            element.show();
            PrimeFaces.invokeDeferredRenders(lazyloadingId);
        }
    }

    function handleUpdateIds(id, status, dataMap, options) {
        let element = jQuery(id);
        if (!element || element.length == 0) {
            return;
        }
        let container;
        if (element.hasClass("cui-lazy-loading")) {
            handleLazyLoadingContainer(id, status, element, dataMap, options, true);
        } else {
            if (!element.data("lazyloading-content")) {
                element = element.parents("[data-lazyloading-content]").first();
            }
            if (!element || element.length == 0) {
                element = jQuery(".cui-lazy-loading", jQuery(id));
                if (!element || element.length == 0) {
                    return;
                }
                element.each(function() {
                    handleLazyLoadingContainer(id, status, jQuery(this), dataMap, options, true);
                });
            } else {
                container = element.parent();
                handleLazyLoadingContainer(id, status, container, dataMap, options, false);
            }
        }
    }
});
