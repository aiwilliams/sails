package org.opensails.prevayler.acceptance;

import org.opensails.prevayler.IdentifiableObjectPrevalentSystem;
import org.opensails.prevayler.PrevaylerPersister;
import org.prevayler.Prevayler;
import org.prevayler.PrevaylerFactory;

public class ExternalMain {

	protected PrevaylerPersister createPersister() {
		IdentifiableObjectPrevalentSystem prevalantSystem = new IdentifiableObjectPrevalentSystem();
		Prevayler prevayler = null;
		try {
			prevayler = PrevaylerFactory.createPrevayler(prevalantSystem);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return new PrevaylerPersister(prevayler);
	}

}
