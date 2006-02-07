package org.opensails.sails.template.viento;

import java.io.InputStream;

import org.opensails.sails.IResourceResolver;
import org.opensails.sails.template.ITemplateRenderer;
import org.opensails.sails.template.TemplateNotFoundException;
import org.opensails.sails.url.IUrl;
import org.opensails.viento.VientoTemplate;

public class VientoTemplateRenderer implements ITemplateRenderer<VientoBinding> {
	public static final String TEMPLATE_IDENTIFIER_EXTENSION = ".vto";
	protected final IResourceResolver templateResolver;

	public VientoTemplateRenderer(IResourceResolver templateResolver) {
		this.templateResolver = templateResolver;
	}

	public VientoBinding createBinding(VientoBinding parent) {
		return new VientoBinding(parent);
	}

	public StringBuilder render(IUrl templateUrl, VientoBinding binding) {
		return render(templateUrl, binding, new StringBuilder());
	}

	public StringBuilder render(IUrl templateUrl, VientoBinding binding, StringBuilder target) {
		InputStream stream = templateResolver.resolve(templateUrl);
		VientoTemplate template = new VientoTemplate(stream);
		template.render(target, binding);
		return target;
	}

	public StringBuilder render(String templateIdentifier, VientoBinding binding) {
		return render(templateIdentifier, binding, new StringBuilder());
	}

	public StringBuilder render(String templateIdentifier, VientoBinding binding, StringBuilder target) {
		InputStream stream = templateResolver.resolve(templateIdentifier + TEMPLATE_IDENTIFIER_EXTENSION);
		if (stream == null) throw new TemplateNotFoundException(templateIdentifier, binding);
		VientoTemplate template = new VientoTemplate(stream);
		template.render(target, binding);
		return target;
	}

	public StringBuilder renderString(String templateContent, VientoBinding binding) {
		StringBuilder target = new StringBuilder();
		VientoTemplate template = new VientoTemplate(templateContent);
		template.render(target, binding);
		return target;
	}

	public boolean templateExists(String templateIdentifier) {
		throw new UnsupportedOperationException("implement this if you need it");
	}
}
