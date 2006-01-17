package org.opensails.hibernate;

import junit.framework.TestCase;

import org.apache.commons.lang.ArrayUtils;
import org.hibernate.FlushMode;
import org.hibernate.PropertyValueException;
import org.hibernate.Session;
import org.hibernate.UnresolvableObjectException;
import org.opensails.sails.tester.util.CollectionAssert;

public class HibernateDataMapperTest extends TestCase {
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

	protected HibernateDataMapper mapper;

	public void testAll() {
		Sailboat asset1 = new Sailboat();
		Sailboat asset2 = new Sailboat();
		mapper.save(asset1);
		mapper.save(asset2);
		CollectionAssert.containsOnly(new Sailboat[] { asset1, asset2 }, mapper.all(Sailboat.class));
		assertTrue(mapper.all(Harbor.class).isEmpty());
	}

	public void testCommit() throws Exception {
		// should not throw an exception if there is no transaction
		mapper.commit();
		mapper.beginTransaction();
		// should not throw an exception if there is nothing to commit
		mapper.commit();
		// should not throw an exception if a second commit occurs
		mapper.commit();
	}

	public void testDestroy_InTransaction() throws Exception {
		mapper.getSession().setFlushMode(FlushMode.AUTO);
		assertTrue(mapper.all(Sailboat.class).isEmpty());
		Sailboat oldSailboat = new Sailboat();
		mapper.save(oldSailboat);

		mapper.beginTransaction();
		mapper.destroy(oldSailboat);
		assertNull(mapper.find(Sailboat.class, oldSailboat.getId()));
		HibernateDataMapper newPersister = new HibernateDataMapper(hibernateTester.getSessionFactory());
		assertEquals(oldSailboat, newPersister.find(Sailboat.class, oldSailboat.getId()));
		CollectionAssert.containsOnly(new Sailboat[] { oldSailboat }, newPersister.all(Sailboat.class));
		// NOTE: This only works in auto FlushMode
		assertTrue(mapper.all(Sailboat.class).isEmpty());
		mapper.commit();

		assertNull(mapper.find(Sailboat.class, oldSailboat.getId()));
		Sailboat staleboat = newPersister.find(Sailboat.class, oldSailboat.getId());
		try {
			newPersister.getSession().refresh(staleboat);
			fail("Expected exception trying to refresh object that was deleted");
		} catch (UnresolvableObjectException e) {
		}
		// NOTE: This deleted object doesn't seem to be saved, even though
		// Hibernate doesn't throw an exception
		newPersister.save(staleboat);
		assertTrue(mapper.all(Sailboat.class).isEmpty());
		assertTrue(newPersister.all(Sailboat.class).isEmpty());
	}

	public void testDestroy_NoTransaction() throws Exception {
		Sailboat oldSailboat = new Sailboat();
		mapper.save(oldSailboat);
		mapper.destroy(oldSailboat);
		HibernateDataMapper newPersister = new HibernateDataMapper(hibernateTester.getSessionFactory());
		assertNull(mapper.find(Sailboat.class, oldSailboat.getId()));
		assertTrue(mapper.all(Sailboat.class).isEmpty());
		assertNull(newPersister.find(Sailboat.class, oldSailboat.getId()));
		assertTrue(newPersister.all(Sailboat.class).isEmpty());
	}

	public void testFind() {
		Sailboat santaMarie = new Sailboat("one");
		Sailboat nina = new Sailboat("two");
		Sailboat pinta = new Sailboat("one");
		mapper.save(santaMarie);
		mapper.save(pinta);
		mapper.save(nina);
		assertEquals(nina, mapper.find(Sailboat.class, "name", "two"));
		try {
			mapper.find(Sailboat.class, "name", "one");
			fail("There is more than one that matches");
		} catch (Exception e) {
		}

		// verify we can query by NULL?
		Harbor harbor = new Harbor();
		mapper.save(harbor);
		santaMarie.setHarbor(harbor);
		nina.setHarbor(harbor);
		mapper.save(santaMarie);
		mapper.save(nina);
		assertEquals(pinta, mapper.find(Sailboat.class, "harbor", null));

		assertNull(mapper.find(Sailboat.class, "name", "dummy"));
	}

	public void testFind_Unique_MultipleAttributes() throws Exception {
		Sailboat blackPearl = new Sailboat("black pearl");
		Harbor harbor = new Harbor();
		blackPearl.setHarbor(harbor);

		mapper.save(harbor);
		mapper.save(blackPearl);

		Sailboat loadedBoat = mapper.find(Sailboat.class, new String[] { "harbor", "name" }, new Object[] { harbor, "black pearl" });
		assertEquals(blackPearl, loadedBoat);
		loadedBoat = mapper.find(Sailboat.class, new String[] { "harbor", "name" }, new Object[] { blackPearl, "pinta" });
		assertNull(loadedBoat);
	}

