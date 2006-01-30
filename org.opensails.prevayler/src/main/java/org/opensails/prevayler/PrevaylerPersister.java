package org.opensails.prevayler;

import java.io.IOException;
import java.util.Collection;

import org.opensails.sails.persist.IDataMapper;
import org.opensails.sails.persist.IIdentifiable;
import org.opensails.sails.persist.PersistException;
import org.prevayler.Prevayler;

public class PrevaylerPersister implements IDataMapper {
	private final Prevayler prevayler;

	public PrevaylerPersister(Prevayler prevayler) {
		this.prevayler = prevayler;
	}

	public void save(IIdentifiable object) throws PersistException {
		prevayler.execute(new SaveCommand(object));
	}

	@SuppressWarnings("unchecked")
	public <T extends IIdentifiable> T find(Class<T> objectType, Long id) throws PersistException {
		try {
			return (T) prevayler.execute(new FindCommand(objectType, id));
		} catch (Exception e) {
			throw new PersistException(e);
		}
	}

	public void destroy(IIdentifiable object) throws PersistException {
		prevayler.execute(new DestroyCommand(object));
	}

	@SuppressWarnings("unchecked")
	public <T extends IIdentifiable> Collection<T> all(Class<T> objectType) throws PersistException {
		try {
			return (Collection<T>) prevayler.execute(new FindCommand(objectType));
		} catch (Exception e) {
			throw new PersistException(e);
		}
	}

	public void beginTransaction() throws PersistException {
		throw new RuntimeException("Not Implemented");
	}

	public void closeSession() throws PersistException {
		try {
			prevayler.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void commit() throws PersistException {
		throw new RuntimeException("Not Implemented");
	}

	public <T extends IIdentifiable> T find(Class<T> theClass, String attributeName, Object value) throws PersistException {
		throw new RuntimeException("Not Implemented");
	}

	public <T extends IIdentifiable> T find(Class<T> theClass, String[] attributeNames, Object[] values) throws PersistException {
		throw new RuntimeException("Not Implemented");
	}

	public <T extends IIdentifiable> Collection<T> findAll(Class<T> theClass, Long... ids) throws PersistException {
		throw new RuntimeException("Not Implemented");
	}

	public <T extends IIdentifiable> Collection<T> findAll(Class<T> theClass, String attributeName, Object value) throws PersistException {
		throw new RuntimeException("Not Implemented");
	}

	public <T extends IIdentifiable> Collection<T> findAll(Class<T> theClass, String[] attributeNames, Object[] values) throws PersistException {
		throw new RuntimeException("Not Implemented");
	}

	public <T extends IIdentifiable> T reload(T instance) {
		throw new RuntimeException("Not Implemented");
	}

}
