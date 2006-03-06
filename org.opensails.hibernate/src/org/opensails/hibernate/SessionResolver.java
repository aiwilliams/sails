package org.opensails.hibernate;

import org.hibernate.Session;
import org.opensails.rigging.ComponentResolver;
import org.opensails.rigging.SimpleContainer;
import org.opensails.sails.IEventContextContainer;

/**
 * A Rigging ComponentResolver for Hibernate Sessions.
 * <p>
 * If you are using the HibernateObjectPersister and want access to the
 * Hibernate Session in any of your Rigging managed objects, you must register
 * this resolver.
 * 
 * @see org.opensails.hibernate.HibernateObjectPersister
 * @author aiwilliams
 */
public class SessionResolver implements ComponentResolver {
	protected final IEventContextContainer eventContainer;
	protected Session session;

	public SessionResolver(IEventContextContainer eventContainer) {
		this.eventContainer = eventContainer;
	}

	public ComponentResolver cloneFor(SimpleContainer container) {
		throw new UnsupportedOperationException();
	}

	public Object instance() {
		if (session == null) session = eventContainer.instance(HibernateSessionFactory.class).openSession();
		return session;
	}

	public boolean isInstantiated() {
		return session != null;
	}

	public Class<?> type() {
		return Session.class;
	}
}