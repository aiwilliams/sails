package org.opensails.sails.template.viento;

import java.io.InputStream;

import org.opensails.sails.IResourceResolver;
import org.opensails.sails.template.ITemplateRenderer;
import org.opensails.sails.template.TemplateNotFoundException;
import org.opensails.sails.url.IUrl;
import org.opensails.viento.Binding;
import org.opensails.viento.VientoTemplate;

public class VientoTemplateRenderer implements ITemplateRenderer<Binding> {
	public static final String TEMPLATE_IDENTIFIER_EXTENSION = ".vto";
	protected final IResourceResolver templateResolver;

	public VientoTemplateRenderer(IResourceResolver templateResolver) {
		this.templateResolver = templateResolver;
	}

	public Binding createBinding(Binding parent) {
		return new Binding(parent);
	}

	public StringBuilder render(IUrl templateUrl, Binding binding) {
		return render(templateUrl, binding, new StringBuilder());
	}

	public StringBuilder render(IUrl templateUrl, Binding binding, StringBuilder target) {
		InputStream stream = templateResolver.resolve(templateUrl);
		VientoTemplate template = new VientoTemplate(stream);
		template.render(target, binding);
		return target;
	}

	public StringBuilder render(String templateIdentifier, Binding binding) {
		return render(templateIdentifier, binding, new StringBuilder());
	}

	public StringBuilder render(String templateIdentifier, Binding binding, StringBuilder target) {
		InputStream stream = templateResolver.resolve(templateIdentifier + TEMPLATE_IDENTIFIER_EXTENSION);
		if (stream == null) throw new TemplateNotFoundException(templateIdentifier, binding);
		VientoTemplate template = new VientoTemplate(stream);
		template.render(target, binding);
		return target;
	}

	public StringBuilder renderString(String templateContent, Binding binding) {
		StringBuilder target = new StringBuilder();
		VientoTemplate template = new VientoTemplate(templateContent);
		template.render(target, binding);
		return target;
	}

	public boolean templateExists(String templateIdentifier) {
		throw new UnsupportedOperationException("implement this if you need it");
	}
}
