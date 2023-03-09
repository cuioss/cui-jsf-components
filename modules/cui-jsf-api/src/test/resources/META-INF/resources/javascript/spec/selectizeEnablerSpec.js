describe("Selectize enabler script", function () {
    beforeEach(function () {
        /* Expected HTML
        <div class="form-control selectize-cancreate selectize-control multi">
          <div class="selectize-input items not-full has-options">
            <input type="text" autocomplete="off" tabindex="" id="cuiTagInput-selectized">
          </div>
          <div class="selectize-dropdown multi form-control">
            <div class="selectize-dropdown-content">
              <div class="option" data-selectable="" data-value="456c696173">Elias</div>
              <div class="option" data-selectable="" data-value="456d696c6961">Emilia</div>
            </div>
          </div>
        </div>
        */
        jQuery(document.body).append('<input id="cuiTagInput" name="cuiTagInput" type="text"' +
            ' data-selectize="true"' +
            ' data-maxitems="42"' +
            ' data-options="[{&quot;label&quot;:&quot;Elias&quot;,&quot;value&quot;:&quot;456c696173&quot;},{&quot;label&quot;:&quot;Emilia&quot;,&quot;value&quot;:&quot;456d696c6961&quot;}]"' +
            ' data-persist="false"' +
            ' data-cancreate="false"' +
            ' data-wrapperclass="selectize-cannotcreate selectize-control"' +
            ' data-delimiter="#"' +
            ' data-removebutton="true"/>');

        initSelectize();
    });

    afterEach(function () {
        jQuery("#cuiTagInput").remove();
        jQuery(".selectize-control").remove();
    });

    it("should selectize", function () {
        expect(jQuery("#cuiTagInput").hasClass("selectized")).toBe(true);
    });

    it("should init correct settings", function () {
        var selectize = jQuery(".selectized")[0].selectize;
        var settings = selectize.settings;
        expect(settings.delimiter).toBe("#");
        expect(settings.create).toBe(false);
        expect(settings.maxItems).toBe(42);
        expect(settings.mode).toBe("multi");
        expect(settings.create).toBe(false);
        expect(settings.optgroupLabelField).toBe("label");
        expect(settings.optgroupValueField).toBe("value");
        expect(settings.searchField).toBe("label");
        expect(settings.wrapperClass).toBe("selectize-cannotcreate selectize-control");
        expect(settings.persist).toBe(false);
        expect(JSON.stringify(settings.plugins)).toBe(JSON.stringify(["remove_button"]));
        expect(JSON.stringify(settings.sortField)).toBe(JSON.stringify([{field: "label", direction: "asc"}]));
    });

    it("should init options", function () {
        var selectizeOptions = jQuery(".selectized")[0].selectize.options;
        var expectedOptions = {};
        expectedOptions["456c696173"] = {label: "Elias", value: "456c696173", "$order": 1};
        expectedOptions["456d696c6961"] = {label: "Emilia", value: "456d696c6961", "$order": 2};
        expect(JSON.stringify(selectizeOptions)).toBe(JSON.stringify(expectedOptions));
    });
});
