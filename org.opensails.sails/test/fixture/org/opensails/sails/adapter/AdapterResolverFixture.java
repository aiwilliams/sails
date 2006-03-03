package org.opensails.sails.adapter;

import org.opensails.rigging.IScopedContainer;
import org.opensails.sails.adapter.oem.PrimitiveAdapter;

public class AdapterResolverFixture {
	public static ContainerAdapterResolver container(IAdapter toReturn) {
		return new ContainerAdapterResolver(single(toReturn), null);
	}

	/**
	 * @return supports primitives only
	 */
	public static ContainerAdapterResolver defaultContainer() {
		return container(new PrimitiveAdapter());
	}

	public static IAdapterResolver single(final IAdapter toReturn) {
		return new IAdapterResolver() {
			public IAdapter resolve(Class<?> parameterClass, IScopedContainer container) {
				return toReturn;
			}
		};
	}
}
