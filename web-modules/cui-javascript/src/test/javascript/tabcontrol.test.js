import { TabControl } from '../../main/resources/META-INF/resources/de.cuioss.javascript/tabcontrol.js';

// Global mocks (like window.open) are in jest.setup.js
// _resetTabControlState() will be removed from jest.setup.js separately.

describe("tabcontrol.js (Class-based)", () => {
    let mockPopup1, mockPopup2;
    let tabControlInstance; // To hold the TabControl instance

    beforeEach(() => {
        // Specific beforeEach for TabControl tests
        mockPopup1 = { name: 'popup1', closed: false, location: { reload: jest.fn() }, close: jest.fn(() => mockPopup1.closed = true), _isMockWindow: true };
        mockPopup2 = { name: 'popup2', closed: false, location: { reload: jest.fn() }, close: jest.fn(() => mockPopup2.closed = true), _isMockWindow: true };

        // Ensure the global.window.open mock in jest.setup.js can access these
        // by putting them into the global.mockOpenedWindows store.
        global.mockOpenedWindows['popup1'] = mockPopup1;
        global.mockOpenedWindows['popup2'] = mockPopup2;
        
        tabControlInstance = new TabControl(); // Create a new instance for each test
    });

    describe("closePopupsByNames", () => {
        it("should close specified popups that are not the current window and are open", () => {
            global.currentWindowName = 'mainAppWindow'; // Set for this test case

            tabControlInstance.closePopupsByNames("popup1,popup2");

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

            tabControlInstance.closePopupsByNames("popup1,popup2");

            expect(window.open).not.toHaveBeenCalledWith('', 'popup1');
            expect(mockPopup1.close).not.toHaveBeenCalled();

            expect(window.open).toHaveBeenCalledWith('', 'popup2');
            expect(mockPopup2.close).toHaveBeenCalled();
        });
    });

    describe("closeLocalPopup", () => {
        it("should close the defaultPopup if it exists and is open", () => {
            const mockDefaultPopup = { name: 'default', closed: false, close: jest.fn(() => mockDefaultPopup.closed = true), _isMockWindow: true };
            tabControlInstance.setDefaultPopup(mockDefaultPopup); // Use the new public method

            tabControlInstance.closeLocalPopup();
            expect(mockDefaultPopup.close).toHaveBeenCalledTimes(1);
        });

        it("should do nothing if defaultPopup is not set", () => {
            tabControlInstance.setDefaultPopup(null); // Ensure no default popup
            tabControlInstance.closeLocalPopup();
            // No specific error, just shouldn't try to call close on null
            expect(mockPopup1.close).not.toHaveBeenCalled(); // Check some other mock to ensure no unexpected calls
            expect(mockPopup2.close).not.toHaveBeenCalled();
        });

        it("should do nothing if defaultPopup is already closed", () => {
            const mockDefaultPopup = { name: 'default', closed: true, close: jest.fn(), _isMockWindow: true };
            tabControlInstance.setDefaultPopup(mockDefaultPopup);
            tabControlInstance.closeLocalPopup();
            expect(mockDefaultPopup.close).not.toHaveBeenCalled();
        });
    });
    
    describe("setDefaultPopup", () => {
        it("should allow setting a new default popup", () => {
            const initialPopup = { name: 'initial', closed: false, close: jest.fn(), _isMockWindow: true };
            const newPopup = { name: 'new', closed: false, close: jest.fn(), _isMockWindow: true };
            
            const tc = new TabControl(initialPopup);
            tc.closeLocalPopup();
            expect(initialPopup.close).toHaveBeenCalledTimes(1);
            
            tc.setDefaultPopup(newPopup);
            tc.closeLocalPopup();
            expect(newPopup.close).toHaveBeenCalledTimes(1);
            // Ensure initialPopup.close was not called again
            expect(initialPopup.close).toHaveBeenCalledTimes(1);
        });
    });
});
