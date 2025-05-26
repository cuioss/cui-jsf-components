const fs = require('fs');
const path = require('path');
// const vm = require('vm'); // No longer using vm, removed as per instruction

// Define the path to the source files
const basePath = path.join(__dirname, '../../main/resources/META-INF/resources/de.cuioss.javascript/');

const filesToLoad = [
    'cui_utilities.js', 
    'session.js',       
    'portal.js',        
    'tabcontrol.js',    
    'cui.js'            
];

// Ensure Cui and its namespaces are initialized on the global object
// These lines ensure the global.Cui object and its namespaces exist before scripts try to use/augment them.
// JSDOM provides 'window' which is aliased to 'global' in Jest's 'jsdom' environment.
// So, global.Cui is equivalent to window.Cui here.
global.Cui = global.Cui || {}; 
global.Cui.Core = global.Cui.Core || {};
global.Cui.Utilities = global.Cui.Utilities || {};
global.Cui.Session = global.Cui.Session || {};
global.Cui.Portal = global.Cui.Portal || {};
global.Cui.TabControl = global.Cui.TabControl || {};

filesToLoad.forEach(file => {
    const filePath = path.resolve(basePath, file); 
    try {
        const scriptContent = fs.readFileSync(filePath, 'utf8');
        // Changed from vm.runInThisContext to direct eval as per instruction
        try {
            eval(scriptContent); // Execute in the current test file's scope
        } catch (e) {
            console.error(`Error evaluating script ${file} using eval:`, e);
            throw e; // Re-throw to make the test fail clearly if a script has syntax errors
        }
    } catch (err) { // This outer catch handles fs.readFileSync errors
        console.error(`Failed to load script ${file} at ${filePath}:`, err);
        throw err; 
    }
});


// --- Existing Mocks for jQuery, faces, window, document ---
const mockJQueryInstance = {
  val: jest.fn().mockReturnThis(),
  show: jest.fn().mockReturnThis(),
  hide: jest.fn().mockReturnThis(),
  on: jest.fn().mockReturnThis(),
  trigger: jest.fn().mockReturnThis(),
  scroll: jest.fn(callback => {
    mockJQueryInstance._scrollCallback = callback;
    return mockJQueryInstance;
  }),
  scrollTop: jest.fn().mockReturnValue(0),
  attr: jest.fn().mockReturnValue(''),
  removeAttr: jest.fn().mockReturnThis(),
  is: jest.fn().mockReturnValue(false),
  select: jest.fn().mockReturnThis(),
  prop: jest.fn().mockReturnThis(),
  tooltip: jest.fn().mockReturnThis(),
  addClass: jest.fn().mockReturnThis(),
  removeClass: jest.fn().mockReturnThis(),
  toggleClass: jest.fn().mockReturnThis(),
  find: jest.fn().mockReturnThis(),
  parent: jest.fn().mockReturnThis(),
  collapse: jest.fn().mockReturnThis(),
  modal: jest.fn(),
  _eventHandlers: {},
  _triggerScroll: () => {
    if (mockJQueryInstance._scrollCallback) mockJQueryInstance._scrollCallback();
  },
  _simulateKeyup: (keyCode) => {
    if (mockJQueryInstance._eventHandlers && mockJQueryInstance._eventHandlers['keyup']) {
        mockJQueryInstance._eventHandlers['keyup']({ which: keyCode });
    }
  },
  get: jest.fn(index => (index === 0 ? { _isMockDomElement: true } : undefined)),
  length: 0,
  click: jest.fn(),
};

global.jQuery_data_storage = new Map();
// Default jQuery mock, will be overridden in specific describe blocks if needed
global.jQuery = jest.fn(selector => {
  // If jQuery is called with an object that is already a Jest mock (has .mock property), return it directly.
  // This handles cases like jQuery(mockedElement).is(...)
  if (typeof selector === 'object' && selector !== null && selector.trigger && typeof selector.trigger.mock === 'function') {
    return selector;
  }
  // Fallback for other cases (string selectors, window, document)
  const instance = {...mockJQueryInstance, _selector: selector};
  if (typeof selector === 'string' && selector.startsWith('[data-cui-click-binding')) {
      instance.length = 1; 
  }
  return instance;
});
global.$ = global.jQuery; // Alias
jQuery._data = jest.fn((element, eventName) => {
    if (!element || !element._isMockDomElement) return undefined;
    const elementStore = global.jQuery_data_storage.get(element);
    if (!elementStore) return undefined;
    return eventName === 'events' ? elementStore.events : undefined;
});

