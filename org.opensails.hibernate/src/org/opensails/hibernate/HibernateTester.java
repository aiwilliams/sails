package org.opensails.hibernate;

import java.util.*;

import org.apache.commons.lang.*;
import org.opensails.sails.persist.*;

public class HibernateTester implements IDataMapper {
	/**
	 * @return a HibernateTester with an in-memory HSQLDB configuration
	 */
	public static HibernateTester basicInMemory() {
		return new HibernateTester();
	}

	protected HibernateSessionFactory sessionFactory;

	protected IHibernateDatabaseConfiguration databaseConfiguration;
	protected IHibernateMappingConfiguration mappingConfiguration;
	protected HibernateDataMapper mapper;

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
		return getMapper().all(theClass);
	}

	public void beginTransaction() throws PersistException {
		getMapper().beginTransaction();
	}

	public void closeSession() throws PersistException {
		getMapper().closeSession();
	}

	public void commit() throws PersistException {
		getMapper().commit();
	}

	public void destroy(IIdentifiable object) throws PersistException {
		getMapper().destroy(object);
	}

	public <T extends IIdentifiable> T find(Class<T> objectType, Long id) throws PersistException {
		return getMapper().find(objectType, id);
	}

	public <T extends IIdentifiable> T find(Class<T> theClass, String attributeName, Object value) throws PersistException {
		return getMapper().find(theClass, attributeName, value);
	}

	public <T extends IIdentifiable> T find(Class<T> theClass, String[] attributeNames, Object[] values) throws PersistException {
		return getMapper().find(theClass, attributeNames, values);
	}

	public <T extends IIdentifiable> Collection<T> findAll(Class<T> theClass, Long... ids) throws PersistException {
		return getMapper().findAll(theClass, ids);
	}

	public <T extends IIdentifiable> Collection<T> findAll(Class<T> theClass, String attributeName, Object value) throws PersistException {
		return getMapper().findAll(theClass, attributeName, value);
	}

	public <T extends IIdentifiable> Collection<T> findAll(Class<T> theClass, String[] attributeNames, Object[] values) throws PersistException {
		return getMapper().findAll(theClass, attributeNames, values);
	}

	public HibernateSessionFactory getSessionFactory() {
		if (sessionFactory == null)
			sessionFactory = new HibernateSessionFactory(databaseConfiguration, mappingConfiguration);
		return sessionFactory;
	}

	@SuppressWarnings("unchecked")
	public <T extends IIdentifiable> T reload(T instance) {
		return (T) getMapper().reload(instance);
	}

	public void save(IIdentifiable object) {
		getMapper().save(object);
	}

	public void setupDatabase(IHibernateDatabaseConfiguration databaseConfiguration) {
		this.databaseConfiguration = databaseConfiguration;
	}

	public void setupMappings(IHibernateMappingConfiguration mappingConfiguration) {
		this.mappingConfiguration = mappingConfiguration;
	}

	protected HibernateDataMapper getMapper() {
		if (mapper == null)
			mapper = new HibernateDataMapper(getSessionFactory());
		return mapper;
	}
}
