import { closePopupsByNames, closeLocalPopup, _setTabControlDefaultPopup } from '../../main/resources/META-INF/resources/de.cuioss.javascript/tabcontrol.js';

// Global mocks (like window.open) are in jest.setup.js
// _resetTabControlState() is called in global beforeEach from jest.setup.js

describe("tabcontrol.js", () => {
    let mockPopup1, mockPopup2;

    beforeEach(() => {
        // Specific beforeEach for TabControl tests
        // These mock popups are specific to the tests in this file
        mockPopup1 = { name: 'popup1', closed: false, location: { reload: jest.fn() }, close: jest.fn(() => mockPopup1.closed = true), _isMockWindow: true };
        mockPopup2 = { name: 'popup2', closed: false, location: { reload: jest.fn() }, close: jest.fn(() => mockPopup2.closed = true), _isMockWindow: true };

        // The global.window.open mock in jest.setup.js needs to be aware of these
        // or be flexible enough. The current setup mock should handle this by using
        // the mockOpenedWindows object, which is cleared in the global beforeEach.
        // We populate mockOpenedWindows here for the tests.
        global.mockOpenedWindows['popup1'] = mockPopup1;
        global.mockOpenedWindows['popup2'] = mockPopup2;
    });

    describe("closePopupsByNames", () => {
        it("should close specified popups that are not the current window and are open", () => {
            global.currentWindowName = 'mainAppWindow'; // Set for this test case

            closePopupsByNames("popup1,popup2");

            expect(window.open).toHaveBeenCalledWith('', 'popup1');
            expect(mockPopup1.location.reload).toHaveBeenCalled();
            expect(mockPopup1.close).toHaveBeenCalled();
            expect(mockPopup1.name).toBe('');

            expect(window.open).toHaveBeenCalledWith('', 'popup2');
            expect(mockPopup2.location.reload).toHaveBeenCalled();
            expect(mockPopup2.close).toHaveBeenCalled();
            expect(mockPopup2.name).toBe('');
        });

        it("should not attempt to close the current window", () => {
            global.currentWindowName = 'popup1'; // Set for this test case
            // mockOpenedWindows already setup in beforeEach

            closePopupsByNames("popup1,popup2");

            expect(window.open).not.toHaveBeenCalledWith('', 'popup1');
            expect(mockPopup1.close).not.toHaveBeenCalled();

            expect(window.open).toHaveBeenCalledWith('', 'popup2');
            expect(mockPopup2.close).toHaveBeenCalled();
        });
    });

    describe("closeLocalPopup", () => {
        it("should close the defaultPopup if it exists and is open", () => {
            const mockDefaultPopup = { name: 'default', closed: false, close: jest.fn(() => mockDefaultPopup.closed = true), _isMockWindow: true };
            _setTabControlDefaultPopup(mockDefaultPopup); // Use imported setter

            closeLocalPopup();
            expect(mockDefaultPopup.close).toHaveBeenCalledTimes(1);
        });
    });
});
