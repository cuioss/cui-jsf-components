import { escapeClientId } from '../../main/resources/META-INF/resources/de.cuioss.javascript/cui_utilities.js';
import { attachKeyEvent, cuiTagDispose, cuicollapsePanel, getKeyCodeByName } from '../../main/resources/META-INF/resources/de.cuioss.javascript/portal.js';

// Global mocks (like faces, jQuery general mocks) are in jest.setup.js
// _reset...State functions are called in global beforeEach from jest.setup.js

describe("portal.js", () => {
    describe("attachKeyEvent", () => {
        let originalWindowLocation;
        let keyupHandler;
        let activeMockBoundElement = null;
        let originalGlobalJQuery;

        beforeEach(() => {
            // Specific beforeEach for attachKeyEvent tests
            originalWindowLocation = global.window.location;
            delete global.window.location; // Or mock more finely
            global.window.location = { href: '', assign: jest.fn() };
            activeMockBoundElement = null;
            originalGlobalJQuery = global.jQuery; // Store the global jQuery from setup

            global.jQuery = jest.fn(selector => {
                if (selector === window) {
                    const jqWin = { ...global.mockJQueryInstance, _selector: 'window' };
                    jqWin.on = jest.fn((event, handler) => {
                        if (event === 'keyup') keyupHandler = handler;
                        return jqWin;
                    });
                    return jqWin;
                }
                if (selector === activeMockBoundElement) {
                    return activeMockBoundElement;
                }
                if (typeof selector === 'string' && selector.startsWith('[data-cui-click-binding')) {
                    const match = selector.match(/\[data-cui-click-binding='(.*?)'\]/);
                    if (match && activeMockBoundElement && match[1] === activeMockBoundElement._expectedKeyName) {
                        return activeMockBoundElement;
                    }
                    return { ...global.mockJQueryInstance, length: 0, _selector: selector, trigger: jest.fn(), attr: jest.fn(), is: jest.fn().mockReturnValue(false), get: jest.fn().mockReturnValue(undefined) };
                }
                return { ...global.mockJQueryInstance, _selector: selector, trigger: jest.fn(), attr: jest.fn(), is: jest.fn().mockReturnValue(false), get: jest.fn().mockReturnValue(undefined), 0: undefined, length: 0 };
            });
            global.$ = global.jQuery;

            attachKeyEvent(); // Call the function under test
        });

        afterEach(() => {
            global.window.location = originalWindowLocation;
            keyupHandler = null; // Clear captured handler
            global.jQuery = originalGlobalJQuery; // Restore global jQuery
            global.$ = global.jQuery;
        });

        const keyMap = { esc: 27, tab: 9, delete: 46, enter: 13, unknown: 100 };

        function setupMockBoundElement(options = {}) {
            activeMockBoundElement = {
                ...global.mockJQueryInstance, // Base on the global mock structure
                length: options.exists !== false ? 1 : 0,
                attr: jest.fn(attrName => (attrName === 'href' ? options.href : undefined)),
                trigger: jest.fn(),
                is: jest.fn(sel => {
                    if (options.keyName === 'esc' && sel === '[onclick]') return true;
                    return sel === '[onclick]' && !!options.hasJsClick;
                }),
                get: jest.fn(index => (index === 0 ? { _isMockDomElement: true } : undefined)),
                _expectedKeyName: options.keyName,
            };
            if (options.hasJqClick && options.keyName !== 'esc' && options.exists !== false && activeMockBoundElement.length > 0) {
                const mockDomElement = activeMockBoundElement.get(0);
                global.jQuery_data_storage.set(mockDomElement, { events: { click: [{ handler: jest.fn() }] } });
            } else if (options.keyName === 'esc') {
                const mockDomElement = activeMockBoundElement.get(0);
                if (mockDomElement) global.jQuery_data_storage.delete(mockDomElement);
            }
        }

        const simulateKeyup = (keyCode) => {
            if (typeof keyupHandler === 'function') {
                keyupHandler({ which: keyCode, preventDefault: jest.fn() });
            } else {
                throw new Error("Keyup handler not captured. Check attachKeyEvent call and jQuery(window).on mock.");
            }
        };

        it("should trigger click if element with click handler is found for 'esc'", () => {
            setupMockBoundElement({ keyName: 'esc', exists: true, hasJsClick: true });
            simulateKeyup(keyMap.esc);
            expect(activeMockBoundElement.trigger).toHaveBeenCalledWith("click");
        });

        it("should navigate to href if element with href is found for 'enter'", () => {
            const href = "http://example.com/enter";
            setupMockBoundElement({ keyName: 'enter', href: href, exists: true });
            simulateKeyup(keyMap.enter);
            expect(window.location.href).toBe(href);
        });

        it("should trigger click for element with JS onclick attribute for 'delete'", () => {
            setupMockBoundElement({ keyName: 'delete', hasJsClick: true, exists: true });
            simulateKeyup(keyMap.delete);
            expect(activeMockBoundElement.trigger).toHaveBeenCalledWith("click");
        });

        it("should do nothing if no element is found for 'tab'", () => {
            setupMockBoundElement({ keyName: 'tab', exists: false });
            simulateKeyup(keyMap.tab);
            expect(window.location.href).toBe(''); // Assuming href starts empty
            if (activeMockBoundElement && activeMockBoundElement.trigger) {
                expect(activeMockBoundElement.trigger).not.toHaveBeenCalled();
            }
        });

        it("should throw error if multiple elements are found for a key binding", () => {
            const originalJQueryForThisTestOnly = global.jQuery;
            global.jQuery = jest.fn(selector => {
                if (selector === window) {
                    const jqWin = { ...global.mockJQueryInstance, _selector: 'window' };
                    jqWin.on = jest.fn((event, handler) => { if (event === 'keyup') keyupHandler = handler; return jqWin; });
                    return jqWin;
                }
                if (selector === "[data-cui-click-binding='esc']") {
                    return { ...global.mockJQueryInstance, length: 2, _selector: selector, _expectedKeyName: 'esc' };
                }
                return originalJQueryForThisTestOnly(selector);
            });
            global.$ = global.jQuery;
            attachKeyEvent(); // Re-attach with this specific mock

            expect(() => {
                simulateKeyup(keyMap.esc);
            }).toThrow("Element with the same key binding already exists.");
            global.jQuery = originalJQueryForThisTestOnly; // Restore
            global.$ = global.jQuery;
        });
    });

    describe("cuiTagDispose", () => {
        const targetId = "myTag";
        let mockTagElement, mockDisposeInfoElement;
        let originalJQueryMock;

        beforeEach(() => {
            originalJQueryMock = global.jQuery;
            mockTagElement = { ...global.mockJQueryInstance, hide: jest.fn() };
            mockDisposeInfoElement = { ...global.mockJQueryInstance, val: jest.fn() };

            global.jQuery = jest.fn(selector => {
                if (selector === escapeClientId(targetId)) return mockTagElement;
                if (selector === escapeClientId(targetId) + "_disposed-info") return mockDisposeInfoElement;
                return originalJQueryMock(selector);
            });
            global.$ = global.jQuery;
        });
        afterEach(() => {
            global.jQuery = originalJQueryMock;
            global.$ = global.jQuery;
        });

        it("should set disposed-info value to 'true' and hide the tag", () => {
            cuiTagDispose(targetId);
            expect(mockDisposeInfoElement.val).toHaveBeenCalledWith("true");
            expect(mockTagElement.hide).toHaveBeenCalledWith("slow");
        });
    });

    describe("cuicollapsePanel", () => {
        const targetId = "myPanel";
        let mockCollapseInfo, mockTargetObject, mockParent, mockIcon;
        let callbackFn;
        let originalJQueryMock;

        beforeEach(() => {
            originalJQueryMock = global.jQuery;
            callbackFn = jest.fn();
            mockCollapseInfo = { ...global.mockJQueryInstance, val: jest.fn().mockReturnValue("false") };
            mockTargetObject = { ...global.mockJQueryInstance, collapse: jest.fn(), parent: jest.fn() };
            mockIcon = { ...global.mockJQueryInstance, toggleClass: jest.fn() };
            mockParent = { ...global.mockJQueryInstance, find: jest.fn(), attr: jest.fn().mockReturnValue(targetId + "_parent") };

            mockTargetObject.parent.mockReturnValue(mockParent);
            mockParent.find.mockImplementation(findSelector => {
                if (findSelector.includes("span.cui_panel_toggle")) return mockIcon;
                if (findSelector === ".panel-collapse") return { ...global.mockJQueryInstance, hasClass: jest.fn().mockReturnValue(false) };
                return {...global.mockJQueryInstance, _selector: findSelector};
            });

            global.jQuery = jest.fn(selector => {
                if (selector === escapeClientId(targetId) + "_collapse-info") return mockCollapseInfo;
                if (selector === escapeClientId(targetId) + "_body-collapse") return mockTargetObject;
                return originalJQueryMock(selector);
            });
            global.$ = global.jQuery;
        });
        afterEach(() => {
            global.jQuery = originalJQueryMock;
            global.$ = global.jQuery;
        });

        it("should toggle collapse state, update icon, and call callback after timeout (no async)", () => {
            cuicollapsePanel(targetId, "false", callbackFn);
            expect(mockCollapseInfo.val).toHaveBeenCalledWith("true");
            expect(mockTargetObject.collapse).toHaveBeenCalledWith('toggle');

            jest.advanceTimersByTime(350);

            expect(mockIcon.toggleClass).toHaveBeenCalledWith("cui-icon-triangle_e", true);
            expect(mockIcon.toggleClass).toHaveBeenCalledWith("cui-icon-triangle_s", false);
            expect(global.faces.ajax.request).not.toHaveBeenCalled(); // global.faces from setup
            expect(callbackFn).toHaveBeenCalledTimes(1);
        });

        it("should make AJAX request if asyncUpdate is true", () => {
            cuicollapsePanel(targetId, "true", null);
            jest.advanceTimersByTime(350);
            expect(global.faces.ajax.request).toHaveBeenCalledWith(targetId + "_parent", null, { execute: '@this' }); // global.faces from setup
        });
    });
});