	public void testFindAll_MultipleAttributes() throws Exception {
		Harbor harbor1 = new Harbor();
		mapper.save(harbor1);

		Sailboat boat1 = new Sailboat("standardName");
		Sailboat boat2 = new Sailboat("standardName");
		Sailboat boat3 = new Sailboat("differentName");
		boat1.setHarbor(harbor1);
		boat2.setHarbor(harbor1);
		mapper.save(boat1);
		mapper.save(boat2);
		mapper.save(boat3);

		CollectionAssert.containsOnly(new Sailboat[] { boat1, boat2 }, mapper.findAll(Sailboat.class, new String[] { "harbor", "name" }, new Object[] { harbor1, "standardName" }));
		CollectionAssert.containsOnly(new Sailboat[] { boat3 }, mapper.findAll(Sailboat.class, new String[] { "harbor", "name" }, new Object[] { null, "differentName" }));

		Harbor emptyHarbor = new Harbor();
		mapper.save(emptyHarbor);
		assertTrue(mapper.findAll(Sailboat.class, new String[] { "harbor", "name" }, new Object[] { emptyHarbor, "differentName" }).isEmpty());
	}

	public void testFindAll_SingleAttribute() throws Exception {
		Sailboat asset1 = new Sailboat("standardName");
		Sailboat asset2 = new Sailboat("standardName");
		Sailboat asset3 = new Sailboat("differentName");
		mapper.save(asset1);
		mapper.save(asset2);
		mapper.save(asset3);
		CollectionAssert.containsOnly(new Sailboat[] { asset1, asset2 }, mapper.findAll(Sailboat.class, "name", "standardName"));
		CollectionAssert.containsOnly(new Sailboat[] { asset3 }, mapper.findAll(Sailboat.class, "name", "differentName"));
		assertTrue(mapper.findAll(Sailboat.class, "name", "unusedName").isEmpty());
	}

	public void testReload() {
		Sailboat sailboat = new Sailboat();
		mapper.save(sailboat);
		Sailboat reloaded = mapper.reload(sailboat);
		assertNotNull(reloaded);
		assertNotSame(sailboat, reloaded);

		mapper.destroy(reloaded);
		try {
			mapper.reload(reloaded);
			fail("Reloading a non-persisted entity should fail. Use find if the instance might not be persisted.");
		} catch (Throwable expected) {
		}
	}

	// GREENBAR!
	public void testSave_InTransaction() {
		mapper.beginTransaction();
		assertTrue("sanity", mapper.all(Sailboat.class).isEmpty());

		Sailboat asset = new Sailboat("Fred");
		assertEquals(0, asset.getId().longValue());

		mapper.save(asset);
		Long firstId = asset.getId();
		assertNotNull(firstId);
		mapper.save(asset);
		assertEquals("Saving same object twice in same transaction should not be a problem", firstId, asset.getId());

		assertEquals("With FlushMode.COMMIT or anything, get by id works because session has identity map", asset, mapper.find(Sailboat.class, firstId));
		mapper.getSession().evict(asset);
		assertFalse("With FlushMode.COMMIT, queries DON'T return 'stale' data, and no-one knows why", mapper.all(Sailboat.class).isEmpty());

		HibernateDataMapper newPersister = new HibernateDataMapper(hibernateTester.getSessionFactory());
		assertNotNull("New session CAN find objects uncommitted in other sessions, and no-one knows why", newPersister.find(Sailboat.class, asset.getId()));

		mapper.commit();
		// Ensure query now returns committed data
		CollectionAssert.containsOnly(new Sailboat[] { asset }, mapper.all(Sailboat.class));
		assertEquals(asset, newPersister.find(Sailboat.class, asset.getId()));
	}

	public void testSave_InTransaction_OneObjectFails() throws Exception {
		Sailboat good = new Sailboat("Good Devotion");
		Sailboat bad = new Sailboat(null);

		mapper.beginTransaction();
		try {
			mapper.save(good);
			mapper.save(bad);
		} catch (PropertyValueException expected) {
			assertNull("The registry should close the session and transaction, therefore, gets from new session return null", mapper.find(Sailboat.class, good.getId()));
			assertNotNull("The bad still got an id", bad.getId());
		}
		try {
			mapper.commit();
		} catch (Throwable e) {
			fail("Should not fail commit even if there is no transaction");
		}
	}

