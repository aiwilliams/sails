package org.opensails.sails.adapter.oem;

import junit.framework.TestCase;

import org.opensails.sails.adapter.IAdapter;

public class DefaultAdapterResolverTest extends TestCase {
	public void testResolveClass() {
		DefaultAdapterResolver<IAdapter> resolver = new DefaultAdapterResolver<IAdapter>();
		assertPrimitiveAdapter(boolean.class, resolver);
		assertPrimitiveAdapter(Boolean.class, resolver);
		assertPrimitiveAdapter(int.class, resolver);
		assertPrimitiveAdapter(Integer.class, resolver);
		assertPrimitiveAdapter(long.class, resolver);
		assertPrimitiveAdapter(Long.class, resolver);
		assertPrimitiveAdapter(float.class, resolver);
		assertPrimitiveAdapter(Float.class, resolver);
		assertPrimitiveAdapter(char.class, resolver);
		assertPrimitiveAdapter(Character.class, resolver);
		assertPrimitiveAdapter(byte.class, resolver);
		assertPrimitiveAdapter(Byte.class, resolver);
		assertPrimitiveAdapter(short.class, resolver);
		assertPrimitiveAdapter(Short.class, resolver);
		assertPrimitiveAdapter(double.class, resolver);
		assertPrimitiveAdapter(Double.class, resolver);
		assertPrimitiveAdapter(String.class, resolver);
	}

	void assertPrimitiveAdapter(Class<?> classToAdapt, DefaultAdapterResolver<IAdapter> resolver) {
		Class<? extends IAdapter> adapter = resolver.resolve(classToAdapt);
		assertEquals(PrimitiveAdapter.class, adapter);
	}
}
