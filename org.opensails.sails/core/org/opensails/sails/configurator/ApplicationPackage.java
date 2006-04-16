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

	public String getPackageName() {
		return packageName;
	}
}
