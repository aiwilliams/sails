package org.opensails.hibernate;

import org.hibernate.Session;
import org.opensails.rigging.ScopedContainer;
import org.opensails.sails.IConfigurableSailsApplication;
import org.opensails.sails.RequestContainer;
import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.oem.BaseConfigurator;
import org.opensails.sails.persist.IDataMapper;

/**
 * An ISailsApplicationConfigurator the makes using Hibernate as the IDataMapper
 * easy.
 * <p>
 * Just subclass this as you would BaseConfigurator. Note that the IDataMapper
 * is registered at both Application scope and Request scope. This allows
 * application scoped objects that need an IDataMapper to get one. Please be
 * aware that this instance will be kept active through the life of the
 * application, and you are responsible for calling commit.
 * 
 * @author aiwilliams
 */
public abstract class HibernateApplicationConfigurator extends BaseConfigurator {
	public HibernateApplicationConfigurator() {
		super();
	}

	@Override
	public void configure(ISailsEvent event, RequestContainer eventContainer) {
		super.configure(event, eventContainer);
		eventContainer.register(IDataMapper.class, HibernateDataMapper.class);
		eventContainer.register(Session.class, new SessionResolver(eventContainer));
	}

	protected abstract Class<? extends IHibernateDatabaseConfiguration> getDefaultDatabaseConfiguration();

	protected abstract Class<? extends IHibernateMappingConfiguration> getDefaultMappingConfiguration();

	@Override
	protected void installObjectPersister(IConfigurableSailsApplication application, ScopedContainer container) {
		container.register(IHibernateDatabaseConfiguration.class, getDefaultDatabaseConfiguration());
		container.register(IHibernateMappingConfiguration.class, getDefaultMappingConfiguration());
		container.register(IDataMapper.class, HibernateDataMapper.class);
		container.register(HibernateSessionFactory.class);
	}
}
