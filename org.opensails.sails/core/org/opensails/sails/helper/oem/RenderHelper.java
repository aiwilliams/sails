package org.opensails.sails.helper.oem;

import org.opensails.sails.ISailsEvent;
import org.opensails.sails.helper.IHelperMethod;
import org.opensails.sails.template.ITemplateBinding;
import org.opensails.sails.template.ITemplateRenderer;

public class RenderHelper implements IHelperMethod {
	protected final ITemplateBinding binding;
	protected final ISailsEvent event;
	protected final ITemplateRenderer renderer;

	public RenderHelper(ISailsEvent event, ITemplateBinding binding, ITemplateRenderer renderer) {
		this.event = event;
		this.binding = binding;
		this.renderer = renderer;
	}

	public Render invoke(Object... args) {
		return new Render(args != null && args.length > 0 ? (String) args[0] : null);
	}

	public class Partial {
		protected String templateIdentifier;

		public Partial(String templateIdentifier) {
			this.templateIdentifier = templateIdentifier;
		}

		@Override
		@SuppressWarnings("unchecked")
		public String toString() {
			ITemplateBinding partialLocalBinding = renderer.createBinding(binding);
			if (templateIdentifier.contains("/")) templateIdentifier = templateIdentifier.replaceFirst("/", "/_");
			else templateIdentifier = event.getControllerName() + "/_" + templateIdentifier;
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
			if (!templateIdentifier.contains("/")) templateIdentifier = event.getControllerName() + "/" + templateIdentifier;
			return renderer.render(templateIdentifier, binding).toString();
		}
	}
}
