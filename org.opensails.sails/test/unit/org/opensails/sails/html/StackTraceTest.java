package org.opensails.sails.html;

import junit.framework.TestCase;

public class StackTraceTest extends TestCase {

	public void testToString() throws Exception {
		StackTrace stackTrace = new StackTrace(new RuntimeException("my message"));
		String output = stackTrace.toString();
		assertTrue(output.contains("my message"));
		assertTrue(output.contains("StackTraceTest.testToString"));
		assertTrue(output.contains("RuntimeException"));
		assertFalse(output.contains("Caused by:"));
	}
	
	public void testCausedBy() throws Exception {
		RuntimeException exception = new RuntimeException("my message", new NullPointerException("cause message"));
		StackTrace stackTrace = new StackTrace(exception);
		String output = stackTrace.toString();
		assertTrue(output.contains("my message"));
		assertTrue(output.contains("StackTraceTest.testCausedBy"));
		assertTrue(output.contains("RuntimeException"));

		assertTrue(output.contains("cause message"));
		assertTrue(output.contains("NullPointerException"));
		assertTrue(output.contains("Caused by:"));
		System.out.println(output);
	}
}
