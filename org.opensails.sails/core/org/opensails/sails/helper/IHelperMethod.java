package org.opensails.sails.helper;

/**
 * If your helper can be called with arguments, as in something like
 * $render('something'), then you must implement this interface.
 * {@link #invoke(Object[])} will be called after the helper has been
 * instantiated.
 * 
 * @author aiwilliams
 */
public interface IHelperMethod {
	public Object invoke(Object... args);
}
