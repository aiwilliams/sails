var DotjerkysIdeal = Component.create();
DotjerkysIdeal.prototype = {
	initialize: function() {
		this.text = $(this.id);
		this.somethingElse = this.complex.firstProperty;
	},
	
	hide: function() {
		if (this.anotherProperty == "text")
			Element.hide(this.text);
		else
			Element.hide(this.container);
		
		this._doSomething('really really interesting');
	},
	
	_doSomethingComplete: function(transport) {
		this.container.innerHTML = transport.responseText;
	},
	
	_doSomethingFailed: function(transport) {
		// you get the idea
	}
}