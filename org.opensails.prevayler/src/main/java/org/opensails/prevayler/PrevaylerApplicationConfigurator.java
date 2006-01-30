package org.opensails.prevayler;

import org.opensails.rigging.ScopedContainer;
import org.opensails.sails.IConfigurableSailsApplication;
import org.opensails.sails.RequestContainer;
import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.oem.BaseConfigurator;
import org.opensails.sails.persist.IDataMapper;
import org.prevayler.Prevayler;

public abstract class PrevaylerApplicationConfigurator extends BaseConfigurator {

	// TODO why do I need to implement this method in addition to installObjectPersister()? I don't understand it's usefullness. This should probably be tested.
	@Override
	public void configure(ISailsEvent event, RequestContainer eventContainer) {
		super.configure(event, eventContainer);
		eventContainer.register(IDataMapper.class, PrevaylerPersister.class);
	}

	@Override
	protected void installObjectPersister(IConfigurableSailsApplication application, ScopedContainer container) {
		super.installObjectPersister(application, container);
		container.register(Prevayler.class, getPrevayler());
		container.register(IDataMapper.class, PrevaylerPersister.class);
	}

	/**
	 * Example implementation:
	 * 
	 * IdentifiableObjectPrevalentSystem prevalantSystem = new IdentifiableObjectPrevalentSystem(); 
	 * Prevayler prevayler = PrevaylerFactory.createTransientPrevayler(prevalantSystem);
	 * return prevayler;
	 * 
	 * @return a Prevayler instance configured as you desire.  See PrevaylerFactory.create methods for more info.
	 */
	protected abstract Class<Prevayler> getPrevayler();
}
