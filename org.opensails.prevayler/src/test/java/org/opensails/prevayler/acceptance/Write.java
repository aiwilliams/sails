package org.opensails.prevayler.acceptance;

import org.opensails.prevayler.IdentifiableString;
import org.opensails.prevayler.PrevaylerPersister;

public class Write extends ExternalMain {

	public static void main(String[] args) {
		String arg = args[0];
		String[] idAndContent = arg.split("=");
		String id = idAndContent[0];
		String content = idAndContent[1];

		new Write().save(id, content);
	}

	public void save(String id, String content) {
		PrevaylerPersister persister = createPersister();
		persister.save(new IdentifiableString(new Long(id), content));
	}

}
