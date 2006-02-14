package org.opensails.functional.mixin;

import junit.framework.TestCase;

import org.opensails.ezfile.RealEzFile;
import org.opensails.functional.SailsFunctionalTester;
import org.opensails.sails.tester.Page;

public class BuiltinsTest extends TestCase {
	/*
	 * Expected to fail until I fix RequireMixin
	 */
	public void testRequire() throws Exception {
		SailsFunctionalTester t = new SailsFunctionalTester();
		Page page = t.getTemplated(RealEzFile.resource(getClass(), "BuiltinsTest_TestRequire.vto").text());
		page.scripts().assertContains("prototype", 0);
		page.scripts().assertContains("prototype.js", 1);
	}
}
