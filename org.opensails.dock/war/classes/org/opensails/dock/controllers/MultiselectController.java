package org.opensails.dock.controllers;

import java.util.List;

import org.opensails.sails.controller.oem.BaseController;
import org.opensails.sails.template.ITemplateRenderer;
import org.opensails.sails.template.viento.VientoBinding;
import org.opensails.viento.Binding;
import org.opensails.viento.IBinding;
import org.opensails.viento.Name;

public class MultiselectController extends BaseController {
	protected final ITemplateRenderer<IBinding> renderer;

	public MultiselectController(ITemplateRenderer<IBinding> renderer) {
		this.renderer = renderer;
	}
	
	@Name("new")
	public Multiselect create(String name, List<String> options) {
		return new Multiselect(name, options);
	}
	
	public class Multiselect {
		private final String name;
		private final List<String> options;

		public Multiselect(String name, List<String> options) {
			this.name = name;
			this.options = options;
		}
		
		@Override
		public String toString() {
			return render();
		}
		
		protected String render() {
			// not good... I shouldn't have to cast here
			IBinding binding = new VientoBinding((Binding) getBinding());
			binding.put("id", name.replace(' ', '_'));
			binding.put("name", name);
			binding.put("options", options);
			return renderer.render("components/multiselect/view", binding).toString();
		}
	}
}
