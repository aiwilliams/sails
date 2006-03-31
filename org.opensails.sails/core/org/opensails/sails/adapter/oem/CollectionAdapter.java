package org.opensails.sails.adapter.oem;

import java.util.Collection;

import org.opensails.sails.ApplicationScope;
import org.opensails.sails.Scope;
import org.opensails.sails.adapter.AbstractAdapter;
import org.opensails.sails.adapter.AdaptationException;
import org.opensails.sails.util.BleedingEdgeException;

@Scope(ApplicationScope.REQUEST)
public class CollectionAdapter extends AbstractAdapter<Collection<? extends Object>, Object> {

	public Collection<? extends Object> forModel(Class<? extends Collection<? extends Object>> modelType, Object fromWeb) throws AdaptationException {
		throw new BleedingEdgeException("implement");
	}

	public Object forWeb(Class<? extends Collection<? extends Object>> modelType, Collection<? extends Object> fromModel) throws AdaptationException {
		throw new BleedingEdgeException("implement");
	}

}
