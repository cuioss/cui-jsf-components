// Imports for resetting state
// import { _resetCoreState } from '../../main/resources/META-INF/resources/de.cuioss.javascript/cui.js'; // No longer needed
// import { _resetSessionState } from '../../main/resources/META-INF/resources/de.cuioss.javascript/session.js'; // No longer needed, Session is a class
// import { _resetTabControlState } from '../../main/resources/META-INF/resources/de.cuioss.javascript/tabcontrol.js'; // No longer needed, TabControl is a class

// --- Global Mocks for jQuery, faces, window, document ---

// jQuery mock setup
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
  if (typeof selector === 'object' && selector !== null && selector.trigger && typeof selector.trigger.mock === 'function') {
    return selector;
  }
  const instance = {...mockJQueryInstance, _selector: selector};
  if (typeof selector === 'string' && selector.startsWith('[data-cui-click-binding')) {
      instance.length = 1;
  }
  return instance;
});
global.$ = global.jQuery; // Alias

// jQuery._data mock
global.jQuery._data = jest.fn((element, eventName) => {
    if (!element || !element._isMockDomElement) return undefined;
    const elementStore = global.jQuery_data_storage.get(element);
    if (!elementStore) return undefined;
    return eventName === 'events' ? elementStore.events : undefined;
});


// faces mock
global.faces = {
  ajax: {
    addOnError: jest.fn(),
    addOnEvent: jest.fn(),
    request: jest.fn(),
  }
};

// window and document mocks
global.currentWindowName = 'initialWindowName'; // Exposed on global
global.mockOpenedWindows = {}; // Exposed on global

global.window.open = jest.fn((urlOrName, nameOrFeatures, features) => {
  // Determine the name of the window to be opened/referenced
  const popupName = (typeof nameOrFeatures === 'string' && !features)
    ? nameOrFeatures
    : (urlOrName && !urlOrName.includes('/') ? urlOrName : null);

  // If a name is determined and it exists in our mockOpenedWindows, return that existing mock
  if (popupName && global.mockOpenedWindows[popupName]) {
    // Update its closed status based on the global mock in case it was closed directly
    // (though tests usually call .close() on the returned object)
    if(global.mockOpenedWindows[popupName].closed === undefined) global.mockOpenedWindows[popupName].closed = false;
    return global.mockOpenedWindows[popupName];
  }

  // If no existing window, create a new one
  const newPopupName = popupName || `_blank${Math.random()}`;
  const newMockPopup = {
    focus: jest.fn(),
    close: jest.fn(() => { newMockPopup.closed = true; }), // Ensure 'this' refers to newMockPopup
    location: { reload: jest.fn(), href: '' },
    name: newPopupName,
    closed: false,
    _isMockWindow: true,
  };

  // Store it in mockOpenedWindows if it has a persistent name
  // For _blank or new random names, they are typically not re-referenced by name
  if (popupName) { // Store only if it was opened with a specific name initially
      global.mockOpenedWindows[newPopupName] = newMockPopup;
  }
  return newMockPopup;
});

Object.defineProperty(global.window, 'name', {
    get: () => global.currentWindowName, // Use global
    set: (value) => { global.currentWindowName = value; }, // Use global
    configurable: true
});

Object.defineProperty(global.window, 'location', {
    value: { href: '' },
    writable: true,
    configurable: true,
});

global.document.execCommand = jest.fn();

// console mocks
global.console = { ...global.console, log: jest.fn(), warn: jest.fn(), error: jest.fn() };

// jest.useFakeTimers()
jest.useFakeTimers();

// Global beforeEach
beforeEach(() => {
  jest.clearAllMocks();

  // Reset states for each imported module
  // _resetCoreState(); // No longer needed
  // _resetSessionState(); // No longer needed
  // _resetTabControlState(); // No longer needed, TabControl is a class

  // Reset global test states
  global.currentWindowName = 'initialWindowName'; // Use global
  for (const key in global.mockOpenedWindows) { // Clear the mockOpenedWindows object
    delete global.mockOpenedWindows[key];
  }
  if (global.window && global.window.location) { // Ensure window.location exists
    global.window.location.href = '';
  }
  global.jQuery_data_storage.clear();

  // Reset jQuery instance mocks
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
  global.$ = global.jQuery; // Re-alias

  // Clear jQuery._data mock calls
  if (global.jQuery._data && jest.isMockFunction(global.jQuery._data)) {
      global.jQuery._data.mockClear();
  }
});
