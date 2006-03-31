package org.opensails.sails.adapter.oem;

import java.util.Collection;

import org.opensails.sails.ApplicationScope;
import org.opensails.sails.Scope;
import org.opensails.sails.adapter.AdaptationException;
import org.opensails.sails.adapter.AdaptationTarget;
import org.opensails.sails.adapter.ContainerAdapterResolver;
import org.opensails.sails.adapter.FieldType;
import org.opensails.sails.adapter.IAdapter;
import org.opensails.sails.util.BleedingEdgeException;

@Scope(ApplicationScope.REQUEST)
public class CollectionAdapter implements IAdapter<Collection<? extends Object>, String[]> {
	protected ContainerAdapterResolver adapterResolver;

	public CollectionAdapter(ContainerAdapterResolver adapterResolver) {
		this.adapterResolver = adapterResolver;
	}

	public Collection<? extends Object> forModel(AdaptationTarget adaptationTarget, String[] fromWeb) throws AdaptationException {
		throw new BleedingEdgeException("implement");
	}

	public String[] forWeb(AdaptationTarget adaptationTarget, Collection<? extends Object> fromModel) throws AdaptationException {
		throw new BleedingEdgeException("implement");
	}

	public FieldType getFieldType() {
		return FieldType.STRING_ARRAY;
	}

}
