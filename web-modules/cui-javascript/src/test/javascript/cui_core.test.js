import { getData } from '../../main/resources/META-INF/resources/de.cuioss.javascript/cui.js';

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
    
    // Add other tests from cui.js here if any, e.g., for trim, checkLength, etc.
    // For now, only getData tests were explicitly in cui.test.js under Cui.Core
});
