package org.opensails.hibernate;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.opensails.sails.persist.AbstractIdentifiable;

@Entity
public class Harbor extends AbstractIdentifiable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToMany(targetEntity = Sailboat.class, mappedBy = "harbor")
	private Collection<Sailboat> sailboats = new ArrayList<Sailboat>();

	public void addSailboat(Sailboat sailboat) {
		sailboats.add(sailboat);
		sailboat.setHarbor(this);
	}

	public Long getId() {
		return id;
	}

	public Collection<Sailboat> getSailboats() {
		return sailboats;
	}
}
