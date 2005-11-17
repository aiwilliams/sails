package org.opensails.sails.url;

import org.opensails.sails.ISailsEvent;

public class AbsoluteUrl extends AbstractUrl<AbsoluteUrl> {
	protected String absoluteHref;

	public AbsoluteUrl(ISailsEvent event, String absoluteHref) {
		super(event);
		this.absoluteHref = absoluteHref;
	}

	public AbsoluteUrl absolute() {
		return this;
	}

	@Override
	public boolean equals(Object obj) {
		return render().equals(((IUrl) obj).render());
	}

	@Override
	public int hashCode() {
		return render().hashCode();
	}

	@Override
	protected String doRender() {
		return renderAbsoluteUrl();
	}

	@Override
	protected String renderAbsoluteUrl() {
		return absoluteHref;
	}
}
