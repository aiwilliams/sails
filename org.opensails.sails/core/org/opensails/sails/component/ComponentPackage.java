package org.opensails.sails.component;

import org.opensails.spyglass.resolvers.PackageClassResolver;

public class ComponentPackage extends PackageClassResolver<IComponentImpl> {
	public ComponentPackage(Class<? extends IComponentImpl> controller) {
		this(controller.getPackage().getName());
	}

	public ComponentPackage(String packageRoot) {
		super(packageRoot, "Component");
	}
}
