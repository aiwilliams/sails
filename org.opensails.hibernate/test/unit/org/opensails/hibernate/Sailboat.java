package org.opensails.hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.validator.NotNull;
import org.opensails.sails.persist.AbstractIdentifiable;

@Entity
public class Sailboat extends AbstractIdentifiable {
	@ManyToOne
	private Harbor harbor;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@NotNull
	private String name;

	public Sailboat() {
		this("undefined");
	}

	public Sailboat(String name) {
		this.name = name;
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

	public void setHarbor(Harbor harbor) {
		this.harbor = harbor;
	}

	public void setName(String name) {
		this.name = name;
	}
}
