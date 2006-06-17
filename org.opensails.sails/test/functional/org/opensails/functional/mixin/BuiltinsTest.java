package org.opensails.functional.mixin;

import junit.framework.TestCase;

import org.opensails.functional.FunctionalTestConfigurator;
import org.opensails.mixin.tester.MixinTester;

public class BuiltinsTest extends TestCase {

	public void testCharSequence() throws Exception {
		MixinTester t = new MixinTester(FunctionalTestConfigurator.class);
		t.addPackage("org.opensails.functional.mixin");

		t.expose("aString", "hello");
		t.expose("aStringBuilder", new StringBuilder("hello"));
		t.expose("aStringBuffer", new StringBuffer("hello"));

		t.assertEquals("hello", "$aString");
		t.assertEquals("5", "$aString.length");
		t.assertEquals("false", "$aString.blank?");

		t.assertEquals("hello", "$aStringBuilder");
		t.assertEquals("5", "$aStringBuilder.length");
		t.assertEquals("false", "$aStringBuilder.blank?");

		/*
		 * Capable of finding most specific and using it first.
		 */
		t.assertEquals("hello", "$aStringBuffer");
		t.assertEquals("5", "$aStringBuffer.length");
		t.assertEquals("world", "$aStringBuffer.sayit");
	}
}
