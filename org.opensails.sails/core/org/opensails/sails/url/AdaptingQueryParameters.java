package org.opensails.sails.url;

import org.opensails.sails.adapter.IWebObjectAdapter;

public class AdaptingQueryParameters extends QueryParameters {
	protected final IWebObjectAdapter adapter;

	public AdaptingQueryParameters(IWebObjectAdapter adapter) {
		this(adapter, null);
	}

	public AdaptingQueryParameters(IWebObjectAdapter adapter, String queryString) {
		super(queryString);
		this.adapter = adapter;
	}

	@Override
	protected Object rawGet(String name) {
		Object value = map.get(name);
		if (value == null) return null;
		return adapter.forWebAsString(value);
	}
}
