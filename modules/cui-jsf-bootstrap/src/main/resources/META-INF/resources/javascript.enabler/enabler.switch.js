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
let initSwitch = function () {
    let classHidden = "hidden";
    jQuery(".switch-placing").each(function () {
        let input = jQuery(this).find(".switch input");
        let onText = jQuery(this).find(".switch-text[data-item-active='true']");
        let offText = jQuery(this).find(".switch-text[data-item-active='false']");

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
    de.cuioss.registerComponentEnabler(initSwitch);
});
