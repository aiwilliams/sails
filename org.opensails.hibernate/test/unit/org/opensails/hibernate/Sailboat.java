package org.opensails.hibernate;

import javax.persistence.*;

import org.hibernate.validator.*;
import org.opensails.sails.persist.*;

@Entity(access = AccessType.FIELD)
public class Sailboat implements IIdentifiable {
	@ManyToOne
	private Harbor harbor;
	@Id(generate = GeneratorType.AUTO)
	private long id;
	@NotNull
	private String name;

	public Sailboat() {
		this("undefined");
	}

	public Sailboat(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		return id == ((Sailboat) obj).id;
	}

	public Harbor getHarbor() {
		return harbor;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		return new Long(id).hashCode();
	}

	public void setHarbor(Harbor harbor) {
		this.harbor = harbor;
	}

	public void setName(String name) {
		this.name = name;
	}
}
