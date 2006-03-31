package org.opensails.sails.model.oem;

import org.opensails.sails.model.IPropertyAccessor;
import org.opensails.sails.model.IPropertyFactory;
import org.opensails.sails.model.IPropertyPath;
import org.opensails.sails.model.MalformedPropertyException;

/**
 * Implements the Sails default of using the {@link DotPropertyPath}.
 * 
 * @author aiwilliams
 */
public class DefaultPropertyFactory implements IPropertyFactory {
	/**
	 * Answers only {@link DotPropertyPath} instances.
	 * <p>
	 * This checks the type for the sole purpose of helping new developers who
	 * attempt to use their own {@link IPropertyPath} types without providing a
	 * corresponding {@link IPropertyAccessor}. If you don't like
	 * DotPropertyPath, please create an implementation of IPropertyFactory and
	 * register is in your application configurator.
	 * 
	 * @throws MalformedPropertyException if the path is not an instance of
	 *         DotPropertyPath
	 */
	public IPropertyAccessor createAccessor(IPropertyPath path) {
		if (!(path instanceof DotPropertyPath)) throw new MalformedPropertyException("Sails supports only DotPropertyPaths by default. Learn more by looking at IPropertyFactory.");
		return new DotPropertyAccessor(path);
	}

	public IPropertyAccessor createAccessor(String path) {
		return null;
	}

	public IPropertyPath createPath(String path) {
		return new DotPropertyPath(path);
	}
}
