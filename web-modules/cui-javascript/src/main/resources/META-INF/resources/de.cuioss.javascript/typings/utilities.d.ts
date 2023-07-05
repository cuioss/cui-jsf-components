declare module de.cuioss {

    function parseBoolean(value: String): Boolean;

    function invertBoolean(value: String): String;
    
     /**
     * Taken from https://github.com/primefaces/primefaces/blob/master/primefaces/src/main/resources/META-INF/resources/primefaces/core/core.js
     * @param id to be escaped
     * @returns the escaped client id
     */
    function escapeClientId(value: String): String;
	
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
    function copyToClipboard(textAreaIdentifier: string, buttonIdentifier: string): void;

}