package org.opensails.sails.html;

import java.io.IOException;
import java.util.Map;

import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.url.IUrl;

public class ImageLink extends AbstractLink<ImageLink> implements ILink<ImageLink>, IImage<ImageLink> {
	protected Image image;

	public ImageLink(ISailsEvent event, IUrl url, IUrl src) {
		super(event, url);
		this.image = new Image(event, src);
	}

	public ImageLink alt(String alt) {
		image.alt(alt);
		return this;
	}

	/**
	 * Attributes go on the image
	 * 
	 * @see #linkAttributes(Map)
	 */
	@Override
	public ImageLink attributes(Map<String, String> attributes) {
		image.attributes(attributes);
		return this;
	}

	public ImageLink linkAttributes(Map<String, String> attributes) {
		super.attributes(attributes);
		return this;
	}

	public ImageLink src(String src) {
		image.src(src);
		return this;
	}

	@Override
	protected void renderLinkBody(HtmlGenerator generator) throws IOException {
		image.render(generator);
	}
}
