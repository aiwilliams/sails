package org.opensails.sails.configurator.oem;

import org.opensails.sails.ApplicationContainer;
import org.opensails.sails.IConfigurableSailsApplication;
import org.opensails.sails.RequestContainer;
import org.opensails.sails.configurator.IFormProcessingConfigurator;
import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.form.HtmlForm;
import org.opensails.sails.form.ValidationContext;
import org.opensails.sails.html.IElementIdGenerator;
import org.opensails.sails.html.UnderscoreIdGenerator;
import org.opensails.sails.model.IPropertyFactory;
import org.opensails.sails.model.ModelContext;
import org.opensails.sails.model.oem.DefaultPropertyFactory;

public class RequiredFormProcessingConfigurator implements IFormProcessingConfigurator {

	protected IFormProcessingConfigurator delegate;

	public RequiredFormProcessingConfigurator(IFormProcessingConfigurator delegate) {
		this.delegate = delegate;
	}

	public void configure(IConfigurableSailsApplication application, ApplicationContainer container) {
		container.register(IPropertyFactory.class, DefaultPropertyFactory.class);
		delegate.configure(application, container);
	}

	public void configure(ISailsEvent event, RequestContainer container) {
		container.register(IElementIdGenerator.class, UnderscoreIdGenerator.class);
		container.register(ModelContext.class);
		container.register(ValidationContext.class);
		container.register(HtmlForm.class);
		delegate.configure(event, container);
	}

}
