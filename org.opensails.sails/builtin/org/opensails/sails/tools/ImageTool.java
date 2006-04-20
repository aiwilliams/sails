package org.opensails.sails.tools;

import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.html.Image;
import org.opensails.sails.template.IMixinMethod;
import org.opensails.sails.url.IUrl;
import org.opensails.sails.url.UrlType;

public class ImageTool implements IMixinMethod<Image> {
	private final ISailsEvent event;

	public ImageTool(ISailsEvent event) {
		this.event = event;
	}

	public Image invoke(Object... args) {
		if (args == null || args.length != 1) throw new IllegalArgumentException("Image requires a source url of String or IUrl");
		if (args[0] instanceof String) return new Image(event.resolve(UrlType.IMAGE, (String) args[0]));
		else if (args[0] instanceof IUrl) return new Image((IUrl) args[0]);
		else throw new IllegalArgumentException(String.format("Illegal argument %s for image(IUrl)", args[0]));
	}
}