	public void testSave_NoTransaction() {
		Sailboat asset = new Sailboat("Fred");
		assertEquals(0, asset.getId().longValue());
		mapper.save(asset);
		assertNotNull(asset.getId());
		assertEquals(asset, mapper.find(asset.getClass(), asset.getId()));

		mapper.save(asset);
		Long firstId = asset.getId();
		assertNotNull(asset.getId());

		// make sure if we save it twice we are only getting one object
		mapper.save(asset);
		Long secondId = asset.getId();
		assertNotNull(asset.getId());
		assertEquals(firstId, secondId);

		assertEquals(asset, mapper.find(Sailboat.class, firstId));
		assertEquals(asset, mapper.find(Sailboat.class, secondId));

		CollectionAssert.containsOnly(new Sailboat[] { asset }, mapper.all(Sailboat.class));

		HibernateDataMapper newPersister = new HibernateDataMapper(hibernateTester.getSessionFactory());
		assertEquals(asset, newPersister.find(Sailboat.class, asset.getId()));

		// Create an object that isn't "whole"
		Sailboat troubledAsset = new Sailboat(null);
		try {
			mapper.save(troubledAsset);
			fail("Should throw exception");
		} catch (PropertyValueException e) {
		}
		troubledAsset.setName("Something");
		mapper.save(troubledAsset);
		mapper.closeSession();
		assertEquals(troubledAsset, mapper.find(troubledAsset.getClass(), troubledAsset.getId()));
		assertNotNull(troubledAsset.getId());

		assertEquals(troubledAsset, newPersister.find(troubledAsset.getClass(), troubledAsset.getId()));
	}

	public void testSave_Twice() {
		Sailboat asset = new Sailboat("Fred");
		assertEquals(0, asset.getId().longValue());

		mapper.save(asset);
		Long firstId = asset.getId();
		assertNotNull(firstId);

		// make sure if we save it twice we are only getting one object
		mapper.save(asset);
		Long secondId = asset.getId();
		assertNotNull(secondId);

		assertEquals(firstId, secondId);
		assertEquals(asset, mapper.find(asset.getClass(), firstId));
		CollectionAssert.containsOnly(new Sailboat[] { asset }, mapper.all(Sailboat.class));
	}

	public void testSave_TwoThreadsModifyingCollectionAtSameTime() throws Exception {
		Sailboat sailboatOne = new Sailboat("firstBoat");
		Sailboat sailboatTwo = new Sailboat("secondBoat");
		mapper.save(sailboatOne);
		mapper.save(sailboatTwo);
		Harbor harbor = new Harbor();
		harbor.addSailboat(sailboatOne);
		harbor.addSailboat(sailboatTwo);
		mapper.save(harbor);

		HibernateDataMapper mapperOne = new HibernateDataMapper(hibernateTester.getSessionFactory());
		HibernateDataMapper mapperTwo = new HibernateDataMapper(hibernateTester.getSessionFactory());
		Harbor harborFromPersisterOne = mapperOne.find(Harbor.class, harbor.getId());
		Harbor harborFromPersisterTwo = mapperTwo.find(Harbor.class, harbor.getId());

		Sailboat sailboatThree = new Sailboat("thirdBoat");
		Sailboat sailboatFour = new Sailboat("fourthBoat");
		mapperOne.save(sailboatThree);
		mapperTwo.save(sailboatFour);
		harborFromPersisterOne.addSailboat(sailboatThree);
		harborFromPersisterTwo.addSailboat(sailboatFour);
		mapperOne.save(harborFromPersisterOne);
		mapperTwo.save(harborFromPersisterTwo);

		HibernateDataMapper newPersister = new HibernateDataMapper(hibernateTester.getSessionFactory());
		CollectionAssert.containsOnly(new Sailboat[] { sailboatOne, sailboatTwo, sailboatThree, sailboatFour }, newPersister.find(Harbor.class, harbor.getId()).getSailboats());

		mapperOne.getSession().clear();
		CollectionAssert.containsOnly(new Sailboat[] { sailboatOne, sailboatTwo, sailboatThree, sailboatFour }, mapperOne.find(Harbor.class, harbor.getId()).getSailboats());

		assertTrue(mapperTwo.getSession().contains(sailboatFour));
		mapperTwo.getSession().evict(harborFromPersisterTwo);
		assertFalse(mapperTwo.getSession().contains(harborFromPersisterTwo));
		assertTrue("Harbors don't cascade", mapperTwo.getSession().contains(sailboatFour));
		mapperTwo.getSession().close();
		mapperOne.getSession().close();

		try {
			mapperTwo.getSession().refresh(harborFromPersisterTwo.getSailboats());
			fail("Unfortunately, Hibernate can't refresh a collection, only its container.  If you see this, this fact has changed");
		} catch (Exception e) {
		}

		mapperTwo.getSession().refresh(harborFromPersisterTwo);
		CollectionAssert.containsOnly(new Sailboat[] { sailboatOne, sailboatTwo, sailboatThree, sailboatFour }, harborFromPersisterTwo.getSailboats());
		CollectionAssert.containsOnly(new Sailboat[] { sailboatOne, sailboatTwo, sailboatThree, sailboatFour }, mapperTwo.find(Harbor.class, harbor.getId()).getSailboats());
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
		mapper = new HibernateDataMapper(hibernateTester.getSessionFactory());
	}
}