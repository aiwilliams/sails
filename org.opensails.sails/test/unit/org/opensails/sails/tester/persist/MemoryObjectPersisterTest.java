package org.opensails.sails.tester.persist;

import java.util.Date;

import junit.framework.TestCase;

import org.opensails.sails.persist.PersistException;
import org.opensails.sails.util.CollectionAssert;
import org.opensails.sails.util.FieldAccessor;

public class MemoryObjectPersisterTest extends TestCase {
	protected MemoryObjectPersister registry = new MemoryObjectPersister();

	public void testDestroy_InTransaction() throws Exception {
		ShamIdentifiable oldDevotion = new ShamIdentifiable();
		registry.provides(oldDevotion);
		registry.beginTransaction();
		registry.destroy(oldDevotion);
		assertNull(registry.find(ShamIdentifiable.class, oldDevotion.getId()));
		assertFalse(registry.wasDestroyed(oldDevotion));
		MemoryObjectPersister newRegistry = new MemoryObjectPersister(registry.source);
		assertEquals(oldDevotion, newRegistry.find(ShamIdentifiable.class, oldDevotion.getId()));
		CollectionAssert.containsOnly(new ShamIdentifiable[] { oldDevotion }, newRegistry.all(ShamIdentifiable.class));
		assertTrue(registry.all(ShamIdentifiable.class).isEmpty());
		registry.commit();
		assertNull(registry.find(ShamIdentifiable.class, oldDevotion.getId()));
		assertNull(newRegistry.find(ShamIdentifiable.class, oldDevotion.getId()));
		assertTrue(registry.wasDestroyed(oldDevotion));
		assertTrue(registry.all(ShamIdentifiable.class).isEmpty());
		assertTrue(newRegistry.all(ShamIdentifiable.class).isEmpty());
	}

	public void testDestroy_NoTransaction() throws Exception {
		ShamIdentifiable oldDevotion = new ShamIdentifiable();
		registry.provides(oldDevotion);
		registry.destroy(oldDevotion);
		MemoryObjectPersister newRegistry = new MemoryObjectPersister(registry.source);
		assertNull(registry.find(ShamIdentifiable.class, oldDevotion.getId()));
		assertTrue(registry.all(ShamIdentifiable.class).isEmpty());
		assertNull(newRegistry.find(ShamIdentifiable.class, oldDevotion.getId()));
		assertTrue(newRegistry.all(ShamIdentifiable.class).isEmpty());
		assertTrue(registry.wasDestroyed(oldDevotion));
	}

	public void testFind_Unique() {
		ShamIdentifiable one = new ShamIdentifiable("one");
		ShamIdentifiable two = new ShamIdentifiable("two");
		ShamIdentifiable oneSquared = new ShamIdentifiable("oneSquared");
		ShamIdentifiable nullIdentifiable = new ShamIdentifiable("dummy");
		FieldAccessor nameWriter = new FieldAccessor("first");
		nameWriter.set(nullIdentifiable, null);
		nameWriter.set(oneSquared, "one");
		registry.save(one);
		registry.save(oneSquared);
		registry.save(two);
		registry.save(nullIdentifiable);
		assertEquals(two, registry.find(ShamIdentifiable.class, "first", "two"));
		try {
			registry.find(ShamIdentifiable.class, "first", "one");
			fail("Should throw an exception");
		} catch (Exception e) {}
		assertEquals(nullIdentifiable, registry.find(ShamIdentifiable.class, "first", null));

		assertNull(registry.find(ShamDatedIdentifiable.class, "first", "dummy"));
	}

	public void testGet() throws Exception {
		try {
			registry.find(ShamIdentifiable.class, null);
			fail("The real registry throws an IllegalArgumentException when given null. This must behave the same to make unit tests accurate.");
		} catch (IllegalArgumentException expected) {}
	}

	public void testGetAll() {
		ShamIdentifiable asset1 = new ShamIdentifiable();
		ShamIdentifiable asset2 = new ShamIdentifiable();
		registry.save(asset1);
		registry.save(asset2);
		CollectionAssert.containsOnly(new ShamIdentifiable[] { asset1, asset2 }, registry.all(ShamIdentifiable.class));
		assertTrue(registry.all(ShamDatedIdentifiable.class).isEmpty());
	}

	public void testGetAll_MultipleAttributes() throws Exception {
		Date standardDate = new Date();
		ShamDatedIdentifiable first = new ShamDatedIdentifiable("standardName", standardDate);
		ShamDatedIdentifiable second = new ShamDatedIdentifiable("standardName", standardDate);
		ShamDatedIdentifiable third = new ShamDatedIdentifiable("differentName", standardDate);
		registry.save(first);
		registry.save(second);
		registry.save(third);
		CollectionAssert.containsOnly(new ShamDatedIdentifiable[] { first, second }, registry.findAll(ShamDatedIdentifiable.class, new String[] { "aDate", "first" }, new Object[] {
			standardDate, "standardName" }));
		CollectionAssert.containsOnly(new ShamDatedIdentifiable[] { third }, registry.findAll(ShamDatedIdentifiable.class, new String[] { "aDate", "first" }, new Object[] {
			standardDate, "differentName" }));
		assertTrue(registry.findAll(ShamDatedIdentifiable.class, new String[] { "aDate", "first" }, new Object[] { "differentId", "differentName" }).isEmpty());
	}

