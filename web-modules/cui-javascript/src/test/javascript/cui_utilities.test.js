import { escapeClientId } from '../../main/resources/META-INF/resources/de.cuioss.javascript/cui_utilities.js';

describe("cui_utilities.js", () => {
    describe("escapeClientId", () => {
        it("should escape colons in strings", () => {
            expect(escapeClientId("form:myInput")).toEqual("#form\\:myInput");
        });

        it("should not change strings without colons, only prepends #", () => {
            expect(escapeClientId("myInput")).toEqual("#myInput");
        });
    });
});
