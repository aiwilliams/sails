package org.opensails.sails.url;

import org.opensails.sails.ISailsEvent;

public class ExternalUrl extends AbsoluteUrl<ExternalUrl> {
    protected String absoluteHref;

    public ExternalUrl(ISailsEvent event, String absoluteHref) {
        super(event);
        this.absoluteHref = absoluteHref;
    }

    @Override
    protected String renderAbsoluteUrl() {
        return absoluteHref;
    }
}
