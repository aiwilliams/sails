package org.opensails.hibernate;

import java.util.*;

import org.apache.commons.lang.*;
import org.opensails.sails.persist.*;

public class HibernateTester implements IObjectPersister {
	/**
	 * @return a HibernateTester with an in-memory HSQLDB configuration
	 */
	public static HibernateTester basicInMemory() {
		return new HibernateTester();
	}

	protected HibernateSessionFactory sessionFactory;

	private IHibernateDatabaseConfiguration databaseConfiguration;
	private IHibernateMappingConfiguration mappingConfiguration;
	private HibernateObjectPersister persister;

	public HibernateTester() {
		databaseConfiguration = new InMemoryDatabaseConfiguration();
		mappingConfiguration = new IHibernateMappingConfiguration() {
			public Class[] annotatedClasses() {
				return ArrayUtils.EMPTY_CLASS_ARRAY;
			}

			public Class[] mappedClasses() {
				return ArrayUtils.EMPTY_CLASS_ARRAY;
			}
		};
	}

	public <T extends IIdentifiable> Collection<T> all(Class<T> theClass) throws PersistException {
		return getPersister().all(theClass);
	}

	public void beginTransaction() throws PersistException {
		getPersister().beginTransaction();
	}

	public void closeSession() throws PersistException {
		getPersister().closeSession();
	}

	public void commit() throws PersistException {
		getPersister().commit();
	}

	public void destroy(IIdentifiable object) throws PersistException {
		getPersister().destroy(object);
	}

	public <T extends IIdentifiable> T find(Class<T> objectType, Long id) throws PersistException {
		return getPersister().find(objectType, id);
	}

	public <T extends IIdentifiable> T find(Class<T> theClass, String attributeName, Object value) throws PersistException {
		return getPersister().find(theClass, attributeName, value);
	}

	public <T extends IIdentifiable> T find(Class<T> theClass, String[] attributeNames, Object[] values) throws PersistException {
		return getPersister().find(theClass, attributeNames, values);
	}

	public <T extends IIdentifiable> Collection<T> findAll(Class<T> theClass, Long... ids) throws PersistException {
		return getPersister().findAll(theClass, ids);
	}

	public <T extends IIdentifiable> Collection<T> findAll(Class<T> theClass, String attributeName, Object value) throws PersistException {
		return getPersister().findAll(theClass, attributeName, value);
	}

	public <T extends IIdentifiable> Collection<T> findAll(Class<T> theClass, String[] attributeNames, Object[] values) throws PersistException {
		return getPersister().findAll(theClass, attributeNames, values);
	}

	public HibernateSessionFactory getSessionFactory() {
		if (sessionFactory == null)
			sessionFactory = new HibernateSessionFactory(databaseConfiguration, mappingConfiguration);
		return sessionFactory;
	}

	@SuppressWarnings("unchecked")
	public <T extends IIdentifiable> T reload(T instance) {
		return (T) getPersister().reload(instance);
	}

	public void save(IIdentifiable object) {
		getPersister().save(object);
	}

	public void setupDatabase(IHibernateDatabaseConfiguration databaseConfiguration) {
		this.databaseConfiguration = databaseConfiguration;
	}

	public void setupMappings(IHibernateMappingConfiguration mappingConfiguration) {
		this.mappingConfiguration = mappingConfiguration;
	}

	protected HibernateObjectPersister getPersister() {
		if (persister == null)
			persister = new HibernateObjectPersister(getSessionFactory());
		return persister;
	}
}
