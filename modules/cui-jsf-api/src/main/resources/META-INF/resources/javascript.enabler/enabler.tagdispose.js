/**
 * Used for initializing tag-dispose-elements, see cui:tag.
 */
var cui_tag_dispose = function (source) {

    var disposeInfo = jQuery(PrimeFaces.escapeClientId(source));
    disposeInfo.val("true");

    // Access the tag object
    var tag = disposeInfo.closest(".cui-tag");

    // Hide the tag
    tag.hide("slow");
    return true;
};
