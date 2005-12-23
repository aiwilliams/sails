package org.opensails.sails.html;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.opensails.sails.SailsException;
import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.url.ActionUrl;
import org.opensails.sails.url.IUrl;
import org.opensails.sails.url.UrlType;

/**
 * Generates links to actions on Controllers.
 * 
 * If no text is explicitly provided, the controller and action will be used. If
 * those are not specified, the value 'home' is used. What links are useful
 * without text?
 */
public class ActionLink extends AbstractLink<ActionLink> {
	protected String action;
	protected String controller;

	public ActionLink(ISailsEvent event) {
		super(event, new ActionUrl(event));
	}

	public ActionLink action(String action) {
		getUrl().setAction(action);
		return this;
	}

	public ActionLink action(String action, List<Object> args) {
		getUrl().setAction(action);
		return this;
	}

	public ActionLink controller(String controller) {
		getUrl().setController(controller);
		return this;
	}

	public ActionUrl getUrl() {
		return (ActionUrl) url;
	}

	public ImageLink image(IUrl src) {
		ImageLink imageLink = new ImageLink(event, url, src);
		StringWriter writer = new StringWriter();
		try {
			renderLinkBody(new HtmlGenerator(writer));
		} catch (IOException e) {
			throw new SailsException("Failure converting ActionLink to ImageLink", e);
		}
		imageLink.alt(writer.toString());
		imageLink.linkAttributes(attributes);
		return imageLink;
	}

	/**
	 * Provides for the expected behaviour of
	 * $link.action('something').image('some.jpg').
	 * 
	 * @param src
	 * @return an ImageLink bound to the href of this
	 */
	public ImageLink image(String src) {
		return image(event.resolve(UrlType.IMAGE, src));
	}

	public ActionLink parameters(List<? extends Object> parameters) {
		getUrl().setParameters(parameters.toArray(new Object[parameters.size()]));
		return this;
	}

	@Override
	protected void onToStringError(IOException e) {
		throw new SailsException("An exception occurred writing a link to action <" + action + "> on controller <" + controller + ">", e);
	}

	@Override
	protected void render(Writer writer) throws IOException {
		if (StringUtils.isBlank(controller) && !StringUtils.isBlank(action)) throw new SailsException("You cannot create a link with an action <" + action
				+ "> but no controller");
		super.render(writer);
	}

	@Override
	protected void renderLinkBody(HtmlGenerator generator) throws IOException {
		String body = text;
		if (StringUtils.isBlank(body)) body = getUrl().getControllerAction();
		if (body == null) body = "home";
		generator.write(body);
	}
}
