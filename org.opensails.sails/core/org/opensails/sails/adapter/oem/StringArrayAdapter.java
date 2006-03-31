package org.opensails.sails.adapter.oem;

import org.opensails.sails.adapter.AdaptationException;
import org.opensails.sails.adapter.AdaptationTarget;
import org.opensails.sails.adapter.FieldType;
import org.opensails.sails.adapter.IAdapter;
import org.opensails.sails.util.BleedingEdgeException;

public class StringArrayAdapter implements IAdapter<String[], String[]> {
	public String[] forModel(AdaptationTarget adaptationTarget, String[] fromWeb) throws AdaptationException {
		throw new BleedingEdgeException("implement");
	}

	public String[] forModel(Class<? extends String[]> modelType, String[] fromWeb) throws AdaptationException {
		return fromWeb;
	}

	public String[] forWeb(AdaptationTarget adaptationTarget, String[] fromModel) throws AdaptationException {
		throw new BleedingEdgeException("implement");
	}

	public String[] forWeb(Class<? extends String[]> modelType, String[] fromModel) throws AdaptationException {
		return fromModel;
	}

	public FieldType getFieldType() {
		return FieldType.STRING_ARRAY;
	}
}
