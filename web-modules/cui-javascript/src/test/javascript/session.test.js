import { escapeClientId } from '../../main/resources/META-INF/resources/de.cuioss.javascript/cui_utilities.js';
import { startLogoutTimeout, resetLogoutTimeout, stopLogoutTimeout, _getSessionState } from '../../main/resources/META-INF/resources/de.cuioss.javascript/session.js';

// _resetSessionState() is called in global beforeEach from jest.setup.js

describe("session.js", () => {
    const intervalSec = 5;
    const linkId = "logoutLink";
    let mockCallback;
    let mockLinkElement;

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

    describe("startLogoutTimeout", () => {
        it("should set a timeout with the given interval and callback", () => {
            startLogoutTimeout(intervalSec, linkId, mockCallback);
            const state = _getSessionState();
            expect(state.interval).toBe(intervalSec * 1000);
            expect(state.storedCallback).toBe(mockCallback);
            expect(state.timeout).not.toBeNull();
            expect(jest.getTimerCount()).toBe(1);

            jest.advanceTimersByTime(intervalSec * 1000);
            expect(mockCallback).toHaveBeenCalledTimes(1);
            expect(jest.getTimerCount()).toBe(0);
        });

        it("should set a timeout to click the linkId if no callback is provided", () => {
            startLogoutTimeout(intervalSec, linkId);
            const state = _getSessionState();
            expect(state.timeout).not.toBeNull();
            expect(jest.getTimerCount()).toBe(1);

            jest.advanceTimersByTime(intervalSec * 1000);
            const jqClicked = mockLinkElement.click.mock.calls.length > 0;
            const domClicked = mockLinkElement[0].click.mock.calls.length > 0;
            expect(jqClicked || domClicked).toBe(true);
            expect(jest.getTimerCount()).toBe(0);
        });

        it("should use interval of 1 second if intervalSec is undefined", () => {
            startLogoutTimeout(undefined, linkId, mockCallback);
            const state = _getSessionState();
            expect(state.interval).toBe(1000);
            expect(state.timeout).not.toBeNull();
            expect(jest.getTimerCount()).toBe(1);
        });

        it("should not set timeout if interval is 0 or less", () => {
            startLogoutTimeout(0, linkId, mockCallback);
            const state = _getSessionState();
            expect(state.timeout).toBeNull();
            expect(jest.getTimerCount()).toBe(0);
        });
    });

    describe("resetLogoutTimeout", () => {
        it("should stop the current timeout and start a new one with previously stored settings", () => {
            startLogoutTimeout(intervalSec, linkId, mockCallback);
            expect(jest.getTimerCount()).toBe(1);

            resetLogoutTimeout();
            const secondState = _getSessionState();
            expect(jest.getTimerCount()).toBe(1);
            expect(secondState.timeout).not.toBeNull();

            jest.advanceTimersByTime(intervalSec * 1000);
            expect(mockCallback).toHaveBeenCalledTimes(1);
            expect(jest.getTimerCount()).toBe(0);
        });

         it("should correctly reset and trigger link click if original had no callback", () => {
            startLogoutTimeout(intervalSec, linkId);
            expect(jest.getTimerCount()).toBe(1);

            resetLogoutTimeout();
            expect(jest.getTimerCount()).toBe(1);
            expect(_getSessionState().timeout).not.toBeNull();

            jest.advanceTimersByTime(intervalSec * 1000);
            const jqClicked = mockLinkElement.click.mock.calls.length > 0;
            const domClicked = mockLinkElement[0].click.mock.calls.length > 0;
            expect(jqClicked || domClicked).toBe(true);
            expect(jest.getTimerCount()).toBe(0);
        });
    });

    describe("stopLogoutTimeout", () => {
        it("should clear the currently active timeout", () => {
            startLogoutTimeout(intervalSec, linkId, mockCallback);
            expect(jest.getTimerCount()).toBe(1);

            stopLogoutTimeout();
            const state = _getSessionState();
            expect(state.timeout).toBeNull();
            expect(jest.getTimerCount()).toBe(0);
        });
    });
});
