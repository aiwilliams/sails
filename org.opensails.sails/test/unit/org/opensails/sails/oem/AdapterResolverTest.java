package org.opensails.sails.oem;

import junit.framework.TestCase;

import org.opensails.rigging.ScopedContainer;
import org.opensails.sails.ApplicationScope;
import org.opensails.sails.SailsException;
import org.opensails.sails.Scope;
import org.opensails.sails.adapter.AbstractAdapter;
import org.opensails.sails.adapter.AdaptationException;
import org.opensails.sails.adapter.IAdapter;
import org.opensails.sails.adapter.oem.PrimitiveAdapter;
import org.opensails.sails.util.ClassResolverAdapter;

public class AdapterResolverTest extends TestCase {
	public void testResolve_NoAdapterFound() throws Exception {
		AdapterResolver resolver = new AdapterResolver();
		try {
			resolver.resolve(ShamAdapter.class, null);
			fail("should complain when no adapter found");
		} catch (SailsException expected) {}
	}

	public void testResolve_Scoped() throws Exception {
		final ShamAdapter adapter = new ShamAdapter();
		AdapterResolver resolver = new AdapterResolver();
		resolver.push(new ClassResolverAdapter<IAdapter>() {
			@Override
			public Class<? extends IAdapter> resolve(Class key) {
				return ShamAdapter.class;
			}
		});
		ScopedContainer container = new ScopedContainer(ApplicationScope.REQUEST) {
			@Override
			@SuppressWarnings("unchecked")
			public <T> T instance(Class<T> key) {
				return (T) adapter;
			}
		};
		container.register(ShamAdapter.class);
		assertSame(adapter, resolver.resolve(String.class, container));
	}

	public void testResolve_StringAndPrimitive() throws Exception {
		AdapterResolver resolver = new AdapterResolver();
		IAdapter stringAdapter = resolver.resolve(String.class, null);
		assertEquals(PrimitiveAdapter.class, stringAdapter.getClass());
		assertSame(stringAdapter, resolver.resolve(String.class, null));
		assertEquals(PrimitiveAdapter.class, resolver.resolve(int.class, null).getClass());
	}

	@Scope(ApplicationScope.REQUEST)
	public static class ShamAdapter extends AbstractAdapter {
		public Object forModel(Class modelType, Object fromWeb) throws AdaptationException {
			return null;
		}

		public Object forWeb(Class modelType, Object fromModel) throws AdaptationException {
			return null;
		}
	}
}