global.faces = {
  ajax: {
    addOnError: jest.fn(), 
    addOnEvent: jest.fn(),
    request: jest.fn(),
  }
};

let currentWindowName = 'initialWindowName';
const mockOpenedWindows = {}; 

global.window.open = jest.fn((urlOrName, nameOrFeatures, features) => {
  const popupName = typeof nameOrFeatures === 'string' && !features ? nameOrFeatures : (urlOrName && !urlOrName.includes('/') ? urlOrName : `_blank${Math.random()}`);
  const mockPopup = {
    focus: jest.fn(),
    close: jest.fn(() => { mockPopup.closed = true; }),
    location: { reload: jest.fn(), href: '' }, 
    name: popupName,
    closed: false,
    _isMockWindow: true,
  };
  if (urlOrName && !urlOrName.includes('/')) {
     if (mockOpenedWindows[urlOrName] && !mockOpenedWindows[urlOrName].closed) {
        return mockOpenedWindows[urlOrName];
     }
  }
  mockOpenedWindows[popupName] = mockPopup;
  return mockPopup;
});
Object.defineProperty(global.window, 'name', {
    get: () => currentWindowName,
    set: (value) => { currentWindowName = value; },
    configurable: true
});
Object.defineProperty(global.window, 'location', {
    value: { href: '' }, 
    writable: true,
    configurable: true,
});

global.document.execCommand = jest.fn();
global.console = { ...global.console, log: jest.fn(), warn: jest.fn(), error: jest.fn() };


jest.useFakeTimers();

beforeEach(() => {
  jest.clearAllMocks(); 
  
  if (global.Cui && global.Cui.Core) { 
    global.Cui.Core._onIdleArray = []; 
  }
  if (global.Cui && global.Cui.Session) { 
    global.Cui.Session.timeout = null;
    global.Cui.Session.interval = 0;
    global.Cui.Session.id = '';
    global.Cui.Session._storedCallback = null; 
  }
  if (global.Cui && global.Cui.TabControl) { 
    global.Cui.TabControl._defaultPopup = null;
  }

  currentWindowName = 'initialWindowName';
  for (const key in mockOpenedWindows) delete mockOpenedWindows[key];
  global.window.location.href = ''; 
  global.jQuery_data_storage.clear();

  Object.values(mockJQueryInstance).forEach(mockFn => {
    if (jest.isMockFunction(mockFn)) {
      mockFn.mockClear();
    }
  });
  mockJQueryInstance.val.mockReturnValue('');
  mockJQueryInstance.scrollTop.mockReturnValue(0);
  mockJQueryInstance.is.mockReturnValue(false);
  mockJQueryInstance.attr.mockReturnValue('');
  mockJQueryInstance.length = 0;
  // Reset global.jQuery to its default general mock
  global.jQuery = jest.fn(selector => {
      if (typeof selector === 'object' && selector !== null && selector.trigger && typeof selector.trigger.mock === 'function') {
        return selector;
      }
      const instance = {...mockJQueryInstance, _selector: selector};
      if (typeof selector === 'string' && selector.startsWith('[data-cui-click-binding')) {
          instance.length = 1; 
      }
      return instance;
    });
  global.$ = global.jQuery;
  if (jQuery._data && jest.isMockFunction(jQuery._data)) {
      jQuery._data.mockClear();
  }
});


// --- Original Tests (should now run against actual converted JS code) ---

describe("Cui.Utilities.escapeClientId (with loaded code)", () => {
  it("should escape colons in strings", () => {
    expect(global.Cui.Utilities.escapeClientId("form:myInput")).toEqual("#form\\:myInput");
  });

  it("should not change strings without colons, only prepends #", () => {
    expect(global.Cui.Utilities.escapeClientId("myInput")).toEqual("#myInput");
  });
});

