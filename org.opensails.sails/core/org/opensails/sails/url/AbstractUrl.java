package org.opensails.sails.url;

import org.opensails.sails.ISailsEvent;
import org.opensails.sails.Sails;

public abstract class AbstractUrl<T extends AbstractUrl> implements IUrl {
	protected ISailsEvent event;
	protected boolean secure;

	public AbstractUrl(ISailsEvent event) {
		this.event = event;
	}

	public AbsoluteUrl absolute() {
		return new AbsoluteUrl(event, renderAbsoluteUrl());
	}

	@Override
	public boolean equals(Object obj) {
		return absolute().equals(((IUrl) obj).absolute());
	}

	@Override
	public int hashCode() {
		return absolute().hashCode();
	}

	public String render() {
		if (secure) {
			String url = renderAbsoluteUrl();
			String secureScheme = event.getConfiguration().getString(Sails.ConfigurationKey.Url.SECURE_SCHEME);
			return url.replaceFirst("^http://", secureScheme + "://");
		}
		return doRender();
	}

	@SuppressWarnings("unchecked")
	public T secure() {
		this.secure = true;
		return (T) this;
	}

	@Override
	public String toString() {
		return render();
	}

	/**
	 * Subclasses override to render url
	 * 
	 * @return the url
	 */
	protected abstract String doRender();

	/**
	 * Subclasses override to render absolute url
	 * 
	 * @return the absolute url
	 */
	protected abstract String renderAbsoluteUrl();
}
