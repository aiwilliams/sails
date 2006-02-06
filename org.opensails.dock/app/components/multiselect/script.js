var Multiselect = Class.create();
Multiselect.prototype = {
	initialize: function(dropdown) {
		this.dropdown = $(dropdown);
		this.count = $(this.dropdown.id + '_count');
		this.content = $(this.dropdown.id + '_content');
		
		Event.observe(this.dropdown, 'click', this.toggle.bind(this));
		Event.observe(document, 'click', this.clickedAnywhere.bindAsEventListener(this));
		
		Element.addClassName(this.dropdown, 'dropdown');
		Element.addClassName(this.content, 'dropdown-content');
		Position.absolutize(this.content);
		Element.hide(this.content);
		
		var labels = this.content.getElementsByTagName("label");
		for (var i=0; i<labels.length; i++) {
			Event.observe(labels[i], 'mouseenter', this.itemMouseOver.bindAsEventListener(this));
			Event.observe(labels[i].firstChild, 'click', this.itemClicked.bindAsEventListener(this));
		}
		this.itemsUpdated();
	},
	
	itemMouseOver: function(event) {
		this.select(Event.element(event));
	},
	
	items: [],
	
	itemClicked: function(event) {
		var element = Event.element(event);
		var newItems = this.items.without(element);
		if (element.checked)
			newItems.push(element);
		this.items = newItems;
		this.itemsUpdated();
	},
	
	itemsUpdated: function() {
		this.count.innerHTML = this.items.length;
		this.dropdown.title = this.items.pluck('value').join('\n');
	},
	
	select: function(selection) {
		if (this.selection != null)
			Element.removeClassName(this.selection, 'selected-item');
		this.selection = selection;
		Element.addClassName(this.selection, 'selected-item');
	},
	
	toggle: function() {
		Element.toggle(this.content);
	},
	
	clickedAnywhere: function(event) {
		if (Event.inside(event, this.dropdown) || Event.inside(event, this.content))
			return;
		Element.hide(this.content);
	}
}

Event.inside = function(event, element) {
	var target = Event.element(event);
	while(target) {
		if (target == element)
			return true;
		target = target.parentNode;
	}
	return false;
}