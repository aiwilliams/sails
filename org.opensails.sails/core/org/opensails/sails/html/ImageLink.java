package org.opensails.sails.html;

import java.io.IOException;

import org.opensails.sails.ISailsEvent;
import org.opensails.sails.url.IUrl;

public class ImageLink extends AbstractLink<ImageLink> implements ILink<ImageLink>, IImage<ImageLink> {
    protected Image image;

    public ImageLink(ISailsEvent event, IUrl url, String src) {
        super(event, url);
        this.image = new Image(event, src);
    }

    public ImageLink alt(String alt) {
        image.alt(alt);
        return this;
    }

    public ImageLink src(String src) {
        image.src(src);
        return this;
    }

    @Override
    protected void renderLinkBody(HtmlGenerator generator) throws IOException {
        image.toString(generator);
    }
}
