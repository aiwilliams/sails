package org.opensails.prevayler;

import java.util.ArrayList;

import junit.framework.TestCase;

import org.opensails.prevayler.acceptance.IdentifiableArrayList;
import org.opensails.prevayler.acceptance.IdentifiableString;
import org.opensails.sails.persist.IObjectPersister;
import org.prevayler.Prevayler;
import org.prevayler.PrevaylerFactory;

public class PrevaylerPersisterTest extends TestCase {

	protected IdentifiableObjectPrevalentSystem prevalantSystem;

	protected Prevayler prevayler;

	protected IObjectPersister dataMapper;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		prevalantSystem = new IdentifiableObjectPrevalentSystem();
		prevayler = PrevaylerFactory.createTransientPrevayler(prevalantSystem);
		dataMapper = new PrevaylerPersister(prevayler);
	}

	public void testSaveFindAndDelete() throws Exception {
		Long id = 1L;
		Long nonExtantIdOne = 10L;
		Long nonExtantIdTwo = 11L;

		IdentifiableString identifiableString = new IdentifiableString(id, "stringOne");

		dataMapper.save(identifiableString);
		assertEquals(identifiableString, dataMapper.find(IdentifiableString.class, id));
		assertNull(dataMapper.find(IdentifiableString.class, nonExtantIdOne));

		IdentifiableArrayList identifiableArrayList = new IdentifiableArrayList(id, new ArrayList());
		dataMapper.save(identifiableArrayList);

		assertEquals(identifiableArrayList, dataMapper.find(IdentifiableArrayList.class, id));
		assertNull(dataMapper.find(IdentifiableArrayList.class, nonExtantIdTwo));

		dataMapper.destroy(identifiableArrayList);
		assertNull(dataMapper.find(IdentifiableArrayList.class, id));

		dataMapper = new PrevaylerPersister(prevayler);
		assertEquals(identifiableString, dataMapper.find(IdentifiableString.class, id));
	}

	public void testAll() throws Exception {
		assertTrue(dataMapper.all(IdentifiableString.class).isEmpty());
		dataMapper.save(new IdentifiableString(1L, ""));
		assertFalse(dataMapper.all(IdentifiableString.class).isEmpty());
	}

}
