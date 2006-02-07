var Logger = Class.create();
Logger.prototype = {
	initialize: function(parent) {
		this.parent = parent;
		this.console = document.createElement('div');
		this.console.style.backgroundColor = 'black';
		this.console.style.height = '600px';
		this.console.style.overflow = 'scroll';
		this.console.style.clear = 'both';
		$(parent).appendChild(this.console);
	},
	_log: function(text, color) {
		var div = document.createElement('div');
		div.innerHTML = text;
		div.style.color = color;
		this.console.appendChild(div);
		this.console.scrollTop = this.console.scrollHeight;
	},
	error: function(text) {
		this._log(text, 'red');
	},
	warn: function(text) {
		this._log(text, 'yellow');
	},
	info: function(text) {
		this._log(text, '#0F0');
	},
	debug: function(text) {
		this._log(text, 'white');
	}
};