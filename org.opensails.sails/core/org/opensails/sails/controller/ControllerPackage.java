package org.opensails.sails.controller;

import org.opensails.sails.util.ClassHelper;
import org.opensails.sails.util.ComponentPackage;

public class ControllerPackage extends ComponentPackage<IControllerImpl> {
	public ControllerPackage(Class<? extends IControllerImpl> controller) {
		this(ClassHelper.getPackage(controller));
	}

	public ControllerPackage(String packageRoot) {
		super(packageRoot, "Controller");
	}
}
