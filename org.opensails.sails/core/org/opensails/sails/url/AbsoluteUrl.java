package org.opensails.sails.url;

import org.opensails.sails.ISailsEvent;
import org.opensails.sails.Sails;

public abstract class AbsoluteUrl<T extends AbsoluteUrl> implements IUrl {
	protected ISailsEvent event;
	protected boolean secure;

	public AbsoluteUrl(ISailsEvent event) {
		this.event = event;
	}

	/**
	 * Aliases {@link #secure()} to make Velocity templates idiomatic, or is it
	 * idiotic?
	 */
	public T getSecure() {
		return secure();
	}

	public String render() {
		String url = renderAbsoluteUrl();
		if (secure) {
			String secureScheme = event.getConfiguration().getString(Sails.ConfigurationKey.Url.SECURE_SCHEME);
			url = event.getResponse().encodeURL(url.replaceFirst("^http://", secureScheme + "://"));
		}
		return url;
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
	 * @return the absolute url
	 */
	protected abstract String renderAbsoluteUrl();
}
