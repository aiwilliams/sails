package org.opensails.sails.tester.persist;

import org.opensails.sails.persist.IIdentifiable;
import org.opensails.sails.persist.IDataMapper;
import org.opensails.sails.persist.PersistException;

public interface IShamDataMapper extends IDataMapper {
	/**
	 * Saves the object, but does not mark it as saved with respect to the
	 * question {@link #wasSaved(IIdentifiable)}. Great for setting up!
	 * 
	 * @param assetType
	 * @param object
	 */
	void provides(Class<? extends IIdentifiable> assetType, IIdentifiable object);

	void provides(IIdentifiable... objects);

	void reset();

	void setExceptionOnSave(PersistException exception);

	boolean wasDestroyed(IIdentifiable object);

	boolean wasSaved(IIdentifiable object);
}