describe("Cui.Core.getData (with loaded code)", () => {
    it("should parse URL parameters correctly", () => {
        const url = "http://example.com?param1=value1&param2=value%202";
        const data = global.Cui.Core.getData(url);
        expect(data.param1).toBe("value1");
        expect(data.param2).toBe("value 2");
    });

    it("should use window.location.search if URL is not provided", () => {
        const originalLocation = global.window.location;
        delete global.window.location; 
        global.window.location = { ...originalLocation, search: "?mockParam=mockValue" };
        
        const data = global.Cui.Core.getData(""); 
        expect(data.mockParam).toBe("mockValue");

        global.window.location = originalLocation; 
    });
});


describe("Cui.Session (with loaded code)", () => {
    const intervalSec = 5;
    const linkId = "logoutLink";
    let mockCallback; 
    let mockLinkElement; 

    beforeEach(() => {
        jest.clearAllTimers(); 
        mockCallback = jest.fn();
        mockLinkElement = { ...mockJQueryInstance, click: jest.fn(), 0: { click: jest.fn() }, length: 1 };
        
        // This jQuery mock is specific to Cui.Session tests
        const sessionSpecificJQueryMock = jest.fn(selector => {
            if (selector === global.Cui.Utilities.escapeClientId(linkId)) {
                return mockLinkElement;
            }
            // Fallback to a generic mock for other selectors within Session tests
            const instance = {...mockJQueryInstance, _selector: selector};
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
            global.Cui.Session.startLogoutTimeout(intervalSec, linkId, mockCallback);
            expect(global.Cui.Session.interval).toBe(intervalSec * 1000);
            expect(global.Cui.Session._storedCallback).toBe(mockCallback); 
            expect(global.Cui.Session.timeout).not.toBeNull();
            expect(jest.getTimerCount()).toBe(1); 

            jest.advanceTimersByTime(intervalSec * 1000);
            expect(mockCallback).toHaveBeenCalledTimes(1);
            expect(jest.getTimerCount()).toBe(0);
        });

        it("should set a timeout to click the linkId if no callback is provided", () => {
            global.Cui.Session.startLogoutTimeout(intervalSec, linkId);
            expect(global.Cui.Session.timeout).not.toBeNull();
            expect(jest.getTimerCount()).toBe(1);

            jest.advanceTimersByTime(intervalSec * 1000);
            const jqClicked = mockLinkElement.click.mock.calls.length > 0;
            const domClicked = mockLinkElement[0].click.mock.calls.length > 0;
            expect(jqClicked || domClicked).toBe(true);
            expect(jest.getTimerCount()).toBe(0); 
        });

        it("should use interval of 1 second if intervalSec is undefined", () => {
            global.Cui.Session.startLogoutTimeout(undefined, linkId, mockCallback);
            expect(global.Cui.Session.interval).toBe(1000);
            expect(global.Cui.Session.timeout).not.toBeNull();
            expect(jest.getTimerCount()).toBe(1);
        });

        it("should not set timeout if interval is 0 or less", () => {
            global.Cui.Session.startLogoutTimeout(0, linkId, mockCallback);
            expect(global.Cui.Session.timeout).toBeNull(); 
            expect(jest.getTimerCount()).toBe(0);
        });
    });

    describe("resetLogoutTimeout", () => {
        it("should stop the current timeout and start a new one with previously stored settings", () => {
            global.Cui.Session.startLogoutTimeout(intervalSec, linkId, mockCallback);
            const firstTimeoutId = global.Cui.Session.timeout;
            expect(jest.getTimerCount()).toBe(1);

            global.Cui.Session.resetLogoutTimeout(); 
            expect(jest.getTimerCount()).toBe(1); 
            expect(global.Cui.Session.timeout).not.toBe(firstTimeoutId); 

            jest.advanceTimersByTime(intervalSec * 1000);
            expect(mockCallback).toHaveBeenCalledTimes(1);
            expect(jest.getTimerCount()).toBe(0); 
        });

         it("should correctly reset and trigger link click if original had no callback", () => {
            global.Cui.Session.startLogoutTimeout(intervalSec, linkId); 
            expect(jest.getTimerCount()).toBe(1);
            const firstTimeoutId = global.Cui.Session.timeout;

            global.Cui.Session.resetLogoutTimeout(); 
            expect(jest.getTimerCount()).toBe(1); 
            expect(global.Cui.Session.timeout).not.toBe(firstTimeoutId); 

            jest.advanceTimersByTime(intervalSec * 1000);
            const jqClicked = mockLinkElement.click.mock.calls.length > 0;
            const domClicked = mockLinkElement[0].click.mock.calls.length > 0;
            expect(jqClicked || domClicked).toBe(true);
            expect(jest.getTimerCount()).toBe(0);
        });
    });

    describe("stopLogoutTimeout", () => {
        it("should clear the currently active timeout", () => {
            global.Cui.Session.startLogoutTimeout(intervalSec, linkId, mockCallback);
            expect(jest.getTimerCount()).toBe(1);
            
            global.Cui.Session.stopLogoutTimeout();
            expect(global.Cui.Session.timeout).toBeNull(); 
            expect(jest.getTimerCount()).toBe(0); 
        });
    });
});


