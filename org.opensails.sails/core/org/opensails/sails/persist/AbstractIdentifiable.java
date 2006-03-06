package org.opensails.sails.persist;

/**
 * A reasonable superclass for IIdentifiable, crafted to save from repeating
 * yourself a bit.
 * <p>
 * If you are using the Hibernate extensions, you can subclass this to get a
 * useful equals and hashCode, but you must create your own id field so that it
 * can be annotated (don't forget to override getId, so that it will return your
 * field). You must declare the Entity and Id annotations on your class.
 * 
 * @author aiwilliams
 */
public class AbstractIdentifiable implements IIdentifiable {
	private Long id;

	public Long getId() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		return getId() != null && getId().equals(getClass().cast(obj).getId());
	}

	@Override
	public int hashCode() {
		return getId() != null ? getId().hashCode() : super.hashCode();
	}
}
