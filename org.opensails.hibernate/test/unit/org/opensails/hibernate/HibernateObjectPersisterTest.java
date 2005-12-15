package org.opensails.hibernate;

import junit.framework.*;

import org.apache.commons.lang.*;
import org.hibernate.*;
import org.opensails.sails.tester.util.*;

public class HibernateObjectPersisterTest extends TestCase {
	protected static HibernateTester hibernateTester;
	static {
		hibernateTester = HibernateTester.basicInMemory();
		hibernateTester.setupMappings(new IHibernateMappingConfiguration() {
			public Class[] annotatedClasses() {
				return new Class[] { Harbor.class, Sailboat.class };
			}

			public Class[] mappedClasses() {
				return ArrayUtils.EMPTY_CLASS_ARRAY;
			}
		});
	}

	protected HibernateObjectPersister persister;

	public void testAll() {
		Sailboat asset1 = new Sailboat();
		Sailboat asset2 = new Sailboat();
		persister.save(asset1);
		persister.save(asset2);
		CollectionAssert.containsOnly(new Sailboat[] { asset1, asset2 }, persister.all(Sailboat.class));
		assertTrue(persister.all(Harbor.class).isEmpty());
	}

	public void testCommit() throws Exception {
		// should not throw an exception if there is no transaction
		persister.commit();
		persister.beginTransaction();
		// should not throw an exception if there is nothing to commit
		persister.commit();
		// should not throw an exception if a second commit occurs
		persister.commit();
	}

	public void testDestroy_InTransaction() throws Exception {
		persister.getSession().setFlushMode(FlushMode.AUTO);
		assertTrue(persister.all(Sailboat.class).isEmpty());
		Sailboat oldSailboat = new Sailboat();
		persister.save(oldSailboat);

		persister.beginTransaction();
		persister.destroy(oldSailboat);
		assertNull(persister.find(Sailboat.class, oldSailboat.getId()));
		HibernateObjectPersister newPersister = new HibernateObjectPersister(hibernateTester.getSessionFactory());
		assertEquals(oldSailboat, newPersister.find(Sailboat.class, oldSailboat.getId()));
		CollectionAssert.containsOnly(new Sailboat[] { oldSailboat }, newPersister.all(Sailboat.class));
		// NOTE: This only works in auto FlushMode
		assertTrue(persister.all(Sailboat.class).isEmpty());
		persister.commit();

		assertNull(persister.find(Sailboat.class, oldSailboat.getId()));
		Sailboat staleboat = newPersister.find(Sailboat.class, oldSailboat.getId());
		try {
			newPersister.getSession().refresh(staleboat);
			fail("Expected exception trying to refresh object that was deleted");
		} catch (UnresolvableObjectException e) {
		}
		// NOTE: This deleted object doesn't seem to be saved, even though
		// Hibernate doesn't throw an exception
		newPersister.save(staleboat);
		assertTrue(persister.all(Sailboat.class).isEmpty());
		assertTrue(newPersister.all(Sailboat.class).isEmpty());
	}

	public void testDestroy_NoTransaction() throws Exception {
		Sailboat oldSailboat = new Sailboat();
		persister.save(oldSailboat);
		persister.destroy(oldSailboat);
		HibernateObjectPersister newPersister = new HibernateObjectPersister(hibernateTester.getSessionFactory());
		assertNull(persister.find(Sailboat.class, oldSailboat.getId()));
		assertTrue(persister.all(Sailboat.class).isEmpty());
		assertNull(newPersister.find(Sailboat.class, oldSailboat.getId()));
		assertTrue(newPersister.all(Sailboat.class).isEmpty());
	}

	public void testFind() {
		Sailboat santaMarie = new Sailboat("one");
		Sailboat nina = new Sailboat("two");
		Sailboat pinta = new Sailboat("one");
		persister.save(santaMarie);
		persister.save(pinta);
		persister.save(nina);
		assertEquals(nina, persister.find(Sailboat.class, "name", "two"));
		try {
			persister.find(Sailboat.class, "name", "one");
			fail("There is more than one that matches");
		} catch (Exception e) {
		}

		// verify we can query by NULL?
		Harbor harbor = new Harbor();
		persister.save(harbor);
		santaMarie.setHarbor(harbor);
		nina.setHarbor(harbor);
		persister.save(santaMarie);
		persister.save(nina);
		assertEquals(pinta, persister.find(Sailboat.class, "harbor", null));

		assertNull(persister.find(Sailboat.class, "name", "dummy"));
	}

	public void testFind_Unique_MultipleAttributes() throws Exception {
		Sailboat blackPearl = new Sailboat("black pearl");
		Harbor harbor = new Harbor();
		blackPearl.setHarbor(harbor);

		persister.save(harbor);
		persister.save(blackPearl);

		Sailboat loadedBoat = persister.find(Sailboat.class, new String[] { "harbor", "name" }, new Object[] { harbor, "black pearl" });
		assertEquals(blackPearl, loadedBoat);
		loadedBoat = persister.find(Sailboat.class, new String[] { "harbor", "name" }, new Object[] { blackPearl, "pinta" });
		assertNull(loadedBoat);
	}