describe("Cui.Portal (with loaded code)", () => {
    describe("attachKeyEvent", () => {
        let originalWindowLocation;
        let keyupHandler; 
        let activeMockBoundElement = null; 
        let originalGlobalJQuery; // To store the truly global jQuery mock

        beforeEach(() => {
            originalWindowLocation = global.window.location;
            delete global.window.location;
            global.window.location = { href: '', assign: jest.fn() };
            activeMockBoundElement = null; 
            originalGlobalJQuery = global.jQuery; // Store the global jQuery mock

            // This jQuery mock is specific to attachKeyEvent tests
            global.jQuery = jest.fn(selector => {
                if (selector === window) {
                    const jqWin = { ...mockJQueryInstance, _selector: 'window' };
                    jqWin.on = jest.fn((event, handler) => {
                        if (event === 'keyup') keyupHandler = handler; 
                        return jqWin;
                    });
                    return jqWin;
                }
                // If jQuery is called with an object that is our active mock, return it.
                if (selector === activeMockBoundElement) { // Check if the selector is the active mock object itself
                    return activeMockBoundElement;
                }
                if (typeof selector === 'string' && selector.startsWith('[data-cui-click-binding')) {
                    const match = selector.match(/\[data-cui-click-binding='(.*?)'\]/);
                    if (match && activeMockBoundElement && match[1] === activeMockBoundElement._expectedKeyName) {
                        return activeMockBoundElement;
                    }
                    // Fallback for other key binding selectors not matching the active one
                    return { ...mockJQueryInstance, length: 0, _selector: selector, trigger: jest.fn(), attr: jest.fn(), is: jest.fn().mockReturnValue(false), get: jest.fn().mockReturnValue(undefined) };
                }
                // Fallback for any other selector using the generic mock instance
                return { ...mockJQueryInstance, _selector: selector, trigger: jest.fn(), attr: jest.fn(), is: jest.fn().mockReturnValue(false), get: jest.fn().mockReturnValue(undefined), 0: undefined, length: 0 };
            });
            global.$ = global.jQuery;
            
            global.Cui.Portal.attachKeyEvent(); 
        });

        afterEach(() => {
            global.window.location = originalWindowLocation;
            keyupHandler = null; 
            global.jQuery = originalGlobalJQuery; // Restore original global jQuery mock
            global.$ = global.jQuery; 
        });

        const keyMap = { esc: 27, tab: 9, delete: 46, enter: 13, unknown: 100 };

        function setupMockBoundElement(options = {}) {
            activeMockBoundElement = {
                ...mockJQueryInstance, 
                length: options.exists !== false ? 1 : 0,
                attr: jest.fn(attrName => (attrName === 'href' ? options.href : undefined)),
                trigger: jest.fn(),
                is: jest.fn(sel => {
                    // For the 'esc' test, make .is('[onclick]') true to test that path
                    if (options.keyName === 'esc' && sel === '[onclick]') return true; 
                    return sel === '[onclick]' && !!options.hasJsClick;
                }),
                get: jest.fn(index => (index === 0 ? { _isMockDomElement: true } : undefined)),
                _expectedKeyName: options.keyName,
            };
            
            // For the 'esc' test, ensure we don't set up jQuery event data,
            // so it's forced to check the .is('[onclick]') path.
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
                throw new Error("Keyup handler not captured for simulation. Check if Cui.Portal.attachKeyEvent was called and jQuery(window).on was mocked correctly.");
            }
        };

        it("should trigger click if element with click handler is found for 'esc'", () => {
            // Forcing the .is('[onclick]') path for 'esc'
            setupMockBoundElement({ keyName: 'esc', exists: true, hasJsClick: true }); // hasJsClick will make .is() true
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
            // Test the .is('[onclick]') path for 'delete' key as well
            setupMockBoundElement({ keyName: 'delete', hasJsClick: true, exists: true });
            simulateKeyup(keyMap.delete);
            expect(activeMockBoundElement.trigger).toHaveBeenCalledWith("click");
        });

        it("should do nothing if no element is found for 'tab'", () => {
            setupMockBoundElement({ keyName: 'tab', exists: false }); 
            simulateKeyup(keyMap.tab);
            expect(window.location.href).toBe('');
            if(activeMockBoundElement && activeMockBoundElement.trigger) { 
                 expect(activeMockBoundElement.trigger).not.toHaveBeenCalled();
            }
        });
        
        it("should throw error if multiple elements are found for a key binding", () => {
            const originalJQueryForThisTest = global.jQuery; // Store the specific attachKeyEvent jQuery
            global.jQuery = jest.fn(selector => {
                if (selector === window) { 
                    const jqWin = { ...mockJQueryInstance, _selector: 'window' };
                    jqWin.on = jest.fn((event, handler) => { if (event === 'keyup') keyupHandler = handler; return jqWin; });
                    return jqWin;
                }
                if (selector === "[data-cui-click-binding='esc']") {
                    return { ...mockJQueryInstance, length: 2, _selector: selector, _expectedKeyName: 'esc' }; 
                }
                return originalJQueryForThisTest(selector); // Delegate to the one set in attachKeyEvent's beforeEach
            });
            global.$ = global.jQuery;
            
            // We need attachKeyEvent to run with this *very specific* jQuery mock for this test.
            // The one in beforeEach for attachKeyEvent might not be specific enough for this throw test.
            // So, we re-call it here. The keyupHandler will be re-captured.
            global.Cui.Portal.attachKeyEvent(); 

            expect(() => {
                simulateKeyup(keyMap.esc);
            }).toThrow("Element with the same key binding already exists.");

            // No need to call setupMockBoundElement here as the test is about jQuery finding multiple elements.
            // Restore the jQuery mock that was active for other attachKeyEvent tests
            global.jQuery = originalJQueryForThisTest;
            global.$ = global.jQuery;
        });
    });

    describe("cuiTagDispose", () => {
        const targetId = "myTag";
        let mockTagElement, mockDisposeInfoElement;
        let originalJQueryMock;

        beforeEach(() => {
            originalJQueryMock = global.jQuery;
            mockTagElement = { ...mockJQueryInstance, hide: jest.fn() };
            mockDisposeInfoElement = { ...mockJQueryInstance, val: jest.fn() };

            global.jQuery = jest.fn(selector => {
                if (selector === global.Cui.Utilities.escapeClientId(targetId)) return mockTagElement;
                if (selector === global.Cui.Utilities.escapeClientId(targetId) + "_disposed-info") return mockDisposeInfoElement;
                return originalJQueryMock(selector);
            });
            global.$ = global.jQuery;
        });
        afterEach(() => {
            global.jQuery = originalJQueryMock;
            global.$ = global.jQuery;
        });


        it("should set disposed-info value to 'true' and hide the tag", () => {
            global.Cui.Portal.cuiTagDispose(targetId);
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
            mockCollapseInfo = { ...mockJQueryInstance, val: jest.fn().mockReturnValue("false") };
            mockTargetObject = { ...mockJQueryInstance, collapse: jest.fn(), parent: jest.fn() };
            mockIcon = { ...mockJQueryInstance, toggleClass: jest.fn() };
            mockParent = { ...mockJQueryInstance, find: jest.fn(), attr: jest.fn().mockReturnValue(targetId + "_parent") };

            mockTargetObject.parent.mockReturnValue(mockParent);
            mockParent.find.mockImplementation(findSelector => {
                if (findSelector.includes("span.cui_panel_toggle")) return mockIcon;
                if (findSelector === ".panel-collapse") return { ...mockJQueryInstance, hasClass: jest.fn().mockReturnValue(false) };
                return {...mockJQueryInstance, _selector: findSelector};
            });
            
            global.jQuery = jest.fn(selector => {
                if (selector === global.Cui.Utilities.escapeClientId(targetId) + "_collapse-info") return mockCollapseInfo;
                if (selector === global.Cui.Utilities.escapeClientId(targetId) + "_body-collapse") return mockTargetObject;
                return originalJQueryMock(selector);
            });
            global.$ = global.jQuery;
        });
        afterEach(() => {
            global.jQuery = originalJQueryMock;
            global.$ = global.jQuery;
        });

        it("should toggle collapse state, update icon, and call callback after timeout (no async)", () => {
            global.Cui.Portal.cuicollapsePanel(targetId, "false", callbackFn);
            expect(mockCollapseInfo.val).toHaveBeenCalledWith("true"); 
            expect(mockTargetObject.collapse).toHaveBeenCalledWith('toggle');
            
            jest.advanceTimersByTime(350); 

            expect(mockIcon.toggleClass).toHaveBeenCalledWith("cui-icon-triangle_e", true); 
            expect(mockIcon.toggleClass).toHaveBeenCalledWith("cui-icon-triangle_s", false);
            expect(faces.ajax.request).not.toHaveBeenCalled();
            expect(callbackFn).toHaveBeenCalledTimes(1);
        });

        it("should make AJAX request if asyncUpdate is true", () => {
            global.Cui.Portal.cuicollapsePanel(targetId, "true", null);
            jest.advanceTimersByTime(350);
            expect(faces.ajax.request).toHaveBeenCalledWith(targetId + "_parent", null, { execute: '@this' });
        });
    });
});


