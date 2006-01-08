package org.opensails.hibernate;

import org.hibernate.Session;
import org.opensails.rigging.ScopedContainer;
import org.opensails.sails.IConfigurableSailsApplication;
import org.opensails.sails.RequestContainer;
import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.oem.BaseConfigurator;
import org.opensails.sails.persist.IObjectPersister;

/**
 * An ISailsApplicationConfigurator the makes using Hibernate as the
 * IObjectPersister easy.
 * <p>
 * Just subclass this as you would BaseConfigurator. Note that the
 * IObjectPersister is registered at both Application scope and Request scope.
 * This allows application scoped objects that need an IObjectPersister to get
 * one. Please be aware that this instance will be kept active through the life
 * of the application, and you are responsible for calling commit.
 * 
 * @author Adam 'Programmer' Williams
 */
public abstract class HibernateApplicationConfigurator extends BaseConfigurator {
	public HibernateApplicationConfigurator() {
		super();
	}

	public void configure(ISailsEvent event, RequestContainer eventContainer) {
		super.configure(event, eventContainer);
		eventContainer.register(IObjectPersister.class, HibernateObjectPersister.class);
		eventContainer.register(Session.class, new SessionResolver(eventContainer));
	}

	protected abstract Class<? extends IHibernateDatabaseConfiguration> getDefaultDatabaseConfiguration();

	protected abstract Class<? extends IHibernateMappingConfiguration> getDefaultMappingConfiguration();

	protected void installObjectPersister(IConfigurableSailsApplication application, ScopedContainer container) {
		container.register(IHibernateDatabaseConfiguration.class, getDefaultDatabaseConfiguration());
		container.register(IHibernateMappingConfiguration.class, getDefaultMappingConfiguration());
		container.register(IObjectPersister.class, HibernateObjectPersister.class);
		container.register(HibernateSessionFactory.class);
	}
}