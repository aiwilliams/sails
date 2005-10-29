package org.opensails.sails.tester.persist;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.opensails.sails.persist.IIdentifiable;
import org.opensails.sails.persist.IObjectPersister;
import org.opensails.sails.persist.PersistException;
import org.opensails.sails.util.DoubleKeyedMap;
import org.opensails.sails.util.FieldAccessor;
import org.opensails.sails.util.IIdGenerator;
import org.opensails.sails.util.TimeUniqueIdGenerator;

public class MemoryObjectPersister implements IObjectPersister {
	protected Collection<IIdentifiable> destroyedInTransaction;
	protected PersistException exceptionOnSave;
	protected FieldAccessor idFieldAccessor;
	protected IIdGenerator<Long> idProvider;
	protected boolean isSessionOpen = true;
	protected DoubleKeyedMap<Class<? extends IIdentifiable>, Long, IIdentifiable> source;
	protected DoubleKeyedMap<Class<? extends IIdentifiable>, Long, IIdentifiable> transaction;
	protected List<IIdentifiable> wasDestroyed;
	protected List<IIdentifiable> wasSaved;

	public MemoryObjectPersister() {
		this(new DoubleKeyedMap<Class<? extends IIdentifiable>, Long, IIdentifiable>());
	}

	public MemoryObjectPersister(DoubleKeyedMap<Class<? extends IIdentifiable>, Long, IIdentifiable> source) {
		this.source = source;
		this.destroyedInTransaction = new HashSet<IIdentifiable>();
		idFieldAccessor = new FieldAccessor("id");
		idProvider = new TimeUniqueIdGenerator();
		wasDestroyed = new ArrayList<IIdentifiable>();
		wasSaved = new ArrayList<IIdentifiable>();
	}

	@SuppressWarnings("unchecked")
	public <T extends IIdentifiable> Collection<T> all(Class<T> theClass) {
		Map<Long, IIdentifiable> all = new HashMap<Long, IIdentifiable>();
		Map<Long, IIdentifiable> map = source.get(theClass);
		if (map != null) all.putAll(map);
		if (transaction != null) {
			map = transaction.get(theClass);
			if (map != null) all.putAll(map);
		}
		Collection<IIdentifiable> values = all.values();
		values.removeAll(destroyedInTransaction);
		return (Collection<T>) values;
	}

	public void beginTransaction() {
		transaction = new DoubleKeyedMap<Class<? extends IIdentifiable>, Long, IIdentifiable>();
		destroyedInTransaction = new HashSet<IIdentifiable>();
	}

	public void closeSession() {
		isSessionOpen = false;
	}

	public void commit() {
		DoubleKeyedMap<Class<? extends IIdentifiable>, Long, IIdentifiable> transactionCopy = new DoubleKeyedMap<Class<? extends IIdentifiable>, Long, IIdentifiable>(transaction);
		transaction = null;
		for (Map<Long, IIdentifiable> idIIdentifiable : transactionCopy.values()) {
			for (IIdentifiable identifiable : idIIdentifiable.values())
				save(identifiable);
		}

		for (IIdentifiable identifiable : destroyedInTransaction)
			destroy(identifiable);
		destroyedInTransaction.clear();
	}

	public void destroy(IIdentifiable object) {
		if (transaction == null) wasDestroyed.add(source.remove(object.getClass(), object.getId()));
		else destroyedInTransaction.add(object);
	}

	@SuppressWarnings("unchecked")
	public <T extends IIdentifiable> T find(Class<T> objectType, Long id) {
		if (id == null) throw new IllegalArgumentException("id to load is required for loading");
		T object = null;
		if (transaction != null) object = (T) transaction.get(objectType, id);
		if (object == null) object = (T) source.get(objectType, id);
		if (destroyedInTransaction.contains(object)) return null;
		return object;
	}

	public <T extends IIdentifiable> T find(Class<T> theClass, String attributeName, Object value) {
		return find(theClass, new String[] { attributeName }, new Object[] { value });
	}

	public <T extends IIdentifiable> T find(Class<T> theClass, String[] attributeNames, Object[] values) {
		Collection<T> matches = findAll(theClass, attributeNames, values);
		if (matches.isEmpty()) return null;
		if (matches.size() == 1) return matches.iterator().next();
		throw new PersistException("There is more than one item that matches 'unique' criterion.");
	}

	public <T extends IIdentifiable> Collection<T> findAll(Class<T> theClass, String attributeName, Object value) {
		return findAll(theClass, new String[] { attributeName }, new Object[] { value });
	}

	public <T extends IIdentifiable> Collection<T> findAll(Class<T> theClass, String[] attributeNames, Object[] values) {
		Collection<T> allObjects = all(theClass);
		List<T> matches = new ArrayList<T>(allObjects);
		for (int i = 0; i < values.length; i++) {
			String attributeName = attributeNames[i];
			FieldAccessor accessor = new FieldAccessor(attributeName);
			Object value = values[i];
			for (Object asset : allObjects) {
				if (!EqualsBuilder.reflectionEquals(value, accessor.get(asset))) matches.remove(asset);
			}
		}
		return matches;
	}

	public boolean isSessionOpen() {
		return isSessionOpen;
	}

	/**
	 * Saves the object, but does not mark it as saved with respect to the
	 * question {@link #wasSaved(KnowledgeAsset)}. Great for setting up!
	 * 
	 * @param assetType TODO
	 * @param object
	 */
	public void provides(Class<? extends IIdentifiable> assetType, IIdentifiable object) {
		ensureId(object);
		source.put(assetType, object.getId(), object);
	}

	@SuppressWarnings("unchecked")
	public void provides(IIdentifiable... objects) {
		for (IIdentifiable object : objects)
			provides(object.getClass(), object);
	}

	public void reset() {
		source = new DoubleKeyedMap<Class<? extends IIdentifiable>, Long, IIdentifiable>();
		wasDestroyed.clear();
		wasSaved.clear();
	}

	@SuppressWarnings("unchecked")
	public void save(IIdentifiable object) {
		if (exceptionOnSave != null) throw exceptionOnSave;
		if (transaction == null) {
			wasSaved.add(object);
			provides(object.getClass(), object);
		} else {
			ensureId(object);
			transaction.put(object.getClass(), object.getId(), object);
		}
	}

	public void setExceptionOnSave(PersistException exception) {
		exceptionOnSave = exception;
	}

	public boolean wasDestroyed(IIdentifiable object) {
		return wasDestroyed.contains(object);
	}

	public boolean wasSaved(IIdentifiable object) {
		return wasSaved.contains(object);
	}

	protected void ensureId(IIdentifiable object) {
		if (object.getId() == null) idFieldAccessor.set(object, idProvider.next());
	}

	protected <T extends IIdentifiable> Collection<T> findAll(Class<T> type, Collection<Long> ids) {
		Collection<T> results = new ArrayList<T>();
		for (Long id : ids)
			results.add(find(type, id));
		return results;
	}
}
