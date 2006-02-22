package org.opensails.sails.adapter.oem;

import org.opensails.sails.adapter.AbstractAdapter;
import org.opensails.sails.adapter.AdaptationException;

public class EnumAdapter extends AbstractAdapter<Enum, String> {
	@SuppressWarnings("unchecked")
	public Enum forModel(Class<? extends Enum> modelType, String fromWeb) throws AdaptationException {
		return Enum.valueOf(modelType, fromWeb);
	}

	public String forWeb(Class<? extends Enum> modelType, Enum fromModel) throws AdaptationException {
		return fromModel.name();
	}
}
