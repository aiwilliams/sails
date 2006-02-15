package org.opensails.sails.tester.persist;

import java.util.Date;

import junit.framework.TestCase;

import org.opensails.sails.persist.PersistException;
import org.opensails.sails.tester.util.CollectionAssert;
import org.opensails.sails.util.FieldAccessor;

public class MemoryObjectPersisterTest extends TestCase {
	protected MemoryObjectPersister persister = new MemoryObjectPersister();

	public void testDestroy_InTransaction() throws Exception {
		ShamIdentifiable old = new ShamIdentifiable();
		persister.provides(old);
		persister.beginTransaction();
		persister.destroy(old);
		assertNull(persister.find(ShamIdentifiable.class, old.getId()));
		assertFalse(persister.wasDestroyed(old));
		MemoryObjectPersister newPersister = new MemoryObjectPersister(persister.source);
		assertEquals(old, newPersister.find(ShamIdentifiable.class, old.getId()));
		CollectionAssert.containsOnly(new ShamIdentifiable[] { old }, newPersister.all(ShamIdentifiable.class));
		assertTrue(persister.all(ShamIdentifiable.class).isEmpty());
		persister.commit();
		assertNull(persister.find(ShamIdentifiable.class, old.getId()));
		assertNull(newPersister.find(ShamIdentifiable.class, old.getId()));
		assertTrue(persister.wasDestroyed(old));
		assertTrue(persister.all(ShamIdentifiable.class).isEmpty());
		assertTrue(newPersister.all(ShamIdentifiable.class).isEmpty());
	}

	public void testDestroy_NoTransaction() throws Exception {
		ShamIdentifiable old = new ShamIdentifiable();
		persister.provides(old);
		persister.destroy(old);
		MemoryObjectPersister newPersister = new MemoryObjectPersister(persister.source);
		assertNull(persister.find(ShamIdentifiable.class, old.getId()));
		assertTrue(persister.all(ShamIdentifiable.class).isEmpty());
		assertNull(newPersister.find(ShamIdentifiable.class, old.getId()));
		assertTrue(newPersister.all(ShamIdentifiable.class).isEmpty());
		assertTrue(persister.wasDestroyed(old));
	}

	public void testFind_Unique() {
		ShamIdentifiable one = new ShamIdentifiable("one");
		ShamIdentifiable two = new ShamIdentifiable("two");
		ShamIdentifiable oneSquared = new ShamIdentifiable("oneSquared");
		ShamIdentifiable nullIdentifiable = new ShamIdentifiable("dummy");
		FieldAccessor nameWriter = new FieldAccessor("first");
		nameWriter.set(nullIdentifiable, null);
		nameWriter.set(oneSquared, "one");
		persister.save(one);
		persister.save(oneSquared);
		persister.save(two);
		persister.save(nullIdentifiable);
		assertEquals(two, persister.find(ShamIdentifiable.class, "first", "two"));
		try {
			persister.find(ShamIdentifiable.class, "first", "one");
			fail("Should throw an exception");
		} catch (Exception e) {}
		assertEquals(nullIdentifiable, persister.find(ShamIdentifiable.class, "first", null));

		assertNull(persister.find(ShamDatedIdentifiable.class, "first", "dummy"));
	}

	public void testGet() throws Exception {
		try {
			persister.find(ShamIdentifiable.class, null);
			fail("The real registry throws an IllegalArgumentException when given null. This must behave the same to make unit tests accurate.");
		} catch (IllegalArgumentException expected) {}
	}

	public void testGetAll() {
		ShamIdentifiable one = new ShamIdentifiable();
		ShamIdentifiable two = new ShamIdentifiable();
		persister.save(one);
		persister.save(two);
		CollectionAssert.containsOnly(new ShamIdentifiable[] { one, two }, persister.all(ShamIdentifiable.class));
		assertTrue(persister.all(ShamDatedIdentifiable.class).isEmpty());
	}

	public void testGetAll_MultipleAttributes() throws Exception {
		Date standardDate = new Date();
		ShamDatedIdentifiable first = new ShamDatedIdentifiable("standardName", standardDate);
		ShamDatedIdentifiable second = new ShamDatedIdentifiable("standardName", standardDate);
		ShamDatedIdentifiable third = new ShamDatedIdentifiable("differentName", standardDate);
		persister.save(first);
		persister.save(second);
		persister.save(third);
		CollectionAssert.containsOnly(new ShamDatedIdentifiable[] { first, second }, persister.findAll(ShamDatedIdentifiable.class, new String[] { "aDate", "first" }, new Object[] {
			standardDate, "standardName" }));
		CollectionAssert.containsOnly(new ShamDatedIdentifiable[] { third }, persister.findAll(ShamDatedIdentifiable.class, new String[] { "aDate", "first" }, new Object[] {
			standardDate, "differentName" }));
		assertTrue(persister.findAll(ShamDatedIdentifiable.class, new String[] { "aDate", "first" }, new Object[] { "differentId", "differentName" }).isEmpty());
	}

