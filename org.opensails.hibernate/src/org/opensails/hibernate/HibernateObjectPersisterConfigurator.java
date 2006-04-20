package org.opensails.hibernate;

import org.hibernate.Session;
import org.opensails.sails.ApplicationContainer;
import org.opensails.sails.IConfigurableSailsApplication;
import org.opensails.sails.RequestContainer;
import org.opensails.sails.configurator.IObjectPersisterConfigurator;
import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.persist.IObjectPersister;
import org.opensails.sails.validation.IValidationEngine;

/**
 * Provides an {@link IObjectPersisterConfigurator} for using Hibernate in your
 * Sails applications.
 * <p>
 * If you utilize this, please note that you should also use the
 * {@link HibernateFormProcessingConfigurator} to install the appropriate
 * {@link IValidationEngine}. This will allow you to use Hibernate Validation
 * Annotations on your model and have Sails collect validation messages
 * appropriately.
 * 
 * @author aiwilliams
 */
public abstract class HibernateObjectPersisterConfigurator implements IObjectPersisterConfigurator {

	public void configure(IConfigurableSailsApplication application, ApplicationContainer container) {
		container.register(IHibernateDatabaseConfiguration.class, getDatabaseConfiguration());
		container.register(IHibernateMappingConfiguration.class, getMappingConfiguration());
		container.register(IObjectPersister.class, HibernateObjectPersister.class);
		container.register(HibernateSessionFactory.class);
	}

	public void configure(ISailsEvent event, RequestContainer container) {
		container.register(IObjectPersister.class, HibernateObjectPersister.class);
		container.registerResolver(Session.class, new SessionResolver(container));
	}

	protected abstract Class<? extends IHibernateDatabaseConfiguration> getDatabaseConfiguration();

	protected abstract Class<? extends IHibernateMappingConfiguration> getMappingConfiguration();
}
