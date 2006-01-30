package org.opensails.prevayler.acceptance;

import java.util.Iterator;

import org.opensails.prevayler.IdentifiableString;
import org.opensails.prevayler.PrevaylerPersister;

public class Read extends ExternalMain {
	public static void main(String[] args) throws InterruptedException {
		PrevaylerPersister persister = new Read().createPersister();
		Iterator<IdentifiableString> iterator = persister.all(IdentifiableString.class).iterator();

		if (!iterator.hasNext())
			System.out.println("nothing persisted");

		while (iterator.hasNext()) {
			IdentifiableString identifiableString = (IdentifiableString) iterator.next();
			System.out.println(identifiableString);
		}
	}
}
