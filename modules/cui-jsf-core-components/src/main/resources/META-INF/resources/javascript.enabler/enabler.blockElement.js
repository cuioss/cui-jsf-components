/*
 * Used for <cui:blockElement /> to block an element after clicking.
 */
function initializeBlock() {
    $("[data-cui-block-element]").on("click", function () {
        if (!$(this).prop("disabled")) {
            let span = document.createElement("span");
            span.setAttribute('class', 'cui-spin cui-icon cui-icon-refresh');
            $(this).prepend(span);
            $(this).prop("disabled", "disabled");
        }
    });
}

// Should be loaded at document-ready
jQuery(document).ready(function () {
    de.cuioss.registerComponentEnabler(initializeBlock);
});
