/**
 * Used for initializing tag-dispose-elements, see cui:tag.
 */
let cui_tag_dispose = function (source) {

    let disposeInfo = jQuery(CuiUtilities.escapeClientId(source));
    disposeInfo.val("true");

    // Access the tag object
    let tag = disposeInfo.closest(".cui-tag");

    // Hide the tag
    tag.hide("slow");
    return true;
};
