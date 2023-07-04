describe("Data from Url", function() {

	let data = Cui.Core.getData("http://www.example.com/search?q=Variable+Global&search=fast&this=+Hello%20&empty");

    it("should contain parameter q = Variable Global", function() {
		expect(data["q"]).toEqual("Variable Global");
	});
	it("should contain parameter search = fast", function() {
		expect(data["search"]).toEqual("fast");
	});
	it("should contain parameter this = Hello", function() {
		expect(data["this"]).toEqual(" Hello ");
	});
	it("should contain parameter empty = ", function() {
		expect(data["empty"]).toEqual("");
	});
});


describe("Boolean parser", function() {
    it("should parse True to true", function() {
		expect(Cui.Utilities.parseBoolean("True")).toEqual(true);
	});
	it("should parse true to true", function() {
		expect(Cui.Utilities.parseBoolean("true")).toEqual(true);
	});
	it("should parse tRue to true", function() {
		expect(Cui.Utilities.parseBoolean("tRue")).toEqual(true);
	});
	it("should parse false to false", function() {
		expect(Cui.Utilities.parseBoolean("false")).toEqual(false);
	});
	it("should parse some to false", function() {
		expect(Cui.Utilities.parseBoolean("some")).toEqual(false);
	});
	it("should parse 1 to false", function() {
		expect(Cui.Utilities.parseBoolean("1")).toEqual(false);
	});
	it("should parse 0 to false", function() {
		expect(Cui.Utilities.parseBoolean("0")).toEqual(false);
	});
});

describe("Invert Boolean", function() {
	it("should return false to true", function() {
		expect(Cui.Utilities.invertBoolean("true")).toEqual("false");
	});
	it("should return false to True", function() {
		expect(Cui.Utilities.invertBoolean("True")).toEqual("false");
	});
	it("should return true to false", function() {
		expect(Cui.Utilities.invertBoolean("false")).toEqual("true");
	});
	it("should return true to 1", function() {
		expect(Cui.Utilities.invertBoolean("1")).toEqual("true");
	});
	it("should return true to 0", function() {
		expect(Cui.Utilities.invertBoolean("1")).toEqual("true");
	});
	it("should return true to some", function() {
		expect(Cui.Utilities.invertBoolean("1")).toEqual("true");
	});
});

describe("Condition with boolean parser", function() {
	it("shouldn't go into true condition", function() {
		let i = 0;
		if (Cui.Utilities.parseBoolean("false")) {
			i = 1;
		}
		expect(i).toEqual(0);
	});
	it("should go into true condition", function() {
		let i = 0;
		if (Cui.Utilities.parseBoolean("true")) {
			i = 1;
		}
		expect(i).toEqual(1);
	});
	it("exactly false shouldn't go into true condition", function() {
		let i = 0;
		if (false) {
			i = 1;
		}
		expect(i).toEqual(0);
	});
	it("exactly true should go into true condition", function() {
		let i = 0;
		if (true) {
			i = 1;
		}
		expect(i).toEqual(1);
	});
	it("and w/o equal shouldn't go into true condition", function() {
		let i = 0;
		let value = "false";
		if (Boolean(value.toLowerCase()) === false) {
			i = 1;
		}
		expect(i).toEqual(0);
	});
	it("and w/o equal should go into true condition", function() {
		let i = 0;
		let value = "true";
		if (Boolean(value.toLowerCase()) === true) {
			i = 1;
		}
		expect(i).toEqual(1);
	});
});

// describe("Session", function() {
//     it("should start", function() {
// 		Cui.Session.startLogoutTimeout();
// 		expect(true).toEqual(true);
// 	});
// 	/*
// 	* Remove the following two methods to test refresh.
// 	*/
// 	it("should reset", function() {
// 		Cui.Session.resetLogoutTimeout();
// 		expect(true).toEqual(true);
// 	});
// 	it("should stop", function() {
// 		Cui.Session.stopLogoutTimeout();
// 		expect(true).toEqual(true);
// 	});
// });

