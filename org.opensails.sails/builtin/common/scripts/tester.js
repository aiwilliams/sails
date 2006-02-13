var Tester = Class.create();
Tester.prototype = {
	passed: [],
	failed: [],
	initialize: function() {
		window.Ajax.getTransport = this.getTransport.bind(this);
	},
	responses: {},
	ajax: function(url, response) {
		this.responses[url] = response;
	},
	getTransport: function() {
		return new ShamTransport(this);
	},
	assertEquals: function(expected, actual, message) {
		if (expected && expected.constructor == Array) {
			actual = $A(actual);
			this.assertEquals(expected.length, actual.length, ' when comparing array [' + expected + '] to array [' + actual + ']');
			expected.each(function(item, index) {
				this.assertEquals(item, actual[index]);
			}.bind(this));
		} else
			this.assertTrue(expected == actual, 'Expected [' + expected + '] but was [' + actual + '] ' + (message||''));
	},
	assertJson: function(expected, actual) {
		if (typeof expected != 'string')
			expected = JSON.stringify(expected);
		if (typeof actual != 'string')
			actual = JSON.stringify(actual);
		this.assertEquals(expected, actual);
	},
	assertUndefined: function(actual, message) {
		this.assertTrue(typeof actual == 'undefined', 'Expected [undefined] but was [' + actual + '] ' + (message||''));
	},
	assertNull: function(actual, message) {
		this.assertEquals(null, actual, message);
	},
	assertTrue: function(condition, message) {
		if (condition)
			this.passed.push(message);
		else {
			log.error(message);
			this.failed.push(message);
		}
	},
	assertHidden: function(node, message) {
		this.assertTrue(Element.getStyle(node, 'display') == 'none', 'Expected node [' + node + '] to be hidden ' + (message||''));
	},
	assertShown: function(node, message) {
		this.assertTrue(Element.getStyle(node, 'display') != 'none', 'Expected node [' + node + '] to be shown ' + (message||''));
	},
	output: function() {
		if (this.failed.length == 0)
			log.info('Successful: ' + this.passed.length);
		else {
			log.error('Failed: ' + this.failed.length + ' Successful: ' + this.passed.length);
		}
	}
};

var ShamTransport = Class.create();
ShamTransport.prototype = {
	initialize: function(tester) {
		this.tester = tester;
	},
	open: function(method, url, asynchronous) {
		log.debug(url);
		// prototype appends '&_=' when there are parameters
		this.responseText = this.tester.responses[url] || this.tester.responses[url.substring(0, url.length - 3)];
	},
	send: function(body) {
		this.readyState = 4;
		this.status = 200;
		if (this.onreadystatechange)
			this.onreadystatechange();
	},
	setRequestHeader: function() {}
};