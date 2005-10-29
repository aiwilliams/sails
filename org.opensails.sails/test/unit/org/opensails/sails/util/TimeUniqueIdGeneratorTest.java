package org.opensails.sails.util;

import junit.framework.TestCase;

public class TimeUniqueIdGeneratorTest extends TestCase {
	public void testNext() {
		TimeUniqueIdGenerator generator = new TimeUniqueIdGenerator();
		Object idOne = generator.next();
		Object idTwo = generator.next();
		Object idThree = generator.next();
		assertFalse(idOne.equals(idTwo));
		assertFalse(idOne.equals(idThree));
	}
}
