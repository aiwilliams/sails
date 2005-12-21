package org.opensails.sails.adapter.oem;

import org.opensails.sails.ApplicationScope;
import org.opensails.sails.Scope;
import org.opensails.sails.adapter.AbstractAdapter;
import org.opensails.sails.adapter.AdaptationException;
import org.opensails.sails.persist.IIdentifiable;
import org.opensails.sails.persist.IObjectPersister;

/**
 * The IAdapter for anything that implements
 * {@link org.opensails.sails.persist.IIdentifiable}. If you are using
 * {@link org.opensails.sails.persist.IObjectPersister}, you get forModel and
 * forWeb forFree.
 * 
 * These adapters are created within the scope of a request so that they work
 * with the IObjectPersister from that scope. This has to do with the way most
 * persistence implementations have some sort of session, transaction, or unit
 * of work model.
 * 
 * @author Adam 'Programmer' Williams
 */
@Scope(ApplicationScope.REQUEST)
public class IdentifiableAdapter extends AbstractAdapter<IIdentifiable, String> {
	protected final IObjectPersister persister;

	public IdentifiableAdapter(IObjectPersister persister) {
		this.persister = persister;
	}

	@SuppressWarnings("unchecked")
	public IIdentifiable forModel(Class<? extends IIdentifiable> modelType, String fromWeb) throws AdaptationException {
		return persister.find(modelType, Long.valueOf(fromWeb));
	}

	public String forWeb(Class<? extends IIdentifiable> modelType, IIdentifiable fromModel) throws AdaptationException {
		return String.valueOf(fromModel.getId());
	}
}