	public void testGetAll_SingleAttribute() throws Exception {
		ShamIdentifiable asset1 = new ShamIdentifiable("standardName");
		ShamIdentifiable asset2 = new ShamIdentifiable("standardName");
		ShamIdentifiable asset3 = new ShamIdentifiable("differentName");
		registry.save(asset1);
		registry.save(asset2);
		registry.save(asset3);
		CollectionAssert.containsOnly(new ShamIdentifiable[] { asset1, asset2 }, registry.findAll(ShamIdentifiable.class, "first", "standardName"));
		CollectionAssert.containsOnly(new ShamIdentifiable[] { asset3 }, registry.findAll(ShamIdentifiable.class, "first", "differentName"));
		assertTrue(registry.findAll(ShamIdentifiable.class, "first", "unusedName").isEmpty());
	}

	public void testGetUnique_MultipleAttributes() throws Exception {
		ShamIdentifiable identifiable = new ShamIdentifiable("leFirst", "leSecond");
		registry.save(identifiable);
		ShamIdentifiable retrieved = registry.find(ShamIdentifiable.class, new String[] { "first", "last" }, new Object[] { "leFirst", "leSecond" });
		assertEquals(identifiable, retrieved);
		retrieved = registry.find(ShamIdentifiable.class, new String[] { "first", "last" }, new Object[] { "oops", "" });
		assertNull(retrieved);
	}

	public void testProvides() {
		ShamIdentifiable asset = new ShamIdentifiable();
		assertNull(asset.getId());
		registry.provides(asset);
		assertNotNull(asset.getId());
		assertEquals(asset, registry.find(asset.getClass(), asset.getId()));
		assertFalse(registry.wasSaved(asset));
	}

	public void testSave_InTransaction() {
		ShamIdentifiable oldDevotion = new ShamIdentifiable("Barney");
		registry.provides(oldDevotion);
		registry.beginTransaction();
		ShamIdentifiable asset = new ShamIdentifiable("Fred");
		assertNull(asset.getId());
		registry.save(asset);
		assertNotNull(asset.getId());
		assertEquals(asset, registry.find(asset.getClass(), asset.getId()));
		CollectionAssert.containsOnly(new ShamIdentifiable[] { oldDevotion, asset }, registry.all(ShamIdentifiable.class));
		CollectionAssert.containsOnly(new ShamIdentifiable[] { asset }, registry.findAll(ShamIdentifiable.class, "first", "Fred"));
		assertEquals(asset, registry.find(ShamIdentifiable.class, "first", "Fred"));

		MemoryObjectPersister newRegistry = new MemoryObjectPersister(registry.source);
		assertNull(newRegistry.find(asset.getClass(), asset.getId()));
		CollectionAssert.containsOnly(new ShamIdentifiable[] { oldDevotion }, newRegistry.all(ShamIdentifiable.class));

		registry.commit();
		assertEquals(asset, newRegistry.find(asset.getClass(), asset.getId()));
		CollectionAssert.containsOnly(new ShamIdentifiable[] { oldDevotion, asset }, registry.all(ShamIdentifiable.class));
		CollectionAssert.containsOnly(new ShamIdentifiable[] { oldDevotion, asset }, newRegistry.all(ShamIdentifiable.class));

		// TODO get another session and make sure its there?
		// assertTrue(registry.wasSaved(asset));

		// Create an object that isn't "whole"
		ShamIdentifiable troubledAsset = new ShamIdentifiable(null);
		registry.setExceptionOnSave(new PersistException("Fake"));
		try {
			registry.save(troubledAsset);
			fail("Should throw exception");
		} catch (Exception e) {}
		registry.setExceptionOnSave(null);
		troubledAsset.setName("Something");
		registry.save(troubledAsset);
		assertEquals(troubledAsset, registry.find(troubledAsset.getClass(), troubledAsset.getId()));
		CollectionAssert.containsOnly(new ShamIdentifiable[] { oldDevotion, asset, troubledAsset }, registry.all(ShamIdentifiable.class));
		assertNotNull(troubledAsset.getId());
		// TODO get another session and make sure its there?
		// assertTrue(registry.wasSaved(asset));
	}

	public void testSave_NoTransaction() {
		ShamIdentifiable asset = new ShamIdentifiable();
		assertNull(asset.getId());
		registry.save(asset);
		assertNotNull(asset.getId());
		assertEquals(asset, registry.find(asset.getClass(), asset.getId()));
		assertTrue(registry.wasSaved(asset));

		ShamIdentifiable troubledAsset = new ShamIdentifiable();
		registry.setExceptionOnSave(new PersistException("Fake"));
		try {
			registry.save(troubledAsset);
			fail("Should throw exception");
		} catch (Exception e) {
			assertNull(troubledAsset.getId());
		}
		registry.setExceptionOnSave(null);
		registry.save(troubledAsset);
		assertNotNull(troubledAsset.getId());
		assertTrue(registry.wasSaved(troubledAsset));
	}
}
