///<reference path='typings/jquery.d.ts'/>
///<reference path='typings/interfaces.d.ts'/>
///<reference path='cui_utilities.ts'/>

namespace Cui {

    export class Session {
        private timeout: ReturnType<typeof setTimeout>;
        private interval: number;
        private id: string;

        /**
        * initialize logout timeout
        * @intervalSec : int value in seconds
        * @logoutUrl : logout url to redirect to
        * @callback : function to execute after timout exceeded
        */
        public startLogoutTimeout(intervalSec: number, linkId: string, callback?: () => void): void {
            this.interval = (intervalSec === undefined ? 1 : intervalSec) * 1000;
            this.id = linkId;
            this.setLogoutTimeout(callback);
        }

        /**
         * set logout timeout
         */
        private setLogoutTimeout(callback?: () => void): void {
            if (callback) {
                if (this.interval > 0) {
                    this.timeout = setTimeout(callback, this.interval);
                }
            } else {
                if (this.interval > 0) {
                    this.timeout = setTimeout(() => {
                        jQuery(Cui.Utilities.escapeClientId(this.id))[0].click();
                    }, this.interval);
                }
            }
        }

        /**
         * reset logout timeout
         */
        public resetLogoutTimeout(): void {
            this.stopLogoutTimeout();
            this.setLogoutTimeout();
        }

        /**
         * reset logout timeout
         */
        public stopLogoutTimeout(): void {
            clearTimeout(this.timeout);
        }
    }
}