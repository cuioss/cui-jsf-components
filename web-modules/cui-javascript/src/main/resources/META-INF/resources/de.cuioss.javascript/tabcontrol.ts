
/**
* Provides methods and states for interacting with opened windows / tabs
*/
namespace Cui {
    export class TabControl {

        private defaultPopup: Window = null;

        private isWindowClosed(window: Window): boolean {

            // Firefox & Others
            return window.closed;
        }

        /**      public openNewWindow(url: string, name = this.defaultPopupName: string): string {
      
              }*/

        /**
        * Closes the open popups by name
        * <ol>
        * <li>Go through all windows names in session registry</li>
        * <li>If name of tab is not where logout was click by user</li>
        * <li>Then open window with name of such tab (it will not opened but
        * activated only since it is already existing)</li>
        * <li>Reload this window (to avoid not calling of js function since browser
        * put inactive tabs in "sleep mode" after long inactivity time).</li>
        * <li>Rename window to empty string to reset browser JS windows names
        * registry</li>
        * <li>Close window</li>
        * </ol>         
        * @param windowNames a comma separated list providing the name of the windows to be closed. may be null or empty 
        */
        public closePopupsByNames(windowNames: string): void {
            if (windowNames) {
                const names: string[] = windowNames.split(',');
                const arrayLength: number = names.length;
                for (let i = 0; i < arrayLength; i++) {
                    const name: string = names[i];
                    if (window.name !== name) {
                        const popup: Window = window.open(names[i]);
                        if (popup && !this.isWindowClosed(popup)) {
                            popup.location.reload();
                            popup.name = '';
                            popup.close();
                        }
                    }
                }
            }
        }

        /**
         * Closes opened popup tab.
         */
        public closeLocalPopup(): void {
            if (this.defaultPopup) {
                if (!this.isWindowClosed(this.defaultPopup)) {
                    this.defaultPopup.close();
                }
            }
        }
    }
}