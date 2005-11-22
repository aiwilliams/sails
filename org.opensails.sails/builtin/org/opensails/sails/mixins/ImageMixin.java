package org.opensails.sails.mixins;

import org.opensails.sails.ISailsEvent;
import org.opensails.sails.html.Image;
import org.opensails.sails.template.IMixinMethod;

public class ImageMixin implements IMixinMethod<Image> {
	private final ISailsEvent event;

	public ImageMixin(ISailsEvent event) {
		this.event = event;
	}

	public Image invoke(Object... args) {
		return new Image(event, (String) args[0]);
	}
}
