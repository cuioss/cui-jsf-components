describe("Typewatch enabler script", function () {

    afterEach(function () {
        jQuery("#typeWatchInput").remove();
    });
    
    it("should init all options", function () {
        jQuery(document.body).append('<input id="typeWatchInput" name="typeWatchInput" type="text"' +
            ' data-typewatch="true"' +
            ' data-typewatch-wait="666"' +
            ' data-typewatch-highlight="true"' +
            ' data-typewatch-allowsubmit="true"' +
            ' data-typewatch-capturelength="42"' +
            ' data-typewatch-execute="test">');

        var options = getTypeWatchOptionsFrom(jQuery("#typeWatchInput"));
        expect(options).toBeDefined();
        expect(options.wait).toBe(666);
        expect(options.highlight).toBe(true);
        expect(options.allowSubmit).toBe(true);
        expect(options.captureLength).toBe(42);
        expect(jQuery.isFunction(options.callback)).toBe(true);
    });

    it("should init necessary options", function () {
        jQuery(document.body).append('<input id="typeWatchInput" name="typeWatchInput" type="text"' +
            ' data-typewatch="true"' +
            ' data-typewatch-highlight="false"' +
            ' data-typewatch-allowsubmit="false"' +
            ' data-typewatch-execute="">');

        var options = getTypeWatchOptionsFrom(jQuery("#typeWatchInput"));
        expect(options).toBeDefined();
        expect(options.highlight).toBe(undefined);
        expect(options.allowSubmit).toBe(undefined);
        expect(jQuery.isFunction(options.callback)).toBe(true);
    });
});
