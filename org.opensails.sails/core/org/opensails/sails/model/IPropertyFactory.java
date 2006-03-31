package org.opensails.sails.model;

import org.opensails.sails.model.oem.DefaultPropertyFactory;

/**
 * Used to convert a String property path to an {@link IPropertyPath} instance
 * and to obtain an {@link IPropertyAccessor} for an implementation of
 * IPropertyPath.
 * <p>
 * This exists to allow folks to change the way that they declare property
 * paths. Right now, Sails uses DotPropertyPaths by default. Ruby on Rails uses
 * a style that fits it's map abilities much better. I work a day on a project
 * where they wanted something completely different. This would have allowed
 * that.
 * 
 * @see DefaultPropertyFactory
 * 
 * @author aiwilliams
 */
public interface IPropertyFactory {

	/**
	 * @param path
	 * @return an accessor for the path
	 * @see #createAccessor(String)
	 */
	IPropertyAccessor createAccessor(IPropertyPath path);

	/**
	 * @param path
	 * @return an accessor for the path
	 * @see #createAccessor(IPropertyPath)
	 */
	IPropertyAccessor createAccessor(String path);

	/**
	 * @param path
	 * @return an IPropertyPath for path
	 */
	IPropertyPath createPath(String path);
}
