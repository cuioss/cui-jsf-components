/**
 * Used for initializing all panel components with asynchronous update support.
 * The implementations assumes JQuery and jsf.js being present.
 */

/**
 * Send AJAX request to update components server side state.
 * @param {string} panelId - panel ID
 * @param {boolean} isExpanded - state after transition
 */
let cuiUpdateCollapsiblePanelServerState = function (panelId, isExpanded, event) {
    //build submit params
    let options = {};
    options["execute"] = panelId;
    options["target"] = panelId;
    options["render"] = panelId; //re-render component
    options[panelId + "_isexpanded"] = isExpanded;

    jsf.ajax.request(panelId, null, options);
    if (event) {
        event.stopPropagation();
    }
};

/**
 * Update component state
 * @param {string} panelId - panel ID
 * @param {boolean} isExpanded - state after transition
 * @param {boolean} isAsyncUpdate - update server side state
 */
let cuiUpdateCollapsiblePanelState = function (panelId, isExpanded, event) {
    let panelStateHolder = jQuery(PrimeFaces.escapeClientId(panelId) + "_isexpanded");
    if (panelStateHolder) {
        panelStateHolder.val(isExpanded);
        let parent = panelStateHolder.parent();
        if (parent) {
            parent.attr("data-not-collapsed", isExpanded);
        }
    }
    event.stopPropagation();
};

/**
 * This is called for each AJAX request/response and after page load.
 * Adding bootstrap collapse/expand listeners to all panel components.
 * After a click the collapse state is updated immediately.
 * If the component has asynUpdate=true the server state is updated after full expand/collapse.
 */
let intitializeCuiPanelUpdate = function () {
    jQuery(".cui-panel .collapse").each(function () { //select panel body
        let panel = jQuery(this).parent();
        let panelId = panel.attr("id");

        jQuery(this).off('hide.bs.collapse').on('hide.bs.collapse', function (event) {
            cuiUpdateCollapsiblePanelState(panelId, false, event);
        });

        jQuery(this).off('show.bs.collapse').on('show.bs.collapse', function (event) {
            cuiUpdateCollapsiblePanelState(panelId, true, event);

        });

        if (panel.attr("data-asyncupdate") === "true" || panel.attr("data-deferred") === "true") {
            jQuery(this).off('hidden.bs.collapse').on('hidden.bs.collapse', function (event) {
                cuiUpdateCollapsiblePanelServerState(panelId, false, event);
            });

            jQuery(this).off('shown.bs.collapse').on('shown.bs.collapse', function (event) {
                cuiUpdateCollapsiblePanelServerState(panelId, true, event);
            });
        }
        if (panel.attr("data-deferred") === "true") {
            if (panel.data("not-collapsed") && !panel.data("content-loaded")) {
                panel.data("content-loaded", "true");
                cuiUpdateCollapsiblePanelServerState(panelId, true, null);
            }
        }
    });
};

/**
 * Register the 'intitializeCuiPanelUpdate' script to all AJAX requests/responses.
 */
jQuery(document).ready(function () {
    icw.cui.registerComponentEnabler(intitializeCuiPanelUpdate);
});
