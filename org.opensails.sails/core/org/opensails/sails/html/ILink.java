package org.opensails.sails.html;

public interface ILink<T extends ILink> extends IHtmlElement<T> {
	T href(String href);
	T text(String text);
	T secure();
}
