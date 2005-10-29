package org.opensails.sails.adapter.oem;

import org.opensails.sails.ApplicationScope;
import org.opensails.sails.Scope;
import org.opensails.sails.adapter.AdaptationException;
import org.opensails.sails.adapter.IAdapter;
import org.opensails.sails.persist.IIdentifiable;
import org.opensails.sails.persist.IObjectPersister;

@Scope(ApplicationScope.REQUEST)
public class IdentifiableAdapter implements IAdapter {
	protected final IObjectPersister persister;

	public IdentifiableAdapter(IObjectPersister persister) {
		this.persister = persister;
	}

	@SuppressWarnings("unchecked")
	public Object forModel(Class modelType, Object fromWeb) throws AdaptationException {
		return persister.find(modelType, Long.valueOf((String) fromWeb));
	}

	public Object forWeb(Class modelType, Object fromModel) throws AdaptationException {
		IIdentifiable identifiable = (IIdentifiable) fromModel;
		return identifiable == null ? "null" : String.valueOf(identifiable.getId());
	}

	public Class[] getSupportedTypes() {
		return new Class[] { IIdentifiable.class };
	}

}