	public void testGetAll_SingleAttribute() throws Exception {
		ShamIdentifiable one = new ShamIdentifiable("standardName");
		ShamIdentifiable two = new ShamIdentifiable("standardName");
		ShamIdentifiable three = new ShamIdentifiable("differentName");
		persister.save(one);
		persister.save(two);
		persister.save(three);
		CollectionAssert.containsOnly(new ShamIdentifiable[] { one, two }, persister.findAll(ShamIdentifiable.class, "first", "standardName"));
		CollectionAssert.containsOnly(new ShamIdentifiable[] { three }, persister.findAll(ShamIdentifiable.class, "first", "differentName"));
		assertTrue(persister.findAll(ShamIdentifiable.class, "first", "unusedName").isEmpty());
	}

	public void testGetUnique_MultipleAttributes() throws Exception {
		ShamIdentifiable identifiable = new ShamIdentifiable("leFirst", "leSecond");
		persister.save(identifiable);
		ShamIdentifiable retrieved = persister.find(ShamIdentifiable.class, new String[] { "first", "last" }, new Object[] { "leFirst", "leSecond" });
		assertEquals(identifiable, retrieved);
		retrieved = persister.find(ShamIdentifiable.class, new String[] { "first", "last" }, new Object[] { "oops", "" });
		assertNull(retrieved);
	}

	public void testProvides() {
		ShamIdentifiable asset = new ShamIdentifiable();
		assertNull(asset.getId());
		persister.provides(asset);
		assertNotNull(asset.getId());
		assertEquals(asset, persister.find(asset.getClass(), asset.getId()));
		assertFalse(persister.wasSaved(asset));
	}

	public void testSave_InTransaction() {
		ShamIdentifiable old = new ShamIdentifiable("Barney");
		persister.provides(old);
		persister.beginTransaction();
		ShamIdentifiable asset = new ShamIdentifiable("Fred");
		assertNull(asset.getId());
		persister.save(asset);
		assertNotNull(asset.getId());
		assertEquals(asset, persister.find(asset.getClass(), asset.getId()));
		CollectionAssert.containsOnly(new ShamIdentifiable[] { old, asset }, persister.all(ShamIdentifiable.class));
		CollectionAssert.containsOnly(new ShamIdentifiable[] { asset }, persister.findAll(ShamIdentifiable.class, "first", "Fred"));
		assertEquals(asset, persister.find(ShamIdentifiable.class, "first", "Fred"));

		MemoryObjectPersister newPersister = new MemoryObjectPersister(persister.source);
		assertNull(newPersister.find(asset.getClass(), asset.getId()));
		CollectionAssert.containsOnly(new ShamIdentifiable[] { old }, newPersister.all(ShamIdentifiable.class));

		persister.commit();
		assertEquals(asset, newPersister.find(asset.getClass(), asset.getId()));
		CollectionAssert.containsOnly(new ShamIdentifiable[] { old, asset }, persister.all(ShamIdentifiable.class));
		CollectionAssert.containsOnly(new ShamIdentifiable[] { old, asset }, newPersister.all(ShamIdentifiable.class));

		// TODO get another session and make sure its there?
		// assertTrue(registry.wasSaved(asset));

		// Create an object that isn't "whole"
		ShamIdentifiable troubledAsset = new ShamIdentifiable(null);
		persister.setExceptionOnSave(new PersistException("Fake"));
		try {
			persister.save(troubledAsset);
			fail("Should throw exception");
		} catch (Exception e) {}
		persister.setExceptionOnSave(null);
		troubledAsset.setName("Something");
		persister.save(troubledAsset);
		assertEquals(troubledAsset, persister.find(troubledAsset.getClass(), troubledAsset.getId()));
		CollectionAssert.containsOnly(new ShamIdentifiable[] { old, asset, troubledAsset }, persister.all(ShamIdentifiable.class));
		assertNotNull(troubledAsset.getId());
		// TODO get another session and make sure its there?
		// assertTrue(registry.wasSaved(asset));
	}

	public void testSave_NoTransaction() {
		ShamIdentifiable asset = new ShamIdentifiable();
		assertNull(asset.getId());
		persister.save(asset);
		assertNotNull(asset.getId());
		assertEquals(asset, persister.find(asset.getClass(), asset.getId()));
		assertTrue(persister.wasSaved(asset));

		ShamIdentifiable troubledAsset = new ShamIdentifiable();
		persister.setExceptionOnSave(new PersistException("Fake"));
		try {
			persister.save(troubledAsset);
			fail("Should throw exception");
		} catch (Exception e) {
			assertNull(troubledAsset.getId());
		}
		persister.setExceptionOnSave(null);
		persister.save(troubledAsset);
		assertNotNull(troubledAsset.getId());
		assertTrue(persister.wasSaved(troubledAsset));
	}

	public void testWasSaved_WasDestroyed() throws Exception {
		ShamIdentifiablePrimitive one = new ShamIdentifiablePrimitive();
		assertFalse(persister.wasSaved(one));
		assertFalse(persister.wasDestroyed(one));
		persister.save(one);
		assertTrue(persister.wasSaved(one));
		assertFalse(persister.wasDestroyed(one));
		persister.destroy(one);
		assertTrue(persister.wasDestroyed(one));
	}
}
