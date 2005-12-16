package org.opensails.sails.mixins;

import java.util.Map;

import org.opensails.sails.ISailsEvent;
import org.opensails.sails.template.IMixinMethod;
import org.opensails.sails.template.ITemplateRenderer;
import org.opensails.viento.IBinding;

public class RenderMixin implements IMixinMethod {
	protected final IBinding binding;
	protected final ISailsEvent event;
	protected final ITemplateRenderer renderer;

	public RenderMixin(ISailsEvent event, IBinding binding, ITemplateRenderer renderer) {
		this.event = event;
		this.binding = binding;
		this.renderer = renderer;
	}

	public Render invoke(Object... args) {
		return new Render(args != null && args.length > 0 ? (String) args[0] : null);
	}

	public class Partial {
		protected String templateIdentifier;
		private Map<String, Object> stuff;

		public Partial(String templateIdentifier) {
			this.templateIdentifier = templateIdentifier;
		}
		
		public Partial expose(Map<String, Object> stuff) {
			this.stuff = stuff;
			return this;
		}

		@Override
		@SuppressWarnings("unchecked")
		public String toString() {
			IBinding partialLocalBinding = renderer.createBinding(binding);
			if (templateIdentifier.contains("/")) templateIdentifier = templateIdentifier.replaceFirst("/([^/]*)$", "/_$1");
			else templateIdentifier = event.getProcessorName() + "/_" + templateIdentifier;
			if (stuff != null)
				partialLocalBinding.putAll(stuff);
			return renderer.render(templateIdentifier, partialLocalBinding).toString();
		}

	}

	public class Render {
		protected String templateIdentifier;

		public Render(String templateIdentifier) {
			this.templateIdentifier = templateIdentifier;
		}

		public Partial partial(String templateIdentifier) {
			return new Partial(templateIdentifier);
		}

		@Override
		@SuppressWarnings("unchecked")
		public String toString() {
			if (!templateIdentifier.contains("/")) templateIdentifier = event.getProcessorName() + "/" + templateIdentifier;
			return renderer.render(templateIdentifier, binding).toString();
		}
	}
}
