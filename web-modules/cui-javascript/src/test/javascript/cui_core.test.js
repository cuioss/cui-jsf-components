import { getData, CuiIdleHandler } from '../../main/resources/META-INF/resources/de.cuioss.javascript/cui.js';

describe("cui_core.js (originally cui.js)", () => {
    describe("getData", () => {
        it("should parse URL parameters correctly", () => {
            const url = "http://example.com?param1=value1&param2=value%202";
            const parsedData = getData(url);
            expect(parsedData.param1).toBe("value1");
            expect(parsedData.param2).toBe("value 2");
        });

        it("should use window.location.search if URL is not provided", () => {
            const originalLocation = global.window.location;
            delete global.window.location; // Or mock more finely if needed
            global.window.location = { ...originalLocation, search: "?mockParam=mockValue" };

            const parsedData = getData("");
            expect(parsedData.mockParam).toBe("mockValue");

            global.window.location = originalLocation; // Restore original location
        });
    });

    describe("CuiIdleHandler", () => {
        let idleHandler;
        let mockModalElement;
        let mockConfirmDialogElement;
        let mockBodyElement;

        beforeEach(() => {
            idleHandler = new CuiIdleHandler();

            // Mock jQuery selections and methods for modal interactions
            mockModalElement = { ...global.mockJQueryInstance, modal: jest.fn() };
            mockConfirmDialogElement = { ...global.mockJQueryInstance, modal: jest.fn() };
            mockBodyElement = { ...global.mockJQueryInstance, addClass: jest.fn() };

            // Reset the global jQuery mock to its default before customizing for these tests
            // The global beforeEach in jest.setup.js already does this, but being explicit can help.
            global.jQuery.mockImplementation(selector => {
                if (selector === '.modal') return mockModalElement;
                if (selector === '[data-modal-dialog-id=confirmDialogTimeout]') return mockConfirmDialogElement;
                if (selector === document.body) return mockBodyElement;
                // Fallback for other selectors using the default mock behavior
                const instance = {...global.mockJQueryInstance, _selector: selector};
                  if (typeof selector === 'string' && selector.startsWith('[data-cui-click-binding')) {
                      instance.length = 1;
                  }
                  return instance;
            });
            global.$ = global.jQuery;
        });

        it("should register and execute a single callback", () => {
            const mockCallback = jest.fn();
            idleHandler.register(mockCallback);
            idleHandler.execute();
            expect(mockCallback).toHaveBeenCalledTimes(1);
        });

        it("should register and execute multiple callbacks", () => {
            const mockCallback1 = jest.fn();
            const mockCallback2 = jest.fn();
            idleHandler.register(mockCallback1);
            idleHandler.register(mockCallback2);
            idleHandler.execute();
            expect(mockCallback1).toHaveBeenCalledTimes(1);
            expect(mockCallback2).toHaveBeenCalledTimes(1);
        });

        it("should not fail if no callbacks are registered", () => {
            expect(() => idleHandler.execute()).not.toThrow();
        });

        it("should only register functions as callbacks", () => {
            idleHandler.register("not a function");
            idleHandler.register(null);
            idleHandler.register({});
            expect(() => idleHandler.execute()).not.toThrow(); // Should not throw, and no actual callbacks called
        });

        it("should perform jQuery modal interactions during execute", () => {
            idleHandler.execute();
            expect(mockModalElement.modal).toHaveBeenCalledWith('hide');
            expect(mockConfirmDialogElement.modal).toHaveBeenCalledWith('show');
            expect(mockBodyElement.addClass).toHaveBeenCalledWith('modal-timeout');
        });

        it("should ensure callbacks are executed after jQuery interactions", () => {
            const mockCallback = jest.fn();
            let modalHidden = false;
            let confirmShown = false;

            mockModalElement.modal.mockImplementation(() => { modalHidden = true; });
            mockConfirmDialogElement.modal.mockImplementation(() => { 
                expect(modalHidden).toBe(true); // Check modal hide was called before confirm show
                confirmShown = true; 
            });
            mockBodyElement.addClass.mockImplementation(() => {
                expect(confirmShown).toBe(true); // Check confirm show was called before addClass
            });
            
            idleHandler.register(() => {
                expect(modalHidden).toBe(true);
                expect(confirmShown).toBe(true);
                expect(mockBodyElement.addClass).toHaveBeenCalled(); // Check addClass was called before callback
                mockCallback();
            });

            idleHandler.execute();
            expect(mockCallback).toHaveBeenCalledTimes(1);
        });
    });
});
