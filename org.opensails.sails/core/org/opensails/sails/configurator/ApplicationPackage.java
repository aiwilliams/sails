package org.opensails.sails.configurator;

public class ApplicationPackage {
	protected String packageName;
	protected String namespace;

	public ApplicationPackage(ApplicationPackage applicationPackage, Class packageClass) {
		this(applicationPackage.getNamespace(), packageClass);
	}

	public ApplicationPackage(ApplicationPackage applicationPackage, String extension) {
		this(applicationPackage.getNamespace(), applicationPackage.getPackageName(), extension);
	}

	public ApplicationPackage(String namespace, Class classPackage) {
		this.namespace = namespace;
		this.packageName = classPackage.getPackage().getName();
	}

	public ApplicationPackage(String namespace, String packageName) {
		this.namespace = namespace;
		this.packageName = packageName;
	}

	public ApplicationPackage(String namespace, String parent, String extension) {
		this(namespace, parent + "." + extension);
	}

	public String getNamespace() {
		return namespace;
	}

	public Package getPackage() {
		Package p = Package.getPackage(getPackageName());
		if (p == null) throw new IllegalArgumentException(String.format("The client desires the real Java Package named %s. Please provide the name of the package by referencing a class in that package and asking it for the name. This will cause the VM to load that class and therefore that package.", getPackageName()));
		return p;
	}

	public String getPackageName() {
		return packageName;
	}
}
