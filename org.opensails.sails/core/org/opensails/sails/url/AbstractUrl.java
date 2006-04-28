package org.opensails.sails.url;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.opensails.sails.Sails;
import org.opensails.sails.SailsException;
import org.opensails.sails.event.ISailsEvent;

public abstract class AbstractUrl<T extends AbstractUrl> implements IUrl {
	protected ISailsEvent event;
	protected boolean secure;

	public AbstractUrl(ISailsEvent event) {
		this.event = event;
	}

	public AbsoluteUrl absolute() {
		return new AbsoluteUrl(event, renderAbsoluteUrl());
	}

	public String decode(String value) {
		try {
			return URLDecoder.decode(value, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new SailsException("Could not decode value", e);
		}
	}

	public String encode(String value) {
		try {
			return URLEncoder.encode(value, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new SailsException("Could not encode value", e);
		}
	}

	@Override
	public boolean equals(Object obj) {
		return absolute().equals(((IUrl) obj).absolute());
	}

	@Override
	public int hashCode() {
		return absolute().hashCode();
	}

	public String renderThyself() {
		if (secure) {
			String url = renderAbsoluteUrl();
			String secureScheme = event.getConfiguration().getString(Sails.ConfigurationKey.Url.SECURE_SCHEME);
			return url.replaceFirst("^http://", secureScheme + "://");
		}
		return doRender();
	}

	public AbstractUrl<T> secure() {
		this.secure = true;
		return this;
	}

	@Override
	public String toString() {
		return renderThyself();
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
