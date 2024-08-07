///<reference path='typings/jquery.d.ts'/>
///<reference path='typings/interfaces.d.ts'/>
///<reference path='cui_utilities.ts'/>

namespace Cui {
    export class Portal {

        /**
         * Attaches key event to an element with data-cui-click-binding key code.
         */
        public static attachKeyEvent(): void {
            jQuery(window).on("keyup", (e: { which: number; }) => {
                const keyName: string = this.getKeyCodeByName(e.which);
                if (keyName !== undefined) {
                    const keyBound: any = jQuery("[data-cui-click-binding='" + keyName + "']");
                    if (keyBound.length > 1)
                        throw new Error("Element with the same key binding already exists.");
                    if (keyBound.length > 0) {
                        const jQueryEvents: any = jQuery._data(jQuery(keyBound).get(0), 'events');
                        const hasJQClickEvents: boolean = jQueryEvents !== undefined && jQueryEvents.click !== undefined && jQueryEvents.click.length > 0;
                        const hasJSClickEvents: boolean = jQuery(keyBound).is("[onclick]");
                        if (hasJQClickEvents || hasJSClickEvents) {
                            jQuery(keyBound).trigger("click");
                        } else {
                            window.location.href = jQuery(keyBound).attr("href");
                        }
                    }
                }
            });
        }

        private static getKeyCodeByName(key: number): string {
            switch (key) {
                case 27:
                    return "esc";
                case 9:
                    return "tab";
                case 46:
                    return "delete";
                case 13:
                    return "enter";
                default:
                    return "undefined";
            }
        }

        /**
         * Used in conjunction with cui:tag.
         * <ul>
         *      <li>Sets the value of the hidden field "_disposed-info" to true indicating that it was disposed</li>
         *      <li>Collapses the tag element.</li>
         * </ul>
         * @target The JSF client Id of the tag to be collapsed
         *
         * */
        public static cuiTagDispose(target: string): void {
            // Access the tag object
            const tag: JQuery = jQuery(Cui.Utilities.escapeClientId(target));

            // Hidden field for disposed state
            const disposeInfo: JQuery = jQuery(Cui.Utilities.escapeClientId(target) + "_disposed-info");
            disposeInfo.val("true");

            // Hide the tag
            tag.hide("slow");

        }

        public static cuicollapsePanel(target: string, asyncUpdate: string, callback?: () => void): void {

            const collapseInfo: JQuery = jQuery(Cui.Utilities.escapeClientId(target) + "_collapse-info");
            collapseInfo.val(<any>Cui.Utilities.invertBoolean(collapseInfo.val()));
            const targetObject: any = jQuery(Cui.Utilities.escapeClientId(target) + "_body-collapse");
            targetObject.collapse('toggle');
            /*
             ** From bootstrap.min.js:
             ** this.$element.one("bsTransitionEnd", a.proxy(g, this)).emulateTransitionEnd(350)[f](this.$element[0][h])
             */
            const bootstrapTransitionLength: number = 350;
            setTimeout(() => {
                const parent = targetObject.parent();
                const icon = parent.find("> div.panel-heading span.cui_panel_toggle");
                const panelCollapse = parent.find(".panel-collapse");
                const collapsed = jQuery(panelCollapse).hasClass("in");
                icon.toggleClass("cui-icon-triangle_e", !collapsed);
                icon.toggleClass("cui-icon-triangle_s", collapsed);
                if (Cui.Utilities.parseBoolean(asyncUpdate)) {

                    faces.ajax.request(jQuery(parent).attr('id'), null, {execute: '@this'});
                }
                if (callback) {
                    callback();
                }
            }, bootstrapTransitionLength);

        }
    }
}