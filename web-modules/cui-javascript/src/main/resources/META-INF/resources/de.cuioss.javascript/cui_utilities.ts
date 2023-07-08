///<reference path='typings/bootstrap.d.ts'/>

namespace Cui {
    export class Utilities {

        /**
         * Taken from https://github.com/primefaces/primefaces/blob/master/primefaces/src/main/resources/META-INF/resources/primefaces/core/core.js
         * @param id to be escaped
         * @returns the escaped client id
         */
        public static escapeClientId(id: string) {
            return "#" + id.replace(/:/g, "\\:");
        }

        public static parseBoolean(value: string): boolean {
            return value.toLowerCase() === "true";
        }

        public static invertBoolean(value: string): string {
            return (!Cui.Utilities.parseBoolean(value)).toString();
        }

        /**
        * JavaScript based method for copying the content of an element to the clipboard.
        * It only works for browsers at least: 
        * <ul>
        * <li>IE10+ (although this document indicates some support was there from IE5.5+).</li>
        * <li>Google Chrome 43+ (~April 2015)</li>
        * <li>Mozilla Firefox 41+ (shipping ~September 2015)</li>
        * <li>Opera 29+ (based on Chromium 42, ~April 2015)</li>
        * </ul>
        * see http://stackoverflow.com/a/30810322
         * @param textAreaIdentifier clientID for accessing the source to be pasted from
         * @param buttonIdentifier clientID for accessing the the element that was pressed
        */
        public static copyToClipboard(textAreaIdentifier: string, buttonIdentifier: string): void {
            let errorMsg: string = 'Copying text command failed. You need at least IE10+, Chrome 43+, Firefox 41+ or Opera 29+';
            let success: string = "Copied Successfully";

            let textarea: JQuery = jQuery(Cui.Utilities.escapeClientId(textAreaIdentifier));
            let button: JQuery = jQuery(Cui.Utilities.escapeClientId(buttonIdentifier));
            let initialVisible: boolean = textarea.is(':visible');
            if (!initialVisible) {
                textarea.show();
            }
            textarea.select();
            let tooltipMessage: string = success;
            try {
                let successful: boolean = document.execCommand('copy');
                if (!successful) {
                    tooltipMessage = errorMsg;
                    console.log(errorMsg);
                }
            } catch (err) {
                tooltipMessage = errorMsg;
                console.log(errorMsg);
            }

            if (!initialVisible) {
                textarea.hide();
            }
            if (button) {
                button.prop('title', tooltipMessage);
                button.tooltip('show');
            }
        }
    }
}