describe("Cui.TabControl (with loaded code)", () => {
    let mockPopup1, mockPopup2;

    beforeEach(() => {
        mockPopup1 = { name: 'popup1', closed: false, location: { reload: jest.fn() }, close: jest.fn(() => mockPopup1.closed = true), _isMockWindow: true };
        mockPopup2 = { name: 'popup2', closed: false, location: { reload: jest.fn() }, close: jest.fn(() => mockPopup2.closed = true), _isMockWindow: true };
        
        global.window.open.mockImplementation((urlOrName, name) => {
             const targetName = name || (typeof urlOrName === 'string' && !urlOrName.includes('/') ? urlOrName : null);
             if (targetName === 'popup1') return mockPopup1;
             if (targetName === 'popup2') return mockPopup2;
             if (urlOrName === '' && mockOpenedWindows[targetName]) return mockOpenedWindows[targetName];

             const newPopupName = targetName || `_blank${Math.random()}`;
             const newPopup = { name: newPopupName, closed: false, location: { reload: jest.fn(), href:'' }, close: jest.fn(() => newPopup.closed = true), focus: jest.fn(), _isMockWindow: true };
             mockOpenedWindows[newPopupName] = newPopup;
             return newPopup;
        });
        if (global.Cui && global.Cui.TabControl) { 
            global.Cui.TabControl._defaultPopup = null; 
        }
    });

    describe("closePopupsByNames", () => {
        it("should close specified popups that are not the current window and are open", () => {
            currentWindowName = 'mainAppWindow'; 
            mockOpenedWindows['popup1'] = mockPopup1; 
            mockOpenedWindows['popup2'] = mockPopup2;

            global.Cui.TabControl.closePopupsByNames("popup1,popup2");

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
            currentWindowName = 'popup1'; 
            mockOpenedWindows['popup1'] = mockPopup1;
            mockOpenedWindows['popup2'] = mockPopup2;

            global.Cui.TabControl.closePopupsByNames("popup1,popup2");

            expect(window.open).not.toHaveBeenCalledWith('', 'popup1'); 
            expect(mockPopup1.close).not.toHaveBeenCalled();

            expect(window.open).toHaveBeenCalledWith('', 'popup2'); 
            expect(mockPopup2.close).toHaveBeenCalled();
        });
    });

    describe("closeLocalPopup", () => {
        it("should close the defaultPopup if it exists and is open", () => {
            const mockDefaultPopup = { name: 'default', closed: false, close: jest.fn(() => mockDefaultPopup.closed = true), _isMockWindow: true };
            global.Cui.TabControl._defaultPopup = mockDefaultPopup; 

            global.Cui.TabControl.closeLocalPopup();
            expect(mockDefaultPopup.close).toHaveBeenCalledTimes(1);
        });
    });
});