	public void testFindAll_MultipleAttributes() throws Exception {
		Harbor harbor1 = new Harbor();
		persister.save(harbor1);

		Sailboat boat1 = new Sailboat("standardName");
		Sailboat boat2 = new Sailboat("standardName");
		Sailboat boat3 = new Sailboat("differentName");
		boat1.setHarbor(harbor1);
		boat2.setHarbor(harbor1);
		persister.save(boat1);
		persister.save(boat2);
		persister.save(boat3);

		CollectionAssert.containsOnly(new Sailboat[] { boat1, boat2 }, persister.findAll(Sailboat.class, new String[] { "harbor", "name" }, new Object[] { harbor1, "standardName" }));
		CollectionAssert.containsOnly(new Sailboat[] { boat3 }, persister.findAll(Sailboat.class, new String[] { "harbor", "name" }, new Object[] { null, "differentName" }));

		Harbor emptyHarbor = new Harbor();
		persister.save(emptyHarbor);
		assertTrue(persister.findAll(Sailboat.class, new String[] { "harbor", "name" }, new Object[] { emptyHarbor, "differentName" }).isEmpty());
	}

	public void testFindAll_SingleAttribute() throws Exception {
		Sailboat asset1 = new Sailboat("standardName");
		Sailboat asset2 = new Sailboat("standardName");
		Sailboat asset3 = new Sailboat("differentName");
		persister.save(asset1);
		persister.save(asset2);
		persister.save(asset3);
		CollectionAssert.containsOnly(new Sailboat[] { asset1, asset2 }, persister.findAll(Sailboat.class, "name", "standardName"));
		CollectionAssert.containsOnly(new Sailboat[] { asset3 }, persister.findAll(Sailboat.class, "name", "differentName"));
		assertTrue(persister.findAll(Sailboat.class, "name", "unusedName").isEmpty());
	}

	public void testReload() {
		Sailboat sailboat = new Sailboat();
		persister.save(sailboat);
		Sailboat reloaded = persister.reload(sailboat);
		assertNotNull(reloaded);
		assertNotSame(sailboat, reloaded);

		persister.destroy(reloaded);
		try {
			persister.reload(reloaded);
			fail("Reloading a non-persisted entity should fail. Use find if the instance might not be persisted.");
		} catch (Throwable expected) {
		}
	}

	// GREENBAR!
	public void testSave_InTransaction() {
		persister.beginTransaction();
		assertTrue("sanity", persister.all(Sailboat.class).isEmpty());

		Sailboat asset = new Sailboat("Fred");
		assertEquals(0, asset.getId().longValue());

		persister.save(asset);
		Long firstId = asset.getId();
		assertNotNull(firstId);
		persister.save(asset);
		assertEquals("Saving same object twice in same transaction should not be a problem", firstId, asset.getId());

		assertEquals("With FlushMode.COMMIT or anything, get by id works because session has identity map", asset, persister.find(Sailboat.class, firstId));
		persister.getSession().evict(asset);
		assertFalse("With FlushMode.COMMIT, queries DON'T return 'stale' data, and no-one knows why", persister.all(Sailboat.class).isEmpty());

		HibernateObjectPersister newPersister = new HibernateObjectPersister(hibernateTester.getSessionFactory());
		assertNotNull("New session CAN find objects uncommitted in other sessions, and no-one knows why", newPersister.find(Sailboat.class, asset.getId()));

		persister.commit();
		// Ensure query now returns committed data
		CollectionAssert.containsOnly(new Sailboat[] { asset }, persister.all(Sailboat.class));
		assertEquals(asset, newPersister.find(Sailboat.class, asset.getId()));
	}

	public void testSave_InTransaction_OneObjectFails() throws Exception {
		Sailboat good = new Sailboat("Good Devotion");
		Sailboat bad = new Sailboat(null);

		persister.beginTransaction();
		try {
			persister.save(good);
			persister.save(bad);
		} catch (PropertyValueException expected) {
			assertNull("The registry should close the session and transaction, therefore, gets from new session return null", persister.find(Sailboat.class, good.getId()));
			assertNotNull("The bad still got an id", bad.getId());
		}
		try {
			persister.commit();
		} catch (Throwable e) {
			fail("Should not fail commit even if there is no transaction");
		}
	}

