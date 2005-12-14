package org.opensails.sails.adapter;

/**
 * A convenience for tests that need an IAdapter, but don't want to override all
 * the methods.
 */
public class AdapterAdapter extends AbstractAdapter {
	public Object forModel(Class modelType, Object fromWeb) throws AdaptationException {
		return null;
	}

	public Object forWeb(Class modelType, Object fromModel) throws AdaptationException {
		return null;
	}
}
