package org.opensails.sails.oem;

import junit.framework.TestCase;

public class ClasspathResourceResolverTest extends TestCase {

	public void testResolve() throws Exception {
		String mountPoint = "common";
		String pathInfo = "/images/sailslogo.jpg";
		ClasspathResourceResolver commonsResolver = new ClasspathResourceResolver(mountPoint);

		assertNotNull(commonsResolver.resolve(pathInfo));
	}

	public void testFullyQualified() throws Exception {
		String mountPoint = "common";
		String idWithLeadingSlash = "/images/sailslogo.jpg";
		String idSansLeadingSlash = "images/sailslogo.jpg";
		ClasspathResourceResolver commonsResolver = new ClasspathResourceResolver(mountPoint);

		assertEquals("common/images/sailslogo.jpg", commonsResolver.moutPointRelative(idWithLeadingSlash));
		assertEquals("common/images/sailslogo.jpg", commonsResolver.moutPointRelative(idSansLeadingSlash));
	}

}
