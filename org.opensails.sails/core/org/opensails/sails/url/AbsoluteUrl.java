package org.opensails.sails.url;

import java.net.MalformedURLException;
import java.net.URL;

import org.opensails.sails.SailsException;
import org.opensails.sails.event.ISailsEvent;

public class AbsoluteUrl extends AbstractUrl<AbsoluteUrl> {
	protected String href;
	protected AdaptingQueryParameters queryParams;

	public AbsoluteUrl(ISailsEvent event, String absoluteHref) {
		super(event);

		try {
			URL url = new URL(absoluteHref);
			String query = url.getQuery();
			queryParams = new AdaptingQueryParameters(event, query);
			if (query == null) href = absoluteHref;
			else href = absoluteHref.replace(query, "");
		} catch (MalformedURLException e) {
			throw new SailsException("Could not create absolute link", e);
		}
	}

	public AbsoluteUrl absolute() {
		return this;
	}

	@Override
	public boolean equals(Object obj) {
		return renderThyself().equals(((IUrl) obj).renderThyself());
	}

	public String getQueryParameter(String name) {
		return queryParams.get(name);
	}

	@Override
	public int hashCode() {
		return renderThyself().hashCode();
	}

	public void setQueryParameter(String name, String value) {
		queryParams.set(name, value);
	}

	@Override
	protected String doRender() {
		return renderAbsoluteUrl();
	}

	@Override
	protected String renderAbsoluteUrl() {
		return href + queryParams;
	}
}
