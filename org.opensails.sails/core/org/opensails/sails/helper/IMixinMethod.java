package org.opensails.sails.helper;

/**
 * If your mixin can be called with arguments, as in something like
 * $render('something'), then you must implement this interface.
 * {@link #invoke(Object[])} will be called after the mixin has been
 * instantiated.
 * 
 * @author aiwilliams
 */
public interface IMixinMethod {
	public Object invoke(Object... args);
}
