package org.opensails.sails.controller;

import org.opensails.sails.util.ClassHelper;
import org.opensails.spyglass.resolvers.PackageClassResolver;

public class ControllerPackage extends PackageClassResolver<IControllerImpl> {
	public ControllerPackage(Class<? extends IControllerImpl> controller) {
		this(ClassHelper.getPackage(controller));
	}

	public ControllerPackage(String packageRoot) {
		super(packageRoot, "Controller");
	}
}
