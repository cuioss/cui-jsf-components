import { escapeClientId } from '../../main/resources/META-INF/resources/de.cuioss.javascript/cui_utilities.js';
import { Session } from '../../main/resources/META-INF/resources/de.cuioss.javascript/session.js';

// _resetSessionState() is no longer needed as class instances manage their own state.
// Global beforeEach from jest.setup.js will still clear timers and mocks.

describe("session.js (Class-based)", () => {
    const intervalSec = 5;
    const linkId = "logoutLink";
    let mockCallback;
    let mockLinkElement;
    let sessionInstance; // To hold the Session instance

    beforeEach(() => {
        // Specific beforeEach for Session tests
        jest.clearAllTimers(); // Clear timers specifically for session tests too
        mockCallback = jest.fn();
        // Use global.mockJQueryInstance as the base for specific mock elements
        mockLinkElement = { ...global.mockJQueryInstance, click: jest.fn(), 0: { click: jest.fn() }, length: 1 };

        const sessionSpecificJQueryMock = jest.fn(selector => {
            if (selector === escapeClientId(linkId)) {
                return mockLinkElement;
            }
            const instance = {...global.mockJQueryInstance, _selector: selector};
            if (typeof selector === 'string' && selector.startsWith('[data-cui-click-binding')) {
                instance.length = 1;
            }
            return instance;
        });
        global.jQuery = sessionSpecificJQueryMock;
        global.$ = global.jQuery;
    });

    describe("Session constructor and start", () => {
        it("should set a timeout with the given interval and callback when start() is called", () => {
            sessionInstance = new Session(linkId, intervalSec, mockCallback);
            sessionInstance.start();

            expect(sessionInstance.interval).toBe(intervalSec * 1000);
            expect(sessionInstance.callback).toBe(mockCallback);
            expect(sessionInstance._getTimeoutId()).not.toBeNull();
            expect(jest.getTimerCount()).toBe(1);

            jest.advanceTimersByTime(intervalSec * 1000);
            expect(mockCallback).toHaveBeenCalledTimes(1);
            expect(jest.getTimerCount()).toBe(0);
        });

        it("should set a timeout to click the linkId if no callback is provided and start() is called", () => {
            sessionInstance = new Session(linkId, intervalSec, null); // No callback
            sessionInstance.start();

            expect(sessionInstance._getTimeoutId()).not.toBeNull();
            expect(jest.getTimerCount()).toBe(1);

            jest.advanceTimersByTime(intervalSec * 1000);
            const jqClicked = mockLinkElement.click.mock.calls.length > 0;
            const domClicked = mockLinkElement[0].click.mock.calls.length > 0;
            expect(jqClicked || domClicked).toBe(true);
            expect(jest.getTimerCount()).toBe(0);
        });

        it("should use interval of 1 second if intervalSec is undefined or invalid, and start() is called", () => {
            sessionInstance = new Session(linkId, undefined, mockCallback);
            sessionInstance.start();
            expect(sessionInstance.interval).toBe(1000);
            expect(sessionInstance._getTimeoutId()).not.toBeNull();
            expect(jest.getTimerCount()).toBe(1);
            sessionInstance.stop(); // clean up

            sessionInstance = new Session(linkId, 0, mockCallback); // Invalid interval
            sessionInstance.start();
            expect(sessionInstance.interval).toBe(1000); // Corrected by constructor logic
            // _setTimer will not set a timer if interval is <=0 as per current class logic,
            // but constructor defaults it to 1s. If constructor did not default, this would be null.
            expect(sessionInstance._getTimeoutId()).not.toBeNull();
            expect(jest.getTimerCount()).toBe(1);
        });

        it("should not set timeout if interval is <= 0 and no callback/linkId (though constructor defaults interval)", () => {
            // Test case where constructor default for interval might be overridden or if logic changes
            sessionInstance = new Session(null, 0, null); // No linkId, no callback, interval 0
            sessionInstance.interval = 0; // Force interval to 0 after constructor
            sessionInstance.start();
            expect(sessionInstance._getTimeoutId()).toBeNull();
            expect(jest.getTimerCount()).toBe(0);
        });
    });

    describe("reset", () => {
        it("should stop the current timeout and start a new one with previously stored settings", () => {
            sessionInstance = new Session(linkId, intervalSec, mockCallback);
            sessionInstance.start();
            // const firstTimeoutId = sessionInstance._getTimeoutId(); // Unused
            expect(jest.getTimerCount()).toBe(1);

            sessionInstance.reset();
            expect(jest.getTimerCount()).toBe(1);
            expect(sessionInstance._getTimeoutId()).not.toBeNull();
            // Check if it's a new timer instance (though ID might be reused by system)
            // This is harder to check reliably without direct access or more sophisticated timer mocking.
            // The main check is that a timer is active and the callback is eventually called.

            jest.advanceTimersByTime(intervalSec * 1000);
            expect(mockCallback).toHaveBeenCalledTimes(1);
            expect(jest.getTimerCount()).toBe(0);
        });

         it("should correctly reset and trigger link click if original had no callback", () => {
            sessionInstance = new Session(linkId, intervalSec, null);
            sessionInstance.start();
            expect(jest.getTimerCount()).toBe(1);

            sessionInstance.reset();
            expect(jest.getTimerCount()).toBe(1);
            expect(sessionInstance._getTimeoutId()).not.toBeNull();

            jest.advanceTimersByTime(intervalSec * 1000);
            const jqClicked = mockLinkElement.click.mock.calls.length > 0;
            const domClicked = mockLinkElement[0].click.mock.calls.length > 0;
            expect(jqClicked || domClicked).toBe(true);
            expect(jest.getTimerCount()).toBe(0);
        });
    });

    describe("stop", () => {
        it("should clear the currently active timeout", () => {
            sessionInstance = new Session(linkId, intervalSec, mockCallback);
            sessionInstance.start();
            expect(jest.getTimerCount()).toBe(1);
            expect(sessionInstance._getTimeoutId()).not.toBeNull();

            sessionInstance.stop();
            expect(sessionInstance._getTimeoutId()).toBeNull();
            expect(jest.getTimerCount()).toBe(0);
        });
    });
});
