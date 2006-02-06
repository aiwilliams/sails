package org.opensails.hibernate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.ObjectDeletedException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.opensails.rigging.Disposable;
import org.opensails.sails.persist.IIdentifiable;
import org.opensails.sails.persist.IObjectPersister;
import org.opensails.sails.persist.PersistException;

/**
 * An implementation if IObjectPersister for Hibernate.
 * <p>
 * To use this persister, your ISailsApplicationConfigurator can subclass
 * HibernateApplicationConfigurator, or, if you prefer, you may configure the
 * persister youself, using the HibernateApplicationConfigurator as a model.
 * 
 * @author aiwilliams
 */
public class HibernateObjectPersister implements IObjectPersister, Disposable {
	protected Session session;
	protected HibernateSessionFactory sessionFactory;
	protected Transaction transaction;

	public HibernateObjectPersister(HibernateSessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	public <T extends IIdentifiable> Collection<T> all(Class<T> theClass) throws PersistException {
		return getSession().createCriteria(theClass).list();
	}

	public void beginTransaction() throws PersistException {
		transaction = getSession().beginTransaction();
	}

	public void closeSession() throws PersistException {
		if (session != null)
			session.close();
		session = null;
		transaction = null;
	}

	public void commit() throws PersistException {
		if (transaction != null) {
			transaction.commit();
			transaction = null;
		}
	}

	public void destroy(IIdentifiable object) throws PersistException {
		if (notInTransaction()) {
			beginTransaction();
			destroy(object);
			commit();
		} else {
			Session session = getSession();
			try {
				session.delete(object);
			} catch (RuntimeException e) {
				transaction.rollback();
				closeSession();
				throw e;
			}
		}
	}

	public void dispose() {
		closeSession();
	}

	@SuppressWarnings("unchecked")
	public <T extends IIdentifiable> T find(Class<T> objectType, Long id) throws PersistException {
		try {
			return (T) getSession().get(objectType, (Serializable) id);
		} catch (ObjectDeletedException e) {
			return null;
		}
	}

	public <T extends IIdentifiable> T find(Class<T> theClass, String attributeName, Object value) throws PersistException {
		return find(theClass, new String[] { attributeName }, new Object[] { value });
	}

	@SuppressWarnings("unchecked")
	public <T extends IIdentifiable> T find(Class<T> theClass, String[] attributeNames, Object[] values) throws PersistException {
		Criteria criteria = getSession().createCriteria(theClass);
		for (int i = 0; i < attributeNames.length; i++) {
			Object value = values[i];
			if (value == null)
				criteria.add(Expression.isNull(attributeNames[i]));
			else
				criteria.add(Expression.eq(attributeNames[i], value));
		}
		return (T) criteria.uniqueResult();
	}

	public <T extends IIdentifiable> Collection<T> findAll(Class<T> theClass, Long... ids) throws PersistException {
		Collection<T> results = new ArrayList<T>();
		for (Long id : ids)
			results.add(find(theClass, id));
		return results;
	}

	public <T extends IIdentifiable> Collection<T> findAll(Class<T> theClass, String attributeName, Object value) throws PersistException {
		return findAll(theClass, new String[] { attributeName }, new Object[] { value });
	}

	@SuppressWarnings("unchecked")
	public <T extends IIdentifiable> Collection<T> findAll(Class<T> theClass, String[] attributeNames, Object[] values) throws PersistException {
		Criteria criteria = getSession().createCriteria(theClass);
		for (int i = 0; i < attributeNames.length; i++) {
			if (values[i] != null)
				criteria.add(Expression.eq(attributeNames[i], values[i]));
			else
				criteria.add(Expression.isNull(attributeNames[i]));
		}
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public <T extends IIdentifiable> T reload(T instance) {
		getSession().evict(instance);
		return (T) find(instance.getClass(), instance.getId());
	}

	public void save(IIdentifiable object) {
		if (notInTransaction()) {
			beginTransaction();
			save(object);
			commit();
		} else {
			Session session = getSession();
			try {
				session.save(object);
			} catch (RuntimeException e) {
				transaction.rollback();
				closeSession();
				throw e;
			}
		}
	}

	/**
	 * The FlushMode is set to COMMIT. This allows for queries that may return
	 * stale data. If we leave it at auto, we cannot run queries within large
	 * transactions without making many saves along the way.
	 * 
	 * @return a Session with FlushMode set to COMMIT.
	 */
	protected Session getSession() {
		if (session == null || !session.isOpen()) {
			session = sessionFactory.openSession();
			session.setFlushMode(FlushMode.COMMIT);
		}
		return session;
	}

	protected boolean notInTransaction() {
		return transaction == null || !transaction.isActive();
	}
}
