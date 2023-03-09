/**
 * Displaying the on/off text depending on its state.
 * <div class="switch-placing">
 *   <label class="switch">
 *     <input/>
 *     <span class="slider round"/>
 *   </label>
 *   <span class="switch-text" data-item-active="true">onText</span>
 *   <span class="switch-text" data-item-active="false">offText</span>
 * </div>
 */
var initSwitch = function () {
    var classHidden = "hidden";
    jQuery(".switch-placing").each(function () {
        var input = jQuery(this).find(".switch input");
        var onText = jQuery(this).find(".switch-text[data-item-active='true']");
        var offText = jQuery(this).find(".switch-text[data-item-active='false']");

        jQuery(input).change(function () {
            if(jQuery(this)[0].checked){
                onText.removeClass(classHidden);
                offText.addClass(classHidden);
            }
            else {
                onText.addClass(classHidden);
                offText.removeClass(classHidden);
            }
        });
    });
};

/**
 * Registering this script to all AJAX requests/responses and page load.
 */
jQuery(document).ready(function () {
    icw.cui.registerComponentEnabler(initSwitch);
});
