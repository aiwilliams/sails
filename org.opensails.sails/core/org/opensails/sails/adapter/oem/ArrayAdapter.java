package org.opensails.sails.adapter.oem;

import java.lang.reflect.Array;

import org.opensails.sails.ApplicationScope;
import org.opensails.sails.Scope;
import org.opensails.sails.adapter.AdaptationException;
import org.opensails.sails.adapter.ContainerAdapterResolver;
import org.opensails.sails.adapter.FieldType;
import org.opensails.sails.adapter.IAdapter;

@Scope(ApplicationScope.REQUEST)
public class ArrayAdapter implements IAdapter<Object[], String[]> {
	private final ContainerAdapterResolver adapterResolver;

	public ArrayAdapter(ContainerAdapterResolver adapterResolver) {
		this.adapterResolver = adapterResolver;
	}

	@SuppressWarnings("unchecked")
	public Object[] forModel(Class<? extends Object[]> modelType, String[] fromWeb) throws AdaptationException {
		Class<?> arrayType = modelType.getComponentType();
		Object[] forModel = (Object[]) Array.newInstance(arrayType, fromWeb.length);
		for (int i = 0; i < fromWeb.length; i++)
			forModel[i] = adapterResolver.resolve(arrayType).forModel(arrayType, fromWeb[i]);
		return forModel;
	}

	@SuppressWarnings("unchecked")
	public String[] forWeb(Class<? extends Object[]> modelType, Object[] fromModel) throws AdaptationException {
		Class<?> arrayType = modelType.getComponentType();
		String[] forWeb = new String[fromModel.length];
		for (int i = 0; i < fromModel.length; i++)
			forWeb[i] = (String) adapterResolver.resolve(arrayType).forWeb(arrayType, fromModel[i]);
		return forWeb;
	}

	public FieldType getFieldType() {
		return FieldType.STRING_ARRAY;
	}
}
