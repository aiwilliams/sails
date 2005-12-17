package org.opensails.sails.html;

/**
 * An IHtmlElement that can have inline content.
 * 
 * @author Adam 'Programmer' Williams
 */
public interface IInlineContentElement<T extends IInlineContentElement> extends IHtmlElement<T> {
	T inline(IInlineContent content);
}
