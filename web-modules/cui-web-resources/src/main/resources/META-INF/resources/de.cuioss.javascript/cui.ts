///<reference path='typings/jquery.d.ts'/>
///<reference path='typings/interfaces.d.ts'/>
///<reference path='session.ts'/>
///<reference path='portal.ts'/>
///<reference path='cui_utilities.ts'/>
///<reference path='tabcontrol.ts'/>
///<reference path='typings/bootstrap.d.ts'/>

namespace Cui.Core {
    
	/** 
	 * Extracts the get (or search) data from a given URL
	 * @param {string} url a URL that might contain parameterUrl
	 * @return {Object} get all the get variables as properties
	**/
    export function getData(url: string): any {
        let match: any;
        let search: RegExp = /([^&=]+)=?([^&]*)/g;
        let query: string = url ?? window.location.search.substring(1);

        if (query && query.indexOf("?") >= 0)
            query = query.substring(query.indexOf("?") + 1);

        let data: any = {};
        while (match = (<any>search).exec(query))
            data[decode(match[1])] = decode(match[2]);

        return data;
    }

    function decode(text: string): string {
        return decodeURIComponent(text.replace(/\+/g, " "));
    }

    export function checkLength(input: any, maxLength: number, moreLengthAction: () => void, lessLengthAction: () => void): void {
        let currentValue = this.trim(jQuery(input).val());
        if (currentValue !== undefined && currentValue.length >= maxLength) {
            moreLengthAction();
        }
        else {
            lessLengthAction();
        }
    }

    export function trim(string: string): string {
        return string.replace(/^\s+|\s+$/g, "");
    }

    /**
     * Display an ajax error message by showing the invisible notification box for 5 seconds
    **/
    function handleAjaxError(): void {
        $('.cui-ajax-error-message').show();
        setTimeout(() => {
            $('.cui-ajax-error-message').hide();
        }, 5000);
    }

    /**
     * Register for ajax errors (jsf standard + PrimeFaces)
    **/
    export function addErrorMessage(): void {
        if (typeof jsf !== "undefined") {
            jsf.ajax.addOnError((data: any) => {
                handleAjaxError();
            });
            $(document).on("pfAjaxError", function(event: any, xhr: { [x: string]: string; }, options: any) {
                if (xhr["statusText"] != "abort") {
                    handleAjaxError();
                }
            });
        }
    }

    export function addScrollToTop(): void {
        $(window).scroll(() => {
            let position: number = $(window).scrollTop();
            if (position >= 200) {
                $('#scroll-to-top').attr('style', 'bottom:8px;');
            }
            else {
                $('#scroll-to-top').removeAttr('style');
            }
        });
    }
    
	/**
     * Register a component enabler to be called immediate and after each AJAX update.
     * Should be called at document.ready.
     * Warning: Event listeners in the callback can get attached multiple times therefore.
     *          You can circumvent that by first removing all event listeners you like to register afterwards.
     *          E.g.: jQuery(this).off('my.event').one('my.event')
     *
     * @param callback
     **/
    export function registerComponentEnabler(callback?:() => void):void {
        callback();
        // Ensure jsf ajax will react properly
        if (typeof jsf !== "undefined") {
            jsf.ajax.addOnEvent((data: { status: string; }): void => {
                if (data.status && data.status === 'success') {
                    callback();
                }
            });
        }
        // Catch all PF ajax events
        jQuery(document).on("pfAjaxComplete", callback);
    }
    
    let onIdle: Array<() => void> = [];

    export function registerOnIdle(callback?:() => void):void {
        onIdle.push(callback);
    }
    
    export function executeOnIdle():void {
        jQuery('.modal').modal('hide');
        jQuery('[data-modal-dialog-id=confirmDialogTimeout]').modal('show');
        jQuery(document.body).addClass('modal-timeout');
        onIdle.forEach((callback:() => void) => { callback(); });
    }

    /**
     * Execute open external application in new window (or tab depend on browser and settings) and set focus on this
     * @param applicationUrl
     */
    export function openExternalApplicationInNewWindow(applicationUrl: string): void {
        try {
            let openedViewer = window.open(applicationUrl);
            openedViewer.focus();
        } catch (e) {
            /* Focus might fail if a download popup comes up and is aborted. No action required here. */
        }
    }
}
