package org.opensails.sails.tester.util;

import java.util.Collection;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

import org.opensails.sails.util.Quick;

public class CollectionAssertTest extends TestCase {

	public void testContainsOnlyCollectionCollection() {
		Collection<String> expected = Quick.list("two", "two");
		Collection<String> actual = Quick.list("one", "two");
		try {
			CollectionAssert.containsOnly(expected, actual);
			throw new RuntimeException("While the actual has an object equal to both the expecteds, this isn't the behavior we're after");
		} catch (AssertionFailedError exp) {}
	}

}
