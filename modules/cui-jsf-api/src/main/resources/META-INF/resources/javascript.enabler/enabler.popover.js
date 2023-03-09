/**
 * Used to initialize bootstraps popover components.
 */
var intitializePopovers = function() {
    jQuery('[data-toggle="popover"]').each(function (i, filtered){
        var element = jQuery(filtered);
        var isHtmlContent = element.data("html") + "" === "true";
        var content = element.data("content");
        var contentwrapper = element.data("contentwrapper");
        var placement = element.data("placement");
        var selector = element.data("selector");
        var title = element.data("title");
        var trigger = element.data("trigger");
        var viewport = element.data("viewport");
        
        if(contentwrapper){
            content = jQuery(PrimeFaces.escapeClientId(contentwrapper)).html();
        }

        if(selector){
            selector = PrimeFaces.escapeClientId(selector);
        }
        
        if(viewport){
            viewport = PrimeFaces.escapeClientId(viewport);
        }
        
        element.popover({
            html:isHtmlContent,
            placement:placement,
            content:content,
            selector:selector,
            title:title,
            trigger:trigger,
            viewport:viewport
        });
    })
};

// Should be loaded at document-ready
jQuery(document).ready(function () {
    icw.cui.registerComponentEnabler(intitializePopovers);
});
