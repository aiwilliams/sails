var Component = {
	create: function() {
		return function(extensions) {
			Object.extend(this, extensions);
			this.initialize();
		}
	},
	callback: function(name, url, options) {
		options = options || {}
		return function() {
			$A(arguments).each(function(arg) {
				url += '/' + arg;
			});
			
			var events = $A(Ajax.Request.Events);
			events.push('Create', 'Success', 'Failure', 'Exception');
			var _this = this;
			events.each(function(listener) {
				var fname = '_' + name + listener;
				if (_this[fname])
					options['on' + listener] = _this[fname].bind(_this);
			});
			new Ajax.Request(url, options);
		}
	}
}