	public void testSave_NoTransaction() {
		Sailboat asset = new Sailboat("Fred");
		assertEquals(0, asset.getId().longValue());
		persister.save(asset);
		assertNotNull(asset.getId());
		assertEquals(asset, persister.find(asset.getClass(), asset.getId()));

		persister.save(asset);
		Long firstId = asset.getId();
		assertNotNull(asset.getId());

		// make sure if we save it twice we are only getting one object
		persister.save(asset);
		Long secondId = asset.getId();
		assertNotNull(asset.getId());
		assertEquals(firstId, secondId);

		assertEquals(asset, persister.find(Sailboat.class, firstId));
		assertEquals(asset, persister.find(Sailboat.class, secondId));

		CollectionAssert.containsOnly(new Sailboat[] { asset }, persister.all(Sailboat.class));

		HibernateObjectPersister newPersister = new HibernateObjectPersister(hibernateTester.getSessionFactory());
		assertEquals(asset, newPersister.find(Sailboat.class, asset.getId()));

		// Create an object that isn't "whole"
		Sailboat troubledAsset = new Sailboat(null);
		try {
			persister.save(troubledAsset);
			fail("Should throw exception");
		} catch (PropertyValueException e) {
		}
		troubledAsset.setName("Something");
		persister.save(troubledAsset);
		persister.closeSession();
		assertEquals(troubledAsset, persister.find(troubledAsset.getClass(), troubledAsset.getId()));
		assertNotNull(troubledAsset.getId());

		assertEquals(troubledAsset, newPersister.find(troubledAsset.getClass(), troubledAsset.getId()));
	}

	public void testSave_Twice() {
		Sailboat asset = new Sailboat("Fred");
		assertEquals(0, asset.getId().longValue());

		persister.save(asset);
		Long firstId = asset.getId();
		assertNotNull(firstId);

		// make sure if we save it twice we are only getting one object
		persister.save(asset);
		Long secondId = asset.getId();
		assertNotNull(secondId);

		assertEquals(firstId, secondId);
		assertEquals(asset, persister.find(asset.getClass(), firstId));
		CollectionAssert.containsOnly(new Sailboat[] { asset }, persister.all(Sailboat.class));
	}

	public void testSave_TwoThreadsModifyingCollectionAtSameTime() throws Exception {
		Sailboat sailboatOne = new Sailboat("firstBoat");
		Sailboat sailboatTwo = new Sailboat("secondBoat");
		persister.save(sailboatOne);
		persister.save(sailboatTwo);
		Harbor harbor = new Harbor();
		harbor.addSailboat(sailboatOne);
		harbor.addSailboat(sailboatTwo);
		persister.save(harbor);

		HibernateObjectPersister persisterOne = new HibernateObjectPersister(hibernateTester.getSessionFactory());
		HibernateObjectPersister persisterTwo = new HibernateObjectPersister(hibernateTester.getSessionFactory());
		Harbor harborFromPersisterOne = persisterOne.find(Harbor.class, harbor.getId());
		Harbor harborFromPersisterTwo = persisterTwo.find(Harbor.class, harbor.getId());

		Sailboat sailboatThree = new Sailboat("thirdBoat");
		Sailboat sailboatFour = new Sailboat("fourthBoat");
		persisterOne.save(sailboatThree);
		persisterTwo.save(sailboatFour);
		harborFromPersisterOne.addSailboat(sailboatThree);
		harborFromPersisterTwo.addSailboat(sailboatFour);
		persisterOne.save(harborFromPersisterOne);
		persisterTwo.save(harborFromPersisterTwo);

		HibernateObjectPersister newPersister = new HibernateObjectPersister(hibernateTester.getSessionFactory());
		CollectionAssert.containsOnly(new Sailboat[] { sailboatOne, sailboatTwo, sailboatThree, sailboatFour }, newPersister.find(Harbor.class, harbor.getId()).getSailboats());

		persisterOne.getSession().clear();
		CollectionAssert.containsOnly(new Sailboat[] { sailboatOne, sailboatTwo, sailboatThree, sailboatFour }, persisterOne.find(Harbor.class, harbor.getId()).getSailboats());

		assertTrue(persisterTwo.getSession().contains(sailboatFour));
		persisterTwo.getSession().evict(harborFromPersisterTwo);
		assertFalse(persisterTwo.getSession().contains(harborFromPersisterTwo));
		assertTrue("Harbors don't cascade", persisterTwo.getSession().contains(sailboatFour));
		persisterTwo.getSession().close();
		persisterOne.getSession().close();

		try {
			persisterTwo.getSession().refresh(harborFromPersisterTwo.getSailboats());
			fail("Unfortunately, Hibernate can't refresh a collection, only its container.  If you see this, this fact has changed");
		} catch (Exception e) {
		}

		persisterTwo.getSession().refresh(harborFromPersisterTwo);
		CollectionAssert.containsOnly(new Sailboat[] { sailboatOne, sailboatTwo, sailboatThree, sailboatFour }, harborFromPersisterTwo.getSailboats());
		CollectionAssert.containsOnly(new Sailboat[] { sailboatOne, sailboatTwo, sailboatThree, sailboatFour }, persisterTwo.find(Harbor.class, harbor.getId()).getSailboats());
	}

	protected void deleteAllHarbors() {
		Session session = hibernateTester.getSessionFactory().openSession();
		session.createQuery("DELETE FROM Harbor").executeUpdate();
		session.flush();
		session.close();
	}

	protected void deleteAllSailboats() {
		Session session = hibernateTester.getSessionFactory().openSession();
		session.createQuery("DELETE FROM Sailboat").executeUpdate();
		session.flush();
		session.close();
	}

	@Override
	protected void setUp() throws Exception {
		deleteAllSailboats();
		deleteAllHarbors();
		persister = new HibernateObjectPersister(hibernateTester.getSessionFactory());
	}
}