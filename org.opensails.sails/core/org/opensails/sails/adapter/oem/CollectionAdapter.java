package org.opensails.sails.adapter.oem;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.opensails.sails.ApplicationScope;
import org.opensails.sails.SailsException;
import org.opensails.sails.Scope;
import org.opensails.sails.adapter.AdaptationException;
import org.opensails.sails.adapter.AdaptationTarget;
import org.opensails.sails.adapter.ContainerAdapterResolver;
import org.opensails.sails.adapter.FieldType;
import org.opensails.sails.adapter.IAdapter;

@Scope(ApplicationScope.REQUEST)
public class CollectionAdapter implements IAdapter<Collection<? extends Object>, String[]> {
	protected ContainerAdapterResolver adapterResolver;

	public CollectionAdapter(ContainerAdapterResolver adapterResolver) {
		this.adapterResolver = adapterResolver;
	}

	@SuppressWarnings("unchecked")
	public Collection<? extends Object> forModel(AdaptationTarget adaptationTarget, String[] fromWeb) throws AdaptationException {
		Class collectionType = getCollectionType(adaptationTarget);
		AdaptationTarget<Object> collectionTarget = new AdaptationTarget<Object>(collectionType);
		List<Object> forModel = new ArrayList<Object>(fromWeb.length);
		for (String value : fromWeb) {
			IAdapter adapter = adapterResolver.resolve(collectionType);
			Object adapted = adapter.forModel(collectionTarget, value);
			forModel.add(adapted);
		}
		return forModel;
	}

	@SuppressWarnings("unchecked")
	public String[] forWeb(AdaptationTarget adaptationTarget, Collection<? extends Object> fromModel) throws AdaptationException {
		Class collectionType = getCollectionType(adaptationTarget);
		AdaptationTarget<Object> collectionTarget = new AdaptationTarget<Object>(collectionType);
		List<String> forWeb = new ArrayList<String>(fromModel.size());
		for (Object value : fromModel) {
			IAdapter adapter = adapterResolver.resolve(collectionType);
			Object adapted = adapter.forWeb(collectionTarget, value);
			forWeb.add(String.valueOf(adapted));
		}
		return forWeb.toArray(new String[forWeb.size()]);
	}

	public FieldType getFieldType() {
		return FieldType.STRING_ARRAY;
	}

	protected Class getCollectionType(AdaptationTarget adaptationTarget) {
		Class collectionType = null;
		Type targetGenericType = adaptationTarget.getTargetGenericType();
		if (targetGenericType != null && targetGenericType instanceof ParameterizedType) {
			ParameterizedType pt = (ParameterizedType) targetGenericType;
			Type[] genTypes = pt.getActualTypeArguments();
			if (genTypes.length == 1 && genTypes[0] instanceof Class) collectionType = (Class) genTypes[0];
		}
		if (collectionType == null) throw new SailsException("Unable to determine type of Collection");
		return collectionType;
	}

}
