package org.opensails.sails.persist;

import java.util.Collection;

public interface IObjectPersister {
	<T extends IIdentifiable> Collection<T> all(Class<T> objectType) throws PersistException;

	/**
	 * Marks the beginning of a transaction. A consecutive call before commit
	 * (or other transaction-terminating call) will cause an exception.
	 */
	void beginTransaction() throws PersistException;

	void closeSession() throws PersistException;

	/**
	 * Commits the open transaction. If there is no open transaction, should do
	 * whatever is appropriate to cause saves to take effect.
	 * 
	 * @throws PersistException when something fails
	 */
	void commit() throws PersistException;

	<T extends IIdentifiable> void destroy(T object) throws PersistException;

	<T extends IIdentifiable> T find(Class<T> objectType, Long id) throws PersistException;

	<T extends IIdentifiable> T find(Class<T> theClass, String attributeName, Object value) throws PersistException;

	<T extends IIdentifiable> T find(Class<T> theClass, String[] attributeNames, Object[] values) throws PersistException;

	<T extends IIdentifiable> Collection<T> findAll(Class<T> theClass, String attributeName, Object value) throws PersistException;

	<T extends IIdentifiable> Collection<T> findAll(Class<T> theClass, String[] attributeNames, Object[] values) throws PersistException;

	<T extends IIdentifiable> void save(T object) throws PersistException;
}
