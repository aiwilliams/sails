package org.opensails.sails.controller;

import org.opensails.spyglass.resolvers.PackageClassResolver;

public class ControllerPackage extends PackageClassResolver<IControllerImpl> {
	public ControllerPackage(Class<? extends IControllerImpl> controller) {
		this(controller.getPackage().getName());
	}

	public ControllerPackage(String packageRoot) {
		super(packageRoot, "Controller");
	}
}
