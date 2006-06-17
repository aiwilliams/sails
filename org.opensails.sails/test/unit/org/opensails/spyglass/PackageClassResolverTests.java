package org.opensails.spyglass;

import java.util.List;
import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;

import org.opensails.spyglass.resolvers.PackageClassResolver;

/**
 * Note that there is a file in this directory called package-info.java that
 * makes this technology work.
 * 
 * @author aiwilliams
 */
public class PackageClassResolverTests extends TestCase {
	public void testResolve_NoPackageInfo() throws Exception {
		PackageClassResolver<Object> resolver = new PackageClassResolver<Object>("some.package.that.dont.exist");
		assertNull(resolver.resolve(String.class));
		assertNull(resolver.resolve("string"));
	}

	public void testResolve_NoSuffix() throws Exception {
		PackageClassResolver<Object> resolver = new PackageClassResolver<Object>(getClass().getPackage());
		assertEquals(PackageClassResolverTests.class, resolver.resolve(Map.class));
		assertEquals(PackageClassResolverTests.class, resolver.resolve(String.class));
		assertEquals(PackageClassResolverTests.class, resolver.resolve(List.class));
		assertEquals(SpyGlassTests.class, resolver.resolve(Set.class));
		assertEquals(PackageClassResolverTests.class, resolver.resolve("packageClassResolverTests"));
	}

	public void testResolve_NullPackage() {
		try {
			new PackageClassResolver<Object>((Package) null);
			throw new RuntimeException("If you pass a null package, there is absolutely no way for this instance to provide value");
		} catch (IllegalArgumentException expected) {}
	}

	public void testResolve_Suffix() throws Exception {
		PackageClassResolver<Object> resolver = new PackageClassResolver<Object>(getClass().getPackage(), "Tests");
		assertEquals(PackageClassResolverTests.class, resolver.resolve("packageClassResolver"));

		// It will find the closest match first
		resolver = new PackageClassResolver<Object>(getClass().getPackage(), "Value");
		assertEquals(ClassKey.class, resolver.resolve(ClassKey.class));
		assertFalse(ClassKeyValue.class.equals(resolver.resolve(ClassKey.class)));
	}
}
