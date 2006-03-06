package org.opensails.sails.adapter.oem;

import org.opensails.sails.adapter.AdaptationException;
import org.opensails.sails.adapter.FieldType;
import org.opensails.sails.adapter.IAdapter;

public class StringArrayAdapter implements IAdapter<String[], String[]> {
	public FieldType getFieldType() {
		return FieldType.STRING_ARRAY;
	}

	public String[] forModel(Class<? extends String[]> modelType, String[] fromWeb) throws AdaptationException {
		return fromWeb;
	}

	public String[] forWeb(Class<? extends String[]> modelType, String[] fromModel) throws AdaptationException {
		return fromModel;
	}
}
